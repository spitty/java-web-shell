package ru.ipccenter.webshell.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

/**
 * Application Lifecycle Listener implementation class ShellContextListener
 *
 */
@WebListener
public final class ShellContextListener implements ServletContextListener {

    public final static String SHELLS_ATTRIBUTE_NAME = "ShelsByIds";
    
    /**
     * Default constructor. 
     */
    public ShellContextListener() {
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
	
	ServletContext context = sce.getServletContext();
	HashMap<String, ShellOutputSocket> shells =
		new HashMap <String, ShellOutputSocket>(); 
	context.setAttribute(SHELLS_ATTRIBUTE_NAME,
		Collections.synchronizedMap(shells));
	
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
    /**
     * 
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, SingleThreadableShellSandbox> 
    					getShellsMap(ServletContext c) {
	
	return (Map<String, SingleThreadableShellSandbox>)c
		.getAttribute(ShellContextListener.SHELLS_ATTRIBUTE_NAME);
    }
    
    /**
     * 
     * @param s
     * @return
     */
    public static Map<String, SingleThreadableShellSandbox> 
    					getShellsMap(HttpSession s) {
	
	ServletContext context = s.getServletContext();
	return getShellsMap(context);
    }
	
}
