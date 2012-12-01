package ru.ipccenter.webshell.server;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.sun.grizzly.websockets.DefaultWebSocket;
import com.sun.grizzly.websockets.ProtocolHandler;
import com.sun.grizzly.websockets.WebSocketListener;


public class ShellIOSocket extends DefaultWebSocket {

    private SingleThreadableShellSandbox shell;
    
    public ShellIOSocket(ServletContext context, ProtocolHandler ph,
	    WebSocketListener... wsl) {

	super(ph, wsl);
	
	Map<String, SingleThreadableShellSandbox> shells = 
		ShellContextListener.getShellsMap(context);
	
	String sessionId = getSessionId(getRequest(), shells);
	if (sessionId != null) {
	    shell = shells.get(sessionId);
	    shell.setShellOutput(this);
	} else {
	    throw new RuntimeException("No session Id in the global Map");
	}
    }
    
    public SingleThreadableShellSandbox getShell() {
	
	return shell;
    }

    private static String getSessionId(HttpServletRequest request,
	    Map<String, SingleThreadableShellSandbox> shells) {
	
	String id = null;
	
	for (Cookie c: request.getCookies()) {

	    //TODO: check the string constant
	    if (c.getName().equals("JSESSIONID") 
		    && shells.containsKey(c.getValue())) {
		id = c.getValue();
		break;
	    }
	}
	
	return id;
    }
}
