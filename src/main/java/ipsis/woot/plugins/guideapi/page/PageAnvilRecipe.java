package ipsis.woot.plugins.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.api.util.TextHelper;
import amerifrance.guideapi.gui.GuiBase;
import ipsis.woot.crafting.IAnvilRecipe;
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
        baseItem = recipe.getBaseItem();
        inputItems.addAll(recipe.getInputs());
    }

    @Override
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {

        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "gui/jei/stygiananvil.png"));

        guiBase.drawTexturedModalRect(guiLeft + 42, guiTop + 53, 0, 0, 146, 104);
        guiBase.drawCenteredString(fontRendererObj, TextHelper.localize("guide.woot.page.anvil"), guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        GuiHelper.drawItemStack(outputItem, guiLeft + 130, guiTop + 81);
        GuiHelper.drawItemStack(baseItem, guiLeft + 61, guiTop + 92);

        for (int i = 0; i < inputItems.size(); i++) {
            int topOffset = 54;
            if (i == 3)
                topOffset = 71;
            GuiHelper.drawItemStack(inputItems.get(i), guiLeft + 43 + (18 * (i % 3)), guiTop + topOffset);
        }


    }
}
