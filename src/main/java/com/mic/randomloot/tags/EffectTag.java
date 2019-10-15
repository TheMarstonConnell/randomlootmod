package com.mic.randomloot.tags;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EffectTag extends BasicTag{

	PotionEffect effect;
	public boolean offensive = false;
	public boolean forTools = false;
	
	public EffectTag(String name, TextFormatting color, PotionEffect effect, boolean offensive, boolean forTools) {
		super(name, color);
		this.effect = effect;
		this.offensive = offensive;
		this.forTools = forTools;
	}
	
	public void runEffect(ItemStack stack, World worldIn,
			EntityLivingBase entityLiving) {
		if (!entityLiving.isPotionActive(effect.getPotion())) { // If the Potion isn't currently active,
		entityLiving.addPotionEffect(effect);
		}
	}

}
