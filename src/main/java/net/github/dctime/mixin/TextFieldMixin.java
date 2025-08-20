package net.github.dctime.mixin;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Widget;
import net.github.dctime.libs.FormattedTextGetterSetter;
import net.github.dctime.libs.Translator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextField.class)
public abstract class TextFieldMixin extends Widget implements FormattedTextGetterSetter {
    @Shadow
    private FormattedText[] formattedText;

    public TextFieldMixin(Panel p) {
        super(p);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        System.out.println("TextField mixin appled!");
    }

    public FormattedText[] getFormattedText() {
        return formattedText;
    }

    public void setFormattedText(int index, String text) {
        if (index < 0 || index >= formattedText.length) {
            throw new IndexOutOfBoundsException("Index out of bounds for formattedText array");
        }

//        FormattedText newFormattedText = (FormattedText) Component.literal(text).setStyle(Translator.translatedStyle);
        FormattedText newFormattedText = FormattedText.composite(formattedText[index], (FormattedText) Component.literal(" " + text).setStyle(Translator.translatedStyle));
        formattedText[index] = newFormattedText;

    }
}
