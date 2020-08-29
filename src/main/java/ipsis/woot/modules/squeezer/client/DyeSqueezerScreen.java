package ipsis.woot.modules.squeezer.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerContainer;
import ipsis.woot.util.WootContainerScreen;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

@OnlyIn(Dist.CLIENT)
public class DyeSqueezerScreen extends WootContainerScreen<DyeSqueezerContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/squeezer.png");
    private static final int GUI_XSIZE = 180;
    private static final int GUI_YSIZE = 177;

    private static final int ENERGY_LX = 10;
    private static final int ENERGY_LY = 18;
    private static final int ENERGY_RX = 25;
    private static final int ENERGY_RY = 77;
    private static final int ENERGY_WIDTH = ENERGY_RX - ENERGY_LX + 1;
    private static final int ENERGY_HEIGHT = ENERGY_RY - ENERGY_LY + 1;

    private static final int TANK_LX = 154;
    private static final int TANK_LY = 18;
    private static final int TANK_RX = 169;
    private static final int TANK_RY = 77;
    private static final int TANK_WIDTH = TANK_RX - TANK_LX + 1;
    private static final int TANK_HEIGHT = TANK_RY - TANK_LY + 1;

    public DyeSqueezerScreen(DyeSqueezerContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        xSize = GUI_XSIZE;
        ySize = GUI_YSIZE;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (isPointInRegion(82, 30, 51, 8, mouseX, mouseY))
            renderTooltip(
                    StringHelper.translate("gui.woot.squeezer.red",
                        container.getRedDyeAmount(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()).getUnformattedComponentText(),
                    mouseX, mouseY);
        if (isPointInRegion(82, 40, 51, 8, mouseX, mouseY))
            renderTooltip(
                    StringHelper.translate("gui.woot.squeezer.yellow",
                    container.getYellowDyeAmount(),
                    SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()).getUnformattedComponentText(),
                    mouseX, mouseY);
        if (isPointInRegion(82, 50, 51, 8, mouseX, mouseY))
            renderTooltip(
                    StringHelper.translate("gui.woot.squeezer.blue",
                        container.getBlueDyeAmount(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()).getUnformattedComponentText(),
                    mouseX, mouseY);
        if (isPointInRegion(82, 60, 51, 8, mouseX, mouseY))
            renderTooltip(
                    StringHelper.translate("gui.woot.squeezer.white",
                    container.getWhiteDyeAmount(),
                    SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()).getUnformattedComponentText(),
                    mouseX, mouseY);
        if (isPointInRegion(TANK_LX, TANK_LY, TANK_WIDTH, TANK_HEIGHT, mouseX, mouseY))
            renderFluidTankTooltip(mouseX, mouseY,
                    container.getPureDye(),
                    SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get());
        if (isPointInRegion(ENERGY_LX, ENERGY_LY, ENERGY_WIDTH, ENERGY_HEIGHT, mouseX, mouseY))
            renderEnergyTooltip(mouseX, mouseY, container.getEnergy(),
                    SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get(), 10);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, this.xSize, this.ySize);

        // Progress
        int progress = container.getProgress();
        blit(this.guiLeft + 58, this.guiTop + 30, 180, 0,(int)(19 * (progress / 100.0F)) , 40);

        // NB: The tanks will change the texture so progress has to be above that or rebind the texture
        renderHorizontalGauge(82, 30, 132, 37,
                container.getRedDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.RED.getColorValue());
        renderHorizontalGauge(82, 40, 132, 47,
                container.getYellowDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.YELLOW.getColorValue());
        renderHorizontalGauge(82, 50, 132, 57,
                container.getBlueDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.BLUE.getColorValue());
        renderHorizontalGauge(82, 60, 132, 67,
                container.getWhiteDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.WHITE.getColorValue());

        renderEnergyBar(
                ENERGY_LX,
                ENERGY_RY,
                ENERGY_HEIGHT,
                ENERGY_WIDTH,
                container.getEnergy(), SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get());

        renderFluidTank(
                TANK_LX,
                TANK_RY,
                TANK_HEIGHT,
                TANK_WIDTH,
                SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get(),
                container.getPureDye());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String text = title.getFormattedText();
        this.font.drawString(text, (float)(this.xSize / 2 - this.font.getStringWidth(text) / 2), 6.0F, 4210752);

        String text2 = container.getDumpExcess() ?
                StringHelper.translate("gui.woot.squeezer.dump").getUnformattedComponentText() :
                StringHelper.translate("gui.woot.squeezer.strict").getUnformattedComponentText();
        this.font.drawString(text2, 82.0F, 70.0F, 4210752);
    }
}
