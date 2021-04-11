package com.minecraftabnormals.allurement.core;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.DataProcessors;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.TrackedData;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.TrackedDataManager;
import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.minecraftabnormals.allurement.core.data.EnchantmentTagGenerator;
import com.minecraftabnormals.allurement.core.data.LootModifierGenerator;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import com.minecraftabnormals.allurement.core.registry.AllurementLootModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Allurement.MOD_ID)
public class Allurement {
	public static final String MOD_ID = "allurement";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public static final TrackedData<Boolean> INFINITY_ARROW = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().build();
	public static final TrackedData<Float> ABSORBED_DAMAGE = TrackedData.Builder.create(DataProcessors.FLOAT, () -> 0.0F).enableSaving().build();

	public Allurement() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		REGISTRY_HELPER.register(bus);
		AllurementEnchantments.ENCHANTMENTS.register(bus);
		AllurementLootModifiers.LOOT_MODIFIERS.register(bus);
		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(this::dataSetup);

		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "shot_infinity_arrow"), INFINITY_ARROW);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "absorbed_damage"), ABSORBED_DAMAGE);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AllurementConfig.COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AllurementConfig.CLIENT_SPEC);
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			dataGenerator.addProvider(new EnchantmentTagGenerator(dataGenerator, existingFileHelper));
			dataGenerator.addProvider(new LootModifierGenerator(dataGenerator));
		}
	}
}