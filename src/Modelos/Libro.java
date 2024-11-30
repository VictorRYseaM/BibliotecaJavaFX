/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author VictorY
 */
public class Libro extends Documento{
    private String Editorial;
    private String Edicion;

    public String getEditorial() {
        return Editorial;
    }

    public String getEdicion() {
        return Edicion;
    }

    public String getEstante() {
        return Estante;
    }

    public String getCod_lomo() {
        return Cod_lomo;
    }
    private String Estante;
    private String Cod_lomo;

    public Libro(String Editorial, String Edicion, String Estante, String Cod_lomo, int id_documento, String Titulo, String Tipo, String Autor, LocalDate Fecha_publicacion, byte[] Resumen, byte[] Indice, byte[] Img_portada, byte[] Archivopdf) {
        super(id_documento, Titulo, Tipo, Autor, Fecha_publicacion, Resumen, Indice, Img_portada, Archivopdf);
        this.Editorial = Editorial;
        this.Edicion = Edicion;
        this.Estante = Estante;
        this.Cod_lomo = Cod_lomo;
    }
    
    public Libro(){
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

    public void setEdicion(String Edicion) {
        this.Edicion = Edicion;
    }

    public void setEstante(String Estante) {
        this.Estante = Estante;
    }

    public void setCod_lomo(String Cod_lomo) {
        this.Cod_lomo = Cod_lomo;
    }
    
    
}
