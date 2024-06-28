package sistemacoil.controlador.alertas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * autor: jafet
 */
public class FXMLAlertaSimpleController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbIndicacion;
    @FXML
    private Button btnAceptar;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setTituloYMensaje(String titulo, String mensaje) {
        lbTitulo.setText(titulo);
        lbIndicacion.setText(ajustarTexto(mensaje));
    }

    private String ajustarTexto(String text) {
        final int MAX_LENGTH = 50;
        StringBuilder adjustedText = new StringBuilder(text);
        int index = 0;
        while (index + MAX_LENGTH < adjustedText.length() && (index = adjustedText.lastIndexOf(" ", index + MAX_LENGTH)) != -1) {
            adjustedText.replace(index, index + 1, "\n");
        }
        return adjustedText.toString();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    private void btnClicAceptar(ActionEvent event) {
        if (stage != null) {
            stage.close();
        }
    }
}
