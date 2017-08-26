package ipsis.woot.command;

import ipsis.woot.reference.Localization;
import ipsis.woot.reference.Reference;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class WootCommand extends CommandTreeBase {

    public WootCommand() {

        addSubcommand(new CommandFlush());
        addSubcommand(new CommandDev());
        addSubcommand(new CommandDump());
        addSubcommand(new CommandGive());
    }

    @Override
    public int getRequiredPermissionLevel() {

        /**
         * 1 - ops can bypass spawn protection
         * 2 - ops can use clear, difficulty, effect, gamemode, gamerule, give, tp
         * 3 - ops can use ban, deop, kick, op
         * 4 - ops can use stop
         */
        return 2;
    }

    @Override
    public String getName() {

        return Reference.MOD_NAME_LOWER;
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return Localization.TAG_COMMAND + "usage";
    }

}
