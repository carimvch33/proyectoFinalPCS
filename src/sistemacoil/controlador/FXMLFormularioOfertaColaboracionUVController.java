package sistemacoil.controlador;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sistemacoil.modelo.dao.CatalogoDAO;
import sistemacoil.modelo.dao.ModuloUniversitarioDAO;
import sistemacoil.modelo.dao.OfertaColaboracionUVDAO;
import sistemacoil.modelo.pojo.AreaAcademica;
import sistemacoil.modelo.pojo.Dependencia;
import sistemacoil.modelo.pojo.ExperienciaEducativa;
import sistemacoil.modelo.pojo.Idioma;
import sistemacoil.modelo.pojo.OfertaColaboracionUV;
import sistemacoil.modelo.pojo.Periodo;
import sistemacoil.modelo.pojo.ProgramaEducativo;
import sistemacoil.observador.ObservadorColaboraciones;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;
import sistemacoil.utilidades.Validaciones;

public class FXMLFormularioOfertaColaboracionUVController implements Initializable {

    private int idProfesorUV;
    private boolean tipoOperacion = false;
    private OfertaColaboracionUV ofertaEdicion;
    private ObservadorColaboraciones observador;
    private ObservableList<Idioma> idiomas;
    private ObservableList<Periodo> periodos;
    private ObservableList<AreaAcademica> areasAcademicas;
    private ObservableList<Dependencia> dependencias;
    private ObservableList<ProgramaEducativo> programasEducativos;
    private ObservableList<ExperienciaEducativa> experienciasEducativas;

    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label lbTituloFormulario;
    @FXML
    private TextArea taObjetivoCurso;
    @FXML
    private TextArea taPerfilEstudiantes;
    @FXML
    private TextArea taTemasInteres;
    @FXML
    private TextArea taInformacionAdicional;
    @FXML
    private Button btnRegresar;
    @FXML
    private Label lbErrorObjetivoCurso;
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
    private Label lbErrorPerfilEstudiante;
    @FXML
    private Label lbErrorTemasInteres;
    @FXML
    private Label lbErrorInfoAdicional;
    @FXML
    private AnchorPane apVentana;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idiomas = FXCollections.observableArrayList();
        periodos = FXCollections.observableArrayList();
        areasAcademicas = FXCollections.observableArrayList();
        dependencias = FXCollections.observableArrayList();
        programasEducativos = FXCollections.observableArrayList();
        experienciasEducativas = FXCollections.observableArrayList();

        cargarIdiomas();
        cargarPeriodos();
        cargarAreasAcademicas();
        
