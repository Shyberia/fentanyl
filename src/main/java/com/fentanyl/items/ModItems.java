package com.fentanyl.items;

import com.fentanyl.Fentanyl;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    static final FoodComponent Fent_Food = new FoodComponent.Builder()
            .alwaysEdible()
            .snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10*20, 1), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 7*20, 0), 0.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 5*20, 0), 0.5f)
            .build();

    public static final Item fentClump = register(
            new Item(new Item.Settings()),
            "fent_clump"
    );
    public static final Item Fent = register(
            new fentanyl(new Item.Settings().food(Fent_Food)),
            "fentanyl"
    );


    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(Fentanyl.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }
    public static void initialize(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup)-> itemGroup.add(ModItems.fentClump));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup)->itemGroup.add(ModItems.Fent));
    }
}
