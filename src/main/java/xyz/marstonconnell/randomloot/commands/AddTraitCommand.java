package xyz.marstonconnell.randomloot.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import xyz.marstonconnell.randomloot.tags.TagHelper;
import xyz.marstonconnell.randomloot.tools.BaseTool;
import xyz.marstonconnell.randomloot.tools.IRLTool;

public class AddTraitCommand {
	
	

	public static int execute(CommandSource commandSource, String trait) throws CommandSyntaxException {

		ServerPlayerEntity player = commandSource.asPlayer();

		ItemStack stack = player.getHeldItemMainhand();

		if (!(stack.getItem() instanceof IRLTool)) {
			return 0;
		}

		TagHelper.addTag(stack, trait, player.getEntityWorld());

		BaseTool.setLore(stack, player.getEntityWorld());

		return 1;
	}

	public static void register(CommandDispatcher<CommandSource> dispatcher) {

		LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("addtrait")
				.requires((p_198533_0_) -> {
					return p_198533_0_.hasPermissionLevel(2);
				});

		{

			for (String s : TagHelper.tagNames) {
				literalargumentbuilder = literalargumentbuilder
						.then(Commands.literal(s).executes(ctx -> execute(ctx.getSource(), s)));
			}

			dispatcher.register(literalargumentbuilder);

		}
	}

}
