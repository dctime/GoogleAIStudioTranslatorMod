package net.github.dctime.events;

import com.mojang.datafixers.util.Either;
import net.github.dctime.GeminiTranslatorClient;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

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
                // Example transformation: prepend and uppercase
                Component replaced = Component.literal("[MOD] " + original.toUpperCase());
                elements.set(finalI, Either.left(replaced));
            });
        }
    }
}
