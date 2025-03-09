package com.teamabnormals.allurement.core.registry;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.allurement.common.enchantment.effects.Launch;
import com.teamabnormals.allurement.common.enchantment.effects.RepairItem;
import com.teamabnormals.allurement.common.enchantment.effects.TransferEffects;
import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.UnaryOperator;

public class AllurementEnchantmentEffects {
	public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Allurement.MOD_ID);
	public static final DeferredRegister<MapCodec<? extends EnchantmentLocationBasedEffect>> LOCATION_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, Allurement.MOD_ID);
	public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Allurement.MOD_ID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> HEAL_WITH_XP = register("heal_with_xp", b -> b.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> STORE_INCOMING_DAMAGE = register("store_incoming_damage", b -> b.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> SHOCKWAVE = register("shockwave", b -> b.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> TRAMPLE_FARMLAND = register("trample_farmland", b -> b.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> INCREASE_INCOMING_DAMAGE = register("increase_incoming_damage", b -> b.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf()));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> PREVENT_ANIMAL_ROAMING = register("prevent_animal_roaming", b -> b.persistent(Unit.CODEC));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> RENDER_INFINITY_ARROW = register("render_infinity_arrow", b -> b.persistent(Unit.CODEC));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> CAN_SHOOT_WITHOUT_ARROW = register("can_shoot_without_arrow", b -> b.persistent(Unit.CODEC));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> PREVENTS_FARMLAND_TRAMPLE = register("prevents_farmland_trample", b -> b.persistent(Unit.CODEC));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> ITEM_ASCEND = register("item_ascend", b -> b.persistent(Unit.CODEC));
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> ITEM_FLEE = register("item_flee", b -> b.persistent(Unit.CODEC));

	public static final DeferredHolder<?, ?> REPAIR_ITEM_LOCATION = LOCATION_EFFECTS.register("repair_item", () -> RepairItem.CODEC);
	public static final DeferredHolder<?, ?> REPAIR_ITEM_ENTITY = ENTITY_EFFECTS.register("repair_item", () -> RepairItem.CODEC);

	public static final DeferredHolder<?, ?> LAUNCH_LOCATION = LOCATION_EFFECTS.register("launch", () -> Launch.CODEC);
	public static final DeferredHolder<?, ?> LAUNCH_ENTITY = ENTITY_EFFECTS.register("launch", () -> Launch.CODEC);

	public static final DeferredHolder<?, ?> TRANSFER_EFFECTS_LOCATION = LOCATION_EFFECTS.register("transfer_effects", () -> TransferEffects.CODEC);
	public static final DeferredHolder<?, ?> TRANSFER_EFFECTS_ENTITY = ENTITY_EFFECTS.register("transfer_effects", () -> TransferEffects.CODEC);

	private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
		return COMPONENTS.register(name, () -> operator.apply(DataComponentType.builder()).build());
	}
}