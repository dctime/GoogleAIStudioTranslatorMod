package net.github.dctime;

import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyMapping {
    public static final Lazy<net.minecraft.client.KeyMapping> DELETE_TRANSLATION_CACHE = Lazy.of(() -> new net.minecraft.client.KeyMapping(
            "key."+GoogleAIStudioTranslatorClient.MODID+".delete_translation_cache",
            KeyConflictContext.UNIVERSAL,
            KeyModifier.NONE, // Default mapping requires shift to be held down
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_F4,
            "key.categories."+GoogleAIStudioTranslatorClient.MODID+".key_mapping_category"
    ));

    public static final Lazy<net.minecraft.client.KeyMapping> SHOW_TRANSLATION_IN_GUI = Lazy.of(() -> new net.minecraft.client.KeyMapping(
            "key."+GoogleAIStudioTranslatorClient.MODID+".show_translation_in_gui",
            KeyConflictContext.UNIVERSAL,
            KeyModifier.NONE, // Default mapping requires shift to be held down
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_LEFT_ALT,
            "key.categories."+GoogleAIStudioTranslatorClient.MODID+".key_mapping_category"
    ));

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(DELETE_TRANSLATION_CACHE.get());
        event.register(SHOW_TRANSLATION_IN_GUI.get());
    }
}
