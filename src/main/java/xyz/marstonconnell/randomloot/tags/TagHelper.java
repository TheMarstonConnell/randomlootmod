package xyz.marstonconnell.randomloot.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import xyz.marstonconnell.randomloot.tags.StatBoost.LowDurabilityBoostEvent;
import xyz.marstonconnell.randomloot.tags.StatBoost.WeatherBoostEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.AutoSmeltEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.BeanStalkEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.BeeSummonEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.BurningEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.ChargingEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.CriticalStrikeEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.DamageEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.ExplosionEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.FindEntitiesEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.FloatEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.GangBangEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.InstaKillEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.LaserArrowEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.LightBoostEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.LongLeggedEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.LowDurabilityAttackEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.MultiBreakEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.MultiShotEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.OreFindEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.PlaceLightEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.RaisingEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.ReplenishEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.SleepingEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.TeleportItemsEvent;
import xyz.marstonconnell.randomloot.tags.worldinteract.UnbreakingEvent;
import xyz.marstonconnell.randomloot.tools.ToolUtilities;
import xyz.marstonconnell.randomloot.utils.Config;
import xyz.marstonconnell.randomloot.tools.IRLTool;

/**
 * A static class that handles all tag functions (add/remove/search, etc.)
 * @author marston connell
 *
 */
public abstract class TagHelper {	

	private static final String TAG_LIST = "rl_tag_data";
	private static final String TAG_LEVEL = "tag_level";
	private static final String TAG_NAME = "tag_name";

	public static List<BasicTag> allTags = new ArrayList<BasicTag>();
	public static List<String> tagNames = new ArrayList<String>();
	
	/**
	 * Holds all tags by name.
	 */
	public static HashMap<String, BasicTag> tagMap = new HashMap<String, BasicTag>();

	/**
	 * Any error will produce a null tag instead of Java's null.
	 */
	public static final BasicTag NULL_TAG;
	
	// HELPFUL EFFECTS
	public static final BasicTag SPEED;
	public static final BasicTag JUMP;
	public static final BasicTag RESISTANCE;
	public static final BasicTag FIRE_RESISTANCE;
	public static final BasicTag HASTE;
	public static final BasicTag LUCK;
	public static final BasicTag STRENGTH;
	public static final BasicTag NIGHT_VISION;
	public static final BasicTag WATER_BREATHING;
	public static final BasicTag REGENERATION;

	// ATTACKING EFFECTS
	public static final BasicTag POISON;
	public static final BasicTag WITHER;
	public static final BasicTag SLOWING;
	public static final BasicTag BLINDING;
	public static final BasicTag GLOWING;
	public static final BasicTag DAMAGE;
	public static final BasicTag WEAKNESS;
	public static final EffectTag FLOATING;

	// WORLD INTERACT EFFECTS
	public static final BasicTag EXPLOSION;
	public static final BasicTag REPLENISH;
	public static final BasicTag TELEPORT_ITEMS;
	public static final BasicTag FIND_ENTITIES;
	public static final BasicTag CLOUD_WALKER;
	public static final BasicTag GIANT_SLAYER;
	public static final BasicTag CRITICAL_STRIKE;
	public static final BasicTag INSTA_KILL;
	public static final BasicTag MOB_RAISE;
	public static final BasicTag HAMMER_MODE;
	public static final BasicTag SUN_BEAMS;
	public static final BasicTag RAINY_DAY;
	public static final BasicTag GROUP_HARM;
	public static final BasicTag DAMAGE_BOOST;
	public static final BasicTag DAMAGE_DEAL;
	public static final BasicTag BEANSTALK;
	public static final BasicTag BEE_KEEPER;
	public static final BasicTag ORE_SIGHT;
	public static final BasicTag CHARGING;
	public static final BasicTag STEPPING;
	public static final BasicTag BURNING;
	public static final BasicTag UNBREAKING;
	public static final BasicTag AUTOSMELT;
	public static final BasicTag MULTI_SHOT;
	public static final BasicTag LASER_ARROW;
	public static final BasicTag PLACE_LIGHT;
	public static final BasicTag LOCKED;
	//public static final BasicTag SLEEPING;

