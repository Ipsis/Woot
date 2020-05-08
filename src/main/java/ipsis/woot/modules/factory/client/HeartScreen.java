package ipsis.woot.modules.factory.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.fluilds.FluidSetup;
import ipsis.woot.modules.factory.*;
import ipsis.woot.modules.factory.blocks.ControllerTileEntity;
import ipsis.woot.modules.factory.blocks.HeartContainer;
import ipsis.woot.modules.factory.items.PerkItem;
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
    private static final int TANK_LX = 226;
    private static final int TANK_LY = 8;
    private static final int TANK_RX = 241;
    private static final int TANK_RY = 91;

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

        if (mouseX > guiLeft + TANK_LX && mouseX < guiLeft + TANK_RX && mouseY > guiTop + TANK_LY && mouseY < guiTop + TANK_RY)
            renderFluidTankTooltip(mouseX, mouseY,
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), container.getTileEntity().getClientFluidAmount()),
                container.getTileEntity().getClientFluidCapacity());
    }

    /**
     * 0,0 is top left hand corner of the gui texture
     */
    private boolean sync = false;
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {


        ClientFactorySetup clientFactorySetup = container.getTileEntity().clientFactorySetup;
        if (clientFactorySetup == null)
            return;

        if (!sync) {
            int idx = 0;

            // Drops
            for (FakeMob fakeMob : clientFactorySetup.controllerMobs) {
                ClientFactorySetup.Mob mobInfo = clientFactorySetup.mobInfo.get(fakeMob);
                for (ItemStack itemStack : mobInfo.drops) {
                    List<String> tooltip = getTooltipFromItem(itemStack);
                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
                    if (entityType != null) {
                        ITextComponent iTextComponent = new TranslationTextComponent(entityType.getTranslationKey());
                        tooltip.add(String.format("%s : %.2f%%",
                                        iTextComponent.getFormattedText(),
                                        itemStack.getCount() / 100.0F));
                    }
                    stackElements.get(idx).addDrop(itemStack, tooltip);
                    idx = (idx + 1) % stackElements.size();
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
                List<String> tooltip = getTooltipFromItem(controllerStack);
                ClientFactorySetup.Mob mobInfo = clientFactorySetup.mobInfo.get(fakeMob);
                if (!mobInfo.itemIngredients.isEmpty()) {
                    for (ItemStack itemStack : mobInfo.itemIngredients) {
                        ITextComponent itextcomponent = (new StringTextComponent("")).appendSibling(itemStack.getDisplayName()).applyTextStyle(itemStack.getRarity().color);
                        tooltip.add("Ingredient: " + itemStack.getCount() + " * " + itextcomponent.getFormattedText());
                    }
                }
                if (!mobInfo.fluidIngredients.isEmpty()) {
                    for (FluidStack fluidStack : mobInfo.fluidIngredients)
                        tooltip.add("Ingredient: " + fluidStack.getAmount() + "mb " + fluidStack.toString());
                }
                mobElements.get(idx).addDrop(controllerStack, tooltip);
                mobElements.get(idx).unlock();
                idx++;
            }

            idx = 0;
            for (Perk perk : clientFactorySetup.perks) {
                ItemStack itemStack = PerkItem.getItemStack(perk);
                List<String> tooltip = getTooltipFromItem(itemStack);
                for (FakeMob fakeMob : clientFactorySetup.controllerMobs) {
                    MobParam mobParam = clientFactorySetup.mobParams.get(fakeMob);
                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
                    if (entityType != null) {
                        ITextComponent iTextComponent = new TranslationTextComponent(entityType.getTranslationKey());
                        if (Perk.EFFICIENCY_PERKS.contains(perk))
                            tooltip.add(String.format("%s : %d%%", iTextComponent.getFormattedText(), mobParam.getPerkEfficiencyValue()));
                        else if (Perk.RATE_PERKS.contains(perk))
                            tooltip.add(String.format("%s : %d%%", iTextComponent.getFormattedText(), mobParam.getPerkRateValue()));
                        else if (Perk.MASS_PERKS.contains(perk))
                            tooltip.add(String.format("%s : %d mobs", iTextComponent.getFormattedText(), mobParam.getMobCount(true)));
                        else if (Perk.XP_PERKS.contains(perk))
                            tooltip.add(String.format("%s : %d%%", iTextComponent.getFormattedText(), mobParam.getPerkXpValue()));
                    }
                }
                if (Perk.TIER_SHARD_PERKS.contains(perk)) {
                    tooltip.add(String.format("%d rolls @ %.2f%%", clientFactorySetup.shardRolls, clientFactorySetup.shardDropChance));
                    tooltip.add(String.format("Basic: %.2f%%", clientFactorySetup.shardDrops[0]));
                    tooltip.add(String.format("Advanced: %.2f%%", clientFactorySetup.shardDrops[1]));
                    tooltip.add(String.format("Elite: %.2f%%", clientFactorySetup.shardDrops[2]));
                }
                upgradeElements.get(idx).addDrop(itemStack, tooltip);
                upgradeElements.get(idx).unlock();
                idx = (idx + 1) % upgradeElements.size();
            }
            sync = true;
        }


        addInfoLine(0, StringHelper.translate("gui.woot.heart.0"), title.getFormattedText());
        addInfoLine(1, StringHelper.translate(
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), 1).getTranslationKey()), clientFactorySetup.recipeFluid + " mB");
        addInfoLine(2,StringHelper.translate("gui.woot.heart.1"), clientFactorySetup.recipeTicks + " ticks");
        addInfoLine(3, StringHelper.translate("gui.woot.heart.2"), container.getProgress() + "%");


        font.drawString(StringHelper.translate("gui.woot.heart.3"), MOBS_X, MOBS_Y - 10, TEXT_COLOR);
        font.drawString(StringHelper.translate("gui.woot.heart.4"), UPGRADES_X, UPGRADES_Y - 10, TEXT_COLOR);
        font.drawString(StringHelper.translate("gui.woot.heart.5"), DROPS_X, DROPS_Y - 10, TEXT_COLOR);

        mobElements.forEach(e -> e.drawForeground(mouseX, mouseY));
        upgradeElements.forEach(e -> e.drawForeground(mouseX, mouseY));
        stackElements.forEach(e -> e.drawForeground(mouseX, mouseY));
    }

    private void addInfoLine(int offset, String tag, String value) {
        int INFO_X = 10;
        int INFO_Y = 10;
        int TEXT_HEIGHT = 10;
        font.drawString(tag, INFO_X, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
        font.drawString(value, INFO_X + 80, INFO_Y + (TEXT_HEIGHT * offset), TEXT_COLOR);
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

        renderFluidTank(
                TANK_LX,
                TANK_RY,
                TANK_RY - TANK_LY + 1,
                TANK_RX - TANK_LX + 1,
                container.getTileEntity().getClientFluidAmount(),
                container.getTileEntity().getClientFluidCapacity(),
                new FluidStack(FluidSetup.CONATUS_FLUID.get(), container.getTileEntity().getClientFluidAmount()));
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

        public void drawTooltip(int mouseX, int mouseY)  {
            if (isLocked)
                return;

            if (itemStacks.isEmpty())
                    return;

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

            if (isLocked) {
                fill(x - 1, y - 1, x - 1 + 18, y - 1 + 18, -2130706433);
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
}
