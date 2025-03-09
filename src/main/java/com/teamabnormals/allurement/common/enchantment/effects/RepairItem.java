package com.teamabnormals.allurement.common.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record RepairItem(LevelBasedValue amount) implements EnchantmentEntityEffect {
	public static final MapCodec<RepairItem> CODEC = RecordCodecBuilder.mapCodec(
			p_345307_ -> p_345307_.group(LevelBasedValue.CODEC.fieldOf("amount").forGetter(p_346038_ -> p_346038_.amount)).apply(p_345307_, RepairItem::new)
	);

	@Override
	public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
		ItemStack stack = item.itemStack();
		if (stack.isDamaged()) {
			stack.setDamageValue(stack.getDamageValue() - (int) this.amount.calculate(enchantmentLevel));
		}
	}

	@Override
	public MapCodec<RepairItem> codec() {
		return CODEC;
	}
}