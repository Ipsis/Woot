package ipsis.woot.server.command;

import ipsis.Woot;
import ipsis.woot.drops.MobDropData;
import ipsis.woot.util.*;
import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class CommandLoot extends CommandBase {

    @Override
    public String getName() { return "loot"; }

    @Override
    public String getUsage(ICommandSender sender) { return "commands.woot.loot.usage"; }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2)
            throw new WrongUsageException(getUsage(sender));

        if (!EntityList.isRegistered(new ResourceLocation(args[0])))
            throw new WrongUsageException("commands.woot.unknownentity");

        int lootLevel = 0;
        try {
            lootLevel = Integer.parseInt(args[1]);
            if (lootLevel < 0 || lootLevel > 3)
                throw new WrongUsageException(getUsage(sender));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(getUsage(sender));
        }

        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromString(args[0]);
        if (fakeMobKey.isValid()) {
            MobDropData mobDropData = Woot.DROP_MANAGER.getMobDropData(fakeMobKey, lootLevel);
            LogHelper.info("loot: " + fakeMobKey);
            for (MobDropData.DropData dropData : mobDropData.getDrops())
                LogHelper.info(dropData.toString());
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());

        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
