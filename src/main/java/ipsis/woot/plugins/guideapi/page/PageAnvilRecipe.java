package ipsis.woot.plugins.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import ipsis.woot.crafting.IAnvilRecipe;
import ipsis.woot.item.ItemEnderShard;
import ipsis.woot.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PageAnvilRecipe extends Page {

    public final ItemStack outputItem;
    public final ItemStack baseItem;
    public List<ItemStack> inputItems = new ArrayList<>();

    public PageAnvilRecipe(IAnvilRecipe recipe) {

        outputItem = recipe.getCopyOutput();
        baseItem = recipe.getBaseItem().copy();
        if (ItemEnderShard.isEnderShard(baseItem))
            ItemEnderShard.setJEIEnderShared(baseItem);

        inputItems.addAll(recipe.getInputs());
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {

        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/jei/stygiananvil.png"));

        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 146, 104);
        guiBase.drawCenteredString(fontRendererObj, TextHelper.localize("guide.woot.page.anvil"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        int uiLeftOrigin = guiLeft + 42;
        int uiTopOrigin = guiTop + 53;
        List<String> tooltip = null;

        GuiHelper.drawItemStack(outputItem, uiLeftOrigin + 88, uiTopOrigin + 29);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, uiLeftOrigin + 88, uiTopOrigin + 29, 15, 15))
            tooltip = GuiHelper.getTooltip(outputItem);

        GuiHelper.drawItemStack(baseItem, uiLeftOrigin + 19, uiTopOrigin + 39);
        if (GuiHelper.isMouseBetween(mouseX, mouseY, uiLeftOrigin + 19, uiTopOrigin + 39, 15, 15))
            tooltip = GuiHelper.getTooltip(baseItem);

        for (int i = 0; i < inputItems.size(); i++) {
            int rowOffset = i < 3 ? 1 : 19;
            int colOffset = 1 + ((i % 3) * 18);
            GuiHelper.drawItemStack(inputItems.get(i), uiLeftOrigin + colOffset, uiTopOrigin + rowOffset);
            if (GuiHelper.isMouseBetween(mouseX, mouseY, uiLeftOrigin + colOffset, uiTopOrigin + rowOffset, 15, 15))
                tooltip = GuiHelper.getTooltip(inputItems.get(i));
        }

        if (tooltip != null)
            guiBase.drawHoveringText(tooltip, mouseX, mouseY);
    }
}
