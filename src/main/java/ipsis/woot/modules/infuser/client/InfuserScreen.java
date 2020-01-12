package ipsis.woot.modules.infuser.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.blocks.InfuserContainer;
import ipsis.woot.util.WootContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InfuserScreen extends WootContainerScreen<InfuserContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/infuser.png");

    public InfuserScreen(InfuserContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        xSize = 180;
        ySize = 177;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 154 && mouseX < guiLeft + 169 && mouseY > guiTop + 18 && mouseY < guiTop + 77)
            renderFluidTankTooltip(mouseX, mouseY, container.getTileEntity().getTankFluid(),
                    InfuserConfiguration.INFUSER_TANK_CAPACITY.get());
        if (mouseX > guiLeft + 10 && mouseX < guiLeft + 25 && mouseY > guiTop + 18 && mouseY < guiTop + 77)
            renderEnergyTooltip(mouseX, mouseY, container.getTileEntity().getEnergy(),
                    InfuserConfiguration.INFUSER_MAX_ENERGY.get(), InfuserConfiguration.INFUSER_ENERGY_PER_TICK.get());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);

        renderEnergyBar(11, 19, 25, 77,
                container.getTileEntity().getEnergy(), InfuserConfiguration.INFUSER_MAX_ENERGY.get());

        renderFluidTank(154, 18, 169, 77,
                container.getTileEntity().getTankFluid().getAmount(),
                InfuserConfiguration.INFUSER_TANK_CAPACITY.get(),
                container.getTileEntity().getTankFluid());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String text = title.getFormattedText();
        this.font.drawString(text, (float)(this.xSize / 2 - this.font.getStringWidth(text) / 2), 6.0F, 4210752);
    }
}



