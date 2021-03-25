package com.minecraftabnormals.allurement.core.other;

import com.minecraftabnormals.allurement.core.Allurement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
		public Entity getTrueSource() {
			return this.attacker;
		}

		@Override
		public ITextComponent getDeathMessage(LivingEntity target) {
			ItemStack stack = this.attacker instanceof LivingEntity ? ((LivingEntity) this.attacker).getItemStackFromSlot(EquipmentSlotType.FEET) : ItemStack.EMPTY;
			String s = "death.attack." + this.damageType;
			return !stack.isEmpty() && stack.hasDisplayName() ? new TranslationTextComponent(s + ".player.item", target.getDisplayName(), this.attacker.getDisplayName(), stack.getTextComponent()) : new TranslationTextComponent(s + ".player", target.getDisplayName(), this.attacker.getDisplayName());
		}

		@Override
		public boolean isDifficultyScaled() {
			return this.attacker != null && this.attacker instanceof LivingEntity && !(this.attacker instanceof PlayerEntity);
		}

		@Override
		@Nullable
		public Vector3d getDamageLocation() {
			return this.attacker != null ? this.attacker.getPositionVec() : null;
		}

		@Override
		public String toString() {
			return "ShockwaveDamageSource (" + this.attacker + ")";
		}
	}
}
