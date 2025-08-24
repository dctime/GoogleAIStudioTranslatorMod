package net.github.dctime.mixin.ftbquests;

import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.ModalPanel;
import dev.ftb.mods.ftblibrary.ui.Panel;
import net.github.dctime.libs.ftbquests.ModalPanelsGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Deque;

@Mixin(BaseScreen.class)
public abstract class BaseScreenMixin extends Panel implements ModalPanelsGetter {
    public BaseScreenMixin(Panel panel) {
        super(panel);
    }

    @Shadow @Final
    private Deque<ModalPanel> modalPanels;

    public Deque<ModalPanel> getModalPanels() {
        return modalPanels;
    }
}
