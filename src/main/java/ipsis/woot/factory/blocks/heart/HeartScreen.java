package ipsis.woot.factory.blocks.heart;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryUIInfo;
import ipsis.woot.factory.items.UpgradeItem;
import ipsis.woot.mod.ModItems;
import ipsis.woot.network.HeartStaticDataRequest;
import ipsis.woot.network.Network;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    @Override
    protected void init() {
        super.init();

        // Request the static data
        Network.channel.sendToServer(new HeartStaticDataRequest(container.getPos()));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * 0,0 is top left hand corner of the gui texture
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        FactoryUIInfo factoryUIInfo = container.getFactoryUIInfo();
        if (factoryUIInfo == null)
            return;

        int TEXT_COLOR = 4210752;

        // TODO Can only render 20 itemstacks!???
        // I'm going to assume that I am an idiot
        font.drawString("Effort", 10, 10, TEXT_COLOR);
        font.drawString(": " + factoryUIInfo.recipeEffort + " mB", 70, 10, TEXT_COLOR);
        font.drawString("Time", 10, 20, TEXT_COLOR);
        font.drawString(": " + factoryUIInfo.recipeTicks + " ticks", 70, 20, TEXT_COLOR);
        font.drawString("Rate", 10, 30, TEXT_COLOR);
        font.drawString(": " + factoryUIInfo.recipeCostPerTick + " mb/tick ", 70, 30, TEXT_COLOR);
        font.drawString("Stored", 10, 40, TEXT_COLOR);
        font.drawString(": " + factoryUIInfo.effortStored + " mB ", 70, 40, TEXT_COLOR);
        font.drawString("Progress", 10, 50, TEXT_COLOR);
        font.drawString(": 15% ", 70, 50, TEXT_COLOR);

        int MOBS_X = 10;
        int MOBS_Y = 76;
        font.drawString("Mobs", MOBS_X, 66.0F, TEXT_COLOR);
        for (int i = 0; i < factoryUIInfo.mobs.size(); i ++)
            drawItemStack(factoryUIInfo.mobs.get(i), MOBS_X + (i * 18), MOBS_Y, false, "");

        int UPGRADES_X = 91;
        int UPGRADES_Y = 76;
        font.drawString("Upgrades", UPGRADES_X, 66.0F, TEXT_COLOR);
        for (int i = 0; i < factoryUIInfo.upgrades.size(); i++) {
            drawItemStack(UpgradeItem.getItemStack(factoryUIInfo.upgrades.get(i)),
                    UPGRADES_X + (i * 18), UPGRADES_Y, false, "");
        }

        int RECIPE_X = 172;
        int RECIPE_Y = 76;
        ItemStack mobStack = new ItemStack(Items.DIRT);
        font.drawString("Recipe", RECIPE_X, 66.0F, TEXT_COLOR);
        for (int i = 0; i < 4; i ++)
            drawItemStack(mobStack, RECIPE_X + (i * 18), RECIPE_Y, false, "");

        int DROPS_X = 10;
        int DROPS_Y = 110;
        int DROPS_COLS = 13;
        int DROPS_ROWS = 2;
        font.drawString("Drops:", 10, 100, 4210752);
        int row = 0;
        int col = 0;
        for (ItemStack itemStack : factoryUIInfo.drops) {
            drawItemStack(itemStack,
                    DROPS_X + (col * 18),
                    DROPS_Y + (row * 18),
                    true, Integer.toString(itemStack.getCount()));
            col++;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (width - xSize) / 2;
        int relY = (height - ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);
    }

    public void drawItemStack(ItemStack stack, int x, int y, boolean drawOverlay, String overlayTxt) {
        GlStateManager.translatef(0.0F, 0.0F, 32.0F);
        blitOffset = 200;
        itemRenderer.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null)
            font = this.font;

        itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        if (drawOverlay)
            itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - 8, overlayTxt);

        blitOffset = 0;
        itemRenderer.zLevel = 0.0F;
    }

}
