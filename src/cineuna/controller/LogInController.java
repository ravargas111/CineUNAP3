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
    @FXML
    private VBox vbOpcUsu;
    @FXML
    private VBox vbRegUsu;
    @FXML
    private VBox vbRegCliente;
    @FXML
    private JFXTextField tfRegNombre;
    @FXML
    private JFXTextField tfRegApe;
    @FXML
    private JFXTextField tfRegUsu;
    @FXML
    private JFXPasswordField tfRegContra;
    @FXML
    private JFXPasswordField tfRegcontraRep;
    @FXML
    private JFXTextField tfRegCorreo;
    @FXML
    private StackPane vbLogIn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        reinicia();
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
                
                AppContext.getInstance().set("nombre", (String)this.txtUsuario.getText());
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

    @FXML
    private void irInicioSesion(ActionEvent event) {
        this.vbLogIn.setVisible(false);
        this.vbInicioSesion.setVisible(true);
    }

    @FXML
    private void irRegistroCliente(ActionEvent event) {
        this.vbLogIn.setVisible(false);
        this.vbRegCliente.setVisible(true);
    }

    @FXML
    private void cambiarModoIngreso(ActionEvent event) {
        if(this.cbAdmin.isSelected())
        this.vbOpcUsu.setVisible(false);
        else
            this.vbOpcUsu.setVisible(true);
    }
    
    @FXML
    private void registrarCliente(ActionEvent event) {
        validaCamposRegistro();
    }
    
    private Boolean validaCamposRegistro(){
        Boolean completo = true;
        String estado = "";
        if(tfRegNombre.getText().isEmpty()){
            completo=false;
            estado+="Ingresar nombre\n";
        }
        if(tfRegApe.getText().isEmpty()){
            completo = false;
            estado+="Ingresar Apellido\n";
        }
        if(tfRegUsu.getText().isEmpty()){
            completo = false;
            estado+="Ingresar Usuario\n";
        }
        if(tfRegContra.getText().isEmpty()||tfRegContra.getText().length()<8){
            completo = false;
            estado+="Tamaño mínimo de contraseña de 8 caracteres\n";
        }
        if(tfRegcontraRep.getText() == null ? tfRegContra.getText() != null : !tfRegcontraRep.getText().equals(tfRegContra.getText())){
            completo = false;
            estado+="Contraseñas no coinciden\n";
        }
        if(tfRegCorreo.getText().isEmpty()){
            completo = false;
            estado+="Ingresar Correo\n";
        }
        
        Mensaje msj = new Mensaje();
        if(!completo)
            msj.show(Alert.AlertType.ERROR, "Algunos problemas encontrados", estado);
        else{
            msj.show(Alert.AlertType.INFORMATION, "Registrado", "favor confirmar mediante clave enviada al correo");
            reinicia();
        }
        return completo;
    }
    
    private void reinicia(){
        this.vbLogIn.setVisible(true);
        this.vbInicioSesion.setVisible(false);
        this.vbRegCliente.setVisible(false);
    }

}
