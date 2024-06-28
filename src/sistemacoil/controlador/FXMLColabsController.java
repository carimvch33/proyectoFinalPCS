/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacoil.controlador;

import sistemacoil.controlador.FXMLInfoColaboracionesController;
import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.utilidades.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sistemacoil.modelo.pojo.Periodo;

/**
 * FXML Controller class
 *
 * @author lalom
 */
public class FXMLColabsController implements Initializable {

    @FXML
    private TableView<Colaboracion> tvColaboraciones;
    @FXML
    private TableColumn colColabs;
    
    @FXML
    private AnchorPane apVentana;
    
    private ColaboracionDAO colaboracionDAO;
    
    private boolean esCoordinador;
    private int idProfesorUV;
    private int idPeriodo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboracionDAO = new ColaboracionDAO();
        colColabs.setCellValueFactory(new PropertyValueFactory<>("nombreColaboracion"));
    }
    
    public void inicializarCoordinador(int idPeriodo) {
        this.idPeriodo=idPeriodo;
        esCoordinador = true;
       
    }

    public void inicializarProfesorUV(int idProfesorUV, int idPeriodo) {
        this.idProfesorUV=idProfesorUV;
        this.idPeriodo=idPeriodo;
        esCoordinador = false;
        
    }
    
    public void inicializarDatos(boolean esCoordinador, int idProfesorUV, int idPeriodo) {
    this.idPeriodo = idPeriodo;
    this.idProfesorUV = idProfesorUV;
    this.esCoordinador = esCoordinador;
    
    cargarColaboraciones();
}
    
    public void cargarColaboraciones() {
    ArrayList<Colaboracion> colaboraciones;
    
    if (esCoordinador) {
        colaboraciones = colaboracionDAO.obtenerColaboracionesPorPeriodo(idPeriodo);
    } else {
        colaboraciones = colaboracionDAO.obtenerColaboracionesPorPeriodoConIdProfUV(idPeriodo, idProfesorUV);
    }
    tvColaboraciones.getItems().clear();
    tvColaboraciones.getItems().setAll(colaboraciones);
}
    
    private void cargarEscena(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL fxmlUrl = getClass().getResource(fxmlPath);
            loader.setLocation(fxmlUrl);
            AnchorPane pane = loader.load();
            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    

    @FXML
    private void btnclicSiguiente(ActionEvent event) {
      Colaboracion colaboracionSeleccionada = tvColaboraciones.getSelectionModel().getSelectedItem();
    if (colaboracionSeleccionada != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLInfoColaboraciones.fxml"));
            AnchorPane pane = loader.load();
            
            FXMLInfoColaboracionesController controller = loader.getController();
            controller.setColaboracion(colaboracionSeleccionada);
            
            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
            Utils.mostrarAlertaSimple("Error", "No se pudo cargar la vista de información de la colaboración.", apVentana);
        }
    } else {
        Utils.mostrarAlertaSimple("Selección requerida", "Por favor, seleccione una colaboración.", apVentana);
    }
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLHistorialColab.fxml"));
            AnchorPane pane = loader.load();
        
            FXMLHistorialColabController controller = loader.getController();
            if (esCoordinador) {
                controller.inicializarCoordinador();
            } else {
                controller.inicializarProfesorUV(idProfesorUV);
            }

            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
            } catch (IOException ex) {
                ex.printStackTrace();
                Utils.mostrarAlertaSimple("Error", "No se pudo cargar la vista del historial de colaboraciones.", apVentana);
            }
        }

    
}
