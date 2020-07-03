package xyz.marstonconnell.randomloot.tags;

import net.minecraft.util.text.TextFormatting;

public class BasicTag {
	public String name;
	public TextFormatting color;
	
	public BasicTag(String name, TextFormatting color) {
		this.name = name;
		this.color = color;
		TagHelper.allTags.add(this);
		TagHelper.tagNames.add(name);
	}
}
