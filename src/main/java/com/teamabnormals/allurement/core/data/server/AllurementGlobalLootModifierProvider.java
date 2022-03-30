package com.teamabnormals.allurement.core.data.server;

import com.teamabnormals.allurement.common.loot.HorseArmorLootModifier;
import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.registry.AllurementLootModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;


public class AllurementGlobalLootModifierProvider extends GlobalLootModifierProvider {

	public AllurementGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, Allurement.MOD_ID);
	}

	@Override
	public void start() {
		this.add("enchanted_horse_armor", AllurementLootModifiers.ENCHANTED_HORSE_ARMOR.get(), new HorseArmorLootModifier(new LootItemCondition[0]));
	}
}