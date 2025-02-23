package org.example.praktiktgbot.Services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.praktiktgbot.Entity.GptProperties;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class GptService {

    private final GptProperties properties;

    public GptService(GptProperties properties) {
        this.properties = properties;
    }

    public String answer(String text) {
        String answer = "Ошибка получения ответа";

        String apiKey = properties.getApiKey();
        String apiUrl = properties.getApiUrl();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", text);
        requestBody.put("messages", new Map[]{message});

        Gson gson = new Gson();
        String jsonRequestBody = gson.toJson(requestBody);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray choices = jsonResponse.getAsJsonArray("choices");

                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    JsonObject messageObject = firstChoice.getAsJsonObject("message");
                    answer = messageObject.get("content").getAsString();
                }
            } else {
                System.out.println("Ошибка: " + response.statusCode());
                System.out.println("Ответ API: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Ошибка соединения: " + e.getMessage());
        }

        return answer;
    }
}
