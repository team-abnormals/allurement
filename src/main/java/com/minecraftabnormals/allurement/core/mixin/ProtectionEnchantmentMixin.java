package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.enchantment.ProtectionEnchantment.Type;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {

	@Shadow
	@Final
	public Type type;

	protected ProtectionEnchantmentMixin(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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