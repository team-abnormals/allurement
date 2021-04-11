package com.minecraftabnormals.allurement.core.data;

import com.minecraftabnormals.allurement.common.loot.HorseArmorLootModifier;
import com.minecraftabnormals.allurement.core.Allurement;
import com.minecraftabnormals.allurement.core.registry.AllurementLootModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;


public class LootModifierGenerator extends GlobalLootModifierProvider {

	public LootModifierGenerator(DataGenerator generator) {
		super(generator, Allurement.MOD_ID);
	}

	@Override
	public void start() {
		this.add("enchanted_horse_armor", AllurementLootModifiers.ENCHANTED_HORSE_ARMOR.get(), new HorseArmorLootModifier(new ILootCondition[0]));
	}
}