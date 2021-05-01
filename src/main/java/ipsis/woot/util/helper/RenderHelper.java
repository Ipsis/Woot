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
        BufferBuilder worldRenderer = tessellator.getBuilder();

        Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float minU = texture.getU0();
        float maxU = texture.getU1();
        float minV = texture.getV0();
        float maxV = texture.getV1();

        // xy anti-clockwise - front
        worldRenderer.vertex(-size, -size, size).uv(maxU, maxV).endVertex();
        worldRenderer.vertex(size, -size, size).uv(minU, maxV).endVertex();
        worldRenderer.vertex(size, size, size).uv(minU, minV).endVertex();
        worldRenderer.vertex(-size, size, size).uv(maxU, minV).endVertex();

        // xy clockwise - back
        worldRenderer.vertex(-size, -size, -size).uv(maxU, maxV).endVertex();
        worldRenderer.vertex(-size, size, -size).uv(maxU, minV).endVertex();
        worldRenderer.vertex(size, size, -size).uv(minU, minV).endVertex();
        worldRenderer.vertex(size, -size, -size).uv(minU, maxV).endVertex();

        // anti-clockwise - left
        worldRenderer.vertex(-size, -size, -size).uv(minU, minV).endVertex();
        worldRenderer.vertex(-size, -size, size).uv(minU, maxV).endVertex();
        worldRenderer.vertex(-size, size, size).uv(maxU, maxV).endVertex();
        worldRenderer.vertex(-size, size, -size).uv(maxU, minV).endVertex();

        // clockwise - right
        worldRenderer.vertex(size, -size, -size).uv(minU, minV).endVertex();
        worldRenderer.vertex(size, size, -size).uv(maxU, minV).endVertex();
        worldRenderer.vertex(size, size, size).uv(maxU, maxV).endVertex();
        worldRenderer.vertex(size, -size, size).uv(minU, maxV).endVertex();

        // anticlockwise - top
        worldRenderer.vertex(-size, size, -size).uv(minU, minV).endVertex();
        worldRenderer.vertex(-size, size, size).uv(minU, maxV).endVertex();
        worldRenderer.vertex(size, size, size).uv(maxU, maxV).endVertex();
        worldRenderer.vertex(size, size, -size).uv(maxU, minV).endVertex();

        // clockwise - bottom
        worldRenderer.vertex(-size, -size, -size).uv(minU, minV).endVertex();
        worldRenderer.vertex(size, -size, -size).uv(maxU, minV).endVertex();
        worldRenderer.vertex(size, -size, size).uv(maxU, maxV).endVertex();
        worldRenderer.vertex(-size, -size, size).uv(minU, maxV).endVertex();

        tessellator.end();
    }

    public static void drawShadedCube(float size) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuilder();

        // Front - anticlockwise vertices
        // Back - clockwise vertices
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        // xy anti-clockwise - front
        worldRenderer.vertex(-size, -size, size).endVertex();
        worldRenderer.vertex(size, -size, size).endVertex();
        worldRenderer.vertex(size, size, size).endVertex();
        worldRenderer.vertex(-size, size, size).endVertex();

        // xy clockwise - back
        worldRenderer.vertex(-size, -size, -size).endVertex();
        worldRenderer.vertex(-size, size, -size).endVertex();
        worldRenderer.vertex(size, size, -size).endVertex();
        worldRenderer.vertex(size, -size, -size).endVertex();

        // anti-clockwise - left
        worldRenderer.vertex(-size, -size, -size).endVertex();
        worldRenderer.vertex(-size, -size, size).endVertex();
        worldRenderer.vertex(-size, size, size).endVertex();
        worldRenderer.vertex(-size, size, -size).endVertex();

        // clockwise - right
        worldRenderer.vertex(size, -size, -size).endVertex();
        worldRenderer.vertex(size, size, -size).endVertex();
        worldRenderer.vertex(size, size, size).endVertex();
        worldRenderer.vertex(size, -size, size).endVertex();

        // anticlockwise - top
        worldRenderer.vertex(-size, size, -size).endVertex();
        worldRenderer.vertex(-size, size, size).endVertex();
        worldRenderer.vertex(size, size, size).endVertex();
        worldRenderer.vertex(size, size, -size).endVertex();

        // clockwise - bottom
        worldRenderer.vertex(-size, -size, -size).endVertex();
        worldRenderer.vertex(size, -size, -size).endVertex();
        worldRenderer.vertex(size, -size, size).endVertex();
        worldRenderer.vertex(-size, -size, size).endVertex();

        tessellator.end();
    }
}
