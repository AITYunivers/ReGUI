package io.github.yunivers.regui.event;

import io.github.yunivers.regui.gui.hud.widget.HudWidget;
import lombok.SneakyThrows;
import net.mine_diver.unsafeevents.Event;
import net.minecraft.client.gui.hud.InGameHud;

@SuppressWarnings("unused")
public class HudWidgetRenderEvent extends Event
{
    public Class<HudWidget> widget;

    // Event Data
    public int widgetOGWidth;
    public int widgetOGHeight;
    public int widgetWidth;
    public int widgetHeight;
    public int widgetDockOffsetX;
    public int widgetDockOffsetY;
    public int scaledWidth;
    public int scaledHeight;
    public float tickDelta;
    public InGameHud hud;
    public HudWidget prevWidget;

    // Return Data
    public int stage;
    public int offsetX;
    public int offsetY;
    public int inflateX;
    public int inflateY;
    public int deflateX;
    public int deflateY;

    /// Setting this to true during Stage 0 will cancel the entire function.
    ///
    /// Other stages will only cancel the rendering logic exclusively;
    /// OpenGL calls such as glEnable, glBlendFunc, and glBindTexture will still be called.
    public boolean cancelNextRender;

    /// Changes per Widget, passes in anything that might be useful
    public Object[] args;

    public HudWidgetRenderEvent(Class<HudWidget> widget, int stage, Object[] args)
    {
        this.widget = widget;
        this.stage = stage;
        this.args = args;
    }

    public void setOffset(int x, int y)
    {
        this.offsetX = x;
        this.offsetY = y;
    }

    @SneakyThrows
    public void setInflate(int width, int height)
    {
        if (stage != 0)
            throw new Exception("Cannot set inflate outside of initial stage");
        this.inflateX = Math.max(inflateX, width);
        this.inflateY = Math.max(inflateY, height);
        this.deflateX = 0;
        this.deflateY = 0;

        this.widgetWidth = widgetOGWidth + inflateX;
        this.widgetHeight = widgetOGHeight + inflateY;
    }

    /// Resets both inflate and deflate
    @SneakyThrows
    public void resetInflate()
    {
        if (stage != 0)
            throw new Exception("Cannot set deflate outside of initial stage");
        this.deflateX = 0;
        this.deflateY = 0;
        this.inflateX = 0;
        this.inflateY = 0;

        this.widgetWidth = widgetOGWidth;
        this.widgetHeight = widgetOGHeight;
    }

    @SneakyThrows
    public void setDeflate(int width, int height)
    {
        if (stage != 0)
            throw new Exception("Cannot set deflate outside of initial stage");
        this.deflateX = Math.max(deflateX, width);
        this.deflateY = Math.max(deflateY, height);
        this.inflateX = 0;
        this.inflateY = 0;

        this.widgetWidth = widgetOGWidth - deflateX;
        this.widgetHeight = widgetOGHeight - deflateY;
    }

    @SneakyThrows
    public void setEventData(int width, int height, int offsetX, int offsetY, InGameHud hud, int scaledWidth, int scaledHeight, float tickDelta, HudWidget prevWidget)
    {
        widgetOGWidth = widgetWidth = width;
        widgetOGHeight = widgetHeight = height;
        widgetDockOffsetX = offsetX;
        widgetDockOffsetY = offsetY;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
        this.tickDelta = tickDelta;
        this.hud = hud;
        this.prevWidget = prevWidget;
    }
}
