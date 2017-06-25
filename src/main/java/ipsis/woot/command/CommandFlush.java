package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Localization;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandFlush extends CommandTreeBase {

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

        if (args.length < 1)
            throw new WrongUsageException(getUsage(sender));

        if (args[0].equalsIgnoreCase("all")) {
            Woot.lootRepository.flushAll();
        } else {
            WootMobName wootMobName = WootMobNameBuilder.create(args[0]);
            if (wootMobName.isValid())
                Woot.lootRepository.flushMob(wootMobName);
            else
                LogHelper.info(Localization.INVALID_MOB_NAME);

        }
    }
}
