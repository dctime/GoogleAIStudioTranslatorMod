package net.github.dctime.events;

import dev.ftb.mods.ftblibrary.ui.IScreenWrapper;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.github.dctime.GeminiTranslatorClient;
import net.github.dctime.libs.Translator;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.io.IOException;


//@EventBusSubscriber(value = Dist.CLIENT, modid = GeminiTranslatorClient.MODID)
public class FTBQuestEvent {

    public static void renderEvent(ScreenEvent.Render.Post event) throws IOException, InterruptedException {
        tryTranslateFTBQuest(event);
    }

    private static String cachedTitle = null;
    private static Quest cachedQuest = null;

    public static void tryTranslateFTBQuest(ScreenEvent.Render.Post event) throws IOException, InterruptedException {
        if(!(event.getScreen() instanceof IScreenWrapper isw)) {
            failedToFindFTBQuest(null);
            return;
        }
        if(!(isw.getGui() instanceof QuestScreen questScreen)) {
            failedToFindFTBQuest(null);
            return;
        }

        Quest quest = questScreen.getViewedQuest();

        if (quest == null) {
            failedToFindFTBQuest(questScreen);
            return;
        }

        if (cachedTitle != null || cachedQuest != null) return;
        if (quest.getRawTitle().isBlank()) return;

        cachedTitle = quest.getRawTitle();
        cachedQuest = quest;
        System.out.println("Title:" + cachedTitle);
//        String translatedTitle = Translator.translateToTraditionalChinese(cachedTitle);
//        if (translatedTitle == null) return;
//        quest.setRawTitle(translatedTitle);
        questScreen.refreshViewQuestPanel();
    }

    public static void failedToFindFTBQuest(QuestScreen screen) {
        if (cachedQuest != null) {
            cachedQuest.setRawTitle(cachedTitle);
            if (screen != null)
                screen.refreshQuestPanel();
        }

        cachedTitle = null;
        cachedQuest = null;
    }
}
