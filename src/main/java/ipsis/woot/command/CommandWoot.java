package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemPrism;
import ipsis.woot.manager.EnumEnchantKey;
import net.minecraft.command.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;

public class CommandWoot extends CommandBase {
    @Override
    public String getCommandName() {

        return "woot";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.woot.usage";
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
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            throw new WrongUsageException("commands.Woot:woot.usage");
        } else if ("dump".equals(args[0])) {
            dumpTable(sender, args);
        } else if ("flush".equals(args[0])) {
            flushTable(sender, args);
        } else if ("give".equals(args[0])) {
            give(server, sender, args);
        } else {
            throw new WrongUsageException("commands.Woot:woot.usage");
        }
    }

    private void give(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 4)
            throw new WrongUsageException("commands.Woot:woot.usage.give");

        EntityPlayer entityplayer = getPlayer(server, sender, args[1]);
        String wootName = args[2];
        int xp = parseInt(args[3]);

        ItemStack itemstack = ItemPrism.getItemStack(wootName, xp);
        if (itemstack == null)
            return;

        /* straight from the CommandGive code */
        boolean flag = entityplayer.inventory.addItemStackToInventory(itemstack);

        if (flag)
        {
            /* added to the players inventory */
            entityplayer.worldObj.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                    SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((entityplayer.getRNG().nextFloat() - entityplayer.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.inventoryContainer.detectAndSendChanges();
        }

        if (flag && itemstack.stackSize <= 0)
        {
            itemstack.stackSize = 1;
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
            EntityItem entityitem1 = entityplayer.dropItem(itemstack, false);

            if (entityitem1 != null)
            {
                entityitem1.makeFakeItem();
            }
        }
        else
        {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1 - itemstack.stackSize);
            EntityItem entityitem = entityplayer.dropItem(itemstack, false);

            if (entityitem != null)
            {
                entityitem.setNoPickupDelay();
                entityitem.setOwner(entityplayer.getName());
            }
        }

    }

    private void dumpTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2)
            throw new WrongUsageException("commands.Woot:woot.usage.dump");

        String type = args[1];

        if ("table".equals(type))
            Woot.spawnerManager.cmdDumpTable(sender);
        else if ("mobs".equals(type))
            Woot.spawnerManager.cmdDumpMobs(sender);
        else if ("tiers".equals(type))
            Woot.tierMapper.cmdDumpTiers(sender);
        else if ("blacklist".equals(type))
            Woot.mobRegistry.cmdDumpBlacklist(sender);
        else
            throw new WrongUsageException("commands.Woot:woot.usage");
    }

    private void flushTable(ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2 && args.length != 3)
            throw new WrongUsageException("commands.Woot:woot.usage.flush");

        if (args.length == 2) {
            if (args[1].equals("all"))
                Woot.spawnerManager.cmdFlushTables(sender);
            else
                throw new WrongUsageException("commands.Woot:woot.usage.flush");
        } else {

            String mobName = args[1];
            String key = args[2];

            EnumEnchantKey enumEnchantKey;
            if (key.equals(EnumEnchantKey.NO_ENCHANT.toString()))
                enumEnchantKey = EnumEnchantKey.NO_ENCHANT;
            else if (key.equals(EnumEnchantKey.LOOTING_I.toString()))
                enumEnchantKey = EnumEnchantKey.LOOTING_I;
            else if (key.equals(EnumEnchantKey.LOOTING_II.toString()))
                enumEnchantKey = EnumEnchantKey.LOOTING_II;
            else if (key.equals(EnumEnchantKey.LOOTING_III.toString()))
                enumEnchantKey = EnumEnchantKey.LOOTING_III;
            else
                throw new WrongUsageException("commands.Woot:woot.usage.flush");

            Woot.spawnerManager.cmdFlushTableEntry(sender, mobName, enumEnchantKey);
        }
    }
}
