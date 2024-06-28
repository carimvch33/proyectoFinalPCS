package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sistemacoil.controlador.alertas.FXMLMotivoCancelacionController;
import sistemacoil.modelo.dao.OfertaColaboracionUVDAO;
import sistemacoil.modelo.pojo.OfertaColaboracionUV;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;
import static sistemacoil.utilidades.Utils.mostrarAlertaConfirmacion;

public class FXMLDetallesOfertaController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfIdioma;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfAreaAcademica;
    @FXML
    private TextField tfDependencia;
    @FXML
    private TextField tfProgramaEducativo;
    @FXML
    private TextField tfExperiencia;
    @FXML
    private TextArea taObjetivoCurso;
    @FXML
    private TextArea taPerfilEstudiantes;
    @FXML
    private TextArea taTemasInteres;
    @FXML
    private TextArea taInformacionAdicional;
    @FXML
    private Label lbTituloFormulario;
    @FXML
    private Button btnRegresar;
    @FXML
    private AnchorPane apVentana;

    private OfertaColaboracionUV ofertaColaboracionUV;
    @FXML
    private Button btnRevisar;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorIdioma;
    @FXML
    private Label lbErrorExperienciaEducativa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setOfertaColaboracion(OfertaColaboracionUV oferta) {
        this.ofertaColaboracionUV = oferta;
        tfNombre.setText(oferta.getNombreColaboracion());
        tfIdioma.setText(oferta.getIdioma());
        tfPeriodo.setText(oferta.getPeriodo());
        tfAreaAcademica.setText(oferta.getAreaAcademica());
        tfDependencia.setText(oferta.getDependencia());
        tfProgramaEducativo.setText(String.valueOf(oferta.getIdProgramaEducativo()));
        tfExperiencia.setText(oferta.getExperienciaEducativa());
        taObjetivoCurso.setText(oferta.getObjetivoCurso());
        taPerfilEstudiantes.setText(oferta.getPerfilEstudiante());
        taTemasInteres.setText(oferta.getTemaInteres());
        taInformacionAdicional.setText(oferta.getInformacionAdicional());
        lbTituloFormulario.setText("Detalles de la Oferta de Colaboración");
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        // Lógica para manejar el evento de regresar
    }

    private void mostrarMotivoCancelacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/alertas/FXMLMotivoCancelacion.fxml"));
            AnchorPane root = loader.load();

            FXMLMotivoCancelacionController controller = loader.getController();
            Stage stage = new Stage();
            controller.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.getScene().setFill(null);
            stage.centerOnScreen();

            stage.showAndWait();

            if (controller.isResultado()) {
                String motivoCancelacion = controller.getMotivoCancelacion();
                int idOferta = ofertaColaboracionUV.getIdOfertaColaboracionUV();
                HashMap<String, Object> resultado = OfertaColaboracionUVDAO.rechazarOfertaColaboracionUV(idOferta);
                if (!(Boolean) resultado.get(Constantes.KEY_ERROR)) {
                    Utils.mostrarAlertaSimple("Cancelación exitosa", "La colaboración ha sido cancelada exitosamente.", apVentana);
                    regresarVentana("/sistemacoil/vista/FXMLSeleccionarColaboracion.fxml", "cancelarColab");
                } else {
                    Utils.mostrarAlertaSimple("Error", (String) resultado.get(Constantes.KEY_MENSAJE), apVentana);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void regresarVentana(String rutaFXML, String estado) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource(rutaFXML);
            loader.setLocation(fxmlUrl);
            AnchorPane pane = loader.load();

            FXMLSeleccionarColaboracionController controller = loader.getController();
            controller.setIdProfesorUV(this.ofertaColaboracionUV.getIdProfesorUV());
            controller.setEstado(estado);

            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicRevisar(ActionEvent event) {
        boolean confirmado = mostrarAlertaConfirmacion("Revisar oferta", "¿Qué acción deseas realizar con esta oferta"
                + " de colaboración?", "Aceptar", "Rechazar", apVentana);
        if (!confirmado) {
            mostrarMotivoCancelacion();
        } else {
            int idOferta = this.ofertaColaboracionUV.getIdOfertaColaboracionUV();
            HashMap<String, Object> resultado = OfertaColaboracionUVDAO.aceptarOfertaColaboracionUV(idOferta);
            if (!(Boolean) resultado.get(Constantes.KEY_ERROR)) {
                Utils.mostrarAlertaSimple("Oferta Aceptada", "La oferta de colaboración ha sido aceptada", apVentana);
                // Cargar o actualizar las ofertas si es necesario
            } else {
                Utils.mostrarAlertaSimple("Error", (String) resultado.get(Constantes.KEY_MENSAJE), apVentana);
            }
        }
    }
}
