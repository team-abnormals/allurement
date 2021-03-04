package com.minecraftabnormals.allurement.core.other;

import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class AllurementDamageSources {
	public static DamageSource causeShockwaveDamage(LivingEntity entity) {
		return new EntityDamageSource(Allurement.MOD_ID + ".shockwave", entity);
	}
}
