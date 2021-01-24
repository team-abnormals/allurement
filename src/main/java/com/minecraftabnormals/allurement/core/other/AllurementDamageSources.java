package com.minecraftabnormals.allurement.core.other;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class AllurementDamageSources {
	public static DamageSource causeShockwaveDamage(LivingEntity entity) {
		return new EntityDamageSource("shockwave", entity);
	}
}
