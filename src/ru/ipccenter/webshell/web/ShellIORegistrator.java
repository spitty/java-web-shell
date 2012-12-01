package ru.ipccenter.webshell.web;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.sun.grizzly.websockets.WebSocketEngine;

/**
 * Servlet implementation class ShellIORegistrator
 */
@WebServlet("/ShellIORegistrator")
public class ShellIORegistrator extends HttpServlet {
	
    private static final long serialVersionUID = -7109156562388653637L;
    private ShellIO app;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShellIORegistrator() {
        super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

	app = new ShellIO(config.getServletContext());
	System.out.println(config.getServletContext().getContextPath());
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
}
