/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.Documento;

import Modelos.Libro;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * FXML Controller class
 *
 * @author VictorY
 */
public class VistaLibroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private AnchorPane viewp;
    private Ajustesmodel ajmodel = new Ajustesmodel();
    private StageMovement stmodel = new StageMovement();
    private registmodel rgmodel = new registmodel();

    @FXML
    private JFXButton btnguarcambios;
    @FXML
    private AnchorPane viewpane1;
    @FXML
    private JFXButton btnresumen;

    @FXML
    private JFXButton btncompartir;
    @FXML
    private JFXButton btnindice;
    @FXML
    private ImageView imgportada;
    @FXML
    private JFXButton btnvolver;
    @FXML
    private JFXTextField textid;
    @FXML
    private StackPane base;
    @FXML
    private JFXTextField texttitulo;

    @FXML
    private JFXTextField textautor;

    @FXML
    private JFXTextField textedicion;

    @FXML
    private JFXTextField textestante;

    @FXML
    private JFXTextField texteditorial;

    @FXML
    private JFXTextField textcodlomo;

    @FXML
    private JFXButton btneliminar;
    @FXML
    private JFXTextField textfecha;

    @FXML
    private JFXButton btneditar;

    @FXML
    private ScrollPane scroller;

    private Libro libro;
    private JFXDialog dialog; // Variable de instancia

    public void setlibro(Libro lib) {
        this.libro = lib;
        if (lib != null) {
            mostrardatos(lib); // Muestra los datos solo si el libro no es nulo
        }
    }

    public void setviewpane(AnchorPane viewpane) {
        this.viewp = viewpane;
    }

    @FXML
    public void volver(MouseEvent e) {
        stmodel.loadpagevolverabuscar("buscar", viewp);
    }

    @FXML
    public void editar(MouseEvent e) {

        stmodel.loadpagemodificarlibro("registrar", viewp, libro);
    }

    @FXML
    public void eliminar(MouseEvent e) {
        // Crear un JFXDialog para la confirmación de eliminación
        VBox dialogContent = new VBox();
        Label mensaje = new Label("¿Estás seguro de eliminar este documento?");
        dialogContent.getChildren().add(mensaje);

        // Botones de confirmación
        JFXButton btnConfirmar = new JFXButton("Sí");
        JFXButton btnCancelar = new JFXButton("No");

        // Configurar la acción de confirmar
        btnConfirmar.setOnAction(event -> {
            boolean decision = rgmodel.eliminarDocumentos(libro.getId_documento());
            if (decision) {
                // Mostrar diálogo de éxito
                JFXDialog dialogExito = new JFXDialog(base, dialogContent, JFXDialog.DialogTransition.CENTER);
                Label exitoMensaje = new Label("Los datos han sido eliminados correctamente.");
                dialogContent.getChildren().clear();
                dialogContent.getChildren().add(exitoMensaje);
                dialogExito.show();

                // Cerrar diálogo de confirmación
                dialogExito.setOnDialogClosed(e1 -> {
                    stmodel.loadpagevolverabuscar("buscar", viewp);
                });
            } else {
                // Mostrar diálogo de error
                JFXDialog dialogError = new JFXDialog(base, dialogContent, JFXDialog.DialogTransition.CENTER);
                Label errorMensaje = new Label("Los datos no han sido eliminados correctamente.");
                dialogContent.getChildren().clear();
                dialogContent.getChildren().add(errorMensaje);
                dialogError.show();
            }
        });

        // Configurar la acción de cancelar
       

        // Agregar botones al contenido del diálogo y mostrarlo
        dialogContent.getChildren().addAll(btnConfirmar, btnCancelar);
        JFXDialog dialog = new JFXDialog(base, dialogContent, JFXDialog.DialogTransition.CENTER);
        dialog.show();
         btnCancelar.setOnAction(event -> {
            // Cerrar el diálogo de confirmación sin hacer nada
            dialogContent.getChildren().clear();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void mostrardatos(Libro lib) {
        textid.setText(String.valueOf(lib.getId_documento()));
        texttitulo.setText(lib.getTitulo());
        textautor.setText(lib.getAutor());
        textedicion.setText(lib.getEdicion());
        textestante.setText(lib.getEstante());
        texteditorial.setText(lib.getEditorial());
        textcodlomo.setText(lib.getCod_lomo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        textfecha.setText(lib.getFecha_publicacion().format(formatter));
        try {
            imgportada.setImage(Libro.convertPdfBytesToImage(lib.getImg_portada()));
        } catch (IOException ex) {
            Logger.getLogger(VistaLibroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //De aqui pa abajo todo queda igual solo toca cambiar el objeto
    public void abrirDialogoImagen(String titulo, byte[] archivoPdf) {
        try {
            // Crear un StackPane para el diálogo
            StackPane stackPane = (StackPane) base.getScene().getRoot();

            // Crear el contenido del diálogo
            JFXDialogLayout contenido = new JFXDialogLayout();
            contenido.setHeading(new Text(titulo));
            contenido.setPrefWidth(1000);
            contenido.setPrefHeight(600);

            // Crear un ScrollPane para contener las imágenes
            ScrollPane scrollPane = new ScrollPane();
            VBox contenedorImagenes = new VBox(10);
            contenedorImagenes.setAlignment(Pos.CENTER); // Alinear contenido al centro
            scrollPane.setContent(contenedorImagenes);
            scrollPane.setFitToWidth(true);

            // Convertir el byte[] en un archivo PDF temporal
            File archivoTemporal = convertirByteArrayAFile(archivoPdf);

            // Cargar las páginas del PDF como imágenes
            registmodel modelo = new registmodel();
            modelo.cargarPaginasComoImagenesParaVistas(archivoTemporal, 1, contarPaginas(archivoTemporal), contenedorImagenes);

            // Configurar el ScrollPane
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Ocultar barra horizontal
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Mostrar barra vertical cuando sea necesario

            // Agregar el ScrollPane al contenido del diálogo
            contenido.setBody(scrollPane);

            // Crear y mostrar el JFXDialog
            JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
            dialog.show();

            // Limpiar archivo temporal después de cerrar el diálogo
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
        abrirDialogoImagen("Portada", libro.getImg_portada());
    }

    @FXML
    private void verresumen(MouseEvent e) {
        abrirDialogoImagen("Resumen", libro.getResumen());
    }

    @FXML
    private void verindice(MouseEvent e) {
        abrirDialogoImagen("Indice", libro.getIndice());
    }

    private String emailFrom = "bibliotecapsmcabimas@gmail.com";
    private String passwordFrom = "cdpspefajfwsyexb"; // Cambia por un método más seguro para almacenar contraseñas
// Cambia por un método más seguro para almacenar contraseñas

    @FXML
    private void mostrarDialogoCorreo(MouseEvent event) {
        // Crear el contenedor raíz del diálogo
        StackPane stackPane = (StackPane) base.getScene().getRoot();

        // Crear el contenido del diálogo
        JFXDialogLayout contenido = new JFXDialogLayout();
        contenido.setHeading(new Text("Enviar archivo por correo"));
        contenido.setPrefWidth(400);
        contenido.setPrefHeight(200);

        // Campo de texto para ingresar el correo electrónico
        JFXTextField campoCorreo = new JFXTextField();
        campoCorreo.setPromptText("Ingrese el correo electrónico");

        JFXTextField campoMensaje = new JFXTextField();
        campoMensaje.setPromptText("Ingrese un mensaje adicional (opcional)");

        // Mensaje de validación o resultado
        Label mensajeResultado = new Label();
        mensajeResultado.setVisible(false);

        // Botón para enviar
        JFXButton btnEnviar = new JFXButton("Enviar");
        btnEnviar.setOnAction(e -> {
            String correo = campoCorreo.getText();
            String mensaje = campoMensaje.getText();
            if (esCorreoValido(correo)) {
                // Llamar al método de envío de correo
                boolean enviado = enviarCorreo(correo, libro, mensaje); // 'libro' es tu objeto de tipo Libro

                if (enviado) {
                    mensajeResultado.setText("Correo enviado exitosamente");
                    mensajeResultado.setStyle("-fx-text-fill: green;");
                } else {
                    mensajeResultado.setText("Error al enviar el correo");
                    mensajeResultado.setStyle("-fx-text-fill: red;");
                }
                mensajeResultado.setVisible(true);
            } else {
                mensajeResultado.setText("Correo inválido. Verifique el formato.");
                mensajeResultado.setStyle("-fx-text-fill: red;");
                mensajeResultado.setVisible(true);
            }
        });

        // Contenedor para los botones
        VBox cuerpo = new VBox(10, campoCorreo, campoMensaje, mensajeResultado, btnEnviar);
        contenido.setBody(cuerpo);

        // Crear y mostrar el diálogo
        JFXDialog dialog = new JFXDialog(stackPane, contenido, JFXDialog.DialogTransition.CENTER);
        dialog.show();
    }

    private boolean enviarCorreo(String correoDestinatario, Libro libro, String msjpersonalizado) {
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
            mCorreo.setSubject("Información sobre el libro");

            // Creación del contenido del correo
            String contenido = "<html><body>"
                    + "<h2>Detalles del Libro</h2>"
                    + "<p>Título: " + libro.getTitulo() + "</p>"
                    + "<p>Autor: " + libro.getAutor() + "</p>" + "<p>Nota: " + msjpersonalizado + "</p>"
                    + "</body></html>";

            // Crear el cuerpo del mensaje
            MimeBodyPart cuerpo = new MimeBodyPart();
            cuerpo.setContent(contenido, "text/html; charset=ISO-8859-1");

            // Crear el archivo adjunto si existe
            MimeBodyPart adjunto = new MimeBodyPart();
            if (libro.getArchivopdf() != null && libro.getArchivopdf().length > 0) {
                DataSource fuenteDatos = new ByteArrayDataSource(libro.getArchivopdf(), "application/pdf");
                adjunto.setDataHandler(new DataHandler(fuenteDatos));
                adjunto.setFileName(libro.getTitulo() + ".pdf");
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

// Método para validar si el correo es válido
    private boolean esCorreoValido(String correo) {
        String patronCorreo = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,}$";
        return correo.matches(patronCorreo);
    }

    @FXML
    private void guardararchivo(MouseEvent e) {
        libro.guardarArchivo(e, libro);
    }

}
