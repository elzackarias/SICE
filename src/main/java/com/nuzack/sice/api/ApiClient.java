package com.nuzack.sice.api;

import com.google.gson.Gson;
import com.nuzack.sice.models.ApiResponseAlumnos;
import com.nuzack.sice.models.GradoGrupoRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ApiClient {
    private static final String BASE_URL = "http://localhost/presente/api/index.php";
    private static final Gson gson = new Gson();
    private String authToken;

    public ApiClient() {
        // Constructor vacío
    }

    public void setAuthToken(String token) {
        this.authToken = token;
    }

    /**
     * Método privado para preparar la conexión HTTP con configuración común
     * @param urlString URL completa con parámetros
     * @param method Método HTTP (GET, POST, etc.)
     * @param contentType Tipo de contenido (ej. "application/json")
     * @return La conexión configurada
     * @throws IOException Si hay error al abrir la conexión
     */
    private static HttpURLConnection prepareConnection(String urlString, String method, String contentType, String authToken) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept", "application/json");
        // Añadir header de autorización si existe token
        if (authToken != null && !authToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + authToken);
        }

        if (contentType != null) {
            connection.setRequestProperty("Content-Type", contentType);
        }

        if ("POST".equals(method)) {
            connection.setDoOutput(true);
        }

        // Timeouts para evitar bloqueos
        connection.setConnectTimeout(5000); // 5 segundos
        connection.setReadTimeout(5000);    // 5 segundos

        return connection;
    }

    /**
     * Realiza una solicitud POST enviando un JSON
     * @param endpoint Tipo de operación (ej. "login")
     * @param jsonPayload Cuerpo de la solicitud en JSON
     * @return Respuesta como String
     * @throws IOException Si hay error de conexión
     */
    public static String makePostRequest(String endpoint, String jsonPayload, String authToken) throws IOException {
        String urlString = BASE_URL + "?type=" + endpoint;
        HttpURLConnection connection = prepareConnection(urlString, "POST", "application/json", authToken);

        // Escribir el cuerpo de la solicitud
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        return readResponse(connection);
    }

    /**
     * Realiza una solicitud GET
     * @param endpoint Tipo de operación (ej. "test")
     * @return Respuesta como String
     * @throws IOException Si hay error de conexión
     */
    public static String makeGetRequest(String endpoint, String authToken) throws IOException {
        String urlString = BASE_URL + "?type=" + endpoint;
        HttpURLConnection connection = prepareConnection(urlString, "GET", null, authToken);
        return readResponse(connection);
    }


    public ApiResponseAlumnos obtenerAlumnosPorGradoGrupo(String grado, String grupo, String authToken) throws IOException {
        String urlParameters = BASE_URL + "?type=q&que=alumnosGradoGrupo";
        GradoGrupoRequest requestBody = new GradoGrupoRequest(grado, grupo);

        HttpURLConnection connection = prepareConnection(urlParameters, "POST","application/json", authToken);

        // Enviar el cuerpo de la solicitud
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = gson.toJson(requestBody).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Procesar la respuesta
        int responseCode = connection.getResponseCode();

        try (InputStream inputStream = responseCode < HttpURLConnection.HTTP_BAD_REQUEST ?
                connection.getInputStream() : connection.getErrorStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String response = reader.lines().collect(Collectors.joining());

            if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                return gson.fromJson(response, ApiResponseAlumnos.class);
            } else {
                ApiResponseAlumnos errorResponse = new ApiResponseAlumnos();
                errorResponse.setStatus("error");
                errorResponse.setMessage("HTTP error " + responseCode + ": " + response);
                return errorResponse;
            }
        }
    }

    /**
     * Lee la respuesta de la conexión
     * @param connection Conexión configurada
     * @return Contenido de la respuesta
     * @throws IOException Si hay error al leer la respuesta
     */
    private static String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }
}