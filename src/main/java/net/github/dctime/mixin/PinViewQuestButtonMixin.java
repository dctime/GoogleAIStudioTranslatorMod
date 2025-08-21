package net.github.dctime.mixin;

import net.github.dctime.libs.IPinViewQuestButton;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel$PinViewQuestButton")
public abstract class PinViewQuestButtonMixin implements IPinViewQuestButton {
}
