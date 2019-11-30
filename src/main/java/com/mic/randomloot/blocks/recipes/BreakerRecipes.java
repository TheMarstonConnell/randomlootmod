package com.mic.randomloot.blocks.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mic.randomloot.init.ModItems;

import net.minecraft.item.ItemStack;

public class BreakerRecipes 
{	
	private static final BreakerRecipes INSTANCE = new BreakerRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static BreakerRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private BreakerRecipes() 
	{
		addSinteringRecipe(new ItemStack(ModItems.RL_PICKAXE), new ItemStack(ModItems.RANDOM_SHARD, 3), 5.0F);
	}

	
	public void addSinteringRecipe(ItemStack input, ItemStack result, float experience) 
	{
		if(getSinteringResult(input) != ItemStack.EMPTY) return;
		this.smeltingList.put(input, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getSinteringResult(ItemStack input1) 
	{
		for(ItemStack entry : this.smeltingList.keySet()) 
		{
			if(this.compareItemStacks(input1, (ItemStack)entry)) 
			{
				return (ItemStack)this.smeltingList.get(entry);
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Map<ItemStack, ItemStack> getSmeltingList() 
	{
		return this.smeltingList;
	}
	
	public float getSinteringExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}