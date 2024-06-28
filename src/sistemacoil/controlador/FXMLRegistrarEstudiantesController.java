package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sistemacoil.modelo.dao.EstudianteDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.modelo.pojo.Estudiante;
import sistemacoil.modelo.pojo.ProfesorExterno;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Escenas;
import sistemacoil.utilidades.Validaciones;
import sistemacoil.utilidades.Utils;

public class FXMLRegistrarEstudiantesController implements Initializable {
    
    private ProfesorExterno profesorExterno;
    private ProfesorUV profesorUV;
    private Colaboracion colaboracion;
    private ObservableList<Estudiante> listaEstudiantes;
    private BooleanProperty haySeleccion = new SimpleBooleanProperty(false);

    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Label lbErrorMatricula;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApPaterno;
    @FXML
    private Label lbErrorApMaterno;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> colMatricula;
    @FXML
    private TableColumn<Estudiante, String> colApellidos;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TextField tfBuscarEstudiante;
    @FXML
    private CheckBox cbRegistroCompleto;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRegistrarLista;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaEstudiantes = FXCollections.observableArrayList();
        configurarTabla();
        configurarListeners();
        btnRegistrarLista.setDisable(true);
        BooleanProperty puedeModificar = new SimpleBooleanProperty();
        puedeModificar.bind(haySeleccion.and(cbRegistroCompleto.selectedProperty().not()));

