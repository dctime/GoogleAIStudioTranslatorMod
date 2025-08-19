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
import java.util.HashMap;

public class Translator {
    public static HashMap<String, String> translationCache = new HashMap<>();
    public static boolean translating = false;
    public static void requestTranslateToTraditionalChinese(String textInEnglish) throws IOException, InterruptedException {
        String apiKey = Config.API_KEY.get();
        if (apiKey.isBlank()) return;

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
        if (textInEnglish.isBlank()) {
            translationCache.put(textInEnglish, "");
            return;
        }

        if (translating) return;
        translating = true;

        client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenAccept(resp->{
                    String responseText = resp.body();
                    Gson gson = new Gson();
                    JsonObject response = gson.fromJson(responseText, JsonObject.class);
                    String translatedText;

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
                    if (translatedText != null) {
                        translationCache.put(textInEnglish, translatedText);
                        System.out.println("Translated: " + textInEnglish + " -> " + translatedText);
                    }

                    System.out.println("status: " + resp.statusCode());
                    translating = false;
                });
    }
}
