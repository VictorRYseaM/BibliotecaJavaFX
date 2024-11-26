/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author VictorY
 */
public class Buscarmodel {

    public Buscarmodel() {
    }
    // Método para obtener los autores de la base de datos

    public ObservableList<String> obtenerAutores() {
        ObservableList<String> autores = FXCollections.observableArrayList();

        // Consulta SQL para obtener los autores de los documentos donde el Tipo sea 'Libros'
        String query = "SELECT Autor FROM documento WHERE Tipo = 'Libros'";

        try (java.sql.Connection con = new Conexion().conectar(); // Asegúrate de que la conexión sea exitosa
                 Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Verificar si la consulta ha devuelto algún resultado
            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron autores.");
                return autores;  // Regresa la lista vacía si no hay resultados
            }

            // Procesar los resultados de la consulta
            while (rs.next()) {
                String autor = rs.getString("Autor"); // Recupera el nombre del autor
                if (autor != null && !autor.trim().isEmpty()) {
                    autores.add(autor); // Solo agrega autores no nulos o vacíos
                }
            }

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores válidos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Agregar detalles de la excepción para depurar mejor el problema
            System.out.println("Error al obtener los autores: " + e.getMessage());
        }

        // Devuelve la lista de autores (puede estar vacía si no hay resultados)
        return autores;
    }

    // Método para obtener las editoriales de la base de datos
    public ObservableList<String> obtenerEditoriales() {
        ObservableList<String> editoriales = FXCollections.observableArrayList();

        String query = "SELECT Editorial FROM libro";

        try (java.sql.Connection con = new Conexion().conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String editorial = rs.getString("Editorial");
                editoriales.add(editorial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return editoriales;
    }
 
}
