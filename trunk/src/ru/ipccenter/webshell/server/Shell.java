/**
 * 
 * Shell.java
 *
 * 21:10:27
 *
 */
package ru.ipccenter.webshell.server;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class Shell extends AbstractShell {

    /**
     * 
     */
    private static final long serialVersionUID = 234235124135123L;

    /**
     * 
     */
    public Shell() {

	initialize();
    }

    /**
     * @param scope
     * @param prototype
     */
    public Shell(Scriptable scope, Scriptable prototype) {

	super(scope, prototype);
	initialize();
    }
    
    /**
     * @see ru.ipccenter.webshell.server.AbstractShell#getClassName()
     */
    @Override
    public String getClassName() {

	return "JSWebShell";
    }
    
    /**
     * 
     */
    @Override
    public void process(String source) {

	// TODO Автоматически созданная заглушка метода
    }
    
    /**
     *  @see ru.ipccenter.webshell.server.AbstractShell#cd(Object[])
     */
    @Override
    protected String cd(Object[] args) {

	//TODO: cd() - empty method;
	return "";
    }
    
    
    /**
     * @see ru.ipccenter.webshell.server.AbstractShell#print(Object[])
     */
    @Override
    protected void print(Object[] args) {

	//TODO: print() - is not full (for exec result, files, etc.)
	for (int i=0; i < args.length; i++) {
	    if (i > 0 )
		stdout.print(" ");
	           
	    Context.toString(args[i]);
	}
	stdout.println();
    }
    
    /**
     * @see ru.ipccenter.webshell.server.AbstractShell#help(Object[])
     */
    @Override
    protected void help(Object[] args) {

	//TODO: help() - empty method;
    }
    
    /**
     * @see ru.ipccenter.webshell.server.AbstractShell#pwd(Object[])
     */
    @Override
    protected void pwd(Object[] args) {

	//TODO: pwd() - empty method;
    }
    
    /**
     * @see ru.ipccenter.webshell.server.AbstractShell#exec(Object[])
     */
    @Override
    protected ExecResult exec(Object[] args) {

	//TODO: exec() - is empty
	return null;
    }
    
    private void initialize() {
	
	//TODO: define ENV variables
    }
}
