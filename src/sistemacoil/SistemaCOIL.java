package sistemacoil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SistemaCOIL extends Application {
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        cargarFuentes();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("vista/FXMLIniciarSesion.fxml"));
        AnchorPane root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setTitle("Sistema COIL");
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void cargarFuentes() {
        Font.loadFont(getClass().getResourceAsStream("/sistemacoil/estilos/tipografias/Inter-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/sistemacoil/estilos/tipografias/Inter-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/sistemacoil/estilos/tipografias/Inter-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/sistemacoil/estilos/tipografias/Inter-SemiBold.ttf"), 12);
    }
}
