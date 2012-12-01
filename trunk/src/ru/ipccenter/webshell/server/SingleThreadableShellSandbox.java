/**
 * 
 * SingleThreadableShellSandbox.java
 *
 * 17:08:13
 *
 */
package ru.ipccenter.webshell.server;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.ipccenter.webshell.server.shell.Shell;

import com.sun.grizzly.websockets.DefaultWebSocket;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class SingleThreadableShellSandbox implements Runnable {

    final Lock sourceLock = new ReentrantLock();
    final Condition newSource = sourceLock.newCondition(); 
    
    volatile private boolean exit;
    private String  source;
    private Shell   shell;
    
    /**
     * 
     * @throws IOException 
     */
    public SingleThreadableShellSandbox() {

    }
    
    /**
     * 
     * @param socket
     */
    public void setShellOutput(DefaultWebSocket socket) {
	
	synchronized (shell) {
	    if (shell == null) {
		return;
	    }
		
	    shell.setOutput(socket);
	}
    }
    
    /**
     * 
     * @return
     */
    public String getCurentDirectoryPath() {
	
	synchronized (shell) {
	    return shell.getCurentDirectoryPath();
	}
    }

    /**
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

	try {
	    shell = new Shell();
	} catch (IOException e) {
	    e.printStackTrace();
	    return;
	}

	while (!exit) {
	    
	    String sourceCopy;
	    
	    sourceLock.lock();
	    try {
		newSource.await();
		sourceCopy = source;
		synchronized (shell) {
		    shell.process(sourceCopy);
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } finally {
		sourceLock.unlock();
	    }
	}
	
	shell = null;
    }
    
    /**
     * 
     * @param source
     */
    public void process(String source) {
	
	if (shell == null) {
	    return;
	}
	sourceLock.lock();
	try {
	    this.source = source;
	    newSource.signal();
	} finally {
	    sourceLock.unlock();
	}
    }
    
    /**
     * 
     */
    public void exit() {
	
	exit = true;
    }
    
    /**
     * 
     */
    protected void finalize() throws Throwable {
	
	exit();
    }
}
