/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple tcp server
 * 
 * @author Kamil Felkel
 */
public class TCPServer implements Closeable {
    
    /**
     * Port which the server listens on
     */
    private int port;
    
    /**
     * Object responsible for dispatching request to appropriate handlers
     */
    private Dispatcher dispatcher;
    
    /**
     * A sockets that listens for client connections
     */
    private ServerSocket serverSocket;

    /**
     * Listens for client connections
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public void run(){        
        if (dispatcher == null) {
            throw new Error("TCPServer needs a defined input handler");
        }
        
        try {
            this.serverSocket = new ServerSocket(port);
        
            Logger.getLogger("TCPServer").log(Level.INFO, "Begining listening on port {0}", port);
            
            while (true) {
                
                Socket socket = serverSocket.accept();
                
                Logger.getLogger("TCPServer").log(Level.INFO, "Accepted connection from {0}", socket.getInetAddress().toString());
                
                new RequestProcessor(socket.getOutputStream(), socket.getInputStream(), dispatcher).process();
                
                socket.close();
            }
        } catch (IOException e) {
            Logger.getLogger("TCPServer").log(Level.SEVERE, "Critical server error");
            Logger.getLogger("TCPServer").log(Level.SEVERE, e.getMessage());
        }
    }
    
    /**
     * Creates server and initialises it using data from given properties file
     * 
     * @param propertiesPath path to properties file
     * 
     * @return Instance created using given properties
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public static TCPServer createFromProperties(String propertiesPath){
        try (InputStream stream = TCPServer.class.getClass().getResourceAsStream(propertiesPath)) {
            
            if (stream == null) {
                Logger.getLogger("TCPServer").log(Level.SEVERE, "No properties at path {0}", propertiesPath);
                throw new Error("Failed to load properties");
            }
            
            Properties properties = new Properties();
            properties.load(stream);
            
            int port = Integer.parseInt((String)properties.get("PORT"));

            if (port <=0 || port > 65535){
                throw new Error("Invalid port value in properties. Was: " + port);
            }
            
            TCPServer server = new TCPServer();
            server.port = port;
            
            return server;
            
        } catch (IOException ex) {
            Logger.getLogger("TCPServer").log(Level.SEVERE, null, ex);
            throw new Error("Loading properties failed");
        }
    }
    
    /**
     * Specifies what object conatins command handlers
     * 
     * @param handler object that contains appropriate handlers
     * @return this
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public TCPServer useInputHandler(InputHandler handler) {
        this.dispatcher = new Dispatcher(handler);
        return this;
    }

    @Override
    public void close() throws IOException {
        if (serverSocket != null)
            serverSocket.close();
    }
}
