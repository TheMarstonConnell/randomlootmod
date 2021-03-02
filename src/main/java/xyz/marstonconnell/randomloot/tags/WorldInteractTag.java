package xyz.marstonconnell.randomloot.tags;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class WorldInteractTag extends BasicTag{

	WorldInteractEvent wie;
	
	public boolean forTools = false;
	public boolean forWeapons = false;
	public boolean forArmor = false;
	
	private String[] names;
	
	public WorldInteractTag(WorldInteractTag clone) {
		super(clone);
		this.names = clone.names;
		wie = clone.wie;
		this.forTools = clone.forTools;
		this.forArmor = clone.forArmor;
		this.forWeapons = clone.forWeapons;
		
		for(int i = 1 ;i < names.length; i ++) {
			TagHelper.tagMap.put(names[i], this);
		}
	}
	
	public WorldInteractTag(String[] namesIn, TextFormatting color, WorldInteractEvent event, boolean forTools, boolean forArmor, boolean forWeapons) {
		super(namesIn[0], color);
		this.names = namesIn;
		wie = event;
		this.forTools = forTools;
		this.forWeapons = forWeapons;
		this.forArmor = forArmor;
		
		for(int i = 1 ;i < namesIn.length; i ++) {
			TagHelper.tagMap.put(namesIn[i], this);
		}
	}
	
	public void runEffect(ItemStack stack, World worldIn,
			LivingEntity entityLiving, BlockState state, BlockPos pos, LivingEntity target) {
		
		wie.effect(this.level + 1, stack, worldIn, entityLiving, state, pos, target);
		
	}
	
	@Override
	public String toString() {
		String newName = this.names[level].replaceAll("_", " ");
		newName = TagHelper.convertToTitleCaseIteratingChars(newName);
		return newName;
	}

}
