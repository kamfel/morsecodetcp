/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkservices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service that handles connection to the tcp server
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class MorseCodeService implements AutoCloseable {
    
    /**
     * The socket
     */
    private final Socket socket;
    
    /**
     * Server ip address
     */
    private InetSocketAddress serverAddress;
    
    /**
     * Output stream extracted from socket
     */
    private PrintWriter output;
    
    /**
     * Input stream extracted from socket
     */
    private BufferedReader input;
    
    /**
     * Constructor
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public MorseCodeService() {
        socket = new Socket();
    }
    
    /**
     * Loads server adress and port from properties
     * 
     * @param propertiesPath path to properties file
     * @return this
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public MorseCodeService loadServerAdressFromProperties(String propertiesPath){
        try (InputStream stream = getClass().getResourceAsStream(propertiesPath)) {
            
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
            
            String address = (String) properties.get("ADDRESS");
            
            String host = (String) properties.get("HOST");
            
            if (address != null ){
                serverAddress = new InetSocketAddress(InetAddress.getByAddress(address.getBytes()), port);
            }
            else if (host != null) {
                serverAddress = new InetSocketAddress(InetAddress.getByName(host) , port);
            }
            else {
                throw new Error("No host or address specified in properties");
            }
            
            if (!serverAddress.getAddress().isReachable(10)) {
                Logger.getLogger("NetworkService").log(Level.WARNING, "Address is unreachable");
            }
            
        } catch (IOException ex) {
            Logger.getLogger("NetworkService").log(Level.SEVERE, null, ex);
            throw new Error("Loading properties failed");
        }
        
        return this;
    }
    
    /**
     * Tries to connect to server. On failure throws
     *
     * @throws IOException on failure to connect
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public void connect() throws IOException {
        
        if (socket.isConnected())
            return;
        
        socket.connect(serverAddress);
        
        this.output = new PrintWriter(
                        new BufferedWriter(
                          new OutputStreamWriter(socket.getOutputStream())), true);
        
        this.input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
    }
    
    /**
     * Encodes given string
     * 
     * @param text string to encode
     * @return encoded string
     * @throws IOException on failure to reach server
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public String encode(String text) throws IOException {
        Objects.requireNonNull(text);
         
        output.println("ENCODE " + text);
 
        return input.readLine();
    }
    
    /**
     * Decodes given string
     * 
     * @param morseText string to decode
     * @return decoded string
     * @throws IOException on failure to reach server
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public String decode(String morseText) throws IOException {
        Objects.requireNonNull(morseText);
        
        output.println("DECODE " + morseText);
        
        return input.readLine();
    }

    /**
     * Closes internal socket
     * 
     * @throws IOException on failure to close socket
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
    
}
