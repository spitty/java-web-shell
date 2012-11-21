package conf;






/**
@startum

package ru.ipccenter.webshell.server.shell {
	
class BadExecutableChainException extends java.lang.RuntimeException {
	private static final long serialVersionUID;
	...
	public BadExecutableChainException();
	public BadExecutableChainException(String message);
	public BadExecutableChainException(Throwable cause);
	public BadExecutableChainException(String message, Throwable cause);
}

interface Executable {

    void execute() throws BadExecutableChainException;
    void addExecutableLink(String[] args) throws BadExecutableChainException;
    void addInputLink(String path) throws BadExecutableChainException,FileNotFoundException;
    void addOutputLink(String path) throws BadExecutableChainException;
    void addErrorLink(String[] path) throws BadExecutableChainException;
    OutputStream getInputStream();
    InputStream getOutputStream();
    InputStream getErrorStream();
}
 
class JSShell extends org.mozilla.javascript.ScriptableObject {
     protected Context context;
     ...
     private static final long serialVersionUID;
     ---
     public JSShell();
     public JSShell(Scriptable scope, Scriptable prototype);
     public void process(String source) throws ...;
     public abstract String[] getBuiltinFuncts();
     public abstract String   getClassName();
     ...     
     protected void finalize() throws Throwable;
}

class JSGlobalObject extends JSShell {
   
     private static final long serialVersionUID;
     private static final String[] BUILTIN_FUNCTS;
     ---
     public JSGlobalObject();
     public JSGlobalObject(Scriptable scope, Scriptable prototype);
     public void process(String source) throws ...;
     public String[] getBuiltinFuncts();
     public String   getClassName();
     ...
     public static void cd(Context context, Scriptable thiz, Object[] args, Function funct);
     public static void pwd(Context context, Scriptable thiz, Object[] args, Function funct);
     public static void ...
     ...    
     protected void finalize() throws Throwable;
     ...
     protected abstract void cd(Object[] args);
     protected abstract void pwd(Object[] args);
     protected abstract void ...
     
}

class Shell extends JSGlobalObject  {
    private static final long serialVersionUID;
    private InputStream  outputStream;
    private InputStream  errorStream;
    private OutputStream inputStream;
    private File curentDirectory;
    private Executable executor;
    ---
    public Shell();
    public Shell(Scriptable scope, Scriptable prototype);
    public void process(String source) throws ...; !ENTRY POINT!
    ...    
    protected void finalize() throws Throwable;
    ...   
    protected void cd(Object[] args);
    protected void pwd(Object[] args);
    protected void ...
}

class Executor implements Executable {
	???
	---
	???
}



Shell "1" *-- "1" Executor
Executor "1" *-- "1" ru.ipccenter.webshell.server.pipeline.PipeLine
BadExecutableChainException <-- Executable
BadExecutableChainException <-- Executor


}

package ru.ipccenter.webshell.server.pipeline {

class Pipe implements java.lang.Runnable {
    	private AtomicReference<Exception> exception;
    	private InputStream input;
    	private OutputStream output;
    	
    	public Pipe(InputStream input, OutputStream output);
    	public AtomicReference<Exception> getException();
    	public void run();
}

class MultiProcessErrorInputStream extends java.io.InputStream {
	private InputStream[] streams;
    	private int curentStreamIndex;
    	
    	public MultiProcessErrorInputStream(Process... processes);
    	public int read() throws IOException;
    	
}

class PipeLine {
    private Process[] processes;
    private InputStream stdout;
    private MultiProcessErrorInputStream  stderr;
    private OutputStream stdin;
    
    public PipeLine(Process... processes);
    public InputStream getStdout();
    public InputStream getStderr();
    public OutputStream getStdin();
    public void exec() throws Exception;   
}

PipeLine "1" <-- "*" Pipe
PipeLine "1" *-- "1" MultiProcessErrorInputStream

}


package org.mozilla.javascript {

class ScriptableObject {
...
}

}



package java.lang {

class RuntimeException {
...
}

interface Runnable {
}

}

package java.io {
class FIleNotFoundException {
...
}

class InputStream {
}

}
 @enduml
 */