package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class HotbarTopperWidget extends HudWidget
{
    public HotbarTopperWidget()
    {
        super(EHudDock.BOTTOM);
        this.baseWidth = 182; // Inherits prevWidget in render (nvm, i'll code that later)
        this.baseHeight = 9; // Scaled x2 when in water
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        //this.baseWidth = prevWidget.width; TODO
        this.baseHeight = 9;
        if (hud.minecraft.player.isInFluid(Material.WATER))
            this.baseHeight = 18;
        super.render(hud, tickDelta, scaler, xOffset, yOffset, prevWidget);
        HudWidgetRenderEvent eResult = this.renderEvent(0); // Pre-Render
        if (eResult.cancelNextRender)
            return;

        int width = scaler.getScaledWidth();
        int height = scaler.getScaledHeight();
        this.zOffset = -90.0F;

        GL11.glEnable(3042);
        GL11.glBindTexture(3553, hud.minecraft.textureManager.getTextureId("/gui/icons.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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
                eResult = this.renderEvent(1, iconId, armorDurability); // Pre-Armor Icon Render
                int y = height - 9 + yOffset;
                if (armorDurability > 0 && !eResult.cancelNextRender)
                {
                    int x = width / 2 + 91 - iconId * 8 - 9 + xOffset;
                    if (iconId * 2 + 1 < armorDurability)
                        this.drawTexture(x + eResult.offsetX, y + eResult.offsetY, 34, 9, 9, 9);

                    if (iconId * 2 + 1 == armorDurability)
                        this.drawTexture(x + eResult.offsetX, y + eResult.offsetY, 25, 9, 9, 9);

                    if (iconId * 2 + 1 > armorDurability)
                        this.drawTexture(x + eResult.offsetX, y + eResult.offsetY, 16, 9, 9, 9);
                }
                eResult = this.renderEvent(2, iconId); // Post-Armor Icon Render/Pre-Heart Icon Render

                byte isHalf = 0;
                if (var12)
                    isHalf = 1;

                int x = width / 2 - 91 + iconId * 8 + xOffset + eResult.offsetX;
                if (var13 <= 4)
                    y += hud.random.nextInt(2);
                y += eResult.offsetY;

                if (!eResult.cancelNextRender)
                {
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
                this.renderEvent(3, iconId); // Post-Heart Icon Render
            }

            if (hud.minecraft.player.isInFluid(Material.WATER))
            {
                int poppedAir = (int)Math.ceil((double)(hud.minecraft.player.air - 2) * 10.0 / 300.0);
                int realAir = (int)Math.ceil((double)hud.minecraft.player.air * 10.0 / 300.0) - poppedAir;

                eResult = this.renderEvent(4, poppedAir, realAir); // Possible Pre-Bubble Icons Render
                if (!eResult.cancelNextRender)
                {
                    for (int air = 0; air < poppedAir + realAir; air++)
                    {
                        this.renderEvent(5, air, poppedAir, realAir); // Pre-Bubble Icon Render
                        if (air < poppedAir)
                            this.drawTexture(width / 2 - 91 + air * 8 + xOffset + eResult.offsetX, height - 18 + yOffset + eResult.offsetY, 16, 18, 9, 9);
                        else
                            this.drawTexture(width / 2 - 91 + air * 8 + xOffset + eResult.offsetX, height - 18 + yOffset + eResult.offsetY, 25, 18, 9, 9);
                        this.renderEvent(6, air, poppedAir, realAir); // Post-Bubble Icon Render
                    }
                }
                this.renderEvent(7); // Possible Post-Bubble Icons Render
            }
        }

        this.renderEvent(8); // Post-Render
        GL11.glDisable(3042);
        this.renderEvent(9); // EOF
    }
}
