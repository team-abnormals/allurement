package com.teamabnormals.allurement.core.data.server.modifiers;


import com.teamabnormals.allurement.common.loot.AnimalArmorLootModifier;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class AllurementGlobalLootModifierProvider extends GlobalLootModifierProvider {

	public AllurementGlobalLootModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, Allurement.MOD_ID);
	}

	@Override
	public void start() {
		this.add("enchanted_animal_armor", new AnimalArmorLootModifier(new LootItemCondition[0]));
	}
}