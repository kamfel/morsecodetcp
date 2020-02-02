/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles requests from a single client and processes his/her commands
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class RequestProcessor {
    
    /**
     * Output extracted from socket
     */
    private final PrintWriter output;
    
    /**
     * Input extracted from socket
     */
    private final BufferedReader input;
    
    /**
     * Object that dispatches commands to handlers
     */
    private final Dispatcher dispatcher;
    
    /**
     * Constructor
     * 
     * @param output outputstream extracted from socket
     * @param input inputstream extracted from socket
     * @param dispatcher see {@link Dispatcher}
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public RequestProcessor(OutputStream output, InputStream input, Dispatcher dispatcher) {
        
        this.output = new PrintWriter(
                        new BufferedWriter(
                          new OutputStreamWriter(output)), true);
        
        this.input = new BufferedReader(
                        new InputStreamReader(input));
        
        this.dispatcher = dispatcher;
    }
    
    /**
     * Services single client connection. On disconnect returns.
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public void process() {
        
        try {
            
            while (true) {
                
                String line = input.readLine();
            
                Logger.getLogger("Processor").log(Level.INFO, "Received request: {0}", line);
                
                ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(" ")));
            
                String command = parts.get(0).toLowerCase();
                    
                if (command.equals("quit")){
                    break;
                }
            
                parts.remove(0);
            
                String param = String.join(" ", parts).toLowerCase();
                     
                TCPResponse response = dispatcher.processRequest(command, param);
            
                Logger.getLogger("Processor").log(Level.INFO, "Responding: {0}", response.asString());
                
                output.println(response.asString());            
            
            }
        } catch(IOException ex){
            
        }
        
    }
    
}
