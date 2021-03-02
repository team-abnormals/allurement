package com.minecraftabnormals.allurement.core;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementConfig {

	public static class Common {
		public final ConfigValue<Boolean> enchantableHorseArmor;

		public final ConfigValue<Boolean> enableAlleviating;
		public final ConfigValue<Boolean> enableReeling;
		public final ConfigValue<Boolean> enableReforming;
		public final ConfigValue<Boolean> enableShockwave;

		public final ConfigValue<Boolean> removeLevelScaling;
		public final ConfigValue<Integer> experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");
			enableAlleviating = builder.comment("Armor enchantment that heals the user when collecting experience").define("enableAlleviating", true);
			enableReeling = builder.comment("Crossbow enchantment that pulls targets towards the user").define("enableReeling", true);
			enableReforming = builder.comment("Gear enchantment that very slowly repairs items over time").define("enableReforming", true);
			enableShockwave = builder.comment("Boots enchantment that creates a shockwave when taking fall damage").define("enableShockwave", true);
			builder.pop();

			builder.push("enchanting");
			enchantableHorseArmor = builder.comment("Allow horse armor to be enchanted").define("enchantableHorseArmor", true);
			builder.pop();

			builder.push("experience");
			removeLevelScaling = builder.comment("Remove the amount of experience per level increasing (experimental)").define("removeLevelScaling", false);
			experiencePerLevel = builder.comment("The amount of experience per level").define("experiencePerLevel", 50);
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
	}
}