package net.github.dctime.mixin;

import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel;
import net.github.dctime.libs.FormattedTextGetterSetter;
import net.github.dctime.libs.Translator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mixin(ViewQuestPanel.class)
public abstract class ViewQuestPanelMixin extends ModalPanel {

    @Shadow
    private TextField titleField;
    @Shadow
    private BlankPanel panelText;

    private boolean isViewQuestPanelTranslated = false;
    private List<Boolean> isDescriptionTranslated = null;

    private ViewQuestPanelMixin(Panel panel) {
        super(panel);
    }

    @Inject(method = "draw", at = @At("HEAD"))
    public void onDraw(CallbackInfo ci) {
        translateTitle();

        if (isDescriptionTranslated == null) {
            isDescriptionTranslated = new ArrayList<>(panelText.getWidgets().size());
            for (int widgetIndex = 0; widgetIndex < panelText.getWidgets().size(); widgetIndex++) {
                if (panelText.getWidgets().get(widgetIndex) instanceof FormattedTextGetterSetter) {
                    isDescriptionTranslated.add(false);
                } else {
                    isDescriptionTranslated.add(true);
                }
            }
            return;
        }

        for (int widgetIndex = 0; widgetIndex < isDescriptionTranslated.size(); widgetIndex++) {
            if (isDescriptionTranslated.get(widgetIndex)) continue;
            // not translated yet, so we need to translate it
            Widget widget = panelText.getWidgets().get(widgetIndex);

            if (!(widget instanceof FormattedTextGetterSetter formattedTextGetter)) return;
            if (translateFormattedText(formattedTextGetter))
                isDescriptionTranslated.set(widgetIndex, true);
        }
    }

    @Inject(method = "onClosed", at = @At("HEAD"))
    public void onClosed(CallbackInfo ci) {
        System.out.println("Warning OnClosed called, resetting translation state.");
        isViewQuestPanelTranslated = false;
        isDescriptionTranslated = null;
    }

    @Inject(method = "setCurrentPage", at = @At("RETURN"))
    public void onSetCurrentPage(int page, CallbackInfo ci) {
        System.out.println("setCurrentPage called with page: " + page);
        // Reset translation state when changing pages
        isViewQuestPanelTranslated = false;
        isDescriptionTranslated = null;
    }

    private boolean translateFormattedText(FormattedTextGetterSetter formattedTextGetter) {
        if (formattedTextGetter.getFormattedText().length < 1) {
            System.out.println("FormattedText is empty, cannot translate.");
            return false;
        }

        String translateText = formattedTextGetter.getFormattedText()[0].getString();
        if (Translator.translationCache.containsKey(translateText)) {
            formattedTextGetter.setFormattedText(0, Translator.translationCache.get(translateText));
            System.out.println("Using cached translation for: " + translateText);

            return true;
        } else {
            System.out.println("Translating text: " + translateText);
            try {
                Translator.requestTranslateToTraditionalChinese(translateText);
            } catch (IOException ex) {
                System.out.println("IO Exception while translating: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("Interrupted Exception while translating: " + ex.getMessage());
            }
        }
        return false;
    }

    private void translateTitle() {
        if (isViewQuestPanelTranslated) {
//            System.out.println("Title field already translated, skipping translation.");
            return;
        }

        if (!(titleField instanceof FormattedTextGetterSetter formattedTextGetter) || formattedTextGetter.getFormattedText().length < 1) {
            System.out.println("Title field is not an instance of FormattedTextGetter, cannot translate.");
            return;
        }

        if (translateFormattedText(formattedTextGetter)) {
            isViewQuestPanelTranslated = true;
        }
    }
}
