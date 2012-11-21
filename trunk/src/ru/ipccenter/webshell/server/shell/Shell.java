/**
 * 
 * Shell.java
 *
 * 19:46:09
 *
 */
package ru.ipccenter.webshell.server.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import ru.ipccenter.webshell.server.shell.io.OutputToInputStreamConverter;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class Shell extends JSGlobalObject {

    private static final long serialVersionUID = -2348308525920909035L;
    private static final int  PIPE_SIZE = 4096;
    
    private OutputToInputStreamConverter output;
    private OutputToInputStreamConverter error;
    
    private File curentDirectory;

    /**
     * @throws IOException 
     * 
     */
    public Shell() throws IOException {

	initialize();
    }

    /**
     * @param scope
     * @param prototype
     * @throws IOException 
     */
    public Shell(Scriptable scope, Scriptable prototype) throws IOException {

	super(scope, prototype);
	initialize();
    }
    
    /**
     * @param source
     */
    public void process(String source) {
	
	//TODO: intercept source string during execution (interpret string as stdin message)
	super.process(source);
    }
    
    /**
     * @return outputStream
     */
    public final InputStream getOutputStream() {
    
        return output.getInput();
    }
    
    /**
     * @return errorStream
     */
    public final InputStream getErrorStream() {
    
        return error.getInput();
    }
    
//    /**
//     * @param inputStream задаваемое inputStream
//     */
//    public final void setInputStream(InputStream inputStream) {
//    
//	//TODO: read all OS and write data to PipedInput stream
//        this.inputStream = inputStream;
//    }
    
    /**
     * @param workingDirectory
     * @throws FileNotFoundException
     */
    protected final void setCurentDir(String workingDirectory) 
    			throws FileNotFoundException {
    
        this.curentDirectory = new File(workingDirectory);
        if (!this.curentDirectory.isDirectory()) {
            
            throw new FileNotFoundException("Isn't a directory: " 
        	    				+ workingDirectory);
        }
    }

    /**
     * 
     */
    protected void finalize() throws Throwable {
	
	Context.exit();
	super.finalize();
    }
    
    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#cd(java.lang.Object[])
     */
    @Override
    final protected void cd(Object[] args) {

	//TODO: cd() is empty.
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#pwd(java.lang.Object[])
     */
    @Override
    final protected String pwd(Object[] args) {

	String curPath = curentDirectory.getAbsolutePath();
	stdout.println(curPath);
	return curPath;
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#print(java.lang.Object[])
     */
    @Override
    final protected void print(Object[] args) {

	for (int i=0; i < args.length; i++) {
	    
	    if (i > 0) {
		System.out.print(" ");
	    }

	    stdout.print(Context.toString(args[i]));
	};
	System.out.println();
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#help(java.lang.Object[])
     */
    @Override
    final protected void help(Object[] args) {

	// TODO help() is empty
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#read(java.lang.Object[])
     */
    @Override
    final protected Scriptable read(Object[] args) {

	return new Executor();
    }

    /**
     * @throws IOException 
     * 
     */
    private void initialize() throws IOException {
	
	output = new OutputToInputStreamConverter(PIPE_SIZE);
	error  = new OutputToInputStreamConverter(PIPE_SIZE);
	
	stdout = new PrintStream(output.getOutput());
	stderr = new PrintStream(error.getOutput());	
	
	this.defineFunctionProperties(getBuiltinFuncts(),
		JSGlobalObject.class, ScriptableObject.DONTENUM);
	
	//TODO: read and initialize environpent
    }
}
