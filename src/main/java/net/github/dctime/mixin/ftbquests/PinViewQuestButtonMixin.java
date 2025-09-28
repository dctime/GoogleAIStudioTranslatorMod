package net.github.dctime.mixin.ftbquests;

import net.github.dctime.libs.ftbquests.IPinViewQuestButton;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "dev.ftb.mods.ftbquests.gui.quests.ViewQuestPanel$PinViewQuestButton")
public abstract class PinViewQuestButtonMixin implements IPinViewQuestButton {
}
