package net.github.dctime;

import net.github.dctime.datagen.LanguageProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = GoogleAIStudioTranslatorClient.MODID)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//@Mod.EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GoogleAIStudioTranslatorClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAIStudioTranslatorClient.class);
    public static final String MODID = "googleaistudiotranslator";
    public GoogleAIStudioTranslatorClient(IEventBus eventBus) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

//    @SubscribeEvent
//    static void onClientSetup(FMLClientSetupEvent event) throws IOException, InterruptedException {
//        // Some client setup code
//    }


}
