/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adnotation that is used to indicate handlers
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    
    /**
     * Command that the handler should handle
     * 
     * @return command associated with the adnotation
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    public String Command() default "";
}
