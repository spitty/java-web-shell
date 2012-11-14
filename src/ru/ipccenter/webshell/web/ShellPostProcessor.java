package ru.ipccenter.webshell.web;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.ipccenter.webshell.server.Shell;

/**
 * Servlet implementation class ShellPostProcessor
 */
@WebServlet("/ShellPostProcessor")
public class ShellPostProcessor extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private Shell shell;

    PipedInputStream servletInput;
    PipedOutputStream shellOutput;
    

    PipedInputStream shellInput;
    PipedOutputStream servletOutput;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShellPostProcessor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	    
	    try {
		servletInput = new PipedInputStream();
		shellOutput  = new PipedOutputStream(servletInput);
		
		shellInput    = new PipedInputStream();
		servletOutput = new PipedOutputStream(shellInput);
	    } catch (IOException e) {
		/* skip */
	    }
	    
	    shell = new Shell(shellInput, shellOutput);
	    new Thread(shell).start();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
