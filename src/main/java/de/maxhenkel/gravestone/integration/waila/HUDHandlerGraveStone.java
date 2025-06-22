package de.maxhenkel.gravestone.integration.waila;

import de.maxhenkel.gravestone.GraveUtils;
import de.maxhenkel.gravestone.Main;
import de.maxhenkel.gravestone.tileentity.GraveStoneTileEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerGraveStone implements IBlockComponentProvider {

    public static final HUDHandlerGraveStone INSTANCE = new HUDHandlerGraveStone();

    private static final ResourceLocation OBJECT_NAME_TAG = ResourceLocation.fromNamespaceAndPath("jade", "object_name");

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "grave");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof GraveStoneTileEntity grave) {
            iTooltip.remove(OBJECT_NAME_TAG);
            iTooltip.add(grave.getName().copy().withStyle(ChatFormatting.WHITE));
            Component time = GraveUtils.getDate(grave.getDeath().getTimestamp());
            if (time != null) {
                iTooltip.add(Component.translatable("message.gravestone.date_of_death", time));
            }

            CompoundTag data = blockAccessor.getServerData();
            if (data.contains("ItemCount")) {
                iTooltip.add(Component.translatable("message.gravestone.item_count", data.getIntOr("ItemCount", 0)));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}