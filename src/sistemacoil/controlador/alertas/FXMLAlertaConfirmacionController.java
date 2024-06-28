package sistemacoil.controlador.alertas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLAlertaConfirmacionController implements Initializable {
    
    private Stage stage;
    private boolean resultado;

    @FXML
    private Button btnSecundario;
    @FXML
    private Button btnPrimario;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbIndicacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setTituloYMensaje(String titulo, String mensaje) {
        lbTitulo.setText(titulo);
        lbIndicacion.setText(mensaje);
    }

    public void setTextoBotones(String textoBtnPrimario, String textoBtnSecundario) {
        btnPrimario.setText(textoBtnPrimario);
        btnSecundario.setText(textoBtnSecundario);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean getResultado() {
        return resultado;
    }
    
    @FXML
    private void btnClicSecundario(ActionEvent event) {
        resultado = false;
        stage.close();
    }

    @FXML
    private void btnClicPrimario(ActionEvent event) {
        resultado = true;
        stage.close();
    }
}
