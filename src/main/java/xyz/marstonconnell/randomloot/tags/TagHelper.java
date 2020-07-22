package xyz.marstonconnell.randomloot.tags;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Types;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants.NBT;
import xyz.marstonconnell.randomloot.tags.worldinteract.CriticalStrikeEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.DamageEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.ExplosionEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.FindEntitiesEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.FloatEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.ReplenishEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.TeleportItemsEvent;
import xyz.marstonconnell.randomloot.utils.Config;

public class TagHelper {
	
	final static int tagLimit = 20;
	
	public static List<BasicTag> allTags = new ArrayList<BasicTag>();
	public static List<String> tagNames = new ArrayList<String>();

	// public static final EffectTag HEALTH_BOOST = new EffectTag("health_boost",
	// TextFormatting.RED, new EffectInstance(Effects.HEALTH_BOOST, 120, 3, false,
	// false), false);
	// public static final EffectTag empty = new EffectTag("empty", COLOR, new
	// PoitionEffect(), offensive, forTools, forWeapons)
	
	
	//HELPFUL EFFECTS
	public static final EffectTag SPEED_ONE = new EffectTag("speedy", TextFormatting.AQUA,
			new EffectInstance(Effects.SPEED, 100, 0), false, false, true);
	public static final EffectTag SPEED_TWO = new EffectTag("speedier", TextFormatting.AQUA,
			new EffectInstance(Effects.SPEED, 100, 1), false, false, true);
	public static final EffectTag JUMP_ONE = new EffectTag("bouncy", TextFormatting.GREEN,
			new EffectInstance(Effects.JUMP_BOOST, 100, 0), false, false, false);
	public static final EffectTag JUMP_TWO = new EffectTag("bouncier", TextFormatting.GREEN,
			new EffectInstance(Effects.JUMP_BOOST, 100, 1), false, false, false);
	
