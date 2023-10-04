package com.teamabnormals.allurement.core;

import com.google.common.collect.Lists;
import com.teamabnormals.blueprint.core.annotations.ConfigKey;
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
		@ConfigKey("enchantable_horse_armor")
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

		@ConfigKey("enable_obedience")
		public final BooleanValue enableObedience;

		public final BooleanValue enableReeling;
		public final DoubleValue reelingHorizontalFactor;
		public final DoubleValue reelingVerticalFactor;

		public final BooleanValue enableReforming;
		public final IntValue reformingTickRate;

		public final BooleanValue enableShockwave;
		public final BooleanValue shockwaveTramplesFarmland;

		public final BooleanValue enableVengeance;
		public final DoubleValue vengeanceDamageFactor;

		public final BooleanValue enableSpreadOfAilments;

		public final BooleanValue enableAscensionCurse;
		public final BooleanValue enableFleetingCurse;

		public final BooleanValue nonSolidBlocksTransmitEnchantingPower;
		public final BooleanValue chiseledBookshelfEnchanting;
		public final IntValue booksNeededPerLevel;
		public final IntValue enchantedBooksNeededPerLevel;

		public final BooleanValue dropExperiencePercentage;
		public final DoubleValue experiencePercentage;

		public final BooleanValue removeLevelScaling;
		public final BooleanValue removeLevelScalingAfterCap;
		public final IntValue experiencePerLevel;

		public final BooleanValue adjustEnderDragonExperienceDrop;
		public final IntValue enderDragonExperienceDrop;
		public final IntValue respawnedEnderDragonExperienceDrop;

		public final BooleanValue cheapItemRenaming;
		public final BooleanValue removeTooExpensive;
		public final BooleanValue capAnvilCosts;
		public final IntValue anvilCostCap;
		public final BooleanValue anvilIngotRepairing;
		public final IntValue ingotRepairChance;

		Common(ForgeConfigSpec.Builder builder) {

			builder.push("enchantments");
			builder.push("alleviating");
			enableAlleviating = builder.comment("Armor enchantment that heals the user when collecting experience").define("Enable Alleviating", true);
			alleviatingHealingFactor = builder.comment("How much the experience value is multiplied by into health").defineInRange("Healing factor", 0.25D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("launch");
			enableLaunch = builder.comment("Weapon enchantment that launches enemies upwards rather than away").define("Enable Launch", true);
			launchVerticalFactor = builder.comment("How much the target is affected on the vertical axis").defineInRange("Vertical factor", 0.35D, 0, Double.MAX_VALUE);
			builder.pop();

			builder.push("obedience");
			enableObedience = builder.comment("Horse armor enchantment tha prevents the horse from roaming around").define("Enable Obedience", true);
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

			builder.push("spread_of_ailments");
			enableSpreadOfAilments = builder.comment("Crossbow enchantment that applies the user's active effects to their arrows").define("Enable Spread of Ailments", true);
			builder.pop();
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
			builder.push("enchanting");
			nonSolidBlocksTransmitEnchantingPower = builder.comment("Allow enchanting power from bookshelves to transmit through any non-solid block, not just replaceable ones").define("Non-solid blocks transmit enchanting power", true);
			chiseledBookshelfEnchanting = builder.comment("Allow Chiseled Bookshelves to be usable to boost enchanting table power").define("Chiseled Bookshelf enchanting", true);
			booksNeededPerLevel = builder.comment("How many regular books are needed to increase the enchantment power by 1").defineInRange("Books needed", 3, 1, 6);
			enchantedBooksNeededPerLevel = builder.comment("How many enchanted are needed to increase the enchantment power by 1").defineInRange("Enchanted Books needed", 2, 1, 6);
			builder.pop();

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

			builder.push("experience");
			builder.push("dropped_xp");
			dropExperiencePercentage = builder.comment("If the player drops a flat percentage of their experience rather than capping at level 7").define("Drop experience percentage", false);
			experiencePercentage = builder.comment("What percentage of the player's total experience is dropped").defineInRange("Experience percentage", 0.75F, 0, 1.0D);
			builder.pop();
			builder.push("level_scaling");
			removeLevelScaling = builder.comment("Remove the amount of experience per level increasing (experimental)").define("Remove level scaling", false);
			experiencePerLevel = builder.comment("The amount of experience per level, if level scaling is removed (experimental)").defineInRange("Experience per level", 50, 0, Integer.MAX_VALUE);
			removeLevelScalingAfterCap = builder.comment("If the level scaling should only be modified after it reaches the 'Experience per level' value (experimental)").define("Remove level scaling after cap", true);
			builder.pop();
			builder.push("ender_dragon");
			adjustEnderDragonExperienceDrop = builder.comment("If Ender Dragon experience dropping should be adjusted (to account for level scaling)").define("Adjust Ender Dragon experience drop", false);
			enderDragonExperienceDrop = builder.comment("The amount of experience (in points) that should be dropped by the Ender Dragon").defineInRange("Ender Dragon experience drop", 3000, 0, Integer.MAX_VALUE);
			respawnedEnderDragonExperienceDrop = builder.comment("The amount of experience (in points) that should be dropped by a respawned Ender Dragon").defineInRange("Respawned Ender Dragon experience drop", 750, 0, Integer.MAX_VALUE);
			builder.pop();
			builder.push("anvil");
			cheapItemRenaming = builder.comment("If renaming items should always cost 1 experience").define("Cheap item renaming", true);
			removeTooExpensive = builder.comment("Remove the cap of 40 on anvil repairing prices").define("Remove too expensive", true);
			anvilIngotRepairing = builder.comment("If anvils can be repaired by right clicking with an iron ingot or using a dispenser").define("Anvil ingot repairing", true);
			ingotRepairChance = builder.comment("The amount of tries it should take on average to repair an anvil with an ingot (1 in x chance)").defineInRange("Ingot repair chance", 5, 0, Integer.MAX_VALUE);
			capAnvilCosts = builder.comment("If anvil transaction costs should be capped at a certain level").define("Cap anvil costs", false);
			anvilCostCap = builder.comment("The max amount of levels an anvil transaction should be able to cost").defineInRange("Anvil cost cap", 30, 1, Integer.MAX_VALUE);
			builder.pop();
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
