package com.teamabnormals.allurement.core.data.server.tags;

import com.teamabnormals.allurement.core.Allurement;
import com.teamabnormals.allurement.core.other.tags.AllurementMobEffectTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AllurementMobEffectTagsProvider extends TagsProvider<MobEffect> {

	public AllurementMobEffectTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Registry.MOB_EFFECT, Allurement.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		this.tag(AllurementMobEffectTags.SPREAD_OF_AILMENTS_CANNOT_INFLICT).add(MobEffects.BAD_OMEN, MobEffects.HERO_OF_THE_VILLAGE).addOptional(new ResourceLocation("environmental", "serenity"));
	}

	@Override
	public String getName() {
		return "Mob Effect Tags";
	}
}