package com.example.interfacegraphique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {
    private double x, y = 0;
    private static final double MIN_WIDTH = 650;
    private static final double MIN_HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        
        // Configuration de la fenêtre
        primaryStage.initStyle(StageStyle.UNDECORATED);
        configureWindowDragging(root, primaryStage);
        
        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        
        // Gestion du redimensionnement
        setupResizeControl(primaryStage, loader.getController());
        
        primaryStage.setTitle("Polaris");
        primaryStage.show();
    }

    private void configureWindowDragging(Parent root, Stage stage) {
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    private void setupResizeControl(Stage stage, HelloController controller) {
        // Zone de redimensionnement (8px sur les bords)
        final int resizeBorder = 8;
        Scene scene = stage.getScene();
        
        scene.setOnMouseMoved(event -> {
            if (event.getX() < resizeBorder || 
                event.getX() > scene.getWidth() - resizeBorder ||
                event.getY() < resizeBorder || 
                event.getY() > scene.getHeight() - resizeBorder) {
                scene.setCursor(Cursor.SE_RESIZE);
            } else {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        
        scene.setOnMouseDragged(event -> {
            if (scene.getCursor() == Cursor.SE_RESIZE) {
                double newWidth = Math.max(MIN_WIDTH, event.getX());
                double newHeight = Math.max(MIN_HEIGHT, event.getY());
                stage.setWidth(newWidth);
                stage.setHeight(newHeight);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*package com.example.interfacegraphique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Polaris");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();

        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
} */