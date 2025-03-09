package com.teamabnormals.allurement.core;

import com.google.common.collect.Lists;
import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AllurementConfig {

	public static class Common {
		@ConfigKey("enchantable_animal_armor")
		public final BooleanValue enchantableAnimalArmor;
		public final BooleanValue enchantedAnimalArmorGenerates;
		public final ConfigValue<List<? extends String>> unenchantedAnimalArmorLootTables;

		public final BooleanValue baneOfArthropodsBreaksCobwebsFaster;
		public final BooleanValue featherFallingPreventsTrampling;
		public final BooleanValue infinityRequiresArrows;
		public final BooleanValue riptideWorksInCauldrons;
		@ConfigKey("soul_speed_hurts_more")
		public final BooleanValue soulSpeedHurtsMore;

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

		Common(ModConfigSpec.Builder builder) {
			builder.push("tweaks");
			builder.push("enchanting");
			nonSolidBlocksTransmitEnchantingPower = builder.comment("Allow enchanting power from bookshelves to transmit through any non-solid block, not just replaceable ones").define("Non-solid blocks transmit enchanting power", true);
			chiseledBookshelfEnchanting = builder.comment("Allow Chiseled Bookshelves to be usable to boost enchanting table power").define("Chiseled Bookshelf enchanting", true);
			booksNeededPerLevel = builder.comment("How many regular books are needed to increase the enchantment power by 1").defineInRange("Books needed", 3, 1, 6);
			enchantedBooksNeededPerLevel = builder.comment("How many enchanted are needed to increase the enchantment power by 1").defineInRange("Enchanted Books needed", 2, 1, 6);
			builder.pop();

			builder.push("animal_armor");
			enchantableAnimalArmor = builder.comment("Allow animal armor to be enchanted").define("Enchantable animal armor", true);
			enchantedAnimalArmorGenerates = builder.comment("If animal armor can appear enchanted when found in loot tables").define("Generates in loot tables", true);
			unenchantedAnimalArmorLootTables = builder.comment("Which loot tables animal armor can't appear enchanted in").define("Unenchanted loot tables", Lists.newArrayList("minecraft:chests/village/village_weaponsmith", "minecraft:chests/stronghold_corridor", "minecraft:chests/nether_bridge"));
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

			builder.push("riptide");
			riptideWorksInCauldrons = builder.comment("Allow Riptide to function when in cauldrons").define("Riptide works in cauldrons", true);
			builder.pop();

			builder.push("soul_speed");
			soulSpeedHurtsMore = builder.comment("Instead of losing durability as you run, Soul Speed makes incoming damage increase when on Soul Speed blocks").define("Soul Speed change", true);
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

		Client(ModConfigSpec.Builder builder) {
			builder.push("tweaks");
			builder.push("infinity");
			infinityArrowTexture = builder.comment("Adds a special texture for arrows shot from infinity bows").define("Infinity arrow texture", true);
			infinityArrowGlint = builder.comment("Adds a glint on arrows shot from infinity bows").define("Infinity arrow glint", true);
			builder.pop();
			builder.pop();
		}
	}

	public static final ModConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	public static final ModConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();

		final Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}
}
