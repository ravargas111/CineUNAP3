/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna;

import cineuna.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author robri
 */
public class CineUNA extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FlowController.getInstance().InitializeFlow(primaryStage,null);
        //primaryStage.getIcons().add(new Image("unaplanilla2/resources/Agregar-48.png"));
        primaryStage.setTitle("UNA PLANILLA");
        FlowController.getInstance().goViewInWindow("LogIn");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
