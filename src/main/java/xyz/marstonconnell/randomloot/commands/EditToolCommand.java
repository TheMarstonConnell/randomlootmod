package xyz.marstonconnell.randomloot.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.marstonconnell.randomloot.tools.BaseTool;

public class EditToolCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		
		dispatcher.register(Commands.literal("editxp").requires((ctxSrc) -> {
			return ctxSrc.hasPermissionLevel(0);
			
			
		}).then(Commands.argument("value", IntegerArgumentType.integer()).executes((context) -> {
			
			int value = IntegerArgumentType.getInteger(context, "value");
			
			
			
			TranslationTextComponent translationtextcomponent = new TranslationTextComponent("chat.type.announcement",
					context.getSource().getDisplayName(), new StringTextComponent("Adjusting XP by " + value));
			
	        ServerPlayerEntity player = context.getSource().asPlayer();

	        ItemStack stack = player.getHeldItemMainhand();
	        
	        if(stack.getItem() instanceof BaseTool) {
			BaseTool.changeXP(stack, value);
			BaseTool.setLore(stack);
	        }else {
	        	translationtextcomponent = new TranslationTextComponent("chat.type.announcement",
						context.getSource().getDisplayName(), new StringTextComponent(TextFormatting.RED + "Error Adjusting xp - not RandomLoot Tool"));
	        }
	        
	        
			
			
			Entity entity = context.getSource().getEntity();
			if (entity != null) {
				context.getSource().getServer().getPlayerList().func_232641_a_(translationtextcomponent,
						ChatType.CHAT, entity.getUniqueID());
			} else {
				context.getSource().getServer().getPlayerList().func_232641_a_(translationtextcomponent,
						ChatType.SYSTEM, Util.field_240973_b_);
			}
			
			
			
			return 1;
		})));
	}

}