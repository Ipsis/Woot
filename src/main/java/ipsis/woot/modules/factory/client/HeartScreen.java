package ipsis.woot.modules.factory.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.setup.NetworkChannel;
import ipsis.woot.setup.ServerDataRequest;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.WootContainerScreen;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * There are two types of information displayed here.
 * Static - the factory recipe and drops - custom packet
 * Dynamic - progress - vanilla progress mechanism
 */

@OnlyIn(Dist.CLIENT)
public class HeartScreen extends WootContainerScreen<HeartContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/heart.png");

    public HeartScreen(HeartContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        xSize = GUI_WIDTH;
        ySize = GUI_HEIGHT;
    }

    private List<GuiItemStackElement> dropElements = new ArrayList<>();
    private List<GuiItemStackElement> mobElements = new ArrayList<>();
    private List<GuiItemStackElement> upgradeElements = new ArrayList<>();
    private List<GuiStackElement> recipeElements = new ArrayList<>();
    private StackElement exoticElement = new StackElement(EXOTIC_X, EXOTIC_Y);

    private int GUI_WIDTH = 252;
    private int GUI_HEIGHT = 222;
    private int DROPS_COLS = 13;
    private int DROPS_ROWS = 4;
    private int DROPS_X = 10;
    private int DROPS_Y = 144;
    private int MOBS_X = 10;
    private int MOBS_Y = 76;
    private int PERKS_X = 99;
    private int PERKS_Y = 76;
    private int RECIPE_X = 10;
    private int RECIPE_Y = 110;
    private float DROP_CYCLE_MS = 5000.0F;
    private int TEXT_COLOR = 4210752;
    private static final int TANK_LX = 226;
    private static final int TANK_LY = 8;
    private static final int TANK_RX = 241;
    private static final int TANK_RY = 91;
    private static int EXOTIC_X = 190;
    private static int EXOTIC_Y = 76;

    private long renderTime;

    @Override
    protected void init() {
        super.init();

        // Mobs
        for (int i = 0; i < 4; i++)
            mobElements.add(new GuiItemStackElement(MOBS_X + (i * 18), MOBS_Y, true));

        // Upgrades
        for (int i = 0; i < 4; i++)
            upgradeElements.add(new GuiItemStackElement(PERKS_X + (i * 18), PERKS_Y, true));

        // Recipe

        // Drops
        for (int row = 0; row < DROPS_ROWS; row++) {
            for (int col = 0; col < DROPS_COLS; col++) {
                dropElements.add(new GuiItemStackElement(DROPS_X + (col * 18), DROPS_Y + (row * 18)));
            }
        }

        // Request the static data
        NetworkChannel.channel.sendToServer(new ServerDataRequest(
                ServerDataRequest.Type.HEART_STATIC_DATA,
                container.getPos(),
                ""));
    }

    private int getCapacity() {
        int capacity = 0;
        if (container.getCellType() == 0)
            capacity = FactoryConfiguration.CELL_1_CAPACITY.get();
        else if (container.getCellType() == 1)
            capacity = FactoryConfiguration.CELL_2_CAPACITY.get();
        else if (container.getCellType() == 2)
            capacity = FactoryConfiguration.CELL_3_CAPACITY.get();
        else if (container.getCellType() == 3)
            capacity = FactoryConfiguration.CELL_4_CAPACITY.get();
        return capacity;
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

        mobElements.forEach(e -> e.drawTooltip(matrixStack, mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawTooltip(matrixStack, mouseX, mouseY));
        dropElements.forEach(e -> e.drawTooltip(matrixStack, mouseX, mouseY));
        recipeElements.forEach(e -> e.drawTooltip(matrixStack, mouseX, mouseY));
        exoticElement.drawTooltip(matrixStack, mouseX, mouseY);

        if (renderTime == 0L)
            renderTime = Util.milliTime();

        /*
        if (Util.milliTime() - renderTime > DROP_CYCLE_MS) {
            dropElements.forEach(e -> e.cycle());
            renderTime = Util.milliTime();
        } */

        if (mouseX > guiLeft + TANK_LX && mouseX < guiLeft + TANK_RX && mouseY > guiTop + TANK_LY && mouseY < guiTop + TANK_RY)
            renderFluidTankTooltip(matrixStack, mouseX, mouseY,
                    container.getInputFluid(), getCapacity());
    }

    /**
     * 0,0 is top left hand corner of the gui texture
     */
    private boolean sync = false;

    /**
     * return true if unique drop added
     */
    private boolean addToDropElements(int idx, FakeMob fakeMob, ItemStack itemStack) {
        List<ITextComponent> tooltip = getTooltipFromItem(itemStack);
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
        if (entityType != null) {
            ITextComponent iTextComponent = new TranslationTextComponent(entityType.getTranslationKey());
            tooltip.add(new StringTextComponent(String.format("%s : %.2f%%",
                    iTextComponent.getString(), itemStack.getCount() / 100.0F)));
        }

        boolean found = false;
        for (GuiItemStackElement guiItemStackElement : dropElements) {
            if (guiItemStackElement.itemStack.isItemEqual(itemStack)) {
                guiItemStackElement.addToolTip(tooltip);
                found = true;
                break;
            }
        }
        if (!found) {
            dropElements.get(idx).setItemStack(itemStack);
            dropElements.get(idx).addToolTip(tooltip);
            return true;
        }
        return false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        ClientFactorySetup clientFactorySetup = container.getTileEntity().clientFactorySetup;
        if (clientFactorySetup == null)
            return;

        if (!sync) {
            int idx = 0;

            // Drops
            List<FakeMob> knownMobs = new ArrayList<>();
            for (FakeMob fakeMob : clientFactorySetup.controllerMobs) {
                if (knownMobs.contains(fakeMob))
                    continue;

                knownMobs.add(fakeMob);
                ClientFactorySetup.Mob mobInfo = clientFactorySetup.mobInfo.get(fakeMob);
                for (ItemStack itemStack : mobInfo.drops) {
                    if (addToDropElements(idx, fakeMob, itemStack))
                        idx = (idx + 1) % dropElements.size();
                }
            }

            if (clientFactorySetup.tier != Tier.TIER_1) {
                for (int i = 0; i < 4; i++) {
                    mobElements.get(i).unlock();
                    upgradeElements.get(i).unlock();
                }
            } else {
                mobElements.get(0).unlock();
                upgradeElements.get(0).unlock();
            }

            idx = 0;
            for (FakeMob fakeMob : clientFactorySetup.controllerMobs) {
                ItemStack controllerStack = ControllerTileEntity.getItemStack(fakeMob);
                List<ITextComponent> tooltip = getTooltipFromItem(controllerStack);
                mobElements.get(idx).setItemStack(controllerStack);
                mobElements.get(idx).addToolTip(tooltip);
                mobElements.get(idx).unlock();
                idx++;
            }

            idx = 0;
            for (Perk perk : clientFactorySetup.perks) {
                ItemStack itemStack = PerkItem.getItemStack(perk);
                List<ITextComponent> tooltip = getTooltipFromItem(itemStack);
                for (FakeMob fakeMob : clientFactorySetup.controllerMobs) {
                    MobParam mobParam = clientFactorySetup.mobParams.get(fakeMob);
                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
                    if (entityType != null) {
                        ITextComponent iTextComponent = new TranslationTextComponent(entityType.getTranslationKey());
                        if (Perk.EFFICIENCY_PERKS.contains(perk))
                            tooltip.add(new StringTextComponent(String.format("%s : %d%%", iTextComponent.getString(), mobParam.getPerkEfficiencyValue())));
                        else if (Perk.RATE_PERKS.contains(perk))
                            tooltip.add(new StringTextComponent(String.format("%s : %d%%", iTextComponent.getString(), mobParam.getPerkRateValue())));
                        else if (Perk.MASS_PERKS.contains(perk))
                            tooltip.add(new StringTextComponent(
                                    String.format("%s : %d mobs",
                                            iTextComponent.getString(),
                                            mobParam.getMobCount(true, clientFactorySetup.exotic == Exotic.EXOTIC_E))));
                        else if (Perk.XP_PERKS.contains(perk))
                            tooltip.add(new StringTextComponent(String.format("%s : %d%%", iTextComponent.getString(), mobParam.getPerkXpValue())));
                    }
                }
                if (Perk.TIER_SHARD_PERKS.contains(perk)) {
                    tooltip.add(new StringTextComponent(String.format("%d rolls @ %.2f%%", clientFactorySetup.shardRolls, clientFactorySetup.shardDropChance)));
                    tooltip.add(new StringTextComponent(String.format("Basic: %.2f%%", clientFactorySetup.shardDrops[0])));
                    tooltip.add(new StringTextComponent(String.format("Advanced: %.2f%%", clientFactorySetup.shardDrops[1])));
                    tooltip.add(new StringTextComponent(String.format("Elite: %.2f%%", clientFactorySetup.shardDrops[2])));
                }
                if (clientFactorySetup.perkCapped) {
                    ITextComponent iTextComponent1 = new TranslationTextComponent(clientFactorySetup.tier.getTranslationKey());
                    ITextComponent iTextComponent2 = new TranslationTextComponent("gui.woot.heart.8");
                    tooltip.add(new StringTextComponent(TextFormatting.RED + String.format("%s : %s", iTextComponent2.getString(), iTextComponent1.getString())));
                }
                upgradeElements.get(idx).setItemStack(itemStack);
                upgradeElements.get(idx).addToolTip(tooltip);
                upgradeElements.get(idx).unlock();
                idx = (idx + 1) % upgradeElements.size();
            }

            idx = 0;
            for (ItemStack itemStack : clientFactorySetup.itemIng) {
                GuiItemStackElement stackElement = new GuiItemStackElement(RECIPE_X + (idx * 18), RECIPE_Y);
                List<ITextComponent> tooltip = getTooltipFromItem(itemStack);
                tooltip.add(new StringTextComponent(String.format("%d items", itemStack.getCount())));
                stackElement.setItemStack(itemStack.copy());
                stackElement.addToolTip(tooltip);
                recipeElements.add(stackElement);
                idx++;
            }
            for (FluidStack fluidStack : clientFactorySetup.fluidIng) {
                GuiFluidStackElement stackElement = new GuiFluidStackElement(RECIPE_X + (idx * 18), RECIPE_Y);
                List<ITextComponent> tooltip = new ArrayList<>();
                tooltip.add(fluidStack.getDisplayName());
                tooltip.add(new StringTextComponent(String.format("%d mb", fluidStack.getAmount())));
                stackElement.setStack(fluidStack.copy());
                stackElement.addTooltip(tooltip);
                recipeElements.add(stackElement);
                idx++;
            }

            sync = true;
        }

        if (clientFactorySetup.tier != Tier.TIER_5) {
            exoticElement.isLocked = true;
        } else {
            if (clientFactorySetup.exotic != Exotic.NONE) {
                List<ITextComponent> tooltip = getTooltipFromItem(clientFactorySetup.exotic.getItemStack());
                exoticElement.addDrop(clientFactorySetup.exotic.getItemStack(), tooltip);
            }
        }

        addInfoLine(matrixStack, 0, StringHelper.translate("gui.woot.heart.0"), title.getString());
        addInfoLine(matrixStack, 1, StringHelper.translate(
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1).getTranslationKey()), clientFactorySetup.recipeFluid + " mB");
        addInfoLine(matrixStack, 2,StringHelper.translate("gui.woot.heart.1"), clientFactorySetup.recipeTicks + " ticks");
        addInfoLine(matrixStack, 3, StringHelper.translate("gui.woot.heart.2"), container.getProgress() + "%");

        font.drawString(matrixStack, StringHelper.translate("gui.woot.heart.3"), MOBS_X, MOBS_Y - 10, TEXT_COLOR);
        font.drawString(matrixStack, StringHelper.translate("gui.woot.heart.4"), PERKS_X, PERKS_Y - 10, TEXT_COLOR);
        font.drawString(matrixStack, StringHelper.translate("gui.woot.heart.5"), DROPS_X, DROPS_Y - 10, TEXT_COLOR);
        font.drawString(matrixStack, StringHelper.translate("gui.woot.heart.6"), RECIPE_X, RECIPE_Y - 10, TEXT_COLOR);
        font.drawString(matrixStack, StringHelper.translate("gui.woot.heart.7"), EXOTIC_X, EXOTIC_Y - 10, TEXT_COLOR);

        mobElements.forEach(e -> e.drawForeground(matrixStack, mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawForeground(matrixStack, mouseX, mouseY));
        recipeElements.forEach(e -> e.drawForeground(matrixStack, mouseX, mouseY));
        dropElements.forEach(e -> e.drawForeground(matrixStack, mouseX, mouseY));
        exoticElement.drawForeground(matrixStack, mouseX, mouseY);
    }

    private void addInfoLine(MatrixStack matrixStack, int offset, String tag, String value) {
        int INFO_X = 10;
        int INFO_Y = 10;
        int TEXT_HEIGHT = 10;
        font.drawString(matrixStack, tag, INFO_X, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
        font.drawString(matrixStack, value, INFO_X + 80, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindTexture(GUI);
        int relX = (width - xSize) / 2;
        int relY = (height - ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, xSize, ySize);

        mobElements.forEach(e -> e.drawBackground(mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawBackground(mouseX, mouseY));
        dropElements.forEach(e -> e.drawBackground(mouseX, mouseY));
        recipeElements.forEach(e -> e.drawBackground(mouseX, mouseY));
        exoticElement.drawBackground(mouseX, mouseY);

        renderFluidTank(
                matrixStack,
                TANK_LX,
                TANK_RY,
                TANK_RY - TANK_LY + 1,
                TANK_RX - TANK_LX + 1,
                getCapacity(),
                container.getInputFluid());
    }

    class StackElement {
        int x;
        int y;
        boolean isLocked = false;
        int idx = 0;
        List<ItemStack> itemStacks = new ArrayList<>();
        List<List<ITextComponent>> tooltips = new ArrayList<>();
        public StackElement(int x, int y, boolean locked) {
            this.x = x;
            this.y = y;
            this.isLocked = locked;
        }

        public StackElement(int x, int y) {
            this(x, y, false);
        }

        public void addDrop(ItemStack itemStack, List<ITextComponent> tooltip) {
            isLocked = false;
            itemStacks.add(itemStack);
            tooltips.add(tooltip);
        }

        public void unlock() { isLocked = false; }

        public void cycle() {
            if (!itemStacks.isEmpty())
                idx = (idx + 1) % itemStacks.size();
        }

        public void drawBackground(int mouseX, int mouseY) {
            if (isLocked)
                return;

            if (itemStacks.isEmpty())
                return;
        }

        public void drawTooltip(MatrixStack matrixStack, int mouseX, int mouseY)  {
            if (isLocked)
                return;

            if (itemStacks.isEmpty())
                    return;

            ItemStack itemStack = itemStacks.get(idx);
            List<ITextComponent> tooltip = tooltips.get(idx);
            if (isPointInRegion(x, y, 16, 16, mouseX, mouseY)) {
                FontRenderer fontRenderer = itemStack.getItem().getFontRenderer(itemStack);
                func_243308_b(matrixStack, tooltip, mouseX, mouseY);
            }
        }


        public void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {

            if (isLocked) {
                fill(matrixStack, x - 1, y - 1, x - 1 + 18, y - 1 + 18, -2130706433);
                return;
            }

            if (itemStacks.isEmpty())
                return;

            ItemStack itemStack = itemStacks.get(idx);
            setBlitOffset(100);
            itemRenderer.zLevel = 100.0F;
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableDepthTest();
            itemRenderer.renderItemIntoGUI(itemStack, x, y);
            RenderHelper.disableStandardItemLighting();
            itemRenderer.zLevel = 0.0F;
            setBlitOffset(0);
        }
    }

    class GuiStackElement {
        int posX;
        int posY;
        boolean isLocked;

        public GuiStackElement() {
            isLocked = false;
        }

        public GuiStackElement(int posX, int posY) {
            this();
            this.posX = posX;
            this.posY = posY;
        }

        public GuiStackElement(int posX, int posY, boolean isLocked) {
            this(posX, posY);
            this.isLocked = isLocked;
        }

        public void unlock() {
            isLocked = false;
        }

        public void lock() {
            isLocked = true;
        }

        public void drawBackground(int mouseX, int mouseY) {}
        public void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (isLocked)
                fill(matrixStack, posX - 1, posY - 1, posX - 1 + 18, posY - 1 + 18, -2130706433);
        }

        public void drawTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        }
    }

    public class GuiItemStackElement extends GuiStackElement {
        private ItemStack itemStack;
        List<ITextComponent> tooltip = new ArrayList<>();

        public GuiItemStackElement(int posX, int posY, boolean isLocked) {
            super(posX, posY, isLocked);
            this.itemStack = ItemStack.EMPTY;
        }

        public GuiItemStackElement(int posX, int posY) {
            super(posX, posY);
            this.itemStack = ItemStack.EMPTY;
        }

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack.copy();
        }

        public void addToolTip(List<ITextComponent> tooltip) {
            this.tooltip.addAll(tooltip);
        }

        @Override
        public void drawBackground(int mouseX, int mouseY) {
            if (isLocked || itemStack.isEmpty())
                return;
        }

        @Override
        public void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
            super.drawForeground(matrixStack, mouseX, mouseY);

            if (itemStack.isEmpty())
                return;

            setBlitOffset(100);
            itemRenderer.zLevel = 100.0F;
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableDepthTest();
            itemRenderer.renderItemIntoGUI(itemStack, posX, posY);
            RenderHelper.disableStandardItemLighting();
            itemRenderer.zLevel = 0.0F;
            setBlitOffset(0);
        }

        @Override
        public void drawTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (isLocked || itemStack.isEmpty() || tooltip.isEmpty())
                return;

            if (!isPointInRegion(posX, posY, 16, 16, mouseX, mouseY))
                return;

            func_243308_b(matrixStack, tooltip, mouseX, mouseY);
        }
    }

    public class GuiFluidStackElement extends GuiStackElement {

        private FluidStack fluidStack;
        private List<ITextComponent> tooltip = new ArrayList<>();

        public GuiFluidStackElement(int posX, int posY, boolean isLocked) {
            super(posX, posY, isLocked);
            this.fluidStack = FluidStack.EMPTY;
        }

        public GuiFluidStackElement(int posX, int posY) {
            super(posX, posY);
            this.fluidStack = FluidStack.EMPTY;
        }

        public void setStack(FluidStack fluidStack) {
            this.fluidStack = fluidStack.copy();
        }

        public void addTooltip(List<ITextComponent> tooltip) {
            this.tooltip.addAll(tooltip);
        }

        @Override
        public void drawBackground(int mouseX, int mouseY) {
            if (isLocked || fluidStack.isEmpty())
                return;

            drawFluid(guiLeft + posX, guiTop + posY, fluidStack, 16, 16);
        }

        @Override
        public void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
            super.drawForeground(matrixStack, mouseX, mouseY);

        }

        @Override
        public void drawTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
            if (isLocked || fluidStack.isEmpty() || tooltip.isEmpty())
                return;

            if (!isPointInRegion(posX, posY, 16, 16, mouseX, mouseY))
                return;

            func_243308_b(matrixStack, tooltip, mouseX, mouseY);
        }
    }
}
