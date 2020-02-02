/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collector;

/**
 * A coder that encodes and decodes Morse code.
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class MorseCoder implements Coder {
    
    /**
     * Stores mappings from source to encoded
     */
    private HashMap<Character, String> encodeMap;
    
    /**
     * Stores mappings from encoded to source
     */
    private HashMap<String, Character> decodeMap;
      
    /**
     * Stores legal chars that can possibly be decoded
     */
    private List legalChars;
    
    /**
     * MorseCoder constructor
     * 
     * @param path path to file with character/string mappings, can't be null
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public MorseCoder(String path){
        if (path == null) 
            throw new IllegalArgumentException("Input path was null");
           
        try (InputStream stream = getClass().getResourceAsStream(path)) {
            
            if (stream == null) {
                System.out.println("No resource at path: " + path);
                return;
            }

            encodeMap = new HashMap<>();
            decodeMap = new HashMap<>();
            Properties properties = new Properties();
            properties.load(stream);

            for (String text : properties.stringPropertyNames()) {
                String morseCode = properties.getProperty(text);
                encodeMap.put(text.charAt(0), morseCode);
                decodeMap.put(morseCode, text.charAt(0));
            }

            //Add spaces to maps
            encodeMap.put(' ', "/");
            decodeMap.put("/", ' ');
            
            //Add morse code legal chars
            legalChars = Arrays.asList("-", ".", "/", " ");
            
        } catch (IOException ex) {
            System.out.println("There was a problem initialising MorseCoder.");
        }
    }
    
    /**
     * Encodes given string into Morse code
     * 
     * @param text string to encode, can't be null
     * @return encoded string
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Override
    public String encode(final String text) throws IllegalArgumentException{
        Objects.requireNonNull(text);
        
        return text.chars()
            .mapToObj(ch -> {
                String str = encodeMap.get((char)ch);                        
                if (str == null)
                    throw new IllegalArgumentException("Input string contains illegal chars");
                
                return str;
                })
            .collect(Collectors.joining(" "));
    }
    
    /**
     * Decodes given string from Morse code
     * 
     * @param morseText String to decode, can't be null
     * @return Decoded string
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Override
    public String decode(final String morseText) throws IllegalArgumentException {
        Objects.requireNonNull(morseText);   
        
        if (morseText.isEmpty())
            return "";
        
        return Arrays.stream(morseText.split(" "))
            .map(token -> {
                Character ch = decodeMap.get(token);                        
                if (ch == null)
                    throw new IllegalArgumentException("Input string contains illegal chars");
                
                return ch;
                })
            .collect(Collector.of(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append,
                StringBuilder::toString));
    }
}
