package cy.jdkdigital.sgearmetalworks.event;

import cy.jdkdigital.productivemetalworks.ProductiveMetalworks;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.model.DynamicFluidContainerModel;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@EventBusSubscriber(modid = SGearMetalworks.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        SGearMetalworks.FLUID_TYPES.getEntries().forEach(fluidHolder -> {
            event.registerFluidType(new IClientFluidTypeExtensions()
            {
                @Override
                public @NotNull ResourceLocation getStillTexture() {
                    return ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "block/fluid/molten_metal");
                }

                @Override
                public @NotNull ResourceLocation getFlowingTexture() {
                    return ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "block/fluid/molten_metal_flow");
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "block/fluid/molten_metal");
                }

                @Override
                public int getTintColor() {
                    return SGearMetalworksRegistrator.FLUID_COLORS.get(fluidHolder.getId().getPath());
                }

                @Override
                public @NotNull Vector3f modifyFogColor(@NotNull Camera camera, float partialTick, @NotNull ClientLevel level, int renderDistance, float darkenWorldAmount, @NotNull Vector3f fluidFogColor) {
                    var fluidColor = SGearMetalworksRegistrator.FLUID_COLORS.get(fluidHolder.getId().getPath());;
                    return new Vector3f(fluidColor >> 16 & 255, fluidColor >> 8 & 255, fluidColor & 255).div(255.0F).mul(0.2f);
                }
            }, fluidHolder.get());
        });
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        SGearMetalworks.ITEMS.getEntries().forEach(itemHolder -> {
            if (itemHolder.getId().getPath().contains("_bucket")) {
                event.register(new DynamicFluidContainerModel.Colors(), itemHolder.get());
            }
        });
    }
}
