package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class ChatWidget extends HudWidget
{
    public ChatWidget()
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
        int height = scaler.getScaledHeight();
        this.zOffset = -90.0F;

        byte msgLimit = 10;
        boolean fullView = false;
        if (hud.minecraft.currentScreen instanceof ChatScreen)
        {
            msgLimit = 20;
            fullView = true;
        }

        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, (float)(height - 48), 0.0F);

        eResult = this.renderEvent(1, msgLimit, fullView); // Pre-Render (Actual)
        if (!eResult.cancelNextRender)
        {
            for (int msgId = 0; msgId < hud.messages.size() && msgId < msgLimit; msgId++)
            {
                eResult = this.renderEvent(2, msgId); // Possible Pre-Render Message
                if ((((ChatHudLine)hud.messages.get(msgId)).age < 200 || fullView) && !eResult.cancelNextRender)
                {
                    double alphaCalc = (double)((ChatHudLine)hud.messages.get(msgId)).age / 200.0;
                    alphaCalc = 1.0 - alphaCalc;
                    alphaCalc *= 10.0;
                    if (alphaCalc < 0.0)
                        alphaCalc = 0.0;

                    if (alphaCalc > 1.0)
                        alphaCalc = 1.0;

                    alphaCalc *= alphaCalc;
                    int alpha = (int)(255.0 * alphaCalc);
                    if (fullView)
                        alpha = 255;

                    if (alpha > 0)
                    {
                        byte x = 2;
                        int y = -msgId * 9;
                        String message = ((ChatHudLine)hud.messages.get(msgId)).text;
                        eResult = this.renderEvent(3, msgId, alphaCalc, alpha, message); // Pre-Render Message
                        if (!eResult.cancelNextRender)
                            this.fill(x, y - 1, x + 320, y + 8, alpha / 2 << 24);
                        GL11.glEnable(3042);
                        hud.minecraft.textRenderer.drawWithShadow(message, x, y, 16777215 + (alpha << 24));
                        this.renderEvent(4, msgId, message); // Post-Render Message
                    }
                }
                this.renderEvent(5, msgId); // Possible Post-Render Message
            }
        }

        this.renderEvent(6); // Post-Render
        GL11.glPopMatrix();
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        this.renderEvent(7); // EOF
    }
}
