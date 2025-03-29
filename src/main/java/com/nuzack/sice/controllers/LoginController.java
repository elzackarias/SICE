package com.nuzack.sice.controllers;

import com.google.gson.Gson;
import com.nuzack.sice.api.ApiClient;
import com.nuzack.sice.models.LoginRequest;
import com.nuzack.sice.models.LoginResponse;
import com.nuzack.sice.models.UserData;
import com.nuzack.sice.utils.AppData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label datos;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
        // Verificar sesión existente al iniciar
        if (AppData.getInstance().isLoggedIn()) {
            redirectToDashboard();
        }
        // Ejecutar la acción al presionar Enter en cualquiera de los campos
        username.setOnAction(event -> onButtonLogin());
        password.setOnAction(event -> onButtonLogin());
    }

    @FXML
    protected void onButtonLogin() {
        String user = username.getText();
        String pass = password.getText();
        datos.setText(user + " " + pass);

        LoginRequest userRequest = new LoginRequest(user, pass);
        Gson gson = new Gson();
        String json = gson.toJson(userRequest);
        try{
            String response = ApiClient.makePostRequest("login", json, null);
            LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);

            // Verificar si el status es "EXITO" o "ERROR"
            if ("EXITO".equals(loginResponse.getStatus())) {
                // Si es exitoso, obtener la data
                if (loginResponse.getData() != null) {
                    UserData userData = loginResponse.getData();
                    //AppData.getInstance().setUserData(userData);
                    datos.setText("Bienvenido, " + userData.getName() + " " + userData.getApellidos());
                    System.out.println("Token: " + userData.getToken());  // Puedes guardar el token para futuras peticiones
                    AppData.getInstance().setUserData(userData);
                    redirectToDashboard();
                } else {
                    datos.setText("Inicio de sesión exitoso, pero sin datos.");
                }
            } else {
                // Si hay error, mostrar el mensaje de la API
                datos.setText("Error: " + loginResponse.getMsg());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ocurrio un error");
                alert.setContentText(loginResponse.getMsg());

                alert.showAndWait(); // Muestra la alerta y espera hasta que el usuario la cierr
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
    private void loadDashboard() {
        try {
            // Cargar el nuevo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/nuzack/sice/home-view.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());

            // Obtener el Stage actual
            Stage currentStage = (Stage) username.getScene().getWindow();

            // Reemplazar la escena actual
            currentStage.setScene(newScene);
            currentStage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            datos.setText("Error al cargar la pantalla.");
        }
    }
    private void redirectToDashboard() {
        try {
            Stage currentStage = (Stage) username.getScene().getWindow();
            Scene dashboardScene = new Scene(FXMLLoader.load(getClass().getResource("/com/nuzack/sice/home-view.fxml")));
            currentStage.setScene(dashboardScene);
            currentStage.setTitle("Inicio - SICE");
            currentStage.setMaximized(true); // Maximizar la ventana
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
