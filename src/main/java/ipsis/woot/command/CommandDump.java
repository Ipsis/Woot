package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Localization;
import ipsis.woot.util.CommandHelper;
import ipsis.woot.util.WootMobName;
import ipsis.woot.util.WootMobNameBuilder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.List;

public class CommandDump extends CommandTreeBase {

    public CommandDump() {

        addSubcommand(new CommandDumpLoot());
        addSubcommand(new CommandDumpMobs());
        addSubcommand(new CommandDumpStatus());
    }

    @Override
    public String getName() {

        return "dump";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return Localization.TAG_COMMAND + "dump.usage";
    }

    public static class CommandDumpMobs extends CommandBase {

        @Override
        public String getName() {

            return "mobs";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return Localization.TAG_COMMAND + "dump.mobs.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            List<String> mobs = Woot.lootRepository.getAllMobs();
            for (String mob : mobs)
                CommandHelper.display(sender, mob);
        }
    }

    public static class CommandDumpStatus extends CommandBase {

        @Override
        public String getName() {

            return "status";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return Localization.TAG_COMMAND + "dump.status.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        }
    }

    public static class CommandDumpLoot extends CommandBase {

        @Override
        public String getName() {

            return "loot";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return Localization.TAG_COMMAND + "dump.loot.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length < 1)
                throw new WrongUsageException(getUsage(sender));

            WootMobName wootMobName = WootMobNameBuilder.create(args[0]);
            if (wootMobName.isValid())
                LogHelper.info("Dump loot for " + wootMobName);
            else
                LogHelper.info(Localization.INVALID_MOB_NAME);
        }
    }
}
