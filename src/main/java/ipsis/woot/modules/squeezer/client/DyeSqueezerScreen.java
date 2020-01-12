package ipsis.woot.modules.squeezer.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.squeezer.SqueezerConfiguration;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerContainer;
import ipsis.woot.util.WootContainerScreen;
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

    public DyeSqueezerScreen(DyeSqueezerContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        xSize = 180;
        ySize = 177;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 30 && mouseY < guiTop + 37)
            renderTooltip(String.format("Red: %d/%d mb",
                        container.getTileEntity().getRed(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 40 && mouseY < guiTop + 47)
            renderTooltip(String.format("Yellow: %d/%d mb",
                        container.getTileEntity().getYellow(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 50 && mouseY < guiTop + 57)
            renderTooltip(String.format("Blue: %d/%d mb",
                        container.getTileEntity().getBlue(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (mouseX > guiLeft + 82 && mouseX < guiLeft + 132 && mouseY > guiTop + 60 && mouseY < guiTop + 67)
            renderTooltip(String.format("White: %d/%d mb",
                        container.getTileEntity().getWhite(),
                        SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get()),
                    mouseX, mouseY);
        if (mouseX > guiLeft + 154 && mouseX < guiLeft + 169 && mouseY > guiTop + 18 && mouseY < guiTop + 77)
            renderFluidTankTooltip(mouseX, mouseY,
                    new FluidStack(FluidSetup.PUREDYE_FLUID.get(), container.getTileEntity().getPure()),
                    SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get());
        if (mouseX > guiLeft + 10 && mouseX < guiLeft + 25 && mouseY > guiTop + 18 && mouseY < guiTop + 77)
            renderEnergyTooltip(mouseX, mouseY, container.getTileEntity().getEnergy(),
                    SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get(), 10);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);

        renderHorizontalGauge(82, 30, 132, 37,
                container.getTileEntity().getRed(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.RED.getColorValue());
        renderHorizontalGauge(82, 40, 132, 47,
                container.getTileEntity().getYellow(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.YELLOW.getColorValue());
        renderHorizontalGauge(82, 50, 132, 57,
                container.getTileEntity().getBlue(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.BLUE.getColorValue());
        renderHorizontalGauge(82, 60, 132, 67,
                container.getTileEntity().getWhite(), SqueezerConfiguration.DYE_SQUEEZER_INTERNAL_FLUID_MAX.get(),
                0xff000000 | DyeColor.WHITE.getColorValue());

        renderEnergyBar(10, 18, 25, 77,
                container.getTileEntity().getEnergy(), SqueezerConfiguration.DYE_SQUEEZER_MAX_ENERGY.get());

        renderFluidTank(154, 18, 169, 77,
                container.getTileEntity().getPure(), SqueezerConfiguration.DYE_SQUEEZER_TANK_CAPACITY.get(),
                new FluidStack(FluidSetup.PUREDYE_FLUID.get(), container.getTileEntity().getPure()));
    }
}
