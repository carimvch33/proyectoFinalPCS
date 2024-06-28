package sistemacoil.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import sistemacoil.modelo.dao.ColaboracionDAO;
import sistemacoil.modelo.dao.EstudianteDAO;
import sistemacoil.modelo.dao.EvidenciaDAO;
import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.modelo.pojo.Estudiante;
import sistemacoil.modelo.pojo.Evidencia;
import sistemacoil.modelo.pojo.ProfesorExterno;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Escenas;
import sistemacoil.utilidades.Utils;

public class FXMLSubirArchivosController implements Initializable {

    private ProfesorUV profesorUV;
    private ProfesorExterno profesorExterno;
    private ObservableList<Estudiante> listaEstudiantes;
    private Colaboracion colaboracion;
    private int idColaboracion;
    private boolean subirEvidencias;
    private File evidencia;

    @FXML
    private Pane paneSubirArchivo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnSubir;
    @FXML
    private Button btnRegresar;
    @FXML
    private Label lbNombreArchivo;
    @FXML
    private Label lbSecundario;
    @FXML
    private Button btnExplorador;
    @FXML
    private ImageView ivArchivo;
    @FXML
    private Button btnEliminarArchivo;
    @FXML
    private AnchorPane apVentana;
    @FXML
    private TextField tfNombreColaboracion;
    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            paneSubirArchivo.setOnDragOver(this::dragOver);
            paneSubirArchivo.setOnDragDropped(this::dragDropped);
            btnEliminarArchivo.setDisable(true);
            ivArchivo.setImage(new Image(getClass().getResourceAsStream("/sistemacoil/recursos/iconos/upload-file.png")));
            cargarNombreColaboracion();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setColaboracion(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
        cargarNombreColaboracion();
    }

    public void setSubirEvidencias(boolean subirEvidencias) {
        this.subirEvidencias = subirEvidencias;
    }

    public void setIdColaboracion(int idColaboracion) {
        this.idColaboracion = idColaboracion;
        cargarNombreColaboracion();
    }

    public void setProfesorUV(ProfesorUV profesorUV) {
        this.profesorUV = profesorUV;
    }

    public void setProfesorExterno(ProfesorExterno profesorExterno) {
        this.profesorExterno = profesorExterno;
    }

