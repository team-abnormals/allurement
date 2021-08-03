package com.minecraftabnormals.allurement.core;


import com.google.common.collect.Lists;
import com.minecraftabnormals.abnormals_core.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementConfig {

	public static class Common {

		@ConfigKey("horse_armor_is_enchantable")
		public final ConfigValue<Boolean> enchantableHorseArmor;

		@ConfigKey("enchanted_horse_armor_generates")
		public final ConfigValue<Boolean> enchantedHorseArmorGenerates;

		@ConfigKey("unenchanted_horse_armor_loot_tables")
		public final ConfigValue<List<String>> unenchantedHorseArmorLootTables;

		@ConfigKey("riptide_works_in_cauldrons")
		public final ConfigValue<Boolean> riptideWorksInCauldrons;


		@ConfigKey("soul_speed_increases_damage")
		public final ConfigValue<Boolean> soulSpeedHurtsMore;

		@ConfigKey("soul_speed_damage_factor")
		public final ConfigValue<Float> soulSpeedDamageFactor;


		@ConfigKey("infinity_requires_arrows")
		public final ConfigValue<Boolean> infinityRequiresArrows;


		@ConfigKey("protection_disabled")
		public final ConfigValue<Boolean> disableProtection;


		@ConfigKey("alleviating_enabled")
		public final ConfigValue<Boolean> enableAlleviating;

		@ConfigKey("alleviating_healing_factor")
		public final ConfigValue<Float> alleviatingHealingFactor;


		@ConfigKey("launch_enabled")
		public final ConfigValue<Boolean> enableLaunch;

		@ConfigKey("launch_vertical_factor")
		public final ConfigValue<Double> launchVerticalFactor;


		@ConfigKey("reeling_enabled")
		public final ConfigValue<Boolean> enableReeling;

		@ConfigKey("reeling_horizontal_factor")
		public final ConfigValue<Double> reelingHorizontalFactor;

		@ConfigKey("reeling_vertical_factor")
		public final ConfigValue<Double> reelingVerticalFactor;


		@ConfigKey("reforming_enabled")
		public final ConfigValue<Boolean> enableReforming;

		@ConfigKey("reforming_tick_rate")
		public final ConfigValue<Integer> reformingTickRate;


		@ConfigKey("shockwave_enabled")
		public final ConfigValue<Boolean> enableShockwave;


		@ConfigKey("vengeance_enabled")
		public final ConfigValue<Boolean> enableVengeance;

		@ConfigKey("vengeance_damage_factor")
		public final ConfigValue<Float> vengeanceDamageFactor;


		@ConfigKey("level_scaling_removed")
		public final ConfigValue<Boolean> removeLevelScaling;

		@ConfigKey("experience_per_level")
		public final ConfigValue<Integer> experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");

			builder.push("alleviating");
			enableAlleviating = builder.comment("Armor enchantment that heals the user when collecting experience").define("Enable Alleviating", true);
			alleviatingHealingFactor = builder.comment("How much the experience value is multiplied by into health").define("Healing factor", 0.25F);
			builder.pop();

			builder.push("missile");
			enableLaunch = builder.comment("Weapon enchantment that launches enemies upwards rather than away").define("Enable Launch", true);
			launchVerticalFactor = builder.comment("How much the target is affected on the vertical axis").define("Vertical factor", 0.35D);
			builder.pop();

			builder.push("reeling");
			enableReeling = builder.comment("Crossbow enchantment that pulls targets towards the user").define("Enable Reeling", true);
			reelingHorizontalFactor = builder.comment("How much the target is affected on the horizontal axis").define("Horizontal factor", 0.5D);
			reelingVerticalFactor = builder.comment("How much the target is affected on the vertical axis").define("Vertical factor", 0.25D);
			builder.pop();

			builder.push("reforming");
			enableReforming = builder.comment("Gear enchantment that very slowly repairs items over time").define("Enable Reforming", true);
			reformingTickRate = builder.comment("How many ticks it takes a reforming item to repair").define("Reforming tick rate", 600);
			builder.pop();

			builder.push("shockwave");
			enableShockwave = builder.comment("Boots enchantment that creates a shockwave when taking fall damage").define("Enable Shockwave", true);
			builder.pop();

			builder.push("vengeance");
			enableVengeance = builder.comment("Armor enchantment that stores incoming damage and applies it to user's next attack").define("Enable Vengeance", true);
			vengeanceDamageFactor = builder.comment("How much the damage taken with vengeance is multiplied for attacks").define("Damage factor", 0.025F);
			builder.pop();

			builder.pop();
			builder.push("tweaks");

			builder.push("horse_armor");
			enchantableHorseArmor = builder.comment("Allow horse armor to be enchanted").define("Enchantable horse armor", true);
			enchantedHorseArmorGenerates = builder.comment("If horse armor can appear enchanted when found in loot tables").define("Generates in loot tables", true);
			unenchantedHorseArmorLootTables = builder.comment("Which loot tables horse armor can't appear enchanted in").define("Unenchanted loot tables", Lists.newArrayList("minecraft:chests/village/village_weaponsmith", "minecraft:chests/stronghold_corridor", "minecraft:chests/nether_bridge"));
			builder.pop();

			builder.push("riptide");
			riptideWorksInCauldrons = builder.comment("Allow Riptide to function when in cauldrons").define("Riptide works in cauldrons", true);
			builder.pop();

			builder.push("soul_speed");
			soulSpeedHurtsMore = builder.comment("Instead of losing durability as you run, Soul Speed makes incoming damage increase when on Soul Speed blocks").define("Soul Speed change", true);
			soulSpeedDamageFactor = builder.comment("How much damage is multiplied when hurt on Soul Speed blocks").define("Damage factor", 1.5F);
			builder.pop();

			builder.push("infinity");
			infinityRequiresArrows = builder.comment("If Infinity requires an arrow in the player's inventory in order to shoot").define("Infinity requires arrows", false);
			builder.pop();

			builder.push("protection");
			disableProtection = builder.comment("Remove the base Protection enchantment, requiring players to choose between the other types").define("Disable Protection", false);
			builder.pop();

			builder.push("level_scaling");
			removeLevelScaling = builder.comment("Remove the amount of experience per level increasing (experimental)").define("Remove level scaling", false);
			experiencePerLevel = builder.comment("The amount of experience per level, if level scaling is removed (experimental)").define("Experience per level", 50);
			builder.pop();

			builder.pop();
		}
	}

	public static class Client {

		@ConfigKey("infinity_arrow_texture_enabled")
		public final ConfigValue<Boolean> infinityArrowTexture;

		@ConfigKey("infinity_arrow_glint_enabled")
		public final ConfigValue<Boolean> infinityArrowGlint;

		Client(ForgeConfigSpec.Builder builder) {
			builder.push("tweaks");
			builder.push("infinity");
			infinityArrowTexture = builder.comment("Adds a special texture for arrows shot from infinity bows").define("Infinity arrow texture", true);
			infinityArrowGlint = builder.comment("Adds a glint on arrows shot from infinity bows").define("Infinity arrow glint", true);
			builder.pop();
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();

		final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}
}
