package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
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
import sistemacoil.modelo.dao.OfertaColaboracionExternaDAO;
import sistemacoil.modelo.dao.UniversidadDAO;
import sistemacoil.modelo.pojo.Idioma;
import sistemacoil.modelo.pojo.OfertaColaboracionExterna;
import sistemacoil.modelo.pojo.Pais;
import sistemacoil.modelo.pojo.ProfesorExterno;
import sistemacoil.modelo.pojo.Universidad;
import sistemacoil.observador.ObservadorColaboraciones;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;
import sistemacoil.utilidades.Validaciones;

/**
 * FXML Controller class
 *
 */
public class FXMLFormularioOfertaColaboracionExternaController implements Initializable {

    private ObservadorColaboraciones observador;
    private ObservableList<Idioma> idiomas;
    private ObservableList<Pais> paises;
    private ObservableList<Universidad> universidades;
    private ProfesorExterno profesorExterno;
    private OfertaColaboracionExterna ofertaExterna;
        private OfertaColaboracionExternaDAO ofertaColaboracionExternaDAO; // Declaración del DAO


    @FXML
    private AnchorPane apVentana;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private ComboBox<Pais> cbPaises;
    @FXML
    private Label lbErrorPais;
    @FXML
    private ComboBox<Idioma> cbIdiomas;
    @FXML
    private Label lbErrorIdioma;
    @FXML
    private Label lbErrorProfesorExterno;
    @FXML
    private TextField tfCarrera;
    @FXML
    private Label lbErrorCarrera;
    @FXML
    private Label lbErrorAsignatura;
    @FXML
    private Label lbErrorDepartamento;
    @FXML
    private ComboBox<Universidad> cbUniversidades;
    @FXML
    private Label lbErrorUniversidad;
    @FXML
    private Button btnCancelarRegistro;
    @FXML
    private Button btnRegistrar;
    @FXML
    private TextField tfProfesorExterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfAsignatura;
    @FXML
    private TextField tfDepartamento;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private TextField tfDuracion;
    @FXML
    private Label lbErrorDuracion;
    @FXML
    private Button btnRegistrarProfeExterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ofertaColaboracionExternaDAO = new OfertaColaboracionExternaDAO(); // Inicialización del DAO
        ofertaExterna = new OfertaColaboracionExterna();
        cargarPaises();
        cargarIdiomas();
        configurarListeners();
    }

    public void inicializarValores(ObservadorColaboraciones observador) {
        this.observador = observador;
    }

    public void configurarListeners() {
        Utils.agregarListenerSimple(tfNombre, Constantes.MAX_LONG_NOMBRE_COLAB);
        Utils.agregarListenerCorreo(tfCorreo, Constantes.MAX_LONG_CORREO);
        Utils.configurarListenerDesbloquearYLLenar(cbPaises, cbUniversidades,
                this::cargarUniversidades, Pais::getIdPais);
    }
    
    public void setProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;
        if (profesorExterno != null) {
            tfProfesorExterno.setText(profesorExterno.getApellidoPaterno() + "-" + profesorExterno.getApellidoMaterno()
                    + ", " + profesorExterno.getNombre());
        }
    }

    private void cargarIdiomas() {
        idiomas = FXCollections.observableArrayList();
        List<Idioma> idiomasBD = CatalogoDAO.obtenerIdiomas();
        idiomas.addAll(idiomasBD);
        cbIdiomas.setItems(idiomas);
    }

    private void cargarPaises() {
        paises = FXCollections.observableArrayList();
        List<Pais> paisesBD = CatalogoDAO.obtenerPaises();
        paises.addAll(paisesBD);
        cbPaises.setItems(paises);
    }

    private void cargarUniversidades(int idPais) {
        universidades = FXCollections.observableArrayList();
        List<Universidad> universidadesBD = UniversidadDAO.obtenerUniversidadesPorPais(idPais);
        universidades.addAll(universidadesBD);
        cbUniversidades.setItems(universidades);
    }

    private boolean validarCamposOferta() {
        List<Boolean> validaciones = Arrays.asList(
                Validaciones.validarCampo(tfNombre, lbErrorNombre),
                Validaciones.validarCampo(tfProfesorExterno, lbErrorProfesorExterno),
                Validaciones.validarCorreo(tfCorreo, lbErrorCorreo),
                Validaciones.validarCampo(tfDepartamento, lbErrorDepartamento),
                Validaciones.validarCampo(tfCarrera, lbErrorCarrera),
                Validaciones.validarCampo(tfAsignatura, lbErrorAsignatura),
                Validaciones.validarComboBox(cbPaises, lbErrorPais),
                Validaciones.validarComboBox(cbIdiomas, lbErrorIdioma),
                Validaciones.validarComboBox(cbUniversidades, lbErrorUniversidad),
                Validaciones.validarCampo(tfDuracion, lbErrorDuracion)
        );
        return validaciones.stream().allMatch(Boolean::booleanValue);
    }

    private OfertaColaboracionExterna guardarDatosOfertaExterna() {
        ofertaExterna = new OfertaColaboracionExterna();
        ofertaExterna.setNombreColaboracion(tfNombre.getText().trim());
        ofertaExterna.setCorreo(tfCorreo.getText().trim());
        ofertaExterna.setCarrera(tfCarrera.getText().trim());
        ofertaExterna.setAsignatura(tfAsignatura.getText().trim());
        ofertaExterna.setDepartamento(tfDepartamento.getText().trim());
        ofertaExterna.setDuracion(tfDuracion.getText().trim());
        
        if (cbIdiomas.getSelectionModel().getSelectedItem() != null) {
            ofertaExterna.setIdIdioma(cbIdiomas.getSelectionModel().getSelectedItem().getIdIdioma());
        }

        if (cbPaises.getSelectionModel().getSelectedItem() != null) {
            ofertaExterna.setIdPais(cbPaises.getSelectionModel().getSelectedItem().getIdPais());
        }

        if (cbUniversidades.getSelectionModel().getSelectedItem() != null) {
            ofertaExterna.setIdUniversidad(cbUniversidades.getSelectionModel().getSelectedItem().getIdUniversidad());
        }

        return ofertaExterna;
    }

    public void cargarDatosOfertaExterna(OfertaColaboracionExterna ofertaExterna) {
        this.ofertaExterna = ofertaExterna;
        if (ofertaExterna != null) {
            tfNombre.setText(ofertaExterna.getNombreColaboracion()!= null ? ofertaExterna.getNombreColaboracion(): "");
            tfCorreo.setText(ofertaExterna.getCorreo() != null ? ofertaExterna.getCorreo() : "");
            tfCarrera.setText(ofertaExterna.getCarrera() != null ? ofertaExterna.getCarrera() : "");
            tfAsignatura.setText(ofertaExterna.getAsignatura() != null ? ofertaExterna.getAsignatura() : "");
            tfDepartamento.setText(ofertaExterna.getDepartamento() != null ? ofertaExterna.getDepartamento() : "");
            tfDuracion.setText(ofertaExterna.getDuracion() != null ? ofertaExterna.getDuracion() : "");

            if (ofertaExterna.getIdIdioma() != null) {
                Idioma idioma = buscarIdIdioma(ofertaExterna.getIdIdioma());
                if (idioma != null) {
                    cbIdiomas.getSelectionModel().select(idioma);
                }
            }

            if (ofertaExterna.getIdPais() != null) {
                Pais pais = buscarIdPais(ofertaExterna.getIdPais());
                if (pais != null) {
                    cbPaises.getSelectionModel().select(pais);
                }
            }

            if (ofertaExterna.getIdUniversidad() != null) {
                Universidad universidad = buscarIdUniversidad(ofertaExterna.getIdUniversidad());
                if (universidad != null) {
                    cbUniversidades.getSelectionModel().select(universidad);
                }
            }
        }
    }   
    
    private Idioma buscarIdIdioma(int id) {
        if (cbIdiomas != null) {
            return cbIdiomas.getItems().stream().filter(p -> p.getIdIdioma() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    private Pais buscarIdPais(int id) {
        if (cbPaises != null) {
            return cbPaises.getItems().stream().filter(p -> p.getIdPais() == id).findFirst().orElse(null);
        } else {
            return null;
        }
    }

    private Universidad buscarIdUniversidad(int id) {
        if (cbUniversidades != null) {
            return cbUniversidades.getItems().stream().filter(p -> p.getIdUniversidad() == id).findFirst().orElse(null);
        } else {
            return null;
        }

    }

     private void registrarOfertaColaboracionExterna(OfertaColaboracionExterna ofertaColaboracionExterna) {
        if (ofertaColaboracionExternaDAO != null) { // Verifica si el DAO no es nulo
            HashMap<String, Object> respuesta = ofertaColaboracionExternaDAO.registrarOfertaColaboracionExterna(ofertaColaboracionExterna);
            if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
                Utils.mostrarAlertaSimple("Registro fallido", "La oferta de colaboración externa no ha podido ser registrada", apVentana);
                if (observador != null) {
                    observador.operacionExitosa("Alta", ofertaColaboracionExterna.getNombreColaboracion());
                }
            } else {
                Utils.mostrarAlertaSimple("Registro exitoso", "La oferta de colaboración externa ha sido registrada correctamente", apVentana);
            }
        } else {
            Utils.mostrarAlertaSimple("Error de inicialización", "El DAO de oferta de colaboración externa no ha sido inicializado correctamente", apVentana);
        }
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
    }

    @FXML
    private void btnClicRegistrarProfesorExterno(ActionEvent event) {
        Animaciones.animarPresionBoton(btnRegistrarProfeExterno);
        try {
            guardarDatosOfertaExterna();

            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource(sistemacoil.utilidades.Escenas.REGISTRAR_PROFESOR_EXTERNO);
            loader.setLocation(fxmlUrl);
            AnchorPane registrarProfesorExternoPane = loader.load();

            FXMLRegistrarProfesorExternoController controller = loader.getController();
            controller.setOfertaExterna(ofertaExterna);
            controller.setFormularioController(this);

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
    }

    @FXML
    private void btnClicRegistrarOferta(ActionEvent event) {
        if (validarCamposOferta()) {
            OfertaColaboracionExterna oferta = guardarDatosOfertaExterna();
            registrarOfertaColaboracionExterna(oferta);
        }
    }
}