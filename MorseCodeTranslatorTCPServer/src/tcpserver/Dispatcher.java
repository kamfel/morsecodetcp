/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles dispatching client commands to appropriate handlers
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class Dispatcher {
    
    /**
     * Command to handler mappings
     */
    private final Map<String, Method> commandMappings;
    
    /**
     * The default handler invoked when a mapping doesn't exist
     */
    private Method defaultHandler;
    
    /**
     * Object that contains methods that handle commands
     */
    private final InputHandler handlerObject;
    
    /**
     * Constructs dispatcher, maps command to handlers
     * 
     * @param handler Object that contains handlers
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    protected Dispatcher(InputHandler handler){
        commandMappings = new HashMap<>();
        
        handlerObject = handler;
        
        Class<?> clazz = handler.getClass();
        
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Handler.class)) {
                String commandName = method.getAnnotation(Handler.class).Command();
                
                if (commandName.toLowerCase().equals("default")){
                    defaultHandler = method;               
                    continue;
                }
                
                commandMappings.put(commandName, method);
            }
        }
        
        if (commandMappings.isEmpty()) {
            throw new Error("No handlers found in " + clazz.getName());
        }
        
        if (defaultHandler == null) {
            throw new Error("No default handler provided");
        }
    }
    
    /**
     * Dispatches given command and param to appropriate handler
     * 
     * @param command request command
     * @param param request param
     * @return response from handler
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public TCPResponse processRequest(String command, String param) {
        if (command == null){
            return TCPResponse.fail("Invalid command.");
        }
        
        Method m = commandMappings.getOrDefault(command, defaultHandler);
        
        Logger.getLogger("Dispatcher").log(Level.INFO, "Redirecting to handler: {0} at {1}", new Object[]{command, m.toString()});
        
        TCPResponse response;
        try {
            
            response = (TCPResponse) m.invoke(handlerObject, param);
            
        } catch(Exception ex) {
            //Handlers may throw any exception which is caught and logged
            Logger.getLogger("Dispatcher").log(Level.WARNING, "Error processing request");
            Logger.getLogger("Dispatcher").log(Level.WARNING, ex.getMessage());
            response = TCPResponse.fail("Server error occurred");
        }
        
        return response;
    }
}
