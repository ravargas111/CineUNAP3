/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class ContainerController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    private Boolean isAdmin;
    private JFXButton b;
    @FXML
    private VBox menuAdmin;
    @FXML
    private VBox menuUsu;
    @FXML
    private FontAwesomeIconView btnOpcionesUsu;
    @FXML
    private Hyperlink hlNombreUsuario;
    private JFXPopup popUp;
    @FXML
    private AnchorPane apTop;
    private VBox vbOpcionesUsu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initContainers();
        isAdmin =(Boolean)(AppContext.getInstance().get("administrador"));
        if(isAdmin){
            menuAdmin.setVisible(true);
            menuUsu.setVisible(false);
        }
        llenarInfoUsuario();
        
    }    

    @Override
    public void initialize() {
        if(isAdmin){
            menuAdmin.setVisible(true);
            menuUsu.setVisible(false);
        }
        llenarInfoUsuario();
    }

    @FXML
    private void irParamG(MouseEvent event) {
    }

    @FXML
    private void irMantUsuarios(MouseEvent event) {
    }

    @FXML
    private void irMantPeliculas(MouseEvent event) {
    }

    @FXML
    private void irMantSalas(MouseEvent event) {
    }

    @FXML
    private void irInfoUsuario(MouseEvent event) {
    }

    @FXML
    private void irInfoCine(MouseEvent event) {
    }

    @FXML
    private void irCartelera(MouseEvent event) {
    }

    @FXML
    private void irProximas(MouseEvent event) {
    }
    
    /**
     * llena el hyperlink del nombre de usuario y asigna el popup
     */
    public void llenarInfoUsuario(){
        this.hlNombreUsuario.setText((String) AppContext.getInstance().get("nombre"));
        this.hlNombreUsuario.setOnAction(e->{
            //FlowController.getInstance().goView("InfoUsuario");
        });
        
        this.btnOpcionesUsu.setOnMouseClicked(event ->{
            this.popUp.show(btnOpcionesUsu,JFXPopup.PopupVPosition.TOP,JFXPopup.PopupHPosition.LEFT,event.getX(),event.getY());
        });
        llenarOpcionesUsu();
        this.popUp.setPopupContent(this.vbOpcionesUsu);
    }
    
    /**
     * llena el vbox de las opciones del perfil de usuario
     */
    public void llenarOpcionesUsu(){
        for (int i = 0; i < 10; i++) {
            Hyperlink hl= new Hyperlink();
            switch (i) {
                case 1:
                    hl.setText("Cerrar Sesión");
                    hl.setOnAction(e->{
                        ((Stage) root.getScene().getWindow()).close();
                        FlowController.getInstance().goViewInWindow("LogIn");
                    });
                    this.vbOpcionesUsu.getChildren().add(hl);
                    break;
                default:
                    break;
            }
            
        }
    }
    
    public void initContainers(){
        this.vbOpcionesUsu=new VBox();
        this.popUp= new JFXPopup();
    }
}
