package xyz.marstonconnell.randomloot.init;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import xyz.marstonconnell.randomloot.commands.AddTraitCommand;
import xyz.marstonconnell.randomloot.commands.EditXPCommand;
import xyz.marstonconnell.randomloot.commands.RemoveTraitCommand;

public class RLCommands {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
        

        EditXPCommand.register(dispatcher);
        AddTraitCommand.register(dispatcher);
        RemoveTraitCommand.register(dispatcher);

    }
}
