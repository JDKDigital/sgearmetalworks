package cy.jdkdigital.sgearmetalworks.integration.jei;

import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.recipe.SilentGearCastingRecipe;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
    private static final ResourceLocation pluginId = ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, SGearMetalworks.MODID);

    public static final RecipeType<RecipeHolder<SilentGearCastingRecipe>> SG_CASTING = RecipeType.createRecipeHolderType(SGearMetalworksRegistrator.SG_GEAR_CASTING_TYPE.getId());

    @Override
    public ResourceLocation getPluginUid() {
        return pluginId;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registration.addRecipeCategories(new SilentGearCastingRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(MetalworksRegistrator.CASTING_TABLE.get(), SG_CASTING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(SG_CASTING, recipeManager.getAllRecipesFor(SGearMetalworksRegistrator.SG_GEAR_CASTING_TYPE.get()));
    }
}
