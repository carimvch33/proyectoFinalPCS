package sistemacoil.utilidades;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sistemacoil.controlador.alertas.FXMLAlertaSimpleController;
import sistemacoil.controlador.alertas.FXMLAlertaConfirmacionController;
import sistemacoil.controlador.alertas.FXMLGestionarColaboracionController;
import sistemacoil.modelo.pojo.Periodo;

public class Utils {
    public static FXMLLoader obtenerLoader(String ruta){
        return new FXMLLoader(sistemacoil.SistemaCOIL.class.getResource(ruta));
    }

    public static void limpiarCampo(TextField textField, Label lbError) {
        textField.clear();
        lbError.setText("");
    }
    
    public static void limpiarComboBox(ComboBox<?> comboBox, Label lbError) {
        if (comboBox != null)
            comboBox.getSelectionModel().clearSelection();
        lbError.setText("");
    }
    
    public static void navegarVentana(String rutaFXML, Pane apVentana) {
        try {
            URL fxmlUrl = Utils.class.getResource(rutaFXML);
            AnchorPane nuevaVentanaPane = FXMLLoader.load(fxmlUrl);
            apVentana.getChildren().setAll(nuevaVentanaPane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void agregarListenerNumPersonal(TextField textField, int longitudMaxima) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= longitudMaxima && newValue.matches("[a-zA-Z0-9]*"))
                textField.setText(newValue.toUpperCase());
            else
                textField.setText(oldValue);
        });
    }
    
    public static void agregarListenerPassword(TextField textField, int longitudMaxima) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= longitudMaxima)
                textField.setText(newValue);
            else
                textField.setText(oldValue);
        });
    }
    
    public static void agregarListenerSimple(TextField textField, int longitudMaxima) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() <= longitudMaxima)
                    textField.setText(newValue.toUpperCase());
                else {
                    textField.setText(oldValue);
                    Animaciones.animarShake(textField);
                }
            }
        });
    }
    
    public static void agregarListenerNombres(TextField textField, int longitudMaxima) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() <= longitudMaxima || !newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]*"))
                    textField.setText(newValue.toUpperCase());   
                else {
                    textField.setText(oldValue);
                    Animaciones.animarShake(textField);
                }
            }
        });
    }
   
    public static void agregarListenerCorreo(TextField textField, int longitudMaxima) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= longitudMaxima)
                textField.setText(newValue.toLowerCase());
            else {
                textField.setText(oldValue);
                Animaciones.animarShake(textField);
            }
        });
    }
    
    public static void agregarListenerTelefono(TextField textField, int longitudMaxima) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= longitudMaxima && newValue.matches("\\d*"))
                textField.setText(newValue);
            else {
                textField.setText(oldValue);
                Animaciones.animarShake(textField);
            }
        });
    }
    
    public static void agregarListenerTextArea(TextArea textArea, int longitudMaxima) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= longitudMaxima)
                textArea.setText(newValue);
            else {
                textArea.setText(oldValue);
                Animaciones.animarShake(textArea);
            }
        });
    }
    
    public static <T, U> void configurarListenerDesbloquearYLLenar(
            ComboBox<T> comboBoxListener,
            ComboBox<U> comboBoxToUnlock,
            Consumer<Integer> loadMethod,
            Function<T, Integer> newValueGetter) {

        comboBoxListener.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                if (newValue != null) {
                    loadMethod.accept(newValueGetter.apply(newValue));
                    comboBoxToUnlock.setDisable(false);
                } else {
                    comboBoxToUnlock.setDisable(true);
                }
            }
        });
    }
    
    public static void agregarListenerFecha(DatePicker datePicker, Label lbError,
            Periodo periodoSeleccionado, BiFunction<DatePicker, Label, Boolean> validarFecha) {

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                validarFecha.apply(datePicker, lbError);
            }
        });
    }
    
    public static void mostrarAlertaSimple(String titulo, String mensaje, Pane pane) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLAlertaSimpleController.class.getResource(Escenas.ALERTA_SIMPLE));
            AnchorPane root = loader.load();

            FXMLAlertaSimpleController controller = loader.getController();
            controller.setTituloYMensaje(titulo, mensaje);

            Stage stage = new Stage();
            controller.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.getScene().setFill(null);

            double centerX = pane.getLayoutX() + pane.getWidth() / 2;
            double centerY = pane.getLayoutY() + pane.getHeight() / 2;

            stage.setOnShown(event -> {
                stage.setX(centerX - stage.getWidth() / 2);
                stage.setY(centerY - stage.getHeight() / 2);
            });

            Animaciones.reducirBrillo(pane);

            stage.showAndWait();

            Animaciones.restablecerBrillo(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean mostrarAlertaConfirmacion(String titulo, String mensaje, 
        String textoBtnPrimario, String textoBtnSecundario, Pane pane) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLAlertaConfirmacionController.class.getResource(Escenas.ALERTA_CONFIRMACION));
            AnchorPane root = loader.load();

            FXMLAlertaConfirmacionController controller = loader.getController();
            controller.setTituloYMensaje(titulo, mensaje);
            controller.setTextoBotones(textoBtnPrimario, textoBtnSecundario);

            Stage stage = new Stage();
            controller.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.getScene().setFill(null);
            stage.centerOnScreen();
            Animaciones.reducirBrillo(pane);

            stage.showAndWait();

            Animaciones.restablecerBrillo(pane); 

            return controller.getResultado();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static int mostrarMenuContextual() {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLGestionarColaboracionController.class.getResource("/sistemacoil/vista/alertas/FXMLGestionarColaboracion.fxml"));
            AnchorPane root = loader.load();

            FXMLGestionarColaboracionController controller = loader.getController();

            Stage stage = new Stage();
            controller.setStage(stage);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.getScene().setFill(null);
            stage.centerOnScreen();

            stage.showAndWait();
            return controller.getOpcionSeleccionada();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static void cancelarOperacionActual(Pane pane) {
        boolean confirmado = mostrarAlertaConfirmacion(Constantes.TITULO_SALIR, Constantes.INDICACION_SALIR, 
                Constantes.CONTINUAR_OPERACION, Constantes.CANCELAR_OPERACION, pane);
        if (!confirmado) {
            try {
                FXMLLoader loader = new FXMLLoader(Utils.class.getResource(Escenas.INICIO));
                AnchorPane inicioPane = loader.load();
                pane.getChildren().clear();
                pane.getChildren().add(inicioPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
