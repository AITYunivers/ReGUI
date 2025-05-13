package io.github.yunivers.regui.event;

import io.github.yunivers.regui.gui.hud.widget.HudWidget;
import net.mine_diver.unsafeevents.Event;

import java.util.ArrayList;

public class InGameHudWidgetInitEvent extends Event
{
    public ArrayList<HudWidget> hudWidgets = new ArrayList<>();
}
