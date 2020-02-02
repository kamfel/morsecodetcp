/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import models.Coder;
import tcpserver.Handler;
import tcpserver.InputHandler;
import tcpserver.TCPResponse;

/**
 * Defines handlers for commands coming from clients
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class MorseCodeInputHandler extends InputHandler {
    
    /**
     * Coder used to encode and decode messages
     */
    private final Coder coder;
    
    /**
     * Constructor
     * 
     * @param coder coder used to encode and decode messages
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public MorseCodeInputHandler(Coder coder) {
        this.coder = coder;
    }
    
    /**
     * Handles HELP command. 
     * 
     * @param param parameter given by the client
     * @return help information as a response
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Handler(Command = "help")
    public TCPResponse help(String param){
        
        if (param == null || param.isEmpty())
            return TCPResponse.success("Commands: HELP, ENCODE, DECODE. Format: [command] [parameter]. Type HELP [command] for more info.");
        else if (param.toLowerCase().equals("encode"))
            return TCPResponse.success("Format: ENCODE [parameter], where parameter is a string of characters (no need for using quotation marks)");
        else if (param.toLowerCase().equals("decode"))
            return TCPResponse.success("Format: DECODE [parameter], where parameter is a string of characters (no need for using quotation marks)");
        else
            return TCPResponse.fail("Unrecognized command");
    }
    
    /**
     * Handles ENCODE command. Encodes given string.
     * 
     * @param param parameter given by client
     * @return encoded string as a response
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Handler(Command = "encode")
    public TCPResponse encodeToMorse(String param){
        try {
            
            String result = coder.encode(param);
            return TCPResponse.success(result);
            
        } catch (NullPointerException | IllegalArgumentException ex) {
            return TCPResponse.fail("Invalid param");
        }
        
    }
    
    /**
     * Handles DECODE command. Decodes given string.
     * 
     * @param param parameter given by client
     * @return decoded string as a response
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Handler(Command = "decode")
    public TCPResponse decodeToMorse(String param){
        try {
            
            String result = coder.decode(param);
            return TCPResponse.success(result);
            
        } catch (NullPointerException | IllegalArgumentException ex) {
            return TCPResponse.fail("Invalid param");
        }      
    }
}
