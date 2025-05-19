package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class SleepOverlayWidget extends HudWidget
{
    public SleepOverlayWidget()
    {
        super(EHudDock.CENTER);
        priority = EHudPriority.LOWER;
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        super.render(hud, tickDelta, scaler, xOffset, yOffset, prevWidget);
        HudWidgetRenderEvent eResult = this.renderEvent(0); // Pre-Render
        if (eResult.cancelNextRender)
            return;
        this.width = scaler.getScaledWidth();
        this.height = scaler.getScaledHeight();

        if (hud.minecraft.player.getSleepTimer() > 0)
        {
            GL11.glDisable(2929);
            GL11.glDisable(3008);
            int sleepTimer = hud.minecraft.player.getSleepTimer();
            float progress = (float)sleepTimer / 100.0F;
            if (progress > 1.0F)
                progress = 1.0F - (float)(sleepTimer - 100) / 10.0F;

            int color = (int)(220.0F * progress) << 24 | 1052704;
            eResult = this.renderEvent(1); // Pre-Render (Actual)
            if (!eResult.cancelNextRender)
                this.fill(0, 0, width, height, color);
            this.renderEvent(2); // Post-Render
            GL11.glEnable(3008);
            GL11.glEnable(2929);
        }
    }
}
