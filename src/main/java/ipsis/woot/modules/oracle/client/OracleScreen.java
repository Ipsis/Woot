package ipsis.woot.modules.oracle.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ScrollPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * When the GUI is first opened the list of fake mobs will be requested via ServerDataRequest.DROP_REGISTRY_STATUS
 * When the reply is received the list will be updated and displayed.
 * When a fake mob is selected, then the drops will be requested from the server via ServerDataRequest.MOB_DROPS
 */

@OnlyIn(Dist.CLIENT)
public class OracleScreen extends ContainerScreen<OracleContainer> {

    public OracleScreen(OracleContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    private GuiSimulatedMobsList guiSimulatedMobsList;
    private GuiSimulatedMobsList.SimulatedMobEntry selected = null;
    private int listWidth;
    private DropPanel simulatedDropsPanel;

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        /*
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (width - xSize) / 2;
        int relY = (height - ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize); */
    }

    @Override
    protected void init() {
        super.init(); // this sets guiLeft/guiTop
        this.listWidth = 100;
        this.guiSimulatedMobsList = new GuiSimulatedMobsList(this, listWidth);
        this.guiSimulatedMobsList.setLeftPos(6);

        this.simulatedDropsPanel = new DropPanel(this.minecraft, 20 * 9, this.height - 32, 16);

        children.add(guiSimulatedMobsList);
        children.add(simulatedDropsPanel);

        waitingSimulatedMobs = true;
        container.refreshMobs();
    }

    Minecraft getMinecraftInstance() { return minecraft; }
    FontRenderer getFontRenderer() { return font; }

    public void setSelected(GuiSimulatedMobsList.SimulatedMobEntry entry) {
        this.selected = entry == this.selected ? null : entry;

        if (selected == null) {
            this.simulatedDropsPanel.clearInfo();
        }  else {
            FakeMob simMob = selected.getInfo();
            waitingServerDrops = true;
            container.refreshDrops(simMob);
        }
    }

    public <T extends ExtendedList.AbstractListEntry<T>> void buildSimulatedMobsList(Consumer<T> consumer, Function<FakeMob, T> newEntry) {
        if (container.simulatedMobs != null)
            container.simulatedMobs.forEach(mob -> consumer.accept(newEntry.apply((mob))));
    }

    private boolean waitingSimulatedMobs = false;
    private boolean waitingServerDrops = false;
    @Override
    public void tick() {
        super.tick();

        if (waitingSimulatedMobs && container.simulatedMobs != null) {
            guiSimulatedMobsList.refreshList();
            waitingSimulatedMobs = false;
        }

        if (waitingServerDrops && container.simulatedDrops != null) {
            simulatedDropsPanel.setInfo(container.simulatedDrops);
            waitingServerDrops = false;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.guiSimulatedMobsList.render(mouseX, mouseY, partialTicks);
        if (this.simulatedDropsPanel != null)
            this.simulatedDropsPanel.render(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
    }

    class DropPanel extends ScrollPanel {

        private List<SimulatedMobDropSummary> drops = new ArrayList<>();

        DropPanel(Minecraft minecraft, int widthIn, int heightIn, int topIn) {
            super(minecraft, widthIn, heightIn, topIn, guiSimulatedMobsList.getRight() + 6);
        }

        @Override
        protected int getContentHeight() {
            int height = 50;
            height += (20 * font.FONT_HEIGHT);
            if (height < this.bottom - this.top - 8)
                height = this.bottom - this.top - 8;
            return height;
        }

        @Override
        protected int getScrollAmount() {
            return font.FONT_HEIGHT * 3;
        }

        void clearInfo() {
            drops.clear();
        }

        void setInfo(List<SimulatedMobDropSummary> drops) {
            this.drops = drops;
        }

        @Override
        protected void drawPanel(int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY) {

            int cols = width / 20;
            int currCol = 0;
            for (SimulatedMobDropSummary simDrop : drops) {

                /**
                 * Simulated Item
                 */
                int stackX = left + 4 + (currCol * 20);
                int stackY = relativeY;

                if (currCol + 1 == cols)
                    relativeY += 20;
                currCol = (currCol + 1) % cols;

                setBlitOffset(100);
                itemRenderer.zLevel = 100.0F;
                RenderHelper.enableStandardItemLighting();
                GlStateManager.enableDepthTest();
                itemRenderer.renderItemIntoGUI(simDrop.itemStack, stackX, stackY);
                RenderHelper.disableStandardItemLighting();
                itemRenderer.zLevel = 0.0F;
                setBlitOffset(0);

                if (mouseX >= stackX && mouseX <= stackX + 20 && mouseY >= stackY && mouseY <= stackY + 20) {
                    FontRenderer fontRenderer = simDrop.itemStack.getItem().getFontRenderer(simDrop.itemStack);
                    if (fontRenderer == null)
                        fontRenderer = font;
                    List<String> tooltip = getTooltipFromItem(simDrop.itemStack);
                    tooltip.add(String.format("No looting : %.2f%%", simDrop.chanceToDrop[0]));
                    tooltip.add(String.format("Looting 1 : %.2f%%", simDrop.chanceToDrop[1]));
                    tooltip.add(String.format("Looting 2 : %.2f%%", simDrop.chanceToDrop[2]));
                    tooltip.add(String.format("Looting 3: %.2f%%", simDrop.chanceToDrop[3]));

                    renderTooltip(tooltip, mouseX, mouseY, fontRenderer);
                }
            }
        }
    }
}
