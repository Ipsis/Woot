package ipsis.woot.server.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class WootCommand extends CommandTreeBase {

    public WootCommand() {
        super.addSubcommand(new CommandDebug());
        super.addSubcommand(new CommandTeleport());
        super.addSubcommand(new CommandLearn());
        super.addSubcommand(new CommandFlush());
        super.addSubcommand(new CommandGive());
        super.addSubcommand(new CommandStatus());
        super.addSubcommand(new CommandLoot());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getName() {
        return "woot";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.usage";
    }
}
