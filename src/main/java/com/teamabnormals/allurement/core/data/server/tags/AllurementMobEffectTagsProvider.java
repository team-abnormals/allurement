package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementMobEffectTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AllurementMobEffectTagsProvider extends IntrinsicHolderTagsProvider<MobEffect> {

	public AllurementMobEffectTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, Registries.MOB_EFFECT, provider, effect -> BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).get(), Allurement.MOD_ID, helper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(AllurementMobEffectTags.SPREAD_OF_AILMENTS_CANNOT_INFLICT)
				.add(MobEffects.BAD_OMEN.getKey(), MobEffects.HERO_OF_THE_VILLAGE.getKey())
				.addOptional(ResourceLocation.fromNamespaceAndPath("environmental", "serenity"));
	}
}