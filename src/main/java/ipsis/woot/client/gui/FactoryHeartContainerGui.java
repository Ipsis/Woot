package ipsis.woot.client.gui;

import ipsis.woot.client.gui.element.ElementBase;
import ipsis.woot.client.gui.element.ElementDualStackRows;
import ipsis.woot.client.gui.element.ElementStackBox;
import ipsis.woot.client.gui.element.ElementTextBox;
import ipsis.woot.client.gui.inventory.FactoryHeartContainer;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.packets.PacketGetFarmInfo;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FactoryHeartContainerGui extends GuiContainerWoot {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 224;
    private TileEntityMobFactoryHeart te;

    private static final ResourceLocation bg = new ResourceLocation(Reference.MOD_ID, "textures/gui/heart.png");
    private List<ElementBase> elementBaseList = new ArrayList<>();
    private ElementTextBox recipeElement;
    private ElementDualStackRows ingredientElement;
    private ElementStackBox dropsElement;
    private boolean populated = false;

    @Override
    public void initGui() {
        super.initGui();

        requestFarmInfo();

        int recipeHeight = 80;
        int ingredientHeight = 60;
        int margin = 4;
        int dropsHeight = HEIGHT - (margin * 2) - recipeHeight - ingredientHeight - (margin * 2);

        recipeElement = new ElementTextBox(this, fontRenderer, "Recipe", 4, margin, 248, recipeHeight);
        ingredientElement = new ElementDualStackRows(this, fontRenderer, "Ingredients", 4, margin + recipeHeight + margin, 248, ingredientHeight);
        dropsElement = new ElementStackBox(this, fontRenderer, "Drops", 4, margin + recipeHeight + margin + ingredientHeight + margin, 248, dropsHeight);

        elementBaseList.add(recipeElement);
        elementBaseList.add(ingredientElement);
        elementBaseList.add(dropsElement);
    }

    public FactoryHeartContainerGui(TileEntityMobFactoryHeart te, FactoryHeartContainer container) {

        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.te = te;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (!populated) {
            FarmUIInfo info = te.getGuiFarmInfo();
            if (info != null) {

                recipeElement.addString("Total Power: " + info.recipeTotalPower + " RF");
                recipeElement.addString("Total Time: " + info.recipeTotalTime + " ticks");
                recipeElement.addString("Power Per Tick: " + info.recipePowerPerTick + " RF/tick");
                recipeElement.addString("Mobs: " + info.mobCount + " x " + "??? mob");

                for (ItemStack itemStack : info.ingredients)
                    ingredientElement.addStack(itemStack);

                for (ItemStack itemStack : info.drops)
                    dropsElement.addStack(itemStack);

                populated = true;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        mc.getTextureManager().bindTexture(bg);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (ElementBase elementBase : elementBaseList)
            elementBase.drawBackground();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        LogHelper.info((mouseX - guiLeft) + ", " + (mouseY - guiTop));
        for (ElementBase elementBase : elementBaseList)
            elementBase.drawForeground(mouseX - guiLeft, mouseY - guiTop);

    }

    private void requestFarmInfo() {

        PacketHandler.INSTANCE.sendToServer(new PacketGetFarmInfo(te.getPos()));
    }
}
