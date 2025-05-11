package com.example.interfacegraphique;

import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
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

/**
 * Composant graphique représentant une rose des vents animée,
 * utilisée comme loader visuel dans l'interface utilisateur JavaFX.
 *
 * <p>Cette classe hérite de {@link BorderPane} et crée dynamiquement une boussole centrée,
 * avec des animations de rotation infinies. L'affichage repose sur des effets visuels
 * tels que les ombres portées et les dégradés radiaux, qui sont difficiles à exprimer
 * uniquement en CSS avec JavaFX.</p>
 *
 * @author Beryl S. 
 */


public class CompassRose extends BorderPane {
    /**
     * Construit une rose des vents animée.
     * Initialise le fond, les directions cardinales, les effets graphiques,
     * et lance l'animation de rotation continue.
     */
    public CompassRose() {
        Pane centerPane = new Pane();
        this.setCenter(centerPane);

        // Group qui contiendra uniquement les éléments rotatifs
        Group compassGroup = new Group();
        centerPane.getChildren().add(compassGroup);

        // Dégradé radial de fond (fixe, donc pas dans le groupe)
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
                new Stop(1, Color.web("rgba(0, 0, 0, 0)"))
        ));
        centerPane.getChildren().add(0, background); // mettre le fond en arrière-plan

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

            double offset = (radius + 20);

            line.setStartX(0);
            line.setStartY(0);
            line.setEndX(radius * Math.cos(angle));
            line.setEndY(-radius * Math.sin(angle));

            label.setX(offset * Math.cos(angle) - 10);
            label.setY(-offset * Math.sin(angle) + 5);

            compassGroup.getChildren().addAll(line, label);
        }

        // Centrer le compassGroup
        compassGroup.layoutXProperty().bind(centerPane.widthProperty().divide(2));
        compassGroup.layoutYProperty().bind(centerPane.heightProperty().divide(2));

        this.setPrefSize(200, 200);
        animateCompass(compassGroup); // ✅ Ne rotate QUE le compas
    }

     /**
     * Anime la rotation du groupe représentant la rose des vents.
     * Chaque animation utilise une durée et un angle aléatoire pour
     * donner un effet naturel et continu.
     *
     * @param compassGroup Le groupe contenant les éléments à faire tourner.
     */

    private void animateCompass(Group compassGroup) {
        Random random = new Random();

        Runnable rotateStep = new Runnable() {
            @Override
            public void run() {
                double newAngle = -180 + random.nextDouble() * 360;
                double duration = 1.5 + random.nextDouble() * 1.5;

                RotateTransition rt = new RotateTransition(Duration.seconds(duration), compassGroup);
                rt.setToAngle(newAngle);
                rt.setInterpolator(Interpolator.EASE_BOTH);
                rt.setOnFinished(e -> Platform.runLater(this));
                rt.play();
            }
        };

        rotateStep.run();
    }
}