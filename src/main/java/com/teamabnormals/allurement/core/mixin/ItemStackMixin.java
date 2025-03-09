package com.teamabnormals.allurement.core.mixin;

import com.teamabnormals.allurement.core.other.tags.AllurementEnchantmentTags;
import com.teamabnormals.allurement.core.other.tags.AllurementItemTags;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IItemStackExtension {

	@Shadow
	public abstract boolean is(TagKey<Item> tag);

	@Override
	public boolean supportsEnchantment(Holder<Enchantment> enchantment) {
		return (!this.is(AllurementItemTags.ANIMAL_ARMOR_ENCHANTABLE) || !enchantment.is(AllurementEnchantmentTags.UNUSABLE_ON_ANIMAL_ARMOR)) && IItemStackExtension.super.supportsEnchantment(enchantment);
	}
}