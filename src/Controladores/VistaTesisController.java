/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.Documento;
import Modelos.Libro;
import Modelos.PrestamosModel;
import Modelos.TrabajoGrado;
import Modelos.registmodel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class VistaTesisController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private AnchorPane viewp;
    private Ajustesmodel ajmodel = new Ajustesmodel();
    private StageMovement stmodel = new StageMovement();
    private registmodel rgmodel = new registmodel();
    private TrabajoGrado tesis;
    @FXML
    private StackPane base;

    @FXML
    private ImageView logopsm;

    @FXML
    private ImageView imgportada;

    @FXML
    private JFXButton btneditar;

    @FXML
    private JFXButton btneliminar;

    @FXML
    private ScrollPane scroller;

    @FXML
    private JFXTextField textid;

    @FXML
    private JFXTextField texttitulo;

    @FXML
    private JFXTextField textautor;

    @FXML
    private JFXTextField textcedula;

    @FXML
    private JFXTextField textcarrera;

    @FXML
    private JFXTextField textcodigo;

    @FXML
    private JFXTextField textfecha;

    @FXML
    private JFXButton btnresumen;

    @FXML
    private JFXButton btnindice;

    @FXML
    private JFXButton btncompartir;

    @FXML
    private JFXButton btndescargar;

    @FXML
    private JFXButton btnvolver111111;

    @FXML
    private ImageView logobanda;

    @FXML
    private JFXButton btnvolver;

    @FXML
    private JFXButton btnguarcambios;
    @FXML
    private AnchorPane viewpane1;

    public void setviewpane(AnchorPane viewpane) {
        this.viewp = viewpane;
    }

    public void settesis(TrabajoGrado tesis) {
        this.tesis = tesis;
        if (tesis != null) {
            System.out.println("Objeto recibido: " + tesis.getTitulo()); // Depuraci�n
            mostrardatos(tesis);
            tesis.getdatos();
        } else {
            System.out.println("No se pas� ning�n objeto al controlador.");
        }
    }
    
    @FXML
    public void iraprestamos(MouseEvent e){
        PrestamosModel p  = new PrestamosModel();
        p.loadpageprestamo("Prestamooficial", viewp, tesis);
        
    
    }

    @FXML
    public void volver(MouseEvent e) {
        stmodel.loadpagevolverabuscar("buscar", viewp);
    }
    @FXML
    public void volveraestudiantes(MouseEvent e){
        stmodel.loadpagevolverabuscarestudiantes("estudiantes", viewp);
    }
    @FXML
    public void editar(MouseEvent e) {

        stmodel.loadpagemodificartesis("registrar", viewp, tesis);
    }

    @FXML
    public void eliminar(MouseEvent e) {
        // Crear un JFXDialog para la confirmaci�n de eliminaci�n
        VBox dialogContent = new VBox();
        Label mensaje = new Label("�Est�s seguro de eliminar este documento?");
        dialogContent.getChildren().add(mensaje);

        // Botones de confirmaci�n
        JFXButton btnConfirmar = new JFXButton("S�");
        JFXButton btnCancelar = new JFXButton("No");

        // Configurar la acci�n de confirmar
        btnConfirmar.setOnAction(event -> {
            boolean decision = rgmodel.eliminarDocumentos(tesis.getId_documento());
            if (decision) {
                // Mostrar di�logo de �xito
                JFXDialog dialogExito = new JFXDialog(base, dialogContent, JFXDialog.DialogTransition.CENTER);
                Label exitoMensaje = new Label("Los datos han sido eliminados correctamente.");
                dialogContent.getChildren().clear();
                dialogContent.getChildren().add(exitoMensaje);
                dialogExito.show();

                // Cerrar di�logo de confirmaci�n
                dialogExito.setOnDialogClosed(e1 -> {
                    stmodel.loadpagevolverabuscar("buscar", viewp);
                });
            } else {
                // Mostrar di�logo de error
                JFXDialog dialogError = new JFXDialog(base, dialogContent, JFXDialog.DialogTransition.CENTER);
                Label errorMensaje = new Label("Los datos no han sido eliminados correctamente.");
                dialogContent.getChildren().clear();
                dialogContent.getChildren().add(errorMensaje);
                dialogError.show();
            }
        });

        // Configurar la acci�n de cancelar
        // Agregar botones al contenido del di�logo y mostrarlo
        dialogContent.getChildren().addAll(btnConfirmar, btnCancelar);
        JFXDialog dialog = new JFXDialog(base, dialogContent, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        btnCancelar.setOnAction(event -> {
            // Cerrar el di�logo de confirmaci�n sin hacer nada
            dialogContent.getChildren().clear();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void mostrardatos(TrabajoGrado tes) {

        textid.setText(String.valueOf(tes.getId_documento()));
        texttitulo.setText(tes.getTitulo());
        textautor.setText(tes.getAutor());
        textcedula.setText(tes.getCedula());
        textcarrera.setText(tes.getCarrera());
        textcodigo.setText(tes.getCodigo());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        textfecha.setText(tes.getFecha_publicacion().format(formatter));
        try {
            imgportada.setImage(Documento.convertPdfBytesToImage(tes.getImg_portada()));
        } catch (IOException ex) {
            Logger.getLogger(VistaTesisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abrirDialogoImagen(String titulo, byte[] archivoPdf) {
        try {
            // Crear un StackPane para el di�logo
            StackPane stackPane = (StackPane) base.getScene().getRoot();

            // Crear el contenido del di�logo
            JFXDialogLayout contenido = new JFXDialogLayout();
            contenido.setHeading(new Text(titulo));
            contenido.setPrefWidth(1000);
            contenido.setPrefHeight(600);

            // Crear un ScrollPane para contener las im�genes
            ScrollPane scrollPane = new ScrollPane();
            VBox contenedorImagenes = new VBox(10);
            contenedorImagenes.setAlignment(Pos.CENTER); // Alinear contenido al centro
            scrollPane.setContent(contenedorImagenes);
            scrollPane.setFitToWidth(true);

            // Convertir el byte[] en un archivo PDF temporal
            File archivoTemporal = convertirByteArrayAFile(archivoPdf);

            // Cargar las p�ginas del PDF como im�genes
            registmodel modelo = new registmodel();
            modelo.cargarPaginasComoImagenesParaVistas(archivoTemporal, 1, contarPaginas(archivoTemporal), contenedorImagenes);

            // Configurar el ScrollPane
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Ocultar barra horizontal
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Mostrar barra vertical cuando sea necesario

            // Agregar el ScrollPane al contenido del di�logo
            contenido.setBody(scrollPane);

            // Crear y mostrar el JFXDialog
            JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
            dialog.show();

            // Limpiar archivo temporal despu�s de cerrar el di�logo
            dialog.setOnDialogClosed(event -> archivoTemporal.delete());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File convertirByteArrayAFile(byte[] contenido) throws IOException {
        File archivoTemporal = File.createTempFile("archivoPDF", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(archivoTemporal)) {
            fos.write(contenido);
        }
        return archivoTemporal;
    }

    private int contarPaginas(File archivoPdf) throws IOException {
        try (PDDocument documento = Loader.loadPDF(archivoPdf)) {
            return documento.getNumberOfPages();
        }
    }

    @FXML
    private void verportada(MouseEvent e) {
        abrirDialogoImagen("Portada", tesis.getImg_portada());
    }

    @FXML
    private void verresumen(MouseEvent e) {
        abrirDialogoImagen("Resumen", tesis.getResumen());
    }

    @FXML
    private void verindice(MouseEvent e) {
        abrirDialogoImagen("Bibliograf�a", tesis.getIndice());
    }

    private String emailFrom = "bibliotecapsmcabimas@gmail.com";
    private String passwordFrom = "cdpspefajfwsyexb"; // Cambia por un m�todo m�s seguro para almacenar contrase�as
// Cambia por un m�todo m�s seguro para almacenar contrase�as

    @FXML
    private void mostrarDialogoCorreo(MouseEvent event) {
        // Crear el contenedor ra�z del di�logo
        StackPane stackPane = (StackPane) base.getScene().getRoot();

        // Crear el contenido del di�logo
        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new Text("Enviar archivo por correo"));
        contenido.setPrefWidth(400);
        contenido.setPrefHeight(200);

        // Campo de texto para ingresar el correo electr�nico
        JFXTextField campoCorreo = new JFXTextField();
        campoCorreo.setPromptText("Ingrese el correo electr�nico");

        JFXTextField campoMensaje = new JFXTextField();
        campoMensaje.setPromptText("Ingrese un mensaje adicional (opcional)");

        // Mensaje de validaci�n o resultado
        Label mensajeResultado = new Label();
        mensajeResultado.setVisible(false);

        // Bot�n para enviar
        JFXButton btnEnviar = new JFXButton("Enviar");
        btnEnviar.setOnAction(e -> {
            String correo = campoCorreo.getText();
            String mensaje = campoMensaje.getText();
            if (esCorreoValido(correo)) {
                // Llamar al m�todo de env�o de correo
                boolean enviado = enviarCorreo(correo, tesis, mensaje); // 'tesis' es tu objeto de tipo TrabajoGrado

                if (enviado) {
                    mensajeResultado.setText("Correo enviado exitosamente");
                    mensajeResultado.setStyle("-fx-text-fill: green;");
                } else {
                    mensajeResultado.setText("Error al enviar el correo");
                    mensajeResultado.setStyle("-fx-text-fill: red;");
                }
                mensajeResultado.setVisible(true);
            } else {
                mensajeResultado.setText("Correo inv�lido. Verifique el formato.");
                mensajeResultado.setStyle("-fx-text-fill: red;");
                mensajeResultado.setVisible(true);
            }
        });

        // Contenedor para los botones
        VBox cuerpo = new VBox(10, campoCorreo, campoMensaje, mensajeResultado, btnEnviar);
        contenido.setBody(cuerpo);

        // Crear y mostrar el di�logo
        JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    private boolean enviarCorreo(String correoDestinatario, TrabajoGrado tesis, String msjpersonalizado) {
        Properties mProperties = new Properties();
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        Session mSession = Session.getDefaultInstance(mProperties);
        MimeMessage mCorreo;

        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(correoDestinatario));
            mCorreo.setSubject("Informaci�n sobre el libro");

            // Creaci�n del contenido del correo
            String contenido = "<html><body>"
                    + "<h2>Detalles del Libro</h2>"
                    + "<p>T�tulo: " + tesis.getTitulo() + "</p>"
                    + "<p>Autor: " + tesis.getAutor() + "</p>" + "<p>Nota: " + msjpersonalizado + "</p>"
                    + "</body></html>";

            // Crear el cuerpo del mensaje
            MimeBodyPart cuerpo = new MimeBodyPart();
            cuerpo.setContent(contenido, "text/html; charset=ISO-8859-1");

            // Crear el archivo adjunto si existe
            MimeBodyPart adjunto = new MimeBodyPart();
            if (tesis.getResumen()!= null && tesis.getResumen().length > 0) {
                DataSource fuenteDatos = new ByteArrayDataSource(tesis.getResumen(), "application/pdf");
                adjunto.setDataHandler(new DataHandler(fuenteDatos));
                adjunto.setFileName(tesis.getTitulo() + ".pdf");
            }

            // Crear un contenedor Multipart y agregar el cuerpo y el adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpo);
            if (adjunto.getDataHandler() != null) {
                multipart.addBodyPart(adjunto);
            }

            mCorreo.setContent(multipart);

            // Enviar el correo
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(VistaLibroController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

// M�todo para validar si el correo es v�lido
    private boolean esCorreoValido(String correo) {
        String patronCorreo = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,}$";
        return correo.matches(patronCorreo);
    }

    @FXML
    private void guardararchivo(MouseEvent e) {
        tesis.guardarArchivo(e, tesis);
    }

}
