package net.github.dctime.libs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.GuardedSerializer;
import net.github.dctime.Config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Translator {
    public static String translateToTraditionalChinese(String textInEnglish) throws IOException, InterruptedException {
        if (textInEnglish.isBlank()) return "";
        String apiKey = Config.API_KEY.get();
        if (apiKey.isBlank()) return "API Key is not set. Please set it in the config file.";

        String model = "gemma-3n-e4b-it";
        String url = String.format("https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent", model);
//
        String prompt = "請把這個翻成繁體中文 不要加前後文方便我直接取代:\n" + textInEnglish;
        String jsonBody = """
                {
                  "contents": [
                    { "parts": [ { "text": "
                """ + prompt + """
                "} ] }
                  ]
                }
                """;
        String translatedText;

//
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("x-goog-api-key", apiKey) // 可以用 ?key=... 也行
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
//
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("status: " + resp.statusCode());


        GuardedSerializer serializer = new GuardedSerializer();
        String responseText = resp.body();
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(responseText, JsonObject.class);


        try {
            translatedText = response.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();
        } catch (Exception e) {
            translatedText = null;
            System.out.println("Error Response:" + responseText);
        }


//        System.out.println("Translated: " + textInEnglish + " -> " + translatedText);
        return translatedText;
    }
}
