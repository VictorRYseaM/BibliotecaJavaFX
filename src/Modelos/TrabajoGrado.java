/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.util.Date;

/**
 *
 * @author VictorY
 */
public class TrabajoGrado extends Documento {
    private String Carrera;
    private String Codigo;
    private String Cedula;

    public TrabajoGrado(String Carrera, String Codigo, String Cedula, int id_documento, String Titulo, String Tipo, String Autor, Date Fecha_publicacion, byte[] Resumen, byte[] Indice, byte[] Img_portada, byte[] Archivopdf) {
        super(id_documento, Titulo, Tipo, Autor, Fecha_publicacion, Resumen, Indice, Img_portada, Archivopdf);
        this.Carrera = Carrera;
        this.Codigo = Codigo;
        this.Cedula = Cedula;
    }

    /**
     * @return the Carrera
     */
    public String getCarrera() {
        return Carrera;
    }

    /**
     * @param Carrera the Carrera to set
     */
    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    /**
     * @return the Codigo
     */
    public String getCodigo() {
        return Codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * @return the Cedula
     */
    public String getCedula() {
        return Cedula;
    }

    /**
     * @param Cedula the Cedula to set
     */
    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }
}
