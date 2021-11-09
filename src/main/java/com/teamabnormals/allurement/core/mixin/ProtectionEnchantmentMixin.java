package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.AllurementConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.item.enchantment.ProtectionEnchantment.Type;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {

	@Shadow
	@Final
	public Type type;

	protected ProtectionEnchantmentMixin(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot[] slots) {
		super(rarityIn, typeIn, slots);
	}

	@Override
	public boolean isTreasureOnly() {
		if (this.type == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			return true;
		return super.isTreasureOnly();
	}

	@Override
	public boolean isTradeable() {
		if (this.type == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			return false;
		return super.isTradeable();
	}

	@Override
	public boolean isDiscoverable() {
		if (this.type == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			return false;
		return super.isDiscoverable();
	}
}