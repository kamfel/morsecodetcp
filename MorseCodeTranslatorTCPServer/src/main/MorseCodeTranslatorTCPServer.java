/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import models.Coder;
import models.MorseCoder;
import tcpserver.InputHandler;
import tcpserver.TCPServer;

/**
 * Main application class
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class MorseCodeTranslatorTCPServer {
    
    /**
     * Main method. Prepares and starts TCP server.
     * 
     * @param args the command line arguments
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public static void main(String[] args) {
       
        Coder coder = new MorseCoder("/resources/morsecodemap.properties");     
        InputHandler handler = new MorseCodeInputHandler(coder);
        
        Logger.getLogger("Main").log(Level.INFO, "Starting server...");
        
        TCPServer.createFromProperties("/resources/tcpserver.properties")
                .useInputHandler(handler)
                .run();
    }
    
}
