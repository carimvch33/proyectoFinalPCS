package sistemacoil.utilidades;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author jafet
 */
public class Animaciones {
    public static void animarShake(Node node) {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(node.translateXProperty(), 0)),
            new KeyFrame(Duration.millis(75), new KeyValue(node.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(150), new KeyValue(node.translateXProperty(), 9)),
            new KeyFrame(Duration.millis(225), new KeyValue(node.translateXProperty(), -8)),
            new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), 7)),
            new KeyFrame(Duration.millis(375), new KeyValue(node.translateXProperty(), -6)),
            new KeyFrame(Duration.millis(450), new KeyValue(node.translateXProperty(), 5)),
            new KeyFrame(Duration.millis(525), new KeyValue(node.translateXProperty(), -4)),
            new KeyFrame(Duration.millis(600), new KeyValue(node.translateXProperty(), 3)),
            new KeyFrame(Duration.millis(675), new KeyValue(node.translateXProperty(), -2)),
            new KeyFrame(Duration.millis(750), new KeyValue(node.translateXProperty(), 1)),
            new KeyFrame(Duration.millis(825), new KeyValue(node.translateXProperty(), 0))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    
    public static void animarPresionBoton(Button boton) {
        ScaleTransition reducirEscala = new ScaleTransition(Duration.millis(90), boton);
        reducirEscala.setToX(0.92);
        reducirEscala.setToY(0.92);

        ScaleTransition restaurarEscala = new ScaleTransition(Duration.millis(90), boton);
        restaurarEscala.setToX(1.0);
        restaurarEscala.setToY(1.0);

        boton.setOnMousePressed(event -> {
            reducirEscala.playFromStart();
        });

        boton.setOnMouseReleased(event -> {
            restaurarEscala.playFromStart();
        });
    }
    
    public static void reducirBrillo(Pane pane) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        pane.setEffect(colorAdjust);
    }
    
    public static void restablecerBrillo(Pane pane) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        pane.setEffect(colorAdjust);
    }
}
