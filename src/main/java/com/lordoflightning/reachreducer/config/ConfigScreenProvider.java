package com.lordoflightning.reachreducer.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.TranslatableText;

public class ConfigScreenProvider implements ModMenuApi {

    public static ConfigBuilder builder() {

        ConfigBuilder configBuilder = ConfigBuilder.create()
                .setTitle(new TranslatableText("reach-reducer.modname"))
                .setEditable(true)
                .setSavingRunnable(() -> ModConfig.writeJson());
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        ConfigCategory general = configBuilder.getOrCreateCategory(new TranslatableText("reach-reducer.config.general"));

        general.addEntry(entryBuilder
                .startFloatField(new TranslatableText("reach-reducer.config.attack_reach"), ModConfig.INSTANCE.getAttackReachDistance())
                .setDefaultValue(3.0f)
                .setTooltip(
                        new TranslatableText("reach-reducer.config.attack_reach.description.line1"),
                        new TranslatableText("reach-reducer.config.attack_reach.description.line2"),
                        new TranslatableText("reach-reducer.config.attack_reach.description.line3")
                )
                .setSaveConsumer(value -> ModConfig.INSTANCE.setAttackReachDistance(value))
                .build()
        );

        general.addEntry(entryBuilder
                .startFloatField(new TranslatableText("reach-reducer.config.block_reach"), ModConfig.INSTANCE.getBlockReachDistance())
                .setDefaultValue(4.5f)
                .setTooltip(
                        new TranslatableText("reach-reducer.config.block_reach.description.line1"),
                        new TranslatableText("reach-reducer.config.block_reach.description.line2"),
                        new TranslatableText("reach-reducer.config.block_reach.description.line3")
                )
                .setSaveConsumer(value -> ModConfig.INSTANCE.setBlockReachDistance(value))
                .build()
        );

        general.addEntry(entryBuilder
                .startFloatField(new TranslatableText("reach-reducer.config.creative_attack_reach"),
                        ModConfig.INSTANCE.getCreativeAttackReachDistance())
                .setDefaultValue(6.0f)
                .setTooltip(
                        new TranslatableText("reach-reducer.config.creative_attack_reach.description.line1"),
                        new TranslatableText("reach-reducer.config.creative_attack_reach.description.line2")
                )
                .setSaveConsumer(value -> ModConfig.INSTANCE.setCreativeAttackReachDistance(value))
                .build()
        );

        general.addEntry(entryBuilder
                .startFloatField(new TranslatableText("reach-reducer.config.creative_block_reach"),
                        ModConfig.INSTANCE.getCreativeBlockReachDistance())
                .setDefaultValue(5.0f)
                .setTooltip(
                        new TranslatableText("reach-reducer.config.creative_block_reach.description.line1"),
                        new TranslatableText("reach-reducer.config.creative_block_reach.description.line2")
                )
                .setSaveConsumer(value -> ModConfig.INSTANCE.setCreativeBlockReachDistance(value))
                .build()
        );

        return configBuilder;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            return builder().setParentScreen(parent).build();
        };
    }

}
