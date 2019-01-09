package ipsis.woot.machines.squeezer;

import ipsis.Woot;
import ipsis.woot.ModFluids;
import ipsis.woot.util.FluidStackRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class GuiSqueezer extends GuiContainer {

    private static final ResourceLocation background = new ResourceLocation(Woot.MODID, "textures/gui/squeezer.png");

    public static final int WIDTH = 174;
    public static final int HEIGHT = 177;

    private TileEntitySqueezer te;
    public GuiSqueezer(TileEntitySqueezer te, ContainerSqueezer containerSqueezer) {
        super(containerSqueezer);

        xSize = WIDTH;
        ySize = HEIGHT;

        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {

        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        drawRect(guiLeft + 82, guiTop + 30, guiLeft + 132, guiTop + 37, Color.BLUE.getRGB());
        drawRect(guiLeft + 82, guiTop + 40, guiLeft + 132, guiTop + 47, Color.RED.getRGB());
        drawRect(guiLeft + 82, guiTop + 50, guiLeft + 132, guiTop + 57, Color.YELLOW.getRGB());
        drawRect(guiLeft + 82, guiTop + 60, guiLeft + 132, guiTop + 67, Color.WHITE.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        FluidStackRenderer.renderFluidStack(new FluidStack(ModFluids.pureDye, 5000), guiLeft + 154,guiTop + 61);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
