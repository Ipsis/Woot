package ipsis.woot.client.gui;

import ipsis.woot.client.gui.inventory.FactoryHeartContainer;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.packets.PacketGetFarmInfo;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.TileEntityMobFactoryHeart;
import ipsis.woot.tileentity.ui.FarmUIInfo;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
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

    @Override
    public void initGui() {
        super.initGui();

        requestFarmInfo();
    }

    public FactoryHeartContainerGui(TileEntityMobFactoryHeart te, FactoryHeartContainer container) {

        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.te = te;

        elementBaseList.add(new ElementTextBox(this, 0, 0, 200, 4 * TXT_HEIGHT));

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
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        FarmUIInfo info = te.getGuiFarmInfo();
        if (info == null)
            return;

        /*
        List<String> strings = new ArrayList<>();
        strings.add("Total Power: " + info.recipeTotalPower + " RF");
        strings.add("Total Time: " + info.recipeTotalTime + " ticks");
        strings.add("Power Per Tick: " + info.recipePowerPerTick + " RF/tick");
        strings.add("Num Mobs: " + info.mobCount);

        int x = 4;
        int y = 4;
        drawRecipePanel(strings, x, y, 200, 4 * TXT_HEIGHT);

        y += (5 * TXT_HEIGHT);
        drawIngredientPanel(info.ingredients, new ArrayList<>(), x, y, 200, (2 * STACK_HEIGHT) + 4);

        y += (TXT_HEIGHT + (2 * STACK_HEIGHT) + 4);
        drawDropsPanel(info.drops, x, y, 200, 4 * STACK_HEIGHT); */

        for (ElementBase elementBase : elementBaseList)
            elementBase.drawForeground();
    }

    private static final int BG_COLOR = 0x205060;
    private static final int HDR_COLOR = 0xFFFFFF;
    private static final int TXT_HEIGHT = 12;
    private static final int STACK_HEIGHT = 12;
    private static final int X_MARGIN = 4;

    private void drawPanel(String header, int x, int y, int sizeX, int sizeY, int bgColor, int hdrColor) {

        // Draw background box
        Gui.drawRect(x, y, x + sizeX, y + sizeY, bgColor);
//        drawGradientRect(x, y, x + sizeX, y + sizeY, bgColor, bgColor);
        fontRenderer.drawString(header, x, y, hdrColor);
    }

    private void drawRecipePanel(List<String> textList, int x, int y, int sizeX, int sizeY) {

        drawPanel("Recipe", x, y, sizeX, sizeY, BG_COLOR, HDR_COLOR);
        int yy = y + TXT_HEIGHT;
        int xx = x + X_MARGIN;

        for (int c = 0; c < textList.size(); c++)
            fontRenderer.drawString(textList.get(c), xx, yy + (c * TXT_HEIGHT), HDR_COLOR);
    }

    private void drawIngredientPanel(List<ItemStack> itemList, List<FluidStack> fluidList, int x, int y, int sizeX, int sizeY) {

        drawPanel("Ingredients", x, y, sizeX, sizeY, BG_COLOR, HDR_COLOR);
        int yy = y + TXT_HEIGHT;
        int xx = x + X_MARGIN;

        for (int c = 0; c < itemList.size(); c++) {
            ItemStack itemStack = itemList.get(c);
            fontRenderer.drawString(Integer.toString(itemStack.getCount()), xx + (c * 18)  + 1, yy, HDR_COLOR);
        }

        for (int c = 0; c < fluidList.size(); c++) {
            FluidStack fluidStack = fluidList.get(c);
            fontRenderer.drawString(Integer.toString(fluidStack.amount), xx + (c * 18)  + 1, yy + STACK_HEIGHT, HDR_COLOR);
        }
    }

    private void drawDropsPanel(List<ItemStack> dropList, int x, int y, int sizeX, int sizeY) {

        drawPanel("Drops", x, y, sizeX, sizeY, BG_COLOR, HDR_COLOR);
        int yy = y + TXT_HEIGHT;
        int xx = x + X_MARGIN;

        for (int c = 0; c < dropList.size(); c++) {
            ItemStack itemStack = dropList.get(c);
            String s = itemStack.getCount() + "% " + itemStack.getDisplayName();
            fontRenderer.drawString(s, xx, yy + (c * STACK_HEIGHT), HDR_COLOR);
        }
    }

    private void requestFarmInfo() {

        PacketHandler.INSTANCE.sendToServer(new PacketGetFarmInfo(te.getPos()));
    }
}
