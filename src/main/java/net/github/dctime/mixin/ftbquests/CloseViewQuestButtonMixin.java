package net.github.dctime.mixin.ftbquests;

import net.github.dctime.libs.ftbquests.ICloseViewQuestButton;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel$CloseViewQuestButton")
public abstract class CloseViewQuestButtonMixin implements ICloseViewQuestButton {

}
