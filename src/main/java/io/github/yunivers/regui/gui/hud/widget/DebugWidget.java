package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.gui.hud.widget.debug.DebugModule;
import io.github.yunivers.regui.util.EHudDock;
import io.github.yunivers.regui.util.EHudPriority;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Method;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class DebugWidget extends HudWidget
{
    public static ArrayList<DebugModule> leftModules = new ArrayList<>();
    public static ArrayList<DebugModule> rightModules = new ArrayList<>();
    private boolean betterf3$previousDebugHudValue = false;

    public DebugWidget()
    {
        super(EHudDock.CENTER);
        priority = EHudPriority.LOWEST;
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        HudWidgetRenderEvent eResult = this.renderEvent(0); // Pre-Render
        if (eResult.cancelNextRender)
            return;
        int width = scaler.getScaledWidth();
        this.zOffset = -90.0F;

        if (FabricLoader.getInstance().isModLoaded("betterf3"))
        {
            if (betterf3$previousDebugHudValue != hud.minecraft.options.debugHud)
            {
                betterf3$previousDebugHudValue = hud.minecraft.options.debugHud;
                ralf2oo2.betterf3.utils.Utils.closingAnimation = !hud.minecraft.options.debugHud;
                if (!ralf2oo2.betterf3.utils.Utils.closingAnimation)
                    ralf2oo2.betterf3.utils.Utils.xPos = 200;
            }

            try
            {
                invokeMethod(hud, "betterf3_renderAnimation");
                if ((hud.minecraft.options.debugHud && !ralf2oo2.betterf3.config.GeneralOptions.disableMod) ||
                    (ralf2oo2.betterf3.utils.Utils.closingAnimation && !ralf2oo2.betterf3.config.GeneralOptions.disableMod))
                {
                    this.renderEvent(1000); // Pre-Render BetterF3
                    if (!eResult.cancelNextRender)
                    {
                        invokeMethod(hud, "betterf3_renderLeftText");
                        invokeMethod(hud, "betterf3_renderRightText");
                    }
                    this.renderEvent(1001); // Post-Render BetterF3
                    return;
                }
            }
            catch (Exception ignore)
            {

            }
        }

        if (hud.minecraft.options.debugHud)
        {
            eResult = this.renderEvent(1); // Pre-Render (Actual)
            GL11.glPushMatrix();
            if (Minecraft.failedSessionCheckTime > 0L)
                GL11.glTranslatef(0.0F, 32.0F, 0.0F);

            if (!eResult.cancelNextRender)
            {
                int y = 2;
                for (DebugModule module : leftModules)
                {
                    eResult = this.renderEvent(2); // Pre-Render Left Module
                    if (!eResult.cancelNextRender)
                    {
                        module.render(hud.minecraft, y + eResult.offsetY, width);
                        y += module.height;
                    }
                    this.renderEvent(3); // Post-Render Left Module
                }

                y = 2;
                for (DebugModule module : rightModules)
                {
                    eResult = this.renderEvent(4); // Pre-Render Right Module
                    if (!eResult.cancelNextRender)
                    {
                        module.render(hud.minecraft, y + eResult.offsetY, width);
                        y += module.height;
                    }
                    this.renderEvent(5); // Post-Render Right Module
                }
            }

            GL11.glPopMatrix();
            this.renderEvent(6); // Post-Render (Actual)
        }
        this.renderEvent(7); // Post-Render
    }

    // Used to access BetterF3
    private static void invokeMethod(Object instance, String methodName) throws Exception
    {
        Method method = instance.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(instance);
    }
}
