package net.github.dctime;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

//    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
//            .comment("Whether to log the dirt block on common setup")
//            .define("logDirtBlock", true);
//
//    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
//            .comment("A magic number")
//            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> API_KEY = BUILDER
            .comment("The API KEY from Google AI Studio [Google AI Studio 的 API KEY] (可以在 https://ai.google.dev/ 申請一個免費的API KEY)")
            .define("Google AI Studio API KEY", "");

    public static final ModConfigSpec.ConfigValue<String> MODEL_NAME = BUILDER
            .comment("The model name to use for translation [使用的Google AI Studio模型] (免費仔可以用Gemmma像是 gemma-3-27b-it)")
            .define("Google AI Studio Model Name", "gemma-3-27b-it");

    public static final ModConfigSpec.ConfigValue<String> PROMPT = BUILDER
            .comment("The prompt to use for translation [翻譯時使用的提示語] (預設為 '請把這個翻成繁體中文 不要加前後文方便我直接取代:')")
            .define("Prompt", "請把這個翻成繁體中文 不要加前後文方便我直接取代:");

    public static final ModConfigSpec.BooleanValue ENABLE_TOOLTIP_TRANSLATION = BUILDER
        .comment("Whether to enable tooltip translation [滑鼠滑在物品上等是否啟用翻譯] (預設為 true)")
        .define("Enable Tooltip Translation", true);

    public static final ModConfigSpec.BooleanValue ENABLE_FTB_QUEST_TRANSLATION = BUILDER
            .comment("Whether to enable FTB Quests translation [是否啟用 FTB Quests 翻譯] (預設為 true)")
            .define("Enable FTB Quests Translation", true);

    // a list of strings that are treated as resource locations for items
//    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