        configurarListeners();
    }

    public void inicializarValores(OfertaColaboracionUV ofertaEdicion, int idProfesorUV, ObservadorColaboraciones observador) {
        this.ofertaEdicion = ofertaEdicion;
        this.idProfesorUV = idProfesorUV;
        this.observador = observador;

        if (this.ofertaEdicion != null) {
            tipoOperacion = true;
            lbTituloFormulario.setText(ofertaEdicion.getNombreColaboracion());
            btnRegistrar.setText("Guardar cambios");

            Integer idAreaAcademica = ofertaEdicion.getIdAreaAcademica();
            Integer idDependencia = ofertaEdicion.getIdDependencia();
            Integer idProgramaEducativo = ofertaEdicion.getIdProgramaEducativo();
            Integer idExperienciaEducativa = ofertaEdicion.getIdExperienciaEducativa();

            if (cbAreasAcademicas != null && idAreaAcademica != null) {
                cbAreasAcademicas.getSelectionModel().select(buscarIdAreaAcademica(idAreaAcademica));
                cargarDependencias(idAreaAcademica);
            }

            if (cbDependencias != null && idDependencia != null) {
                cbDependencias.getSelectionModel().select(buscarIdDependencia(idDependencia));
                cargarProgramasEducativos(idDependencia);
            }

            if (cbProgramasEducativos != null && idProgramaEducativo != null) {
                cbProgramasEducativos.getSelectionModel().select(buscarIdProgramaEducativo(idProgramaEducativo));
                cargarExperienciasEducativas(idProgramaEducativo);
            }
            
            if (cbExperienciasEducativas != null && idExperienciaEducativa != null) {
                cbExperienciasEducativas.getSelectionModel().select(buscarIdExperienciaEducativa(idExperienciaEducativa));
            }

            cargarInformacionOfertaEdicion(this.ofertaEdicion);
        }
    }

    public void configurarListeners() {
        Utils.agregarListenerSimple(tfNombre, Constantes.MAX_LONG_NOMBRE_COLAB);
        Utils.agregarListenerTextArea(taObjetivoCurso, Constantes.MAX_LONG_TEXTAREA);
        Utils.agregarListenerTextArea(taPerfilEstudiantes, Constantes.MAX_LONG_TEXTAREA);
        Utils.agregarListenerTextArea(taInformacionAdicional, Constantes.MAX_LONG_TEXTAREA);
        Utils.agregarListenerTextArea(taTemasInteres, Constantes.MAX_LONG_TEXTAREA);

        Utils.configurarListenerDesbloquearYLLenar(cbAreasAcademicas, cbDependencias,
                this::cargarDependencias, AreaAcademica::getIdAreaAcademica);

        Utils.configurarListenerDesbloquearYLLenar(cbDependencias, cbProgramasEducativos,
                this::cargarProgramasEducativos, Dependencia::getIdDependencia
        );

        Utils.configurarListenerDesbloquearYLLenar(cbProgramasEducativos, cbExperienciasEducativas,
                this::cargarExperienciasEducativas, ProgramaEducativo::getIdProgramaEducativo
        );
    }

    public void setIdProfesorUV(int idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
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

    private int buscarIdIdioma(int idIdioma){
        for(int i = 0; i < idiomas.size(); i++){
            if(idiomas.get(i).getIdIdioma() == idIdioma){
                return i;
            }
        }
        return 0;
    }

    private int buscarIdAreaAcademica(int idAreaAcademica){
        for(int i = 0; i < areasAcademicas.size(); i++){
            if(areasAcademicas.get(i).getIdAreaAcademica() == idAreaAcademica){
                return i;
            }
        }
        return 0;
    }

    private int buscarIdDependencia(int idDependencia){
        for(int i = 0; i < dependencias.size(); i++){
            if(dependencias.get(i).getIdDependencia() == idDependencia){
                return i;
            }
        }
        return 0;
    }

    private int buscarIdPeriodo(int idPeriodo){
        for(int i = 0; i < periodos.size(); i++){
            if(periodos.get(i).getIdPeriodo() == idPeriodo){
                return i;
            }
        }
        return 0;
    }

    private int buscarIdProgramaEducativo(int idProgramaEducativo){
        for(int i = 0; i < programasEducativos.size(); i++){
            if(programasEducativos.get(i).getIdProgramaEducativo() == idProgramaEducativo){
                return i;
            }
        }
        return 0;
    }

    private int buscarIdExperienciaEducativa(int idExperienciaEducativa){
        for(int i = 0; i < experienciasEducativas.size(); i++){
            if(experienciasEducativas.get(i).getIdExperienciaEducativa() == idExperienciaEducativa){
                return i;
            }
        }
        return 0;
    }

    private void cargarInformacionOfertaEdicion(OfertaColaboracionUV ofertaColaboracionUV) {
        tfNombre.setText(ofertaColaboracionUV.getNombreColaboracion());
        cbExperienciasEducativas.getSelectionModel().select(buscarIdExperienciaEducativa(ofertaColaboracionUV.getIdExperienciaEducativa()));
        taObjetivoCurso.setText(ofertaColaboracionUV.getObjetivoCurso());
        taPerfilEstudiantes.setText(ofertaColaboracionUV.getPerfilEstudiante());
        taTemasInteres.setText(ofertaColaboracionUV.getTemaInteres());
        taInformacionAdicional.setText(ofertaColaboracionUV.getInformacionAdicional());
        cbIdiomas.getSelectionModel().select(buscarIdIdioma(ofertaColaboracionUV.getIdIdioma()));
        cbAreasAcademicas.getSelectionModel().select(buscarIdAreaAcademica(ofertaColaboracionUV.getIdAreaAcademica()));
        cbDependencias.getSelectionModel().select(buscarIdDependencia(ofertaColaboracionUV.getIdDependencia()));
        cbProgramasEducativos.getSelectionModel().select(buscarIdProgramaEducativo(ofertaColaboracionUV.getIdProgramaEducativo()));
        cbPeriodos.getSelectionModel().select(buscarIdPeriodo(ofertaColaboracionUV.getIdPeriodo()));
    }

    private OfertaColaboracionUV obtenerInformacionOfertaColaboracionUV() {
        OfertaColaboracionUV ofertaColaboracionUV = new OfertaColaboracionUV();
        ofertaColaboracionUV.setNombreColaboracion(tfNombre.getText());
        ofertaColaboracionUV.setIdExperienciaEducativa(cbExperienciasEducativas.getSelectionModel().getSelectedItem().getIdExperienciaEducativa());
        ofertaColaboracionUV.setObjetivoCurso(taObjetivoCurso.getText());
        ofertaColaboracionUV.setPerfilEstudiante(taPerfilEstudiantes.getText());
        ofertaColaboracionUV.setTemaInteres(taTemasInteres.getText());
        ofertaColaboracionUV.setInformacionAdicional(taInformacionAdicional.getText());
        ofertaColaboracionUV.setIdIdioma(cbIdiomas.getSelectionModel().getSelectedItem().getIdIdioma());
        ofertaColaboracionUV.setIdAreaAcademica(cbAreasAcademicas.getSelectionModel().getSelectedItem().getIdAreaAcademica());
        ofertaColaboracionUV.setIdDependencia(cbDependencias.getSelectionModel().getSelectedItem().getIdDependencia());
        ofertaColaboracionUV.setIdPeriodo(cbPeriodos.getSelectionModel().getSelectedItem().getIdPeriodo());
        ofertaColaboracionUV.setIdProgramaEducativo(cbProgramasEducativos.getSelectionModel().getSelectedItem().getIdProgramaEducativo());
        ofertaColaboracionUV.setIdProfesorUV(this.idProfesorUV);
        if (ofertaEdicion != null) {
            ofertaColaboracionUV.setIdOfertaColaboracionUV(ofertaEdicion.getIdOfertaColaboracionUV());
        }
        return ofertaColaboracionUV;
    }

    private boolean validarCamposOferta() {
        List<Boolean> validaciones = Arrays.asList(
            Validaciones.validarCampo(tfNombre, lbErrorNombre),
            Validaciones.validarComboBox(cbIdiomas, lbErrorIdioma),
            Validaciones.validarComboBox(cbPeriodos, lbErrorPeriodo),
            Validaciones.validarComboBox(cbAreasAcademicas, lbErrorAreaAcademica),
            Validaciones.validarComboBox(cbDependencias, lbErrorDependencia),
            Validaciones.validarComboBox(cbProgramasEducativos, lbErrorProgramaEducativo),
            Validaciones.validarComboBox(cbExperienciasEducativas, lbErrorExperienciaEducativa),
            Validaciones.validarTextArea(taObjetivoCurso, lbErrorObjetivoCurso),
            Validaciones.validarTextArea(taPerfilEstudiantes, lbErrorPerfilEstudiante),
            Validaciones.validarTextArea(taTemasInteres, lbErrorTemasInteres),
            Validaciones.validarTextArea(taInformacionAdicional, lbErrorInfoAdicional)
        );

        return validaciones.stream().allMatch(Boolean::booleanValue);
    }

    private void registrarOfertaColaboracionUV(OfertaColaboracionUV ofertaColaboracionUV){
        HashMap<String, Object> respuesta = OfertaColaboracionUVDAO.registrarOfertaColaboracionUV(ofertaColaboracionUV);
        if(!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            Animaciones.reducirBrillo(apVentana);
            Utils.mostrarAlertaSimple("Registro exitoso", "La oferta de colaboración UV ha sido registrada correctamente", apVentana);
            Animaciones.restablecerBrillo(apVentana);
            //observador.operacionExitosa("Alta", ofertaColaboracionUV.getNombreColaboracion());
            //cerrarVentana();
        }else{
            Utils.mostrarAlertaSimple("Registro fallido", ""+respuesta.get(Constantes.KEY_MENSAJE), apVentana);
        }
    }

    private void modificarOfertaColaboracionUV(OfertaColaboracionUV ofertaColaboracionUV){
        HashMap<String, Object> respuesta = OfertaColaboracionUVDAO.modificarOfertaColaboracionUV(ofertaColaboracionUV);
        if(!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            Utils.mostrarAlertaSimple("Oferta modificada", "Los datos de la oferta de colaboración han sido modificados correctamente.", apVentana);
            observador.operacionExitosa("Modificación", ofertaColaboracionUV.getNombreColaboracion());
        }else{
            Utils.mostrarAlertaSimple("Registro fallido", "Ocurrió un error al registrar la oferta de colaboración", apVentana);
        }
    }

    private void cerrarVentana(){
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicRegistrarOferta(ActionEvent event) {
        Animaciones.animarPresionBoton(btnRegistrar);
        if(validarCamposOferta()) {
            OfertaColaboracionUV ofertaColaboracionUV = obtenerInformacionOfertaColaboracionUV();
            if(ofertaEdicion == null) {
                registrarOfertaColaboracionUV(ofertaColaboracionUV);
            } else {
                ofertaEdicion = obtenerInformacionOfertaColaboracionUV();
                modificarOfertaColaboracionUV(ofertaEdicion);
            }
        }
    }

    public void cargarDatosOferta(int idOfertaColaboracionUV) {
        HashMap<String, Object> resultado = OfertaColaboracionUVDAO.obtenerOfertaColaboracionUVPorId(idOfertaColaboracionUV);
        if (!(boolean) resultado.get(Constantes.KEY_ERROR)) {
            OfertaColaboracionUV ofertaColaboracionUV = (OfertaColaboracionUV) resultado.get("ofertaColaboracionUV");
            inicializarValores(ofertaColaboracionUV, idProfesorUV, observador);
        } else {
            Utils.mostrarAlertaSimple("Error", "No se pudo cargar la oferta seleccionada", apVentana);
        }
    }
}