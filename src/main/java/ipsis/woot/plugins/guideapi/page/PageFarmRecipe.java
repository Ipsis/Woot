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

        if (!itemIngredients.isEmpty()) {
            for (int i = 0; i < itemIngredients.size(); i++) {
                int topOffset = 10 + (30 * (i + 1));
                GuiHelper.drawItemStack(itemIngredients.get(i), guiLeft + 50, guiTop + topOffset);
                guiBase.drawSplitString(itemIngredients.get(i).getDisplayName(), guiLeft + 70, guiTop + topOffset + 5, 60, 0);
            }
        }
    }
}
