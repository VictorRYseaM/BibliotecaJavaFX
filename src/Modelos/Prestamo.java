/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author VictorY
 */
public class Prestamo {

    private int idPrestamo;
    private int idDocumento;
    private StringProperty fechaHora = new SimpleStringProperty();
    private StringProperty titulo = new SimpleStringProperty();
    private StringProperty tipo = new SimpleStringProperty();
    private StringProperty cedula = new SimpleStringProperty();
    private int estado;
    private StringProperty estadoTexto = new SimpleStringProperty();

    // Constructor
    public Prestamo(int idPrestamo, int idDocumento, String titulo, String tipo, String cedula, String fechaPrestamo, int estado) {
        this.idPrestamo = idPrestamo;
        this.idDocumento = idDocumento;
        this.titulo.set(titulo);
        this.tipo.set(tipo);
        this.fechaHora.set(fechaPrestamo);
        this.estado = estado;
        this.cedula.set(cedula);
        setEstado(estado); // Esto establecerá `estadoTexto` correctamente
    }

    // Getters y setters
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getFechaHora() {
        return fechaHora.get();
    }

    public void setFechaHora(String fechaPrestamo) {
        this.fechaHora.set(fechaPrestamo);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getEstadoTexto() {
        return estadoTexto.get();
    }

    public void setEstado(int estado) {
        this.estado = estado;
        // Asignar el valor de estadoTexto basado en el estado numérico
        this.estadoTexto.set(estado == 0 ? "Pendiente" : "Completo");
    }

    public int getEstado() {
        return estado;
    }

    public void setEstadoTexto(String estadoTexto) {
        this.estadoTexto.set(estadoTexto);
    }
}
