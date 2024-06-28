/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacoil.controlador;

import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.utilidades.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lalom
 */
public class FXMLConsultarActividadesController implements Initializable {

    @FXML
    private TableView<Colaboracion> tvColaboracion;
    @FXML
    private TableColumn colColaboracion;
    
    private ObservableList<Colaboracion> colaboraciones;
    private ColaboracionDAO colaboracionDAO;
    private Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboracionDAO = new ColaboracionDAO();
        colColaboracion.setCellValueFactory(new PropertyValueFactory<>("nombreColaboracion"));
        cargarDatos();
    }    

    
    private void cargarDatos() {
        tvColaboracion.getItems().addAll(colaboracionDAO.obtenerActividadesConEvidencia());
    }

    @FXML
     private void btnSiguiente() {
        Colaboracion colaboracionSeleccionada = tvColaboracion.getSelectionModel().getSelectedItem();
        if (colaboracionSeleccionada != null) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Seleccione una carpeta");
            File directorioDestino = directoryChooser.showDialog(primaryStage);
            if (directorioDestino != null) {
                try {
                    String ruta = directorioDestino.getAbsolutePath() + "/" + colaboracionSeleccionada.getNombreColaboracion() + "Actividad.pdf";
                    File archivoDestino = new File(ruta);
                    byte[] contenido = colaboracionDAO.obtenerContenidoEvidencia(colaboracionSeleccionada.getIdColaboracion());
                    if (contenido != null) {
                        OutputStream outputStream = new FileOutputStream(archivoDestino);
                        outputStream.write(contenido);
                        outputStream.close();
                        //Utils.mostrarAlertaSimple("Descarga Exitosa", "La actividad fue descargada correctamente.", Alert.AlertType.INFORMATION);
                    } else {
                        //Utils.mostrarAlertaSimple("Error", "No se encontró evidencia para esta colaboración.", Alert.AlertType.ERROR);
                    }
                } catch (IOException e) {
                    //Utils.mostrarAlertaSimple("Error al exportar", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        } else {
            //Utils.mostrarAlertaSimple("Error", "Seleccione una colaboración de la tabla.", Alert.AlertType.WARNING);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
   
    
    
}
