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
		public final ConfigValue<Boolean> infinityRequiresArrows;

		public final ConfigValue<Boolean> enableAlleviating;
		public final ConfigValue<Boolean> enableReeling;
		public final ConfigValue<Boolean> enableReforming;
		public final ConfigValue<Boolean> enableShockwave;

		public final ConfigValue<Boolean> removeLevelScaling;
		public final ConfigValue<Integer> experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");

			enchantableHorseArmor = builder.comment("Allow horse armor to be enchanted").define("Enchantable horse armor", true);
			riptideWorksInCauldrons = builder.comment("Allow Riptide to function when in cauldrons").define("Riptide works in cauldrons", true);
			soulSpeedHurtsMore = builder.comment("Instead of losing durability as you run, Soul Speed makes incoming damage increase when on Soul Speed blocks").define("Soul Speed change", true);
			infinityRequiresArrows = builder.comment("If Infinity requires an arrow in the player's inventory in order to shoot").define("Infinity requires arrows", false);

			builder.push("additions");
			enableAlleviating = builder.comment("Armor enchantment that heals the user when collecting experience").define("Enable Alleviating", true);
			enableReeling = builder.comment("Crossbow enchantment that pulls targets towards the user").define("Enable Reeling", true);
			enableReforming = builder.comment("Gear enchantment that very slowly repairs items over time").define("Enable Reforming", true);
			enableShockwave = builder.comment("Boots enchantment that creates a shockwave when taking fall damage").define("Enable Shockwave", true);
			builder.pop();

			builder.pop();

			builder.push("experience");
			removeLevelScaling = builder.comment("Remove the amount of experience per level increasing (experimental)").define("Remove level scaling", false);
			experiencePerLevel = builder.comment("The amount of experience per level, if level scaling is removed").define("Experience per level", 50);
			builder.pop();
		}
	}

	public static class Client {
		public final ConfigValue<Boolean> infinityArrowTexture;
		public final ConfigValue<Boolean> infinityArrowGlint;

		Client(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");
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