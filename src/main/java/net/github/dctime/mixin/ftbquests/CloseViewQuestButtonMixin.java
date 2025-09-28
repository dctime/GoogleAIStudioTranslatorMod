package net.github.dctime.mixin.ftbquests;

import net.github.dctime.libs.ftbquests.ICloseViewQuestButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(targets = "dev.ftb.mods.ftbquests.gui.quests.ViewQuestPanel$CloseViewQuestButton")
public abstract class CloseViewQuestButtonMixin implements ICloseViewQuestButton {

}
