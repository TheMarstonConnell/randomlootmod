package com.mic.randomloot.tags;

import java.util.Collection;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EffectTag extends BasicTag {

	PotionEffect effect;
	public boolean offensive = false;
	public boolean forWeapons = false;
	public boolean forTools = false;

	public EffectTag(String name, TextFormatting color, PotionEffect effect, boolean offensive, boolean forTools,
			boolean forWeapons) {
		super(name, color);
		this.effect = effect;
		this.offensive = offensive;
		this.forWeapons = forWeapons;
		this.forTools = forTools;

	}

	public PotionEffect copyEffect(PotionEffect effect) {
		return new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier());
	}

	public void runEffect(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

		// if (!entityLiving.isPotionActive(effect.getPotion())) { // If the Potion
		// isn't currently active,
		PotionEffect copy = copyEffect(effect);
		// System.out.println("Applying " + copy.getEffectName() + " level " +
		// copy.getAmplifier() + " to " + entityLiving.getName() + " for " +
		// copy.getDuration() + " ticks.");

		Collection<PotionEffect> effects = entityLiving.getActivePotionEffects();

		boolean foundEffect = false;
		for (PotionEffect ef : effects) {
			if (ef.getEffectName().equals(copy.getEffectName())) {
				foundEffect = true;
			}
		}
		if (!foundEffect) {
			entityLiving.addPotionEffect(copy);
		}
		// }

	}

}
