/**
 * 
 * PipeLine.java
 *
 * 14:10:57
 *
 */
package ru.ipccenter.webshell.pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Ivan Penkin <a
 *         href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 * 
 */
public class PipeLine {

    
    private Process[] processes;
    
    private InputStream       		  stdout;
    private MultiProcessErrorInputStream  stderr;
    private OutputStream      		  stdin;
    
    /**
     * 
     * @param processes array of processes in pipeline 
     */
    public PipeLine(Process... processes) {

	if (processes.length == 0) {
	    throw new IllegalArgumentException();
	}
	this.processes = processes;

	stdin = processes[0].getOutputStream();
	stdout = processes[processes.length - 1].getInputStream();
	stderr = new MultiProcessErrorInputStream(processes);
    }

    /**
     * 
     * @return stdout stdout of last process in pipeline
     */
    public InputStream getStdout() {

	return stdout;
    }

    /**
     * 
     * @return stderr stderr of all processes in pipeline 
     * (error messages from first process to last) 
     */
    public InputStream getStderr() {

	return stderr;
    }

    /**
     * 
     * @return stdin stdin of first process in pipeline
     */
    public OutputStream getStdin() {

	return stdin;
    }

    /**
     * Execute data transfer betwen processes in pipeline
     * 
     * @throws Exception when {@link IOException} occurred in pipes
     */
    public void exec() throws Exception {

	if (processes == null) {
	    return;
	}

        class Pair<K, V> {
            public K first;
            public V second;
        }
	
	ArrayList<Pair<Thread, Pipe>> threads = 
		new ArrayList<Pair<Thread,Pipe>>();

	for (int i = 0; i < processes.length; i++) {

	    Process p1 = processes[i];
	    Process p2 = null;

	    if (i + 1 < processes.length) {
		p2 = processes[i + 1];

		Pair<Thread, Pipe> thread = new Pair<Thread, Pipe>();
		thread.second = new Pipe(p1.getInputStream(), p2.getOutputStream());
		thread.first  = new Thread(thread.second);
		threads.add(thread);
		
		thread.first.start();
	    }
	}

	while(threads.size() > 0) {
	    Iterator<Pair<Thread, Pipe>> itr = threads.iterator();
		
	    while (itr.hasNext()) {
		Pair<Thread, Pipe> thread = itr.next();
		if (!thread.first.isAlive()) {
		    itr.remove();
		    Exception e = thread.second.getException().get();
		    if (e != null) {
			throw e;
		    }
		}
	    }
	}
	
	
	processes[processes.length - 1].waitFor();
    }
}

