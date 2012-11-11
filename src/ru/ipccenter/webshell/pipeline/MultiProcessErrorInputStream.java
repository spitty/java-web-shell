/**
 * 
 * MultiProcessErrorInputStream.java
 *
 * 16:14:52
 *
 */
package ru.ipccenter.webshell.pipeline;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
class MultiProcessErrorInputStream extends InputStream {

    private InputStream[] streams;
    private int           curentStreamIndex;
    
    public MultiProcessErrorInputStream(Process... processes) {
	
	streams = new InputStream[processes.length];
	
	int i = 0;
	for (Process curent: processes) {
	    streams[i++] = curent.getErrorStream();
	}
	
	curentStreamIndex = (streams.length > 0)? 0: -1;
    }
    
    @Override
    public int read() throws IOException {

	if (curentStreamIndex == -1 
		|| curentStreamIndex >= streams.length) {
	    
	    /* no enough input streams  */
	    return -1;
	} else {
	    int nextByte = streams[curentStreamIndex].read();
	    if (nextByte == -1) {
		
		/* next stream */
		curentStreamIndex++;
		nextByte = read();
	    }
	    return nextByte;
	}
    }
}