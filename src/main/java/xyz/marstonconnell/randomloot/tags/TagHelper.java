package xyz.marstonconnell.randomloot.tags;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Types;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
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
import xyz.marstonconnell.randomloot.tools.IRLTool;
import xyz.marstonconnell.randomloot.utils.Config;

public class TagHelper {
	
	private static final String TAG_LIST = "rl_tag_data";
	
	final static int tagLimit = 20;
	
	public static List<BasicTag> allTags = new ArrayList<BasicTag>();
	public static List<String> tagNames = new ArrayList<String>();

	// public static final EffectTag HEALTH_BOOST = new EffectTag("health_boost",
	// TextFormatting.RED, new EffectInstance(Effects.HEALTH_BOOST, 120, 3, false,
	// false), false);
	// public static final EffectTag empty = new EffectTag("empty", COLOR, new
	// PoitionEffect(), offensive, forTools, forWeapons)
	
	
	//HELPFUL EFFECTS
	public static final BasicTag SPEED = new EffectTag("speedy", TextFormatting.AQUA,
			Effects.SPEED, false, false, true).setMaxLevel(3);
	
	public static final BasicTag JUMP = new EffectTag("bouncy", TextFormatting.GREEN,
			Effects.JUMP_BOOST, false, false, false).setMaxLevel(2);
	
	
	public static final BasicTag RESISTANCE = new EffectTag("resistant", TextFormatting.GRAY,
			Effects.RESISTANCE, false, false, false).setMaxLevel(4);
	
	
	public static final BasicTag FIRE_RESISTANCE = new EffectTag("fire_resistant", TextFormatting.YELLOW,
			Effects.FIRE_RESISTANCE, false, false, false).setMaxLevel(2);
	
	
	public static final BasicTag HASTE = new EffectTag("hastey", TextFormatting.YELLOW,
			Effects.HASTE, false, true, false).setMaxLevel(2);
	
	
	public static final BasicTag LUCK = new EffectTag("lucky", TextFormatting.GREEN,
			Effects.LUCK, false, true, false);
	
	
	public static final BasicTag STRENGTH = new EffectTag("strong", TextFormatting.DARK_RED,
			Effects.STRENGTH, false, false, true).setMaxLevel(5);
	
	
	public static final BasicTag NIGHT_VISION = new EffectTag("insightful", TextFormatting.BLUE,
			new EffectInstance(Effects.NIGHT_VISION, 500, 0), false, false, false);
	
	public static final BasicTag WATER_BREATHING = new EffectTag("deep_breathing", TextFormatting.DARK_BLUE,
			Effects.WATER_BREATHING, false, false, false).setMaxLevel(2);
	
	public static final BasicTag REGENERATION = new EffectTag("regenerating", TextFormatting.RED,
			Effects.REGENERATION, false, false, true).setMaxLevel(3);

	//ATTACKING EFFECTS
	public static final BasicTag POISON = new EffectTag("poisonous", TextFormatting.DARK_GREEN,
			Effects.POISON, true, false, true).setMaxLevel(4);
	
	
	public static final BasicTag WITHER = new EffectTag("withering", TextFormatting.DARK_GRAY,
			Effects.WITHER, true, false, true).setMaxLevel(2);
	
	
	public static final BasicTag SLOWING = new EffectTag("webbed", TextFormatting.WHITE,
			Effects.SLOWNESS, true, false, true).setMaxLevel(4);
	
	
	public static final BasicTag BLINDING = new EffectTag("blinding", TextFormatting.DARK_PURPLE,
			Effects.BLINDNESS, true, false, true);
	
	public static final BasicTag GLOWING = new EffectTag("glittering", TextFormatting.GOLD,
			Effects.GLOWING, true, false, true);
	
