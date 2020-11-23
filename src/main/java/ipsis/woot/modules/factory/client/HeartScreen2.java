package ipsis.woot.modules.factory.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.factory.Exotic;
import ipsis.woot.modules.factory.Tier;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.recipe.HeartRecipe;
import ipsis.woot.modules.factory.recipe.HeartSummary;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class HeartScreen2 extends WootContainerScreen<HeartContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/heart.png");

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

    public HeartScreen2(HeartContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        xSize = GUI_WIDTH;
        ySize = GUI_HEIGHT;
    }

    private List<GuiItemStackElement> mobElements = new ArrayList<>();
    private List<GuiItemStackElement> perkElements = new ArrayList<>();
    private List<GuiStackElement> recipeElements = new ArrayList<>();
    private List<GuiItemStackElement> dropElements = new ArrayList<>();
    private GuiItemStackElement exoticElement;

    @Override
    protected void init() {
        super.init();

        for (int i = 0; i < 4; i++)
            mobElements.add(new GuiItemStackElement(MOBS_X + (i * 18), MOBS_Y, true));

        for (int i = 0; i < 4; i++)
            perkElements.add(new GuiItemStackElement(PERKS_X + (i * 18), PERKS_Y, true));

        // recipeElements has no entries until we get the data from the server

        for (int row = 0; row < DROPS_ROWS; row++) {
            for (int col = 0; col < DROPS_COLS; col++) {
                dropElements.add(new GuiItemStackElement(
                        DROPS_X + (col * 18), DROPS_Y + (row * 19)));
            }
        }

        exoticElement = new GuiItemStackElement(EXOTIC_X, EXOTIC_Y, true);

        NetworkChannel.channel.sendToServer(new ServerDataRequest(
                ServerDataRequest.Type.HEART_STATIC_DATA,
                container.getPos(),
                ""));
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

        mobElements.forEach(e -> e.drawTooltip( mouseX, mouseY));
        perkElements.forEach(e -> e.drawTooltip(mouseX, mouseY));
        recipeElements.forEach(e -> e.drawTooltip(mouseX, mouseY));
        dropElements.forEach(e -> e.drawTooltip(mouseX, mouseY));
        exoticElement.drawTooltip(mouseX, mouseY);

        if (mouseX > guiLeft + TANK_LX && mouseX < guiLeft + TANK_RX && mouseY > guiTop + TANK_LY && mouseY < guiTop + TANK_RY)
            renderFluidTankTooltip(mouseX, mouseY, container.getInputFluid(), container.getCapacity());
    }

    /**
     * 0,0 is top left hand corner of the gui texture
     */

    private boolean staticConfigSynced = false;
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        /**
         * All the gui text
         */
        this.font.drawString(this.title.getFormattedText(), (float)(this.xSize / 2 - this.font.getStringWidth(this.title.getFormattedText()) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);

        HeartSummary summary = container.getTileEntity().clientHeartSummary;
        if (summary == null)
            return;

        if (!staticConfigSynced)
            setupSyncedData(summary);

        addInfoLine(0, StringHelper.translate("gui.woot.heart.0").getUnformattedComponentText(), title.getFormattedText());
        addInfoLine(1, StringHelper.translate(
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1).getTranslationKey()).getUnformattedComponentText(), summary.getRecipeFluid() + " mB");
        addInfoLine(2,StringHelper.translate("gui.woot.heart.1").getUnformattedComponentText(), summary.getRecipeTicks() + " ticks");
        addInfoLine(3, StringHelper.translate("gui.woot.heart.2").getUnformattedComponentText(), container.getProgress() + "%");

        font.drawString(StringHelper.translate("gui.woot.heart.3").getUnformattedComponentText(), MOBS_X, MOBS_Y - 10, TEXT_COLOR);
        font.drawString(StringHelper.translate("gui.woot.heart.4").getUnformattedComponentText(), PERKS_X, PERKS_Y - 10, TEXT_COLOR);
        font.drawString(StringHelper.translate("gui.woot.heart.5").getUnformattedComponentText(), DROPS_X, DROPS_Y - 10, TEXT_COLOR);

        mobElements.forEach(e -> e.drawForeground());
        perkElements.forEach(e -> e.drawForeground());
        recipeElements.forEach(e -> e.drawForeground());
        dropElements.forEach(e -> e.drawForeground());
        exoticElement.drawForeground();


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (width - xSize) / 2;
        int relY = (height - ySize) / 2;
        this.blit(relX, relY, 0, 0, xSize, ySize);

        mobElements.forEach(e -> e.drawBackground());
        perkElements.forEach(e -> e.drawBackground());
        recipeElements.forEach(e -> e.drawBackground());
        dropElements.forEach(e -> e.drawBackground());
        exoticElement.drawBackground();

        renderFluidTank(
                TANK_LX,
                TANK_RY,
                TANK_RY - TANK_LY + 1,
                TANK_RX - TANK_LX + 1,
                container.getCapacity(),
                container.getInputFluid());
    }

    private void setupSyncedData(HeartSummary summary) {

        // Tier controls which parts of the gui are enabled/disabled
        // All non-drop elements are disabled by default
        Tier tier = summary.getTier();
        if (tier == Tier.TIER_5) {
            exoticElement.unlock();
            Exotic exotic = summary.getExotic();
            if (exotic != Exotic.NONE) {
                List<String> tooltip = getTooltipFromItem(exotic.getItemStack());
                exoticElement.setStack(exotic.getItemStack());
                exoticElement.addTooltip(tooltip);
            }
        }
        if (tier == Tier.TIER_1) {
            mobElements.get(0).unlock();
            perkElements.get(0).unlock();
        } else {
            mobElements.forEach(i -> i.unlock());
            perkElements.forEach( i -> i.unlock());
        }

        /**
         * Mobs
         */
        AtomicInteger idx = new AtomicInteger(0);
        summary.getMobs().forEach(m -> {
            if (idx.get() < 4) {
                ItemStack itemStack = ControllerTileEntity.getItemStack(m);
                mobElements.get(idx.get()).setStack(itemStack);
                mobElements.get(idx.get()).addTooltip(getTooltipFromItem(itemStack));
                idx.getAndIncrement();
            }
        });

        /**
         * Perks
         */
        idx.set(0);
        summary.getPerks().forEach(p -> {
            if (idx.get() < 4) {
                ItemStack itemStack = PerkItem.getItemStack(p.perk);
                perkElements.get(idx.get()).setStack(itemStack);
                perkElements.get(idx.get()).addTooltip(getTooltipFromItem(itemStack));
                for (Map.Entry<FakeMob, Float> e : p.configs.entrySet()) {
                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(e.getKey().getResourceLocation());
                    if (entityType != null) {
                        ITextComponent iTextComponent = StringHelper.translate(entityType.getTranslationKey());
                    }
                }

                idx.getAndIncrement();
            }
        });

        /**
         * Recipe
         */
        idx.set(0);
        summary.getIngredients().forEach(i -> {
            if (i instanceof HeartRecipe.ItemIngredient) {
                GuiItemStackElement element = new GuiItemStackElement(RECIPE_X + (idx.get() * 18), RECIPE_Y);
                ItemStack itemStack = ((HeartRecipe.ItemIngredient) i).getItemStack().copy();
                itemStack.setCount(i.getAmount());
                element.setStack(itemStack);
                element.addTooltip(getTooltipFromItem(itemStack));
                idx.getAndIncrement();
            } else if (i instanceof HeartRecipe.FluidIngredient) {
                GuiFluidStackElement element = new GuiFluidStackElement(RECIPE_X + (idx.get() * 18), RECIPE_Y);
                FluidStack fluidStack = ((HeartRecipe.FluidIngredient) i).getFluidStack().copy();
                fluidStack.setAmount(i.getAmount());
                element.setStack(fluidStack);
                List<String> tooltip = new ArrayList<>();
                tooltip.add(fluidStack.getDisplayName().getFormattedText());
                tooltip.add(String.format("%d mb", fluidStack.getAmount()));
                element.addTooltip(tooltip);
                idx.getAndIncrement();
            }
        });

        /**
         * Drops
         */
        idx.set(0);
        summary.getDrops().forEach(d -> {
            if (idx.get() < DROPS_COLS * DROPS_ROWS) {
                List<String> tooltip = getTooltipFromItem(d.itemStack);
                for (Map.Entry<FakeMob, Float> e : d.dropChances.entrySet()) {
                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(e.getKey().getResourceLocation());
                    if (entityType != null) {
                        ITextComponent iTextComponent = StringHelper.translate(entityType.getTranslationKey());
                        tooltip.add(String.format("%s : %.2f%%",
                                iTextComponent.getFormattedText(),
                                e.getValue()));
                    }
                }
                dropElements.get(idx.get()).setStack(d.itemStack);
                dropElements.get(idx.get()).addTooltip(tooltip);
                idx.getAndIncrement();
            }
        });

        staticConfigSynced = true;
    }

    private void addInfoLine(int offset, String tag, String value) {
        int INFO_X = 10;
        int INFO_Y = 10;
        int TEXT_HEIGHT = 10;
        font.drawString(tag, INFO_X, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
        font.drawString(value, INFO_X + 80, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
    }

    /****************************************************************
     *
     * Stack elements
     */

    class GuiStackElement {

        int posX;
        int posY;
        boolean isLocked;

        public GuiStackElement() {
            this.isLocked = false;
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

        public void drawBackground() {
        }

        public void drawForeground() {
            if (isLocked)
                fill(posX - 1, posY - 1, posX - 1 + 18, posY - 1 + 18, -2130706433);

        }

        public void drawTooltip(int mouseX, int mouseY) {
        }
    }

    public class GuiItemStackElement extends GuiStackElement {

        private ItemStack itemStack;
        private List<String> tooltip = new ArrayList<>();

        public GuiItemStackElement(int posX, int posY, boolean islocked) {
            super(posX, posY, islocked);
            this.itemStack = ItemStack.EMPTY;
        }

        public GuiItemStackElement(int posX, int posY) {
            super(posX, posY);
            this.itemStack = ItemStack.EMPTY;
        }

        public void setStack(ItemStack itemStack) {
            this.itemStack = itemStack.copy();
        }

        public void addTooltip(List<String> tooltip) {
            this.tooltip.addAll(tooltip);
        }

        @Override
        public void drawBackground() {
            if (isLocked || itemStack.isEmpty())
                return;
        }

        @Override
        public void drawForeground() {
            super.drawForeground();

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
        public void drawTooltip(int mouseX, int mouseY) {
            if (isLocked || itemStack.isEmpty() || tooltip.isEmpty())
                return;

            if (!isPointInRegion(posX, posY, 16, 16, mouseX, mouseY))
                return;

            FontRenderer fontRenderer = itemStack.getItem().getFontRenderer(itemStack);
            if (fontRenderer == null)
                fontRenderer = font;
            renderTooltip(tooltip, mouseX, mouseY, fontRenderer);
        }
    }

    public class GuiFluidStackElement extends GuiStackElement {

        private FluidStack fluidStack;
        private List<String> tooltip = new ArrayList<>();

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

        public void addTooltip(List<String> tooltip) {
            this.tooltip.addAll(tooltip);
        }

        @Override
        public void drawBackground() {
            if (isLocked || fluidStack.isEmpty())
                return;

        }

        @Override
        public void drawForeground() {
            super.drawForeground();

            if (fluidStack.isEmpty())
                return;
        }

        @Override
        public void drawTooltip(int mouseX, int mouseY) {
            if (isLocked || fluidStack.isEmpty() || tooltip.isEmpty())
                return;

            if (!isPointInRegion(posX, posY, 16, 16, mouseX, mouseY))
                return;

            renderTooltip(tooltip, mouseX, mouseY, font);
        }
    }
}
