package cy.jdkdigital.sgearmetalworks.event;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = SGearMetalworks.MODID)
public class EventHandler
{
    @SubscribeEvent
    public static void itemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().is(ModTags.Items.BLUEPRINT_OVERRIDE)) {
            event.getToolTip().add(Component.translatable("tooltip." + SGearMetalworks.MODID + ".blueprint").withStyle(ChatFormatting.RED));
        }
    }
}
