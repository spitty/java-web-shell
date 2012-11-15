/**
 * 
 * ExecResult.java
 *
 * 22:00:36
 *
 */
package ru.ipccenter.webshell.server;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class ExecResult extends ScriptableObject {

    /**
     * 
     */
    private static final long serialVersionUID = 989234234L;

    /**
     * 
     */
    public ExecResult() {

	// TODO Автоматически созданная заглушка конструктора
    }

    /**
     * @param scope
     * @param prototype
     */
    public ExecResult(Scriptable scope, Scriptable prototype) {

	super(scope, prototype);
	// TODO Автоматически созданная заглушка конструктора
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName() {

	// TODO Автоматически созданная заглушка метода
	return null;
    }

}
