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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import ru.ipccenter.webshell.server.shell.io.WebPrintStream;

import com.sun.grizzly.websockets.DefaultWebSocket;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class Shell extends JSGlobalObject {

    private static final long serialVersionUID = -2348308525920909035L;
    
//    private OutputToInputStreamConverter output;
//    private OutputToInputStreamConverter error;
    
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
    
    
    public void setOutput(DefaultWebSocket socket) {
	
	stdout = new WebPrintStream(socket);
	stderr = new WebPrintStream(socket);	
    }
    
    /**
     * @param source
     */
    public void process(String source) {
	
	//TODO: intercept source string during execution (interpret string as stdin message)
	super.process(source);
    }
    
//    /**
//     * @return outputStream
//     */
//    public final InputStream getOutputStream() {
//    
//        return output.getInput();
//    }
    
//    /**
//     * @return errorStream
//     */
//    public final InputStream getErrorStream() {
//    
//        return error.getInput();
//    }
    
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

	if (args.length != 1) {
	    stderr.println("Wrong number of arguments");
	    return;
	}
	
	String arg;
	try {
	    arg = (String) args[0];
	    
	} catch (ClassCastException e) {
	    stderr.println("Wrong argument type");
	    return;
	}
	
	File newCurentDirectory;
	if (arg.startsWith("/") || arg.startsWith("\\")) {
	    newCurentDirectory = new File(arg);
	} else {
	    newCurentDirectory = new File(curentDirectory, arg);
	}
	
	if (newCurentDirectory.isDirectory()) {
	    curentDirectory = newCurentDirectory;
	} else {
	    stderr.print("\"");
	    stderr.print(arg);
	    stderr.print("\" ");
	    stderr.println("is not a directory");
	}
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#pwd(java.lang.Object[])
     */
    @Override
    final protected String pwd(Object[] args) {

	String curPath;
	try {
	    curPath = curentDirectory.getCanonicalPath();
	    /*
	     * because returned string will be printed to stderr
	     */
	    //stdout.println(curPath); 
	    return curPath;
    	} catch (IOException e) {
	    stderr.println(e.getMessage());
	}
	return "";
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#print(java.lang.Object[])
     */
    @Override
    final protected void print(Object[] args) {
	for (int i=0; i < args.length; i++) {
	    if (i > 0) {
		stdout.print(" ");
    	    }
	    stdout.print(Context.toString(args[i]));
	}
	stdout.println();
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSGlobalObject#help(java.lang.Object[])
     */
    @Override
    final protected void help(Object[] args) {

	stderr.println("+ help()");
	stderr.println("Display usage and help messages.");
//	stderr.println("load(['foo.js', ...])  Load JavaScript source files named by ");
//	stderr.println("                       string arguments. ");
	stderr.println("+ cd(path)");
	stderr.println("Changes working directory.");
	stderr.println("+ pwd()");
	stderr.println("Prints working directory, returns working directory path");
	stderr.println("+ print([expr ...])");
	stderr.println("Evaluate and print expressions.");
	stderr.println("+ read([path])");
	stderr.println("Reads from path and redirects readed data to new Executable object.");
	stderr.println("If no path, it reads data from standard input.");
//	stderr.println("quit()                 Quit the shell. ");
//	stderr.println("version([number])      Get or set the JavaScript version number.");
	stderr.println();
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
	
	this.defineFunctionProperties(getBuiltinFuncts(),
		JSGlobalObject.class, ScriptableObject.DONTENUM);

	//TODO: read and initialize environpent
	//TODO: change base working directory
	setCurentDir("/home/ivan");
    }
}
