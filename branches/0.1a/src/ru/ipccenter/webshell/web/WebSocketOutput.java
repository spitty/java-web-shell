package ru.ipccenter.webshell.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.grizzly.websockets.WebSocketEngine;

/**
 * Servlet implementation class WebSocketOutput
 */
@WebServlet("/WebSocketOutput")
public class WebSocketOutput extends HttpServlet {
	
    private static final long serialVersionUID = -7109156562388653637L;
    private ShellOutput app;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebSocketOutput() {
        super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

	app = new ShellOutput();
	WebSocketEngine.getEngine().register(app);
    }
    
    /**
     * @see Servlet#destroy() 
     */
    @Override
    public void destroy() {
	
	if (app != null) {
	    WebSocketEngine.getEngine().unregister(app);
	}
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	response.getWriter().println("hello");
	response.getWriter().close();
    }
}
