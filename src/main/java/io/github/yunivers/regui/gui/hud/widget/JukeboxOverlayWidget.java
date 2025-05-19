package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class JukeboxOverlayWidget extends HudWidget
{
    public JukeboxOverlayWidget()
    {
        super(EHudDock.CENTER);
        priority = EHudPriority.LOWEST;
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        super.render(hud, tickDelta, scaler, xOffset, yOffset, prevWidget);
        HudWidgetRenderEvent eResult = this.renderEvent(0); // Pre-Render
        if (eResult.cancelNextRender)
            return;
        int width = scaler.getScaledWidth();
        int height = scaler.getScaledHeight();
        this.zOffset = -90.0F;

        if (hud.overlayRemaining > 0)
        {
            float overlayRemaining = (float)hud.overlayRemaining - tickDelta;
            int alpha = Math.min((int)(overlayRemaining * 256.0F / 20.0F), 255);

            if (alpha > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(width / 2), (float)(height - 48), 0.0F);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                int color = 16777215;
                if (hud.overlayTinted)
                    color = Color.HSBtoRGB(overlayRemaining / 50.0F, 0.7F, 0.6F) & 16777215;

                TextRenderer textRenderer = hud.minecraft.textRenderer;
                eResult = this.renderEvent(1, overlayRemaining, alpha); // Pre-Render (Actual)
                if (!eResult.cancelNextRender)
                    textRenderer.draw(hud.overlayMessage, -textRenderer.getWidth(hud.overlayMessage) / 2, -4, color + (alpha << 24));
                this.renderEvent(2); // Post-Render
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                this.renderEvent(3); // EOF
            }
        }
    }
}
