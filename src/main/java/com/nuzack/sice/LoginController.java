package com.nuzack.sice;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label datos;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
        // Ejecutar la acción al presionar Enter en cualquiera de los campos
        username.setOnAction(event -> onButtonLogin());
        password.setOnAction(event -> onButtonLogin());
    }

    @FXML
    protected void onButtonLogin() {
        String user = username.getText();
        String pass = password.getText();
        datos.setText(user + " " + pass);

        UserData userData = new UserData(user, pass);

        Gson gson = new Gson();
        String json = gson.toJson(userData);
        System.out.println(json);
    }

    private static class UserData {
        public String username;
        public final String password;

        public UserData(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
