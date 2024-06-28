package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sistemacoil.modelo.pojo.Coordinador;
import sistemacoil.utilidades.Escenas;

/**
 * FXML Controller class
 *
 */
public class FXMLPrincipalCoordinadorController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private AnchorPane apVentana;
    @FXML
    private Label lbCoordinador;
    
    private Coordinador coordinador;
    @FXML
    private Pane paneSuperior;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneSuperior.setOnMousePressed((MouseEvent event) -> {
            Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        paneSuperior.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
    }
    
    public void inicializarValores(Coordinador coordinador) {
        this.coordinador=coordinador;
        if (this.coordinador != null) {
            lbCoordinador.setText(this.coordinador.getApellidoPaterno() + "-" + this.coordinador.getApellidoMaterno() + " " + this.coordinador.getNombre());
            
        }
    }
    
    private void cargarEscena(String fxmlPath) {
    try {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlUrl = getClass().getResource(fxmlPath);
        if (fxmlUrl == null) {
            System.err.println("Archivo FXML no encontrado: " + fxmlPath);
            return;
        }
        loader.setLocation(fxmlUrl);
        AnchorPane pane = loader.load();
        apVentana.getChildren().clear();
        apVentana.getChildren().add(pane);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    @FXML
    private void btnClicRegistrarOfertaExterna(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLFormularioOfertaColaboracionExterna.fxml");
    }

    @FXML
    private void btnClicConsultarRegion(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLNumeraliaRegion.fxml");
    }

    @FXML
    private void btnClicConsultarArea(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLNumeraliaAreaA.fxml");
    }

    @FXML
    private void btnClicCerrarSesion(ActionEvent event) {
        try {
            Stage stage = (Stage) apVentana.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLIniciarSesion.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicHistorialColab(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLHistorialColab.fxml"));
            AnchorPane pane = loader.load();
            FXMLHistorialColabController controller = loader.getController();
            controller.inicializarCoordinador();
            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalCoordinadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnClicConsultarActividades(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLConsultarActividades.fxml");
    }

    @FXML
    private void btnClicRevisarRegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Escenas.SELECCIONAR_OFERTAS));
            AnchorPane pane = loader.load();
            FXMLSeleccionarOfertaController controller = loader.getController();
            controller.setEstado("revisarOferta");
            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalCoordinadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
