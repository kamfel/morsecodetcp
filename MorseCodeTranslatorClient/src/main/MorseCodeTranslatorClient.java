/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class
 * 
 * @author Kamil
 */
public class MorseCodeTranslatorClient extends Application {

     /**
     * Entry point of JavaFX application
     * 
     * @param stage primary stage of the application
     * @throws Exception throws if some condition prevents app from starting
     * 
     * @version 1.0
     * @author Kamil Felkel
     */
    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/resources/MainView.fxml");
        
        Parent root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Entry point of the entire application
     * 
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
