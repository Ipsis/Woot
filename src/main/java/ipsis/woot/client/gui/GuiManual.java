package ipsis.woot.client.gui;

import ipsis.woot.client.manual.BookManager;
import ipsis.woot.client.manual.IBookPage;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiManual extends GuiScreen {

    private int xSize = 239;
    private int ySize = 196;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonNextPage;
    private GuiButton buttonPrevPage;
    private GuiButton buttonHome;
    private IBookPage currPage;

    private static final ResourceLocation guiResource = new ResourceLocation(Reference.MOD_ID, "textures/gui/manual.png");

    @Override
    public void initGui() {
        super.initGui();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;


        this.buttonPrevPage = this.addButton(new GuiButton(0, guiLeft + 16, guiTop + ySize - 32, 60, 20, "Prev"));
        this.buttonNextPage = this.addButton(new GuiButton(1, guiLeft + this.xSize - 16 - 60, guiTop + ySize - 32, 60, 20, "Next"));
        this.buttonHome = this.addButton(new GuiButton(2, guiLeft + (this.xSize / 2) - 30, guiTop + ySize - 32, 60, 20, "Home"));

        currPage = BookManager.INSTANCE.getFirstPage();

        updateButtons();
    }

    @Override
    public boolean doesGuiPauseGame() {

        return false;
    }

    private void updateButtons() {

        boolean showPrev = false;
        boolean showNext = false;

        if (BookManager.INSTANCE.hasNextPage(currPage))
            showNext = true;

        if (BookManager.INSTANCE.hasPrevPage(currPage))
            showPrev = true;

        this.buttonHome.visible = true;
        this.buttonNextPage.visible = showNext;
        this.buttonPrevPage.visible = showPrev;

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        /* Draw the manual */
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiResource);
        drawTexturedModalRect(guiLeft, guiTop, 6, 6, xSize, ySize);

        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        if (currPage != null) {

            String title = currPage.getSection().getTitle();
            drawCenteredString(fontRendererObj, title, guiLeft + (xSize / 2), guiTop + 16, 255);
            currPage.renderPage(this, guiLeft + 16, guiTop + 16 + fontRendererObj.FONT_HEIGHT + 16, this.xSize - 32);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        if (button.enabled) {
            if (button.id == 0) {
                /* prev */
                currPage = BookManager.INSTANCE.getPrevPage(currPage);
            } else if (button.id == 1) {
                /* next */
                currPage = BookManager.INSTANCE.getNextPage(currPage);
            } else if (button.id == 2) {
                LogHelper.info("Pressed Home");
            }
        }

        this.updateButtons();
    }
}
