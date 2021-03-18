package com.minecraftabnormals.allurement.core;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementConfig {

	public static class Common {
		public final ConfigValue<Boolean> enchantableHorseArmor;

		public final ConfigValue<Boolean> riptideWorksInCauldrons;

		public final ConfigValue<Boolean> soulSpeedHurtsMore;
		public final ConfigValue<Float> soulSpeedDamageFactor;

		public final ConfigValue<Boolean> infinityRequiresArrows;

		public final ConfigValue<Boolean> disableProtection;

		public final ConfigValue<Boolean> enableAlleviating;

		public final ConfigValue<Boolean> enableReeling;
		public final ConfigValue<Double> reelingHorizontalFactor;
		public final ConfigValue<Double> reelingVerticalFactor;

		public final ConfigValue<Boolean> enableReforming;
		public final ConfigValue<Integer> reformingTickRate;

		public final ConfigValue<Boolean> enableShockwave;

		public final ConfigValue<Boolean> enableVengeance;

		public final ConfigValue<Boolean> removeLevelScaling;
		public final ConfigValue<Integer> experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");

			builder.push("alleviating");
			enableAlleviating = builder.comment("Armor enchantment that heals the user when collecting experience").define("Enable Alleviating", true);
			builder.pop();

			builder.push("reeling");
			enableReeling = builder.comment("Crossbow enchantment that pulls targets towards the user").define("Reeling", true);
			reelingHorizontalFactor = builder.comment("How much the entity is affected on the horizontal axis").define("Horizontal factor", 0.5D);
			reelingVerticalFactor = builder.comment("How much the entity is affected on the vertical axis").define("Vertical factor", 0.25D);
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
			builder.pop();

			builder.pop();
			builder.push("tweaks");

			builder.push("horse_armor");
			enchantableHorseArmor = builder.comment("Allow horse armor to be enchanted").define("Enchantable horse armor", true);
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
		public final ConfigValue<Boolean> infinityArrowTexture;
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
