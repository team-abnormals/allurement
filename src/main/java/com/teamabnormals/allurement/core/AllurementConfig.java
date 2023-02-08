package com.teamabnormals.allurement.core;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementConfig {

	public static class Common {
		public final BooleanValue enchantableHorseArmor;
		public final BooleanValue enchantedHorseArmorGenerates;
		public final ConfigValue<List<? extends String>> unenchantedHorseArmorLootTables;

		public final BooleanValue baneOfArthropodsBreaksCobwebsFaster;

		public final BooleanValue featherFallingPreventsTrampling;

		public final BooleanValue infinityRequiresArrows;

		public final BooleanValue disableProtection;

		public final BooleanValue riptideWorksInCauldrons;

		public final BooleanValue soulSpeedHurtsMore;
		public final DoubleValue soulSpeedDamageFactor;

		public final BooleanValue enableAlleviating;
		public final DoubleValue alleviatingHealingFactor;

		public final BooleanValue enableLaunch;
		public final DoubleValue launchVerticalFactor;

		public final BooleanValue enableReeling;
		public final DoubleValue reelingHorizontalFactor;
		public final DoubleValue reelingVerticalFactor;

		public final BooleanValue enableReforming;
		public final IntValue reformingTickRate;

		public final BooleanValue enableShockwave;
		public final BooleanValue shockwaveTramplesFarmland;

		public final BooleanValue enableVengeance;
		public final DoubleValue vengeanceDamageFactor;

		public final BooleanValue enableAscensionCurse;
		public final BooleanValue enableFleetingCurse;

		public final BooleanValue removeLevelScaling;
		public final IntValue experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");

			builder.push("alleviating");
			enableAlleviating = builder.comment("Armor enchantment that heals the user when collecting experience").define("Enable Alleviating", true);
			alleviatingHealingFactor = builder.comment("How much the experience value is multiplied by into health").defineInRange("Healing factor", 0.25D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("missile");
			enableLaunch = builder.comment("Weapon enchantment that launches enemies upwards rather than away").define("Enable Launch", true);
			launchVerticalFactor = builder.comment("How much the target is affected on the vertical axis").defineInRange("Vertical factor", 0.35D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("reeling");
			enableReeling = builder.comment("Crossbow enchantment that pulls targets towards the user").define("Enable Reeling", true);
			reelingHorizontalFactor = builder.comment("How much the target is affected on the horizontal axis").defineInRange("Horizontal factor", 0.5D, 0, Double.MAX_VALUE);
			reelingVerticalFactor = builder.comment("How much the target is affected on the vertical axis").defineInRange("Vertical factor", 0.25D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("reforming");
			enableReforming = builder.comment("Gear enchantment that very slowly repairs items over time").define("Enable Reforming", true);
			reformingTickRate = builder.comment("How many ticks it takes a reforming item to repair").defineInRange("Reforming tick rate", 600, 0, Integer.MAX_VALUE);
			builder.pop();

			builder.push("shockwave");
			enableShockwave = builder.comment("Boots enchantment that creates a shockwave when taking fall damage").define("Enable Shockwave", true);
			shockwaveTramplesFarmland = builder.comment("If Shockwave tramples farmland within the wave radius").define("Shockwave tramples farmland", true);
			builder.pop();

			builder.push("vengeance");
			enableVengeance = builder.comment("Armor enchantment that stores incoming damage and applies it to user's next attack").define("Enable Vengeance", true);
			vengeanceDamageFactor = builder.comment("How much the damage taken with vengeance is multiplied for attacks").defineInRange("Damage factor", 0.025D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("curses");
			builder.push("ascension_curse");
			enableAscensionCurse = builder.comment("Curse that causes the cursed item to float upwards when dropped").define("Enable Curse of Ascension", true);
			builder.pop();

			builder.push("fleeting_curse");
			enableFleetingCurse = builder.comment("Curse that causes nearby entities to repel the cursed item").define("Enable Curse of Fleeting", true);
			builder.pop();
			builder.pop();

			builder.push("tweaks");
			builder.push("horse_armor");
			enchantableHorseArmor = builder.comment("Allow horse armor to be enchanted").define("Enchantable horse armor", true);
			enchantedHorseArmorGenerates = builder.comment("If horse armor can appear enchanted when found in loot tables").define("Generates in loot tables", true);
			unenchantedHorseArmorLootTables = builder.comment("Which loot tables horse armor can't appear enchanted in").define("Unenchanted loot tables", Lists.newArrayList("minecraft:chests/village/village_weaponsmith", "minecraft:chests/stronghold_corridor", "minecraft:chests/nether_bridge"));
			builder.pop();

			builder.push("bane_of_arthropods");
			baneOfArthropodsBreaksCobwebsFaster = builder.comment("If Bane of Arthropods increases the mining speed of Cobwebs").define("Bane of Arthropods mines cobwebs faster", true);
			builder.pop();

			builder.push("feather_falling");
			featherFallingPreventsTrampling = builder.comment("If having Feather Falling prevents farmland from being trampled").define("Feather Falling prevents trampling", true);
			builder.pop();

			builder.push("infinity");
			infinityRequiresArrows = builder.comment("If Infinity requires an arrow in the player's inventory in order to shoot").define("Infinity requires arrows", false);
			builder.pop();

			builder.push("protection");
			disableProtection = builder.comment("Remove the base Protection enchantment, requiring players to choose between the other types").define("Disable Protection", false);
			builder.pop();

			builder.push("riptide");
			riptideWorksInCauldrons = builder.comment("Allow Riptide to function when in cauldrons").define("Riptide works in cauldrons", true);
			builder.pop();

			builder.push("soul_speed");
			soulSpeedHurtsMore = builder.comment("Instead of losing durability as you run, Soul Speed makes incoming damage increase when on Soul Speed blocks").define("Soul Speed change", true);
			soulSpeedDamageFactor = builder.comment("How much damage is multiplied when hurt on Soul Speed blocks").defineInRange("Damage factor", 1.5D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("level_scaling");
			removeLevelScaling = builder.comment("Remove the amount of experience per level increasing (experimental)").define("Remove level scaling", false);
			experiencePerLevel = builder.comment("The amount of experience per level, if level scaling is removed (experimental)").defineInRange("Experience per level", 50, 0, Integer.MAX_VALUE);
			builder.pop();

			builder.pop();
		}
	}

	public static class Client {
		public final BooleanValue infinityArrowTexture;
		public final BooleanValue infinityArrowGlint;

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
