package ipsis.woot.client.gui;

import ipsis.woot.client.gui.element.*;
import ipsis.woot.client.gui.inventory.FactoryHeartContainer;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.packets.PacketGetFarmInfo;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FactoryHeartContainerGui extends GuiContainerWoot {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 224;
    private static final int GUI_X_MARGIN = 4;
    private static final int GUI_Y_MARGIN = 4;
    private TileEntityMobFactoryHeart te;
    private FactoryHeartContainer container;

    private static final ResourceLocation bg = new ResourceLocation(Reference.MOD_ID, "textures/gui/heart.png");
    private List<ElementBase> elementBaseList = new ArrayList<>();
    private ElementTextBox recipeElement;
    private ElementProgress progressElementPower;
    private ElementProgress progressElementRecipe;
    private ElementStackBox ingredientElement;
    private ElementStackBox dropsElement;

    @Override
    public void initGui() {
        super.initGui();

        requestFarmInfo();

        /**
         *
         * Recipe
         * Margin
         * Progress - Power
         * Progress - Recipe
         * Margin
         * Ingredients
         * Margin
         * Drops
         */

        int panelWidth = WIDTH - (GUI_X_MARGIN * 2);
        int panelMargin = 4;
        int recipeHeight = 55;
        int progressHeight0 = 18;
        int progressHeight1 = 18;
        int ingredientHeight = 40;
        int dropsHeight = HEIGHT - (GUI_Y_MARGIN * 2) - recipeHeight - progressHeight0 - progressHeight1 - ingredientHeight - (4 * panelMargin);

        recipeElement = new ElementTextBox(
                this, fontRenderer,
                "Configuration",
                GUI_X_MARGIN, GUI_Y_MARGIN,
                panelWidth, recipeHeight);

        progressElementPower = new ElementProgress(
                this, fontRenderer,
                null,
                "Power", 1, Color.red.getRGB(),
                GUI_X_MARGIN, GUI_Y_MARGIN + recipeHeight + panelMargin,
                panelWidth, progressHeight0);
        progressElementRecipe = new ElementProgress(
                this, fontRenderer,
                null,
                "Spawning", 0, Color.orange.getRGB(),
                GUI_X_MARGIN, GUI_Y_MARGIN + recipeHeight + progressHeight0 + (panelMargin * 2),
                panelWidth, progressHeight0);
        ingredientElement = new ElementStackBox(
                this, fontRenderer,
                "Ingredients",
                GUI_X_MARGIN, GUI_Y_MARGIN + recipeHeight + progressHeight0 + progressHeight1 + (panelMargin * 3),
                panelWidth, ingredientHeight);
        dropsElement = new ElementStackBox(this, fontRenderer,
                "Drops",
                GUI_X_MARGIN, GUI_Y_MARGIN + recipeHeight + progressHeight0 + progressHeight1 + ingredientHeight + (panelMargin * 4),
                panelWidth, dropsHeight);

        elementBaseList.add(recipeElement);
        elementBaseList.add(progressElementPower);
        elementBaseList.add(progressElementRecipe);
        elementBaseList.add(ingredientElement);
        elementBaseList.add(dropsElement);
    }

    public FactoryHeartContainerGui(TileEntityMobFactoryHeart te, FactoryHeartContainer container) {

        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.container = container;
        this.te = te;
    }

    private boolean init = false;
    private static DecimalFormat dfCommas = new DecimalFormat("###,###");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (!init && container.hasServerSync()) {
            FarmUIInfo info = te.getGuiFarmInfo();
            if (info != null) {

                String s = info.tier.getTranslated("info.woot.tier");
                s += " " + info.mobName + " * " + info.mobCount;
                recipeElement.addString(s);

                String s1 = dfCommas.format(info.recipeTotalPower) + "RF";
                String s2 = dfCommas.format(info.recipePowerPerTick) + "RF/tick";
                recipeElement.addString(TextFormatting.GREEN + "Power: " + s1 + " @ " +  s2);
                recipeElement.addString(TextFormatting.GREEN + "Time: " + info.recipeTotalTime + " ticks");

                if (info.missingIngredients) {
                    LogHelper.info("Missing ingredients");
                    ingredientElement.setShowFlag(true);
                }

                for (ItemStack itemStack : info.ingredientsItems) {
                    DisplayItemStack displayItemStack = ingredientElement.addItemStack(itemStack);
                    displayItemStack.appendTooltip(itemStack.getCount() + " per mob");
                }

                for (FluidStack fluidStack : info.ingredientsFluids) {
                    DisplayFluidStack displayFluidStack = ingredientElement.addFluidStack(fluidStack);
                    displayFluidStack.appendTooltip(fluidStack.amount + "mb per mob");
                }

                for (ItemStack itemStack : info.drops) {
                    DisplayItemStack displayItemStack = dropsElement.addItemStack(itemStack);
                    displayItemStack.appendTooltip("Chance: " + itemStack.getCount() + "%");
                }

                progressElementPower.setMax(info.powerCapacity);
                progressElementRecipe.setMax(100);

                init = true;
            }
        }

        progressElementPower.updateProgress(te.guiStoredPower);
        progressElementRecipe.updateProgress(te.guiProgress);

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