	public static final BasicTag DAMAGE = new EffectTag("blood_thirst", TextFormatting.DARK_RED,
			Effects.INSTANT_DAMAGE, true, false, true).setMaxLevel(4);
	
	
	public static final BasicTag WEAKNESS = new EffectTag("weakening", TextFormatting.GRAY,
			Effects.WEAKNESS, true, false, true).setMaxLevel(2);
	
	
	public static final EffectTag FLOATING = new EffectTag("floating", TextFormatting.AQUA,
			Effects.LEVITATION, true, false, true);
	
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

	
	public static BasicTag upgradeTrait(BasicTag tag) {
		return tag.setLevel(tag.level < tag.maxLevel ? tag.level + 1 : tag.maxLevel);
	}
	
	/**
	 * Converts an NBT tag to a Java tag.
	 * @param tag
	 * @return
	 */
	public static BasicTag convertToTag(CompoundNBT tag) {
		for(BasicTag t : allTags) {
			if(tag.get("tag_name").getString().equals(t.name)) {
				return t.setLevel(tag.getInt("tag_level"));
			}
		}
		
		return null;
	}
	
	/**
	 * Converts tag to NBT to be added to a tool.
	 * @param tag
	 * @return
	 */
	public static CompoundNBT convertToNBT(BasicTag tag) {
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.put("tag_name", StringNBT.valueOf(tag.name));
		nbt.putInt("tag_level", tag.level);
		
		
		return nbt;
		
	}
	
	public static List<BasicTag> getCompatibleTags(ItemStack stack){
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		List<BasicTag> allowedTags = new ArrayList<BasicTag>();
		
		for(BasicTag t: ((IRLTool)stack.getItem()).getAllowedTags()) {
			allowedTags.add(t);
		}
		
		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);		
		
		
		for(int i = 0; i < heldTags.size(); i ++) {
			BasicTag tg = convertToTag(heldTags.getCompound(i));
			for(BasicTag toRemove: tg.incompatibleTags) {
				allowedTags.remove(toRemove);
			}
		}
		
		return allowedTags;
		
	}
	
	public static ItemStack addTag(ItemStack stack, String tagName) {
		for(BasicTag tag: allTags) {
			if(tag.name.equals(tagName)) {
				addTag(stack, tag);
				return stack;
			}
		}
		
		return stack;
	}
	
	public static ItemStack addTag(ItemStack stack, BasicTag tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		
		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);		
		
		
		for(int i = 0 ; i < heldTags.size(); i ++) {
			BasicTag newTag = convertToTag(heldTags.getCompound(i));
			if(newTag.name.equals(tag.name)) {
				TagHelper.removeTag(stack, newTag);
				tag = TagHelper.upgradeTrait(newTag);
			}
		}
		
		
		heldTags.add(convertToNBT(tag));
		
		nbt.put(TAG_LIST, heldTags);
		

		stack.setTag(nbt);
		return stack;
	}
	
	public static ItemStack removeTag(ItemStack stack, String tagName) {
		for(BasicTag tag: allTags) {
			if(tag.name.equals(tagName)) {
				removeTag(stack, tag);
				return stack;
			}
		}
		
		return stack;
	}

	public static ItemStack removeTag(ItemStack stack, BasicTag tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);
		
		CompoundNBT toRemove = null;
		for(int i = 0 ; i < heldTags.size(); i ++) {
			if(convertToTag(heldTags.getCompound(i)).equals(tag)) {
				toRemove = heldTags.getCompound(i);
			}
		}
		
		heldTags.remove(toRemove);
		
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
		
		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);
		
		heldTags.clear();
		

		stack.setTag(nbt);
		return stack;
	}

	/**
	 * Checks item for given tag.
	 * @param stack
	 * @param tag
	 * @return
	 */
	public static boolean checkForTag(ItemStack stack, BasicTag tag) {
		return getTagList(stack).contains(tag.setLevel(0));
	}

	
	/**
	 * Gets all tags from item.
	 * @param stack
	 * @return
	 */
	public static List<BasicTag> getTagList(ItemStack stack) {
		List<BasicTag> tags = new ArrayList<BasicTag>();
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);
		

		for (int i = 0; i < heldTags.size(); i++) {
			CompoundNBT tag = heldTags.getCompound(i);
			
			tags.add(convertToTag(tag));
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
