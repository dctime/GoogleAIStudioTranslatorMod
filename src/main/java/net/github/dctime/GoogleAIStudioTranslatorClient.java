package net.github.dctime;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = GoogleAIStudioTranslatorClient.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT)
public class GoogleAIStudioTranslatorClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAIStudioTranslatorClient.class);
    public static final String MODID = "googleaistudiotranslator";
    public GoogleAIStudioTranslatorClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) throws IOException, InterruptedException {
        // Some client setup code
    }

    private static boolean loginHandled = false;

    @SubscribeEvent
    public static void onLocalPlayerJoinLevel(EntityJoinLevelEvent event) {
        if (!loginHandled && event.getEntity() == Minecraft.getInstance().player) {
            loginHandled = true;
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Google AI Studio Translator Client Loaded!").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("感謝使用 Google AI Studio Translator! 自動翻譯提示匡與FTBQuest的內容的小工具!").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("如果找到bug或是想要什麼請到:").withStyle(net.minecraft.ChatFormatting.GREEN));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("點這裡").withStyle(s -> s.withColor(net.minecraft.ChatFormatting.GREEN).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/dctime/GoogleAIStudioTranslatorMod/issues"))));
        }
    }

    @SubscribeEvent
    public static void onClientLogout(net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut event) {
        loginHandled = false;
    }
}
