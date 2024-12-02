/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

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
}
