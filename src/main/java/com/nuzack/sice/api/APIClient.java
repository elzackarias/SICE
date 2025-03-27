package com.nuzack.sice.api;

import com.google.gson.Gson;
import com.nuzack.sice.models.UserData;
import com.nuzack.sice.models.ApiResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {

    private static final String API_URL = "http://localhost:8080/api/index.php"; // Cambia esta URL a la API real

    public ApiResponse sendLoginRequest(UserData userData) throws IOException {
        // Convertir datos de usuario a JSON
        Gson gson = new Gson();
        String json = gson.toJson(userData);

        // Configurar la conexión HTTP
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Enviar los datos en el cuerpo de la solicitud
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Leer la respuesta de la API
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Convertir la respuesta JSON en un objeto ApiResponse
                return gson.fromJson(response.toString(), ApiResponse.class);
            }
        } else {
            throw new IOException("Error en la conexión. Código de respuesta: " + responseCode);
        }
    }
}
