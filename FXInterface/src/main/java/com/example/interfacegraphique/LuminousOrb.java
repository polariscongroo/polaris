package com.example.interfacegraphique;

import javafx.animation.RotateTransition;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LuminousOrb extends StackPane {

    public LuminousOrb() {
        Circle inner = new Circle(40, Color.web("#1F2937")); // Trou central
        Circle outer = new Circle(60, Color.TRANSPARENT);
        outer.setStroke(Color.WHITE);
        outer.setStrokeWidth(15);

        outer.setEffect(new Glow(0.8));
        this.getChildren().addAll(outer, inner);

        RotateTransition rotate = new RotateTransition(Duration.seconds(2), this);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();

     
    }
}
