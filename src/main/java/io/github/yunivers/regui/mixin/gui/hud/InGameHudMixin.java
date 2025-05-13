package io.github.yunivers.regui.mixin.gui.hud;

import io.github.yunivers.regui.event.InGameHudWidgetInitEvent;
import io.github.yunivers.regui.gui.hud.widget.*;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import net.modificationstation.stationapi.api.StationAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin(value = InGameHud.class, priority = 10000)
public class InGameHudMixin
{
    @Shadow public Minecraft minecraft;
    @Unique public ArrayList<HudWidget> hudWidgets;

    @SuppressWarnings("ReassignedVariable")
    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true
    )
    public void regui$render_renderNewHUD(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci)
    {
        ScreenScaler scaler = new ScreenScaler(minecraft.options, minecraft.displayWidth, minecraft.displayHeight);
        minecraft.gameRenderer.setupHudRender();

        for (EHudPriority priority : EHudPriority.values())
        {
            for (EHudDock dockDir : EHudDock.values())
            {
                int xOffset = 0, yOffset = 0;
                Iterator<HudWidget> iterator = hudWidgets.stream().filter(x -> x.dock == dockDir && x.priority == priority).iterator();
                HudWidget prevWidget = null;
                while (iterator.hasNext())
                {
                    HudWidget hudWidget = iterator.next();
                    hudWidget.render((InGameHud)(Object)this, tickDelta, scaler, xOffset, yOffset, prevWidget);
                    switch (dockDir)
                    {
                        case LEFT -> xOffset += hudWidget.width;
                        case TOP -> yOffset += hudWidget.height;
                        case RIGHT -> xOffset -= hudWidget.width;
                        case BOTTOM -> yOffset -= hudWidget.height;
                    }
                    prevWidget = hudWidget;
                }
            }
        }

        ci.cancel();
    }

    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    public void regui$init_initWidgets(Minecraft minecraft, CallbackInfo ci)
    {
        InGameHudWidgetInitEvent event = new InGameHudWidgetInitEvent();
        StationAPI.EVENT_BUS.post(event);
        hudWidgets = event.hudWidgets;
    }
}
