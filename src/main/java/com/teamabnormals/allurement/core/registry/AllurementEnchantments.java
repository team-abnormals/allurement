package com.teamabnormals.allurement.core.registry;

import com.teamabnormals.allurement.common.enchantment.*;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = Allurement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AllurementEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Allurement.MOD_ID);
	private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

	public static final RegistryObject<Enchantment> ALLEVIATING = ENCHANTMENTS.register("alleviating", () -> new AlleviatingEnchantment(Enchantment.Rarity.RARE, ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> LAUNCH = ENCHANTMENTS.register("launch", () -> new LaunchEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
	public static final RegistryObject<Enchantment> REELING = ENCHANTMENTS.register("reeling", () -> new ReelingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
	public static final RegistryObject<Enchantment> REFORMING = ENCHANTMENTS.register("reforming", () -> new ReformingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.values()));
	public static final RegistryObject<Enchantment> SHOCKWAVE = ENCHANTMENTS.register("shockwave", () -> new ShockwaveEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> VENGEANCE = ENCHANTMENTS.register("vengeance", () -> new VengeanceEnchantment(Enchantment.Rarity.VERY_RARE, ARMOR_SLOTS));
}