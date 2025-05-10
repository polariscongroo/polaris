package com.example.interfacegraphique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Classe principale de l'application JavaFX.
 * Elle charge la vue principale, configure la fenêtre, et gère les actions
 * comme le déplacement et le redimensionnement de la fenêtre.
 * 
 * <p>Elle utilise FXML pour l'interface graphique et applique un style CSS personnalisé.</p>
 * 
 * @author Beryl S.
 */
public class HelloApplication extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Point d'entrée de l'application JavaFX.
     * Configure la scène, applique le style CSS, gère les événements de déplacement et de redimensionnement.
     *
     * @param primaryStage La fenêtre principale fournie par le système JavaFX.
     * @throws Exception Si le fichier FXML ou le CSS ne peut pas être chargé.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Chargement du CSS
        scene.getStylesheets().add(getClass().getResource("/com/example/interfacegraphique/css/style.css").toExternalForm());

        // Gestion du déplacement de la fenêtre
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // Gestion personnalisée de la fermeture de la fenêtre
        primaryStage.setOnCloseRequest(event -> {
            event.consume(); // Empêche la fermeture immédiate
            HelloController controller = fxmlLoader.getController();
            controller.handleClose(null); // Appelle la méthode handleClose
        });

        primaryStage.setTitle("Polaris");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);

        // Redimensionnement manuel
        Resize(root, primaryStage);

        primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        primaryStage.show();
    }

    /**
     * Permet le redimensionnement manuel de la fenêtre selon la position du curseur.
     * Change l'apparence du curseur et ajuste la taille de la fenêtre.
     *
     * @param root  Le conteneur principal de la scène.
     * @param stage La fenêtre à redimensionner.
     */
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

    /**
     * Méthode main classique pour lancer l'application JavaFX.
     *
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
