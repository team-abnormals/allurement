package com.teamabnormals.allurement.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.allurement.core.AllurementConfig;
import com.teamabnormals.allurement.core.registry.AllurementEnchantments;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class AscensionCurseLootModifier extends LootModifier {
	public static final Supplier<Codec<AscensionCurseLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AscensionCurseLootModifier::new)));

	public AscensionCurseLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (AllurementConfig.COMMON.enableAscensionCurse.get()) {
			if (!BuiltInLootTables.END_CITY_TREASURE.equals(context.getQueriedLootTableId())) {
				for (ItemStack stack : generatedLoot) {
					if (EnchantmentHelper.getTagEnchantmentLevel(AllurementEnchantments.ASCENSION_CURSE.get(), stack) > 0) {
						Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
						enchantments.remove(AllurementEnchantments.ASCENSION_CURSE.get());
						EnchantmentHelper.setEnchantments(enchantments, stack);
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