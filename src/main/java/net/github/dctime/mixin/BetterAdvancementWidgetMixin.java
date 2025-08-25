package net.github.dctime.mixin;

import betteradvancements.common.gui.BetterAdvancementTab;
import betteradvancements.common.gui.BetterAdvancementWidget;
import betteradvancements.common.gui.BetterAdvancementsScreen;
import betteradvancements.common.util.CriterionGrid;
import net.github.dctime.libs.Translator;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BetterAdvancementWidget.class)
public abstract class BetterAdvancementWidgetMixin {
    @Shadow
    @Final
    @Mutable
    private String title;

    @Shadow
    @Final
    private AdvancementNode advancementNode;

    @Shadow
    @Final
    private BetterAdvancementTab betterAdvancementTabGui;

    @Shadow
    private CriterionGrid criterionGrid;

    @Shadow
    private int width;

    @Shadow
    private AdvancementProgress advancementProgress;

    @Shadow
    private List<FormattedCharSequence> description;

    @Shadow
    @Final
    private DisplayInfo displayInfo;

    private List<FormattedCharSequence> tempDescription;
    private String tempTitle;

    @Shadow
    protected abstract List<FormattedText> findOptimalLines(Component line, int width);

    @Inject(method = "drawHover", at = @At(value = "FIELD", target = "width", ordinal = 0))
    public void onDrawHover(GuiGraphics guiGraphics, int scrollX, int scrollY, float fade, int left, int top, CallbackInfo ci) {
        // end of line 276
        System.out.println("Will this work");
        tempTitle = this.title;
        tempDescription = description.stream().toList();

        this.title = this.title + " [MODDED]";

        // resize UI
        int k = 0;
        if (this.advancementNode.advancement().requirements().size() > 1) {
            int strLengthRequirementCount = String.valueOf(this.advancementNode.advancement().requirements().size()).length();
            k = Minecraft.getInstance().font.width("  ") + Minecraft.getInstance().font.width("0") * strLengthRequirementCount * 2 + Minecraft.getInstance().font.width("/");
        }

        int titleWidth = 29 + Minecraft.getInstance().font.width(this.title) + k;
        BetterAdvancementsScreen screen = this.betterAdvancementTabGui.getScreen();
        this.criterionGrid = CriterionGrid.findOptimalCriterionGrid(this.advancementNode.holder(), this.advancementNode.advancement(), this.advancementProgress, screen.width / 2, Minecraft.getInstance().font);
        int maxWidth;
        if (CriterionGrid.requiresShift && !Screen.hasShiftDown()) {
            maxWidth = titleWidth;
        } else {
            maxWidth = Math.max(titleWidth, this.criterionGrid.width);
        }

        this.description = Language.getInstance().getVisualOrder(this.findOptimalLines(ComponentUtils.mergeStyles(this.displayInfo.getDescription().copy().append(Component.literal("\n[MODDDD]").withStyle(Translator.translatedStyle)), Style.EMPTY.withColor(this.displayInfo.getType().getChatColor())), maxWidth));

        for(FormattedCharSequence line : this.description) {
            maxWidth = Math.max(maxWidth, Minecraft.getInstance().font.width(line));
        }

        this.width = maxWidth + 8;
    }

    @Inject(method = "drawHover", at = @At(value = "RETURN"))
    public void endDrawHover(GuiGraphics guiGraphics, int scrollX, int scrollY, float fade, int left, int top, CallbackInfo ci) {
        this.title = tempTitle;
        this.description = tempDescription.stream().toList();
    }
}
