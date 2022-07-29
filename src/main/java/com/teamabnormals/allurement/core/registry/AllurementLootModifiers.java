package com.teamabnormals.allurement.core.registry;

import com.mojang.serialization.Codec;
import com.teamabnormals.allurement.common.loot.HorseArmorLootModifier;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Allurement.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AllurementLootModifiers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Allurement.MOD_ID);

	public static final RegistryObject<Codec<HorseArmorLootModifier>> ENCHANTED_HORSE_ARMOR = GLOBAL_LOOT_MODIFIERS.register("enchanted_horse_armor", HorseArmorLootModifier.CODEC);
}
