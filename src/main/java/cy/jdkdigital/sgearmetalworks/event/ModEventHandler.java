package cy.jdkdigital.sgearmetalworks.event;

import cy.jdkdigital.productivemetalworks.event.CastingRecipeEvent;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.util.RecipeHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = SGearMetalworks.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventHandler
{
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(MetalworksRegistrator.TAB_KEY)) {
            for (DeferredHolder<Item, ? extends Item> item : SGearMetalworks.ITEMS.getEntries()) {
                event.accept(item.value());
            }
        }
    }

    @SubscribeEvent
    public static void getCastingRecipe(CastingRecipeEvent event) {
        if (event.isTable && event.cast.is(ModTags.Items.SG_CASTS) || event.cast.is(Items.HEAVY_CORE)) {
            var recipe = RecipeHelper.getSilentGearCastingRecipe(event.level, event.cast, event.fluid);
            if (recipe != null) {
                event.setRecipe(recipe.value());
            }
        }
    }
}
