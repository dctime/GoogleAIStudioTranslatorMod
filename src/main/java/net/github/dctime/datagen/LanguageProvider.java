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
        add("key.categories."+GoogleAIStudioTranslatorClient.MODID+".key_mapping_category", "Google AI Studio Translator");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.API_KEY_PATH, "API Key (API 金鑰)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.MODEL_NAME_PATH, "Model Name (模型名稱)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.PROMPT_PATH, "Prompt (提示語)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_JADE_CONFIG_PATH, "Enable Jade Integration (啟用 Jade 整合)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_FTB_QUEST_TRANSLATION_PATH, "Enable FTB Quests Integration (啟用 FTB Quests 整合)");
        add(GoogleAIStudioTranslatorClient.MODID+".configuration."+ Config.ENABLE_TOOLTIP_TRANSLATION_PATH, "Enable Tooltip Translation (啟用物品提示匡翻譯)");
    }
}
