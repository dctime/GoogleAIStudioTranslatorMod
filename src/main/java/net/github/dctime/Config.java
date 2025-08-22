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

/**
 * Example config class for the translation mod.
 * 使用 NeoForge 的設定 API，集中管理模組設定。
 */
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // === Basic keys for Google AI Studio ===
    public static final ModConfigSpec.ConfigValue<String> API_KEY = BUILDER
            .comment("The API KEY from Google AI Studio [Google AI Studio 的 API KEY]\n" +
                    "(Apply one for free at https://ai.google.dev/)")
            .define("Google AI Studio API KEY", "");

    public static final ModConfigSpec.ConfigValue<String> MODEL_NAME = BUILDER
            .comment("The model name to use for translation [使用的 Google AI Studio 模型]\n" +
                    "(免費可用 Gemma，如：gemma-3-27b-it)")
            .define("Google AI Studio Model Name", "gemma-3-27b-it");

    // === Prompt tailored for Minecraft/mod tone ===
    public static final ModConfigSpec.ConfigValue<String> PROMPT = BUILDER
            .comment("The prompt to use for translation [翻譯時使用的提示語]\n" +
                    "預設口吻更貼近 Minecraft／模組（短句、提示風），保留各種佔位符與格式，不腦補。")
            .define("Prompt",
                    "只回繁體中文的翻譯，不要多字、不要解釋。\n" +
                            "語氣貼近《Minecraft》與常見模組敘述（短句、指令／提示感）。\n" +
                            "遵守：\n" +
                            "1) 保留原字串所有佔位符與格式：{player}、{item}、%s、%d、{0}、\\n、\\t、\\r、\\uXXXX、JSON、NBT、<...>、[...]、(...)\n" +
                            "2) 不翻譯：模組/方塊/物品 ID、路徑、Key、Tag、檔名、指令（/give 等）、進度代碼、顏色/格式碼（§ 或 &）。\n" +
                            "3) 優先使用《Minecraft》繁中（zh_tw）官方譯名；無官方譯名則用台灣社群慣用語。\n" +
                            "4) 名詞遵循遊戲慣用：block=方塊、slab=半磚、stairs=樓梯、planks=木材、log=原木、ore=礦石、ingot=錠、nugget=金粒、dye=染料、bucket=桶、stack=堆疊、craft=合成、smelt=熔煉、furnace=熔爐、blast furnace=高爐、smoker=煙燻爐、enchant=附魔、anvil=鐵砧、loot=戰利品、biome=生態域。\n" +
                            "5) 字面直譯、保持簡潔；不要加背景、不要腦補。\n" +
                            "6) 標點與大小寫盡量貼近原風格（專有名詞維持大小寫）。\n" +
                            "待翻譯：\n" +
                            "「{TEXT}」"
            );

    // === Feature toggles ===
    public static final ModConfigSpec.BooleanValue ENABLE_TOOLTIP_TRANSLATION = BUILDER
            .comment("Whether to enable tooltip translation [滑鼠指向物品時是否啟用翻譯] (預設 true)")
            .define("Enable Tooltip Translation", true);

    public static final ModConfigSpec.BooleanValue ENABLE_FTB_QUEST_TRANSLATION = BUILDER
            .comment("Whether to enable FTB Quests translation [是否啟用 FTB Quests 翻譯] (預設 true)")
            .define("Enable FTB Quests Translation", true);

    // // Example of item list config (kept as reference):
    // public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
    //         .comment("A list of items to log on common setup.")
    //         .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
