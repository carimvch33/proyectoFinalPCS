/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemacoil.controlador;

import sistemacoil.modelo.pojo.Colaboracion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.utilidades.Constantes;

/**
 * FXML Controller class
 *
 * @author lalom
 */
public class FXMLInfoColaboracionesController implements Initializable {

    @FXML
    private TextField tfNombreColab;
    @FXML
    private TextField tfIdioma;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfFechaInicio;
    @FXML
    private TextField tfFechaConclusion;
    @FXML
    private TextField tfAreaAcademica;
    @FXML
    private TextField tfDependencia;
    @FXML
    private TextField tfProgramaEducativo;
    @FXML
    private TextField tfExperienciaEducativa;
    @FXML
    private TextField tfProfesorUV;
    @FXML
    private TextField tfProfesorExterno;
    @FXML
    private TextArea taObjetivosCurso;
    @FXML
    private TextArea taPerfilEstudiantes;
    @FXML
    private TextArea taInfoAdicional;
    @FXML
    private TextArea taTemasInteres;

    private int idColaboracion;
    private FXMLColabsController parentController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setIdColaboracion(int idColaboracion) {
        this.idColaboracion = idColaboracion;
       
    }
    
    public void setParentController(FXMLColabsController parentController) {
    this.parentController = parentController;
    }


    public void setColaboracion(Colaboracion colaboracionSeleccionada) {
        if (colaboracionSeleccionada != null) {
            tfNombreColab.setText(colaboracionSeleccionada.getNombreColaboracion());
            tfIdioma.setText(colaboracionSeleccionada.getIdioma());
            tfPeriodo.setText(colaboracionSeleccionada.getPeriodo());
            tfFechaInicio.setText(colaboracionSeleccionada.getFechaInicio().format(Constantes.FORMATO_FECHAS));
            tfFechaConclusion.setText(colaboracionSeleccionada.getFechaConclusion().format(Constantes.FORMATO_FECHAS));
            tfAreaAcademica.setText(colaboracionSeleccionada.getAreaAcademica());
            tfDependencia.setText(colaboracionSeleccionada.getDependencia());
            tfProgramaEducativo.setText(colaboracionSeleccionada.getProgramaEducativo());
            tfExperienciaEducativa.setText(colaboracionSeleccionada.getExperienciaEducativa());
            tfProfesorUV.setText(colaboracionSeleccionada.getProfesorUV());
            tfProfesorExterno.setText(colaboracionSeleccionada.getNombreProfesorExterno());
            taObjetivosCurso.setText(colaboracionSeleccionada.getObjetivoCurso());
            taPerfilEstudiantes.setText(colaboracionSeleccionada.getPerfilEstudiante());
            taTemasInteres.setText(colaboracionSeleccionada.getTemaInteres());
            taInfoAdicional.setText(colaboracionSeleccionada.getInformacionAdicional());
        }
    }
    @FXML
    private void btnClicRegresar(ActionEvent event) {
    }
    
    

}
