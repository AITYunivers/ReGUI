package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import net.modificationstation.stationapi.api.StationAPI;

@Environment(EnvType.CLIENT)
public class HudWidget extends DrawContext
{
    public EHudDock dock;
    public EHudPriority priority = EHudPriority.NORMAL;
    public int width;
    public int height;

    // For event
    private InGameHud hud;
    private float tickDelta;
    private int scaledWidth;
    private int scaledHeight;
    private int offsetX;
    private int offsetY;
    private HudWidget prevWidget;

    public HudWidget(EHudDock dock)
    {
        this.dock = dock;
        this.width = this.height = 0; // uhh idk
    }

    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        this.hud = hud;
        this.tickDelta = tickDelta;
        scaledWidth = scaler.getScaledWidth();
        scaledHeight = scaler.getScaledHeight();
        offsetX = xOffset;
        offsetY = yOffset;
        this.prevWidget = prevWidget;
    }

    protected HudWidgetRenderEvent renderEvent(int stage, Object... args)
    {
        return renderEvent(stage, offsetX, offsetY, args);
    }

    protected HudWidgetRenderEvent renderEvent(int stage, int offsetX, int offsetY, Object[] args)
    {
        HudWidgetRenderEvent event = new HudWidgetRenderEvent(this, stage, args);
        event.setEventData(width, height, offsetX, offsetY, hud, scaledWidth, scaledHeight, tickDelta, prevWidget);
        StationAPI.EVENT_BUS.post(event);
        if (stage == 0)
        {
            width += event.inflateX - event.deflateX;
            height += event.inflateY - event.deflateY;
        }
        return event;
    }
}
