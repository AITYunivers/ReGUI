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
    private int offsetX;
    private int offsetY;

    public HudWidget(EHudDock dock)
    {
        this.dock = dock;
        this.width = this.height = 0; // uhh idk
    }

    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        this.hud = hud;
        offsetX = xOffset;
        offsetY = yOffset;
    }
    protected HudWidgetRenderEvent renderEvent(int stage, Object... args)
    {
        return renderEvent(stage, offsetX, offsetY, args);
    }

    @SuppressWarnings("unchecked")
    protected HudWidgetRenderEvent renderEvent(int stage, int offsetX, int offsetY, Object... args)
    {
        HudWidgetRenderEvent event = new HudWidgetRenderEvent((Class<HudWidget>)this.getClass(), stage, args);
        event.setEventData(width, height, offsetX, offsetY, hud);
        StationAPI.EVENT_BUS.post(event);
        if (stage == 0)
        {
            width += event.inflateX - event.deflateX;
            height += event.inflateY - event.deflateY;
        }
        return event;
    }
}
