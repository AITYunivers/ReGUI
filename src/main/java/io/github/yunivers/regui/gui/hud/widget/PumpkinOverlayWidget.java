package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class PumpkinOverlayWidget extends HudWidget
{
    public PumpkinOverlayWidget()
    {
        super(EHudDock.CENTER);
        priority = EHudPriority.HIGHEST;
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

        ItemStack stack = hud.minecraft.player.inventory.getArmorStack(3);
        if (!hud.minecraft.options.thirdPerson && stack != null && stack.itemId == Block.PUMPKIN.id)
        {
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3008);
            GL11.glBindTexture(3553, hud.minecraft.textureManager.getTextureId("%blur%/misc/pumpkinblur.png"));
            eResult = this.renderEvent(1); // Pre-Render (Actual)
            if (!eResult.cancelNextRender)
            {
                Tessellator var3 = Tessellator.INSTANCE;
                var3.startQuads();
                var3.vertex(0,     height, -90, 0, 1);
                var3.vertex(width,    height, -90, 1, 1);
                var3.vertex(width, 0,      -90, 1, 0);
                var3.vertex(0,  0,      -90, 0, 0);
                var3.draw();
            }
            this.renderEvent(2); // Post-Render
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
