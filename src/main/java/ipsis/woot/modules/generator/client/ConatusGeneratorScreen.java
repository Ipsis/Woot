package ipsis.woot.modules.generator.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.modules.generator.GeneratorConfiguration;
import ipsis.woot.modules.generator.blocks.ConatusGeneratorContainer;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.util.WootContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ConatusGeneratorScreen extends WootContainerScreen<ConatusGeneratorContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/conatusgenerator.png");

    private static final int GUI_XSIZE = 180;
    private static final int GUI_YSIZE = 177;
    private static final int ENERGY_LX = 10;
    private static final int ENERGY_LY = 18;
    private static final int ENERGY_RX = 25;
    private static final int ENERGY_RY = 77;
    private static final int ENERGY_WIDTH = ENERGY_RX - ENERGY_LX + 1;
    private static final int ENERGY_HEIGHT = ENERGY_RY - ENERGY_LY + 1;

    private static final int IN_TANK_LX = 46;
    private static final int IN_TANK_LY = 18;
    private static final int IN_TANK_RX = 61;
    private static final int IN_TANK_RY = 77;
    private static final int IN_TANK_WIDTH = IN_TANK_RX - IN_TANK_LX + 1;
    private static final int IN_TANK_HEIGHT = IN_TANK_RY - IN_TANK_LY + 1;

    private static final int OUT_TANK_LX = 154;
    private static final int OUT_TANK_LY = 18;
    private static final int OUT_TANK_RX = 169;
    private static final int OUT_TANK_RY = 77;
    private static final int OUT_TANK_WIDTH = OUT_TANK_RX - OUT_TANK_LX + 1;
    private static final int OUT_TANK_HEIGHT = OUT_TANK_RY - OUT_TANK_LY + 1;

    public ConatusGeneratorScreen(ConatusGeneratorContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        xSize = GUI_XSIZE;
        ySize = GUI_YSIZE;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (isPointInRegion(IN_TANK_LX, IN_TANK_LY, IN_TANK_WIDTH, IN_TANK_HEIGHT, mouseX, mouseY))
            renderFluidTankTooltip(mouseX, mouseY, container.getTileEntity().getInputTankFluid(),
                    GeneratorConfiguration.CONATUS_GEN_INPUT_TANK_CAPACITY.get());
        if (isPointInRegion(OUT_TANK_LX, OUT_TANK_LY, OUT_TANK_WIDTH, OUT_TANK_HEIGHT, mouseX, mouseY))
            renderFluidTankTooltip(mouseX, mouseY, container.getTileEntity().getOutputTankFluid(),
                    GeneratorConfiguration.CONATUS_GEN_OUTPUT_TANK_CAPACITY.get());
        if (isPointInRegion(ENERGY_LX, ENERGY_LY, ENERGY_WIDTH, ENERGY_HEIGHT, mouseX, mouseY))
            renderEnergyTooltip(mouseX, mouseY, container.getTileEntity().getEnergy(),
                    InfuserConfiguration.INFUSER_MAX_ENERGY.get(), InfuserConfiguration.INFUSER_ENERGY_PER_TICK.get());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, this.xSize, this.ySize);

        // Progress
        int progress = container.getTileEntity().getClientProgress();
        blit(this.guiLeft + 73, this.guiTop + 39, 180, 0,(int)(72 * (progress / 100.0F)) , 28);

        renderEnergyBar(
                ENERGY_LX,
                ENERGY_RY,
                ENERGY_HEIGHT,
                ENERGY_WIDTH,
                container.getTileEntity().getEnergy(), InfuserConfiguration.INFUSER_MAX_ENERGY.get());

        renderFluidTank(
                IN_TANK_LX,
                IN_TANK_RY,
                IN_TANK_HEIGHT,
                IN_TANK_WIDTH,
                container.getTileEntity().getInputTankFluid().getAmount(),
                GeneratorConfiguration.CONATUS_GEN_INPUT_TANK_CAPACITY.get(),
                container.getTileEntity().getInputTankFluid());

        renderFluidTank(
                OUT_TANK_LX,
                OUT_TANK_RY,
                OUT_TANK_HEIGHT,
                OUT_TANK_WIDTH,
                container.getTileEntity().getOutputTankFluid().getAmount(),
                GeneratorConfiguration.CONATUS_GEN_OUTPUT_TANK_CAPACITY.get(),
                container.getTileEntity().getOutputTankFluid());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String text = title.getFormattedText();
        this.font.drawString(text, (float)(this.xSize / 2 - this.font.getStringWidth(text) / 2), 6.0F, 4210752);
    }

}