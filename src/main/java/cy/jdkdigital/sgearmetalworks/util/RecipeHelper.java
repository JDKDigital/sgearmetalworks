package cy.jdkdigital.sgearmetalworks.util;

import cy.jdkdigital.sgearmetalworks.recipe.SilentGearCastingRecipe;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeHelper
{
    static Map<String, RecipeHolder<SilentGearCastingRecipe>> gearCastingRecipeCache = new HashMap<>();
    public static RecipeHolder<SilentGearCastingRecipe> getSilentGearCastingRecipe(Level level, ItemStack cast, FluidStack fluid) {
        String cacheKey = cy.jdkdigital.productivemetalworks.util.RecipeHelper.itemCacheKey(cast) + cy.jdkdigital.productivemetalworks.util.RecipeHelper.fluidCacheKey(fluid);
        if (!gearCastingRecipeCache.containsKey(cacheKey)) {
            for (RecipeHolder<SilentGearCastingRecipe> recipeHolder : level.getRecipeManager().getAllRecipesFor(SGearMetalworksRegistrator.SG_GEAR_CASTING_TYPE.get())) {
                if (recipeHolder.value().matches(cast, fluid, level)) {
                    gearCastingRecipeCache.put(cacheKey, recipeHolder);
                }
            }
        }
        return gearCastingRecipeCache.getOrDefault(cacheKey, null);
    }
}
