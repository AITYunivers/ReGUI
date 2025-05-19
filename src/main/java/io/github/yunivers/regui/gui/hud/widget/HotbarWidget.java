package io.github.yunivers.regui.gui.hud.widget;

import io.github.yunivers.regui.event.HudWidgetRenderEvent;
import io.github.yunivers.regui.util.EHudDock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class HotbarWidget extends HudWidget
{
    public HotbarWidget()
    {
        super(EHudDock.BOTTOM);
        this.width = 182;
        this.height = 23;
    }

    @Override
    public void render(InGameHud hud, float tickDelta, ScreenScaler scaler, int xOffset, int yOffset, HudWidget prevWidget)
    {
        HudWidgetRenderEvent eResult = this.renderEvent(0); // Pre-Render
        if (eResult.cancelNextRender)
            return;
        int width = scaler.getScaledWidth();
        int height = scaler.getScaledHeight();
        this.zOffset = -90.0F;

        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(3553, hud.minecraft.textureManager.getTextureId("/gui/gui.png"));
        PlayerInventory inv = hud.minecraft.player.inventory;
        eResult = this.renderEvent(1); // Pre-Hotbar Render
        if (!eResult.cancelNextRender)
            this.drawTexture(width / 2 - 91 + xOffset + eResult.offsetX, height - 22 + yOffset + eResult.offsetY, 0, 0, 182, 22);
        eResult = this.renderEvent(2); // Post-Hotbar Render/Pre-Hotbar Selection Outline Render
        if (!eResult.cancelNextRender)
            this.drawTexture(width / 2 - 91 - 1 + inv.selectedSlot * 20 + xOffset + eResult.offsetX, height - 22 - 1 + yOffset + eResult.offsetY, 0, 22, 24, 24);
        this.renderEvent(3); // Post-Hotbar Selection Outline Render

        GL11.glDisable(3042);
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        Lighting.turnOn();
        GL11.glPopMatrix();

        this.renderEvent(4); // Pre-Hotbar Items
        if (!eResult.cancelNextRender)
        {
            for (int slot = 0; slot < 9; slot++)
            {
                eResult = this.renderEvent(5); // Pre-Hotbar Item
                int x = width / 2 - 90 + slot * 20 + 2;
                int y = height - 16 - 3;
                if (!eResult.cancelNextRender)
                    this.renderHotbarItem(hud, slot, x + xOffset + eResult.offsetX, y + yOffset + eResult.offsetY, tickDelta);
            }
        }

        Lighting.turnOff();
        GL11.glDisable(32826);
        this.renderEvent(8); // Post-Render
    }

    private void renderHotbarItem(InGameHud hud, int slot, int x, int y, float tickDelta)
    {
        ItemStack stack = hud.minecraft.player.inventory.main[slot];
        if (stack != null)
        {
            float var6 = (float)stack.bobbingAnimationTime - tickDelta;
            if (var6 > 0.0F)
            {
                GL11.glPushMatrix();
                float var7 = 1.0F + var6 / 5.0F;
                GL11.glTranslatef((float)(x + 8), (float)(y + 12), 0.0F);
                GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
            }

            HudWidgetRenderEvent eResult = this.renderEvent(6); // Pre-Hotbar Item Render
            if (!eResult.cancelNextRender)
                InGameHud.ITEM_RENDERER.renderGuiItem(hud.minecraft.textRenderer, hud.minecraft.textureManager, stack, x + eResult.offsetX, y + eResult.offsetY);
            if (var6 > 0.0F)
                GL11.glPopMatrix();

            eResult = this.renderEvent(7); // Post-Hotbar Item Render/Pre-Hotbar Item Decoration Render
            if (!eResult.cancelNextRender)
                InGameHud.ITEM_RENDERER.renderGuiItemDecoration(hud.minecraft.textRenderer, hud.minecraft.textureManager, stack, x + eResult.offsetX, y + eResult.offsetY);
        }
    }
}
