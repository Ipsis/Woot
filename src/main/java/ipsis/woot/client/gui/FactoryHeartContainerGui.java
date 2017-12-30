package ipsis.woot.client.gui;

import ipsis.woot.client.gui.element.*;
import ipsis.woot.client.gui.inventory.FactoryHeartContainer;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.packets.PacketGetFarmInfo;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import ipsis.woot.util.StringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidRegistry;
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
    private ElementProgress progressElement;
    private ElementStackBox ingredientElement;
    private ElementStackBox dropsElement;
    private boolean populated = false;

    @Override
    public void initGui() {
        super.initGui();

        requestFarmInfo();

        int recipeHeight = 55;
        int progressHeight = 45;
        int ingredientHeight = 45;
        int margin = 4;
        int dropsHeight = HEIGHT - (margin * 4) - recipeHeight - progressHeight - ingredientHeight - (margin * 2);

        recipeElement = new ElementTextBox(
                this, fontRenderer,
                "Configuration",
                4, margin,
                248, recipeHeight);
        progressElement = new ElementProgress(
                this, fontRenderer,
                "Progress",
                4, margin + recipeHeight + margin,
                248, progressHeight);
        ingredientElement = new ElementStackBox(
                this, fontRenderer,
                "Ingredients",
                4, margin + recipeHeight + margin + progressHeight + margin,
                248, ingredientHeight);
        dropsElement = new ElementStackBox(this, fontRenderer,
                "Drops",
                4, margin + recipeHeight + margin + progressHeight + margin + ingredientHeight + margin,
                248, dropsHeight);

        elementBaseList.add(recipeElement);
        elementBaseList.add(progressElement);
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

                String s = info.tier.getTranslated("info.woot.tier");
                s += " " + info.mobName + " * " + info.mobCount;
                recipeElement.addString(s);

                /*
                if (info.isRunning)
                    recipeElement.addString("Running");
                else
                    recipeElement.addString(TextFormatting.RED + "Stopped"); */

                recipeElement.addString(TextFormatting.GREEN + "Power: " + info.recipeTotalPower + "RF @ " + info.recipePowerPerTick + "RF/tick");
                recipeElement.addString(TextFormatting.GREEN + "Time: " + info.recipeTotalTime + " ticks");

                for (ItemStack itemStack : info.itemIngredients) {
                    DisplayItemStack displayItemStack = ingredientElement.addItemStack(itemStack);
                    displayItemStack.appendTooltip(itemStack.getCount() + " per mob");
                }

                for (FluidStack fluidStack : info.fluidIngredients) {
                    DisplayFluidStack displayFluidStack = ingredientElement.addFluidStack(fluidStack);
                    displayFluidStack.appendTooltip(fluidStack.amount + "mb per mob");
                }

                // Padding out the drops for testing
                for (int i = 0; i < 3; i++) {
                    for (ItemStack itemStack : info.drops) {
                        DisplayItemStack displayItemStack = dropsElement.addItemStack(itemStack);
                        displayItemStack.appendTooltip(itemStack.getCount() + "%");
                    }
                }

                progressElement.addProgress(0, "Power", info.powerCapacity);
                progressElement.addProgress(1, "Spawning", 100);

                populated = true;
            }
        }

        progressElement.updateProgress(0, te.guiStoredPower);
        progressElement.updateProgress(1, te.guiProgress);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        mc.getTextureManager().bindTexture(bg);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (ElementBase elementBase : elementBaseList)
            elementBase.drawBackground(mouseX - guiLeft, mouseY - guiTop);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        for (ElementBase elementBase : elementBaseList)
            elementBase.drawForeground(mouseX - guiLeft, mouseY - guiTop);
    }

    private void requestFarmInfo() {

        PacketHandler.INSTANCE.sendToServer(new PacketGetFarmInfo(te.getPos()));
    }
}
