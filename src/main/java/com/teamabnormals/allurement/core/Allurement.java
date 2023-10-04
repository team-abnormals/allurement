package com.teamabnormals.allurement.core;

import com.teamabnormals.allurement.common.dispenser.IronIngotDispenseBehavior;
import com.teamabnormals.allurement.core.data.client.AllurementLanguageProvider;
import com.teamabnormals.allurement.core.data.server.AllurementDatapackBuiltinEntriesProvider;
import com.teamabnormals.allurement.core.data.server.modifiers.AllurementGlobalLootModifierProvider;
import com.teamabnormals.allurement.core.data.server.modifiers.AllurementLootModifierProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementBlockTagsProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementEnchantmentTagsProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementMobEffectTagsProvider;
import com.teamabnormals.allurement.core.other.AllurementTrackedData;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import com.teamabnormals.allurement.core.registry.AllurementLootConditions;
import com.teamabnormals.allurement.core.registry.AllurementLootModifiers;
import com.teamabnormals.blueprint.core.util.BlockUtil;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.blueprint.core.util.DataUtil.AlternativeDispenseBehavior;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(Allurement.MOD_ID)
public class Allurement {
	public static final String MOD_ID = "allurement";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Allurement() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext context = ModLoadingContext.get();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		AllurementEnchantments.ENCHANTMENTS.register(bus);
		AllurementLootConditions.LOOT_CONDITION_TYPES.register(bus);
		AllurementLootModifiers.GLOBAL_LOOT_MODIFIERS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::dataSetup);

		context.registerConfig(ModConfig.Type.COMMON, AllurementConfig.COMMON_SPEC);
		context.registerConfig(ModConfig.Type.CLIENT, AllurementConfig.CLIENT_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		AllurementTrackedData.registerTrackedData();
		event.enqueueWork(() -> {
			DataUtil.registerAlternativeDispenseBehavior(new AlternativeDispenseBehavior(Allurement.MOD_ID, Items.IRON_INGOT, (source, stack) -> AllurementConfig.COMMON.anvilIngotRepairing.get() && IronIngotDispenseBehavior.canBeRepaired(source.getLevel().getBlockState(BlockUtil.offsetPos(source))), new IronIngotDispenseBehavior()));
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean includeServer = event.includeServer();
		generator.addProvider(includeServer, new AllurementEnchantmentTagsProvider(output, provider, helper));
		generator.addProvider(includeServer, new AllurementMobEffectTagsProvider(output, provider, helper));
		generator.addProvider(includeServer, new AllurementBlockTagsProvider(output, provider, helper));
		generator.addProvider(includeServer, new AllurementGlobalLootModifierProvider(output));
		generator.addProvider(includeServer, new AllurementLootModifierProvider(output, provider));
		generator.addProvider(includeServer, new AllurementDatapackBuiltinEntriesProvider(output, provider));

		boolean includeClient = event.includeClient();
		generator.addProvider(includeClient, new AllurementLanguageProvider(output));
	}
}