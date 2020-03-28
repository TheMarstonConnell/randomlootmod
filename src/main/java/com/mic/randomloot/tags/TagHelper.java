package com.mic.randomloot.tags;

import java.util.ArrayList;
import java.util.List;

import com.mic.randomloot.tags.worldinteract.ExplosionEvent;
import com.mic.randomloot.tags.worldinteract.FindEntitiesEvent;
import com.mic.randomloot.tags.worldinteract.FloatEvent;
import com.mic.randomloot.tags.worldinteract.ReplenishEvent;
import com.mic.randomloot.tags.worldinteract.TeleportItemsEvent;
import com.mic.randomloot.util.handlers.ConfigHandler;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public class TagHelper {

	public static List<BasicTag> allTags = new ArrayList<BasicTag>();
	public static List<String> tagNames = new ArrayList<String>();

	// public static final EffectTag HEALTH_BOOST = new EffectTag("health_boost",
	// TextFormatting.RED, new PotionEffect(MobEffects.HEALTH_BOOST, 120, 3, false,
	// false), false);
	// public static final EffectTag empty = new EffectTag("empty", COLOR, new
	// PoitionEffect(), offensive, forTools, forWeapons)
	
	//HELPFUL EFFECTS
	public static final EffectTag SPEED_ONE = new EffectTag("speedy", TextFormatting.AQUA,
			new PotionEffect(MobEffects.SPEED, 100, 0), false, false, true);
	public static final EffectTag SPEED_TWO = new EffectTag("speedier", TextFormatting.AQUA,
			new PotionEffect(MobEffects.SPEED, 100, 1), false, false, true);
	public static final EffectTag JUMP_ONE = new EffectTag("bouncy", TextFormatting.GREEN,
			new PotionEffect(MobEffects.JUMP_BOOST, 100, 0), false, false, false);
	public static final EffectTag JUMP_TWO = new EffectTag("bouncier", TextFormatting.GREEN,
			new PotionEffect(MobEffects.JUMP_BOOST, 100, 1), false, false, false);
	public static final EffectTag RESISTANCE_ONE = new EffectTag("resistant", TextFormatting.GRAY,
			new PotionEffect(MobEffects.RESISTANCE, 100, 0), false, false, true);
	public static final EffectTag RESISTANCE_TWO = new EffectTag("more_resistant", TextFormatting.GRAY,
			new PotionEffect(MobEffects.RESISTANCE, 100, 1), false, false, true);
	public static final EffectTag FIRE_RESISTANCE_ONE = new EffectTag("fire_resistant", TextFormatting.YELLOW,
			new PotionEffect(MobEffects.FIRE_RESISTANCE, 100, 0), false, false, false);
	public static final EffectTag FIRE_RESISTANCE_TWO = new EffectTag("more_fire_resistant", TextFormatting.YELLOW,
			new PotionEffect(MobEffects.FIRE_RESISTANCE, 100, 1), false, false, false);
	public static final EffectTag HASTE_ONE = new EffectTag("hastey", TextFormatting.YELLOW,
			new PotionEffect(MobEffects.HASTE, 100, 0), false, true, false);
	public static final EffectTag HASTE_TWO = new EffectTag("hastier", TextFormatting.YELLOW,
			new PotionEffect(MobEffects.HASTE, 100, 1), false, true, false);
	public static final EffectTag LUCK_ONE = new EffectTag("lucky", TextFormatting.GREEN,
			new PotionEffect(MobEffects.LUCK, 100, 0), false, true, false);
	public static final EffectTag LUCK_TWO = new EffectTag("super_lucky", TextFormatting.GREEN,
			new PotionEffect(MobEffects.LUCK, 100, 1), false, true, false);
	public static final EffectTag STRENGTH_ONE = new EffectTag("strong", TextFormatting.DARK_RED,
			new PotionEffect(MobEffects.STRENGTH, 100, 0), false, false, true);
	public static final EffectTag STRENGTH_TWO = new EffectTag("stronger", TextFormatting.DARK_RED,
			new PotionEffect(MobEffects.STRENGTH, 100, 1), false, false, true);
	public static final EffectTag NIGHT_VISION = new EffectTag("insightful", TextFormatting.BLUE,
			new PotionEffect(MobEffects.NIGHT_VISION, 100, 0), false, false, false);
	public static final EffectTag WATER_BREATHING = new EffectTag("deep_breathing", TextFormatting.DARK_BLUE,
			new PotionEffect(MobEffects.WATER_BREATHING, 100, 0), false, false, false);
	public static final EffectTag REGENERATION = new EffectTag("regenerating", TextFormatting.RED,
			new PotionEffect(MobEffects.REGENERATION, 100, 0), false, false, true);

	//ATTACKING EFFECTS
	public static final EffectTag POISON_ONE = new EffectTag("poisonous", TextFormatting.DARK_GREEN,
			new PotionEffect(MobEffects.POISON, 100, 0), true, false, true);
	public static final EffectTag POISON_TWO = new EffectTag("very_poisonous", TextFormatting.DARK_GREEN,
			new PotionEffect(MobEffects.POISON, 100, 1), true, false, true);
	public static final EffectTag WITHER_ONE = new EffectTag("withering", TextFormatting.DARK_GRAY,
			new PotionEffect(MobEffects.WITHER, 100, 0), true, false, true);
	public static final EffectTag WITHER_TWO = new EffectTag("very_withering", TextFormatting.DARK_GRAY,
			new PotionEffect(MobEffects.WITHER, 100, 1), true, false, true);
	public static final EffectTag SLOWING_ONE = new EffectTag("webbed", TextFormatting.WHITE,
			new PotionEffect(MobEffects.SLOWNESS, 100, 0), true, false, true);
	public static final EffectTag SLOWING_TWO = new EffectTag("double_webbed", TextFormatting.WHITE,
			new PotionEffect(MobEffects.SLOWNESS, 160, 1), true, false, true);
	public static final EffectTag BLINDING = new EffectTag("blinding", TextFormatting.DARK_PURPLE,
			new PotionEffect(MobEffects.BLINDNESS, 100, 0), true, false, true);
	public static final EffectTag GLOWING = new EffectTag("glittering", TextFormatting.GOLD,
			new PotionEffect(MobEffects.GLOWING, 100, 0), true, false, true);
	public static final EffectTag DAMAGE_ONE = new EffectTag("damage+", TextFormatting.DARK_RED,
			new PotionEffect(MobEffects.INSTANT_DAMAGE, 120, 1), true, false, true);
	public static final EffectTag DAMAGE_TWO = new EffectTag("damage++", TextFormatting.DARK_RED,
			new PotionEffect(MobEffects.INSTANT_DAMAGE, 120, 2), true, false, true);
	public static final EffectTag WEAKNESS_ONE = new EffectTag("weakening", TextFormatting.GRAY,
			new PotionEffect(MobEffects.WEAKNESS, 100, 0), true, false, true);
	public static final EffectTag WEAKNESS_TWO = new EffectTag("very_weakening", TextFormatting.GRAY,
			new PotionEffect(MobEffects.WEAKNESS, 100, 2), true, false, true);
	public static final EffectTag FLOATING = new EffectTag("floating", TextFormatting.AQUA,
			new PotionEffect(MobEffects.LEVITATION, 100, 0), true, false, true);
	
	//WORLD INTERACT EFFECTS
	public static final WorldInteractTag EXPLOSION = new WorldInteractTag("explosive", TextFormatting.RED,
			new ExplosionEvent(), true, false, false);
	public static final WorldInteractTag REPLENISH = new WorldInteractTag("filling", TextFormatting.DARK_GREEN,
			new ReplenishEvent(), true, false, true);
	public static final WorldInteractTag TELEPORT_ITEMS = new WorldInteractTag("phasing", TextFormatting.AQUA,
			new TeleportItemsEvent(), true, false, false);
	public static final WorldInteractTag FIND_ENTITIES = new WorldInteractTag("soul_searching", TextFormatting.YELLOW,
			new FindEntitiesEvent(), false, true, false);
	public static final WorldInteractTag CLOUD_WALKER = new WorldInteractTag("cloud_walker", TextFormatting.YELLOW,
			new FloatEvent(), false, true, false);
	
	//PASSIVE EFFECTS
	public static final BasicTag UNBREAKABLE = new BasicTag("fortified", TextFormatting.BLUE);
	public static final BasicTag AUTOSMELT = new BasicTag("auto-smelt", TextFormatting.DARK_RED);

	public static ItemStack addTag(ItemStack stack, String tagName) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int index = 0;
		while (!nbt.getString(index + "").equals("")) {
			System.out.println(index);
			if (nbt.getString(index + "").equals(tagName)) {
//				System.out.println("Tag already used on this item.");
				return stack;
			}
			index++;
			if (index > 10) {
//				System.out.println("No availible slots left on this item.");
				return stack;
			}

		}

		nbt.setString(index + "", tagName);
		stack.setTagCompound(nbt);
		return stack;
	}

	public static ItemStack removeTag(ItemStack stack, String tagName) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int index = 0;
		while (!nbt.getString(index + "").equals("")) {
//			System.out.println(index);
			if (nbt.getString(index + "").equals(tagName)) {
				nbt.setString(index + "", "");
				return stack;
			}
			index++;
			if (index > 10) {
//				System.out.println("Trait not found.");
				return stack;
			}

		}

		nbt.setString(index + "", tagName);
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static ItemStack removeAllTags(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		int index = 0;
		while (!nbt.getString(index + "").equals("")) {
//			System.out.println(index);
			
			nbt.setString(index + "", "");
			
			index++;
			if (index > 10) {
//				System.out.println("Trait not found.");
				return stack;
			}

		}

		stack.setTagCompound(nbt);
		return stack;
	}

	public static boolean checkForTag(ItemStack stack, BasicTag tag) {
		return getAllTags(stack).contains(tag);
	}

	public static List<BasicTag> getTagList(ItemStack stack) {
		List<BasicTag> tags = new ArrayList<BasicTag>();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		for (int i = 0; i < 10; i++) {
			String tag = nbt.getString(i + "");
			if (!tag.equals("")) {
				for (int j = 0; j < TagHelper.allTags.size(); j++) {
					if (tag.equals(TagHelper.allTags.get(j).name)) {
						tags.add(TagHelper.allTags.get(j));
					}
				}
			}
		}

		return tags;

	}
	
	
	public static List<BasicTag> getAllTags(ItemStack stack) {
		List<BasicTag> tags = new ArrayList<BasicTag>();
		NBTTagCompound nbt;
		if (stack.hasTagCompound()) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}

		for (int i = 0; i < 10; i++) {
			String tag = nbt.getString(i + "");
			if (!tag.equals("")) {
				for (int j = 0; j < TagHelper.allTags.size(); j++) {
					if (tag.equals(TagHelper.allTags.get(j).name)) {
						if((boolean) ConfigHandler.traitsEnabled.get(j)) {
							tags.add(TagHelper.allTags.get(j));
						}
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
