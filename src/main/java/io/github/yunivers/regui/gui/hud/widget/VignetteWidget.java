package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.ScreenScaler;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class VignetteWidget extends HudWidget
{
    public VignetteWidget()
    {
        super(EHudDock.CENTER);
        priority = EHudPriority.HIGHEST;
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        HudWidgetRenderEvent eResult = this.renderEvent(0); // Pre-Render
        if (!eResult.cancelNextRender)
            return;
        this.width = scaler.getScaledWidth();
        this.height = scaler.getScaledHeight();

        if (Minecraft.isFancyGraphicsEnabled())
        {
            eResult = this.renderEvent(1); // Pre-Render (Actual)
            GL11.glEnable(3042);
            float brightness = MathHelper.clamp(1.0F - hud.minecraft.player.getBrightnessAtEyes(tickDelta), 0.0F, 1.0F);

            hud.vignetteDarkness = (float)((double)hud.vignetteDarkness + (double)(brightness - hud.vignetteDarkness) * 0.01);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(0, 769);
            GL11.glColor4f(hud.vignetteDarkness, hud.vignetteDarkness, hud.vignetteDarkness, 1.0F);
            GL11.glBindTexture(3553, hud.minecraft.textureManager.getTextureId("%blur%/misc/vignette.png"));
            if (!eResult.cancelNextRender)
            {
                Tessellator var4 = Tessellator.INSTANCE;
                var4.startQuads();
                var4.vertex(0,     height, -90, 0, 1);
                var4.vertex(width,    height, -90, 1, 1);
                var4.vertex(width, 0,      -90, 1, 0);
                var4.vertex(0,  0,      -90, 0, 0);
                var4.draw();
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBlendFunc(770, 771);
            this.renderEvent(2); // Post-Render
        }
    }
}
