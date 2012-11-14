/**
 * 
 * Pipe.java
 * 
 * 11.11.12
 * 
 */
package ru.ipccenter.webshell.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.io.IOUtils;


/**
 * 
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 */
class Pipe implements Runnable {

    private AtomicReference<Exception> exception =
	    new AtomicReference<Exception>();
    
    private InputStream  input;
    private OutputStream output;
    

    public Pipe(InputStream input, OutputStream output) {
	
        this.input = input;
        this.output = output;
    }
    
    /**
     * @return exception
     */
    public AtomicReference<Exception> getException() {
    
        return exception;
    }
    
    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        byte[] bufer = new byte[512];
        int length = 1;
        
	try {
	    while ((length = input.read(bufer, 0, bufer.length)) > -1) {
                output.write(bufer, 0, length);
                output.flush();
            }
        } catch (Exception e) {
            exception.set(e);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }
}
