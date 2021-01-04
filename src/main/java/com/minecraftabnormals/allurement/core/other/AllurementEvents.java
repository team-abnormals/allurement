package com.minecraftabnormals.allurement.core.other;

import com.minecraftabnormals.allurement.core.Allurement;
import com.minecraftabnormals.allurement.core.registry.AllurementEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Allurement.MOD_ID)
public class AllurementEvents {

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		LivingEntity entity = event.getEntityLiving();
		World world = entity.getEntityWorld();
		int level = EnchantmentHelper.getEnchantmentLevel(AllurementEnchantments.SHOCKWAVE.get(), entity.getItemStackFromSlot(EquipmentSlotType.FEET));

		EffectInstance effectinstance = entity.getActivePotionEffect(Effects.JUMP_BOOST);
		float f = effectinstance == null ? 0.0F : (float)(effectinstance.getAmplifier() + 1);
		int damage = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());

		if (level > 0 && damage > 0) {
			System.out.println(event.getDamageMultiplier());
			for (LivingEntity target : world.getEntitiesWithinAABB(LivingEntity.class, entity.getBoundingBox().grow(level, 0.0D, level))) {
				if (entity != target)
					target.attackEntityFrom(DamageSource.causeMobDamage(entity), event.getDistance());
			}

			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(ParticleTypes.CLOUD, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 200, level, 0.5, level, 0);
			}
		}
	}
}
