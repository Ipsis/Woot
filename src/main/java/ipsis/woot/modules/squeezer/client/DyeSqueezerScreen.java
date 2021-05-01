package ipsis.woot.modules.squeezer.client;

import com.mojang.blaze3d.matrix.MatrixStack;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
        imageWidth = GUI_XSIZE;
        imageHeight = GUI_YSIZE;
        titleLabelY = getYSize() - 94;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);

        if (isHovering(82, 30, 51, 8, mouseX, mouseY))
            renderTooltip(matrixStack, new TranslationTextComponent(
                          "gui.woot.squeezer.red",
                        menu.getRedDyeAmount(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (isHovering(82, 40, 51, 8, mouseX, mouseY))
            renderTooltip(matrixStack, new TranslationTextComponent(
                    "gui.woot.squeezer.yellow",
                    menu.getYellowDyeAmount(),
                    SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (isHovering(82, 50, 51, 8, mouseX, mouseY))
            renderTooltip(matrixStack, new TranslationTextComponent(
                    "gui.woot.squeezer.blue",
                        menu.getBlueDyeAmount(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (isHovering(82, 60, 51, 8, mouseX, mouseY))
            renderTooltip(matrixStack, new TranslationTextComponent(
                    "gui.woot.squeezer.white",
                    menu.getWhiteDyeAmount(),
                    SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (isHovering(TANK_LX, TANK_LY, TANK_WIDTH, TANK_HEIGHT, mouseX, mouseY))
            renderFluidTankTooltip(matrixStack, mouseX, mouseY,
                    menu.getPureDye(),
                    SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get());
        if (isHovering(ENERGY_LX, ENERGY_LY, ENERGY_WIDTH, ENERGY_HEIGHT, mouseX, mouseY))
            renderEnergyTooltip(matrixStack, mouseX, mouseY, menu.getEnergy(),
                    SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get(), 10);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bind(GUI);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.getXSize(), this.getYSize());

        // Progress
        int progress = menu.getProgress();
        this.blit(matrixStack, this.getGuiLeft() + 58, this.getGuiTop() + 30, 180, 0,(int)(19 * (progress / 100.0F)) , 40);

        // NB: The tanks will change the texture so progress has to be above that or rebind the texture
        renderHorizontalGauge(matrixStack, 82, 30, 132, 37,
                menu.getRedDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.RED.getColorValue());
        renderHorizontalGauge(matrixStack, 82, 40, 132, 47,
                menu.getYellowDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.YELLOW.getColorValue());
        renderHorizontalGauge(matrixStack, 82, 50, 132, 57,
                menu.getBlueDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.BLUE.getColorValue());
        renderHorizontalGauge(matrixStack, 82, 60, 132, 67,
                menu.getWhiteDyeAmount(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.WHITE.getColorValue());

        renderEnergyBar(
                matrixStack,
                ENERGY_LX,
                ENERGY_RY,
                ENERGY_HEIGHT,
                ENERGY_WIDTH,
                menu.getEnergy(), SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get());

        renderFluidTank(
                matrixStack,
                TANK_LX,
                TANK_RY,
                TANK_HEIGHT,
                TANK_WIDTH,
                SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get(),
                menu.getPureDye());
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        String text2 = menu.getDumpExcess() ?
                StringHelper.translate("gui.woot.squeezer.dump") :
                StringHelper.translate("gui.woot.squeezer.strict");
        this.font.draw(matrixStack, text2, 82.0F, 70.0F, 4210752);
    }
}
