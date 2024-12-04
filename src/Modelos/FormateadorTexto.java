/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author VictorY
 */
public class FormateadorTexto {

    public static void configurarFormatoCedula(JFXTextField textField) {
        // Asegurar que el campo empiece siempre con "V-"
        textField.setText("V-");

        // Limitar el n�mero m�ximo de caracteres a 10
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String textoNuevo = cambio.getControlNewText();

            // Limitar a 10 caracteres
            if (textoNuevo.length() > 10) {
                return null;
            }

            // Validar que despu�s de "V-" solo haya n�meros
            if (!textoNuevo.matches("V-\\d*")) {
                return null;
            }

            return cambio;
        };

        // Crear el formateador con el filtro
        TextFormatter<String> formateador = new TextFormatter<>(filtro);

        // Asignar el formateador al campo de texto
        textField.setTextFormatter(formateador);

        // Prevenir que el usuario borre el prefijo "V-"
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() < 2) {
                textField.setText("V-");
                textField.positionCaret(2); // Coloca el cursor despu�s del "V-"
            }
        });
    }

    public static void limitarCantidadCaracteres(JFXTextField textField, int limite) {
        // Crear un filtro para limitar la cantidad de caracteres
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String textoNuevo = cambio.getControlNewText();

            // Verificar si el nuevo texto excede el l�mite
            if (textoNuevo.length() > limite) {
                return null; // Rechazar el cambio si supera el l�mite
            }

            return cambio; // Aceptar el cambio
        };

        // Asignar el filtro al TextFormatter
        TextFormatter<String> formateador = new TextFormatter<>(filtro);
        textField.setTextFormatter(formateador);
    }
    public static void limitarCantidadCaracteresPass(JFXPasswordField textField, int limite) {
        // Crear un filtro para limitar la cantidad de caracteres
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String textoNuevo = cambio.getControlNewText();

            // Verificar si el nuevo texto excede el l�mite
            if (textoNuevo.length() > limite) {
                return null; // Rechazar el cambio si supera el l�mite
            }

            return cambio; // Aceptar el cambio
        };

        // Asignar el filtro al TextFormatter
        TextFormatter<String> formateador = new TextFormatter<>(filtro);
        textField.setTextFormatter(formateador);
    }

    public static void aplicarMascaraEntrada(JFXTextField textField, int limite) {
        // Texto inicial del ejemplo como m�scara
        String mascara = "Ejemplo: XXXXXXXX, XXXXXXXX, XXXXXXXX...";

        // Colocar el promptText inicial
        textField.setPromptText(mascara);

        // Crear un filtro para validar entrada
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String textoNuevo = cambio.getControlNewText();

            // Verificar si el texto excede el l�mite de caracteres
            if (textoNuevo.length() > limite) {
                return null; // Rechazar el cambio
            }

            // Validar que solo se permitan n�meros, comas y espacios
            if (!textoNuevo.matches("[0-9, ]*")) {
                return null; // Rechazar caracteres no permitidos
            }

            return cambio; // Aceptar el cambio
        };

        // Asignar el filtro al TextFormatter
        TextFormatter<String> formateador = new TextFormatter<>(filtro);
        textField.setTextFormatter(formateador);

        // Actualizar din�micamente el formato del texto
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Mantener la m�scara si est� vac�o
                textField.setPromptText(mascara);
            } else {
                // Mostrar solo los valores ingresados
                textField.setPromptText("");
            }
        });
    }

    public static void limitarNumerosYPuntos(JFXTextField textField, int limite) {
        // Establecer el promptText como referencia
        textField.setPromptText("Ejemplo: 2.3");

        // Crear un filtro para validar la entrada
        UnaryOperator<TextFormatter.Change> filtro = cambio -> {
            String textoNuevo = cambio.getControlNewText();

            // Verificar si el texto excede el l�mite de caracteres
            if (textoNuevo.length() > limite) {
                return null; // Rechazar el cambio
            }

            // Validar que solo se permitan n�meros y puntos
            if (!textoNuevo.matches("[0-9.]*")) {
                return null; // Rechazar caracteres no permitidos
            }

            // Asegurarse de que solo haya un punto en el texto
            if (textoNuevo.chars().filter(ch -> ch == '.').count() > 1) {
                return null; // Rechazar si hay m�s de un punto
            }

            return cambio; // Aceptar el cambio
        };

        // Asignar el filtro al TextFormatter
        TextFormatter<String> formateador = new TextFormatter<>(filtro);
        textField.setTextFormatter(formateador);
    }
}
