/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelos.StageMovement;
import Modelos.Ajustesmodel;
import Modelos.Documento;
import Modelos.Libro;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private JFXButton btnguarcambios;
    @FXML
    private AnchorPane viewpane1;

    @FXML
    private ImageView imgportada;
    @FXML
    private JFXButton btnvolver;
    @FXML
    private JFXTextField textid;

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
    private JFXTextField textfecha;

    private Libro libro;

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
}
