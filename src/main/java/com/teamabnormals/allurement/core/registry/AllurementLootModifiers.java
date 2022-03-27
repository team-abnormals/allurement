package com.teamabnormals.allurement.core.registry;

import com.teamabnormals.allurement.common.loot.HorseArmorLootModifier.HorseArmorSerializer;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Allurement.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AllurementLootModifiers {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(Keys.LOOT_MODIFIER_SERIALIZERS, Allurement.MOD_ID);

	public static final RegistryObject<HorseArmorSerializer> ENCHANTED_HORSE_ARMOR = LOOT_MODIFIERS.register("enchanted_horse_armor", HorseArmorSerializer::new);
}
