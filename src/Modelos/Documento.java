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
public class Documento {
    private int id_documento;
    private String Titulo;
    private String Tipo;
    private String Autor;
    private Date Fecha_publicacion;
    private byte[] Resumen;
    private byte[] Indice;
    private byte[] Img_portada;
    private byte[] Archivopdf;

    public Documento(int id_documento, String Titulo, String Tipo, String Autor, Date Fecha_publicacion, byte[] Resumen, byte[] Indice, byte[] Img_portada, byte[] Archivopdf) {
        this.id_documento = id_documento;
        this.Titulo = Titulo;
        this.Tipo = Tipo;
        this.Autor = Autor;
        this.Fecha_publicacion = Fecha_publicacion;
        this.Resumen = Resumen;
        this.Indice = Indice;
        this.Img_portada = Img_portada;
        this.Archivopdf = Archivopdf;
    }
    
    public Documento(int id_documento, String Titulo, String Tipo, String Autor, byte[] Img_portada){
        this.id_documento=id_documento;
        this.Titulo=Titulo;
        this.Tipo=Tipo;
        this.Autor=Autor;
        this.Img_portada = Img_portada;
        
    }

    public Documento() {
    }
    
    public void getdatos(){
        System.out.println("\n"+id_documento);
        System.out.println(Titulo);
        System.out.println(Tipo);
        System.out.println(Autor);
        System.out.println("---------------------");
    }
    
    
    /**
     * @return the id_documento
     */
    public int getId_documento() {
        return id_documento;
    }

    /**
     * @param id_documento the id_documento to set
     */
    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    /**
     * @return the Titulo
     */
    public String getTitulo() {
        return Titulo;
    }

    /**
     * @param Titulo the Titulo to set
     */
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the Autor
     */
    public String getAutor() {
        return Autor;
    }

    /**
     * @param Autor the Autor to set
     */
    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    /**
     * @return the Fecha_publicacion
     */
    public Date getFecha_publicacion() {
        return Fecha_publicacion;
    }

    /**
     * @param Fecha_publicacion the Fecha_publicacion to set
     */
    public void setFecha_publicacion(Date Fecha_publicacion) {
        this.Fecha_publicacion = Fecha_publicacion;
    }

    /**
     * @return the Resumen
     */
    public byte[] getResumen() {
        return Resumen;
    }

    /**
     * @param Resumen the Resumen to set
     */
    public void setResumen(byte[] Resumen) {
        this.Resumen = Resumen;
    }

    /**
     * @return the Indice
     */
    public byte[] getIndice() {
        return Indice;
    }

    /**
     * @param Indice the Indice to set
     */
    public void setIndice(byte[] Indice) {
        this.Indice = Indice;
    }

    /**
     * @return the Img_portada
     */
    public byte[] getImg_portada() {
        return Img_portada;
    }

    /**
     * @param Img_portada the Img_portada to set
     */
    public void setImg_portada(byte[] Img_portada) {
        this.Img_portada = Img_portada;
    }

    /**
     * @return the Archivopdf
     */
    public byte[] getArchivopdf() {
        return Archivopdf;
    }

    /**
     * @param Archivopdf the Archivopdf to set
     */
    public void setArchivopdf(byte[] Archivopdf) {
        this.Archivopdf = Archivopdf;
    }
}