	public static final EffectTag RESISTANCE_ONE = new EffectTag("resistant", TextFormatting.GRAY,
			new EffectInstance(Effects.RESISTANCE, 100, 0), false, false, true);
	public static final EffectTag RESISTANCE_TWO = new EffectTag("more_resistant", TextFormatting.GRAY,
			new EffectInstance(Effects.RESISTANCE, 100, 1), false, false, true);
	public static final EffectTag FIRE_RESISTANCE_ONE = new EffectTag("fire_resistant", TextFormatting.YELLOW,
			new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0), false, false, false);
	public static final EffectTag FIRE_RESISTANCE_TWO = new EffectTag("more_fire_resistant", TextFormatting.YELLOW,
			new EffectInstance(Effects.FIRE_RESISTANCE, 100, 1), false, false, false);
	public static final EffectTag HASTE_ONE = new EffectTag("hastey", TextFormatting.YELLOW,
			new EffectInstance(Effects.HASTE, 100, 0), false, true, false);
	public static final EffectTag HASTE_TWO = new EffectTag("hastier", TextFormatting.YELLOW,
			new EffectInstance(Effects.HASTE, 100, 1), false, true, false);
	
	public static final EffectTag LUCK_ONE = new EffectTag("lucky", TextFormatting.GREEN,
			new EffectInstance(Effects.LUCK, 100, 0), false, true, false);
	public static final EffectTag LUCK_TWO = new EffectTag("super_lucky", TextFormatting.GREEN,
			new EffectInstance(Effects.LUCK, 100, 1), false, true, false);
	
	public static final EffectTag STRENGTH_ONE = new EffectTag("strong", TextFormatting.DARK_RED,
			new EffectInstance(Effects.STRENGTH, 100, 0), false, false, true);
	public static final EffectTag STRENGTH_TWO = new EffectTag("stronger", TextFormatting.DARK_RED,
			new EffectInstance(Effects.STRENGTH, 100, 1), false, false, true);
	
	public static final EffectTag NIGHT_VISION = new EffectTag("insightful", TextFormatting.BLUE,
			new EffectInstance(Effects.NIGHT_VISION, 500, 0), false, false, false);
	
	public static final EffectTag WATER_BREATHING = new EffectTag("deep_breathing", TextFormatting.DARK_BLUE,
			new EffectInstance(Effects.WATER_BREATHING, 100, 0), false, false, false);
	
	public static final EffectTag REGENERATION = new EffectTag("regenerating", TextFormatting.RED,
			new EffectInstance(Effects.REGENERATION, 100, 0), false, false, true);

	//ATTACKING EFFECTS
	public static final EffectTag POISON_ONE = new EffectTag("poisonous", TextFormatting.DARK_GREEN,
			new EffectInstance(Effects.POISON, 100, 0), true, false, true);
	public static final EffectTag POISON_TWO = new EffectTag("very_poisonous", TextFormatting.DARK_GREEN,
			new EffectInstance(Effects.POISON, 100, 1), true, false, true);
	
	public static final EffectTag WITHER_ONE = new EffectTag("withering", TextFormatting.DARK_GRAY,
			new EffectInstance(Effects.WITHER, 100, 0), true, false, true);
	public static final EffectTag WITHER_TWO = new EffectTag("very_withering", TextFormatting.DARK_GRAY,
			new EffectInstance(Effects.WITHER, 100, 1), true, false, true);
	
	public static final EffectTag SLOWING_ONE = new EffectTag("webbed", TextFormatting.WHITE,
			new EffectInstance(Effects.SLOWNESS, 100, 0), true, false, true);
	public static final EffectTag SLOWING_TWO = new EffectTag("double_webbed", TextFormatting.WHITE,
			new EffectInstance(Effects.SLOWNESS, 160, 1), true, false, true);
	
	public static final EffectTag BLINDING = new EffectTag("blinding", TextFormatting.DARK_PURPLE,
			new EffectInstance(Effects.BLINDNESS, 100, 0), true, false, true);
	
	public static final EffectTag GLOWING = new EffectTag("glittering", TextFormatting.GOLD,
			new EffectInstance(Effects.GLOWING, 100, 0), true, false, true);
	
	public static final EffectTag DAMAGE_ONE = new EffectTag("blood_thirst", TextFormatting.DARK_RED,
			new EffectInstance(Effects.INSTANT_DAMAGE, 120, 1), true, false, true);
	public static final EffectTag DAMAGE_TWO = new EffectTag("blood_hunger", TextFormatting.DARK_RED,
			new EffectInstance(Effects.INSTANT_DAMAGE, 120, 2), true, false, true);
	
	public static final EffectTag WEAKNESS_ONE = new EffectTag("weakening", TextFormatting.GRAY,
			new EffectInstance(Effects.WEAKNESS, 100, 0), true, false, true);
	public static final EffectTag WEAKNESS_TWO = new EffectTag("very_weakening", TextFormatting.GRAY,
			new EffectInstance(Effects.WEAKNESS, 100, 2), true, false, true);
	
	public static final EffectTag FLOATING = new EffectTag("floating", TextFormatting.AQUA,
			new EffectInstance(Effects.LEVITATION, 100, 0), true, false, true);
	
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
	public static final WorldInteractTag GIANT_SLAYER = new WorldInteractTag("giant_slayer", TextFormatting.BLUE, new DamageEvent(0.18f), false, false, true);
	public static final WorldInteractTag GIANT_SLAYER_II = new WorldInteractTag("titan_slayer", TextFormatting.BLUE, new DamageEvent(0.24f), false, false, true);
	
	public static final WorldInteractTag CRITICAL_STRIKE = new WorldInteractTag("infinity_edge", TextFormatting.YELLOW, new CriticalStrikeEvent(), false, false, true);

	
	
	
	//PASSIVE EFFECTS
	public static final BasicTag UNBREAKABLE = new BasicTag("fortified", TextFormatting.BLUE);
	public static final BasicTag AUTOSMELT = new BasicTag("auto-smelt", TextFormatting.DARK_RED);

	public static ItemStack addTag(ItemStack stack, String tagName) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		
		ListNBT heldTags = nbt.getList("rl_tags", NBT.TAG_STRING);
		
		heldTags.add(StringNBT.valueOf(tagName));
		
		nbt.put("rl_tags", heldTags);
		

		stack.setTag(nbt);
		return stack;
	}

	public static ItemStack removeTag(ItemStack stack, String tagName) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		ListNBT heldTags = nbt.getList("rl_tags", NBT.TAG_STRING);
		
		heldTags.remove(StringNBT.valueOf(tagName));
		
		stack.setTag(nbt);
		return stack;
	}
	
	public static ItemStack removeAllTags(ItemStack stack) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		ListNBT heldTags = nbt.getList("rl_tags", NBT.TAG_STRING);
		
		heldTags.clear();
		

		stack.setTag(nbt);
		return stack;
	}

	public static boolean checkForTag(ItemStack stack, BasicTag tag) {
		return getAllTags(stack).contains(tag);
	}

	public static List<BasicTag> getTagList(ItemStack stack) {
		List<BasicTag> tags = new ArrayList<BasicTag>();
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		ListNBT heldTags = nbt.getList("rl_tags", NBT.TAG_STRING);
		

		for (int i = 0; i < heldTags.size(); i++) {
			String tag = heldTags.getString(i);
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
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		
		ListNBT heldTags = nbt.getList("rl_tags", NBT.TAG_STRING);
		
		
		for (int i = 0; i < heldTags.size(); i++) {
			String tag = heldTags.getString(i);
			if (!tag.equals("")) {
				for (int j = 0; j < TagHelper.allTags.size(); j++) {
					if (tag.equals(TagHelper.allTags.get(j).name)) {
						if(Config.traitsEnabled.get(TagHelper.allTags.get(j).name).get()) {
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
		
		text = text.replaceAll("_", " ");
		

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
