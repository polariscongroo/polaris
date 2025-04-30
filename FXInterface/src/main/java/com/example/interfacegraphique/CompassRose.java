package com.example.interfacegraphique;

import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CompassRose extends StackPane {

    public CompassRose() {
        Pane root = new Pane();
        this.getChildren().add(root);

        // 🔵 Ajout d’un fond en dégradé radial
        Circle background = new Circle();
        background.radiusProperty().bind(Bindings.createDoubleBinding(() ->
                Math.min(root.getWidth(), root.getHeight()) / 2, root.widthProperty(), root.heightProperty()));
        background.centerXProperty().bind(root.widthProperty().divide(2));
        background.centerYProperty().bind(root.heightProperty().divide(2));
        background.setFill(new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2c3e50")),
                new Stop(1, Color.web("#000000"))
        ));
        root.getChildren().add(background);

        double radius = 100;
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

        for (int i = 0; i < directions.length; i++) {
            double angle = Math.toRadians(i * 45);

            Line line = new Line();
            line.setStroke(Color.LIGHTBLUE);
            line.setStrokeWidth(i % 2 == 0 ? 2 : 1);
            line.setEffect(new Glow(0.5));

            Text label = new Text(directions[i]);
            label.setFill(Color.WHITE);
            label.setFont(Font.font("Verdana", i % 2 == 0 ? 20 : 14));
            label.setEffect(new DropShadow(5, Color.CYAN));

            line.startXProperty().bind(root.widthProperty().divide(2));
            line.startYProperty().bind(root.heightProperty().divide(2));
            line.endXProperty().bind(Bindings.createDoubleBinding(() ->
                    root.getWidth() / 2 + radius * Math.cos(angle),
                    root.widthProperty()));
            line.endYProperty().bind(Bindings.createDoubleBinding(() ->
                    root.getHeight() / 2 - radius * Math.sin(angle),
                    root.heightProperty()));

            label.xProperty().bind(Bindings.createDoubleBinding(() ->
                    root.getWidth() / 2 + (radius + 20) * Math.cos(angle) - 10,
                    root.widthProperty()));
            label.yProperty().bind(Bindings.createDoubleBinding(() ->
                    root.getHeight() / 2 - (radius + 20) * Math.sin(angle) + 5,
                    root.heightProperty()));

            root.getChildren().addAll(line, label);
        }

        // 💫 Rotation subtile et infinie
        RotateTransition rotate = new RotateTransition(Duration.seconds(20), root);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();
    }
}