        btnEliminar.disableProperty().bind(puedeModificar.not());
        btnEditar.disableProperty().bind(puedeModificar.not());
        btnAgregar.setText("Agregar Estudiante");
        btnAgregar.setText("Agregar Estudiante");
    }
    
    public void setColaboracion(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
    }
    
    public void setProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;
    }
    
    public void setProfesorUV(ProfesorUV profesorUV) {
        this.profesorUV = profesorUV;
    }
    
    public void setListaEstudiantes(ObservableList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
        if (this.listaEstudiantes != null) {
            tvEstudiantes.setItems(this.listaEstudiantes);
        } else {
            this.listaEstudiantes = FXCollections.observableArrayList();
            tvEstudiantes.setItems(this.listaEstudiantes);
        }
    }

    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getApellidoPaterno() + "-" + cellData.getValue().getApellidoMaterno()));

        if (listaEstudiantes == null) {
            listaEstudiantes = FXCollections.observableArrayList();
        }
        tvEstudiantes.setItems(listaEstudiantes);
    }
    
    private void configurarListeners() {
        Utils.agregarListenerSimple(tfMatricula, Constantes.MAX_LONG_MATRICULA);
        Utils.agregarListenerNombres(tfNombre, Constantes.MAX_LONG_NOMBRES);
        Utils.agregarListenerNombres(tfApellidoPaterno, Constantes.MAX_LONG_NOMBRES);
        Utils.agregarListenerNombres(tfApellidoMaterno, Constantes.MAX_LONG_NOMBRES);
        
        tfCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > Constantes.MAX_LONG_CORREO) {
                tfCorreo.setText(oldValue);
                Animaciones.animarShake(tfCorreo);
            }
        });
        
        tfMatricula.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (tfCorreo.getText().isEmpty() && Validaciones.validarMatricula(tfMatricula, lbErrorMatricula)) {
                    autocompletarCorreo(tfMatricula.getText());
                }
            }
        });
        
        tvEstudiantes.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Estudiante>) change -> {
            haySeleccion.set(!change.getList().isEmpty());
        });

        tfBuscarEstudiante.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarEstudiante();
        });
        
        cbRegistroCompleto.selectedProperty().addListener((observable, oldValue, newValue) -> {
            deshabilitarCampos(newValue);
        });
    }

    private boolean validarCamposEstudiantes() {
        List<Boolean> validaciones = Arrays.asList(
            Validaciones.validarMatricula(tfMatricula, lbErrorMatricula) && validarMatriculaExistente(tfMatricula),
            Validaciones.validarCampo(tfNombre, lbErrorNombre),
            Validaciones.validarCampo(tfApellidoPaterno, lbErrorApPaterno),
            Validaciones.validarCampo(tfApellidoMaterno, lbErrorApMaterno),
            Validaciones.validarCorreo(tfCorreo, lbErrorCorreo)
        );

        return validaciones.stream().allMatch(Boolean::booleanValue);
    }

    
    private boolean validarMatriculaExistente(TextField tfMatricula) {
        String matricula = tfMatricula.getText();
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();

        for (Estudiante estudiante : listaEstudiantes) {
            if (estudianteSeleccionado != null && estudiante.getMatricula().equals(estudianteSeleccionado.getMatricula())) {
                continue;
            }

            if (estudiante.getMatricula().equals(matricula)) {
                Animaciones.animarShake(tfMatricula);
                lbErrorMatricula.setText("Matrícula ya existente*");
                return false;
            }
        }
        return true;
    }

    private void agregarEstudianteTabla() {
        Estudiante estudiante = new Estudiante(
                tfNombre.getText().trim(),
                tfApellidoPaterno.getText().trim(),
                tfApellidoMaterno.getText().trim(),
                tfMatricula.getText().trim(),
                tfCorreo.getText().trim());

        listaEstudiantes.add(estudiante);
        limpiarCamposEstudiantes();
    }

    private void limpiarCamposEstudiantes() {
        Utils.limpiarCampo(tfMatricula, lbErrorMatricula);
        Utils.limpiarCampo(tfNombre, lbErrorNombre);
        Utils.limpiarCampo(tfApellidoPaterno, lbErrorApPaterno);
        Utils.limpiarCampo(tfApellidoMaterno, lbErrorApMaterno);
        Utils.limpiarCampo(tfCorreo, lbErrorCorreo);
    }

    private void autocompletarCorreo(String matricula) {
        if (matricula != null && !matricula.isEmpty()) {
            String correo = "z" + matricula.toLowerCase() + "@estudiantes.uv.mx";
            tfCorreo.setText(correo);
        } else {
            tfCorreo.setText("");
        }
    }
    
    private void deshabilitarCampos(boolean deshabilitar) {
        limpiarCamposEstudiantes();
        tfMatricula.setDisable(deshabilitar);
        tfNombre.setDisable(deshabilitar);
        tfApellidoPaterno.setDisable(deshabilitar);
        tfApellidoMaterno.setDisable(deshabilitar);
        tfCorreo.setDisable(deshabilitar);
        btnRegistrarLista.setDisable(deshabilitar);
        btnAgregar.setDisable(deshabilitar);
        btnLimpiar.setDisable(deshabilitar);
        btnRegistrarLista.setDisable(!deshabilitar);
    }
    
    private void cargarDatosEstudiante(Estudiante estudianteSeleccionado) {
        tfMatricula.setText(estudianteSeleccionado.getMatricula());
        tfNombre.setText(estudianteSeleccionado.getNombre());
        tfApellidoPaterno.setText(estudianteSeleccionado.getApellidoPaterno());
        tfApellidoMaterno.setText(estudianteSeleccionado.getApellidoMaterno());
        tfCorreo.setText(estudianteSeleccionado.getCorreo());
    }
    
    private void modificarEstudianteSeleccionado() {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado != null) {
            estudianteSeleccionado.setMatricula(tfMatricula.getText().trim());
            estudianteSeleccionado.setNombre(tfNombre.getText().trim());
            estudianteSeleccionado.setApellidoPaterno(tfApellidoPaterno.getText().trim());
            estudianteSeleccionado.setApellidoMaterno(tfApellidoMaterno.getText().trim());
            estudianteSeleccionado.setCorreo(tfCorreo.getText().trim());
            tvEstudiantes.refresh();
            tvEstudiantes.getSelectionModel().clearSelection();
            limpiarCamposEstudiantes();
        } else {
            System.out.println("¡No se ha seleccionado ningún estudiante!");
        }
    }
    
    private void eliminarEstudianteSeleccionado(Estudiante estudianteSeleccionado) {
        if (estudianteSeleccionado != null) {
            boolean confirmacion = Utils.mostrarAlertaConfirmacion("Eliminar estudiante",
                    "¿Está seguro de que desea eliminar al estudiante seleccionado? "
                            + "Esta acción no se puede deshacer.", "Cancelar", "Eliminar", apVentana);
                if (!confirmacion) {
                    listaEstudiantes.remove(estudianteSeleccionado);
                }
        }
    }
    
    private void buscarEstudiante() {
        String busqueda = tfBuscarEstudiante.getText().trim().toLowerCase();
        ObservableList<Estudiante> resultados = FXCollections.observableArrayList();

        if (busqueda.isEmpty()) {
            tvEstudiantes.setItems(listaEstudiantes);
            return;
        }

        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getMatricula().toLowerCase().contains(busqueda) ||
                estudiante.getNombre().toLowerCase().contains(busqueda) ||
                estudiante.getApellidoPaterno().toLowerCase().contains(busqueda) ||
                estudiante.getApellidoMaterno().toLowerCase().contains(busqueda)) {
                resultados.add(estudiante);
            }
        }
        tvEstudiantes.setItems(resultados);
    }
    
    public static boolean registrarEstudiantesEnColaboracion(int idColaboracion, List<Estudiante> listaEstudiantes) {
        boolean exito = true;
        for (Estudiante estudiante : listaEstudiantes) {
            exito = EstudianteDAO.registrarEstudiante(estudiante);
            if (exito) {
                exito = EstudianteDAO.relacionarEstudianteConColaboracion(idColaboracion, estudiante.getIdEstudiante());
            }
            if (!exito) {
                break;
            }
        }
        return exito;
    }
    
    private void navegarRegistrarColaboracion(AnchorPane apVentana, ProfesorExterno profesorExterno, 
            Colaboracion colaboracion, ProfesorUV profesorUV, ObservableList<Estudiante> listaEstudiantes) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Escenas.REGISTRAR_COLABORACION));
            AnchorPane registrarColaboracionPane = loader.load();
            FXMLRegistrarColaboracionController controller = loader.getController();
            controller.setProfesorExterno(profesorExterno);
            controller.cargarDatosColaboracion(colaboracion);
            controller.setProfesorUV(profesorUV);
            controller.setListaEstudiantes(listaEstudiantes);
            apVentana.getChildren().setAll(registrarColaboracionPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicAgregarEstudiante(ActionEvent event) {
        Animaciones.animarPresionBoton(btnAgregar);
        if (validarCamposEstudiantes()) {
            if (btnAgregar.getText().equals("Agregar Estudiante"))
                agregarEstudianteTabla();
            else {
                modificarEstudianteSeleccionado();
                btnAgregar.setText("Agregar Estudiante");
            }
        }
    }

    @FXML
    private void btnClicLimpiarCampos(ActionEvent event) {
        Animaciones.animarPresionBoton(btnLimpiar);
        if (btnLimpiar.getText().equals("Cancelar Edición")) {
            tvEstudiantes.getSelectionModel().clearSelection();
            btnAgregar.setText("Agregar Estudiante");
            btnLimpiar.setText("Limpiar Campos");
            limpiarCamposEstudiantes();
        } else
            limpiarCamposEstudiantes();
    }
    
    @FXML
    private void btnClicCancelarBusqueda(ActionEvent event) {
        tfBuscarEstudiante.clear();
        tvEstudiantes.setItems(listaEstudiantes);
    }

    @FXML
    private void btnClicEliminarEstudiante(ActionEvent event) {
        Animaciones.animarPresionBoton(btnEliminar);
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        eliminarEstudianteSeleccionado(estudianteSeleccionado);
    }

    @FXML
    private void btnClicEditarEstudiante(ActionEvent event) {
        Animaciones.animarPresionBoton(btnEditar);
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado != null) {
            btnAgregar.setText("Editar Estudiante");
            btnLimpiar.setText("Cancelar Edición");
            cargarDatosEstudiante(estudianteSeleccionado);
        }
    }

    @FXML
    private void btnClicCancelarRegistro(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        navegarRegistrarColaboracion(apVentana, profesorExterno, colaboracion, profesorUV, listaEstudiantes);
    }
    
    @FXML
    private void btnClicRegresar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnRegresar);
        navegarRegistrarColaboracion(apVentana, profesorExterno, colaboracion, profesorUV, listaEstudiantes);
    }

    @FXML
    private void btnClicRegistrarListaEstudiantes(ActionEvent event) {
        if (listaEstudiantes.size() < Constantes.MIN_ESTUDIANTES_COLAB) {
            Utils.mostrarAlertaSimple("Lista incompleta", "La colaboración debe contar por lo menos con "
                    + Constantes.MIN_ESTUDIANTES_COLAB + "estudiantes registrados", apVentana);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Escenas.SUBIR_ARCHIVOS));
                AnchorPane subirArchivosPane = loader.load();
                FXMLSubirArchivosController controller = loader.getController();
                controller.setIdColaboracion(colaboracion.getIdColaboracion());
                controller.setSubirEvidencias(false);
                controller.setProfesorUV(profesorUV);
                controller.setProfesorExterno(profesorExterno);
                controller.setListaEstudiantes(listaEstudiantes);
                controller.setColaboracion(colaboracion);
                apVentana.getChildren().setAll(subirArchivosPane);
            } catch (IOException ex) {
                ex.printStackTrace();
                Utils.mostrarAlertaSimple("Error de navegación", "Ocurrió un error al cargar la siguiente ventana", apVentana);
            }
        }
    } 
}
