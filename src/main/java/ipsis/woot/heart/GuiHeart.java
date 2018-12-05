package ipsis.woot.heart;

import ipsis.Woot;
import ipsis.woot.network.PacketHandler;
import ipsis.woot.network.PacketRequestServerData;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiHeart extends GuiContainer {

    private static final int WIDTH = 256;
    private static final int HEIGHT = 224;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Woot.MODID, "textures/gui/heart.png");
    private TileEntityHeart tileEntityHeart;

    public GuiHeart(TileEntityHeart tileEntityHeart, ContainerHeart containerHeart) {
        super(containerHeart);

        xSize = WIDTH;
        ySize = HEIGHT;
        this.tileEntityHeart = tileEntityHeart;
    }

    @Override
    public void initGui() {
        super.initGui();

        PacketHandler.INSTANCE.sendToServer(new PacketRequestServerData(PacketRequestServerData.CMD_GET_STATIC_HEART_DATA, tileEntityHeart.getPos()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tileEntityHeart.isRunning()) {
            int progress = tileEntityHeart.getClientProgress();
            drawString(mc.fontRenderer, "Progress: " + progress + "%", guiLeft + 10, guiTop + 50, 0xffffff);
            drawString(mc.fontRenderer, "Running: " + (tileEntityHeart.getClientRunning() == 1 ? "yes" : "no"), guiLeft + 10, guiTop + 66, 0xffffff);
        }

        HeartUIFixedInfo info = tileEntityHeart.getClientUIFixedInfo();
        if (info.isFormed()) {
            drawString(mc.fontRenderer, "Ticks: " + info.recipeTicks, guiLeft + 10, guiTop + 80, 0xffffff);
            drawString(mc.fontRenderer, "Units: " + info.recipeUnits, guiLeft + 10, guiTop + 95, 0xffffff);
            drawString(mc.fontRenderer, "Tier: " + info.tier, guiLeft + 10, guiTop + 110, 0xffffff);
            drawString(mc.fontRenderer, "Mob: " + info.fakeMobKey.toString(), guiLeft + 10, guiTop + 125, 0xffffff);
        }
    }
}
