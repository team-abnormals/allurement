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

		public final ConfigValue<Boolean> enableAlleviating;
		public final ConfigValue<Boolean> enableReeling;
		public final ConfigValue<Boolean> enableReforming;
		public final ConfigValue<Boolean> enableShockwave;

		public final ConfigValue<Boolean> removeLevelScaling;
		public final ConfigValue<Integer> experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Allurement common configuration").push("common");
			builder.push("enchantments");
			
			enchantableHorseArmor = builder.comment("Allow horse armor to be enchanted").define("Enchantable horse armor", true);
			riptideWorksInCauldrons = builder.comment("Allow Riptide to function when in cauldrons").define("Riptide works in cauldrons", true);

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