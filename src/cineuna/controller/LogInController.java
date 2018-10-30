/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;
import cineuna.model.UsuarioDto;
import cineuna.service.UsuarioService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Mensaje;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.xml.sax.SAXException;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

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
    UsuarioDto usuario;
    @FXML
    private JFXTextField tfRegApe1;
    @FXML
    private VBox vbpassChange;
    @FXML
    private JFXTextField txtPassChange;
    @FXML
    private VBox vbNewPass;
    @FXML
    private JFXPasswordField txtNewPass;
    @FXML
    private JFXPasswordField txtNewPassConf;
    @FXML
    private VBox vbLogInvb;
    UsuarioDto usuDto;

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
                
                 UsuarioService usuarioService = new UsuarioService();
                 Respuesta respuesta = usuarioService.getUsuario(txtUsuario.getText(), txtClave.getText());
                 
                if (respuesta.getEstado()) {
                       usuDto = (UsuarioDto)respuesta.getResultado("Usuario");
                       if(txtClave.getText().equals(usuDto.getUsuNewpassword())){
                            if(usuDto.getUsuCambio().equals("N")){
                                usuDto.setUsuCambio("S");
                                usuarioService.guardarUsuario(usuDto);
                            }
                            this.vbInicioSesion.setVisible(false);
                            this.vbNewPass.setVisible(true);  
                        }else{
                                if(usuDto.getUsuEstado().equals("A")){
                                     if(usuDto.usuAdmin.equals("S")){
                                         AppContext.getInstance().set("administrador", (Boolean)true);
                                    } 
                                    else{
                                        AppContext.getInstance().set("administrador", (Boolean)false);   
                                     } 
                                     AppContext.getInstance().setUsuario((UsuarioDto)respuesta.getResultado("Usuario"));
                                     FlowController.getInstance().goMain();
                                     ((Stage) root.getScene().getWindow()).close();  
                                }else{                    
                                     new Mensaje().show(Alert.AlertType.ERROR, "Cuenta Inactiva", "Es necesario activar la cuenta mediante el correo electronico");
                                 }
                        }
                }
                else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Usuario no registrado", getStage(), respuesta.getMensaje());
                }
        }
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
    }

    @FXML
    private void irRecuperacion(ActionEvent event) {
       
        this.vbInicioSesion.setVisible(false);    
        this.vbpassChange.setVisible(true);
        
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
        Boolean req = validaCamposRegistro();
        if(req){
            usuario = new UsuarioDto();
            usuario.setUsuNombre(tfRegNombre.getText());
            usuario.setUsuPapellido(tfRegApe.getText());
            usuario.setUsuUser(tfRegUsu.getText());
            usuario.setUsuPassword(tfRegContra.getText());
            usuario.setUsuEmail(tfRegCorreo.getText());
            if(!tfRegApe1.getText().isEmpty()){{
                usuario.setUsuSapellido(tfRegApe1.getText());
            }
            
             try {
            UsuarioService usuarioService = new UsuarioService();
            Respuesta respuesta = usuarioService.guardarUsuario(usuario);
             if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), respuesta.getMensaje());
                } else {
                    usuario = (UsuarioDto) respuesta.getResultado("Usuario");
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar usuario", getStage(), "Usuario creado correctamente.");
                    sendEmailActivar(usuario);
                }
            
        } catch (Exception ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Error registrando usuario.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), "Ocurrio un error registrando el usuario.");
        }
     }   
    }
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
    //  enviar email
     public boolean sendEmailActivar(UsuarioDto usuario) throws ParserConfigurationException, SAXException, IOException, AddressException, MessagingException {

        final String username = "proyectosuna83@gmail.com";
        final String password = "proy.una";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(usuario.getUsuEmail()));
            message.setSubject("Activacion Cuenta CINEUNAPZ") ;
            message.setText("Ingrese al link para activar la cuenta " + "http://DESKTOP-RCLJD2G:80/WsCineUNA/wsCine/UsuarioController/activar/"+usuario.getUsuUser());

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }
     
     //  enviar email
     public boolean sendEmailNewPass(UsuarioDto usuario,String newPass) throws ParserConfigurationException, SAXException, IOException, AddressException, MessagingException {

        final String username = "proyectosuna83@gmail.com";
        final String password = "proy.una";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(usuario.getUsuEmail()));
            message.setSubject("Cambio de Contraseña Cuenta CINEUNAPZ") ;
            message.setText("Para realizar el cambio de contraseña ingrese con su nombre de usuario y esta contraseña."
                    + "Contraseña probicional :  " + newPass);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }  

    /* public void generarReport() throws JRException, FileNotFoundException{
         String outPutFile = "src/cineuna/jasperReport/jasperPrueba.pdf";
         List<cobro> list = new ArrayList<>();        
         cobro c1 = new cobro();
         c1.setNombre("mario"); 
         c1.setInicio("18/06/1996");
         c1.setfin("25/01/1998");
         c1.setOcupados(15);
         c1.setDesocupados(10);
         c1.setMonto(50000);
         list.add(c1);
         JRBeanCollectionDataSource cobrojrb = new JRBeanCollectionDataSource(list);        
         JasperReport jasperReport = JasperCompileManager.compileReport("src/cineuna/jasperReport/reporteCanchasPZu.jrxml");
         Map<String, Object> parametros = new HashMap<>();
         parametros.put("dataSource", cobrojrb);
         JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
         
         OutputStream outputStream = new FileOutputStream(new File(outPutFile));
            /* Write content to PDF file */
       /*  JasperExportManager.exportReportToPdfStream(jasperprint, outputStream);
         File png = new File("src/cineuna/jasperReport/jasperPrueba.pdf");
         
     }*/

    @FXML
    private void btnVlvPassChange(ActionEvent event) {
        this.vbInicioSesion.setVisible(true);    
        this.vbpassChange.setVisible(false);
    }

    @FXML
    private void btnMailPassChange(ActionEvent event) throws ParserConfigurationException, SAXException, IOException, MessagingException {
        if(!this.txtPassChange.getText().isEmpty()){
            UsuarioDto usuDto;
            UsuarioService usuarioService = new UsuarioService();
            Respuesta respuesta = usuarioService.getUsuarioUsu(txtUsuario.getText());
 
                if (respuesta.getEstado()) {
                    usuDto = (UsuarioDto)respuesta.getResultado("Usuario");
                    String newPass = generarNewPassword();
                   
                    Boolean enviado =  sendEmailNewPass(usuDto,newPass);
                    if(enviado){
                      usuDto.setUsuNewpassword(newPass);
                      usuarioService.guardarUsuario(usuDto);
                      this.vbpassChange.setVisible(false);
                      this.vbLogIn.setVisible(true);  
                    } 
                    else{
            new Mensaje().show(ERROR, "Error Enviando Correo", "Reeintentelo");
        }
        }else{
                 new Mensaje().showModal(Alert.AlertType.ERROR, "Encontrando usuario", getStage(), respuesta.getMensaje());    
                }
        }
        else{
            new Mensaje().show(ERROR, "Nombre de usuario vacio", "Ingrese su nombre de usuario");
        }
    
    }
    @FXML
    private void btnNewPassVlv(ActionEvent event) {
           this.vbInicioSesion.setVisible(true);
           this.vbNewPass.setVisible(false);
    }

    @FXML
    private void btnNewPassCont(ActionEvent event) {
        if(txtNewPass.getText().isEmpty()||txtNewPass.getText().length()<8){
            new Mensaje().show(ERROR, "Contraseña invalida!", "Tamaño mínimo de contraseña de 8 caracteres");
        }
        else if(txtNewPass.getText() == null ? txtNewPassConf.getText() != null : !txtNewPass.getText().equals(txtNewPassConf.getText())){
            new Mensaje().show(ERROR, "Contraseña invalida!", "Contraseñas no coinciden");
        }else{
            UsuarioService usuarioService = new UsuarioService();
            usuDto.setUsuPassword(txtNewPass.getText());
            usuDto.setUsuCambio("N");
            usuDto.setUsuNewpassword("");
            
            Respuesta respuesta = usuarioService.guardarUsuario(usuDto);
                if (respuesta.getEstado()) {  
                    new Mensaje().show(INFORMATION, "Usuario actualizando !","Se cambio tu contreseña correctamente");  
                      this.vbInicioSesion.setVisible(false);
                      this.vbNewPass.setVisible(true);  
                } else{
                     new Mensaje().show(ERROR, "Error Actualizando usuario!", respuesta.getMensaje());  
                     this.vbInicioSesion.setVisible(false);
                      this.vbNewPass.setVisible(true); 
                }
            
        }
    }

    
     private String generarNewPassword() {
         Integer numero;
         Integer numeroABC;
         String newPass ="";
         String [] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
         "K", "L", "M","N","O","P","Q","R","S","T","U","V","W", "X","Y","Z" };

int numRandon = (int) Math.round(Math.random() * 26 ) ;

System.out.println( abecedario[numRandon] );
         
         for(int i = 0; i <= 3 ; i++){
            numero = (int) (Math.random() * 9) + 1;
            numeroABC = (int) (Math.random() * 26) + 1;
            newPass += numero.toString();
            
            newPass += abecedario[numeroABC];
         }
         return newPass;
    }
}
