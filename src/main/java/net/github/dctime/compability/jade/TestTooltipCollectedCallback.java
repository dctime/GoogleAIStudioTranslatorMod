package net.github.dctime.compability.jade;

import net.github.dctime.Config;
import net.github.dctime.libs.Translator;
import net.minecraft.network.chat.Component;
import snownee.jade.api.Accessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.callback.JadeTooltipCollectedCallback;
import snownee.jade.api.ui.IElement;
import snownee.jade.impl.ui.TextElement;

import java.io.IOException;
import java.util.List;

public class TestTooltipCollectedCallback implements JadeTooltipCollectedCallback {

    @Override
    public void onTooltipCollected(ITooltip iTooltip, Accessor<?> accessor) {
        if (!Config.ENABLE_JADE_CONFIG.get()) return;
        for (int jadeIndex = 0; jadeIndex < iTooltip.size(); jadeIndex++) {
            String lineMsg = "";
            List<IElement> elements = iTooltip.get(jadeIndex, IElement.Align.LEFT);
            for (IElement element : elements) {
                if (element instanceof TextElement textElement) {
                    lineMsg += textElement.getMessage();
                }
            }

            if (!Translator.translationCache.containsKey(lineMsg)) {
                try {
                    Translator.requestTranslateToTraditionalChinese(lineMsg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            iTooltip.append(jadeIndex, new TextElement(Component.literal(" " + Translator.translationCache.get(lineMsg)).withStyle(Translator.translatedStyle)));
        }

    }
}
