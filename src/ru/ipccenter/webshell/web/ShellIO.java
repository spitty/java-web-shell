/**
 * 
 * ShellIO.java
 *
 * 11:43:36
 *
 */
package ru.ipccenter.webshell.web;

import javax.servlet.ServletContext;

import ru.ipccenter.webshell.server.ShellIOSocket;

import com.sun.grizzly.tcp.Request;
import com.sun.grizzly.websockets.DataFrame;
import com.sun.grizzly.websockets.ProtocolHandler;
import com.sun.grizzly.websockets.WebSocket;
import com.sun.grizzly.websockets.WebSocketApplication;
import com.sun.grizzly.websockets.WebSocketListener;


/**
 * @author Ivan Penkin
 * <a href="mailto:grek.penkin@gmail.com">grek.penkin@gmail.com</a>
 * 
 *
 */
public class ShellIO extends WebSocketApplication {

    private ServletContext servletContext;
    
    
    public ShellIO(ServletContext context) {

	servletContext = context;
    }
    
    
    
    /**
     * 
     * @param protocolHandler
     * @param listeners
     * @return
     */
    @Override
    public WebSocket createWebSocket(ProtocolHandler protocolHandler,
            WebSocketListener... listeners) {
		
	return new ShellIOSocket(servletContext,
			protocolHandler, listeners);
    }
    
    /**
     * 
     * @param request
     * @return
     */
    @Override
    public boolean isApplicationRequest(Request request) {

	return true;
    }
    
    @Override
    public void onConnect(WebSocket socket) {
//	System.out.println("connected");
    }
    
    @Override
    public void onMessage(WebSocket socket, String text) {
	((ShellIOSocket)socket).getShell().process(text);
    }
    
    @Override
    public void onClose(WebSocket socket, DataFrame frame) {
//	System.out.println("closed");s
    }
}
