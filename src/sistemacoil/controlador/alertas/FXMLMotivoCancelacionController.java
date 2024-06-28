package sistemacoil.controlador.alertas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;

public class FXMLMotivoCancelacionController implements Initializable {

    private Stage stage;
    private boolean resultado;
    private String motivoCancelacion;

    @FXML
    private TextArea taMotivo;
    @FXML
    private Label lbLimiteCaracteres;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taMotivo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > Constantes.MAX_LONG_MOTIVO) {
                    taMotivo.setText(oldValue);
                    Animaciones.animarShake(taMotivo);
                }
                actualizarContador();
            }
        });
        actualizarContador();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public boolean isResultado() {
        return resultado;
    }

    private void actualizarContador() {
        int longitudActual = taMotivo.getText().length();
        lbLimiteCaracteres.setText(longitudActual + "/" + Constantes.MAX_LONG_MOTIVO);
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        resultado = false;
        stage.close();
    }

    @FXML
    private void btnClicAceptar(ActionEvent event) {
        if (taMotivo.getText().length() > 0) {
            motivoCancelacion = taMotivo.getText();
            resultado = true;
            stage.close();
        } else {
            Animaciones.animarShake(taMotivo);
        }
    }
}
