package io.github.yunivers.regui.gui.hud.widget;

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
                    invokeMethod(hud, "betterf3_renderLeftText");
                    invokeMethod(hud, "betterf3_renderRightText");
                    return;
                }
            }
            catch (Exception ignore)
            {

            }
        }

        if (hud.minecraft.options.debugHud)
        {
            GL11.glPushMatrix();
            if (Minecraft.failedSessionCheckTime > 0L)
                GL11.glTranslatef(0.0F, 32.0F, 0.0F);

            int y = 2;
            for (DebugModule module : leftModules)
            {
                module.render(hud.minecraft, y, width);
                y += module.height;
            }

            y = 2;
            for (DebugModule module : rightModules)
            {
                module.render(hud.minecraft, y, width);
                y += module.height;
            }

            GL11.glPopMatrix();
        }
    }

    // Used to access BetterF3
    private static void invokeMethod(Object instance, String methodName) throws Exception
    {
        Method method = instance.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(instance);
    }
}
