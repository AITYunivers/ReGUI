package io.github.yunivers.regui.gui.hud.widget.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.modificationstation.stationapi.api.client.render.RendererAccess;

import java.util.Objects;

import static net.modificationstation.stationapi.api.StationAPI.NAMESPACE;

public class RendererModule extends DebugModule
{
    public RendererModule()
    {
        super();
    }

    @Override
    public void render(Minecraft minecraft, int y, int width)
    {
        TextRenderer textRenderer = minecraft.textRenderer;
        drawTextWithShadow(textRenderer, "[" + NAMESPACE.getName() + "] Active renderer: " + (RendererAccess.INSTANCE.hasRenderer() ? Objects.requireNonNull(RendererAccess.INSTANCE.getRenderer()).getClass().getSimpleName() : "none (vanilla)"), 2, y, 0xE0E0E0);
    }
}
