package com.minecraftabnormals.allurement.core;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.DataProcessors;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.TrackedData;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.TrackedDataManager;
import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Allurement.MOD_ID)
public class Allurement {
	public static final String MOD_ID = "allurement";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);
	public static final TrackedData<Boolean> INFINITY_ARROW = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().build();

	public Allurement() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		REGISTRY_HELPER.register(bus);
		AllurementEnchantments.ENCHANTMENTS.register(bus);
		MinecraftForge.EVENT_BUS.register(this);

		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "shot_infinity_arrow"), INFINITY_ARROW);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AllurementConfig.COMMON_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AllurementConfig.CLIENT_SPEC);
	}
}