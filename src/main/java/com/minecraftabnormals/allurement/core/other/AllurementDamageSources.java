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
		public Entity getEntity() {
			return this.attacker;
		}

		@Override
		public ITextComponent getLocalizedDeathMessage(LivingEntity target) {
			ItemStack stack = this.attacker instanceof LivingEntity ? ((LivingEntity) this.attacker).getItemBySlot(EquipmentSlotType.FEET) : ItemStack.EMPTY;
			String s = "death.attack." + this.msgId;
			return !stack.isEmpty() && stack.hasCustomHoverName() ? new TranslationTextComponent(s + ".player.item", target.getDisplayName(), this.attacker.getDisplayName(), stack.getDisplayName()) : new TranslationTextComponent(s + ".player", target.getDisplayName(), this.attacker.getDisplayName());
		}

		@Override
		public boolean scalesWithDifficulty() {
			return this.attacker != null && this.attacker instanceof LivingEntity && !(this.attacker instanceof PlayerEntity);
		}

		@Override
		@Nullable
		public Vector3d getSourcePosition() {
			return this.attacker != null ? this.attacker.position() : null;
		}

		@Override
		public String toString() {
			return "ShockwaveDamageSource (" + this.attacker + ")";
		}
	}
}
