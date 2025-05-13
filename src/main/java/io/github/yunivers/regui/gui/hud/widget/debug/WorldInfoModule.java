package io.github.yunivers.regui.gui.hud.widget.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;

public class WorldInfoModule extends DebugModule
{
    public WorldInfoModule()
    {
        super();
        height = 40;
    }

    @Override
    public void render(Minecraft minecraft, int y, int width)
    {
        TextRenderer textRenderer = minecraft.textRenderer;
        textRenderer.drawWithShadow(minecraft.getRenderChunkDebugInfo(), 2, y, 0xFFFFFF);
        textRenderer.drawWithShadow(minecraft.getRenderEntityDebugInfo(), 2, y + 10, 0xFFFFFF);
        textRenderer.drawWithShadow(minecraft.getWorldDebugInfo(), 2, y + 20, 0xFFFFFF);
        textRenderer.drawWithShadow(minecraft.getChunkSourceDebugInfo(), 2, y + 30, 0xFFFFFF);
    }
}
