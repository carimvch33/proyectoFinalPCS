package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sistemacoil.modelo.dao.CatalogoDAO;
import sistemacoil.modelo.dao.ModuloUniversitarioDAO;
import sistemacoil.modelo.pojo.AreaAcademica;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.modelo.pojo.Dependencia;
import sistemacoil.modelo.pojo.Estudiante;
import sistemacoil.modelo.pojo.ExperienciaEducativa;
import sistemacoil.modelo.pojo.Idioma;
import sistemacoil.modelo.pojo.Periodo;
import sistemacoil.modelo.pojo.ProfesorExterno;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.modelo.pojo.ProgramaEducativo;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Escenas;
import sistemacoil.utilidades.Utils;
import sistemacoil.utilidades.Validaciones;

public class FXMLRegistrarColaboracionController implements Initializable {

    private ObservableList<Idioma> idiomas;
    private ObservableList<Periodo> periodos;
    private ObservableList<AreaAcademica> areasAcademicas;
    private ObservableList<Dependencia> dependencias;
    private ObservableList<ProgramaEducativo> programasEducativos;
    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    
    private Periodo periodoSeleccionado;
    private ProfesorUV profesorUV;
    private ProfesorExterno profesorExterno;
    private Colaboracion colaboracion;
    private ObservableList<Estudiante> listaEstudiantes;

    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private ComboBox<Idioma> cbIdiomas;
    @FXML
    private Label lbErrorIdioma;
    @FXML
    private ComboBox<Periodo> cbPeriodos;
    @FXML
    private Label lbErrorPeriodo;
    @FXML
    private Label lbErrorProfesorExterno;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private Label lbErrorFechaInicio;
    @FXML
    private DatePicker dpFechaConclusion;
    @FXML
    private Label lbErrorFechaConclusion;
    @FXML
    private ComboBox<AreaAcademica> cbAreasAcademicas;
    @FXML
    private Label lbErrorAreaAcademica;
    @FXML
    private ComboBox<Dependencia> cbDependencias;
    @FXML
    private Label lbErrorDependencia;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramasEducativos;
    @FXML
    private Label lbErrorProgramaEducativo;
    @FXML
    private ComboBox<ExperienciaEducativa> cbExperienciasEducativas;
    @FXML
    private Label lbErrorExperienciaEducativa;
    @FXML
    private TextArea taObjetivosCurso;
    @FXML
    private TextArea taPerfilEstudiantes;
    @FXML
    private TextArea taInfoAdicional;
    @FXML
    private TextArea taTemasInteres;
    @FXML
    private TextField tfProfesorUV;
    @FXML
    private Label lbErrorObjetivoCurso;
    @FXML
    private Label lbErrorPerfilEstudiantes;
    @FXML
    private Label lbErrorInformacionAdicional;
    @FXML
    private Label lbErrorTemaInteres;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnCancelarRegistro;
    @FXML
    private Button btnContinuar;
    @FXML
    private TextField tfProfesorExterno;
    @FXML
    private Button btnRegistrarProfeExterno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboracion = new Colaboracion();
        configurarListeners();
        cargarIdiomas();
        cargarPeriodos();
        cargarAreasAcademicas();
        if (profesorExterno != null)
            btnRegistrarProfeExterno.setText("Editar Prof. Externo");
    }

    public void configurarListeners() {
        Utils.agregarListenerSimple(tfNombre, Constantes.MAX_LONG_NOMBRE_COLAB);
        Utils.agregarListenerTextArea(taObjetivosCurso, Constantes.MAX_LONG_TEXTAREA);
        Utils.agregarListenerTextArea(taPerfilEstudiantes, Constantes.MAX_LONG_TEXTAREA);
        Utils.agregarListenerTextArea(taInfoAdicional, Constantes.MAX_LONG_TEXTAREA);
        Utils.agregarListenerTextArea(taTemasInteres, Constantes.MAX_LONG_TEXTAREA);
        
        cbPeriodos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            periodoSeleccionado = newValue;
            if (periodoSeleccionado != null) {
                Utils.agregarListenerFecha(dpFechaInicio, lbErrorFechaInicio, periodoSeleccionado, (datePicker, lbError) ->
                        Validaciones.validarFecha(datePicker, lbError, periodoSeleccionado)
                );
                Utils.agregarListenerFecha(dpFechaConclusion, lbErrorFechaConclusion, periodoSeleccionado, (datePicker, lbError) ->
                        validarFechaConclusion(datePicker, lbError, periodoSeleccionado)
                );
            } else {
                dpFechaInicio.setValue(null);
                dpFechaConclusion.setValue(null);
                dpFechaInicio.setDisable(true);
                dpFechaConclusion.setDisable(true);
                lbErrorFechaInicio.setText("");
                lbErrorFechaConclusion.setText("");
            }
            dpFechaInicio.setDisable(periodoSeleccionado == null);
            dpFechaConclusion.setDisable(periodoSeleccionado == null);
        });
        
        Utils.configurarListenerDesbloquearYLLenar(cbAreasAcademicas, cbDependencias,
                this::cargarDependencias, AreaAcademica::getIdAreaAcademica);

        Utils.configurarListenerDesbloquearYLLenar(cbDependencias, cbProgramasEducativos,
                this::cargarProgramasEducativos, Dependencia::getIdDependencia
        );
        
        Utils.configurarListenerDesbloquearYLLenar(cbProgramasEducativos, cbExperienciasEducativas,
                this::cargarExperienciasEducativas, ProgramaEducativo::getIdProgramaEducativo
        );
    }
    
    public void setProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;
        if (profesorExterno != null) {
            tfProfesorExterno.setText(profesorExterno.getApellidoPaterno() + "-" + profesorExterno.getApellidoMaterno()
                    + ", " + profesorExterno.getNombre());
        }
    }
    
    public void setProfesorUV(ProfesorUV profesorUV) {
        this.profesorUV = profesorUV;
        if (profesorUV != null) {
            tfProfesorUV.setText(profesorUV.getApellidoPaterno() + " " + profesorUV.getApellidoMaterno() + ", " + profesorUV.getNombre());
        }
    }
    
    public void setListaEstudiantes(ObservableList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }
    
    private void cargarIdiomas() {
        idiomas = FXCollections.observableArrayList();
        List<Idioma> idiomasBD = CatalogoDAO.obtenerIdiomas();
        idiomas.addAll(idiomasBD);
        cbIdiomas.setItems(idiomas);
    }

    private void cargarPeriodos() {
        periodos = FXCollections.observableArrayList();
        List<Periodo> periodosBD = ModuloUniversitarioDAO.obtenerPeriodos();
        periodos.addAll(periodosBD);
        cbPeriodos.setItems(periodos);
    }

    private void cargarAreasAcademicas() {
        areasAcademicas = FXCollections.observableArrayList();
        List<AreaAcademica> areasAcademicasBD = 
                ModuloUniversitarioDAO.obtenerAreasAcademicas();
        areasAcademicas.addAll(areasAcademicasBD);
        cbAreasAcademicas.setItems(areasAcademicas);
    }

    private void cargarDependencias(int idAreaAcademica) {
        dependencias = FXCollections.observableArrayList();
        List<Dependencia> dependenciasBD = 
                ModuloUniversitarioDAO.obtenerDependenciasPorAreaAcademica(idAreaAcademica);
        dependencias.addAll(dependenciasBD);
        cbDependencias.setItems(dependencias);
    }

    private void cargarProgramasEducativos(int idDependencia) {
        programasEducativos = FXCollections.observableArrayList();
        List<ProgramaEducativo> programasEducativosBD = 
                ModuloUniversitarioDAO.obtenerProgramasEducativosPorDependencia(idDependencia);
        programasEducativos.addAll(programasEducativosBD);
        cbProgramasEducativos.setItems(programasEducativos);
    }

    private void cargarExperienciasEducativas(int idProgramaEducativo) {
        experienciasEducativas = FXCollections.observableArrayList();
        List<ExperienciaEducativa> experienciasEducativasBD = 
                ModuloUniversitarioDAO.obtenerExperienciasPorProgramaEducativo(idProgramaEducativo);
        experienciasEducativas.addAll(experienciasEducativasBD);
        cbExperienciasEducativas.setItems(experienciasEducativas);
    }

    private Idioma buscarIdIdioma(int id) {
        if (idiomas != null) {
            return idiomas.stream().filter(i -> i.getIdIdioma() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    private Periodo buscarIdPeriodo(int id) {
        if (periodos != null) {
            return periodos.stream().filter(p -> p.getIdPeriodo() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    private AreaAcademica buscarIdAreaAcademica(int id) {
        if (cbAreasAcademicas != null) {
            return cbAreasAcademicas.getItems().stream().filter(a -> a.getIdAreaAcademica() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    private Dependencia buscarIdDependencia(int id) {
        if (cbDependencias != null) {
            return cbDependencias.getItems().stream().filter(d -> d.getIdDependencia() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    private ProgramaEducativo buscarIdProgramaEducativo(int id) {
        if (cbProgramasEducativos != null) {
            return cbProgramasEducativos.getItems().stream().filter(p -> p.getIdProgramaEducativo() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }
    
    private ExperienciaEducativa buscarIdExperienciaEducativa(int id) {
        if (experienciasEducativas != null && !experienciasEducativas.isEmpty()) {
            return experienciasEducativas.stream().filter(e -> e.getIdExperienciaEducativa() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }
    
    private boolean validarFechaConclusion(DatePicker datePicker, Label lbError, Periodo periodoSeleccionado) {
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaConclusion = datePicker.getValue();

        if (fechaConclusion == null) {
            lbError.setText(Constantes.ERROR_CAMPOS_VACIOS);
            Animaciones.animarShake(datePicker);
            return false;
        }

        if (periodoSeleccionado != null) {
            LocalDate inicioPeriodo = periodoSeleccionado.getInicioPeriodo();
            LocalDate finPeriodo = periodoSeleccionado.getFinPeriodo();

            if (fechaConclusion.isBefore(inicioPeriodo) || fechaConclusion.isAfter(finPeriodo)) {
                lbError.setText(Constantes.ERROR_FECHA_FUERA_RANGO);
                Animaciones.animarShake(datePicker);
                return false;
            }
        }

        if (fechaInicio != null && fechaConclusion.isBefore(fechaInicio)) {
            lbError.setText(Constantes.ERROR_FECHA_CONCLUSION);
            return false;
        }

        lbError.setText("");
        return true;
    }

    private boolean validarCamposColaboracion() {
        periodoSeleccionado = cbPeriodos.getSelectionModel().getSelectedItem();
        List<Boolean> validaciones = Arrays.asList(
            Validaciones.validarCampo(tfNombre, lbErrorNombre),
            Validaciones.validarComboBox(cbIdiomas, lbErrorIdioma),
            Validaciones.validarComboBox(cbPeriodos, lbErrorPeriodo),
            Validaciones.validarCampo(tfProfesorExterno, lbErrorProfesorExterno),
            Validaciones.validarComboBox(cbAreasAcademicas, lbErrorAreaAcademica),
            Validaciones.validarComboBox(cbDependencias, lbErrorDependencia),
            Validaciones.validarComboBox(cbProgramasEducativos, lbErrorProgramaEducativo),
            Validaciones.validarComboBox(cbExperienciasEducativas, lbErrorExperienciaEducativa),
            Validaciones.validarFecha(dpFechaInicio, lbErrorFechaInicio, periodoSeleccionado),
            validarFechaConclusion(dpFechaConclusion, lbErrorFechaConclusion, periodoSeleccionado),
            Validaciones.validarTextArea(taObjetivosCurso, lbErrorObjetivoCurso),
            Validaciones.validarTextArea(taPerfilEstudiantes, lbErrorPerfilEstudiantes),
            Validaciones.validarTextArea(taTemasInteres, lbErrorTemaInteres),
            Validaciones.validarTextArea(taInfoAdicional, lbErrorInformacionAdicional)
        );

        return validaciones.stream().allMatch(Boolean::booleanValue);
    }

    private void guardarDatosColaboracion() {
        colaboracion.setNombreColaboracion(tfNombre.getText());

        if (cbIdiomas.getSelectionModel().getSelectedItem() != null)
            colaboracion.setIdIdioma(cbIdiomas.getSelectionModel().
                    getSelectedItem().getIdIdioma());

        if (cbPeriodos.getSelectionModel().getSelectedItem() != null)
            colaboracion.setIdPeriodo(cbPeriodos.getSelectionModel().
                    getSelectedItem().getIdPeriodo());

        colaboracion.setFechaInicio(dpFechaInicio.getValue());
        colaboracion.setFechaConclusion(dpFechaConclusion.getValue());

        if (cbAreasAcademicas.getSelectionModel().getSelectedItem() != null)
            colaboracion.setIdAreaAcademica(cbAreasAcademicas.getSelectionModel().
                    getSelectedItem().getIdAreaAcademica());

        if (cbDependencias.getSelectionModel().getSelectedItem() != null)
            colaboracion.setIdDependencia(cbDependencias.getSelectionModel().
                    getSelectedItem().getIdDependencia());

        if (cbProgramasEducativos.getSelectionModel().getSelectedItem() != null)
            colaboracion.setIdProgramaEducativo(cbProgramasEducativos.getSelectionModel().
                    getSelectedItem().getIdProgramaEducativo());

        if (cbExperienciasEducativas.getSelectionModel().getSelectedItem() != null)
            colaboracion.setIdExperienciaEducativa(cbExperienciasEducativas.getSelectionModel().
                    getSelectedItem().getIdExperienciaEducativa());

        colaboracion.setObjetivoCurso(taObjetivosCurso.getText());
        colaboracion.setPerfilEstudiante(taPerfilEstudiantes.getText());
        colaboracion.setTemaInteres(taTemasInteres.getText());
        colaboracion.setInformacionAdicional(taInfoAdicional.getText());
    }
    
    public void cargarDatosColaboracion(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
        if (colaboracion != null) {
            tfNombre.setText(colaboracion.getNombreColaboracion());
            cbIdiomas.getSelectionModel().select(buscarIdIdioma(colaboracion.getIdIdioma()));
            cbPeriodos.getSelectionModel().select(buscarIdPeriodo(colaboracion.getIdPeriodo()));
            dpFechaInicio.setValue(colaboracion.getFechaInicio());
            dpFechaConclusion.setValue(colaboracion.getFechaConclusion());
            cbAreasAcademicas.getSelectionModel().select(buscarIdAreaAcademica(colaboracion.getIdAreaAcademica()));
            cbDependencias.getSelectionModel().select(buscarIdDependencia(colaboracion.getIdDependencia()));
            cbProgramasEducativos.getSelectionModel().select(buscarIdProgramaEducativo(colaboracion.getIdProgramaEducativo()));
            cbExperienciasEducativas.getSelectionModel().select(buscarIdExperienciaEducativa(colaboracion.getIdExperienciaEducativa()));
            taObjetivosCurso.setText(colaboracion.getObjetivoCurso());
            taPerfilEstudiantes.setText(colaboracion.getPerfilEstudiante());
            taTemasInteres.setText(colaboracion.getTemaInteres());
            taInfoAdicional.setText(colaboracion.getInformacionAdicional());
        }
    }

    @FXML
    private void btnClicRegistrarProfesorExterno(ActionEvent event) {
        Animaciones.animarPresionBoton(btnRegistrarProfeExterno);
        try {
            guardarDatosColaboracion();

            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource(Escenas.REGISTRAR_PROFESOR_EXTERNO);
            loader.setLocation(fxmlUrl);
            AnchorPane registrarProfesorExternoPane = loader.load();

            FXMLRegistrarProfesorExternoController controller = loader.getController();
            controller.setParentController(this);
            controller.setColaboracion(colaboracion);
            controller.setProfesorUV(profesorUV);

            if (profesorExterno != null) {
                controller.setProfesorExterno(profesorExterno);
                controller.cargarDatosProfesorExterno(profesorExterno);
            }

            apVentana.getChildren().clear();
            apVentana.getChildren().add(registrarProfesorExternoPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void btnClicCancelarRegistro(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelarRegistro);
        Utils.cancelarOperacionActual(apVentana);
    }
    
    @FXML
    private void btnClicRegresar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnRegresar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicContinuar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnContinuar);
        if (validarCamposColaboracion()) {
            guardarDatosColaboracion();
            Utils.mostrarAlertaSimple("Colaboración guardada", 
                    "Los datos de la colaboración han sido guardados correctamente.", apVentana);

            try {
                FXMLLoader loader = new FXMLLoader();
                URL fxmlUrl = getClass().getResource(Escenas.REGISTRAR_ESTUDIANTES);
                loader.setLocation(fxmlUrl);
                AnchorPane registrarEstudiantesPane = loader.load();

                FXMLRegistrarEstudiantesController controller = loader.getController();
                controller.setColaboracion(colaboracion);
                controller.setProfesorExterno(profesorExterno);
                controller.setProfesorUV(profesorUV);
                controller.setListaEstudiantes(listaEstudiantes != null ? listaEstudiantes : FXCollections.observableArrayList());

                apVentana.getChildren().clear();
                apVentana.getChildren().add(registrarEstudiantesPane);
            } catch (IOException ex) {
                ex.printStackTrace();
                Utils.mostrarAlertaSimple("Error de navegación", "Ocurrió un error al cargar la siguiente ventana", apVentana);
            }
        }
    }
}
