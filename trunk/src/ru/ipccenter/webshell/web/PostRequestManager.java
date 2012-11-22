package ru.ipccenter.webshell.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.ipccenter.webshell.server.SingleThreadableShellSandbox;

/**
 * Servlet implementation class PostRequestManager
 */
@WebServlet("/PostRequestManager")
public class PostRequestManager extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostRequestManager() {

	super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession(false);
	
	SingleThreadableShellSandbox sandbox = 
		(SingleThreadableShellSandbox)session
		.getAttribute("shellSandbox");
	
	String source = request.getParameter("query");
	sandbox.process(source);
	sandbox.waitForEndProcessing();

	byte[] output = new byte[sandbox.getOutputStream().available()];
	byte[] error  = new byte[sandbox.getErrorStream().available()];
	
	sandbox.getOutputStream().read(output);
	sandbox.getErrorStream().read(error);
	
	ServletOutputStream out = response.getOutputStream();
	out.write(output);
	out.write(error);
	out.close();
	
	output = null;
	error  = null;
	out    = null;
    } 
}
