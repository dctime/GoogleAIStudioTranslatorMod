package net.github.dctime.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin {
    @Shadow
    @Final
    @Mutable
    private FormattedCharSequence title;

    @Shadow
    @Final
    @Mutable
    private List<FormattedCharSequence> description;

    private FormattedCharSequence tempTitle;
    private List<FormattedCharSequence> tempDesc = new ArrayList<>();

    @Shadow
    protected abstract int getMaxProgressWidth();


    @Inject(method = "drawHover", at = @At(value = "HEAD"))
    public void onDrawHover(GuiGraphics guiGraphics, int x, int y, float fade, int width, int height, CallbackInfo ci) {
        // replace orignal title
        AtomicReference<String> text = new AtomicReference<>("");
        title.accept((var1, var2, var3) -> {
//            System.out.println("var1: " + var1 + ", var2: " + var2 + ", var3: " + var3);
            text.set(text.get() + (char) var3);
            return true;
        });
        System.out.println("Title Text: " + text.get());

        FormattedCharSequence seq = FormattedCharSequence.forward(" HMMMMM", Style.EMPTY);
        tempTitle = title;
        title = FormattedCharSequence.composite(title, seq);

        // replace orignal description
        List<FormattedCharSequence> replaceDesc = new ArrayList<>();
        for (int descIndex = 0; descIndex < description.size(); descIndex++) {
            FormattedCharSequence descSeq = description.get(descIndex);
            AtomicReference<String> descText = new AtomicReference<>("");
            descSeq.accept((var1, var2, var3) -> {
                descText.set(descText.get() + (char) var3);
                return true;
            });
//            System.out.println("Desc Text: " + descText.get());
            FormattedCharSequence newDescSeq = FormattedCharSequence.forward(" HMMMMM", Style.EMPTY);
            tempDesc.add(description.get(descIndex));
            replaceDesc.add(descIndex, FormattedCharSequence.composite(descSeq, newDescSeq));
        }
        description = replaceDesc;
        // replace width
//        int i = getMaxProgressWidth();
//        int j = 29 + Minecraft.getInstance().font.width(this.title) + i;
    }

    @Inject(method="drawHover", at = @At(value = "RETURN"))
    public void endDrawHover(GuiGraphics guiGraphics, int x, int y, float fade, int width, int height, CallbackInfo ci) {
        title = tempTitle;
        for (int descIndex = 0; descIndex < tempDesc.size(); descIndex++) {
            description.set(descIndex, tempDesc.get(descIndex));
        }
        tempDesc.clear();
    }
}
