package com.example.interfacegraphique;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LuminousOrb extends StackPane {

    public LuminousOrb() {

        Circle circle = new Circle(40); // rayon de 40
        circle.setFill(Color.TRANSPARENT); // intérieur foncé
        circle.setStroke(Color.WHITE); // contour blanc
        circle.setStrokeWidth(8);

        circle.setEffect(new DropShadow(50, Color.WHITE)); // lueur blanche autour
        Circle inner = new Circle(60, Color.TRANSPARENT); // Trou central
        Circle outer = new Circle(70, Color.TRANSPARENT);
        outer.setStroke(Color.WHITE);
        outer.setStrokeWidth(15);

        outer.setEffect(new Glow(0.9));
        this.getChildren().addAll(outer, inner);

        RotateTransition rotate = new RotateTransition(Duration.seconds(2), this);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();

        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1.5), circle);
        pulse.setFromX(1);
        pulse.setFromY(1);
        pulse.setToX(1.1);
        pulse.setToY(1.1);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
        this.getChildren().add(circle);
     
    }
}
