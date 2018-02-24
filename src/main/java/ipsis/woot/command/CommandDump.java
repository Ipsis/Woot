package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.multiblock.EnumMobFactoryModule;
import ipsis.woot.multiblock.EnumMobFactoryTier;
import ipsis.woot.multiblock.FactoryPatternRepository;
import ipsis.woot.util.CommandHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

import java.util.ArrayList;
import java.util.List;


public class CommandDump extends CommandTreeBase {

    public CommandDump() {

        addSubcommand(new CommandDumpLoot());
        addSubcommand(new CommandDumpMobs());
        addSubcommand(new CommandDumpTartarus());
        addSubcommand(new CommandDumpLearning());
        addSubcommand(new CommandDumpPolicy());
        addSubcommand(new CommandDumpStructure());
    }

    @Override
    public String getName() {

        return "dump";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return "commands.woot.dump.usage";
    }

    public static class CommandDumpMobs extends CommandBase {

        @Override
        public String getName() {

            return "mobs";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dump.mobs.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            // Shows the mobs that the respository knows about
            List<String> mobs = Woot.lootRepository.getAllMobs();
            for (String mob : mobs)
                CommandHelper.display(sender, mob);

            mobs = Woot.customDropsRepository.getAllMobs();
            for (String mob : mobs)
                CommandHelper.display(sender, mob);
        }
    }

    public static class CommandDumpStructure extends CommandBase {

        @Override
        public String getName() {

            return "structure";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dump.structure.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) {

            for (EnumMobFactoryTier tier : EnumMobFactoryTier.VALID_TIERS) {
                StringBuilder sb = new StringBuilder();
                for (EnumMobFactoryModule m : EnumMobFactoryModule.VALUES) {

                    int c = Woot.factoryPatternRepository.getBlockCount(tier, m);
                    if (c > 0) {
                        String s = m.getName() + ":" + c + " ";
                        sb.append(s);
                    }
                }

                String s = tier.getTranslated("%s") + " " + sb.toString();
                CommandHelper.display(sender, s);
            }
        }
    }

    public static class CommandDumpTartarus extends CommandBase {

        @Override
        public String getName() {

            return "tartarus";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dump.tartarus.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length != 0)
                throw new WrongUsageException(getUsage(sender));

            List<ITextStatus> status = new ArrayList<>();
            status.add(Woot.wootDimensionManager);
            status.add(Woot.tartarusManager);

            for (ITextStatus status1 : status) {
                for (String s : status1.getStatus())
                    CommandHelper.display(sender, s);
                for (String s : status1.getStatus(server.getWorld(Woot.wootDimensionManager.getDimensionId())))
                    CommandHelper.display(sender, s);
            }
        }
    }

    public static class CommandDumpLearning extends CommandBase {

        @Override
        public String getName() {

            return "learning";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dump.learning.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length != 0)
                throw new WrongUsageException(getUsage(sender));

            for (String s : Woot.lootRepository.getStatus())
                CommandHelper.display(sender, s);
        }
    }

    public static class CommandDumpLoot extends CommandBase {

        @Override
        public String getName() {

            return "loot";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dump.loot.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        }
    }

    public static class CommandDumpPolicy extends CommandBase {

        @Override
        public String getName() {

            return "policy";
        }

        @Override
        public String getUsage(ICommandSender sender) {

            return "commands.woot.dump.policy.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

            if (args.length < 2)
                throw new WrongUsageException(getUsage(sender));

            boolean internal;
            if (args[0].equalsIgnoreCase("ext"))
                internal = false;
            else if (args[0].equalsIgnoreCase("int"))
                internal = true;
            else
                throw new WrongUsageException(getUsage(sender));

            if (args[1].equalsIgnoreCase("mob")) {
                List<String> mobList = Woot.policyRepository.getEntityList(internal);
                StringBuilder sb = new StringBuilder();
                for (String s : mobList)
                    sb.append(s).append(" ");

                CommandHelper.display(sender, sb.toString());

            } else if (args[1].equalsIgnoreCase("item")) {
                List<String> mobList = Woot.policyRepository.getItemList(internal);
                StringBuilder sb = new StringBuilder();
                for (String s : mobList)
                    sb.append(s).append(" ");

                CommandHelper.display(sender, sb.toString());

            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        }
    }
}
