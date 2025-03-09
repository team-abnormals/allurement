package com.teamabnormals.allurement.core.registry;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.allurement.common.loot.AnimalArmorLootModifier;
import com.teamabnormals.allurement.core.Allurement;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AllurementLootModifiers {
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Allurement.MOD_ID);

	public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, ?> ENCHANTED_HORSE_ARMOR = GLOBAL_LOOT_MODIFIERS.register("enchanted_horse_armor", () -> AnimalArmorLootModifier.CODEC);
}
