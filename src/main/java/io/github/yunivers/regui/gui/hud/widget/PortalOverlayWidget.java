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
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class PortalOverlayWidget extends HudWidget
{
    public PortalOverlayWidget()
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

        float distortion = hud.minecraft.player.lastScreenDistortion + (hud.minecraft.player.screenDistortion - hud.minecraft.player.lastScreenDistortion) * tickDelta;
        if (distortion > 0.0F)
        {
            if (distortion < 1.0F)
            {
                distortion *= distortion;
                distortion *= distortion;
                distortion = distortion * 0.8F + 0.2F;
            }

            GL11.glDisable(3008);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, distortion);

            StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE).bindTexture();
            Sprite sprite = Block.NETHER_PORTAL.getAtlas().getTexture(Block.NETHER_PORTAL.textureId).getSprite();

            eResult = this.renderEvent(1, distortion, sprite); // Pre-Render (Actual)
            if (!eResult.cancelNextRender)
            {
                Tessellator t = Tessellator.INSTANCE;
                t.startQuads();
                t.vertex(0,     height, -90, sprite.getMinU(), sprite.getMaxV());
                t.vertex(width,    height, -90, sprite.getMaxU(), sprite.getMaxV());
                t.vertex(width, 0,      -90, sprite.getMaxU(), sprite.getMinV());
                t.vertex(0,  0,      -90, sprite.getMinU(), sprite.getMinV());
                t.draw();
            }
            this.renderEvent(2); // Post-Render

            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.renderEvent(3); // EOF
        }
    }
}
