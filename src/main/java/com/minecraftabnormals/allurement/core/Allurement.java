package com.minecraftabnormals.allurement.core;

import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

@Mod(Allurement.MOD_ID)
public class Allurement {
	public static final String MOD_ID = "allurement";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Allurement() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		REGISTRY_HELPER.register(bus);
		AllurementEnchantments.ENCHANTMENTS.register(bus);
		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(this::commonSetup);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			bus.addListener(this::clientSetup);
		});

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AllurementConfig.COMMON_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	public static Map<String, Integer> getEnchantibilityMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put("minecraft:leather_horse_armor", 15);
		map.put("minecraft:iron_horse_armor", 9);
		map.put("minecraft:golden_horse_armor", 25);
		map.put("minecraft:diamond_horse_armor", 10);
		map.put("nether_extension:netherite_horse_armor", 15);
		map.put("caverns_and_chasms:necromium_horse_armor", 13);
		return map;
	}
}