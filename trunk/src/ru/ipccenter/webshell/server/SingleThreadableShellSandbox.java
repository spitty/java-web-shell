/**
 * 
 * SingleThreadableShellSandbox.java
 *
 * 17:08:13
 *
 */
package ru.ipccenter.webshell.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.ipccenter.webshell.server.shell.Shell;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class SingleThreadableShellSandbox implements Runnable {

    final Lock sourceLock = new ReentrantLock();
    final Condition newSource = sourceLock.newCondition(); 
    
    final Lock processingLock = new ReentrantLock();
    final Condition endProcessing = processingLock.newCondition();
    
    volatile private boolean exit;
    
    private boolean processing;
    private Shell   shell;
    private String  source;
    
    
    /**
     * 
     * @throws IOException 
     */
    public SingleThreadableShellSandbox() throws IOException {

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
	}
	
	while (!exit) {
	    
	    sourceLock.lock();
	    try {
		newSource.await();
		String sourceCopy = source;
		shell.process(sourceCopy);
		
		processingLock.lock();
		processing = false;
		endProcessing.signal();
		processingLock.unlock();
		
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } finally {
		sourceLock.unlock();
	    }
	}
    }
    
    /**
     * 
     * @param source
     */
    public void process(String source) {
	
	if (shell == null) {
	    return;
	}
	
	processingLock.lock();
	processing = true;
	processingLock.unlock();
	
	sourceLock.lock();
	try {
	    this.source = source;
	    newSource.signal();
	} finally {
	    sourceLock.unlock();
	}
    }
    
    public void waitForEndProcessing() {
	
	processingLock.lock();
	try {
	    while (processing == true)
		endProcessing.await();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    processingLock.unlock();
	}
    }
    
    /**
     * 
     * @return
     */
    public InputStream getOutputStream() {
	
	return (shell == null)? null: shell.getOutputStream();
    }
    
    /**
     * 
     * @return
     */
    public InputStream getErrorStream() {
	
	return (shell == null)? null: shell.getErrorStream();
    }
    
    /**
     * 
     */
    public void destroy() {
	
	exit = true;
    }
    
    protected void finalize() throws Throwable {
	
	destroy();
    }
}
