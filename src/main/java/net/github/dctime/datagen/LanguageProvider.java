package net.github.dctime.datagen;

import net.github.dctime.Config;
import net.github.dctime.GoogleAIStudioTranslatorClient;
import net.minecraft.data.PackOutput;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {

    public LanguageProvider(PackOutput output) {
        super(output, GoogleAIStudioTranslatorClient.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("key."+GoogleAIStudioTranslatorClient.MODID+".delete_translation_cache", "Delete Translation Cache");
        add("key."+GoogleAIStudioTranslatorClient.MODID+".show_translation_in_gui", "Show Translation in GUI (Hold)");
        add("key.categories."+GoogleAIStudioTranslatorClient.MODID+".key_mapping_category", "Google AI Studio Translator");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.API_KEY_PATH, "API Key (API 金鑰)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.MODEL_NAME_PATH, "Model Name (模型名稱)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.PROMPT_PATH, "Prompt (提示語)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.PROMPT_SCREENSHOT_PATH, "Prompt for Screenshot Translation (翻譯螢幕截圖的提示語)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_JADE_CONFIG_PATH, "Enable Jade Integration (啟用 Jade 整合)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_FTB_QUEST_TRANSLATION_PATH, "Enable FTB Quests Integration (啟用 FTB Quests 整合)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_TOOLTIP_TRANSLATION_PATH, "Enable Tooltip Translation (啟用物品提示匡翻譯)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_ADVANCEMENTS_CONFIG_PATH, "Enable Advancements Translation (啟用成就翻譯)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_SCREENSHOT_CONFIG_PATH, "Enable Screenshot Translation (啟用螢幕截圖翻譯)");

    }
}
