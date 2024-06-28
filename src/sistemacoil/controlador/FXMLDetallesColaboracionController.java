package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import sistemacoil.controlador.alertas.FXMLMotivoCancelacionController;
import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Escenas;
import sistemacoil.utilidades.Utils;

public class FXMLDetallesColaboracionController implements Initializable {

    private int idColaboracion;
    private int idProfesorUV;
    private boolean cancelarColab;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfProfesorUV;
    @FXML
    private TextField tfProfesorExterno;
    @FXML
    private TextArea taObjetivosCurso;
    @FXML
    private TextArea taPerfilEstudiantes;
    @FXML
    private TextArea taInfoAdicional;
    @FXML
    private TextArea taTemasInteres;
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
    private TextField tfExperienciaEducativa;
    @FXML
    private TextField tfFechaInicio;
    @FXML
    private TextField tfFechaConclusion;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnConcluir;
    @FXML
    private AnchorPane apVentana;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelarColab = false;
    }

    public void setIdColaboracion(int idColaboracion) {
        this.idColaboracion = idColaboracion;
        cargarDetallesColaboracion();
    }

    public void setIdProfesorUV(int idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }

    public void setCancelarColab(boolean cancelarColab) {
        this.cancelarColab = cancelarColab;
        updateButtonConcluirText();
    }

    private void updateButtonConcluirText() {
        if (cancelarColab) {
            btnConcluir.setText("Cancelar Colaboración");
        } else {
            btnConcluir.setText("Concluir Colaboración");
        }
    }

    private void cargarDetallesColaboracion() {
        Colaboracion colaboracion = ColaboracionDAO.obtenerDetallesColaboracion(idColaboracion);
        if (colaboracion != null) {
            tfNombre.setText(colaboracion.getNombreColaboracion());
            tfIdioma.setText(colaboracion.getIdioma());
            tfPeriodo.setText(colaboracion.getPeriodo());
            tfFechaInicio.setText(colaboracion.getFechaInicio().format(Constantes.FORMATO_FECHAS));
            tfFechaConclusion.setText(colaboracion.getFechaConclusion().format(Constantes.FORMATO_FECHAS));
            tfAreaAcademica.setText(colaboracion.getAreaAcademica());
            tfDependencia.setText(colaboracion.getDependencia());
            tfProgramaEducativo.setText(colaboracion.getProgramaEducativo());
            tfExperienciaEducativa.setText(colaboracion.getExperienciaEducativa());
            tfProfesorUV.setText(colaboracion.getProfesorUV());
            tfProfesorExterno.setText(colaboracion.getNombreProfesorExterno());
            taObjetivosCurso.setText(colaboracion.getObjetivoCurso());
            taPerfilEstudiantes.setText(colaboracion.getPerfilEstudiante());
            taTemasInteres.setText(colaboracion.getTemaInteres());
            taInfoAdicional.setText(colaboracion.getInformacionAdicional());
        }
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
                boolean exito = ColaboracionDAO.cancelarColaboracion(idColaboracion, motivoCancelacion);
                if (exito) {
                    Utils.mostrarAlertaSimple("Cancelación exitosa", "La colaboración ha sido cancelada exitosamente.", apVentana);
                    regresarVentana("/sistemacoil/vista/FXMLSeleccionarColaboracion.fxml", "cancelarColab");
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
            controller.setIdProfesorUV(this.idProfesorUV);
            controller.setEstado(estado);

            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        boolean confirmacion = Utils.mostrarAlertaConfirmacion(Constantes.TITULO_SALIR,
                Constantes.INDICACION_SALIR, Constantes.CANCELAR_OPERACION, Constantes.CONTINUAR_OPERACION, apVentana);
        if (!confirmacion) {
            regresarVentana(Escenas.SELECCIONAR_COLABORACION, "cancelarColab");
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        if (cancelarColab) {
            regresarVentana("/sistemacoil/vista/FXMLSeleccionarColaboracion.fxml", "cancelarColab");
        } else {
            boolean confirmacion = Utils.mostrarAlertaConfirmacion(Constantes.TITULO_SALIR,
                    Constantes.INDICACION_SALIR, Constantes.CONTINUAR_OPERACION, Constantes.CANCELAR_OPERACION, apVentana);
            if (!confirmacion) {
                regresarVentana(Escenas.SELECCIONAR_COLABORACION, null);
            }
        }
    }

    @FXML
    private void btnClicConcluir(ActionEvent event) {
        Animaciones.animarPresionBoton(btnConcluir);
        if (cancelarColab) {
            Animaciones.reducirBrillo(apVentana);
            mostrarMotivoCancelacion();
            Animaciones.restablecerBrillo(apVentana);
        } else {
            String titulo = "Concluir colaboración";
            String mensaje = "¿Está seguro de que desea concluir la colaboración seleccionada? Esta acción no se puede deshacer.";
            boolean confirmacion = Utils.mostrarAlertaConfirmacion(titulo, mensaje, "Concluir", "Cancelar", apVentana);

            if (confirmacion) {
                boolean exito = ColaboracionDAO.actualizarEstadoColaboracion(idColaboracion, Constantes.ESTADO_CONCLUIDA);
                if (exito) {
                    Utils.mostrarAlertaSimple(titulo, "La colaboración ha sido concluida exitosamente.", apVentana);
                    regresarVentana("/sistemacoil/vista/FXMLSeleccionarColaboracion.fxml", null);
                }
            }
        }
    }
}
