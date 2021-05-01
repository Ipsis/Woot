package ipsis.woot.modules.oracle.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.Woot;
import ipsis.woot.modules.oracle.blocks.OracleContainer;
import ipsis.woot.simulator.SimulatedMobDropSummary;
import ipsis.woot.util.FakeMob;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class OracleScreen extends ContainerScreen<OracleContainer> {

    private ResourceLocation GUI = new ResourceLocation(Woot.MODID, "textures/gui/oracle.png");

    public OracleScreen(OracleContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        imageWidth = 180;
        imageHeight = 177;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bind(GUI);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, getXSize(), getYSize());

        if (!menu.simulatedDrops.isEmpty()) {
            int currRow = 0;
            int currCol = 0;
            for (SimulatedMobDropSummary summary : menu.simulatedDrops) {

                int stackX = getGuiLeft() + (currCol * 18) + 10;
                int stackY = getGuiTop() + (currRow * 18) + 41;

                RenderHelper.setupForFlatItems();
                itemRenderer.renderGuiItem(summary.itemStack, stackX, stackY);
                RenderHelper.setupFor3DItems();

                currCol++;
                if (currCol == 9) {
                    currCol = 0;
                    currRow++;
                }
            }
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);

        if (menu.simulatedMobs.isEmpty()) {
            String mob = "N/A";
            this.font.draw(matrixStack, mob, (float)(this.getXSize() / 2 - this.font.width(mob) / 2), 25.0F, 4210752);
        } else {
            FakeMob fakeMob = menu.simulatedMobs.get(mobIndex);
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(fakeMob.getResourceLocation());
            if (entityType != null) {
                String mob = new TranslationTextComponent(entityType.getDescriptionId()).getString();
                if (fakeMob.hasTag())
                    mob += "[" + fakeMob.getTag() + "]";
                this.font.draw(matrixStack, mob, (float)(this.getXSize() / 2 - this.font.width(mob) / 2), 25.0F, 4210752);
            }
        }

        if (!menu.simulatedDrops.isEmpty()) {
            int currRow = 0;
            int currCol = 0;
            for (SimulatedMobDropSummary summary : menu.simulatedDrops) {

                int stackX = (currCol * 18) + 10;
                int stackY = (currRow * 18) + 41;


                if (mouseX - getGuiLeft() > stackX && mouseX - getGuiLeft() <= stackX + 20 && mouseY - getGuiTop() >= stackY && mouseY - getGuiTop() <= stackY + 20) {
                    FontRenderer fontRenderer = summary.itemStack.getItem().getFontRenderer(summary.itemStack);
                    if (fontRenderer == null)
                        fontRenderer = font;
                    List<ITextComponent> tooltip = getTooltipFromItem(summary.itemStack);
                    tooltip.add(new TranslationTextComponent("gui.woot.oracle.looting.0", summary.chanceToDrop[0]));
                    tooltip.add(new TranslationTextComponent("gui.woot.oracle.looting.1", summary.chanceToDrop[1]));
                    tooltip.add(new TranslationTextComponent("gui.woot.oracle.looting.2", summary.chanceToDrop[2]));
                    tooltip.add(new TranslationTextComponent("gui.woot.oracle.looting.3", summary.chanceToDrop[3]));
                    renderComponentTooltip(matrixStack, tooltip, mouseX - getGuiLeft(), mouseY - getGuiTop());
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
                this.getGuiLeft() + 9 + (8 * 18),
                this.getGuiTop() + 18, 18, 18, new StringTextComponent(">"),
                h -> {
                    if (!menu.simulatedMobs.isEmpty()) {
                        mobIndex = (mobIndex + 1);
                        mobIndex = MathHelper.clamp(mobIndex, 0, menu.simulatedMobs.size() - 1);
                        menu.refreshDrops(mobIndex);
                    }
                }));

        this.prevMobButton = this.addButton(new Button(
                this.getGuiLeft() + 9,
                this.getGuiTop() + 18, 18, 18, new StringTextComponent("<"),
                h -> {
                    if (!menu.simulatedMobs.isEmpty()) {
                        mobIndex = (mobIndex - 1);
                        mobIndex = MathHelper.clamp(mobIndex, 0, menu.simulatedMobs.size() - 1);
                        menu.refreshDrops(mobIndex);
                    }
                }));

        menu.refreshMobs();
    }
}
