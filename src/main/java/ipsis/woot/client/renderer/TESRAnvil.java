package ipsis.woot.client.renderer;

import ipsis.woot.tileentity.TileEntityAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TESRAnvil extends TileEntitySpecialRenderer<TileEntityAnvil> {

    @Override
    public void render(TileEntityAnvil te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        ItemStack stack = te.getBaseItem();
        if (!stack.isEmpty()) {

            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(x, y, z);

                GlStateManager.translate(0.5F, 1.0F, 0.5F);
                float scale = 0.45F;
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.rotate(-90, 1, 0, 0);

                GlStateManager.pushMatrix();
                {
                    GlStateManager.disableLighting();
                    GlStateManager.pushAttrib();
                    RenderHelper.enableStandardItemLighting();
                    Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.popAttrib();
                    GlStateManager.enableLighting();
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
        }
    }
}
