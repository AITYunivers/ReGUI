package io.github.yunivers.regui.eventhandlers;

import io.github.yunivers.regui.event.InGameHudWidgetInitEvent;
import io.github.yunivers.regui.gui.hud.widget.*;
import io.github.yunivers.regui.gui.hud.widget.debug.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;

@SuppressWarnings("unused")
public class InitHudWidgets
{
    @EventListener(priority = ListenerPriority.HIGH)
    public void initHudWidgets(InGameHudWidgetInitEvent event)
    {
        event.hudWidgets.add(new VignetteWidget());
        event.hudWidgets.add(new PumpkinOverlayWidget());
        event.hudWidgets.add(new PortalOverlayWidget());
        event.hudWidgets.add(new CrosshairWidget());
        event.hudWidgets.add(new HotbarWidget());
        event.hudWidgets.add(new HotbarTopperWidget());
        event.hudWidgets.add(new SleepOverlayWidget());
        event.hudWidgets.add(new DebugWidget());
        event.hudWidgets.add(new JukeboxOverlayWidget());
        event.hudWidgets.add(new ChatWidget());

        DebugWidget.leftModules.add(new VersionModule());
        DebugWidget.leftModules.add(new WorldInfoModule());
        DebugWidget.leftModules.add(new SpacingModule(1));
        DebugWidget.leftModules.add(new PositionModule());
        DebugWidget.leftModules.add(new RendererModule());

        DebugWidget.rightModules.add(new MemoryModule());
        DebugWidget.rightModules.add(new SpacingModule(1));
        DebugWidget.rightModules.add(new TargetBlockModule());
    }
}
