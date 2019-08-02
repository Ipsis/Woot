package ipsis.woot.factory.blocks.heart;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.mod.ModItems;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeartScreen extends ContainerScreen<HeartContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/heart.png");

    public HeartScreen(HeartContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        xSize = 216;
        ySize = 152;
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

        // TODO Can only render 20 itemstacks!???
        // I'm going to assume that I am an idiot
        font.drawString("Effort", 10, 10, 4210752);
        font.drawString(": 12000mB", 70, 10, 4210752);
        font.drawString("Time", 10, 20, 4210752);
        font.drawString(": 3000 ticks", 70, 20, 4210752);
        font.drawString("Rate", 10, 30, 4210752);
        font.drawString(": 10 mb/tick ", 70, 30, 4210752);
        font.drawString("Stored", 10, 40, 4210752);
        font.drawString(": 3123234 mB ", 70, 40, 4210752);
        font.drawString("Progress", 10, 50, 4210752);
        font.drawString(": 15% ", 70, 50, 4210752);

        font.drawString("Mobs", 10, 66.0F, 4210752);

        // TODO swap these to controllers or shards
        ItemStack mobStack = new ItemStack(Items.DRAGON_EGG);
        for (int i = 0; i < 4; i ++)
            drawItemStack(mobStack, 10 + (i * 18), 76, false, "");

        font.drawString("Upgrades", 136, 66.0F, 4210752);
        ItemStack[] upgrades = new ItemStack[]{
                new ItemStack(ModItems.MASS_1_ITEM),
                new ItemStack(ModItems.EFFICIENCY_3_ITEM),
                new ItemStack(ModItems.LOOTING_3_ITEM),
                new ItemStack(ModItems.CAPACITY_3_ITEM),
        };
        for (int i = 0; i < 4; i ++)
            drawItemStack(upgrades[i], 136 + (i * 18), 76, false, "");

        font.drawString("Drops:", 10, 100, 4210752);
        ItemStack itemStack = new ItemStack(Items.DIAMOND_SWORD);
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 11; col++) {
                drawItemStack(itemStack, 10 + (col * 18), 110 + (row * 18),false, "2.4%");
            }
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
