/**
 * 
 * Shell.java
 *
 * 16:40:54
 *
 */
package ru.ipccenter.webshell.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrappedException;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class Shell extends ScriptableObject implements Runnable {

    private static final long serialVersionUID = -5638074146250193112L;
    private static final String[] NAMES =
	{ "print", "quit", "version", "load", "help", "exec" };
    
    private volatile boolean quitting;
    
    private Context context = Context.enter();
    private String[] args   = new String[] {};
    
    private InputStream  input;
    private OutputStream output;
    
    private PrintStream out;
    
    public Shell(InputStream input, OutputStream output) {

	this.input  = input;
	this.output = output;
	
	 out = new PrintStream(output);
	
	context.initStandardObjects(this);
	defineFunctionProperties(NAMES, Shell.class,ScriptableObject.DONTENUM);
	
	Scriptable argsObj = context.newArray(this, args);
	defineProperty("arguments", argsObj, ScriptableObject.DONTENUM);
    }
    
    
    
    /**
     * @see org.mozilla.javascript.ScriptableObject#getClassName()
     */
    @Override
    public String getClassName()
    {
        return "global";
    }


    @Override
    public void run() {
	
	processSource(context, args.length == 0 ? null : args[0]);
    }
    
    
    /**
     * Parse arguments.
     */
    public String[] processOptions(Context cx, String args[]) {
        for (int i=0; i < args.length; i++) {
            String arg = args[i];
            if (!arg.startsWith("-")) {
                String[] result = new String[args.length - i];
                for (int j=i; j < args.length; j++)
                    result[j-i] = args[j];
                return result;
            }
            if (arg.equals("-version")) {
                if (++i == args.length)
                    usage(arg);
                double d = Context.toNumber(args[i]);
                if (d != d)
                    usage(arg);
                cx.setLanguageVersion((int) d);
                continue;
            }
            usage(arg);
        }
        return new String[0];
    }
    
    /**
     * Print a usage message.
     */
    private void usage(String s) {
        p("Didn't understand \"" + s + "\".");
        p("Valid arguments are:");
        p("-version 100|110|120|130|140|150|160|170");
        //System.exit(1);
    }
    
    /**
     * Print a help message.
     *
     * This method is defined as a JavaScript function.
     */
    public void help() {
        p("");
        p("Command                Description");
        p("=======                ===========");
        p("help()                 Display usage and help messages. ");
        p("defineClass(className) Define an extension using the Java class");
        p("                       named with the string argument. ");
        p("                       Uses ScriptableObject.defineClass(). ");
        p("load(['foo.js', ...])  Load JavaScript source files named by ");
        p("                       string arguments. ");
        p("loadClass(className)   Load a class named by a string argument.");
        p("                       The class must be a script compiled to a");
        p("                       class file. ");
        p("print([expr ...])      Evaluate and print expressions. ");
        p("quit()                 Quit the shell. ");
        p("version([number])      Get or set the JavaScript version number.");
        p("");
    }
    
    /**
     * Print the string values of its arguments.
     *
     * This method is defined as a JavaScript function.
     * Note that its arguments are of the "varargs" form, which
     * allows it to handle an arbitrary number of arguments
     * supplied to the JavaScript function.
     *
     */
    public void print(Context cx, Scriptable thisObj,
                             Object[] args, Function funObj)
    {
        for (int i=0; i < args.length; i++) {
	    if (i > 0)
		out.println(" ");

	    // Convert the arbitrary JavaScript value into a string form.
            String s = Context.toString(args[i]);

            out.print(s);
        }
        out.println();
    }
    
    
    /**
     * Quit the shell.
     *
     * This only affects the interactive mode.
     *
     * This method is defined as a JavaScript function.
     */
    public void quit()
    {
        quitting = true;
    }
    
    
    /**
     * Get and set the language version.
     *
     * This method is defined as a JavaScript function.
     */
    public static double version(Context cx, Scriptable thisObj,
                                 Object[] args, Function funObj)
    {
        double result = cx.getLanguageVersion();
        if (args.length > 0) {
            double d = Context.toNumber(args[0]);
            cx.setLanguageVersion((int) d);
        }
        return result;
    }
    
    
    /**
     * Load and execute a set of JavaScript source files.
     *
     * This method is defined as a JavaScript function.
     *
     */
    public static void load(Context cx, Scriptable thisObj,
                            Object[] args, Function funObj)
    {
        Shell shell = (Shell)getTopLevelScope(thisObj);
        for (int i = 0; i < args.length; i++) {
            shell.processSource(cx, Context.toString(args[i]));
        }
    }
    
    /**
     * Evaluate JavaScript source.
     *
     * @param cx the current context
     * @param filename the name of the file to compile, or null
     *                 for interactive mode.
     */
    private void processSource(Context cx, String filename)
    {
        if (filename == null) {
            BufferedReader in = new BufferedReader
                (new InputStreamReader(input));
            String sourceName = "<stdin>";
            int lineno = 1;
            boolean hitEOF = false;
            do {
                int startline = lineno;
                //err.print("js> ");
                //System.err.flush();
                try {
                    String source = "";
                    // Collect lines of source to compile.
                    while(true) {
                        String newline;
                        newline = in.readLine();
                        if (newline == null) {
                            hitEOF = true;
                            break;
                        }
                        source = source + newline + "\n";
                        lineno++;
                        // Continue collecting as long as more lines
                        // are needed to complete the current
                        // statement.  stringIsCompilableUnit is also
                        // true if the source statement will result in
                        // any error other than one that might be
                        // resolved by appending more source.
                        if (cx.stringIsCompilableUnit(source))
                            break;
                    }
                    //System.out.println(source);
                    Object result = cx.evaluateString(this, source,
                                                      sourceName, startline,
                                                      null);
                    if (result != Context.getUndefinedValue()) {
                        out.println(Context.toString(result));
                    }
                }
                catch (WrappedException we) {
                    // Some form of exception was caught by JavaScript and
                    // propagated up.
                    out.println(we.getWrappedException().toString());
                    we.printStackTrace();
                }
                catch (EvaluatorException ee) {
                    // Some form of JavaScript error.
                    out.println("js: " + ee.getMessage());
                }
                catch (JavaScriptException jse) {
                    // Some form of JavaScript error.
                    out.println("js: " + jse.getMessage());
                }
                catch (IOException ioe) {
                    out.println(ioe.toString());
                }
                if (quitting) {
                    // The user executed the quit() function.
                    break;
                }
            } while (!hitEOF);
            out.println();
        } else {
            FileReader in = null;
            try {
                in = new FileReader(filename);
            }
            catch (FileNotFoundException ex) {
                Context.reportError("Couldn't open file \"" + filename + "\".");
                return;
            }

            try {
                // Here we evalute the entire contents of the file as
                // a script. Text is printed only if the print() function
                // is called.
                cx.evaluateReader(this, in, filename, 1, null);
            }
            catch (WrappedException we) {
                System.err.println(we.getWrappedException().toString());
                we.printStackTrace();
            }
            catch (EvaluatorException ee) {
                System.err.println("js: " + ee.getMessage());
            }
            catch (JavaScriptException jse) {
                System.err.println("js: " + jse.getMessage());
            }
            catch (IOException ioe) {
                System.err.println(ioe.toString());
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException ioe) {
                    System.err.println(ioe.toString());
                }
            }
        }
    }
    
    private void p(String s) {
	   
	out.println(s);
    }
    
}
