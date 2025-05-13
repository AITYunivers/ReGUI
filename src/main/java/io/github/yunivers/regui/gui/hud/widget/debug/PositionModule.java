package io.github.yunivers.regui.gui.hud.widget.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.math.MathHelper;

public class PositionModule extends DebugModule
{
    public PositionModule()
    {
        super();
        height = 32 + 2;
    }

    @Override
    public void render(Minecraft minecraft, int y, int width)
    {
        TextRenderer textRenderer = minecraft.textRenderer;
        this.drawTextWithShadow(textRenderer, "x: " + minecraft.player.x, 2, y, 0xE0E0E0);
        this.drawTextWithShadow(textRenderer, "y: " + minecraft.player.y, 2, y + 8, 0xE0E0E0);
        this.drawTextWithShadow(textRenderer, "z: " + minecraft.player.z, 2, y + 16, 0xE0E0E0);
        this.drawTextWithShadow(textRenderer, "f: " + (MathHelper.floor((double)(minecraft.player.yaw * 4.0F / 360.0F) + 0.5) & 3), 2, y + 24, 0xE0E0E0);
    }
}
