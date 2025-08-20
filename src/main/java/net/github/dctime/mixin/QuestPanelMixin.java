package net.github.dctime.mixin;

import dev.ftb.mods.ftbquests.client.gui.quests.QuestPanel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(QuestPanel.class)
public abstract class QuestPanelMixin {

    @Inject(method="addWidgets", at = @At("RETURN"))
    public void useTest(CallbackInfo ci) {
        System.out.println("Adding Widgets to QuestPanel...");
    }
}
