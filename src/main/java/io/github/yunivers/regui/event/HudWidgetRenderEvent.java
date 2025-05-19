package io.github.yunivers.regui.event;

import io.github.yunivers.regui.gui.hud.widget.HudWidget;
import lombok.SneakyThrows;
import net.mine_diver.unsafeevents.Event;

@SuppressWarnings("unused")
public class HudWidgetRenderEvent extends Event
{
    public Class<HudWidget> widget;
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

    public HudWidgetRenderEvent(Class<HudWidget> widget, int stage)
    {
        this.widget = widget;
        this.stage = stage;
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
    }
}
