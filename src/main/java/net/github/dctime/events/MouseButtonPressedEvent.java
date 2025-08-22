package net.github.dctime.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.github.dctime.GoogleAIStudioTranslatorClient;
import net.github.dctime.KeyMapping;
import net.github.dctime.libs.Translator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT)
public class MouseButtonPressedEvent {
    @SubscribeEvent
    public static void onMouseButtonPressed(ScreenEvent.KeyPressed.Post event) {
        int keyCode = event.getKeyCode();
        int scanCode = event.getScanCode();
        if (KeyMapping.DELETE_TRANSLATION_CACHE.get().isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
            Translator.clearCache();
        }
    }
}
