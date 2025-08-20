package net.github.dctime.libs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.dto.GuardedSerializer;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel;
import net.github.dctime.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Translator {
    public static HashMap<String, String> translationCache = new HashMap<>();
    public static boolean translating = false;
    public static boolean translatingTitle = false;
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    // --- ftb quest ---

    public static TextField cachedTextField = null;
    public static final Style translatedStyle = Style.EMPTY.withColor(ChatFormatting.GRAY);

    @Nullable
    private static HttpRequest setupRequest(String textInEnglish) {
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

        String apiKey = Config.API_KEY.get();
        if (apiKey.isBlank()) return null;

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
            return null;
        }

        return req;
    }

    public static void requestTranslateToTraditionalChinese(String textInEnglish) throws IOException, InterruptedException {

        HttpRequest req = setupRequest(textInEnglish);
        if (req == null) {
            System.out.println("Error when setting up request for translation.");
            return;
        }

        if (translating) {
            System.out.println("Translator in use.");
            return;
        }
        translating = true;

        CLIENT.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenAccept(resp -> {
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
                        // 移除換行符號
                        translatedText = translatedText.replace("\n", " ");
                        // 移除控制字符（避免顯示方框）
                        translatedText = translatedText.replaceAll("\\p{Cntrl}", "");
                        // 去掉首尾空白
                        translatedText = translatedText.trim();
                        translationCache.put(textInEnglish, translatedText);
                        System.out.println("Translated: " + textInEnglish + " -> " + translatedText);
                    }

                    System.out.println("status: " + resp.statusCode());
                    translating = false;
                });
    }
}
