package sistemacoil.utilidades;

import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sistemacoil.modelo.pojo.Periodo;

/**
 *
 * @author jafet
 */
public class Validaciones {
    
    public static boolean validarMatricula(TextField textField, Label lbError) {
        String matricula = textField.getText().trim();
        String msjError = null;

        if (matricula.isEmpty())
            msjError = Constantes.ERROR_CAMPOS_VACIOS;
        else if (!matricula.matches("S\\d{8}"))
            msjError = Constantes.ERROR_FORMATO_MATRICULA;

        if (msjError != null) {
            lbError.setText(msjError);
            Animaciones.animarShake(textField);
            return false;
        }

        lbError.setText("");
        textField.setText(matricula);
        return true;
    }
    
    public static boolean validarCampo(TextField textField, Label lbError) {
        String apellidos = textField.getText().trim();
        String msjError = null;

        if (apellidos.isEmpty())
            msjError = Constantes.ERROR_CAMPOS_VACIOS;

        if (msjError != null) {
            lbError.setText(msjError);
            Animaciones.animarShake(textField);
            return false;
        }

        lbError.setText("");
        return true;
    }

    public static boolean validarCorreo(TextField textField, Label lbError) {
        String email = textField.getText().trim();
        String msjError = null;

        if (email.isEmpty())
            msjError = Constantes.ERROR_CAMPOS_VACIOS;
        else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
            msjError = Constantes.ERROR_FORMATO_CORREO;

        if (msjError != null) {
            lbError.setText(msjError);
            Animaciones.animarShake(textField);
            return false;
        }

        lbError.setText("");
        return true;
    }
    
    public static boolean validarComboBox(ComboBox<?> comboBox, Label lbError) {
        String msjError = (comboBox == null || comboBox.getValue() == null) ? Constantes.ERROR_CB_VACIO : null;

        if (msjError != null) {
            lbError.setText(msjError);
            Animaciones.animarShake(comboBox);
            return false;
        }

        lbError.setText("");
        return true;
    }
    
    public static boolean validarFecha(DatePicker datePicker, Label lbError, Periodo periodoSeleccionado) {
        String msjError = null;
        LocalDate fecha = datePicker.getValue();

        if (fecha == null)
            msjError = Constantes.ERROR_CAMPOS_VACIOS;
        else if (fecha.isBefore(periodoSeleccionado.getInicioPeriodo()) || fecha.isAfter(periodoSeleccionado.getFinPeriodo()))
            msjError = Constantes.ERROR_FECHA_FUERA_RANGO;

        if (msjError != null) {
            lbError.setText(msjError);
            Animaciones.animarShake(datePicker);
            return false;
        }

        lbError.setText("");
        return true;
    }
    
    public static boolean validarTextArea(TextArea textArea, Label lbError) {
        String texto = textArea.getText().trim();
        String msjError = null;

        if (texto.isEmpty()) {
            msjError = Constantes.ERROR_CAMPOS_VACIOS;
        }

        if (msjError != null) {
            lbError.setText(msjError);
            Animaciones.animarShake(textArea);
            return false;
        }

        lbError.setText("");
        return true;
    }
}
