package net.github.dctime.events;

import com.mojang.datafixers.util.Either;
import net.github.dctime.GeminiTranslatorClient;
import net.github.dctime.libs.Translator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@EventBusSubscriber(modid = GeminiTranslatorClient.MODID, value = Dist.CLIENT)
public class RenderTooltipEvent {
//    @SubscribeEvent
//    public static void onRenderTooltip(net.neoforged.neoforge.client.event.RenderTooltipEvent.GatherComponents event) {
//        for (Either<FormattedText, TooltipComponent> component : event.getTooltipElements()) {
//            component.ifLeft((text)->{
//               System.out.println(text.getString());
//
//            });
//        }
//    }
    @SubscribeEvent
    public static void onRenderTooltip(net.neoforged.neoforge.client.event.RenderTooltipEvent.GatherComponents event) {
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
                        System.out.println("IO Exception while translating: " + ex.getMessage());
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted Exception while translating: " + ex.getMessage());
                    }
                    return;
//                    if (translated == null) return;
//                    translationCache.put(original, translated);
                }
                Component replaced;
                // 移除換行符號
                translated = translated.replace("\n", " ");

                // 移除控制字符（避免顯示方框）
                translated = translated.replaceAll("\\p{Cntrl}", "");

                // 去掉首尾空白
                translated = translated.trim();
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
