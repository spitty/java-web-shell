/**
 * 
 * Executable.java
 *
 * 18:34:11
 *
 */
package ru.ipccenter.webshell.server.shell;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public interface Executable {
    
    /**
     * Executes the chain that was built with methods:
     * <ul>
     * <li>{@link #addExecutableLink(Object[])}</li>
     * <li>{@link #addInputLink(Object[])}</li>
     * <li>{@link #addOutputLink(Object[])}</li>
     * <li>{@link #addErrorLink(Object[])}</li>
     * </ul>
     * Chain is bad when first link isn't read link 
     * {@link #addInputLink(Object[])}, and last link isn't
     * write ({@link #addOutputLink(Object[])}) or write error
     * ({@link #addErrorLink(Object[])}) link.
     * 
     * @throws BadExecutableChainException when is bad or not
     * constructed @see {@link #construct()}.
     */
    void execute() throws BadExecutableChainException;
    
    /**
     * Adds link to the executable chain that executes one process.
     * 
     * @param args String array of parameters: first is a command,
     * second and etc. are command line arguments. Must contains one
     * or more elements.
     * 
     * @throws BadExecutableChainException when link before this isn't
     * executable link ({@link #addExecutableLink(String[])}) or
     * reading link ({@link #addInputLink(String[])}).
     */
    void addExecutableLink(String[] args) throws BadExecutableChainException;
    
    /**
     * Adds link to the executable chain that reads from file or input
     * stream @see {@link #getInputStream()}. 
     * 
     * @param path path to file for reading or {@code null} for reading
     * from input stream.
     * 
     * @throws BadExecutableChainException when link isn't first link
     * in the chain.
     * 
     * @throws FileNotFoundException when file is not found.
     */
    void addInputLink(String path) throws BadExecutableChainException,
    			FileNotFoundException;
    
    /**
     * Adds link to the executable chain that writes to file or output
     * stream
     * @see {@link #getOutputStream()}.
     * 
     * @param path path to file for writing or {@code null} for writing
     * to output stream.
     * 
     * @throws BadExecutableChainException when link before this isn't
     * executable link ({@link #addExecutableLink(String[])}) or 
     * error-writing link ({@link #addErrorLink(String[])}).
     * 
     * @throws FileNotFoundException when file is not found.
     */
    void addOutputLink(String path) throws BadExecutableChainException;
    
    /**
     * Adds link to the executable chain that writes errors to file or
     * error stream
     * @see {@link #getErrorStream()}.
     * 
     * @param path path to file for writing or {@code null} for writing
     * to error stream.
     * 
     * @throws BadExecutableChainException when link before this isn't
     * executable link ({@link #addExecutableLink(String[])}).
     * 
     * @throws FileNotFoundException when file is not found.
     */
    void addErrorLink(String[] path) throws BadExecutableChainException;
    
    /**
     * Gets the input stream of the chain.
     * 
     * @return input stream of the chain
     */
    OutputStream getInputStream();
    
    /**
    * Gets the output stream of the chain.
    * 
    * @return output stream of the chain
    */
    InputStream  getOutputStream();
    
    /**
     * Gets the error stream of the chain.
     * 
     * @return error stream of the chain
     */
    InputStream  getErrorStream();
}
