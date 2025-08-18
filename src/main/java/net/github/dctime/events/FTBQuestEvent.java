package net.github.dctime.events;

import dev.ftb.mods.ftblibrary.ui.IScreenWrapper;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.github.dctime.GeminiTranslatorClient;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;


@EventBusSubscriber(value = Dist.CLIENT, modid = GeminiTranslatorClient.MODID)
public class FTBQuestEvent {



    @SubscribeEvent
    public static void renderEvent(ScreenEvent.Render.Post event) {
        findFTBQuest(event);
    }

    @SubscribeEvent
    public static void renderEventPost(ScreenEvent.Render.Post event) {
        resetFTBQuest(event);
    }

    private static Quest cachedQuest = null;
    private static String cachedTitle = null;

    public static void findFTBQuest(ScreenEvent.Render.Post event) {
        if(!(event.getScreen() instanceof IScreenWrapper isw)) {
            failedToFindFTBQuest();
            return;
        }
        if(!(isw.getGui() instanceof QuestScreen questScreen)) {
            failedToFindFTBQuest();
            return;
        }
        Quest quest = questScreen.getViewedQuest();
        if (quest == null) {
            failedToFindFTBQuest();
            return;
        }

        if (cachedTitle != null) return;
        cachedQuest = quest;
        cachedTitle = quest.getRawTitle();
        quest.setRawTitle("UHHUHUHU");
        questScreen.refreshViewQuestPanel();
    }

    public static void failedToFindFTBQuest() {
        cachedTitle = null;
        cachedQuest = null;
    }

    public static void resetFTBQuest(ScreenEvent.Render.Post event) {
//        if(!(event.getScreen() instanceof IScreenWrapper isw)) return;
//        if(!(isw.getGui() instanceof QuestScreen questScreen)) return;
//        Quest quest = questScreen.getViewedQuest();
//        if(quest == null) return;
//        quest.setRawTitle("Hello");


    }
}
