package xyz.marstonconnell.randomloot.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.marstonconnell.randomloot.RandomLootMod;

public class CraftingItem extends Item{

	boolean glows = false;
	
	
	public CraftingItem(String name, boolean glows) {
		super(new Properties().group(ItemGroup.MATERIALS));
		this.setRegistryName(new ResourceLocation(RandomLootMod.MODID, name));
		this.glows= glows;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		// TODO Auto-generated method stub
		return this.glows;
	}
	

	
	
	

}
