package net.github.dctime.mixin.ftbquests;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import net.github.dctime.libs.ftbquests.FormattedTextGetterSetter;
import net.github.dctime.libs.Translator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextField.class)
public abstract class TextFieldMixin extends Widget implements FormattedTextGetterSetter {
    @Shadow(remap = false)
    private FormattedText[] formattedText;

    @Shadow(remap = false)
    private Component rawText;
    private FormattedText rawTranslatedText;

    @Shadow(remap = false)
    public abstract TextField resize(Theme theme);

    @Shadow(remap = false)
    public int maxWidth;

    public TextFieldMixin(Panel p) {
        super(p);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
//        System.out.println("TextField mixin appled!");
    }

    public FormattedText[] getFormattedText() {
        return formattedText;
    }

    public void setTranslatedFormattedText(String text) {
        rawTranslatedText = FormattedText.of(" " + text, Translator.translatedStyle);
        // reflow with the translated text
        Theme theme = this.getGui().getTheme();
        this.formattedText = (FormattedText[])theme.listFormattedStringToWidth(FormattedText.composite(this.rawText, rawTranslatedText), this.maxWidth).toArray(new FormattedText[0]);
        resize(theme);
    }
}
