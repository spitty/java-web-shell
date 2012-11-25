/**
 * 
 * WebPrintStream.java
 *
 * 17:41:14
 *
 */
package ru.ipccenter.webshell.server.shell.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class WebPrintStream extends PrintStream {
    
    private static final String LINE_BREAK = "<br/>";

    /**
     * @param out
     */
    public WebPrintStream(OutputStream out) {

	super(out);
    }

    /**
     * @param fileName
     * @throws FileNotFoundException
     */
    public WebPrintStream(String fileName) throws FileNotFoundException {

	super(fileName);
    }

    /**
     * @param file
     * @throws FileNotFoundException
     */
    public WebPrintStream(File file) throws FileNotFoundException {

	super(file);
    }

    /**
     * @param out
     * @param autoFlush
     */
    public WebPrintStream(OutputStream out, boolean autoFlush) {

	super(out, autoFlush);
    }

    /**
     * @param fileName
     * @param csn
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public WebPrintStream(String fileName, String csn)
	    throws FileNotFoundException, UnsupportedEncodingException {

	super(fileName, csn);
    }

    /**
     * @param file
     * @param csn
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public WebPrintStream(File file, String csn) throws FileNotFoundException,
	    UnsupportedEncodingException {

	super(file, csn);
    }

    /**
     * @param out
     * @param autoFlush
     * @param encoding
     * @throws UnsupportedEncodingException
     */
    public WebPrintStream(OutputStream out, boolean autoFlush, String encoding)
	    throws UnsupportedEncodingException {

	super(out, autoFlush, encoding);
    }
    
    /**
     * 
     * @param s
     */
    public void print(String s) {
	if (s == null) {
	    s = "null";
	} else {
	    s = s.replace(System.getProperty("line.separator"), LINE_BREAK);
	}
	super.print(s);
    }
    
    /**
     * 
     * @param obj 
     */
    public void print(Object obj) {
	super.print(String.valueOf(obj)
		.replace(System.getProperty("line.separator"), LINE_BREAK));
    }
    
    /**
     * Terminates the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     */
    public void println() {
	newWebLine();
    }

    /**
     * Prints a boolean and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>boolean</code> to be printed
     */
    public void println(boolean x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints a character and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>char</code> to be printed.
     */
    public void println(char x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints an integer and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>int</code> to be printed.
     */
    public void println(int x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints a long and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  a The <code>long</code> to be printed.
     */
    public void println(long x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints a float and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>float</code> to be printed.
     */
    public void println(float x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints a double and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(double)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>double</code> to be printed.
     */
    public void println(double x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints an array of characters and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and
     * then <code>{@link #println()}</code>.
     *
     * @param x  an array of chars to print.
     */
    public void println(char x[]) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints a String and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>String</code> to be printed.
     */
    public void println(String x) {
	synchronized (this) {
	    print(x);
	    newWebLine();
	}
    }

    /**
     * Prints an Object and then terminate the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value,
     * then behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>Object</code> to be printed.
     */
    public void println(Object x) {
        String s = String.valueOf(x);
        synchronized (this) {
            print(s);
            newWebLine();
        }
    }
    
    private void newWebLine() {
	super.print(LINE_BREAK);
    }
}
