package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.modelo.dao.EvidenciaDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Utils;

public class FXMLSeleccionarColaboracionController implements Initializable {

    private int idProfesorUV;
    private String estado;

    @FXML
    private Label lbTitulo;
    @FXML
    private TableView<Colaboracion> tvColaboraciones;
    @FXML
    private TableColumn<Colaboracion, String> colCurso;
    @FXML
    private TableColumn<Colaboracion, String> colProfesorExterno;
    @FXML
    private TableColumn<Colaboracion, String> colExperiencia;
    @FXML
    private TableColumn<Colaboracion, LocalDate> colFechaInicio;
    @FXML
    private TableColumn<Colaboracion, LocalDate> colFechaConclusion;
    @FXML
    private TableColumn<Colaboracion, String> colIdioma;
    @FXML
    private TextField tfBuscarColaboraciones;
    @FXML
    private Button btnCancelar;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private Button btnGestionar;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCurso.setCellValueFactory(new PropertyValueFactory<>("nombreColaboracion"));
        colProfesorExterno.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreProfesorExterno()));
        colExperiencia.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExperienciaEducativa()));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaConclusion.setCellValueFactory(new PropertyValueFactory<>("fechaConclusion"));
        colIdioma.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdioma()));

        configurarListeners();
    }

    private void configurarListeners() {
        tvColaboraciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Colaboracion>() {
            @Override
            public void changed(ObservableValue<? extends Colaboracion> observable, Colaboracion oldValue, Colaboracion newValue) {
                boolean seleccionValida = newValue != null;
                btnGestionar.setDisable(!seleccionValida);
            }
        });
    }

    public void setIdProfesorUV(int idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
        cargarColaboraciones();
    }

    public void setEstado(String estado) {
        this.estado = estado;
        ajustarVistaSegunEstado();
    }

    private void ajustarVistaSegunEstado() {
        if ("cancelarColab".equals(estado)) {
            lbTitulo.setText("Cancelar Colaboración");
            btnGestionar.setText("Cancelar Colaboración");
            btnCancelar.setText("Regresar");
        }
    }

    private void cargarColaboraciones() {
        List<Colaboracion> colaboraciones = ColaboracionDAO.obtenerColaboracionesVigentes(idProfesorUV);
        tvColaboraciones.getItems().setAll(colaboraciones);
    }

    private void abrirDetallesColaboracion(int idColaboracion, boolean cancelarColab) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLDetallesColaboracion.fxml"));
            AnchorPane root = loader.load();

            FXMLDetallesColaboracionController controlador = loader.getController();
            controlador.setIdColaboracion(idColaboracion);
            controlador.setIdProfesorUV(idProfesorUV);
            controlador.setCancelarColab(cancelarColab);

            apVentana.getChildren().clear();
            apVentana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void abrirSubirArchivos(int idColaboracion, boolean subirEvidencias) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLSubirArchivos.fxml"));
            AnchorPane root = loader.load();

            FXMLSubirArchivosController controlador = loader.getController();
            controlador.setIdColaboracion(idColaboracion);
            controlador.setSubirEvidencias(subirEvidencias);

            apVentana.getChildren().clear();
            apVentana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void abrirEliminarEstudiantes(int idColaboracion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLEliminarEstudiantes.fxml"));
            AnchorPane root = loader.load();

            FXMLEliminarEstudiantesController controlador = loader.getController();
            controlador.setIdColaboracion(idColaboracion);

            apVentana.getChildren().clear();
            apVentana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnRegresar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicGestionar(ActionEvent event) {
        Colaboracion seleccionada = tvColaboraciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Animaciones.reducirBrillo(apVentana);
            if ("cancelarColab".equals(estado)) {
                abrirDetallesColaboracion(seleccionada.getIdColaboracion(), true);
            } else {
                int opcion = Utils.mostrarMenuContextual();
                switch (opcion) {
                    case 1:
                        abrirDetallesColaboracion(seleccionada.getIdColaboracion(), false);
                        break;
                    case 2:
                        if (EvidenciaDAO.tieneEvidenciaAsociada(seleccionada.getIdColaboracion())) {
                            Utils.mostrarAlertaSimple("Evidencia Existente", "La colaboración ya tiene una evidencia asociada.\n"
                                    + "No se puede subir una nueva evidencia.", apVentana);
                        } else {
                            abrirSubirArchivos(seleccionada.getIdColaboracion(), true);
                        }
                        break;
                    case 3:
                        abrirEliminarEstudiantes(seleccionada.getIdColaboracion());
                        break;
                    default:
                        break;
                }
            }
            Animaciones.restablecerBrillo(apVentana);
        }
    }

    @FXML
    private void btnClicCancelarBusqueda(ActionEvent event) {
    }
}
