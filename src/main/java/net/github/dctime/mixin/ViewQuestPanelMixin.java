package net.github.dctime.mixin;

import dev.ftb.mods.ftblibrary.ui.ModalPanel;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel;
import net.github.dctime.libs.FormattedTextGetterSetter;
import net.github.dctime.libs.Translator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(ViewQuestPanel.class)
public abstract class ViewQuestPanelMixin extends ModalPanel {

    @Shadow
    private TextField titleField;

    private ViewQuestPanelMixin(Panel panel) {
        super(panel);
    }

//    @Inject(method="addWidgets", at = @At("RETURN"))
//    public void useTest(CallbackInfo ci) {
//        System.out.println("Adding Widgets to ViewQuestPanel...");
//        if (!(titleField instanceof FormattedTextGetterSetter formattedTextGetter) || formattedTextGetter.getFormattedText().length < 1) {
//            System.out.println("Title field is not an instance of FormattedTextGetter, cannot translate.");
//            return;
//        }
//
//
//
//    }

    @Inject(method = "draw", at = @At("HEAD"))
    public void onDraw(CallbackInfo ci) {
        if (!(titleField instanceof FormattedTextGetterSetter formattedTextGetter) || formattedTextGetter.getFormattedText().length < 1) {
            System.out.println("Title field is not an instance of FormattedTextGetter, cannot translate.");
            return;
        }

        if (Translator.translationCache.containsKey(formattedTextGetter.getFormattedText()[0].getString())) {
            formattedTextGetter.setFormattedText(0, Translator.translationCache.get(formattedTextGetter.getFormattedText()[0].getString()));
            System.out.println("Using cached translation for title field: " + formattedTextGetter.getFormattedText()[0].getString());
        } else {
            System.out.println("Translating title field...");

            try {
                Translator.requestTranslateToTraditionalChinese(formattedTextGetter.getFormattedText()[0].getString());
            } catch (IOException ex) {
                System.out.println("IO Exception while translating: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("Interrupted Exception while translating: " + ex.getMessage());
            }
        }
    }
}
