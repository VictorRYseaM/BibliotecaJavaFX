/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.sql.*;
import javafx.scene.control.Alert;

/**
 *
 * @author VictorY
 */
public class Usuario {

    public void actualizarUsuario(JFXTextField usernameField1, JFXTextField usernameField2,
            JFXPasswordField passwordField1, JFXPasswordField passwordField2,
            JFXTextField preguntaField, JFXTextField respuestaField) {
        // Obtener los valores de los campos
        String username1 = usernameField1.getText().trim();
        String username2 = usernameField2.getText().trim();
        String password1 = passwordField1.getText().trim();
        String password2 = passwordField2.getText().trim();
        String pregunta = preguntaField.getText().trim();
        String respuesta = respuestaField.getText().trim();

        // Validaciones
        if (username1.isEmpty() || username2.isEmpty() || password1.isEmpty() || password2.isEmpty()
                || pregunta.isEmpty() || respuesta.isEmpty()) {
            mostrarError("Error", "Todos los campos son obligatorios.");
            return;
        } else if (!username1.equals(username2)) {
            mostrarError("Error", "Los nombres de usuario no coinciden.");
            return;
        } else if (!password1.equals(password2)) {
            mostrarError("Error", "Las contraseñas no coinciden.");
            return;
        }else if(password1.length()<6){
            mostrarError("Error", "La contraseña debe tener minimo 6 caracteres.");
           
        }

        // Actualizar la tabla en la base de datos
        String sql = "UPDATE user SET username = ?, password = ?, pregunta = ?, respuesta = ? WHERE id_user = 1";

        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username1);
            pst.setString(2, password1);
            pst.setString(3, pregunta);
            pst.setString(4, respuesta);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                mostrarAlerta("Éxito", "Usuario actualizado correctamente.");
                usernameField1.clear();
                usernameField2.clear();
                passwordField1.clear();
                passwordField2.clear();
                preguntaField.clear();
                respuestaField.clear();
            } else {
                mostrarError("Error", "No se pudo actualizar el usuario.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }
    public boolean actualizarUsuario(JFXTextField usernameField, JFXPasswordField passwordField1, JFXPasswordField passwordField2) {
        // Obtener los valores de los campos
        String username1 = usernameField.getText().trim();
        String password1 = passwordField1.getText().trim();
        String password2 = passwordField2.getText().trim();

        // Validaciones
        if (username1.isEmpty()  || password1.isEmpty() || password2.isEmpty()) {
            mostrarError("Error", "Todos los campos son obligatorios.");
            return false;
        }else if (!password1.equals(password2)) {
            mostrarError("Error", "Las contraseñas no coinciden.");
            passwordField1.clear();
            passwordField2.clear();
            return false;
        }else if(password1.length()<6){
            mostrarError("Error", "La contraseña debe tener minimo 6 caracteres.");
            passwordField1.clear();
            passwordField2.clear();
            return false;
        }

        // Actualizar la tabla en la base de datos
        String sql = "UPDATE user SET username = ?, password = ? WHERE id_user = 1";

        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username1);
            pst.setString(2, password1);
          

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                mostrarAlerta("Éxito", "Usuario actualizado correctamente.");
                usernameField.clear();
                passwordField1.clear();
                passwordField2.clear();
                return true;
            } else {
                mostrarError("Error", "No se pudo actualizar el usuario.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
        return false;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setpregunta(JFXTextField pregunta) {
        String sql = "SELECT pregunta FROM user WHERE id_user=1";
        String preg = "";
        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                preg = rs.getString("pregunta");
                
            }
            pregunta.setText(preg);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error retomar la informacion de la pregunta: " + e.getMessage());
        }
    }

    public boolean verificarRespuesta(JFXTextField respuestaField) {
        String sql = "SELECT respuesta FROM user WHERE id_user=1"; // Asume que solo hay un usuario
        String respuestaDB = "";

        try (java.sql.Connection con = new Conexion().conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                respuestaDB = rs.getString("respuesta");
            }

            // Compara la respuesta de la base de datos con el texto del TextField
            return respuestaDB.equals(respuestaField.getText());

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al verificar la respuesta: " + e.getMessage());
        }

        return false; // Retorna false si ocurre un error
    }

}
