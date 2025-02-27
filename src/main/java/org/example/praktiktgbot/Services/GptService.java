package org.example.praktiktgbot.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.praktiktgbot.Entity.GptProperties;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class GptService {

    private final GptProperties properties;
    private final HttpClient client;
    private final Gson gson;

    public GptService(GptProperties properties) {
        this.properties = properties;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String answer(String text) {
        String apiUrl = properties.getApiUrl();
        String apiKey = properties.getApiKey();

        Map<String, Object> requestBody = Map.of(
                "model", "command-xlarge-nightly",
                "prompt", text,
                "max_tokens", 900,
                "temperature", 0.8
        );

        String jsonRequestBody = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Парсим JSON-ответ
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                return jsonResponse.get("text").getAsString();
            } else {
                return "Помилка: " + response.statusCode() + "\nВідповідь API: " + response.body();
            }
        } catch (Exception e) {
            return "Помилка з'єднання: " + e.getMessage();
        }
    }
}
