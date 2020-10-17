package ipsis.woot.modules.anvil.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import ipsis.woot.modules.anvil.blocks.AnvilTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnvilTileEntitySpecialRenderer extends TileEntityRenderer<AnvilTileEntity> {

    public AnvilTileEntitySpecialRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(AnvilTileEntity anvilTileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay) {

        ItemStack itemStack = anvilTileEntity.getBaseItem();
        if (!itemStack.isEmpty()) {
            renderStack(itemStack, matrixStack, iRenderTypeBuffer, 0.5F, 1.05F, 0.5F, combinedLight, combinedOverlay);
        }

        ItemStack[] ingredients = anvilTileEntity.getIngredients();
        if (!ingredients[0].isEmpty())
            renderStack(ingredients[0],  matrixStack, iRenderTypeBuffer, 0.5F, 1.05F, 0.5F - 0.2F, combinedLight, combinedOverlay);
        if (!ingredients[1].isEmpty())
            renderStack(ingredients[1],  matrixStack, iRenderTypeBuffer, 0.5F, 1.05F, 0.5F + 0.2F, combinedLight, combinedOverlay);
        if (!ingredients[2].isEmpty())
            renderStack(ingredients[2],  matrixStack, iRenderTypeBuffer, 0.5F, 1.05F, 0.5F - 0.4F, combinedLight, combinedOverlay);
        if (!ingredients[3].isEmpty())
            renderStack(ingredients[3],  matrixStack, iRenderTypeBuffer, 0.5F, 1.05F, 0.5F + 0.4F, combinedLight, combinedOverlay);
    }

    private void renderStack(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double x, double y, double z, int combinedLight, int combinedOverlay) {
        float scale = 0.20F;
        matrixStack.push();
        matrixStack.translate(x, y, z);
        matrixStack.scale(scale, scale, scale);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90));

        Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, iRenderTypeBuffer);
        matrixStack.pop();;
    }
}
