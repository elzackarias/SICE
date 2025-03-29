package com.nuzack.sice.controllers;

import com.nuzack.sice.api.ApiClient;
import com.nuzack.sice.models.AlumnoData;
import com.nuzack.sice.models.ApiResponseAlumnos;
import com.nuzack.sice.models.UserData;
import com.nuzack.sice.utils.AppData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label role;
    @FXML
    private TextField gradoField;

    @FXML
    private TextField grupoField;
    @FXML
    private TableView<AlumnoData> alumnosTableView;
    @FXML
    private TableColumn<AlumnoData, String> columnaID;
    @FXML
    private TableColumn<AlumnoData, String> columnaApellidoPaterno;
    @FXML
    private TableColumn<AlumnoData, String> columnaApellidoMaterno;
    @FXML
    private TableColumn<AlumnoData, String> columnaNombre;
    @FXML
    private TableColumn<AlumnoData, String> columnaSexo;

    private ApiClient apiClient = new ApiClient();
    private String token;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserData userData = AppData.getInstance().getUserData();
        token = userData.getToken();
        role.setText(token);

        // Configurar las columnas
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        columnaApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
    }

    @FXML
    private void seleccionarAlumno(MouseEvent event) {
        if (event.getClickCount() == 2) { // Detectar doble clic
            AlumnoData alumnoSeleccionado = alumnosTableView.getSelectionModel().getSelectedItem();
            if (alumnoSeleccionado != null) {
                System.out.println("Doble clic en alumno: " + alumnoSeleccionado.getId());
                // Aquí puedes agregar más lógica para el doble clic
            }else{
                mostrarAlerta("Error", "Seleccione un alumno de la lista");
            }
        }
    }

    @FXML
    private void editarAlumno() {
        AlumnoData alumnoSeleccionado = alumnosTableView.getSelectionModel().getSelectedItem();
        if (alumnoSeleccionado != null) {
            // Aquí puedes manejar la edición del alumno
            System.out.println("Editar alumno: " + alumnoSeleccionado.getId());
        } else {
            mostrarAlerta("Error", "Seleccione un alumno de la lista");
        }
    }


    @FXML
    private void buscarAlumnos() {
        String grado = gradoField.getText().trim();
        String grupo = grupoField.getText().trim().toUpperCase();

        if (grado.isEmpty() || grupo.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar grado y grupo");
            return;
        }

        try {
            ApiResponseAlumnos respuesta = apiClient.obtenerAlumnosPorGradoGrupo(grado, grupo, token);

            if ("EXITO".equals(respuesta.getStatus())) {
                alumnosTableView.getItems().clear();
                alumnosTableView.getItems().addAll(respuesta.getData());
            } else {
                mostrarAlerta("Error en la API", respuesta.getMessage());
            }
        } catch (IOException e) {
            mostrarAlerta("Error de conexión", "No se pudo conectar al servidor: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        // Limpiar la sesión
        AppData.getInstance().clearSession();

        // Redirigir al login
        try {
            Stage currentStage = (Stage) role.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nuzack/sice/login-view.fxml"));
            Scene loginScene = new Scene(loader.load());

            // Configurar la nueva escena
            currentStage.setScene(loginScene);
            currentStage.setMaximized(true); // Mantener la ventana maximizada

            // Forzar el enfoque en macOS (similar a tu implementación original)
            currentStage.requestFocus();
            currentStage.toFront();

            if (System.getProperty("os.name").contains("Mac")) {
                Platform.runLater(() -> {
                    currentStage.setIconified(false);
                    currentStage.setAlwaysOnTop(true);
                    currentStage.setAlwaysOnTop(false);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error adecuadamente
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la pantalla de login");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
