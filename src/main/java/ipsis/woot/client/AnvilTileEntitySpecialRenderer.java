package ipsis.woot.client;

import com.mojang.blaze3d.platform.GlStateManager;
import ipsis.woot.misc.anvil.AnvilTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnvilTileEntitySpecialRenderer extends TileEntityRenderer<AnvilTileEntity> {

    @Override
    public void render(AnvilTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {

        ItemStack itemStack = tileEntityIn.getBaseItem();
        if (!itemStack.isEmpty()) {
            renderStack(itemStack, x + 0.5F, y + 1.0F, z + 0.5F);
        }

        ItemStack[] ingredients = tileEntityIn.getIngredients();
        if (!ingredients[0].isEmpty())
            renderStack(ingredients[0], x + 0.5F + 0.15F, y + 1.0F, z + 0.5F);
        if (!ingredients[1].isEmpty())
            renderStack(ingredients[1], x + 0.5F - 0.15F, y + 1.0F, z + 0.5F);
        if (!ingredients[2].isEmpty())
            renderStack(ingredients[2], x + 0.5F, y + 1.0F, z + 0.5F + 0.15F);
        if (!ingredients[3].isEmpty())
            renderStack(ingredients[3], x + 0.5F, y + 1.0F, z + 0.5F - 0.15F);
    }

    private void renderStack(ItemStack itemStack, double x, double y, double z) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translated(x, y, z);

            float scale = 0.2F;
            GlStateManager.scalef(scale, scale, scale);
            GlStateManager.rotatef(-90, 1, 0, 0);

            GlStateManager.pushMatrix();
            {
                GlStateManager.disableLighting();
                GlStateManager.pushLightingAttributes();
                RenderHelper.enableStandardItemLighting();
                Minecraft.getInstance().getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttributes();
                GlStateManager.enableLighting();
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}
