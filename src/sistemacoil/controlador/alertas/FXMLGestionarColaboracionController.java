package sistemacoil.controlador.alertas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class FXMLGestionarColaboracionController implements Initializable {

    private Stage stage;
    private int opcionSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    @FXML
    private void btnClicConcluir(ActionEvent event) {
        opcionSeleccionada = 1;
        stage.close();
    }

    @FXML
    private void btnClicSubirEvidencias(ActionEvent event) {
        opcionSeleccionada = 2;
        stage.close();
    }

    @FXML
    private void btnClicCerrar(ActionEvent event) {
        opcionSeleccionada = 0;
        stage.close();
    }

    @FXML
    private void btnClicEliminarEstudiantes(ActionEvent event) {
        opcionSeleccionada = 3;
        stage.close();
    }
}
