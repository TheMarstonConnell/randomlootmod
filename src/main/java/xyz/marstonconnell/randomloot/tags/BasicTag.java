package xyz.marstonconnell.randomloot.tags;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.tools.ToolUtilities;
import xyz.marstonconnell.randomloot.utils.Pair;
import xyz.marstonconnell.randomloot.utils.RomanNumber;

public class BasicTag {
	public String name;
	public TextFormatting color;
	public int level;
	public int maxLevel;
	public List<String> incompatibleTags;
	
	public String description;
	
	public boolean offensive = false;
	public boolean forWeapons = false;
	public boolean forTools = false;
	public boolean forArmor = false;
	public boolean forBows = false;
	
	public boolean natural = true;
	
	public boolean active = false;
	
	public boolean enabled = true;
	
	private Map<Integer, Pair<Item, Integer>> levelingMaterials;
	
	Map<String, Float> extraValues;

	public boolean onTagAdded(ItemStack s, World worldIn, PlayerEntity player) {
		if(worldIn == null) {
			return false;
		}
		
		return true;
	}
	
	public BasicTag setDescription(String desc) {
		description = desc;
		return this;
	}

	public BasicTag setUnnatural() {
		natural = false;
		return this;
	}
	
	public BasicTag setActive() {
		this.active = true;
		return this;
	}
	
	public BasicTag setBowTag() {
		forBows = true;
		return this;
	}
	
	public BasicTag addCraftMaterial(Item i, int count, int level) {
		levelingMaterials.put(level, new Pair<Item, Integer>(i, count));
		return this;
	}
	
	public Pair<Boolean, Integer> canMaterialsCauseLevelUp(ItemStack material) {
		
		if(!levelingMaterials.containsKey(this.level + 1)) {
			return new Pair<Boolean, Integer>(false, 0);
		}
		
		
		Pair<Item, Integer> p = levelingMaterials.get(this.level + 1);
		
		
		
		if(p.getLeft().equals(material.getItem())){
			if(p.getRight() <= material.getCount()) {
				return new Pair<Boolean, Integer>(true, p.getRight());
			}
		}		
		
		return new Pair<Boolean, Integer>(false, 0);
	}
	

	
	public BasicTag addValue(String s, float f) {
		extraValues.put(s, f);
		return this;
	}
	
	public float getValue(String s) {
		return extraValues.get(s);
	}
	
	public BasicTag(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
		this.level = 0;
		this.maxLevel = 0;
		this.incompatibleTags = new ArrayList<String>();

		
		TagHelper.allTags.add(this);
		TagHelper.tagNames.add(name);
		TagHelper.tagMap.put(name, this);
		
		extraValues = new HashMap<String, Float>();
		levelingMaterials = new HashMap<Integer, Pair<Item, Integer>>();

	}
	
	public BasicTag setUniversal() {
		return forObjects(true, true, true, true);
	}
	
	public BasicTag forObjects(boolean forWeaponsIn, boolean forToolsIn, boolean forArmorIn, boolean forBowsIn) {
		forWeapons = forWeaponsIn;
		forTools = forToolsIn;
		forArmor = forArmorIn;
		forBows = forBowsIn;
		
		return this;
	}
	
	
	public BasicTag addBlackTags(String ... tags) {
		
		for(String t : tags) {
			
			this.incompatibleTags.add(t);
			
		}
		
		return this;
	}
	
	public BasicTag(BasicTag clone) {
		this.name = clone.name;
		this.color = clone.color;
		this.level = clone.level;
		this.maxLevel = clone.maxLevel;
		this.incompatibleTags = clone.incompatibleTags;
		extraValues = clone.extraValues;
		levelingMaterials = clone.levelingMaterials;
		active = clone.active;
		forArmor = clone.forArmor;
		forTools = clone.forTools;
		forWeapons = clone.forWeapons;
		offensive = clone.offensive;
		forBows = clone.forBows;
		enabled = clone.enabled;


	}
	
	public boolean sameTag(BasicTag tag) {
		return this.name.equals(tag.name);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof BasicTag) {
			
			BasicTag compareTo = (BasicTag) obj;
			
			return compareTo.level == this.level && sameTag(compareTo);
			
		}
		
		return false;
	}
	
	public BasicTag setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
		return this;
	}

	public BasicTag setLevel(int lvlIn) {
		this.level  = lvlIn;
		return this;
	}

	
	
	@Override
	public String toString() {

		
		String newName = this.name.replaceAll("_", " ");
		newName = TagHelper.convertToTitleCaseIteratingChars(newName);
		
		if(this.level >= 1) {
			String roman = RomanNumber.toRoman(this.level + 1);
			newName = newName + " " + roman;
		}
		
		if(!this.enabled) {
			newName = newName + " [disabled]";
		}
		
		return newName;
	}


	public BasicTag get() {
		return this;
	}


	public boolean onTagAdded(ItemStack stack, World worldIn, LivingEntity entityLiving, BlockState state, BlockPos pos,
			LivingEntity target) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public BasicTag setLimited(int y, int m, int d, int i) {
		
		
		
		LocalDate min = LocalDate.of( y , m , d ); 
		
		LocalDate max = LocalDate.of( y , m , d);
		max = max.plusDays(i);
		
		LocalDate date = LocalDate.now();          // the date in question

		
		
		this.enabled = date.isAfter(min) && date.isBefore(max);
		
		
		
		
		return this;
	}
}
