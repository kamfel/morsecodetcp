/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

/**
 * Represents a server response with a status code and message
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class TCPResponse {
    
    /**
     * Indicates what kind of response the instance represents
     */
    private final StatusCode statusCode;
    
    /**
     * Message for client, kind of a response body
     */
    private final String message;
    
    /**
     * Constructor
     * 
     * @param code see {@link StatusCode}
     * @param message message for client
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public TCPResponse(StatusCode code, String message){
        this.statusCode = code;
        this.message = message;
    }
    
    /**
     * Creates a failure response
     * 
     * @param reason reason of failure
     * @return a failure response
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public static TCPResponse fail(String reason) {
        return new TCPResponse(StatusCode.FAILURE, reason);
    }

    /**
     * Creates a success response
     * 
     * @param message message for client
     * @return a success response
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public static TCPResponse success(String message) {
        return new TCPResponse(StatusCode.SUCCESS, message);
    }
    
    /**
     * Converts response to a string, ready to be sent
     * 
     * @return response converted to string
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public String asString(){
        return "[" + statusCode.name() + "] " + message;
    } 
}
