package ipsis.woot.command;

import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Localization;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandFlush extends CommandBase {

    @Override
    public String getName() {

        return "flush";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return Localization.TAG_COMMAND + "flush.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 2)
            throw new WrongUsageException(getUsage(sender));

        if (args[1].equalsIgnoreCase("all")) {
            LogHelper.info("Flush all mobs");
        } else {
            WootMobName wootMobName = WootMobNameBuilder.create(args[1]);
            if (wootMobName.isValid())
                LogHelper.info("Flush " + wootMobName);
            else
                LogHelper.info(Localization.INVALID_MOB_NAME);

        }
    }
}
