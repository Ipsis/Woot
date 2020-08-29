package ipsis.woot.modules.oracle.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.helper.StringHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class OracleScreen extends ContainerScreen<OracleContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/oracle.png");

    public OracleScreen(OracleContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        xSize = 180;
        ySize = 177;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);

        if (!container.simulatedDrops.isEmpty()) {
            int currRow = 0;
            int currCol = 0;
            for (SimulatedMobDropSummary summary : container.simulatedDrops) {

                int stackX = guiLeft + (currCol * 18) + 10;
                int stackY = guiTop + (currRow * 18) + 41;

                RenderHelper.enableStandardItemLighting();
                itemRenderer.renderItemIntoGUI(summary.itemStack, stackX, stackY);

                currCol++;
                if (currCol == 9) {
                    currCol = 0;
                    currRow++;
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String text = title.getFormattedText();
        this.font.drawString(text, (float)(this.xSize / 2 - this.font.getStringWidth(text) / 2), 6.0F, 4210752);

        if (container.simulatedMobs.isEmpty()) {
            String mob = "N/A";
            this.font.drawString(mob, (float)(this.xSize / 2 - this.font.getStringWidth(mob) / 2), 25.0F, 4210752);
        } else {
            FakeMob fakeMob = container.simulatedMobs.get(mobIndex);
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
            if (entityType != null) {
                String mob = StringHelper.translate(entityType.getTranslationKey()).getUnformattedComponentText();
                if (fakeMob.hasTag())
                    mob += "[" + fakeMob.getTag() + "]";
                this.font.drawString(mob, (float)(this.xSize / 2 - this.font.getStringWidth(mob) / 2), 25.0F, 4210752);
            }
        }

        if (!container.simulatedDrops.isEmpty()) {
            int currRow = 0;
            int currCol = 0;
            for (SimulatedMobDropSummary summary : container.simulatedDrops) {

                int stackX = (currCol * 18) + 10;
                int stackY = (currRow * 18) + 41;


                if (mouseX - guiLeft > stackX && mouseX - guiLeft <= stackX + 20 && mouseY - guiTop >= stackY && mouseY - guiTop <= stackY + 20) {
                    FontRenderer fontRenderer = summary.itemStack.getItem().getFontRenderer(summary.itemStack);
                    if (fontRenderer == null)
                        fontRenderer = font;
                    List<String> tooltip = getTooltipFromItem(summary.itemStack);
                    tooltip.add(StringHelper.translate("gui.woot.oracle.looting.0", summary.chanceToDrop[0]).getUnformattedComponentText());
                    tooltip.add(StringHelper.translate("gui.woot.oracle.looting.1", summary.chanceToDrop[1]).getUnformattedComponentText());
                    tooltip.add(StringHelper.translate("gui.woot.oracle.looting.2", summary.chanceToDrop[2]).getUnformattedComponentText());
                    tooltip.add(StringHelper.translate("gui.woot.oracle.looting.3", summary.chanceToDrop[3]).getUnformattedComponentText());
                    renderTooltip(tooltip, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
                    break;
                }

                currCol++;
                if (currCol == 9) {
                    currCol = 0;
                    currRow++;
                }
            }
        }
    }

    private Button nextMobButton;
    private Button prevMobButton;
    private int mobIndex = 0;

    @Override
    protected void init() {
        // This is called twice ?
        super.init(); // This sets guiLeft/guiTop

        this.nextMobButton = this.addButton(new Button(
                this.guiLeft + 9 + (8 * 18),
                this.guiTop + 18, 18, 18, ">",
                h -> {
                    if (!container.simulatedMobs.isEmpty()) {
                        mobIndex = (mobIndex + 1);
                        mobIndex = MathHelper.clamp(mobIndex, 0, container.simulatedMobs.size() - 1);
                        container.refreshDrops(mobIndex);
                    }
                }));

        this.prevMobButton = this.addButton(new Button(
                this.guiLeft + 9,
                this.guiTop + 18, 18, 18, "<",
                h -> {
                    if (!container.simulatedMobs.isEmpty()) {
                        mobIndex = (mobIndex - 1);
                        mobIndex = MathHelper.clamp(mobIndex, 0, container.simulatedMobs.size() - 1);
                        container.refreshDrops(mobIndex);
                    }
                }));

        container.refreshMobs();
    }
}
