/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

/**
 * An object that contains basic command handlers
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public abstract class InputHandler {
    
    /**
     * Handles commands that couldn't be matched to any other command
     * 
     * @param param command parameter
     * @return deafult server response 
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Handler(Command = "default")
    public TCPResponse onValidationFailure(String param){
        return TCPResponse.fail("Unknown command. Type HELP for more info.");
    }
}
