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
	
	public WorldInteractTag(String name, TextFormatting color, WorldInteractEvent event, boolean forTools, boolean forArmor, boolean forWeapons) {
		super(name, color);
		wie = event;
		this.forTools = forTools;
		this.forWeapons = forWeapons;
		this.forArmor = forArmor;

	}
	
	public void runEffect(ItemStack stack, World worldIn,
			LivingEntity entityLiving, BlockState state, BlockPos pos) {
		
		wie.effect(stack, worldIn, entityLiving, state, pos);
		
	}

}
