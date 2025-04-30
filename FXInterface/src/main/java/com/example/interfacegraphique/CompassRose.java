package com.example.interfacegraphique;

import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CompassRose extends BorderPane {

    public CompassRose() {

        Pane centerPane = new Pane();
        this.setCenter(centerPane); // 👈 Ajout dans la zone centrale du BorderPane

        // 🔵 Dégradé radial
        Circle background = new Circle();
        background.radiusProperty().bind(Bindings.createDoubleBinding(() ->
                Math.min(0.55 * Math.min(centerPane.getWidth(), centerPane.getHeight()) / 2, 200),
                centerPane.widthProperty(), centerPane.heightProperty()));

        background.centerXProperty().bind(centerPane.widthProperty().divide(2));
        background.centerYProperty().bind(centerPane.heightProperty().divide(2));
        background.setFill(new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2c3e50")),
                new Stop(1, Color.web("rgba(0, 0, 0, 0.4)"))
        ));
        centerPane.getChildren().add(background);

        double radius = 50;
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

            line.startXProperty().bind(centerPane.widthProperty().divide(2));
            line.startYProperty().bind(centerPane.heightProperty().divide(2));
            line.endXProperty().bind(Bindings.createDoubleBinding(() ->
                    centerPane.getWidth() / 2 + radius * Math.cos(angle),
                    centerPane.widthProperty()));
            line.endYProperty().bind(Bindings.createDoubleBinding(() ->
                    centerPane.getHeight() / 2 - radius * Math.sin(angle),
                    centerPane.heightProperty()));

            label.xProperty().bind(Bindings.createDoubleBinding(() ->
                    centerPane.getWidth() / 2 + (radius + 20) * Math.cos(angle) - 10,
                    centerPane.widthProperty()));
            label.yProperty().bind(Bindings.createDoubleBinding(() ->
                    centerPane.getHeight() / 2 - (radius + 20) * Math.sin(angle) + 5,
                    centerPane.heightProperty()));

            centerPane.getChildren().addAll(line, label);
        }

        this.setPrefSize(200, 200);
        animateCompass(centerPane);
    }

    private void animateCompass(Pane node) {
        Random random = new Random();

        Runnable rotateStep = new Runnable() {
            @Override
            public void run() {
                double newAngle = -180 + random.nextDouble() * 360;
                double duration = 1.5 + random.nextDouble() * 1.5;

                RotateTransition rt = new RotateTransition(Duration.seconds(duration), node);
                rt.setToAngle(newAngle);
                rt.setInterpolator(Interpolator.EASE_BOTH);
                rt.setOnFinished(e -> Platform.runLater(this));
                rt.play();
            }
        };

        rotateStep.run();
    }
}

