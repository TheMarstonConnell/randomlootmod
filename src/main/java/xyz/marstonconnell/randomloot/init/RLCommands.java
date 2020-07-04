package xyz.marstonconnell.randomloot.init;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import xyz.marstonconnell.randomloot.commands.EditToolCommand;

public class RLCommands {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
        

        EditToolCommand.register(dispatcher);
    }
}
