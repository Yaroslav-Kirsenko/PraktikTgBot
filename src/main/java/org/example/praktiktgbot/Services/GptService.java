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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GptService {

    private final GptProperties properties;

    public GptService(GptProperties properties) {
        this.properties = properties;
    }

    public String answer(String text) {

        String apiKey = properties.getApiKey();
        String apiUrl = properties.getApiUrl() + apiKey;


        String answer = "Ошибка получения ответа";

        // Формируем JSON-тело запроса
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");

        List<Map<String, String>> parts = new ArrayList<>();
        Map<String, String> part = new HashMap<>();
        part.put("text", text);
        parts.add(part);

        userMessage.put("parts", parts);
        contents.add(userMessage);

        requestBody.put("contents", contents);

        // Преобразуем в JSON
        Gson gson = new Gson();
        String jsonRequestBody = gson.toJson(requestBody);

        // Создаем HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                .build();

        try {
            // Отправляем запрос
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Разбираем JSON-ответ
                String responseBody = response.body();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray candidates = jsonResponse.getAsJsonArray("candidates");

                if (candidates != null && candidates.size() > 0) {
                    JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
                    JsonObject content = firstCandidate.getAsJsonObject("content");

                    JsonArray responseParts = content.getAsJsonArray("parts");
                    if (responseParts != null && responseParts.size() > 0) {
                        answer = responseParts.get(0).getAsJsonObject().get("text").getAsString();
                    }
                }
            } else {
                System.out.println("Ошибка API: " + response.statusCode());
                System.out.println("Ответ: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Ошибка соединения: " + e.getMessage());
        }

        return answer;
    }
}
