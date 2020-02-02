/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import networkservices.MorseCodeService;

/**
 * Controller that handles the main logic of the application
 * 
 * @version 1.0
 * @author Kamil Felkel
 */
public class MainViewController implements Initializable {
    
    /**
     * Shows current connection status
     */
    @FXML
    private Label connectionStatus;
    
    /**
     * Connects to server
     */
    @FXML
    private Button connectionButton;
    
    /**
     * Toggles between encoding and decoding
     */
    @FXML
    private ToggleButton toggleButton;
    
    /**
     * Performs the encoding and decoding
     */
    @FXML
    private Button executeButton;
    
    /**
     * Source of text from user
     */
    @FXML
    private TextArea inputTextArea;
    
    /**
     * Output of encoding or decoding
     */
    @FXML
    private TextArea outputTextArea;
    
    /**
     * Coder that defines how to encode and decode
     */
    private MorseCodeService service;
    
    /**
     * Current encoding/decoding strategy
     */
    private Function<String, String> currentFunc;
    
    /**
     * Function used to encode user data
     */
    private Function<String, String> encodeFunc;
    
    /**
     * Function used to decode user data
     */
    private Function<String, String> decodeFunc;
    
    /**
     * Toggles between encode and decode strategy
     * 
     * @param event event that occured when toggleButton was toggled
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @FXML
    private void toggleAction(ActionEvent event) {
        if (currentFunc == decodeFunc) {
            currentFunc = encodeFunc;
            toggleButton.setText("To Morse");
            executeButton.setText("Encode");
        }
        else {
            currentFunc = decodeFunc;
            toggleButton.setText("From Morse");
            executeButton.setText("Decode");
        }
    }
    
    /**
     * Executes current strategy and outputs result to defined output
     * 
     * @param event event that occured when executeButton was pressed
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @FXML
    private void executeAction(ActionEvent event) {      
        final String sourceText = inputTextArea.getText();
        final String outputText = currentFunc.apply(sourceText);
        outputTextArea.setText(outputText);
    }

    /**
     * Tries to connect to server
     * 
     * @param event event that occured when executeButton was pressed
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @FXML
    private void connectAction(ActionEvent event) {
        try {
            
            connectionStatus.setText("Connecting...");
            service.connect();           
            connectionStatus.setText("Connected");
            
            connectionButton.setVisible(false);
            
        } catch (IOException ex) {
            Logger.getLogger("Controller").log(Level.SEVERE, null, ex);
            connectionStatus.setText("Failed");
        }

    }
    
    
    /**
     * Called when root element has been processed; initializes controller
     * 
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb the resources used to localize the root object, or null if the root object was not localized.
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        service = new MorseCodeService();
        
        service.loadServerAdressFromProperties("/resources/connection.properties");
        
        encodeFunc = in -> {
            try {
                return service.encode(in);
            } catch (IOException ex) {
                Logger.getLogger("Controller").log(Level.SEVERE, null, ex);
                handleConnectionProblem();
                return "";
            }
        };
        decodeFunc = in -> {
            try {
                return service.decode(in);
            } catch (IOException ex) {
                Logger.getLogger("Controller").log(Level.SEVERE, null, ex);
                handleConnectionProblem();
                return "";
            }
        };
        
        outputTextArea.setEditable(false);
        
        currentFunc = encodeFunc;
        toggleButton.setText("To Morse");
        executeButton.setText("Encode");
    }    
    
    private void handleConnectionProblem(){
        connectionStatus.setText("Disconnected.");
        
        connectionButton.setVisible(true);
    }
}
