package ru.ipccenter.webshell.server;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.ipccenter.webshell.server.shell.Shell;

import com.sun.grizzly.websockets.DefaultWebSocket;
import com.sun.grizzly.websockets.ProtocolHandler;
import com.sun.grizzly.websockets.WebSocketListener;


public class ShellOutputSocket extends DefaultWebSocket implements Runnable{

    final Lock sourceLock = new ReentrantLock();
    final Condition newSource = sourceLock.newCondition(); 
    
    volatile private boolean exit;
    private String  source;
    private Shell   shell;
    
    public ShellOutputSocket(ProtocolHandler arg0, WebSocketListener... arg1) {

	super(arg0, arg1);
    }

    @Override
    public void run() {

	try {
	    shell = new Shell(this);
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
		shell.process(sourceCopy);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } finally {
		sourceLock.unlock();
	    }
	}
	
	shell = null;
    }
    
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
    
    public void exit() {
	exit = true;
    }
}
