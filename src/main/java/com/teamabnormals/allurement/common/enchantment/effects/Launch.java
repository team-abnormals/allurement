package com.teamabnormals.allurement.common.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record Launch(LevelBasedValue amount) implements EnchantmentEntityEffect {
	public static final MapCodec<Launch> CODEC = RecordCodecBuilder.mapCodec(
			p_345307_ -> p_345307_.group(LevelBasedValue.CODEC.fieldOf("amount").forGetter(p_346038_ -> p_346038_.amount)).apply(p_345307_, Launch::new)
	);

	@Override
	public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
		if (entity instanceof LivingEntity target) {
			target.push(0.0D, this.amount.calculate(enchantmentLevel) * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), 0.0D);
		}
	}

	@Override
	public MapCodec<Launch> codec() {
		return CODEC;
	}
}