/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kamil
 */
public class MorseCoderTest {
    
    private static MorseCoder coder;
    
    @BeforeClass
    public static void setUp(){
        coder = new MorseCoder("/resources/morsecodemap.properties");
    }
    
    @Test(expected = NullPointerException.class)
    public void encode_NullInputString_ShouldThrow(){     
        String output = coder.encode(null);        
    }
    
    @Test
    public void encode_EmptyInputString_ShouldReturnEmptyString(){      
        String output = coder.encode(new String());
        
        Assert.assertTrue(output.equals(""));
    }
    
    @Test
    public void encode_ValidInputString_ShouldReturnProperString(){
        String[] inputs = new String[] { 
            "abc", 
            "ala ma kota", 
            "1.3vs1.5", 
            "?2';ćż"
        };
        
        String[] expected = new String[] { 
            ".- -... -.-.", 
            ".- .-.. .- / -- .- / -.- --- - .-", 
            ".---- .-.-.- ...-- ...- ... .---- .-.-.- .....",
            "..--.. ..--- .----. -.-.-. -.-.. --..-."
        };
        
        String[] output = new String[4];
        
        for (int i = 0; i < 4; i++) {
            output[i] = coder.encode(inputs[i]);
        }
        
        Assert.assertTrue("Input 1", output[0].equals(expected[0]));
        Assert.assertTrue("Input 2", output[1].equals(expected[1]));
        Assert.assertTrue("Input 3", output[2].equals(expected[2]));
        Assert.assertTrue("Input 4", output[3].equals(expected[3]));
    }
    
    @Test(expected = NullPointerException.class)
    public void decode_NullInputString_ShouldThrow(){        
        String output = coder.decode(null);
    }
    
    @Test
    public void decode_EmptyInputString_ShouldReturnEmptyString(){       
        String output = coder.decode(new String());
        
        Assert.assertTrue(output.equals(""));
    }
    
    @Test
    public void decode_ValidInputString_ShouldReturnProperString(){
        String[] inputs = new String[] { 
            ".- -... -.-.", 
            ".- .-.. .- / -- .- / -.- --- - .-", 
            "..--- .-.-.- ...-- ...- ... ..--- .-.-.- .....",
            "..--.. ..--- .----. -.-.-. -.-.. --..-."
        };
        
        String[] expected = new String[] { 
            "abc", 
            "ala ma kota", 
            "2.3vs2.5", 
            "?2';ćż"
        };
             
        String[] output = new String[4];
        
        for (int i = 0; i < 4; i++) {
            output[i] = coder.decode(inputs[i]);
        }
            
        Assert.assertTrue("Input 1", output[0].equals(expected[0]));
        Assert.assertTrue("Input 2", output[1].equals(expected[1]));
        Assert.assertTrue("Input 3", output[2].equals(expected[2]));
        Assert.assertTrue("Input 4", output[3].equals(expected[3]));
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void decode_InvalidInputString_ShouldThrow(){
        coder.decode("ab c");
    }
}
