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
	public List<BasicTag> incompatibleTags;
	
	public BasicTag(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
		TagHelper.allTags.add(this);
		TagHelper.tagNames.add(name);
		this.level = 0;
		this.maxLevel = 1;
		this.incompatibleTags = new ArrayList<BasicTag>();
		
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
		
		String roman = RomanNumber.toRoman(this.level);
		if(this.level > 1) {
			newName = newName + " " + roman;
		}
		return newName;
	}
}
