package com.fentanyl;

import com.fentanyl.items.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fentanyl.items.ModItems.fentClump;

public class Fentanyl implements ModInitializer {
	public static final String MOD_ID = "fentanyl";

	private static final Identifier DIRT_LOOT_TABLE_ID = Blocks.DIRT.getLootTableId();

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
				LootPool poolBuilder = LootPool.builder().with(ItemEntry.builder(fentClump).weight(1)).with(ItemEntry.builder(Items.AIR).weight(3)).build();
				tableBuilder.pool(poolBuilder);
			}
		});
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}