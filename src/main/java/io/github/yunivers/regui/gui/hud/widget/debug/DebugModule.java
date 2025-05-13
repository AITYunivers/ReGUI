package io.github.yunivers.regui.gui.hud.widget.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;

public class DebugModule extends DrawContext
{
    public int height = 10;

    public DebugModule()
    {
        zOffset = -90.0F;
    }

    public void render(Minecraft minecraft, int y, int width)
    {

    }
}
