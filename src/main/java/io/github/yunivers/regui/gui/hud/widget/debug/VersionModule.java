package io.github.yunivers.regui.gui.hud.widget.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;

public class VersionModule extends DebugModule
{
    public VersionModule()
    {
        super();
    }

    @Override
    public void render(Minecraft minecraft, int y, int width)
    {
        TextRenderer textRenderer = minecraft.textRenderer;
        textRenderer.drawWithShadow("Minecraft Beta 1.7.3 (" + minecraft.debugText + ")", 2, y, 16777215);
    }
}
