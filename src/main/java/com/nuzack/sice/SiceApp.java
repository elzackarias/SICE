package com.nuzack.sice;

import com.nuzack.sice.utils.AppData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SiceApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Determinar qué vista cargar basado en la sesión
        String fxmlFile = AppData.getInstance().isLoggedIn()
                ? "home-view.fxml"
                : "login-view.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(fxmlFile.equals("home-view.fxml") ? "SICE - Inicio" : "SICE");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaximized(true);

        // Solución combinada para macOS
        stage.show();
        stage.requestFocus();
        stage.toFront();

        // Truco específico para macOS
        if (System.getProperty("os.name").contains("Mac")) {
            Platform.runLater(stage::requestFocus);
        }

    }

    public static void main(String[] args) {
        // Configuración específica para macOS
        System.setProperty("glass.gtk.uiScale", "1.0");
        System.setProperty("prism.allowhidpi", "false");

        launch(args);
    }
}