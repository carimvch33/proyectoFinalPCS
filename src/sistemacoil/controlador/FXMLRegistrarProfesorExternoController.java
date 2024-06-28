package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sistemacoil.modelo.dao.CatalogoDAO;
import sistemacoil.modelo.dao.ProfesorExternoDAO;
import sistemacoil.modelo.dao.UniversidadDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.modelo.pojo.Idioma;
import sistemacoil.modelo.pojo.OfertaColaboracionExterna;
import sistemacoil.modelo.pojo.Pais;
import sistemacoil.modelo.pojo.ProfesorExterno;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.modelo.pojo.Universidad;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;
import sistemacoil.utilidades.Validaciones;

public class FXMLRegistrarProfesorExternoController implements Initializable {

    private ObservableList<Pais> paises;
    private ObservableList<Idioma> idiomas;
    private ObservableList<Universidad> universidades;
    private ProfesorExterno profesorExterno;
    private ProfesorUV profesorUV;
    private Colaboracion colaboracion;
    private OfertaColaboracionExterna ofertaExterna;
    private FXMLRegistrarColaboracionController parentController;
    private FXMLFormularioOfertaColaboracionExternaController formularioController;
    
    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private Label lbErrorApPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private Label lbErrorApMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private ComboBox<Pais> cbPaises;
    @FXML
    private ComboBox<Idioma> cbIdiomas;
    @FXML
    private Label lbErrorIdioma;
    @FXML
    private ComboBox<Universidad> cbUniversidades;
    @FXML
    private Label lbErrorUniversidad;
    @FXML
    private TextField tfCarrera;
    @FXML
    private Label lbErrorCarrera;
    @FXML
    private TextField tfDepartamento;
    @FXML
    private Label lbErrorDepartamento;
    @FXML
    private Button btCancelarRegistro;
    @FXML
    private Label lbErrorPais;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Label lbErrorTelefono;
    @FXML
    private TextField tfLadaTelefono;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private Label lbTitulo;
    @FXML
    private Button btnRegistrarProfesorExterno;
    @FXML
    private Button btnLimpiarCampos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarListeners();
        cargarPaises();
        cargarIdiomas();
    }
    
    public void configurarListeners() {
        Utils.agregarListenerNombres(tfNombre, Constantes.MAX_LONG_NOMBRES);
        Utils.agregarListenerNombres(tfApellidoPaterno, Constantes.MAX_LONG_NOMBRES);
        Utils.agregarListenerNombres(tfApellidoMaterno, Constantes.MAX_LONG_NOMBRES);
        Utils.agregarListenerSimple(tfCarrera, Constantes.MAX_LONG_INSTITUCION);
        Utils.agregarListenerSimple(tfDepartamento, Constantes.MAX_LONG_INSTITUCION);
        Utils.agregarListenerTelefono(tfTelefono, Constantes.MAX_LONG_TELEFONO);
        Utils.agregarListenerCorreo(tfCorreo, Constantes.MAX_LONG_CORREO);
        
        cbPaises.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarUniversidades(newValue.getIdPais());
                cargarLadaTelefono(newValue.getLadaTelefono());
                cbUniversidades.setDisable(false);
            } else {
                cbUniversidades.setDisable(true);
            }
        });
    }
    
    public void setProfesorUV(ProfesorUV profesorUV) {
        this.profesorUV = profesorUV;
    }
    
    public void setProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;
    }
    
    public void setColaboracion(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
    }
    
    public void setOfertaExterna(OfertaColaboracionExterna ofertaExterna) {
        this.ofertaExterna = ofertaExterna;
    }

    public void setParentController(FXMLRegistrarColaboracionController parentController) {
        this.parentController = parentController;
    }
    
    public void setFormularioController(FXMLFormularioOfertaColaboracionExternaController formularioController) {
        this.formularioController = formularioController;
    }

    private void cargarPaises() {
        paises = FXCollections.observableArrayList();
        List<Pais> paisesBD = CatalogoDAO.obtenerPaises();
        paises.addAll(paisesBD);
        cbPaises.setItems(paises);
    }
    
    private void cargarIdiomas() {
        idiomas = FXCollections.observableArrayList();
        List<Idioma> idiomasBD = CatalogoDAO.obtenerIdiomas();
        idiomas.addAll(idiomasBD);
        cbIdiomas.setItems(idiomas);
    }

    private void cargarUniversidades(int idPais) {
        universidades = FXCollections.observableArrayList();
        List<Universidad> universidadesBD = UniversidadDAO.obtenerUniversidadesPorPais(idPais);
        universidades.addAll(universidadesBD);
        cbUniversidades.setItems(universidades);
    }

    private void cargarLadaTelefono(String ladaTelefono) {
        tfLadaTelefono.setText("+" + ladaTelefono);
    }
    
    private void limpiarCamposProfesorExterno(){
        Utils.limpiarCampo(tfNombre, lbErrorNombre);
        Utils.limpiarCampo(tfApellidoPaterno, lbErrorApPaterno);
        Utils.limpiarCampo(tfApellidoMaterno, lbErrorApMaterno);
        Utils.limpiarCampo(tfCorreo, lbErrorCorreo);
        Utils.limpiarCampo(tfTelefono, lbErrorTelefono);
        Utils.limpiarCampo(tfLadaTelefono, lbErrorTelefono);
        Utils.limpiarCampo(tfCarrera, lbErrorCarrera);
        Utils.limpiarCampo(tfDepartamento, lbErrorDepartamento);
        Utils.limpiarComboBox(cbPaises, lbErrorPais);
        Utils.limpiarComboBox(cbIdiomas, lbErrorIdioma);
        Utils.limpiarComboBox(cbUniversidades, lbErrorUniversidad);
    }
    
    private boolean validarCamposProfesor() {
        List<Boolean> validaciones = Arrays.asList(
            Validaciones.validarCampo(tfNombre, lbErrorNombre),
            Validaciones.validarCampo(tfApellidoPaterno, lbErrorApPaterno),
            Validaciones.validarCampo(tfApellidoMaterno, lbErrorApMaterno),
            Validaciones.validarCorreo(tfCorreo, lbErrorCorreo),
            Validaciones.validarCampo(tfTelefono, lbErrorTelefono),
            Validaciones.validarCampo(tfLadaTelefono, lbErrorTelefono),
            Validaciones.validarCampo(tfDepartamento, lbErrorDepartamento),
            Validaciones.validarCampo(tfCarrera, lbErrorCarrera),
            Validaciones.validarComboBox(cbPaises, lbErrorPais),
            Validaciones.validarComboBox(cbIdiomas, lbErrorIdioma),
            Validaciones.validarComboBox(cbUniversidades, lbErrorUniversidad)
        );
        return validaciones.stream().allMatch(Boolean::booleanValue);
    }
    
    private Pais buscarIdPais(int id) {
        if (paises != null && !paises.isEmpty())
            return paises.stream().filter(e -> e.getIdPais() == id).findFirst().orElse(null);
        else
            return null;
    }
    
    private Idioma buscarIdIdioma(int id) {
        if (idiomas != null && !idiomas.isEmpty())
            return idiomas.stream().filter(e -> e.getIdIdioma() == id).findFirst().orElse(null);
        else
            return null;
    }
    
    private Universidad buscarIdUniversidad(int id) {
        if (universidades != null && !universidades.isEmpty())
            return universidades.stream().filter(e -> e.getIdUniversidad() == id).findFirst().orElse(null);
        else
            return null;
    }
    
    private ProfesorExterno guardarDatosProfesorExterno() {
        ProfesorExterno profesorExterno = new ProfesorExterno();
        profesorExterno.setNombre(tfNombre.getText());
        profesorExterno.setApellidoPaterno(tfApellidoPaterno.getText());
        profesorExterno.setApellidoMaterno(tfApellidoMaterno.getText());
        profesorExterno.setDepartamento(tfDepartamento.getText());
        profesorExterno.setCarrera(tfCarrera.getText());
        profesorExterno.setCorreo(tfCorreo.getText());
        profesorExterno.setTelefono(tfLadaTelefono.getText() + " " + tfTelefono.getText());

        if (cbUniversidades.getSelectionModel().getSelectedItem() != null)
            profesorExterno.setIdUniversidad(cbUniversidades.getSelectionModel().
                    getSelectedItem().getIdUniversidad());

        if (cbIdiomas.getSelectionModel().getSelectedItem() != null)
            profesorExterno.setIdIdioma(cbIdiomas.getSelectionModel().
                    getSelectedItem().getIdIdioma());

        if (cbPaises.getSelectionModel().getSelectedItem() != null)
            profesorExterno.setIdPais(cbPaises.getSelectionModel().
                    getSelectedItem().getIdPais());

        return profesorExterno;
    }
    
    public void cargarDatosProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;

        if (profesorExterno != null) {
            lbTitulo.setText("Editar Profesor Externo");
            btnRegistrarProfesorExterno.setText("Editar Profesor Externo");
            btCancelarRegistro.setText("Cancelar Edición");
            tfNombre.setText(profesorExterno.getNombre());
            tfApellidoPaterno.setText(profesorExterno.getApellidoPaterno());
            tfApellidoMaterno.setText(profesorExterno.getApellidoMaterno());
            tfCorreo.setText(profesorExterno.getCorreo());
            tfDepartamento.setText(profesorExterno.getDepartamento());
            tfCarrera.setText(profesorExterno.getCarrera());
            cbPaises.getSelectionModel().select(buscarIdPais(profesorExterno.getIdPais()));
            cbIdiomas.getSelectionModel().select(buscarIdIdioma(profesorExterno.getIdIdioma()));
            cbUniversidades.getSelectionModel().select(buscarIdUniversidad(profesorExterno.getIdUniversidad()));

            String telefono = profesorExterno.getTelefono();
            if (telefono.length() >= 12) {
                String numero = telefono.substring(telefono.indexOf(" ") + 1);
                tfTelefono.setText(numero);
            } else {
                tfTelefono.setText("");
            }
            btnLimpiarCampos.setVisible(false);
            btnLimpiarCampos.setDisable(true);
        } else {
            lbTitulo.setText("Registrar Profesor Externo");
            btnRegistrarProfesorExterno.setText("Registrar Profesor Externo");
            btCancelarRegistro.setText("Cancelar Registro");
            limpiarCamposProfesorExterno();
            btnLimpiarCampos.setVisible(true);
            btnLimpiarCampos.setDisable(false);
        }
    }

    private void irARegistrarColaboracion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLRegistrarColaboracion.fxml"));
            AnchorPane registrarColaboracionPane = loader.load();

            FXMLRegistrarColaboracionController controller = loader.getController();
            controller.setProfesorExterno(profesorExterno);
            controller.cargarDatosColaboracion(colaboracion);
            controller.setProfesorUV(profesorUV);

            apVentana.getChildren().setAll(registrarColaboracionPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void irARegistrarOfertaExterna() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLFormularioOfertaColaboracionExterna.fxml"));
            AnchorPane registrarColaboracionPane = loader.load();

            FXMLFormularioOfertaColaboracionExternaController controller = loader.getController();
            controller.setProfesorExterno(profesorExterno);
            controller.cargarDatosOfertaExterna(ofertaExterna);

            apVentana.getChildren().setAll(registrarColaboracionPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean camposEstanVacios() {
        return tfNombre.getText().isEmpty() &&
               tfApellidoPaterno.getText().isEmpty() &&
               tfApellidoMaterno.getText().isEmpty() &&
               tfCorreo.getText().isEmpty() &&
               tfTelefono.getText().isEmpty() &&
               tfLadaTelefono.getText().isEmpty() &&
               tfCarrera.getText().isEmpty() &&
               tfDepartamento.getText().isEmpty() &&
               cbPaises.getSelectionModel().isEmpty() &&
               cbIdiomas.getSelectionModel().isEmpty() &&
               cbUniversidades.getSelectionModel().isEmpty();
    }
    
    public static int registrarProfesorExterno(ProfesorExterno profesorExterno) {
        return ProfesorExternoDAO.registrarProfesorExterno(profesorExterno);
    }

    @FXML
    private void btnClicLimpiarCampos(ActionEvent event) {
        Animaciones.animarPresionBoton(btnLimpiarCampos);
        limpiarCamposProfesorExterno();
    }
    
    @FXML
    private void btnClicCancelarRegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLRegistrarColaboracion.fxml"));
            AnchorPane registrarColaboracionPane = loader.load();
            FXMLRegistrarColaboracionController controller = loader.getController();
            controller.setProfesorExterno(profesorExterno);
            controller.cargarDatosColaboracion(colaboracion);
            controller.setProfesorUV(profesorUV);
            apVentana.getChildren().setAll(registrarColaboracionPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicRegistrarProfesorExterno(ActionEvent event) {
        if (validarCamposProfesor() && !camposEstanVacios()) {
            profesorExterno = guardarDatosProfesorExterno();
            if (colaboracion != null) {
                parentController.setProfesorExterno(profesorExterno);
                irARegistrarColaboracion();
            } else {    
                formularioController.setProfesorExterno(profesorExterno);
                irARegistrarOfertaExterna();
            }
        }
    }
}
