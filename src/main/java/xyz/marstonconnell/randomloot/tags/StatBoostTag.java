package xyz.marstonconnell.randomloot.tags;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class StatBoostTag extends BasicTag{

	StatBoostEvent sbe;
	String[] names;
	
	public StatBoostTag(String[] names, TextFormatting color, StatBoostEvent sbe, boolean forTools, boolean forArmor, boolean forWeapons) {
		super(names[0], color);
		this.names = names;
		this.forArmor = forArmor;
		this.forTools = forTools;
		this.forWeapons = forWeapons;
		this.sbe = sbe;
		
		for(int i = 1 ;i < names.length; i ++) {
			TagHelper.tagMap.put(names[i], this);
		}
	}
	
	
	public StatBoostTag(StatBoostTag clone) {
		super(clone);
		this.names = clone.names;
		this.sbe = clone.sbe;
		this.forTools = clone.forTools;
		this.forArmor = clone.forArmor;
		this.forWeapons = clone.forWeapons;
		
	}
	
	public void runEffect(ItemStack stack, World worldIn,
			LivingEntity entityLiving, BlockPos pos, LivingEntity target) {
		
		sbe.effect(this.level + 1, stack, worldIn, entityLiving, pos, target);
		
	}
	
	public void undoEffect(ItemStack stack, World worldIn,
			LivingEntity entityLiving, BlockPos pos, LivingEntity target) {
		
		sbe.undoEffect(this.level + 1, stack, worldIn, entityLiving, pos, target);
		
	}
	
	@Override
	public String toString() {
		String newName = this.names[level].replaceAll("_", " ");
		newName = TagHelper.convertToTitleCaseIteratingChars(newName);
		return newName;
	}
	

}