	/**
	 * Every tags assignment.
	 */
	static {
		NULL_TAG = new BasicTag("", TextFormatting.WHITE);
		
		SPEED = new EffectTag("speedy", TextFormatting.AQUA, Effects.SPEED, false, false, true).setMaxLevel(2);

		JUMP = new EffectTag("bouncy", TextFormatting.GREEN, Effects.JUMP_BOOST, false, false, false).setMaxLevel(1).addBlackTags("cloud_walker");

		RESISTANCE = new EffectTag("resistant", TextFormatting.GRAY, Effects.RESISTANCE, false, false, false)
				.setMaxLevel(3);

		FIRE_RESISTANCE = new EffectTag("fire_resistant", TextFormatting.YELLOW, Effects.FIRE_RESISTANCE, false, false,
				false).setMaxLevel(1);

		HASTE = new EffectTag("hastey", TextFormatting.YELLOW, Effects.HASTE, false, true, false).setMaxLevel(1);

		LUCK = new EffectTag("lucky", TextFormatting.GREEN, Effects.LUCK, false, true, false);

		STRENGTH = new EffectTag("strong", TextFormatting.DARK_RED, Effects.STRENGTH, false, false, true)
				.setMaxLevel(4);

		NIGHT_VISION = new EffectTag("insightful", TextFormatting.BLUE,
				new EffectInstance(Effects.NIGHT_VISION, 500, 0), false, false, false);

		WATER_BREATHING = new EffectTag("deep_breathing", TextFormatting.DARK_BLUE, Effects.WATER_BREATHING, false,
				false, false).setMaxLevel(1);

		REGENERATION = new EffectTag("regenerating", TextFormatting.RED, Effects.REGENERATION, false, false, true)
				.setMaxLevel(2);

		POISON = new EffectTag("poisonous", TextFormatting.DARK_GREEN, Effects.POISON, true, false, true).setMaxLevel(3)
				.addBlackTags("withering");

		WITHER = new EffectTag("withering", TextFormatting.DARK_GRAY, Effects.WITHER, true, false, true).setMaxLevel(1).addBlackTags("poisonous");

		SLOWING = new EffectTag("webbed", TextFormatting.WHITE, Effects.SLOWNESS, true, false, true).setMaxLevel(3).addCraftMaterial(Items.STRING, 10, 0).addCraftMaterial(Items.STRING, 10, 1).addCraftMaterial(Items.STRING, 15, 2).addCraftMaterial(Items.STRING, 20, 3);

		BLINDING = new EffectTag("blinding", TextFormatting.DARK_PURPLE, Effects.BLINDNESS, true, false, true);

		GLOWING = new EffectTag("glittering", TextFormatting.GOLD, Effects.GLOWING, true, false, true);

		DAMAGE = new EffectTag("blood_thirst", TextFormatting.DARK_RED, Effects.INSTANT_DAMAGE, true, false, true)
				.setMaxLevel(3);

		WEAKNESS = new EffectTag("weakening", TextFormatting.GRAY, Effects.WEAKNESS, true, false, true).setMaxLevel(1);

		FLOATING = new EffectTag("floating", TextFormatting.AQUA, Effects.LEVITATION, true, false, true);

		// WORLD INTERACT EFFECTS
		EXPLOSION = new WorldInteractTag(new String[] { "explosive" }, TextFormatting.RED, new ExplosionEvent(), true,
				false, false).addBlackTags("phasing", "sun_bathing", "auto-smelt").addCraftMaterial(Items.TNT, 1, 0);
		
		REPLENISH = new WorldInteractTag(new String[] { "filling" }, TextFormatting.DARK_GREEN, new ReplenishEvent(),
				true, false, true).addBlackTags("fortified");
		
		TELEPORT_ITEMS = new WorldInteractTag(new String[] { "phasing" }, TextFormatting.AQUA, new TeleportItemsEvent(),
				true, false, false).addBlackTags("explosive");
		
		FIND_ENTITIES = new WorldInteractTag(new String[] { "soul_searching" }, TextFormatting.YELLOW,
				new FindEntitiesEvent(), false, true, false);
		CLOUD_WALKER = new WorldInteractTag(new String[] { "cloud_walker" }, TextFormatting.YELLOW, new FloatEvent(),
				false, true, false).addBlackTags("bouncy");

		GIANT_SLAYER = new WorldInteractTag(new String[] { "giant_slayer", "titan_slayer" }, TextFormatting.BLUE,
				new DamageEvent(), false, false, true).setMaxLevel(1).addBlackTags("sharper_edge", "enraged");

		CRITICAL_STRIKE = new WorldInteractTag(new String[] { "sharper_edge", "gilded_edge", "infinity_edge" },
				TextFormatting.YELLOW, new CriticalStrikeEvent(), false, false, true).setMaxLevel(2).addBlackTags("enraged", "giant_slayer");

		INSTA_KILL = new WorldInteractTag(new String[] { "enraged", "exasperating", "exodian" },
				TextFormatting.DARK_BLUE, new InstaKillEvent(), false, false, true).setMaxLevel(2).addBlackTags("sharper_edge", "giant_slayer");

		MOB_RAISE = new WorldInteractTag(new String[] { "taming", "automotonizing" }, TextFormatting.DARK_GREEN,
				new RaisingEvent(), false, false, true).setMaxLevel(1).addBlackTags("hailey's_wrath", "taming");

		UNBREAKING = new WorldInteractTag(new String[]{"fortified", "reinforced", "plated", "everlasting"}, TextFormatting.BLUE, new UnbreakingEvent(), true, true, true).addBlackTags("filling", "excavating").setMaxLevel(3);
		
		AUTOSMELT = new WorldInteractTag(new String[]{"auto_smelt"}, TextFormatting.DARK_RED, new AutoSmeltEvent(), true, false, false).addBlackTags("excavating", "explosive").addCraftMaterial(Items.BLAZE_ROD, 32, 0);
		
		HAMMER_MODE = new WorldInteractTag(new String[]{"excavating", "world_breaking"}, TextFormatting.DARK_BLUE, new MultiBreakEvent(), true, false, false).setMaxLevel(1).addBlackTags("fortified", "explosive", "auto-smelt");
		
		SUN_BEAMS = new WorldInteractTag(new String[] {"sun_bathing"}, TextFormatting.YELLOW, new LightBoostEvent(), true, false, true);
		
		RAINY_DAY = new StatBoostTag(new String[] {"rained_out"}, TextFormatting.DARK_AQUA, new WeatherBoostEvent(), true, false, false);
		
		GROUP_HARM = new WorldInteractTag(new String[] {"crowd_pleaser"}, TextFormatting.DARK_PURPLE, new GangBangEvent(), false, false, true);
		
		DAMAGE_BOOST = new StatBoostTag(new String[] {"sturdy"}, TextFormatting.GOLD, new LowDurabilityBoostEvent(), true, false, false);
		
		DAMAGE_DEAL = new WorldInteractTag(new String[] {"fierce", "feisty"}, TextFormatting.RED, new LowDurabilityAttackEvent(), false, false, true).setMaxLevel(1);
		
		BEANSTALK = new WorldInteractTag(new String[] {"stalky"}, TextFormatting.GREEN, new BeanStalkEvent(), false, false, true).addBlackTags("hailey's_wrath", "taming");
		
		BEE_KEEPER = new WorldInteractTag(new String[] {"hailey's_wrath"}, TextFormatting.YELLOW, new BeeSummonEvent(), false, false, true).addBlackTags("taming", "stalky");
		
		ORE_SIGHT = new WorldInteractTag(new String[] {"ore_sight"}, TextFormatting.AQUA, new OreFindEvent(), true, false, false);
		
		ChargingEvent ce = new ChargingEvent();
		
		CHARGING = new WorldInteractTag(new String[] {"charged"}, TextFormatting.AQUA, ce, false, false, true).addValue("rl_charge", 0.0f);
		
		STEPPING = new WorldInteractTag(new String[] {"long_legged", "elastic_legs", "stilted"}, TextFormatting.DARK_GREEN, new LongLeggedEvent(), false, true, false).setMaxLevel(2);
		
		BURNING = new WorldInteractTag(new String[] {"fire_storm", "solar_flare"}, TextFormatting.RED, new BurningEvent(), false, true, false).setMaxLevel(1);
	
		MULTI_SHOT = new WorldInteractTag(new String[] {"twin_shooter", "triple_shot"}, TextFormatting.DARK_BLUE, new MultiShotEvent(), false, false, false).setBowTag().setMaxLevel(1);
	
		LASER_ARROW = new WorldInteractTag(new String[] {"end_arrows"}, TextFormatting.LIGHT_PURPLE, new LaserArrowEvent(), false, false, false).setBowTag();
	
		//SLEEPING = new WorldInteractTag(new String[] {"drowsy"}, TextFormatting.RED, new SleepingEvent(), true, false, true).setActive();
		
		PLACE_LIGHT = new WorldInteractTag(new String[] {"illuminated"}, TextFormatting.YELLOW, new PlaceLightEvent(), true, false, false).setActive();
	
		LOCKED = new BasicTag("locked", TextFormatting.WHITE).addCraftMaterial(Items.NETHER_STAR, 1, 0).setUniversal().setUnnatural();
	
	}
	
