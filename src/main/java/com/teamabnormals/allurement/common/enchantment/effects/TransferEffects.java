package com.teamabnormals.allurement.common.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record TransferEffects(LevelBasedValue amount, TagKey<MobEffect> ignoredEffects) implements EnchantmentEntityEffect {
	public static final MapCodec<TransferEffects> CODEC = RecordCodecBuilder.mapCodec(
			p_345307_ -> p_345307_.group(
					LevelBasedValue.CODEC.fieldOf("amount").forGetter(p_346038_ -> p_346038_.amount),
					TagKey.codec(Registries.MOB_EFFECT).fieldOf("ignored_effects").forGetter(t -> t.ignoredEffects)
			).apply(p_345307_, TransferEffects::new)
	);

	@Override
	public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
		if (entity instanceof LivingEntity living) {
			item.owner().getActiveEffects().forEach(effect -> {
				if (!effect.getEffect().value().isInstantenous() && !effect.isAmbient() && !effect.getEffect().is(this.ignoredEffects())) {
					MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), (int) this.amount.calculate(enchantmentLevel), effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon());
					living.addEffect(newEffect, item.owner());
				}
			});
		}
	}

	@Override
	public MapCodec<TransferEffects> codec() {
		return CODEC;
	}
}