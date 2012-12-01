package ru.ipccenter.webshell.server;

import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class ShellSessionListener
 *
 */
@WebListener
public final class ShellSessionListener implements HttpSessionListener {

    
    /**
     * Default constructor. Does nothing.
     */
    public ShellSessionListener() {
    }

    /**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent hse) {
	
	HttpSession session = hse.getSession();
	Map<String, SingleThreadableShellSandbox> shells =
		ShellContextListener.getShellsMap(session);
	
	SingleThreadableShellSandbox sandbox = 
		new SingleThreadableShellSandbox();
	
	new Thread(sandbox).start();
	shells.put(session.getId(), sandbox);
    }

    /**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent hse) {
        
	HttpSession session = hse.getSession();
	Map<String, SingleThreadableShellSandbox> shells =
		ShellContextListener.getShellsMap(session);
	shells.get(session.getId()).exit();
	shells.remove(session.getId());
    }
}
