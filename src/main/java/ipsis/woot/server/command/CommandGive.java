package ipsis.woot.server.command;

import ipsis.woot.ModBlocks;
import ipsis.woot.ModItems;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.FakeMobKeyFactory;
import ipsis.woot.util.helpers.ProgrammedMobHelper;
import net.minecraft.command.*;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandGive extends CommandBase {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.woot.give.usage";
    }

    /**
     * woot give <player> {shard|controller} <entity>
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException  {

        if (args.length != 3)
            throw new WrongUsageException(getUsage(sender));

        if (!args[1].equalsIgnoreCase("shard") && !args[1].equalsIgnoreCase("controller"))
            throw new WrongUsageException(getUsage(sender));

        if (!EntityList.isRegistered(new ResourceLocation(args[2])))
            throw new WrongUsageException("commands.woot.unknownentity");

        EntityPlayer entityplayer = getPlayer(server, sender, args[0]);
        FakeMobKey fakeMobKey = FakeMobKeyFactory.createFromString(args[2]);
        if (fakeMobKey.isValid()) {

            ItemStack itemStack;
            if (args[1].equalsIgnoreCase("shard")) {
                itemStack = new ItemStack(ModItems.enderShard, 1);
                ProgrammedMobHelper.programEntity(itemStack, fakeMobKey);
            } else {
                itemStack = new ItemStack(ModBlocks.controllerBlock, 1);
                ProgrammedMobHelper.programEntity(itemStack, fakeMobKey);
            }

            if (ProgrammedMobHelper.isEntityProgrammed(itemStack)) {

                // From the vanilla give command
                boolean added = entityplayer.inventory.addItemStackToInventory(itemStack);
                if (added) {
                    entityplayer.world.playSound(null,
                            entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                            SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,
                            0.2F, ((entityplayer.getRNG().nextFloat() - entityplayer.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    entityplayer.inventoryContainer.detectAndSendChanges();
                }

                /**
                 * We only give the user 1, so the vanilla code to handle !empty is no needed
                 */
                if (added && itemStack.isEmpty()) {
                    itemStack.setCount(1);
                    sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
                    EntityItem entityitem = entityplayer.dropItem(itemStack, false);
                    if (entityitem != null)
                        entityitem.makeFakeItem();
                }
            }

        } else {
            throw new WrongUsageException("commands.woot.invalidmob");
        }

    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        else if (args.length == 3)
            return getListOfStringsMatchingLastWord(args, EntityList.getEntityNameList());

        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }
}
