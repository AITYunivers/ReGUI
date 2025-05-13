package io.github.yunivers.regui.gui.hud.widget.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;

public class MemoryModule extends DebugModule
{
    public MemoryModule()
    {
        super();
        height = 20;
    }

    @Override
    public void render(Minecraft minecraft, int y, int width)
    {
        TextRenderer textRenderer = minecraft.textRenderer;
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        String str = "Used memory: " + usedMemory * 100L / maxMemory + "% (" + usedMemory / 1024L / 1024L + "MB) of " + maxMemory / 1024L / 1024L + "MB";
        this.drawTextWithShadow(textRenderer, str, width - textRenderer.getWidth(str) - 2, 2, 0xE0E0E0);
        str = "Allocated memory: " + totalMemory * 100L / maxMemory + "% (" + totalMemory / 1024L / 1024L + "MB)";
        this.drawTextWithShadow(textRenderer, str, width - textRenderer.getWidth(str) - 2, 12, 0xE0E0E0);
    }
}
