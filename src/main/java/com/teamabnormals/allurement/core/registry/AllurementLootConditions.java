package com.teamabnormals.allurement.core.registry;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AllurementLootConditions {
	public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registry.LOOT_ITEM_REGISTRY, Allurement.MOD_ID);

	public static final RegistryObject<LootItemConditionType> CONFIG = LOOT_CONDITION_TYPES.register("config", () -> DataUtil.registerConfigCondition(Allurement.MOD_ID, AllurementConfig.COMMON, AllurementConfig.CLIENT));
}