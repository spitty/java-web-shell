/**
 * 
 * JSShell.java
 *
 * 19:14:38
 *
 */
package ru.ipccenter.webshell.server.shell;

import java.util.regex.Pattern;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrappedException;

import ru.ipccenter.webshell.server.shell.io.WebPrintStream;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
abstract class JSShell extends ScriptableObject {

    private static final long serialVersionUID = 6610524644191646475L;
    private static final Pattern SPLIT_PATTERN = 
		Pattern.compile("[\\r\\n]|\\n|\\r");
    
    protected Context context;
    protected WebPrintStream  stdout;
    protected WebPrintStream  stderr;
    
    /**
     * 
     */
    public JSShell() {

	context = Context.enter();
	context.initStandardObjects(this);
    }

    /**
     * @param scope
     * @param prototype
     */
    public JSShell(Scriptable scope, Scriptable prototype) {

	super(scope, prototype);
	context = Context.enter();
	context.initStandardObjects(this);
    }

    /**
     * 
     * @param source
     */
    public void process(String source) {
	
	String[] sourceLines = SPLIT_PATTERN.split(source, 0);
	process(sourceLines);
    }
    
    /**
     * 
     * @param source
     */ 
    public void process(String[] sourceLines) {
	
	StringBuilder code = new StringBuilder(sourceLines.length * 128);
	try {
	    for (String line: sourceLines) {
		code.append(line);
		if (context.stringIsCompilableUnit(code.toString())) {
		    break;
		}
	    }
	
	    Object result = context.evaluateString(this, code.toString(),
		    "web-stdin: ", 0, null);  // WTF?
	
	    if (result != Context.getUndefinedValue()) {
		stderr.println(Context.toString(result));
	    };
	} catch (EcmaError ee) {
	    stderr.println(ee.getErrorMessage());
	} catch (WrappedException we) {
	    stderr.println(we.getWrappedException().toString());
	} catch (EvaluatorException ee) {
            stderr.println("js: " + ee.getMessage());
        }
        catch (JavaScriptException jse) {
            stderr.println("js: " + jse.getMessage());
        }
    }
    
    /**
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public abstract String getClassName();
    
    /**
     * 
     * @return
     */
    public abstract String[] getBuiltinFuncts();
    
    /**
     * 
     */
    protected void finalize() throws Throwable {
	
	super.finalize();
    }

}
