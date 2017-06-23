package ipsis.woot.command;

import ipsis.Woot;
import ipsis.woot.item.ItemPrism;
import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.reference.Reference;
import net.minecraft.command.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Valid commands are:
 *
 * woot dev {true|false}
 * woot flush {all|<mob>}
 * woot give
 * woot status
 * woot mobs
 * woot loot <mob>
 * woot blacklist
 * woot tiers
 * woot prism
 * woot cost
 */
public class CommandWoot extends CommandBase {
    @Override
    public String getName() {

        return Reference.MOD_NAME_LOWER + "_old";
    }

    @Override
    public String getUsage(ICommandSender sender) {
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

        if (args.length == 0)
            throw new WrongUsageException("commands.woot:woot.usage");
        else if ("dev".equals(args[0]))
            dev(server, sender,args);
        else if ("flush".equals(args[0]))
            flush(sender, args);
        else if ("give".equals(args[0]))
            give(server, sender, args);
        else if ("status".equals(args[0]))
            Woot.LOOT_TABLE_MANAGER.dumpStatus(sender);
        else if ("mobs".equals(args[0]))
            Woot.LOOT_TABLE_MANAGER.dumpMobs(sender);
        else if ("loot".equals(args[0]))
            loot(server, sender, args);
        else if ("blacklist".equals(args[0]))
            Woot.LOOT_TABLE_MANAGER.dumpBlacklist(sender);
        else if ("prism".equals(args[0]))
            Woot.mobRegistry.cmdDumpPrism(sender);
        else if ("cost".equals(args[0]))
            Woot.mobRegistry.cmdDumpCosts(sender);
        else
            throw new WrongUsageException("commands.woot:woot.usage");
    }

    private void dev(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 2)
            throw new WrongUsageException("commands.woot:woot.usage");

        boolean b = false;
        String v = args[1];
        if (v.equalsIgnoreCase("true"))
            b = true;
        else if (v.equalsIgnoreCase("false"))
            b = false;
        else
            throw new WrongUsageException("commands.woot:woot.usage");

        Woot.devMode = b;
        sender.sendMessage(new TextComponentTranslation("commands.woot:woot.dev.summary", v.toLowerCase()));
    }

    private void flush(ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 2)
            throw new WrongUsageException("commands.woot:woot.usage");

        String mobName = args[1];

        if ("all".equals(mobName))
            Woot.LOOT_TABLE_MANAGER.flushAllMobs(sender);
        else {
            Woot.LOOT_TABLE_MANAGER.flushMob(sender, mobName, EnumEnchantKey.NO_ENCHANT);
            Woot.LOOT_TABLE_MANAGER.flushMob(sender, mobName, EnumEnchantKey.LOOTING_I);
            Woot.LOOT_TABLE_MANAGER.flushMob(sender, mobName, EnumEnchantKey.LOOTING_II);
            Woot.LOOT_TABLE_MANAGER.flushMob(sender, mobName, EnumEnchantKey.LOOTING_III);
        }
    }

    private void give(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length != 4)
            throw new WrongUsageException("commands.woot:woot.usage");

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
            entityplayer.world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                    SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((entityplayer.getRNG().nextFloat() - entityplayer.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.inventoryContainer.detectAndSendChanges();
        }

        if (flag && itemstack.isEmpty())
        {
            itemstack.setCount(1);
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
            EntityItem entityitem1 = entityplayer.dropItem(itemstack, false);

            if (entityitem1 != null)
            {
                entityitem1.makeFakeItem();
            }
        }
        else
        {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1 - itemstack.getCount());
            EntityItem entityitem = entityplayer.dropItem(itemstack, false);

            if (entityitem != null)
            {
                entityitem.setNoPickupDelay();
                entityitem.setOwner(entityplayer.getName());
            }
        }

    }

    private void loot(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length < 2)
            throw new WrongUsageException("commands.woot:woot.usage");

        String mobName = args[1];
        boolean detail = false;
        if (args.length == 3 && args[2].equals("detail"))
            detail = true;

        Woot.LOOT_TABLE_MANAGER.dumpDrops(sender, mobName, detail);
    }
}
