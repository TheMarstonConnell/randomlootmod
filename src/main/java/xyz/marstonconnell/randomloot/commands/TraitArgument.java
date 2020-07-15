package xyz.marstonconnell.randomloot.commands;

import java.util.Collection;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.util.text.StringTextComponent;
import xyz.marstonconnell.randomloot.tags.TagHelper;

public class TraitArgument implements ArgumentType<String>{
	private SimpleCommandExceptionType INVALID_TRAIT_EXCEPTION = new SimpleCommandExceptionType(new StringTextComponent("Not a valid trait."));

	
	public static TraitArgument trait() {
		return new TraitArgument();
	}
	
	@Override
	public String parse(StringReader reader) throws CommandSyntaxException {
		final String input = reader.readString();
		
		if(TagHelper.tagNames.contains(input.trim())) {
			
		}else {
			
			
			
			throw INVALID_TRAIT_EXCEPTION.create();
		}
		
		
		return input;
	}
	
	
	
	
	public Collection<String> getExamples() {
		return TagHelper.tagNames;
	}

}
