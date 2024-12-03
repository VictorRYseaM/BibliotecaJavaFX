/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;

/**
 *
 * @author VictorY
 */
public class DocumentoDepuracion {
    private int idDocumento;
    private String titulo;
    private LocalDate fechaPublicacion;
    private String tipo;
    private int aniosVejez; // Columna calculada

    public DocumentoDepuracion(int idDocumento, String titulo, LocalDate fechaPublicacion, String tipo, int aniosVejez) {
        this.idDocumento = idDocumento;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.tipo = tipo;
        this.aniosVejez = aniosVejez;
    }

    // Getters y Setters
    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAniosVejez() {
        return aniosVejez;
    }

    public void setAniosVejez(int aniosVejez) {
        this.aniosVejez = aniosVejez;
    }
}
