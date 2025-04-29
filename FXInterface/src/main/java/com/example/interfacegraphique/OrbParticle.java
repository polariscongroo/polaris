package com.example.interfacegraphique;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

//Node c'est la classe de base pour tout objet graphqiue (comme circle, Pane, etc)
//Parent c'est la classe de base pour les objets qui peuvent contenir d'autres objets graphiques (comme Pane, Group, etc)
//Pane conteneur générique avec getChildren()
//Platform.runLater Execute du code JavaFx en toute sécurité après le rendu ou une animation

public class OrbParticle extends Circle {

    public OrbParticle(double centerX, double centerY) {
        super(2 + Math.random() * 3); // rayon entre 2 et 4
        setFill(Color.WHITE);
        setOpacity(0.8);
        setEffect(new DropShadow(10, Color.WHITE));

        // Position au centre
        setTranslateX(centerX);
        setTranslateY(centerY);

        // +ion aléatoire vers l'extérieur
        double angle = Math.random() * 2 * Math.PI;
        double distance = 100 + Math.random() * 50;
        double x = Math.cos(angle) * distance;
        double y = Math.sin(angle) * distance;

        //la on fait une animation de déplacement : on veut le faire bouger vers une direction aléatoire (x,y), une foisbterminer supprimer le cercle du conteneur  (remove(this))
        TranslateTransition move = new TranslateTransition(Duration.seconds(1 + Math.random()), this);
        move.setByX(x);
        move.setByY(y);
        //Si le parent  n'est pas un Pane : si on ajoutes la particule dasn un stackPane, hbox etc, alors Pane ets un mauvais cast (on veut un pane qui permet de faire un getChildren)
        Node parent = getParent();
        if (parent instanceof Pane pane) {
             pane.getChildren().remove(this);
        }
        move.play();

        FadeTransition fade = new FadeTransition(Duration.seconds(7), this);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.play();
    }
}