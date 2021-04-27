package ipsis.woot.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;

public class EnergyBarHelper {

    public static void drawEnergyBar(MatrixStack matrixStack, Screen screen, int max, int curr, int x, int y, int width, int height) {

        int p = max * 100 / curr;
        p = MathHelper.clamp(p, 0, 100);
        int h = y - (p * height / 100);
        screen.fill(matrixStack, x, h, x + width, y, 0xffff0000);
    }
}
