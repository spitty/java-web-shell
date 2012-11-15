/**
 * 
 * AbstractShell.java
 *
 * 20:59:19
 *
 */
package ru.ipccenter.webshell.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

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
public abstract class AbstractShell extends ScriptableObject {

    private   static final long     serialVersionUID = 87936523123123L;
    protected static final String[] BUILTIN_FUNCTS = 
			{"cd", "print", "help", "pwd", "exec"};
    
    protected Context      context = Context.enter();
    protected File         workingDirectory;
    protected PrintStream  stdout;
    protected PrintStream  stderr;
    
    {
	context.initStandardObjects(this);
	this.defineFunctionProperties(BUILTIN_FUNCTS, AbstractShell.class,
		ScriptableObject.DONTENUM);
    }

    /**
     * 
     */
    public AbstractShell() {

	super();
	initialize();
    }

    /**
     * @param scope
     * @param prototype
     */
    public AbstractShell(Scriptable scope, Scriptable prototype) {

	super(scope, prototype);
	initialize();
    }

    
    /**
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    public abstract String getClassName();
    
    /**
     * 
     * @param source
     */
    public abstract void process(String source);
    
    /**
     * 
     * @param context
     * @param shell
     * @param arg
     * @param funct
     * @return
     */
    public static String cd(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	return cast(thiz).cd(args);
    }
    
    /**
     * 
     * @param context
     * @param shell
     * @param args
     * @param funct
     */
    public static void print(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	cast(thiz).print(args);
    }

    /**
     * 
     * @param context
     * @param thiz
     * @param args
     * @param funct
     */
    public static void help(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	cast(thiz).help(args);
    }
    
    /**
     * 
     * @param context
     * @param thiz
     * @param args
     * @param funct
     */
    public static void pwd(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	cast(thiz).pwd(args);
    }
    
    /**
     * 
     * @param context
     * @param thiz
     * @param args
     * @param funct
     * @return
     */
    public static ExecResult exec(Context context, Scriptable thiz,
		Object[] args, Function funct) {

	return cast(thiz).exec(args);
    }
    
    @Override
    protected void finalize() throws Throwable {
    
	Context.exit();
        super.finalize();
    }
    
    /**
     * @param workingDirectory задаваемое workingDirectory
     */
    protected final void setWorkingDirectory(String workingDirectory) 
    			throws FileNotFoundException {
    
        this.workingDirectory = new File(workingDirectory);
        if (!this.workingDirectory.isDirectory()) {
            
            throw new FileNotFoundException("Isn't a directory: " 
        	    				+ workingDirectory);
        }
    }

    
    /**
     * @param stdout задаваемое stdout
     */
    protected final void setStdout(OutputStream stdout) {
    
        this.stdout = new PrintStream(stdout);
    }

    
    /**
     * @param stderr задаваемое stderr
     */
    protected final void setStderr(OutputStream stderr) {
    
        this.stderr = new PrintStream(stderr);
    }

    
    /**
     * @return stdout
     */
    protected final PrintStream getStdout() {
    
        return stdout;
    }

    
    /**
     * @return stderr
     */
    protected final PrintStream getStderr() {
    
        return stderr;
    }
    
    /**
     * 
     * @param args
     */
    protected abstract String cd(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract void print(Object[] args);

    /**
     * 
     * @param args
     */
    protected abstract void help(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract void pwd(Object[] args);
    
    /**
     * 
     * @param args
     * @return
     */
    protected abstract ExecResult exec(Object[] args);
	
    /**
     * 
     */
    private void initialize() {
	
	
    }
    
    /**
     * 
     * @param thiz
     * @return
     */
    private static AbstractShell cast(Scriptable thiz) {
	
	return (AbstractShell)thiz;
    }
}
