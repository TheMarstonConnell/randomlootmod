package xyz.marstonconnell.randomloot.tags.worldinteract;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tags.WorldInteractEvent;

public class AutoSmeltEvent extends WorldInteractEvent{

	@Override
	public void effect(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		
		if(true)
			return;
		
		
		List<ItemEntity> items = worldIn.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
		if(items.isEmpty()) {
			return;
		}
		
		ItemEntity drop = items.get(0);
		
		for(ItemEntity i : items) {
			if(i.ticksExisted < drop.ticksExisted) {
				drop = i;
			}
		}
		
		
		
		
        List<FurnaceRecipe> irecipe = worldIn.getRecipeManager().getRecipesForType(IRecipeType.SMELTING);

		
		
		for(FurnaceRecipe fr : irecipe) {
			for(Ingredient i : fr.getIngredients()) {
				if(i.test(drop.getItem())){
					
					drop.setPosition(0, -64, 0);
					drop.ticksExisted = drop.lifespan - 1;
					
					ItemEntity item = new ItemEntity(EntityType.ITEM, worldIn);
					item.setItem(fr.getRecipeOutput());
					item.setPosition(pos.getX(), pos.getY(), pos.getZ());
					worldIn.addEntity(item);
					return;
					
					
				}
			}
		}
		
		
	}

	@Override
	public void onAdd(int level, ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state,
			BlockPos pos, Entity target) {
		// TODO Auto-generated method stub
		
	}

}
