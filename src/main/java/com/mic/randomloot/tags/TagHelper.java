package com.mic.randomloot.tags;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class TagHelper {
	
	public static List<BasicTag> allTags = new ArrayList<BasicTag>();
	
//	public static final EffectTag HEALTH_BOOST = new EffectTag("health_boost", TextFormatting.RED, new PotionEffect(MobEffects.HEALTH_BOOST, 1, 2, false, false), false);
	public static final EffectTag SPEED_ONE = new EffectTag("speedy", TextFormatting.AQUA, new PotionEffect(MobEffects.SPEED, 6, 0, false, false), false);
	public static final EffectTag SPEED_TWO = new EffectTag("speedier", TextFormatting.AQUA, new PotionEffect(MobEffects.SPEED, 6, 1, false, false), false);
	public static final EffectTag JUMP_ONE = new EffectTag("bouncy", TextFormatting.GREEN, new PotionEffect(MobEffects.JUMP_BOOST, 6, 0, false, false), false);
	public static final EffectTag JUMP_TWO = new EffectTag("bouncier", TextFormatting.GREEN, new PotionEffect(MobEffects.JUMP_BOOST, 6, 1, false, false), false);
	public static final EffectTag RESISTANCE_ONE = new EffectTag("resistant", TextFormatting.GRAY, new PotionEffect(MobEffects.RESISTANCE, 6, 0, false, false), false);
	public static final EffectTag RESISTANCE_TWO = new EffectTag("more_resistant", TextFormatting.GRAY, new PotionEffect(MobEffects.RESISTANCE, 6, 1, false, false), false);
	public static final EffectTag FIRE_RESISTANCE_ONE = new EffectTag("fire_resistant", TextFormatting.YELLOW, new PotionEffect(MobEffects.FIRE_RESISTANCE, 6, 0, false, false), false);
	public static final EffectTag FIRE_RESISTANCE_TWO = new EffectTag("more_fire_resistant", TextFormatting.YELLOW, new PotionEffect(MobEffects.FIRE_RESISTANCE, 6, 1, false, false), false);
	public static final EffectTag HASTE_ONE = new EffectTag("hastey", TextFormatting.YELLOW, new PotionEffect(MobEffects.HASTE, 6, 0, false, false), false);
	public static final EffectTag HASTE_TWO = new EffectTag("hastier", TextFormatting.YELLOW, new PotionEffect(MobEffects.HASTE, 6, 1, false, false), false);
	public static final EffectTag LUCK_ONE = new EffectTag("lucky", TextFormatting.GREEN, new PotionEffect(MobEffects.LUCK, 6, 0, false, false), false);
	public static final EffectTag LUCK_TWO = new EffectTag("super_lucky", TextFormatting.GREEN, new PotionEffect(MobEffects.LUCK, 6, 1, false, false), false);
	public static final EffectTag STRENGTH_ONE = new EffectTag("strong", TextFormatting.DARK_RED, new PotionEffect(MobEffects.STRENGTH, 6, 0, false, false), false);
	public static final EffectTag STRENGTH_TWO = new EffectTag("stronger", TextFormatting.DARK_RED, new PotionEffect(MobEffects.STRENGTH, 6, 1, false, false), false);
	public static final EffectTag NIGHT_VISION = new EffectTag("insightful", TextFormatting.BLUE, new PotionEffect(MobEffects.NIGHT_VISION, 6, 0, false, false), false);
	public static final EffectTag WATER_BREATHING = new EffectTag("deep_breathing", TextFormatting.DARK_BLUE, new PotionEffect(MobEffects.WATER_BREATHING, 6, 0, false, false), false);
	public static final EffectTag REGENERATION = new EffectTag("regenerating", TextFormatting.RED, new PotionEffect(MobEffects.REGENERATION, 6, 0, false, false), false);

	public static ItemStack addTag(ItemStack stack, String tagName) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		
		int index = 0;
		while(!nbt.getString(index + "").equals("")) {
			System.out.println(index);
			if(nbt.getString(index + "").equals(tagName)) {
				System.out.println("Tag already used on this item.");
				return stack;
			}
			index ++;
			if(index > 10) {
				System.out.println("No availible slots left on this item.");
				return stack;
			}
			
		}
		
		nbt.setString(index + "", tagName);
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static List<BasicTag> getAllTags(ItemStack stack) {
		List<BasicTag> tags = new ArrayList<BasicTag>();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		
	
		for(int i = 0; i < 10; i ++) {
			String tag = nbt.getString(i + "");
			if(!tag.equals("")) {
				for(int j = 0; j < TagHelper.allTags.size(); j ++) {
					if(tag.equals(TagHelper.allTags.get(j).name)) {
						tags.add(TagHelper.allTags.get(j));
					}
				}
			}
		}
		
		return tags;
	
	}
	
	public static String convertToTitleCaseIteratingChars(String text) {
	    if (text == null || text.isEmpty()) {
	        return text;
	    }
	 
	    StringBuilder converted = new StringBuilder();
	 
	    boolean convertNext = true;
	    for (char ch : text.toCharArray()) {
	        if (Character.isSpaceChar(ch)) {
	            convertNext = true;
	        } else if (convertNext) {
	            ch = Character.toTitleCase(ch);
	            convertNext = false;
	        } else {
	            ch = Character.toLowerCase(ch);
	        }
	        converted.append(ch);
	    }
	 
	    return converted.toString();
	}
}
