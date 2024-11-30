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
public class InformePasantia extends Documento {
    private String Empresa;
    private String Carrera;
    private String Cedula;

    public String getEmpresa() {
        return Empresa;
    }

    public String getCarrera() {
        return Carrera;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }
    
    

    public InformePasantia(String Empresa, String Carrera, String Cedula, int id_documento, String Titulo, String Tipo, String Autor, LocalDate Fecha_publicacion, byte[] Resumen, byte[] Indice, byte[] Img_portada, byte[] Archivopdf) {
        super(id_documento, Titulo, Tipo, Autor, Fecha_publicacion, Resumen, Indice, Img_portada, Archivopdf);
        this.Empresa = Empresa;
        this.Carrera = Carrera;
        this.Cedula = Cedula;
    }
    
    public InformePasantia(){}
    
    
}
