package ipsis.woot.util.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderHelper {

    public static void drawTexturedCube(TextureAtlasSprite texture, float size) {

        if (texture == null)
            return;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();

        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float minU = texture.getMinU();
        float maxU = texture.getMaxU();
        float minV = texture.getMinV();
        float maxV = texture.getMaxV();

        // xy anti-clockwise - front
        worldRenderer.pos(-size, -size, size).tex(maxU, maxV).endVertex();
        worldRenderer.pos(size, -size, size).tex(minU, maxV).endVertex();
        worldRenderer.pos(size, size, size).tex(minU, minV).endVertex();
        worldRenderer.pos(-size, size, size).tex(maxU, minV).endVertex();

        // xy clockwise - back
        worldRenderer.pos(-size, -size, -size).tex(maxU, maxV).endVertex();
        worldRenderer.pos(-size, size, -size).tex(maxU, minV).endVertex();
        worldRenderer.pos(size, size, -size).tex(minU, minV).endVertex();
        worldRenderer.pos(size, -size, -size).tex(minU, maxV).endVertex();

        // anti-clockwise - left
        worldRenderer.pos(-size, -size, -size).tex(minU, minV).endVertex();
        worldRenderer.pos(-size, -size, size).tex(minU, maxV).endVertex();
        worldRenderer.pos(-size, size, size).tex(maxU, maxV).endVertex();
        worldRenderer.pos(-size, size, -size).tex(maxU, minV).endVertex();

        // clockwise - right
        worldRenderer.pos(size, -size, -size).tex(minU, minV).endVertex();
        worldRenderer.pos(size, size, -size).tex(maxU, minV).endVertex();
        worldRenderer.pos(size, size, size).tex(maxU, maxV).endVertex();
        worldRenderer.pos(size, -size, size).tex(minU, maxV).endVertex();

        // anticlockwise - top
        worldRenderer.pos(-size, size, -size).tex(minU, minV).endVertex();
        worldRenderer.pos(-size, size, size).tex(minU, maxV).endVertex();
        worldRenderer.pos(size, size, size).tex(maxU, maxV).endVertex();
        worldRenderer.pos(size, size, -size).tex(maxU, minV).endVertex();

        // clockwise - bottom
        worldRenderer.pos(-size, -size, -size).tex(minU, minV).endVertex();
        worldRenderer.pos(size, -size, -size).tex(maxU, minV).endVertex();
        worldRenderer.pos(size, -size, size).tex(maxU, maxV).endVertex();
        worldRenderer.pos(-size, -size, size).tex(minU, maxV).endVertex();

        tessellator.draw();
    }

    public static void drawShadedCube(float size) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();

        // Front - anticlockwise vertices
        // Back - clockwise vertices
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        // xy anti-clockwise - front
        worldRenderer.pos(-size, -size, size).endVertex();
        worldRenderer.pos(size, -size, size).endVertex();
        worldRenderer.pos(size, size, size).endVertex();
        worldRenderer.pos(-size, size, size).endVertex();

        // xy clockwise - back
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(-size, size, -size).endVertex();
        worldRenderer.pos(size, size, -size).endVertex();
        worldRenderer.pos(size, -size, -size).endVertex();

        // anti-clockwise - left
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(-size, -size, size).endVertex();
        worldRenderer.pos(-size, size, size).endVertex();
        worldRenderer.pos(-size, size, -size).endVertex();

        // clockwise - right
        worldRenderer.pos(size, -size, -size).endVertex();
        worldRenderer.pos(size, size, -size).endVertex();
        worldRenderer.pos(size, size, size).endVertex();
        worldRenderer.pos(size, -size, size).endVertex();

        // anticlockwise - top
        worldRenderer.pos(-size, size, -size).endVertex();
        worldRenderer.pos(-size, size, size).endVertex();
        worldRenderer.pos(size, size, size).endVertex();
        worldRenderer.pos(size, size, -size).endVertex();

        // clockwise - bottom
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(size, -size, -size).endVertex();
        worldRenderer.pos(size, -size, size).endVertex();
        worldRenderer.pos(-size, -size, size).endVertex();

        tessellator.draw();
    }
}
