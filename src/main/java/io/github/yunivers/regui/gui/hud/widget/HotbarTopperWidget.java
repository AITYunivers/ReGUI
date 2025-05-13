package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.util.EHudDock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class HotbarTopperWidget extends HudWidget
{
    public HotbarTopperWidget()
    {
        super(EHudDock.BOTTOM);
        this.width = 182; // Inherits prevWidget in render
        this.height = 9; // Scaled x2 when in water
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        this.width = prevWidget.width;
        this.height = 9;
        if (hud.minecraft.player.isInFluid(Material.WATER))
            this.height = 18;

        int width = scaler.getScaledWidth();
        int height = scaler.getScaledHeight();
        this.zOffset = -90.0F;

        GL11.glEnable(3042);
        GL11.glBindTexture(3553, hud.minecraft.textureManager.getTextureId("/gui/icons.png"));
        boolean var12 = hud.minecraft.player.hearts / 3 % 2 == 1;
        if (hud.minecraft.player.hearts < 10)
            var12 = false;

        int var13 = hud.minecraft.player.health;
        int var14 = hud.minecraft.player.lastHealth;
        hud.random.setSeed(hud.ticks * 312871L);
        if (hud.minecraft.interactionManager.canBeRendered())
        {
            int armorDurability = hud.minecraft.player.getTotalArmorDurability();

            for (int iconId = 0; iconId < 10; iconId++)
            {
                int y = height - 9 + yOffset;
                if (armorDurability > 0)
                {
                    int x = width / 2 + 91 - iconId * 8 - 9 + xOffset;
                    if (iconId * 2 + 1 < armorDurability)
                        this.drawTexture(x, y, 34, 9, 9, 9);

                    if (iconId * 2 + 1 == armorDurability)
                        this.drawTexture(x, y, 25, 9, 9, 9);

                    if (iconId * 2 + 1 > armorDurability)
                        this.drawTexture(x, y, 16, 9, 9, 9);
                }

                byte isHalf = 0;
                if (var12)
                    isHalf = 1;

                int x = width / 2 - 91 + iconId * 8 + xOffset;
                if (var13 <= 4)
                    y += hud.random.nextInt(2);

                this.drawTexture(x, y, 16 + isHalf * 9, 0, 9, 9);
                if (var12)
                {
                    if (iconId * 2 + 1 < var14)
                        this.drawTexture(x, y, 70, 0, 9, 9);

                    if (iconId * 2 + 1 == var14)
                        this.drawTexture(x, y, 79, 0, 9, 9);
                }

                if (iconId * 2 + 1 < var13)
                    this.drawTexture(x, y, 52, 0, 9, 9);

                if (iconId * 2 + 1 == var13)
                    this.drawTexture(x, y, 61, 0, 9, 9);
            }

            if (hud.minecraft.player.isInFluid(Material.WATER))
            {
                int poppedAir = (int)Math.ceil((double)(hud.minecraft.player.air - 2) * 10.0 / 300.0);
                int realAir = (int)Math.ceil((double)hud.minecraft.player.air * 10.0 / 300.0) - poppedAir;

                for (int air = 0; air < poppedAir + realAir; air++)
                {
                    if (air < poppedAir)
                        this.drawTexture(width / 2 - 91 + air * 8 + xOffset, height - 18 + yOffset, 16, 18, 9, 9);
                    else
                        this.drawTexture(width / 2 - 91 + air * 8 + xOffset, height - 18 + yOffset, 25, 18, 9, 9);
                }
            }
        }

        GL11.glDisable(3042);
    }
}
