/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacoil.controlador;

import sistemacoil.modelo.dao.NumeraliaDAO;
import sistemacoil.modelo.pojo.NumeraliaRegion;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author lalom
 */
public class FXMLNumeraliaRegionController implements Initializable {

    @FXML
    private TableView<NumeraliaRegion> tvNumeRegion;
    @FXML
    private TableColumn colRegion;
    @FXML
    private TableColumn colProfesores;
    @FXML
    private TableColumn colEstudiantes;
    
    private ObservableList<NumeraliaRegion> colaboraciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosRegion();
    }    
    private void configurarTabla() {
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        colProfesores.setCellValueFactory(new PropertyValueFactory<>("numeroDeProfesores"));
        colEstudiantes.setCellValueFactory(new PropertyValueFactory<>("numeroDeEstudiantes"));
    }

    private void cargarDatosRegion() {
        colaboraciones = FXCollections.observableArrayList();
        NumeraliaDAO dao = new NumeraliaDAO();
        HashMap<String, Object> respuesta = dao.consultarNumeraliaRegion();

        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<NumeraliaRegion> colaboracionesBD = (ArrayList<NumeraliaRegion>) respuesta.get("colaboraciones");
            colaboraciones.addAll(colaboracionesBD);
            tvNumeRegion.setItems(colaboraciones);
        } else {
           // Utils.mostrarAlertaSimple("Error", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnRegresar(ActionEvent event) {
    }

    @FXML
    private void btnDescargar(ActionEvent event) {
        String encabezados="Region, Numero de Profesores, Numero de estudiantes";
        DirectoryChooser directorio=new DirectoryChooser();
        directorio.setTitle("Seleccione una carpeta");
        File directorioDestino=directorio.showDialog(tvNumeRegion.getScene().getWindow());
        if(directorioDestino !=null){
            try{
                String ruta= directorioDestino.getAbsolutePath()+"/NumeraliaRegion.csv";
                File archivoExportado=new File(ruta);
                Writer archivoEscritura= new BufferedWriter(new FileWriter(archivoExportado));
                archivoEscritura.write(encabezados);
                for(NumeraliaRegion numeralia: colaboraciones){
                    String fila=String.format("\n%s,%s,%s", numeralia.getRegion(),""+numeralia.getNumeroDeProfesores(),""+numeralia.getNumeroDeEstudiantes());
                    archivoEscritura.write(fila);
                }
                archivoEscritura.close();
               // Utils.mostrarAlertaSimple("ArchivoExportado", "El archivo fue exportado correctamente", Alert.AlertType.INFORMATION);
            }catch(IOException e){
               // Utils.mostrarAlertaSimple("Error al exportar", e.getMessage(), Alert.AlertType.ERROR);
                
            }
        }
    }
}
