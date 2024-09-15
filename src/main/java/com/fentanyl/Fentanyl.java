package com.fentanyl;

import com.fentanyl.items.ModItems;
import com.fentanyl.items.fentThrowable;
import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fentanyl.items.ModItems.FentSeeds;
import static com.fentanyl.items.ModItems.fentClump;

public class Fentanyl implements ModInitializer {
	public static final String MOD_ID = "fentanyl";

	private static final Identifier DIRT_LOOT_TABLE_ID = Blocks.DIRT.getLootTableId();

	public static final EntityType<fentThrowable> fentProjectileEntityType = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "fentanyl"),
			FabricEntityTypeBuilder.<fentThrowable>create(SpawnGroup.MISC, fentThrowable::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
					.trackRangeBlocks(128).trackedUpdateRate(10)
					.build()
			);
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			// Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
			// We also check that the loot table ID is equal to the ID we want.
			if (DIRT_LOOT_TABLE_ID.equals(id)) {
				// We make the pool and add an item
				LootPool poolBuilder = LootPool.builder().with(ItemEntry.builder(FentSeeds).weight(1)).with(ItemEntry.builder(Items.AIR).weight(3)).build();
				tableBuilder.pool(poolBuilder);
			}
		});
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("godi love fent mmmmm");
	}
}