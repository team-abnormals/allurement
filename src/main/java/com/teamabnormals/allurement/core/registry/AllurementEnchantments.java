package com.teamabnormals.allurement.core.registry;

import com.teamabnormals.allurement.common.enchantment.*;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Allurement.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AllurementEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Allurement.MOD_ID);

	public static final EnchantmentCategory HORSE_ARMOR = EnchantmentCategory.create("HORSE_ARMOR", item -> item instanceof HorseArmorItem);

	private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

	public static final RegistryObject<Enchantment> ALLEVIATING = ENCHANTMENTS.register("alleviating", () -> new AlleviatingEnchantment(Enchantment.Rarity.RARE, ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> LAUNCH = ENCHANTMENTS.register("launch", () -> new LaunchEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
	public static final RegistryObject<Enchantment> OBEDIENCE = ENCHANTMENTS.register("obedience", () -> new ObedienceEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.CHEST));
	public static final RegistryObject<Enchantment> REELING = ENCHANTMENTS.register("reeling", () -> new ReelingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
	public static final RegistryObject<Enchantment> REFORMING = ENCHANTMENTS.register("reforming", () -> new ReformingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.values()));
	public static final RegistryObject<Enchantment> SHOCKWAVE = ENCHANTMENTS.register("shockwave", () -> new ShockwaveEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> SPREAD_OF_AILMENTS = ENCHANTMENTS.register("spread_of_ailments", () -> new SpreadOfAilmentsEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
	public static final RegistryObject<Enchantment> VENGEANCE = ENCHANTMENTS.register("vengeance", () -> new VengeanceEnchantment(Enchantment.Rarity.VERY_RARE, ARMOR_SLOTS));

	public static final RegistryObject<Enchantment> ASCENSION_CURSE = ENCHANTMENTS.register("ascension_curse", () -> new AscensionCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));
	public static final RegistryObject<Enchantment> FLEETING_CURSE = ENCHANTMENTS.register("fleeting_curse", () -> new FleetingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));
}