    public void setListaEstudiantes(ObservableList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    private void cargarNombreColaboracion() {
        if (colaboracion != null) {
            String nombreColaboracion = colaboracion.getNombreColaboracion();
            tfNombreColaboracion.setText(nombreColaboracion);
        } else if (idColaboracion > 0) {
            String nombreColaboracion = ColaboracionDAO.obtenerNombreColaboracion(idColaboracion);
            if (nombreColaboracion != null) {
                tfNombreColaboracion.setText(nombreColaboracion);
            }
        }
    }

    private void dragOver(DragEvent event) {
        if (event.getGestureSource() != paneSubirArchivo &&
                event.getDragboard().hasFiles() && evidencia == null) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void dragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);
            if (validarArchivo(file)) {
                cargarArchivo(file);
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private boolean validarArchivo(File file) {
        String fileName = file.getName().toLowerCase();
        if (!fileName.endsWith(".pdf") && !fileName.endsWith(".docx") && !fileName.endsWith(".zip")) {
            Utils.mostrarAlertaSimple("Extensión inválida", "Solo se permiten archivos con la extensión .pdf, .docx o .zip.", apVentana);
            return false;
        }

        if (file.length() > Constantes.MAX_PESO_ARCHIVOS) {
            Utils.mostrarAlertaSimple("Tamaño excedido", "El archivo cargado no debe superar los 20 MB.", apVentana);
            return false;
        }

        return true;
    }

    private void cargarArchivo(File file) {
        evidencia = file;
        lbNombreArchivo.setText(file.getName());
        lbSecundario.setText(String.format("%.2f MB", file.length() / 1024.0 / 1024.0));

        String rutaIcono = null;
        if (file.getName().endsWith(".pdf"))
            rutaIcono = "/sistemacoil/recursos/iconos/pdf-icon.png";
        else if (file.getName().endsWith(".docx"))
            rutaIcono = "/sistemacoil/recursos/iconos/docx-icon.png";
        else if (file.getName().endsWith(".zip"))
            rutaIcono = "/sistemacoil/recursos/iconos/zip-icon.png";

        if (rutaIcono != null && getClass().getResourceAsStream(rutaIcono) != null) {
            ivArchivo.setImage(new Image(getClass().getResourceAsStream(rutaIcono)));
        }

        btnEliminarArchivo.setDisable(false);
    }

    private void mostrarErrorSubida() {
        Utils.mostrarAlertaSimple("Error de carga", "Ocurrió un error al subir la evidencia", apVentana);
    }

    private void mostrarErrorLecturaArchivo() {
        Utils.mostrarAlertaSimple("Error de lectura", "Ocurrió un error al leer el archivo de evidencia", apVentana);
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        // Lógica para botón cancelar si es necesario
    }

    @FXML
    private void btnClicRegresar(ActionEvent event) {
        Animaciones.animarPresionBoton(btnCancelar);
        Utils.cancelarOperacionActual(apVentana);
    }

    @FXML
    private void btnClicAbrirExplorador(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Archivos .pdf", "*.pdf"),
                new ExtensionFilter("Archivos .docx", "*.docx"),
                new ExtensionFilter("Archivos .zip", "*.zip")
        );

        List<File> archivosSeleccionados = fileChooser.showOpenMultipleDialog(paneSubirArchivo.getScene().getWindow());

        if (archivosSeleccionados != null && archivosSeleccionados.size() > 0) {
            File evidencia = archivosSeleccionados.get(0);
            if (validarArchivo(evidencia)) {
                cargarArchivo(evidencia);
            }
        }
    }

    @FXML
    private void btnClicSubir(ActionEvent event) {
        if (evidencia == null) {
            Utils.mostrarAlertaSimple("Syllabus faltante", "Debe subir el syllabus antes de registrar la colaboración", apVentana);
            return;
        }

        if (subirEvidencias) {
            subirEvidencia();
        } else {
            registrarColaboracionConProfesorExterno();
        }
    }

    private void subirEvidencia() {
        try {
            byte[] archivoBytes = java.nio.file.Files.readAllBytes(evidencia.toPath());
            Evidencia nuevaEvidencia = crearEvidencia(archivoBytes);
            boolean exito = EvidenciaDAO.subirEvidencia(nuevaEvidencia);
            if (exito) {
                Utils.mostrarAlertaSimple("Evidencia subida", "La evidencia ha sido subida exitosamente", apVentana);
            } else {
                mostrarErrorSubida();
            }
        } catch (IOException e) {
            mostrarErrorLecturaArchivo();
        }
    }

    private Evidencia crearEvidencia(byte[] archivoBytes) {
        Evidencia nuevaEvidencia = new Evidencia();
        nuevaEvidencia.setArchivoEvidencia(archivoBytes);
        nuevaEvidencia.setIdColaboracion(idColaboracion);
        return nuevaEvidencia;
    }

    private void registrarColaboracionConProfesorExterno() {
        int idProfesorExterno = FXMLRegistrarProfesorExternoController.registrarProfesorExterno(profesorExterno);
        if (idProfesorExterno != -1 && profesorUV != null && profesorUV.getIdProfesorUV() != 0) {
            if (registrarColaboracion(idProfesorExterno)) {
                try {
                    byte[] archivoBytes = java.nio.file.Files.readAllBytes(evidencia.toPath());
                    boolean exitoSyllabus = ColaboracionDAO.subirSyllabus(idColaboracion, archivoBytes);
                    if (exitoSyllabus) {
                        Utils.mostrarAlertaSimple("Registro exitoso", "La información de la colaboración ha sido registrada exitosamente", apVentana);
                        regresarAInicio();
                    } else {
                        Utils.mostrarAlertaSimple("Registro fallido", "Ocurrió un error al registrar el syllabus", apVentana);
                    }
                } catch (IOException e) {
                    mostrarErrorLecturaArchivo();
                }
            } else {
                Utils.mostrarAlertaSimple("Registro fallido", "Ocurrió un error al registrar la colaboración", apVentana);
            }
        } 
    }

    private boolean registrarColaboracion(int idProfesorExterno) {
        colaboracion.setIdProfesorExterno(idProfesorExterno);
        colaboracion.setIdProfesorUV(profesorUV.getIdProfesorUV());
        colaboracion.setEstado(Constantes.ESTADO_VIGENTE);
        int idColaboracion = ColaboracionDAO.registrarColaboracion(colaboracion);

        if (idColaboracion != -1) {
            colaboracion.setIdColaboracion(idColaboracion);
            this.idColaboracion = idColaboracion;
            return FXMLRegistrarEstudiantesController.registrarEstudiantesEnColaboracion(idColaboracion, listaEstudiantes);
        } else {
            return false;
        }
    }

    private void regresarAInicio() {
        Utils.navegarVentana(Escenas.INICIO, apVentana);
    }

    @FXML
    private void btnClicEliminarArchivo(ActionEvent event) {
        Animaciones.animarPresionBoton(btnEliminarArchivo);
        evidencia = null;
        ivArchivo.setImage(new Image(getClass().getResourceAsStream("/sistemacoil/recursos/iconos/upload-file.png")));
        lbNombreArchivo.setText("Arrastrar y soltar aquí");
        lbSecundario.setText("o");
        btnEliminarArchivo.setDisable(true);
    }
}
