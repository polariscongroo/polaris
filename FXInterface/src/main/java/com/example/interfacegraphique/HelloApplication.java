package com.example.interfacegraphique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Chargement du CSS
        scene.getStylesheets().add(getClass().getResource("/com/example/interfacegraphique/css/style.css").toExternalForm());
        
        // 1. Gestion du déplacement de la fenêtre
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        

        primaryStage.setOnCloseRequest(event -> {
            event.consume(); // Empêche la fermeture immédiate
            HelloController controller = fxmlLoader.getController();
            controller.handleClose(null); // Appelle la méthode handleClose
        });

        // 2. Configuration de la fenêtre
        
        primaryStage.setTitle("Polaris");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        
        // 3. Redimensionnement manuel
        Resize(root, primaryStage);

        primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private void Resize(Pane root, Stage stage) {
        final int resizeMargin = 5;
        
        root.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            double width = root.getWidth();
            double height = root.getHeight();
            
            // Changer le curseur selon la position
            if (x < resizeMargin && y < resizeMargin) {
                root.setCursor(javafx.scene.Cursor.NW_RESIZE);
            } else if (x < resizeMargin && y > height - resizeMargin) {
                root.setCursor(javafx.scene.Cursor.SW_RESIZE);
            } else if (x > width - resizeMargin && y < resizeMargin) {
                root.setCursor(javafx.scene.Cursor.NE_RESIZE);
            } else if (x > width - resizeMargin && y > height - resizeMargin) {
                root.setCursor(javafx.scene.Cursor.SE_RESIZE);
            } else if (x < resizeMargin) {
                root.setCursor(javafx.scene.Cursor.W_RESIZE);
            } else if (x > width - resizeMargin) {
                root.setCursor(javafx.scene.Cursor.E_RESIZE);
            } else if (y < resizeMargin) {
                root.setCursor(javafx.scene.Cursor.N_RESIZE);
            } else if (y > height - resizeMargin) {
                root.setCursor(javafx.scene.Cursor.S_RESIZE);
            } else {
                root.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });
        
        root.setOnMouseDragged(event -> {
            double x = event.getX();
            double y = event.getY();
            
            // Redimensionnement selon le bord
            if (root.getCursor() != javafx.scene.Cursor.DEFAULT) {
                double newWidth = stage.getWidth();
                double newHeight = stage.getHeight();
                
                if (root.getCursor() == javafx.scene.Cursor.NW_RESIZE) {
                    newWidth = stage.getX() - event.getScreenX() + stage.getWidth();
                    newHeight = stage.getY() - event.getScreenY() + stage.getHeight();
                    stage.setWidth(newWidth);
                    stage.setHeight(newHeight);
                    stage.setX(event.getScreenX());
                    stage.setY(event.getScreenY());
                } else if (root.getCursor() == javafx.scene.Cursor.SE_RESIZE) {
                    stage.setWidth(x);
                    stage.setHeight(y);
                } else if (root.getCursor() == javafx.scene.Cursor.NE_RESIZE) {
                    stage.setWidth(x);
                    stage.setHeight(y);
                } else if (root.getCursor() == javafx.scene.Cursor.SW_RESIZE) {
                    stage.setWidth(x);
                    stage.setHeight(y);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}