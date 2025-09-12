package net.github.dctime.events;

import net.github.dctime.GoogleAIStudioTranslatorClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT)
public class PlayerJoinWorldEvent {
    private static boolean loginHandled = false;

    @SubscribeEvent
    public static void onLocalPlayerJoinLevel(EntityJoinLevelEvent event) {
        if (!loginHandled && event.getEntity() == Minecraft.getInstance().player) {
            loginHandled = true;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("感謝使用 Google AI Studio Translator! 自動翻譯提示匡, Jade, Advancements 與 FTBQuest 的內容的小工具!").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("螢幕截圖會把裡面的英文文字翻成中文顯示在對話框").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("使用前請先去修改 config/googleaistudiotranslator-client.toml").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("按F4可以清除翻譯快取 可去按鍵設定修改").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("如果找到bug或是想要什麼請").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("點這裡").withStyle(s -> s.withColor(net.minecraft.ChatFormatting.GREEN).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/dctime/GoogleAIStudioTranslatorMod/issues"))));
        }
    }

    @SubscribeEvent
    public static void onClientLogout(net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut event) {
        loginHandled = false;
    }
}
