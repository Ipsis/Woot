package ipsis.woot.modules.squeezer.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import ipsis.woot.Woot;
import ipsis.woot.modules.squeezer.blocks.DyeSqueezerTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DyeSqueezerGUI extends ContainerScreen<DyeSqueezerContainer> {

    private static final ResourceLocation background = new ResourceLocation(Woot.MODID, "textures/gui/dyesqueezer.png");

    private final DyeSqueezerContainer container;
    private final DyeSqueezerTileEntity te;

    public DyeSqueezerGUI(DyeSqueezerContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.container = container;
        this.te = container.getTe();
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bind(background);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int progress = 75;
        if (progress > 0) {
            int l = MathHelper.clamp((progress * 28) / 100, 0, 28);
            this.blit(matrixStack, i + 73, j + 24, 176, 0, l, 37);
        }

        // render red, yellow, blue, white internal tanks
        // render energy
        // render output tank
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
    }
}
