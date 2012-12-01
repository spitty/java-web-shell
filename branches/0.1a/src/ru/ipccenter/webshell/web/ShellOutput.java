/**
 * 
 * ShellOutput.java
 *
 * 11:43:36
 *
 */
package ru.ipccenter.webshell.web;

import javax.servlet.ServletContext;

import ru.ipccenter.webshell.server.ShellOutputSocket;

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
public class ShellOutput extends WebSocketApplication {

    private ServletContext servletContext;
    
    
    public ShellOutput(ServletContext context) {

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
		
	return new ShellOutputSocket(servletContext,
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
	((ShellOutputSocket)socket).getShell().process(text);
    }
    
    @Override
    public void onClose(WebSocket socket, DataFrame frame) {
//	System.out.println("closed");s
    }
}
