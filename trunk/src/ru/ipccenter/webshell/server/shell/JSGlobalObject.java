/**
 * 
 * JSGlobalObject.java
 *
 * 19:20:32
 *
 */
package ru.ipccenter.webshell.server.shell;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
abstract class JSGlobalObject extends JSShell {

    private static final long serialVersionUID = -3380083016944253143L;
    private static final String[] BUILTIN_FUNCTS = 
	{"cd", "pwd", "print", "flush", "println", "help", "read"};

    /**
     * 
     */
    public JSGlobalObject() {

    }

    /**
     * @param scope
     * @param prototype
     */
    public JSGlobalObject(Scriptable scope, Scriptable prototype) {

	super(scope, prototype);
    }
    
    /**
     * 
     * @param source
     */
    public void process(String source) {
	
	super.process(source);
    }
    
    /**
     * 
     * @see ru.ipccenter.webshell.server.shell.JSShell#getClassName()
     */
    @Override
    public String getClassName() {

	return "JSGlobalObject";
    }

    /**
     * @see ru.ipccenter.webshell.server.shell.JSShell#getBuiltinFuncts()
     */
    @Override
    public String[] getBuiltinFuncts() {

	return BUILTIN_FUNCTS;
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param arg
     * @param funct
     * @return
     */
    public static void cd(Context context, Scriptable obj,
		Object[] args, Function funct) {

	
	cast(obj).cd(args);
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param args
     * @param funct
     */
    public static String pwd(Context context, Scriptable obj,
		Object[] args, Function funct) {

	return cast(obj).pwd(args);
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param args
     * @param funct
     */
    public static void print(Context context, Scriptable obj,
		Object[] args, Function funct) {

	cast(obj).print(args);
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param args
     * @param funct
     */
    public static void flush(Context context, Scriptable obj,
		Object[] args, Function funct) {

	cast(obj).flush(args);
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param args
     * @param funct
     */
    public static void println(Context context, Scriptable obj,
		Object[] args, Function funct) {

	cast(obj).println(args);
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param args
     * @param funct
     */
    public static void help(Context context, Scriptable obj,
		Object[] args, Function funct) {

	cast(obj).help(args);
    }
    
    /**
     * 
     * @param context
     * @param obj
     * @param args
     * @param funct
     * @return 
     */
    public static Scriptable read(Context context, Scriptable obj,
		Object[] args, Function funct) {

	return cast(obj).read(args);
    }
    
    /**
     * 
     */
    @Override
    protected void finalize() throws Throwable {
    
        super.finalize();
    }
    
    /**
     * 
     * @param args
     */
    protected abstract void cd(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract String pwd(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract void print(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract void flush(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract void println(Object[] args);
    
    /**
     * 
     * @param args
     */
    protected abstract void help(Object[] args);
    
    /**
     * 
     * @param args
     * @return
     */
    protected abstract Scriptable read(Object[] args);

    /**
     * 
     * @param thiz
     * @return
     */
    private static JSGlobalObject cast(Scriptable thiz) {
	
	return (JSGlobalObject)thiz;
    }
}