	public static void finalizeActiveTraits() {
		for(BasicTag t : allTags) {
			
			if(!t.equals(LOCKED)) {
				LOCKED.addBlackTags(t.name);
			}
			
			if(t.active) {
				for(BasicTag t2 : allTags) {
					if(t2.active) {
						if(t2 != t) {
							t.addBlackTags(t2.name);
						}
					}
				}
			}
		}
		
		
	}

	/**
	 * Removes tag from list of tags without considering level.
	 * @param list
	 * @param toRemove
	 */
	public static void removeTagFromList(List<BasicTag> list, BasicTag toRemove) {
		int i = 0;
		for(i = i; i < list.size(); i ++) {
			if(list.get(i).sameTag(toRemove)) {
				break;
			}
		}
		list.remove(i);
		return;
	}
	
	/**
	 * Loops through a list to call removeTagFromList using every tag in the toRemove list.
	 * @param list
	 * @param toRemove
	 */
	public static void removeTagsFromList(List<BasicTag> list, List<BasicTag> toRemove) {
		for(BasicTag t : toRemove) {
			removeTagFromList(list, t);
		}
		
		return;
	}
	
	/**
	 * Converts an NBT tag to a Java tag.
	 * 
	 * @param tag
	 * @return
	 */
	public static BasicTag convertToTag(CompoundNBT tag) {
		
		Set<String> keys = tag.keySet();
		
		for (BasicTag t : allTags) {
			if (tag.get("tag_name").getString().equals(t.name)) {
				if (t instanceof EffectTag) {
					t = new EffectTag((EffectTag) t);
				} else if (t instanceof WorldInteractTag) {
					t = new WorldInteractTag((WorldInteractTag) t);
				
				} else if(t instanceof StatBoostTag){
					t = new StatBoostTag((StatBoostTag) t);
				}else {
					t = new BasicTag(t);
				}

				for(String s : keys) {
					if(! (s.equals(TAG_LEVEL) || s.equals(TAG_NAME))) {
						t.addValue(s, tag.getFloat(s));
					}
				}
				
				
				return t.setLevel(tag.getInt(TAG_LEVEL));
			}
		}

		return new BasicTag(NULL_TAG);
	}

