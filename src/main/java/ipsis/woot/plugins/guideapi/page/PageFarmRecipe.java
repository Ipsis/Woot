package ipsis.woot.plugins.guideapi.page;

import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.Page;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.GuiHelper;
import amerifrance.guideapi.gui.GuiBase;
import ipsis.woot.farming.SpawnRecipe;
import ipsis.woot.util.WootMobName;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PageFarmRecipe extends Page {

    public final WootMobName wootMobName;
    public List<ItemStack> itemIngredients = new ArrayList<>();
    public List<FluidStack> fluidIngredients = new ArrayList<>();

    public PageFarmRecipe(WootMobName wootMobName, SpawnRecipe recipe) {

        this.wootMobName = wootMobName;
        itemIngredients.addAll(recipe.getItems());
        fluidIngredients.addAll(recipe.getFluids());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void draw(Book book, CategoryAbstract category, EntryAbstract entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRendererObj) {

        super.draw(book, category, entry, guiLeft, guiTop, mouseX, mouseY, guiBase, fontRendererObj);

        String name = EntityList.getTranslationName(wootMobName.getResourceLocation());
        if (name == null)
            name = wootMobName.getName();

        guiBase.drawCenteredString(fontRendererObj, name, guiLeft + guiBase.xSize / 2, guiTop + 12, 0);

        List<String> tooltip = null;

        int uiLeftOrigin = guiLeft + 42;
        int uiTopOrigin = guiTop + 30;
        for (int i = 0; i < itemIngredients.size(); i++) {
            int rowOffset = i < 5 ? 1 : 19;
            int colOffset = 1 + ((i % 5) * 18);
            GuiHelper.drawItemStack(itemIngredients.get(i), uiLeftOrigin + colOffset, uiTopOrigin + rowOffset);
            if (GuiHelper.isMouseBetween(mouseX, mouseY, uiLeftOrigin + colOffset, uiTopOrigin + rowOffset, 15, 15))
                tooltip = GuiHelper.getTooltip(itemIngredients.get(i));
        }

        uiTopOrigin = guiTop + 70;
        for (int i = 0; i < fluidIngredients.size(); i++) {
            guiBase.drawCenteredString(fontRendererObj, fluidIngredients.get(i).getLocalizedName() + " " + fluidIngredients.get(i).amount + "mb", guiLeft + guiBase.xSize / 2, uiTopOrigin + (12 * i), 0);
        }

        if (tooltip != null)
            guiBase.drawHoveringText(tooltip, mouseX, mouseY);
    }
}
