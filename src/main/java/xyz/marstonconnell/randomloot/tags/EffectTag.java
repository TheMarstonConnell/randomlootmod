package xyz.marstonconnell.randomloot.tags;

import java.util.Collection;

import net.minecraft.client.gui.fonts.TexturedGlyph.Effect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EffectTag extends BasicTag {

	EffectInstance effect;
	public boolean offensive = false;
	public boolean forWeapons = false;
	public boolean forTools = false;

	public EffectTag(String name, TextFormatting color, EffectInstance effect, boolean offensive, boolean forTools,
			boolean forWeapons) {
		super(name, color);
		this.effect = effect;
		this.offensive = offensive;
		this.forWeapons = forWeapons;
		this.forTools = forTools;

	}

	public EffectInstance copyEffect(EffectInstance effect) {
		return new EffectInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier(), false, false);
	}
	
	public EffectInstance getEffect() {
		return copyEffect(this.effect);
	}

	public void runEffect(ItemStack stack, World worldIn, LivingEntity entityLiving) {

		// if (!entityLiving.isPotionActive(effect.getPotion())) { // If the Potion
		// isn't currently active,
		EffectInstance copy = copyEffect(effect);
		
		// System.out.println("Applying " + copy.getEffectName() + " level " +
		// copy.getAmplifier() + " to " + entityLiving.getName() + " for " +
		// copy.getDuration() + " ticks.");

		Collection<EffectInstance> effects = entityLiving.getActivePotionEffects();
		
		boolean foundEffect = false;
		for (EffectInstance ef : effects) {
			if(!ef.getEffectName().equals(Effects.NIGHT_VISION.getName())) {
			if (ef.getEffectName().equals(copy.getEffectName())) {
				foundEffect = true;
			}
			}
		}
		if (!foundEffect) {
			
			entityLiving.addPotionEffect(copy);
		}
		// }

	}

}
