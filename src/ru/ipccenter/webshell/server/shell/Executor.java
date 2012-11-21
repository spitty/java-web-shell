/**
 * 
 * Executor.java
 *
 * 20:04:09
 *
 */
package ru.ipccenter.webshell.server.shell;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class Executor extends ScriptableObject implements Executable {

    private static final long serialVersionUID = -2770973137034054643L;
    private static final String[] BUILTIN_FUNCTS =  {"exec", "write"};
    private Context context;
    
    
    /**
     * 
     */
    public Executor() {

	this.context = Context.enter();
	initialize();
    }

    
    /**
     * 
     */
    public Executor(Context cx) {

	this.context = cx;
	initialize();
    }
    
    /**
     * @param scope
     * @param prototype
     */
    public Executor(Scriptable scope, Scriptable prototype, Context cx) {

	super(scope, prototype);
	this.context = cx;
	initialize();
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#execute()
     */
    @Override
    public void execute() throws BadExecutableChainException {

	//TODO: execute() is empty
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#addExecutableLink(java.lang.String[])
     */
    @Override
    public void addExecutableLink(String[] args)
	    throws BadExecutableChainException {

	// TODO Автоматически созданная заглушка метода

    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#addInputLink(java.lang.String)
     */
    @Override
    public void addInputLink(String path) throws BadExecutableChainException,
	    FileNotFoundException {

	// TODO Автоматически созданная заглушка метода

    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#addOutputLink(java.lang.String)
     */
    @Override
    public void addOutputLink(String path) throws BadExecutableChainException {

	// TODO Автоматически созданная заглушка метода

    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#addErrorLink(java.lang.String[])
     */
    @Override
    public void addErrorLink(String[] path) throws BadExecutableChainException {

	// TODO Автоматически созданная заглушка метода

    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#getInputStream()
     */
    @Override
    public OutputStream getInputStream() {

	// TODO Автоматически созданная заглушка метода
	return null;
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#getOutputStream()
     */
    @Override
    public InputStream getOutputStream() {

	// TODO Автоматически созданная заглушка метода
	return null;
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.Executable#getErrorStream()
     */
    @Override
    public InputStream getErrorStream() {

	// TODO Автоматически созданная заглушка метода
	return null;
    }

    @Override
    public String getClassName() {

	return "Executor";
    }
    
    public String[] getBuiltinFuncts() {

	return BUILTIN_FUNCTS;
    }
    
    /**
     * 
     * @param context
     * @param shell
     * @param arg
     * @param funct
     * @return
     */
    public static Scriptable exec(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	return cast(thiz).exec(args);
    }
    
    /**
     * 
     * @param context
     * @param shell
     * @param arg
     * @param funct
     * @return
     */
    public static Scriptable write(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	return cast(thiz).write(args);
    }
    
    /**
     * 
     * @param args
     * @return
     */
    protected Scriptable exec(Object[] args) {
	
	//TODO: exec() is empty
	return this;
    }
    
    protected Scriptable write(Object[] args) {
	
	//TODO: write() is empty
	return this;
    }
    
    /**
     * 
     */
    private void initialize() {

	context.initStandardObjects(this);
	this.defineFunctionProperties(getBuiltinFuncts(),
		Executor.class, ScriptableObject.DONTENUM);
    }
    
    /**
     * 
     * @param thiz
     * @return
     */
    private static Executor cast(Scriptable thiz) {
	
	return (Executor)thiz;
    }

}
