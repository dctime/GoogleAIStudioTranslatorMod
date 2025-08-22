package net.github.dctime.datagen;

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
    }
}
