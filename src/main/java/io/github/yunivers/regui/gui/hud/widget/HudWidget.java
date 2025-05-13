package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;

@Environment(EnvType.CLIENT)
public class HudWidget extends DrawContext
{
    public EHudDock dock;
    public EHudPriority priority = EHudPriority.NORMAL;
    public int width;
    public int height;

    public HudWidget(EHudDock dock)
    {
        this.dock = dock;
        this.width = this.height = 0; // uhh idk
    }

    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {

    }
}
