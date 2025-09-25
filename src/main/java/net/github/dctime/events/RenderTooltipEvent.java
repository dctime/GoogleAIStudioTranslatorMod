package net.github.dctime.events;

import com.mojang.datafixers.util.Either;
import net.github.dctime.Config;
import net.github.dctime.GoogleAIStudioTranslatorClient;
import net.github.dctime.libs.Translator;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT)
public class RenderTooltipEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenderTooltipEvent.class);
    @SubscribeEvent
    public static void onRenderTooltip(net.minecraftforge.client.event.RenderTooltipEvent.GatherComponents event) {
        if (!Config.ENABLE_TOOLTIP_TRANSLATION.get()) return;
        var elements = event.getTooltipElements();

        for (int i = 0; i < elements.size(); i++) {
            var e = elements.get(i);
            int finalI = i;
            e.ifLeft(text -> {
                String original = text.getString();
                String translated;
                if (Translator.translationCache.containsKey(original))
                    translated = Translator.translationCache.get(original);
                else {
                    try {
                        Translator.requestTranslateToTraditionalChinese(original);
                    } catch (IOException ex) {
                        LOGGER.warn("IO Exception while translating: " + ex.getMessage());
                    } catch (InterruptedException ex) {
                        LOGGER.warn("Interrupted Exception while translating: " + ex.getMessage());
                    }
                    return;
//                    if (translated == null) return;
//                    translationCache.put(original, translated);
                }
                Component replaced;

                if (text instanceof Component textComponent)
                    replaced = textComponent.copy().append(Component.literal(" " + translated).setStyle(Translator.translatedStyle));
                else
                // Example transformation: prepend and uppercase
                    replaced = Component.literal(text.getString()).append(Component.literal(" " + translated).setStyle(Translator.translatedStyle));
                elements.set(finalI, Either.left(replaced));
            });
        }
    }
}
