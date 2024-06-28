package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import sistemacoil.modelo.dao.OfertaColaboracionUVDAO;
import sistemacoil.modelo.pojo.OfertaColaboracionUV;
import sistemacoil.observador.ObservadorColaboraciones;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Utils;

public class FXMLSeleccionarOfertaController implements Initializable{

    private int idProfesorUV;
    private List<OfertaColaboracionUV> ofertas;
    private ObservableList<OfertaColaboracionUV> ofertasColaboracionUV;
    private String estado;

    @FXML
    private TableView<OfertaColaboracionUV> tvOfertas;
    @FXML
    private TableColumn<OfertaColaboracionUV, String> colNombre;
    @FXML
    private TableColumn<OfertaColaboracionUV, String> colRegion;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableColumn<OfertaColaboracionUV, String> colExperienciaEducativa;
    @FXML
    private TableColumn<OfertaColaboracionUV, String> colPeriodo;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private TextField tfBuscarOferta;
    @FXML
    private Label lbTitulo;
    @FXML
    private Button btnModificar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreColaboracion"));
        colExperienciaEducativa.setCellValueFactory(new PropertyValueFactory<>("experienciaEducativa"));
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("campus"));
        TableColumn<OfertaColaboracionUV, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idOfertaColaboracionUV"));
        tvOfertas.getColumns().add(colId);
        configurarSegunEstado(estado);
    }

    public void setIdProfesorUV(int idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
        cargarOfertas();
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
        cargarOfertas();
    }

    private void cargarOfertas() {
        if (estado == null) {
            ofertas = OfertaColaboracionUVDAO.obtenerOfertasUVProfesorUV(idProfesorUV);
        } else {
            ofertas = OfertaColaboracionUVDAO.obtenerOfertasColaboracionUVEnEspera();
        } 
        tvOfertas.getItems().setAll(ofertas);
    }
    
    private void configurarSegunEstado(String estado) {
        if (estado == null) {
            btnModificar.setText("Modificar Oferta");
            lbTitulo.setText("Modificar Oferta de Colaboración");
        } else {
            btnModificar.setText("Revisar Oferta");
            lbTitulo.setText("Revisar Oferta de Colaboración");
        }
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicCancelarBusqueda(ActionEvent event) {
        tfBuscarOferta.clear();
        cargarOfertas();
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicModificar(ActionEvent event) {
        OfertaColaboracionUV ofertaSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        
        if (ofertaSeleccionada != null) {
            if (estado == null) {
                abrirFormularioEdicion(ofertaSeleccionada, idProfesorUV);
            } else {
                abrirDetallesOferta(ofertaSeleccionada);
            }
        }
    }

    private void abrirFormularioEdicion(OfertaColaboracionUV ofertaColaboracionUV, int idProfesorUV) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLFormularioOfertaColaboracionUV.fxml"));
            AnchorPane root = loader.load();

            FXMLFormularioOfertaColaboracionUVController controlador = loader.getController();
            controlador.inicializarValores(ofertaColaboracionUV, idProfesorUV, new ObservadorColaboraciones() {
                @Override
                public void operacionExitosa(String tipoOperacion, String nombreColaboracion) {
                    System.out.println("Operación: " + tipoOperacion);
                    System.out.println("Oferta: " + nombreColaboracion);
                    cargarOfertas();
                }
            });

            apVentana.getChildren().clear();
            apVentana.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirDetallesOferta(OfertaColaboracionUV ofertaColaboracionUV) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLDetallesOferta.fxml"));
            AnchorPane root = loader.load();

            FXMLDetallesOfertaController controlador = loader.getController();
            controlador.setOfertaColaboracion(ofertaColaboracionUV);

            apVentana.getChildren().clear();
            apVentana.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
