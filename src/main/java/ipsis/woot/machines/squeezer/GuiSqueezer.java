package ipsis.woot.machines.squeezer;

import ipsis.woot.Woot;
import ipsis.woot.config.MachineConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiSqueezer extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 177;

    // @todo fix the color RGB for the gui
    private static final int BLUE = 0xFF0000FF;
    private static final int RED = 0xFFFF0000;
    private static final int YELLOW = 0xFFFFFF00;
    private static final int WHITE = 0xFFFFFFFF;

    private static final ResourceLocation background = new ResourceLocation(Woot.MODID, "textures/gui/squeezer.png");
    private TileEntitySqueezer squeezer;


    public GuiSqueezer(TileEntitySqueezer tileEntitySqueezer, ContainerSqueezer containerSqueezer) {
        super(containerSqueezer);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.squeezer = tileEntitySqueezer;
    }

    private static final int ENERGY_HEIGHT = 60;
    private static final float ENERGY_PER = (float)ENERGY_HEIGHT / 100.0F;
    private static final int COLOR_WIDTH = 51;
    private static final float COLOR_PER = (float)COLOR_WIDTH / 100.0F;
    private static final int PROGRESS_WIDTH = 19;
    private static final float PROGRESS_PER = (float)PROGRESS_WIDTH / 100.0F;

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize + PROGRESS_WIDTH, ySize + 15);

        /**
         * Energy bar
         */
        int max = MachineConfig.SQUEEZER_ENERGY_CAPACITY.get();
        int curr = squeezer.getClientEnergy();
        float per = (100.0F / (float)max) * (float)curr;
        int offset = MathHelper.clamp((int)(per * ENERGY_PER) , 0, ENERGY_HEIGHT - 1);
        drawRect(guiLeft + 10, guiTop + 77, guiLeft + 26, guiTop + 77 - offset, RED);

        /**
         * Progress
         */
        offset = MathHelper.clamp((int)(squeezer.getClientProgress() * PROGRESS_PER), 0, PROGRESS_WIDTH - 1);
        drawTexturedModalRect(guiLeft + 59, guiTop + 40, 180, 0, offset, 15);

        /**
         * Color levels
         */
        max = MachineConfig.SQUEEZER_COLOR_CAPACITY.get();
        curr = squeezer.getClientBlue();
        per = (100.0F / (float)max) * (float)curr;
        offset = MathHelper.clamp((int)(per * COLOR_PER) , 0, COLOR_WIDTH - 1);
        drawRect(guiLeft + 82, guiTop + 30, guiLeft + 82 + offset, guiTop + 37, BLUE);

        curr = squeezer.getClientRed();
        per = (100.0F / (float)max) * (float)curr;
        offset = MathHelper.clamp((int)(per * COLOR_PER) , 0, COLOR_WIDTH - 1);
        drawRect(guiLeft + 82, guiTop + 40, guiLeft + 82 + offset, guiTop + 47, RED);

        curr = squeezer.getClientYellow();
        per = (100.0F / (float)max) * (float)curr;
        offset = MathHelper.clamp((int)(per * COLOR_PER) , 0, COLOR_WIDTH - 1);
        drawRect(guiLeft + 82, guiTop + 50, guiLeft + 82 + offset, guiTop + 57, YELLOW);

        curr = squeezer.getClientWhite();
        per = (100.0F / (float)max) * (float)curr;
        offset = MathHelper.clamp((int)(per * COLOR_PER) , 0, COLOR_WIDTH - 1);
        drawRect(guiLeft + 82, guiTop + 60, guiLeft + 82 + offset, guiTop + 67, WHITE);

        /**
         * Output tank
         */
        drawRect(guiLeft + 154, guiTop + 18, guiLeft + 170, guiTop + 77, RED);


    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.squeezer.getName().getString();
        this.fontRenderer.drawString(s, (float)(this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6.0F, 4210752);
    }
}
