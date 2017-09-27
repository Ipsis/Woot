package ipsis.woot.plugins.guideapi.book;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import ipsis.Woot;
import ipsis.woot.init.ModBlocks;
import ipsis.woot.init.ModItems;
import ipsis.woot.item.ItemDie;
import ipsis.woot.item.ItemFactoryCore;
import ipsis.woot.item.ItemShard;
import ipsis.woot.plugins.guideapi.page.PageAnvilRecipe;
import ipsis.woot.reference.Reference;
import net.minecraft.item.ItemStack;

public class CategoryAnvil {

    public static void buildCategory(Book book) {

        final String keyBase = "guide." + Reference.MOD_ID + ".entry.anvil.";

        CategoryItemStack category = new CategoryItemStack(keyBase + "anvil", new ItemStack(ModBlocks.blockAnvil));
        category.withKeyBase(keyBase);

        category.addEntry("intro", new Entry(keyBase + "intro", true));
        category.getEntry("intro").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "intro.info")));

        category.addEntry("dies", new Entry(keyBase + "dies", true));
        category.getEntry("dies").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.MESH.getMeta()))));
        category.getEntry("dies").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.MESH.getMeta()))));
        category.getEntry("dies").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.SHARD.getMeta()))));
        category.getEntry("dies").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemDie, 1, ItemDie.EnumDyeType.CORE.getMeta()))));

        category.addEntry("plate", new Entry(keyBase + "plate", true));
        category.getEntry("plate").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemStygianIronPlate))));
        category.getEntry("plate").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "plate.info")));

        category.addEntry("shards", new Entry(keyBase + "shards", true));
        category.getEntry("shards").addPageList(PageHelper.pagesForLongText(TextHelper.localize(keyBase + "shards.info")));
        category.getEntry("shards").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemEnderShard))));
        category.getEntry("shards").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.QUARTZ.getMeta()))));
        category.getEntry("shards").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.DIAMOND.getMeta()))));
        category.getEntry("shards").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.EMERALD.getMeta()))));
        category.getEntry("shards").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemShard, 1, ItemShard.EnumShardType.NETHERSTAR.getMeta()))));

        category.addEntry("dusts", new Entry(keyBase + "dusts", true));
        category.getEntry("dusts").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemSoulSandDust))));
        category.getEntry("dusts").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemStygianIronDust))));

        category.addEntry("cores", new Entry(keyBase + "cores", true));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.CONTROLLER.getMeta()))));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.HEART.getMeta()))));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.T1_UPGRADE.getMeta()))));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.T2_UPGRADE.getMeta()))));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.T3_UPGRADE.getMeta()))));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.POWER.getMeta()))));
        category.getEntry("cores").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModItems.itemFactoryCore, 1, ItemFactoryCore.EnumCoreType.CAP.getMeta()))));

        category.addEntry("controller", new Entry(keyBase + "controller", true));
        category.getEntry("controller").addPage(new PageAnvilRecipe(Woot.anvilManager.getRecipe(new ItemStack(ModBlocks.blockFactoryController))));

        for (EntryAbstract entry : category.entries.values()) {
            for (IPage p : entry.pageList) {
                if (p instanceof Page)
                    ((Page) p).setUnicodeFlag(true);

            }
        }

        book.addCategory(category);
    }
}
