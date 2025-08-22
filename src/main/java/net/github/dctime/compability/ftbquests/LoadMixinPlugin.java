package net.github.dctime.compability.ftbquests;

import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class LoadMixinPlugin implements IMixinConfigPlugin {
    private static boolean otherLoaded = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadMixinPlugin.class);

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (LoadingModList.get().getModFileById("ftbquests") != null && !otherLoaded) {
            otherLoaded = true;
            LOGGER.info("FTB Quests is loaded, applying mixins for FTB Quests compatibility.");
        }
        if (mixinClassName.endsWith("ViewQuestPanelMixin")) return otherLoaded;
        if (mixinClassName.endsWith("TextFieldMixin")) return otherLoaded;
        if (mixinClassName.endsWith("QuestPanelMixin")) return otherLoaded;
        if (mixinClassName.endsWith("QuestDescriptionFieldMixin")) return otherLoaded;
        if (mixinClassName.endsWith("PinViewQuestButtonMixin")) return otherLoaded;
        if (mixinClassName.endsWith("CloseViewQuestButtonMixin")) return otherLoaded;
        if (mixinClassName.endsWith("BaseScreenMixin")) return otherLoaded;
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
