package net.github.dctime.mixin;

import dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel;
import net.github.dctime.libs.ICloseViewQuestButton;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel$CloseViewQuestButton")
public abstract class CloseViewQuestButtonMixin implements ICloseViewQuestButton {

}
