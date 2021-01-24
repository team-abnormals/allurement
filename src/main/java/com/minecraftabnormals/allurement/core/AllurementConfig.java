package com.minecraftabnormals.allurement.core;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementConfig {

	public static class Common {
		public final ConfigValue<Boolean> enchantingHorseArmor;

		public final ConfigValue<Boolean> removeLevelScaling;
		public final ConfigValue<Integer> experiencePerLevel;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("enchantments");
			builder.pop();

			builder.push("enchanting");
			enchantingHorseArmor = builder.define("Allow horse armor to be enchanted", true);
			builder.pop();

			builder.push("experience");
			removeLevelScaling = builder.define("Remove the amount of experience per level increasing", true);
			experiencePerLevel = builder.define("The amount of experience per level", 50);
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