	/**
	 * Converts tag to NBT to be added to a tool.
	 * 
	 * @param tag
	 * @return
	 */
	public static CompoundNBT convertToNBT(BasicTag tag) {
		CompoundNBT nbt = new CompoundNBT();

		nbt.put(TAG_NAME, StringNBT.valueOf(tag.name));
		nbt.putInt(TAG_LEVEL, tag.level);
		
		
		Set<String> keys = tag.extraValues.keySet();
		for(String s : keys) {
			nbt.putFloat(s, tag.extraValues.get(s));
		}

		return nbt;

	}

	/**
	 * Returns a list of tags that the tool accepts based on the individual tools style.
	 * @param stack
	 * @return
	 */
	public static List<BasicTag> getCompatibleTags(ItemStack stack) {
		
		if(!(stack.getItem() instanceof IRLTool)) {
			return allTags;
		}
		
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		List<BasicTag> allowedTags = new ArrayList<BasicTag>();

		for (BasicTag t : ((IRLTool) stack.getItem()).getAllowedTags()) {
			allowedTags.add(t);
		}

		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);

		for (int i = 0; i < heldTags.size(); i++) {
			BasicTag tg = convertToTag(heldTags.getCompound(i));
			for (String toRemove : tg.incompatibleTags) {
				
				allowedTags.remove(tagMap.get(toRemove));
				
			}
		}

