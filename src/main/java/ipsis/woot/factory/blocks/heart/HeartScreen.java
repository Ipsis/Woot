package ipsis.woot.factory.blocks.heart;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryUIInfo;
import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.items.UpgradeItem;
import ipsis.woot.network.HeartStaticDataRequest;
import ipsis.woot.network.Network;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * There are two types of information displayed here.
 * Static - the factory recipe and drops - custom packet
 * Dynamic - progress - vanilla progress mechanism
 */

@OnlyIn(Dist.CLIENT)
public class HeartScreen extends ContainerScreen<HeartContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/heart.png");

    public HeartScreen(HeartContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        xSize = 252;
        ySize = 152;
    }

    private List<StackElement> stackElements = new ArrayList<>();
    private List<StackElement> mobElements = new ArrayList<>();
    private List<StackElement> upgradeElements = new ArrayList<>();

    private int DROPS_COLS = 13;
    private int DROPS_ROWS = 2;
    private int DROPS_X = 10;
    private int DROPS_Y = 110;
    private int MOBS_X = 10;
    private int MOBS_Y = 76;
    private int UPGRADES_X = 99;
    private int UPGRADES_Y = 76;
    private float DROP_CYCLE_MS = 5000.0F;
    private int TEXT_COLOR = 4210752;

    private long renderTime;

    @Override
    protected void init() {
        super.init();

        // Mobs
        for (int i = 0; i < 4; i++)
            mobElements.add(new StackElement(MOBS_X + (i * 18), MOBS_Y, true));

        // Upgrades
        for (int i = 0; i < 4; i++)
            upgradeElements.add(new StackElement(UPGRADES_X + (i * 18), UPGRADES_Y, true));

        // Recipe

        // Drops
        for (int row = 0; row < DROPS_ROWS; row++) {
            for (int col = 0; col < DROPS_COLS; col++) {
                stackElements.add(new StackElement(DROPS_X + (col * 18), DROPS_Y + (row * 18)));
            }
        }

        // Request the static data
        Network.channel.sendToServer(new HeartStaticDataRequest(container.getPos()));
    }

    /**
     * ContainerScreen render
     *
     * drawGuiContainerBackgroundLayer
     *     draw slots and itemstacks
     *     gradient fill active slot to highlight
     * drawGuiContainerForegroundLayer
     *     draw dragged itemstack
     *
     */

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        mobElements.forEach(e -> e.drawTooltip(mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawTooltip(mouseX, mouseY));
        stackElements.forEach(e -> e.drawTooltip(mouseX, mouseY));

        if (renderTime == 0L)
            renderTime = Util.milliTime();

        // Cycle the stacks every 5s
        if (Util.milliTime() - renderTime > DROP_CYCLE_MS) {
            stackElements.forEach(e -> e.cycle());
            renderTime = Util.milliTime();
        }
    }

    /**
     * 0,0 is top left hand corner of the gui texture
     */
    private boolean sync = false;
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        /*
        font.drawString(title.getFormattedText(),
                (float)(xSize / 2 - font.getStringWidth(title.getFormattedText()) / 2),
                6.0F, TEXT_COLOR); */

        FactoryUIInfo factoryUIInfo = container.getFactoryUIInfo();
        if (factoryUIInfo == null)
            return;

        if (!sync) {
            int idx = 0;
            for (ItemStack itemStack : factoryUIInfo.drops) {
                List<String> tooltip = getTooltipFromItem(itemStack);
                tooltip.add(String.format("Drop chance: %.2f%%", itemStack.getCount() / 100.0F));
                stackElements.get(idx).addDrop(itemStack, tooltip);
                idx = (idx + 1) % stackElements.size();
            }

            idx = 0;
            for (FactoryUIInfo.Mob mob : factoryUIInfo.mobInfo) {
                List<String> tooltip = getTooltipFromItem(mob.controller);
                if (!mob.itemIngredients.isEmpty()) {
                    for (ItemStack itemStack : mob.itemIngredients) {
                        ITextComponent itextcomponent = (new StringTextComponent("")).appendSibling(itemStack.getDisplayName()).applyTextStyle(itemStack.getRarity().color);
                        tooltip.add("Ingredient: " + itemStack.getCount() + " * " + itextcomponent.getFormattedText());
                    }
                }
                if (!mob.fluidIngredients.isEmpty()) {
                    for (FluidStack fluidStack : mob.fluidIngredients)
                        tooltip.add(fluidStack.amount + "mb " + fluidStack.toString());
                }
                mobElements.get(idx).addDrop(mob.controller, tooltip);
                idx++;
            }

            idx = 0;
            for (FactoryUpgrade upgrade : factoryUIInfo.upgrades) {
                ItemStack itemStack = UpgradeItem.getItemStack(upgrade);
                List<String> tooltip = getTooltipFromItem(itemStack);
                upgradeElements.get(idx).addDrop(itemStack, tooltip);
                idx = (idx + 1) % upgradeElements.size();
            }
            sync = true;
        }

        addInfoLine(0, "Effort", factoryUIInfo.recipeEffort + " mB");
        addInfoLine(1, "Time", factoryUIInfo.recipeTicks + " ticks");
        addInfoLine(2, "Rate", factoryUIInfo.recipeCostPerTick + " mb/tick");
        addInfoLine(3, "Progress", "15%");


        font.drawString("Mobs", MOBS_X, MOBS_Y - 10, TEXT_COLOR);
        font.drawString("Upgrades", UPGRADES_X, UPGRADES_Y - 10, TEXT_COLOR);
        font.drawString("Drops:", DROPS_X, DROPS_Y - 10, TEXT_COLOR);

        mobElements.forEach(e -> e.drawForeground(mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawForeground(mouseX, mouseY));
        stackElements.forEach(e -> e.drawForeground(mouseX, mouseY));
    }

    private void addInfoLine(int offset, String tag, String value) {
        int INFO_X = 10;
        int INFO_Y = 10;
        int TEXT_HEIGHT = 10;
        font.drawString(tag, INFO_X, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
        font.drawString(value, INFO_X + 60, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (width - xSize) / 2;
        int relY = (height - ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);

        mobElements.forEach(e -> e.drawBackground(mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawBackground(mouseX, mouseY));
        stackElements.forEach(e -> e.drawBackground(mouseX, mouseY));
    }

    class StackElement {
        int x;
        int y;
        boolean isLocked = false;
        int idx = 0;
        List<ItemStack> itemStacks = new ArrayList<>();
        List<List<String>> tooltips = new ArrayList<>();
        public StackElement(int x, int y, boolean locked) {
            this.x = x;
            this.y = y;
            this.isLocked = locked;
        }

        public StackElement(int x, int y) {
            this(x, y, false);
        }

        public void addDrop(ItemStack itemStack, List<String> tooltip) {
            isLocked = false;
            itemStacks.add(itemStack);
            tooltips.add(tooltip);
        }

        public void cycle() {
            if (!itemStacks.isEmpty())
                idx = (idx + 1) % itemStacks.size();
        }

        public void drawBackground(int mouseX, int mouseY) {
            if (itemStacks.isEmpty())
                return;

            if (isLocked) {
                // TODO draw a cross or something
                return;
            }
        }

        public void drawTooltip(int mouseX, int mouseY)  {
            if (itemStacks.isEmpty())
                    return;

            if (isLocked) {
                // TODO draw a cross or something
                return;
            }

            ItemStack itemStack = itemStacks.get(idx);
            List<String> tooltip = tooltips.get(idx);
            if (isPointInRegion(x, y, 16, 16, mouseX, mouseY)) {
                FontRenderer fontRenderer = itemStack.getItem().getFontRenderer(itemStack);
                if (fontRenderer == null)
                    fontRenderer = font;
                renderTooltip(tooltip, mouseX, mouseY, fontRenderer);
            }
        }


        public void drawForeground(int mouseX, int mouseY) {

            if (itemStacks.isEmpty())
                return;

            if (isLocked) {
                // TODO draw a cross or something
                return;
            }

            ItemStack itemStack = itemStacks.get(idx);
            blitOffset = 100;
            itemRenderer.zLevel = 100.0F;
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableDepthTest();
            itemRenderer.renderItemIntoGUI(itemStack, x, y);
            RenderHelper.disableStandardItemLighting();
            itemRenderer.zLevel = 0.0F;
            blitOffset = 0;
        }
    }
}
