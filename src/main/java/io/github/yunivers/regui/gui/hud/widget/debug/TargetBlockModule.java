package io.github.yunivers.regui.gui.hud.widget.debug;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.property.Property;
import net.modificationstation.stationapi.api.tag.TagKey;

import java.util.Collection;

public class TargetBlockModule extends DebugModule
{
    public TargetBlockModule()
    {
        super();
    }

    @Override
    public void render(Minecraft minecraft, int y, int width)
    {
        TextRenderer textRenderer = minecraft.textRenderer;
        HitResult hit = minecraft.crosshairTarget;
        int offset = y;
        if (hit != null && hit.type == HitResultType.BLOCK)
        {
            BlockState state = minecraft.world.getBlockState(hit.blockX, hit.blockY, hit.blockZ);

            String text = "Block: " + state.getBlock().getTranslatedName();
            drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y += 10, 0xFFFFFF);

            text = "Meta: " + minecraft.world.getBlockMeta(hit.blockX, hit.blockY, hit.blockZ);
            drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y += 10, 0xFFFFFF);

            Collection<Property<?>> properties = state.getProperties();
            if (!properties.isEmpty())
            {
                text = "Properties:";
                drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y += 10, 0xFFFFFF);

                for (Property<?> property : properties)
                {
                    text = property.getName() + ": " + state.get(property);
                    drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y += 10, 0xE0E0E0);
                }
            }

            Collection<TagKey<Block>> tags = state.streamTags().toList();
            if (!tags.isEmpty())
            {
                text = "Tags:";
                drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y += 10, 0xFFFFFF);

                for (TagKey<Block> property : tags)
                {
                    text = "#" + property.id();
                    drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y += 10, 0xE0E0E0);
                }
            }

            if (FabricLoader.getInstance().isDevelopmentEnvironment())
            {
                BlockEntity entity = minecraft.world.getBlockEntity(hit.blockX, hit.blockY, hit.blockZ);
                if (entity != null)
                {
                    String className = entity.getClass().getName();
                    text = "Tile Entity: " + className.substring(className.lastIndexOf('.') + 1);
                    drawTextWithShadow(textRenderer, text, width - textRenderer.getWidth(text) - 2, y + 20, 0xFFFFFF);
                    y += 20;
                }
            }
        }

        height = y - offset;
    }
}