		return allowedTags;

	}

	/**
	 * Adds a tag to the given tool using the tags name.
	 * @param stack
	 * @param tagName
	 * @param worldIn
	 * @return
	 */
	public static ItemStack addTag(ItemStack stack, String tagName, World worldIn) {
	
		addTag(stack, tagMap.get(tagName), worldIn);
		
		return stack;
	}

	/**
	 * Adds a tag to the given item using the tags object in memory.
	 * @param stack
	 * @param tag
	 * @param worldIn
	 * @return
	 */
	public static ItemStack addTag(ItemStack stack, BasicTag tag, World worldIn) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}
		
		if(!Config.traitsEnabled.get(tag.name).get()) {
			return stack;
		}
	
		

		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);
		ListNBT newList = new ListNBT();

		boolean upgraded = false;
		for (int i = 0; i < heldTags.size(); i++) {

			BasicTag newTag = convertToTag(heldTags.getCompound(i));
			CompoundNBT tagAsNBT = heldTags.getCompound(i);

			System.out.println("Tool already has: " + newTag.name + " at level " + newTag.level);

			if (newTag.sameTag(tag)) {

				int lvl = newTag.level;
				if (newTag.level < newTag.maxLevel) {
					lvl++;
				}

				tagAsNBT.putInt(TAG_LEVEL, lvl);

				upgraded = true;

			}

			
			newList.add(tagAsNBT);

		}

		if (!upgraded) {
			
			if(getCompatibleTags(stack).contains(tag)) {
				tag.onTagAdded(stack, worldIn, null, null, null, null);
				newList.add(convertToNBT(tag));
			}
			
		}

		nbt.put(TAG_LIST, newList);

		stack.setTag(nbt);
		return stack;
	}

	/**
	 * Removes tag from tool using the tags name.
	 * @param stack
	 * @param tagName
	 * @return
	 */
	public static ItemStack removeTag(ItemStack stack, String tagName) {
		for (BasicTag tag : allTags) {
			if (tag.name.equals(tagName)) {
				removeTag(stack, tag);
				return stack;
			}
		}

		return stack;
	}

	/**
	 * Removes a tag from the tool using the tag in memory.
	 * @param stack
	 * @param tag
	 * @return
	 */
	public static ItemStack removeTag(ItemStack stack, BasicTag tag) {
		CompoundNBT nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new CompoundNBT();
		}

		
		ListNBT heldTags = nbt.getList(TAG_LIST, NBT.TAG_COMPOUND);
		ListNBT newList = new ListNBT();

		for (int i = 0; i < heldTags.size(); i++) {
			if (!convertToTag(heldTags.getCompound(i)).sameTag(tag)) {
				newList.add(heldTags.getCompound(i));
			}
		}

		nbt.put(TAG_LIST, newList);

		stack.setTag(nbt);
		return stack;
	}

	/**
	 * Removes every tag currently attached to the itemstack.
	 * @param stack
	 * @return
	 */
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
	 * 
	 * @param stack
	 * @param tag
	 * @return
	 */
	public static boolean checkForTag(ItemStack stack, BasicTag tag) {
		List<BasicTag> tags = getTagList(stack);
		for(BasicTag t : tags) {
			if(t.sameTag(tag)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Gets all tags from item.
	 * 
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
	
	/**
	 * Clones tag.
	 * @param t
	 * @return
	 */
	public static BasicTag copyTag(BasicTag t) {
		return convertToTag(convertToNBT(t));
	}

	/**
	 * Converts a tools name to title case. 
	 * <br><br>
	 * Example: "trait_name" -> "Trait Name"
	 * @param text
	 * @return
	 */
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
	
	public static void main(String[] args) {
		
	}

}
