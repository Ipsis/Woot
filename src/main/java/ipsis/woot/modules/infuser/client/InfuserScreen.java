package ipsis.woot.modules.infuser.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.modules.infuser.InfuserConfiguration;
import ipsis.woot.modules.infuser.blocks.InfuserContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InfuserScreen extends ContainerScreen<InfuserContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/infuser.png");

    public InfuserScreen(InfuserContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        xSize = 256;
        ySize = 256;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 154  && mouseX < guiLeft + 169 && mouseY > guiTop + 18 && mouseY < guiTop + 77)
            renderTooltip("Tank: " + container.getTileEntity().getTankAmount() + "mB",
                    mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);

        // Tank
        int p = container.getTileEntity().getTankAmount() * (77 - 18) / InfuserConfiguration.TANK_CAPACITY.get();
        //fill(guiLeft + 154, guiTop + 18, guiLeft + 154, guiTop + 77, 0xffff00ff);
        fill(guiLeft + 154, guiTop + p, guiLeft + 154, guiTop + 77, 0xffff00ff);

    }
}



