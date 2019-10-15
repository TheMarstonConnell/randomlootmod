package com.mic.randomloot.items;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mic.randomloot.RandomLoot;
import com.mic.randomloot.init.ModItems;
import com.mic.randomloot.tags.BasicTag;
import com.mic.randomloot.tags.EffectTag;
import com.mic.randomloot.tags.TagHelper;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class RandomArmor extends ItemArmor {

	public RandomArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		
		setCreativeTab(RandomLoot.randomlootTab);
		setRegistryName(new ResourceLocation(RandomLoot.MODID, name));
		setUnlocalizedName(name);
		ModItems.ITEMS.add(this);


		// TODO Auto-generated constructor stub
	}


	@Override
	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {

		super.onArmorTick(world, player, itemStack);
		List<BasicTag> tags = TagHelper.getAllTags(itemStack);

		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i) instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tags.get(i);
				eTag.runEffect(itemStack, world, player);
			}
		}

	}

	public static ItemStack assignType(ItemStack item) {
		
		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		for (BasicTag tag : TagHelper.allTags) {
			if (tag instanceof EffectTag) {
				EffectTag eTag = (EffectTag) tag;
				if (!eTag.offensive) {
					allowedTags.add(eTag);
				}
			}
		}

		int totalTags = RandomLoot.rand.nextInt(3);
		for (int i = 0; i < totalTags; i++) {
			TagHelper.addTag(item, allowedTags.get(RandomLoot.rand.nextInt(allowedTags.size())).name);
		}

		return item;
	}

	public void setLore(ItemStack item, EntityLivingBase player) {

		// System.out.println(digSpeed);

		NBTTagCompound compound;
		if (item.hasTagCompound()) {
			compound = item.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}

		TextFormatting color = null;
		switch (compound.getInteger("rarity")) {
		case 1:
			color = TextFormatting.WHITE;
			break;
		case 2:
			color = TextFormatting.GOLD;
			break;
		case 3:
			color = TextFormatting.LIGHT_PURPLE;
			break;

		}
		NBTTagCompound display;

		if(compound.hasKey("display")) {
			display = compound.getCompoundTag("display");
		}else {
			display = new NBTTagCompound();

		}

		NBTTagList lore;
		if(display.hasKey("Lore")) {
			lore = display.getTagList("Lore", 8);
		}else {
			lore = new NBTTagList();

		}
		DecimalFormat f = new DecimalFormat("#0.00");
		lore.appendTag(new NBTTagString(""));
		
		List<BasicTag> tags = TagHelper.getAllTags(item);
		System.out.println("Amount of tags on item: " + tags.size());
		for(int i = 0; i < tags.size(); i ++) {
			
			String name = tags.get(i).name.replaceAll("_", " ");
			name = TagHelper.convertToTitleCaseIteratingChars(name);
			System.out.println("Writing new tag to lore...");
			 lore.appendTag(new NBTTagString(tags.get(i).color+
						 name));
		}
		

		lore.appendTag(new NBTTagString(""));
		lore.appendTag(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInteger("Lvl")));
		lore.appendTag(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInteger("Xp") + "/" + compound.getInteger("lvlXp") + " Xp"));

		
		display.setTag("Lore", lore);
		compound.setTag("display", display);

//		item.setStackDisplayName(color + compound.getString("name"));

	}

	public static void setName(ItemStack stack){
		NBTTagCompound compound;
		if (stack.hasTagCompound()) {
			compound = stack.getTagCompound();
		} else {
			compound = new NBTTagCompound();
		}

		TextFormatting color = null;
		switch (compound.getInteger("rarity")) {
			case 1:
				color = TextFormatting.WHITE;
				break;
			case 2:
				color = TextFormatting.GOLD;
				break;
			case 3:
				color = TextFormatting.LIGHT_PURPLE;
				break;

		}
		stack.setStackDisplayName(color + compound.getString("name"));

	}

}
