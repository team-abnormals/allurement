package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AllurementDamageTypes {
	public static final ResourceKey<DamageType> SHOCKWAVE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Allurement.MOD_ID, "shockwave"));

	public static void bootstrap(BootstapContext<DamageType> context) {
		context.register(SHOCKWAVE, new DamageType(Allurement.MOD_ID + ".shockwave", 0.1F));
	}

	public static DamageSource shockwave(Level level, Entity causingEntity, @Nullable Entity directEntity) {
		return level.damageSources().source(SHOCKWAVE, causingEntity);
	}
}
