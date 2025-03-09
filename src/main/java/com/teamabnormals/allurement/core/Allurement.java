package com.teamabnormals.allurement.core;

import com.teamabnormals.allurement.common.dispenser.IronIngotDispenseBehavior;
import com.teamabnormals.allurement.core.data.client.AllurementLanguageProvider;
import com.teamabnormals.allurement.core.data.server.AllurementDataRemolderProvider;
import com.teamabnormals.allurement.core.data.server.AllurementDatapackBuiltinEntriesProvider;
import com.teamabnormals.allurement.core.data.server.modifiers.AllurementGlobalLootModifierProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementBlockTagsProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementEnchantmentTagsProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementItemTagsProvider;
import com.teamabnormals.allurement.core.data.server.tags.AllurementMobEffectTagsProvider;
import com.teamabnormals.allurement.core.other.AllurementTrackedData;
import com.teamabnormals.allurement.core.registry.AllurementConditions;
import com.teamabnormals.allurement.core.registry.AllurementEnchantmentEffects;
import com.teamabnormals.allurement.core.registry.AllurementLootModifiers;
import com.teamabnormals.blueprint.core.util.BlockUtil;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.blueprint.core.util.DataUtil.AlternativeDispenseBehavior;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

// TODO: Complete port

/**
 * Add Ascension to End Cities
 * Remove Protection
 */

@Mod(Allurement.MOD_ID)
public class Allurement {
	public static final String MOD_ID = "allurement";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Allurement(IEventBus bus, ModContainer container) {
		REGISTRY_HELPER.register(bus);

		AllurementLootModifiers.GLOBAL_LOOT_MODIFIERS.register(bus);
		AllurementConditions.CONDITION_SERIALIZERS.register(bus);
		AllurementEnchantmentEffects.COMPONENTS.register(bus);
		AllurementEnchantmentEffects.ENTITY_EFFECTS.register(bus);
		AllurementEnchantmentEffects.LOCATION_EFFECTS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::dataSetup);

		container.registerConfig(ModConfig.Type.COMMON, AllurementConfig.COMMON_SPEC);
		container.registerConfig(ModConfig.Type.CLIENT, AllurementConfig.CLIENT_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		AllurementTrackedData.registerTrackedData();
		event.enqueueWork(() -> {
			DataUtil.registerAlternativeDispenseBehavior(new AlternativeDispenseBehavior(Allurement.MOD_ID, Items.IRON_INGOT, (source, stack) -> AllurementConfig.COMMON.anvilIngotRepairing.get() && IronIngotDispenseBehavior.canBeRepaired(source.level().getBlockState(BlockUtil.offsetPos(source))), new IronIngotDispenseBehavior()));
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();
		DatapackBuiltinEntriesProvider datapack = new AllurementDatapackBuiltinEntriesProvider(output, provider);
		generator.addProvider(server, datapack);
		provider = datapack.getRegistryProvider();

		generator.addProvider(server, new AllurementEnchantmentTagsProvider(output, provider, helper));
		generator.addProvider(server, new AllurementMobEffectTagsProvider(output, provider, helper));
		BlockTagsProvider blockTags = new AllurementBlockTagsProvider(output, provider, helper);
		generator.addProvider(server, blockTags);
		generator.addProvider(server, new AllurementItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
		generator.addProvider(server, new AllurementDataRemolderProvider(output, provider));
		generator.addProvider(server, new AllurementGlobalLootModifierProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new AllurementLanguageProvider(output));
	}
}