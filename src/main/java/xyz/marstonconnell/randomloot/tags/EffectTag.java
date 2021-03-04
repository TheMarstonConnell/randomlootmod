package xyz.marstonconnell.randomloot.tags;

import java.util.Collection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EffectTag extends BasicTag {

	Effect effect;
	
	public int level = 0;
	private int duration = 100;
	

	public EffectTag(String name, TextFormatting color, Effect effect, boolean offensive, boolean forTools,
			boolean forWeapons) {
		super(name, color);
		this.effect = effect;
		this.offensive = offensive;
		this.forWeapons = forWeapons;
		this.forTools = forTools;

	}
	
	public EffectTag(EffectTag clone) {
		super(clone);
		this.effect = clone.effect;
		this.offensive = clone.offensive;
		this.forWeapons = clone.forWeapons;
		this.forTools = clone.forTools;
		this.level = clone.level;
	}
	
	public EffectTag(String name, TextFormatting color, EffectInstance effect, boolean offensive, boolean forTools,
			boolean forWeapons) {
		super(name, color);
		this.effect = effect.getPotion();
		this.duration = effect.getDuration();
		this.offensive = offensive;
		this.forWeapons = forWeapons;
		this.forTools = forTools;

	}

	public EffectInstance createEffect() {
		return new EffectInstance(effect, duration, this.level + 1, false, false);
	}
	
	public EffectInstance copyEffect(EffectInstance effect) {
		return new EffectInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier(), false, false);
	}
	
	public EffectInstance getEffect() {
		return copyEffect(this.createEffect());
	}

	public void runEffect(ItemStack stack, World worldIn, LivingEntity entityLiving) {

		// if (!entityLiving.isPotionActive(effect.getPotion())) { // If the Potion
		// isn't currently active,
		
		// System.out.println("Applying " + copy.getEffectName() + " level " +
		// copy.getAmplifier() + " to " + entityLiving.getName() + " for " +
		// copy.getDuration() + " ticks.");

		Collection<EffectInstance> effects = entityLiving.getActivePotionEffects();
		
		EffectInstance toApply = createEffect();
		
		boolean foundEffect = false;
		for (EffectInstance ef : effects) {
			if(!ef.getEffectName().equals(Effects.NIGHT_VISION.getName())) {
				if (ef.getPotion().equals(this.effect)) {
					
					if(ef.getAmplifier() >= toApply.getAmplifier()) {
						foundEffect = true;
					}
					
				}
			}
		}
		if (!foundEffect) {
			
			entityLiving.addPotionEffect(toApply);
		}
		// }

	}

}
