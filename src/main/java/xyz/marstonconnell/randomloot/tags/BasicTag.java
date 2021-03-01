package xyz.marstonconnell.randomloot.tags;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import xyz.marstonconnell.randomloot.utils.RomanNumber;

public class BasicTag {
	public String name;
	public TextFormatting color;
	public int level;
	public int maxLevel;
	public List<String> incompatibleTags;
	
	public BasicTag(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
		this.level = 0;
		this.maxLevel = 0;
		this.incompatibleTags = new ArrayList<String>();
		
		TagHelper.allTags.add(this);
		TagHelper.tagNames.add(name);
		TagHelper.tagMap.put(name, this);
		
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
		return newName;
	}


	public BasicTag get() {
		return this;
	}
}
