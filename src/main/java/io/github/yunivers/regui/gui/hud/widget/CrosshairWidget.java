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
public class CrosshairWidget extends HudWidget
{
    public CrosshairWidget()
    {
        super(EHudDock.CENTER);
        this.priority = EHudPriority.HIGH;
        this.baseWidth = this.baseHeight = 16;
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

        GL11.glBindTexture(3553, hud.minecraft.textureManager.getTextureId("/gui/icons.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(775, 769);
        eResult = this.renderEvent(1); // Pre-Render (Actual)
        if (!eResult.cancelNextRender)
            this.drawTexture(width / 2 - 7 + eResult.offsetX, height / 2 - 7 + eResult.offsetY, 0, 0, 16, 16);
        this.renderEvent(2); // Post-Render
        GL11.glDisable(3042);
        GL11.glBlendFunc(770, 771);
        this.renderEvent(3); // EOF
    }
}
