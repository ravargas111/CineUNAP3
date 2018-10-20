/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.cobro;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Mensaje;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.xml.sax.SAXException;
import java.util.Map;
import java.util.HashMap;
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
         /*  Boolean veri = sendEmail();
           if (veri) {
               new Mensaje().showModal(Alert.AlertType.ERROR, "Correo prueba", (Stage) root.getScene().getWindow(), "Se envio.");
           }*/
           generarReport();
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
    // Prueba de enviar email
     public boolean sendEmail() throws ParserConfigurationException, SAXException, IOException, AddressException, MessagingException {

        final String username = "mario.flores2598@gmail.com";
        // final String username = "eflores.crc@gmail.com";
        final String password = "m60a16r53i53o";

        Properties props = new Properties();
        /*props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");*/

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
            InternetAddress.parse("mario.flores2598@gmail.com"));
            message.setSubject("Comprobando mail ") ;
            message.setText("GEsta es la prueba.");

            MimeBodyPart messageBodyPart1 = new MimeBodyPart();
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            MimeBodyPart messageBodyPart3 = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

          /*  messageBodyPart1 = new MimeBodyPart();
            String file = parametrosGeneralesDto.getRutaComprobantesXmlFirmado() + comp.getClave() + "firmado.xml";
            String fileName = "Comprobante-" + comp.getClave() + ".xml";
            DataSource source = new FileDataSource(file);
            messageBodyPart1.setDataHandler(new DataHandler(source));
            messageBodyPart1.setFileName(fileName);

            messageBodyPart2 = new MimeBodyPart();
            String file2 = parametrosGeneralesDto.getRutaComprobantesPdf() + comp.getClave() + ".pdf";
            String fileName2 = "Comprobante-" + comp.getClave() + ".pdf";
            DataSource source2 = new FileDataSource(file2);
            messageBodyPart2.setDataHandler(new DataHandler(source2));
            messageBodyPart2.setFileName(fileName2);*/

           /* if (comprobanteHaciendaDto.getRespuestaXml() != null) {

                String string = comprobanteHaciendaDto.getRespuestaXml();
                byte[] byteArray = Base64.decodeBase64(string.getBytes());
                String decodedString = new String(byteArray);
                InputStream stream = new ByteArrayInputStream(decodedString.getBytes(StandardCharsets.UTF_8));

                File XmlRespuestaFile = new File("respuestaMinisterioHacienda.xml");
                FileUtils.writeByteArrayToFile(XmlRespuestaFile, byteArray);

                messageBodyPart3 = new MimeBodyPart();

                DataSource source3 = new FileDataSource(XmlRespuestaFile);
                messageBodyPart3.setDataHandler(new DataHandler(source3));
                messageBodyPart3.setFileName(XmlRespuestaFile.getName());
                multipart.addBodyPart(messageBodyPart3);

            }*/

           // multipart.addBodyPart(messageBodyPart1);
           // multipart.addBodyPart(messageBodyPart2);

            //message.setContent(multipart);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }
     public void generarReport() throws JRException, FileNotFoundException{
         String userHomeDirect = System.getProperty("user.home");
         String outPutFile = "src/cineuna/jasperReport/jasperPrueba.pdf";
         List<cobro> list = new ArrayList<>();
         
         cobro c1 = new cobro("25/01/1998",15000);
         cobro c2 = new cobro("25/01/2018",25000);
         list.add(c1);
         list.add(c2);
        // JRBeanCollectionDataSource cobrojrb = new JRBeanCollectionDataSource(list);
         JasperReport jasperReport = JasperCompileManager.compileReport("src/cineuna/jasperReport/ReporteGanancias=!!.jrxml");
         Map<String, Object> parametros = new HashMap<>();
         Map<String, Object> parametros1 = new HashMap<>();
         parametros1.put("idPar",22);
       //  parametros.put("dataSource", cobrojrb);
         JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, parametros1, new JREmptyDataSource());
         
         OutputStream outputStream = new FileOutputStream(new File(outPutFile));
            /* Write content to PDF file */
         JasperExportManager.exportReportToPdfStream(jasperprint, outputStream);
     }

}
