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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.utilidades.Escenas;

public class FXMLPrincipalProfesorUVController implements Initializable {

    private ProfesorUV profesorUV;
    @FXML
    private BorderPane bpPrincipal;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private Label lbProfesorUV;
    @FXML
    private Pane paneSuperior;

    private double xOffset = 0;
    private double yOffset = 0;

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

    public void inicializarValores(ProfesorUV profesorUV) {
        this.profesorUV = profesorUV;
        if (this.profesorUV != null) {
            lbProfesorUV.setText(this.profesorUV.getApellidoPaterno() + "-" + this.profesorUV.getApellidoMaterno() + " " + this.profesorUV.getNombre());
            cargarEscena(Escenas.INICIO, null);
        }
    }

    public void setBorderPanePrincipal(BorderPane bpPrincipal) {
        this.bpPrincipal = bpPrincipal;
    }

    private void cargarEscena(String fxmlPath, String estado) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                throw new IOException("No se pudo encontrar el archivo FXML en la ruta: " + fxmlPath);
            }
            loader.setLocation(fxmlUrl);
            AnchorPane pane = loader.load();

            if (fxmlPath.contains("FXMLFormularioOfertaColaboracionUV.fxml")) {
                FXMLFormularioOfertaColaboracionUVController controller = loader.getController();
                controller.setIdProfesorUV(profesorUV.getIdProfesorUV());
            } else if (fxmlPath.contains("FXMLRegistrarColaboracion.fxml")) {
                FXMLRegistrarColaboracionController controller = loader.getController();
                controller.setProfesorUV(profesorUV);
            } else if (fxmlPath.contains("FXMLSeleccionarColaboracion.fxml")) {
                FXMLSeleccionarColaboracionController controller = loader.getController();
                controller.setIdProfesorUV(profesorUV.getIdProfesorUV());
                if (estado != null) {
                    controller.setEstado(estado);
                }
            } else if (fxmlPath.contains("FXMLSeleccionarOferta.fxml")) {
                FXMLSeleccionarOfertaController controller = loader.getController();
                controller.setIdProfesorUV(profesorUV.getIdProfesorUV());
            }

            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicPrincipal(ActionEvent event) {
    }
    
    @FXML
    private void btnClicRegistrarOfertaUV(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLFormularioOfertaColaboracionUV.fxml", null);
    }

    @FXML
    private void btnClicRegistrarColaboracion(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLRegistrarColaboracion.fxml", null);
    }
    
    @FXML
    private void btnClicMisColaboraciones(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLSeleccionarColaboracion.fxml", null);
    }
    
    @FXML
    private void btnClicCancelarColaboracion(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLSeleccionarColaboracion.fxml", "cancelarColab");
    }
    
    @FXML
    private void btnClicModificarOfertaUV(ActionEvent event) {
        cargarEscena("/sistemacoil/vista/FXMLSeleccionarOferta.fxml", null);
    }

    @FXML
    private void btnClicHistorialColaboracion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLHistorialColab.fxml"));
            AnchorPane pane = loader.load();
            FXMLHistorialColabController controller = loader.getController();
            controller.inicializarProfesorUV(profesorUV.getIdProfesorUV());
            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalProfesorUVController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void btnClicCerrar(ActionEvent event) {
        Stage stage = (Stage) apVentana.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnClicMinimizar(ActionEvent event) {
        Stage stage = (Stage) apVentana.getScene().getWindow();
        stage.setIconified(true);
    }
}
