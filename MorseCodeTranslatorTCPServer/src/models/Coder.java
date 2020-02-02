/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 * Defines a basic coder.
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public interface Coder {
    
    /**
    * Encodes given string.
    * 
    * @param plainText String to encode
    * 
    * @return Encoded string
    * 
    * @version 1.0
    * @author Kamil Felkel
    */
    public String encode(final String plainText);
    
    /**
    * Decodes given string.
    * 
    * @param encodedText String to decode
    * 
    * @return Decoded string
    * 
    * @version 1.0
    * @author Kamil Felkel
    */
    public String decode(final String encodedText);
}
