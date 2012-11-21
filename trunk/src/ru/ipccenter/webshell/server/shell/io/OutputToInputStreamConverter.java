/**
 * 
 * StreamConverter.java
 *
 * 13:39:47
 *
 */
package ru.ipccenter.webshell.server.shell.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.commons.io.IOUtils;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class OutputToInputStreamConverter {

    protected PipedOutputStream src;
    protected PipedInputStream  dst;
    
    public OutputToInputStreamConverter() throws IOException {

	src = new PipedOutputStream();
	dst = new PipedInputStream(src);
    }

    public OutputToInputStreamConverter(int pipeSize) throws IOException {

	src = new PipedOutputStream();
	dst = new PipedInputStream(src, pipeSize);
    }

    
    public InputStream getInput() {
	
	return dst;
    }
    
    public OutputStream getOutput() {
	
	return src;
    }
    
    protected void finalyze() throws Throwable {
	
	IOUtils.closeQuietly(src);
	IOUtils.closeQuietly(dst);
    }
}
