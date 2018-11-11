package ipsis.woot.server.command;

import ipsis.woot.spawning.SpawnManager;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import ipsis.woot.util.helpers.LogHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class CommandCost extends CommandBase {

    @Override
    public String getName() {
        return "cost";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.cost.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 1)
            throw new WrongUsageException(getUsage(sender));

        if (!EntityList.isRegistered(new ResourceLocation(args[0])))
            throw new WrongUsageException("commands.woot.unknownentity");

        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromString(args[0]);
        if (fakeMobKey.isValid()) {
            ResourceLocation rl = fakeMobKey.getResourceLocation();
            Entity entity = EntityList.createEntityByIDFromName(rl, sender.getEntityWorld());
            if (entity instanceof EntityLiving) {
                LogHelper.info("Entity health " + entity.getName() + " " + ((EntityLiving)entity).getHealth());
            }

        }

    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());

        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
