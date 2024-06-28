/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacoil.controlador;

import sistemacoil.controlador.FXMLColabsController;
import sistemacoil.modelo.pojo.Periodo;
import sistemacoil.modelo.dao.ColaboracionDAO;
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
import sistemacoil.modelo.pojo.ProfesorUV;

/**
 * FXML Controller class
 *
 * @author lalom
 */
public class FXMLHistorialColabController implements Initializable {

    @FXML
    private TableView<Periodo> tvPeriodos;
    @FXML
    private TableColumn colHisPeriodos;
    
    private ColaboracionDAO colaboracionDAO;
    @FXML
    private AnchorPane apVentana;
    
    private boolean esCoordinador;
    private int idProfesorUV;
    private int idPeriodo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboracionDAO = new ColaboracionDAO();
        colHisPeriodos.setCellValueFactory(new PropertyValueFactory<>("nombrePeriodo"));
        
        // TODO
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
         Periodo periodoSeleccionado = tvPeriodos.getSelectionModel().getSelectedItem();
    if (periodoSeleccionado != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLColabs.fxml"));
            AnchorPane pane = loader.load();
            FXMLColabsController controller = loader.getController();
             controller.inicializarDatos(esCoordinador, idProfesorUV, periodoSeleccionado.getIdPeriodo());
            apVentana.getChildren().clear();
            apVentana.getChildren().add(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
             Utils.mostrarAlertaSimple("Error", "No se pudo cargar la vista de colaboraciones.",apVentana);
        }
    } else {
        Utils.mostrarAlertaSimple("Selección requerida", "Por favor, seleccione un periodo.", apVentana);
    }
    }
    
    public void inicializarCoordinador() {
        esCoordinador = true;
        cargarPeriodosConColaboraciones();
    }

    public void inicializarProfesorUV(int idProfesorUV) {
        this.idProfesorUV=idProfesorUV;
        esCoordinador = false;
        cargarPeriodosConColaboraciones();
    }

    private void cargarPeriodosConColaboraciones() {
        ArrayList<Periodo> periodosConColaboraciones;
        if (esCoordinador) {
            periodosConColaboraciones = colaboracionDAO.obtenerPeriodosConColaboraciones();
        } else {
            periodosConColaboraciones = colaboracionDAO.obtenerPeriodosConColaboracionesPorProfesorUV(idProfesorUV);
        }
        tvPeriodos.getItems().setAll(periodosConColaboraciones);
    }
}
