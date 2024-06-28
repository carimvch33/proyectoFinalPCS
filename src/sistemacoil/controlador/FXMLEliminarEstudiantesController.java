package sistemacoil.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.modelo.dao.EstudianteDAO;
import sistemacoil.modelo.pojo.Estudiante;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;

public class FXMLEliminarEstudiantesController implements Initializable {

    private int idColaboracion;

    @FXML
    private TextField tfBuscarEstudiante;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> colMatricula;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TableColumn<Estudiante, String> colApPaterno;
    @FXML
    private TableColumn<Estudiante, String> colApMaterno;
    @FXML
    private TableColumn<Estudiante, String> colCorreo;
    @FXML
    private TextField tfNombreColaboracion;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEliminar;
    @FXML
    private AnchorPane apVentana;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        tvEstudiantes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estudiante>() {
            @Override
            public void changed(ObservableValue<? extends Estudiante> observable, Estudiante oldValue, Estudiante newValue) {
                btnEliminar.setDisable(newValue == null);
            }
        });
    }

    public void setIdColaboracion(int idColaboracion) {
        this.idColaboracion = idColaboracion;
        cargarEstudiantes();
        cargarNombreColaboracion();
    }

    private void cargarEstudiantes() {
        List<Estudiante> estudiantes = EstudianteDAO.obtenerEstudiantesPorColaboracion(idColaboracion);
        tvEstudiantes.getItems().setAll(estudiantes);
    }

    private void cargarNombreColaboracion() {
        String nombreColaboracion = ColaboracionDAO.obtenerNombreColaboracion(idColaboracion);
        if (nombreColaboracion != null)
            tfNombreColaboracion.setText(nombreColaboracion);
    }

    private void eliminarEstudiante() {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado == null) {
            Utils.mostrarAlertaSimple("Sin selección", "Seleccione un estudiante para eliminar", apVentana);
            return;
        }
        if (!hayEstudiantesSuficientes()) {
            Utils.mostrarAlertaSimple("Límite alcanzado", "El mínimo de estudiantes en una colaboración son 10", apVentana);
            return;
        }
        if (confirmarEliminacion()) {
            ejecutarEliminacion(estudianteSeleccionado);
        }
    }

    private boolean hayEstudiantesSuficientes() {
        return tvEstudiantes.getItems().size() > Constantes.MIN_ESTUDIANTES_COLAB;
    }

    private boolean confirmarEliminacion() {
        return Utils.mostrarAlertaConfirmacion(
                "Confirmación de eliminación",
                "¿Está seguro de que desea eliminar al estudiante seleccionado?",
                "Eliminar",
                "Cancelar", apVentana
        );
    }

    private void ejecutarEliminacion(Estudiante estudianteSeleccionado) {
        boolean exito = EstudianteDAO.eliminarEstudianteDeColaboracion(idColaboracion, estudianteSeleccionado.getIdEstudiante());
        if (exito) {
            tvEstudiantes.getItems().remove(estudianteSeleccionado);
            Utils.mostrarAlertaSimple("Eliminación exitosa", "El estudiante ha sido eliminado de la colaboración", apVentana);
        } else {
            Utils.mostrarAlertaSimple("Error", "No se pudo eliminar al estudiante de la colaboración", apVentana);
        }
    }
    
    @FXML
    private void btnClicCancelarBusqueda(ActionEvent event) {
        tfBuscarEstudiante.clear();
        cargarEstudiantes();
    }

    @FXML
    private void btnClicSalir(ActionEvent event) {
        
        //TODO
    }

    @FXML
    private void btnClicEliminar(ActionEvent event) {
        eliminarEstudiante();
    }
}
