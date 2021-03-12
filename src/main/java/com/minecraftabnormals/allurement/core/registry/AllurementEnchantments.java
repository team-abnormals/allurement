package com.minecraftabnormals.allurement.core.registry;

import com.minecraftabnormals.allurement.common.enchantment.*;
import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Allurement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AllurementEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Allurement.MOD_ID);
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};

	public static final RegistryObject<Enchantment> ALLEVIATING = ENCHANTMENTS.register("alleviating", () -> new AlleviatingEnchantment(Enchantment.Rarity.RARE, ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> SHOCKWAVE = ENCHANTMENTS.register("shockwave", () -> new ShockwaveEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> REELING = ENCHANTMENTS.register("reeling", () -> new ReelingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
	public static final RegistryObject<Enchantment> REFORMING = ENCHANTMENTS.register("reforming", () -> new ReformingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.values()));
	public static final RegistryObject<Enchantment> ABSORBING = ENCHANTMENTS.register("absorbing", () -> new AbsorbingEnchantment(Enchantment.Rarity.VERY_RARE, ARMOR_SLOTS));
}
