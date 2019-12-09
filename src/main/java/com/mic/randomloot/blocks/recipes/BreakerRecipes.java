package com.mic.randomloot.blocks.recipes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mic.randomloot.init.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BreakerRecipes {
	private static final BreakerRecipes INSTANCE = new BreakerRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	public static BreakerRecipes getInstance() {
		return INSTANCE;
	}

	private BreakerRecipes() {
		addBreakingRecipe(new ItemStack(ModItems.RL_PICKAXE), new ItemStack(ModItems.RANDOM_SHARD, 3), 4.0F);
		addBreakingRecipe(new ItemStack(ModItems.RL_AXE), new ItemStack(ModItems.RANDOM_SHARD, 3), 5.0F);
		addBreakingRecipe(new ItemStack(ModItems.RL_BOW), new ItemStack(ModItems.RANDOM_SHARD, 3), 5.0F);
		addBreakingRecipe(new ItemStack(ModItems.RL_PAXEL), new ItemStack(ModItems.RANDOM_SHARD, 3), 5.0F);
		addBreakingRecipe(new ItemStack(ModItems.RANDOM_BOOTS), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.RANDOM_CHEST), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.RANDOM_HELMET), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.RANDOM_LEGS), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.RL_SHOVEL), new ItemStack(ModItems.RANDOM_SHARD, 3), 5.0F);
		addBreakingRecipe(new ItemStack(ModItems.RL_SWORD), new ItemStack(ModItems.RANDOM_SHARD, 3), 3.0F);
		addBreakingRecipe(new ItemStack(ModItems.THROWABLE), new ItemStack(ModItems.RANDOM_SHARD, 3), 7.0F);
		addBreakingRecipe(new ItemStack(ModItems.TITANIUM_BOOTS), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.TITANIUM_CHEST), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.TITANIUM_HELMET), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);
		addBreakingRecipe(new ItemStack(ModItems.TITANIUM_LEGS), new ItemStack(ModItems.RANDOM_SHARD, 3), 6.0F);


	}

	public void addBreakingRecipe(ItemStack input, ItemStack result, float experience) {
		if (getBreakingResult(input) != ItemStack.EMPTY)
			return;
		this.smeltingList.put(input, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}

	public ItemStack getBreakingResult(ItemStack input1) {
		Random rand = new Random();
		NBTTagCompound compound = (input1.hasTagCompound()) ? input1.getTagCompound() : new NBTTagCompound();
		int rarity = compound.getInteger("rarity");

		for (ItemStack entry : this.smeltingList.keySet()) {
			if (this.compareItemStacks(input1, (ItemStack) entry)) {
				ItemStack i = (ItemStack) this.smeltingList.get(entry);

				i.setCount(rand.nextInt(3) + rarity);

				return i;
			}
		}
		return ItemStack.EMPTY;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getSmeltingList() {
		return this.smeltingList;
	}

	public float getBreakingExperience(ItemStack stack) {
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return ((Float) entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}