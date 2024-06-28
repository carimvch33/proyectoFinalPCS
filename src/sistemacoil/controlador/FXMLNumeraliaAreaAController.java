/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacoil.controlador;

import sistemacoil.modelo.dao.NumeraliaDAO;
import sistemacoil.modelo.pojo.NumeraliaAreaAcademica;
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
public class FXMLNumeraliaAreaAController implements Initializable {

    private ObservableList<NumeraliaAreaAcademica> colaboraciones;
    
    @FXML
    private TableView<NumeraliaAreaAcademica> tvNumeAreaAcademica;
    @FXML
    private TableColumn colAreaAcademica;
    @FXML
    private TableColumn colProfesores;
    @FXML
    private TableColumn colEstudiantes;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        inicializarValores();
    }
    void inicializarValores(){
        cargarDatosAreaAcademica();
    }
    
    private void configurarTabla(){
        colAreaAcademica.setCellValueFactory(new PropertyValueFactory<>("areaAcademica"));
        colProfesores.setCellValueFactory(new PropertyValueFactory<>("numeroDeProfesores"));
        colEstudiantes.setCellValueFactory(new PropertyValueFactory<>("numeroDeEstudiantes"));
    }

    private void cargarDatosAreaAcademica() {
        colaboraciones = FXCollections.observableArrayList();
        NumeraliaDAO dao = new NumeraliaDAO();
        HashMap<String, Object> respuesta = dao.consultarNumeraliaAreaAcademica();

        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<NumeraliaAreaAcademica> colaboracionesBD = (ArrayList<NumeraliaAreaAcademica>) respuesta.get("colaboraciones");
            colaboraciones.addAll(colaboracionesBD);
            tvNumeAreaAcademica.setItems(colaboraciones);
        } else {
            //Utils.mostrarAlertaSimple("Error", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnDescargar(ActionEvent event) {
        String encabezados="Area Academica, Numero de Profesores, Numero de estudiantes";
        DirectoryChooser directorio=new DirectoryChooser();
        directorio.setTitle("Seleccione una carpeta");
        File directorioDestino=directorio.showDialog(tvNumeAreaAcademica.getScene().getWindow());
        if(directorioDestino !=null){
            try{
                String ruta= directorioDestino.getAbsolutePath()+"/NumeraliaAreaAcademica.csv";
                File archivoExportado=new File(ruta);
                Writer archivoEscritura= new BufferedWriter(new FileWriter(archivoExportado));
                archivoEscritura.write(encabezados);
                for(NumeraliaAreaAcademica numeralia: colaboraciones){
                    String fila=String.format("\n%s,%s,%s", numeralia.getAreaAcademica(),""+numeralia.getNumeroDeProfesores(),""+numeralia.getNumeroDeEstudiantes());
                    archivoEscritura.write(fila);
                }
                archivoEscritura.close();
                //Utils.mostrarAlertaSimple("ArchivoExportado", "El archivo fue exportado correctamente", Alert.AlertType.INFORMATION);
            }catch(IOException e){
                //Utils.mostrarAlertaSimple("Error al exportar", e.getMessage(), Alert.AlertType.ERROR);
                
            }
        }
    }

    @FXML
    private void btnRegresar(ActionEvent event) {
    }
     
}
