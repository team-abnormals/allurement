package com.teamabnormals.allurement.core.other;

import com.teamabnormals.allurement.core.Allurement;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AllurementDamageSources {

	public static DamageSource causeShockwaveDamage(LivingEntity entity) {
		return new ShockwaveDamageSource(Allurement.MOD_ID + ".shockwave", entity);
	}

	public static class ShockwaveDamageSource extends DamageSource {
		@Nullable
		protected final Entity attacker;

		public ShockwaveDamageSource(String damageTypeIn, @Nullable Entity damageSourceEntityIn) {
			super(damageTypeIn);
			this.attacker = damageSourceEntityIn;
		}

		@Override
		@Nullable
		public Entity getEntity() {
			return this.attacker;
		}

		@Override
		public Component getLocalizedDeathMessage(LivingEntity target) {
			ItemStack stack = this.attacker instanceof LivingEntity ? ((LivingEntity) this.attacker).getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY;
			String s = "death.attack." + this.msgId;
			return !stack.isEmpty() && stack.hasCustomHoverName() ? Component.translatable(s + ".player.item", target.getDisplayName(), this.attacker.getDisplayName(), stack.getDisplayName()) : Component.translatable(s + ".player", target.getDisplayName(), this.attacker.getDisplayName());
		}

		@Override
		public boolean scalesWithDifficulty() {
			return this.attacker != null && this.attacker instanceof LivingEntity && !(this.attacker instanceof Player);
		}

		@Override
		@Nullable
		public Vec3 getSourcePosition() {
			return this.attacker != null ? this.attacker.position() : null;
		}

		@Override
		public String toString() {
			return "ShockwaveDamageSource (" + this.attacker + ")";
		}
	}
}
