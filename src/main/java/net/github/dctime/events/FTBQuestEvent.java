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
    public static void renderEvent(ScreenEvent.Render.Pre event) {
        findFTBQuest(event);
    }

    public static void findFTBQuest(ScreenEvent.Render.Pre event) {
        if(!(event.getScreen() instanceof IScreenWrapper isw)) return;
        if(!(isw.getGui() instanceof QuestScreen questScreen)) return;
        Quest quest = questScreen.getViewedQuest();
        if(quest == null) return;
        if (quest.getTitle() instanceof MutableComponent titleComponent) {
//            titleComponent.append("HEYYYYY");
            System.out.println(titleComponent.getString());
            quest.setRawTitle(titleComponent.getString() + " Bruhhhh");

        }
    }
}
