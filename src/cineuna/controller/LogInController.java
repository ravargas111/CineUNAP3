/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Mensaje;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class LogInController extends Controller implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private VBox vbInicioSesion;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXCheckBox cbAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void irMain(ActionEvent event) {
        try{
        if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) root.getScene().getWindow(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtClave.getText() == null || txtClave.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) root.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {
                /*EmpleadoService empleadoService =  new EmpleadoService();
                Respuesta respuesta = empleadoService.getUsuario(txtUsuario.getText(), txtClave.getText());
                if (respuesta.getEstado()) {
                    AppContext.getInstance().set("Usuario", (EmpleadoDto)respuesta.getResultado("Empleado"));
                    FlowController.getInstance().goMain();
                        ((Stage) btnIngresar.getScene().getWindow()).close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Ingreso", getStage(), respuesta.getMensaje());
                }*/
                
                if(cbAdmin.isSelected())
                AppContext.getInstance().set("administrador", (Boolean)true);
                else
                   AppContext.getInstance().set("administrador", (Boolean)false); 
                
                FlowController.getInstance().goMain();
                ((Stage) root.getScene().getWindow()).close();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
    }

    @FXML
    private void irRecuperacion(ActionEvent event) {
    }

    @FXML
    private void irRegistro(ActionEvent event) {
    }    
    
}
