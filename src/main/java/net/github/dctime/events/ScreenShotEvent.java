package net.github.dctime.events;

import com.mojang.blaze3d.platform.NativeImage;
import net.github.dctime.Config;
import net.github.dctime.GoogleAIStudioTranslatorClient;
import net.github.dctime.libs.Translator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ScreenshotEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Mod.EventBusSubscriber(modid = GoogleAIStudioTranslatorClient.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ScreenShotEvent {
    public static String lastScreenShotImage = null;
    @SubscribeEvent
    public static void onScreenShot(ScreenshotEvent event) {
        if (!Config.ENABLE_SCREENSHOT_CONFIG.get()) return;
//        System.out.println("SCREEN SHOT!");
        NativeImage image = event.getImage();
        try {
            lastScreenShotImage = pixelsToBase64(image.getPixelsRGBA(), image.getWidth(), image.getHeight());
        } catch (Exception e) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("螢幕截圖檔案無法翻成Base64" + e.getMessage()).withStyle(ChatFormatting.RED));
            }
//            System.out.println("Error processing image: " + e.getMessage());
            lastScreenShotImage = null;
        }

//        if (lastScreenShotImage != null) {
//            System.out.println("Image:\n" + lastScreenShotImage);
//        }

        if (Translator.translating) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("翻譯器正在忙碌中，請稍後再截圖。").withStyle(ChatFormatting.YELLOW));
                ScreenEventRender.setRenderText("翻譯器正在忙碌中，請稍後再截圖。");
                return;
            }
        }

        try {
            Translator.requestTranslateToTraditionalChinese(":", lastScreenShotImage);
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("螢幕截圖翻譯中...").withStyle(ChatFormatting.GREEN));
                ScreenEventRender.setRenderText("螢幕截圖翻譯中...");
            }
        } catch (IOException | InterruptedException e) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("螢幕翻譯錯誤: " + e.getMessage()).withStyle(ChatFormatting.RED));
            }
        }

    }

    public static String pixelsToBase64(int[] pixels, int width, int height) throws Exception {
        // 建立 BufferedImage (ARGB 格式)
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] imageData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        // 複製你的 RGBA 資料進去
        System.arraycopy(pixels, 0, imageData, 0, pixels.length);

        // 轉成 PNG (你也可以換成 "jpg")
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        // Base64 編碼
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
