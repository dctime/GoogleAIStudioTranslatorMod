package net.github.dctime.events;

import com.mojang.blaze3d.platform.NativeImage;
import net.github.dctime.Config;
import net.github.dctime.GoogleAIStudioTranslatorClient;
import net.github.dctime.libs.Translator;
import net.github.dctime.screen.ScreenShotSelectAreaScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenshotEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ScreenShotEvent {
    @SubscribeEvent
    public static void onScreenShot(ScreenshotEvent event) {
        if (!Config.ENABLE_SCREENSHOT_CONFIG.get()) return;
        final NativeImage image = event.getImage();
        File file = event.getScreenshotFile();

        Minecraft.getInstance().setScreen(new ScreenShotSelectAreaScreen(image, Minecraft.getInstance().screen));
//        System.out.println("SCREEN SHOT!");



//        if (lastScreenShotImage != null) {
//            System.out.println("Image:\n" + lastScreenShotImage);
//        }



    }


}
