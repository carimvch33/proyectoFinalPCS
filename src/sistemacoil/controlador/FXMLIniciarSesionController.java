package sistemacoil.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sistemacoil.modelo.dao.AutenticacionDAO;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.modelo.pojo.RespuestaLoginCoordinador;
import sistemacoil.modelo.pojo.RespuestaLoginProfesorUV;
import sistemacoil.utilidades.Animaciones;
import sistemacoil.utilidades.Constantes;
import sistemacoil.utilidades.Utils;

public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfPasswordVisible;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Pane paneLogin;
    @FXML
    private Button btnMostrarPassword;
    @FXML
    private ImageView ivIconoMostrar;

    private boolean passwordVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarListeners();
        tfPasswordVisible.setVisible(false);
    }  
    
    private void configurarListeners() {
        Utils.agregarListenerNumPersonal(tfUsuario, Constantes.MAX_LONG_LOGIN_USER);
        Utils.agregarListenerPassword(tfPassword, Constantes.MAX_LONG_LOGIN_PASSWORD);

        tfPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!passwordVisible) {
                    tfPasswordVisible.setText(newValue);
                }
            }
        });

        tfPasswordVisible.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (passwordVisible) {
                    tfPassword.setText(newValue);
                }
            }
        });
    }
    
    private boolean validarCampos(String numPersonal, String password){
        if (!numPersonal.isEmpty() && !password.isEmpty())
            return true;
        else {
            Utils.mostrarAlertaSimple("Campos Incompletos", "Faltan datos necesarios.\nComplete todos los campos para iniciar sesión.", paneLogin);
            return false;
        }
    }
    
    private boolean esCoordinador(String numPersonal) {
        return numPersonal.contains(Constantes.ES_COORDINADOR);
    }
    
    public void iniciarSesionCoordinador(String numPersonal, String password) {
        RespuestaLoginCoordinador respuesta = AutenticacionDAO.iniciarSesionCoordinador(numPersonal, password);
        if (!respuesta.getError()) {
            Utils.mostrarAlertaSimple("Bienvenido(a) Coordinador", "Ha ingresado el coordinador "
                    + respuesta.getCoordinador().getNombre() + " " + respuesta.getCoordinador().getApellidoPaterno() + " " 
                    + respuesta.getCoordinador().getApellidoMaterno() + " al sistema.", paneLogin);
            irPantallaPrincipalCoordinador();
        } else {
            Utils.mostrarAlertaSimple("Autenticación fallida", respuesta.getMensaje(), paneLogin);
        }
    }
    
    private void irPantallaPrincipalCoordinador() {
        try {
            Stage nuevoEscenarioPrincipal = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLPrincipalCoordinador.fxml"));
            Parent root = loader.load();
            Scene escenaPrincipal = new Scene(root);
            nuevoEscenarioPrincipal.setTitle("Sistema COIL");
            
            nuevoEscenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
            escenaPrincipal.setFill(null);

            nuevoEscenarioPrincipal.setScene(escenaPrincipal);

            nuevoEscenarioPrincipal.setOnShown(event -> {
                double centerX = Screen.getPrimary().getVisualBounds().getWidth() / 2;
                double centerY = Screen.getPrimary().getVisualBounds().getHeight() / 2;
                nuevoEscenarioPrincipal.setX(centerX - nuevoEscenarioPrincipal.getWidth() / 2);
                nuevoEscenarioPrincipal.setY(centerY - nuevoEscenarioPrincipal.getHeight() / 2);
            });
            
            nuevoEscenarioPrincipal.show();
            Stage escenarioActual = (Stage) btnIniciarSesion.getScene().getWindow();
            escenarioActual.close();
        } catch (IOException ex) {
            System.out.println("Error al redirigir a la pantalla principal del coordinador: " + ex.getMessage());
        }
    }
    
    public void iniciarSesionProfesorUV(String numPersonal, String password) {
        RespuestaLoginProfesorUV respuesta = AutenticacionDAO.iniciarSesionProfesorUV(numPersonal, password);
        if (!respuesta.getError()) {
            Utils.mostrarAlertaSimple("Bienvenido(a) Profesor", 
                    "Ha ingresado el profesor " + respuesta.getProfesorUV().getNombre() + " \n" + 
                            respuesta.getProfesorUV().getApellidoPaterno() + " "
                            + respuesta.getProfesorUV().getApellidoMaterno() + " al sistema.", paneLogin);
            irPantallaPrincipalProfesorUV(respuesta.getProfesorUV());
            
        } else {
            Utils.mostrarAlertaSimple("Autenticación fallida", respuesta.getMensaje(),paneLogin);
        }
    }

    private void irPantallaPrincipalProfesorUV(ProfesorUV profesorUV) {
        try {
            Stage nuevoEscenarioPrincipal = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemacoil/vista/FXMLPrincipalProfesorUV.fxml"));
            Parent root = loader.load();
            FXMLPrincipalProfesorUVController controlador = loader.getController();
            controlador.inicializarValores(profesorUV);
            Scene escenaPrincipal = new Scene(root);
            nuevoEscenarioPrincipal.setTitle("Sistema COIL");
            nuevoEscenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
            escenaPrincipal.setFill(null);
            
            nuevoEscenarioPrincipal.setScene(escenaPrincipal);

            nuevoEscenarioPrincipal.setOnShown(event -> {
                double centerX = Screen.getPrimary().getVisualBounds().getWidth() / 2;
                double centerY = Screen.getPrimary().getVisualBounds().getHeight() / 2;
                nuevoEscenarioPrincipal.setX(centerX - nuevoEscenarioPrincipal.getWidth() / 2);
                nuevoEscenarioPrincipal.setY(centerY - nuevoEscenarioPrincipal.getHeight() / 2);
            });

            nuevoEscenarioPrincipal.show();

            Stage escenarioActual = (Stage) btnIniciarSesion.getScene().getWindow();
            escenarioActual.close();
        } catch (IOException ex) {
            System.out.println("Error al redirigir a la pantalla principal del profesor UV: " + ex.getMessage());
        }
    }
    
    @FXML
    private void btnClicIniciarSesion(ActionEvent event) {
        Animaciones.animarPresionBoton(btnIniciarSesion);
        String numPersonal = tfUsuario.getText().toLowerCase().trim();
        String password = passwordVisible ? tfPasswordVisible.getText().toLowerCase().trim() : tfPassword.getText().toLowerCase().trim();
        
        if (validarCampos(numPersonal, password)) {
            if (esCoordinador(numPersonal)) {
                iniciarSesionCoordinador(numPersonal, password);
            } else {
                iniciarSesionProfesorUV(numPersonal, password);
            }
        }
    }

    @FXML
    private void btnClicMostrarPassword(ActionEvent event) {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            tfPasswordVisible.setText(tfPassword.getText());
            tfPasswordVisible.setVisible(true);
            tfPassword.setVisible(false);
            ivIconoMostrar.setImage(new Image(getClass().getResourceAsStream("/sistemacoil/recursos/iconos/hide-password.png")));
        } else {
            tfPassword.setText(tfPasswordVisible.getText());
            tfPasswordVisible.setVisible(false);
            tfPassword.setVisible(true);
            ivIconoMostrar.setImage(new Image(getClass().getResourceAsStream("/sistemacoil/recursos/iconos/show-password.png")));
        }
    }
}
