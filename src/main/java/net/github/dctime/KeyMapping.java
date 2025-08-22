package net.github.dctime;

import com.mojang.blaze3d.platform.InputConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, value = Dist.CLIENT)
public class KeyMapping {
    public static final Lazy<net.minecraft.client.KeyMapping> DELETE_TRANSLATION_CACHE = Lazy.of(() -> new net.minecraft.client.KeyMapping(
            "key."+GoogleAIStudioTranslatorClient.MODID+".delete_translation_cache",
            KeyConflictContext.UNIVERSAL,
            KeyModifier.NONE, // Default mapping requires shift to be held down
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_F4,
            "key.categories."+GoogleAIStudioTranslatorClient.MODID+".key_mapping_category"
    ));

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(DELETE_TRANSLATION_CACHE.get());
    }
}
