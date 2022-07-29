package com.teamabnormals.allurement.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.allurement.core.AllurementConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class HorseArmorLootModifier extends LootModifier {
	public static final Supplier<Codec<HorseArmorLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, HorseArmorLootModifier::new)));

	public HorseArmorLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (AllurementConfig.COMMON.enchantableHorseArmor.get() && AllurementConfig.COMMON.enchantedHorseArmorGenerates.get()) {
			if (!AllurementConfig.COMMON.unenchantedHorseArmorLootTables.get().contains(context.getQueriedLootTableId().toString())) {
				RandomSource random = context.getRandom();
				for (ItemStack stack : generatedLoot) {
					if (stack.getItem() instanceof HorseArmorItem) {
						EnchantmentHelper.enchantItem(random, stack, UniformGenerator.between(20.0F, 39.0F).getInt(context), true);
					}
				}
			}
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}