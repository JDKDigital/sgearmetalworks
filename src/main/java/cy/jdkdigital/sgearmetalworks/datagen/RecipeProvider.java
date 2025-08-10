package cy.jdkdigital.sgearmetalworks.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import cy.jdkdigital.productivebees.common.crafting.conditions.BeeExistsCondition;
import cy.jdkdigital.productivebees.common.crafting.ingredient.ComponentIngredient;
import cy.jdkdigital.productivebees.init.ModDataComponents;
import cy.jdkdigital.productivebees.init.ModEntities;
import cy.jdkdigital.productivebees.init.ModItems;
import cy.jdkdigital.productivelib.common.condition.LazyCondition;
import cy.jdkdigital.productivelib.crafting.condition.FluidTagEmptyCondition;
import cy.jdkdigital.productivemetalworks.ProductiveMetalworks;
import cy.jdkdigital.productivemetalworks.datagen.recipe.BlockCastingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.datagen.recipe.FluidAlloyingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.datagen.recipe.ItemCastingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.datagen.recipe.ItemMeltingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.datagen.recipe.GearComponentIngredient;
import cy.jdkdigital.sgearmetalworks.datagen.recipe.SilentGearCastingRecipeBuilder;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.util.CastingMaterialCategories;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.TagFluidIngredient;
import net.neoforged.neoforge.registries.DeferredItem;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.crafting.ingredient.BlueprintIngredient;
import net.silentchaos512.gear.crafting.ingredient.GearPartIngredient;
import net.silentchaos512.gear.crafting.ingredient.PartMaterialIngredient;
import net.silentchaos512.gear.crafting.recipe.ShapedGearRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapelessCompoundPartRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapelessGearRecipe;
import net.silentchaos512.gear.gear.material.MaterialCategories;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.item.CompoundPartItem;
import net.silentchaos512.gear.item.GearItemSet;
import net.silentchaos512.gear.item.gear.GearArmorItem;
import net.silentchaos512.gear.setup.GearItemSets;
import net.silentchaos512.gear.setup.SgDataComponents;
import net.silentchaos512.gear.setup.SgItems;
import net.silentchaos512.gear.setup.SgTags;
import net.silentchaos512.gear.setup.gear.GearTypes;
import net.silentchaos512.gear.setup.gear.PartTypes;
import net.silentchaos512.gems.util.Gems;
import net.silentchaos512.lib.data.recipe.ExtendedShapedRecipeBuilder;
import net.silentchaos512.lib.data.recipe.ExtendedShapelessRecipeBuilder;
import net.silentchaos512.lib.util.NameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder
{
    static String[] SGM_METALS = new String[]{
            "sgearmetalworks:uru_metal"
    };
    static String[] SG_METALS = new String[]{
            "silentgear:crimson_iron", "silentgear:crimson_steel", "silentgear:blaze_gold",
            "silentgear:azure_silver", "silentgear:azure_electrum", "silentgear:tyrian_steel"
    };

    public RecipeProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> registries) {
        super(gen, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ItemMeltingRecipeBuilder.of(Ingredient.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("silentgear", "crushed_shulker_shell"))), new FluidStack(MetalworksRegistrator.MOLTEN_SHULKER_SHELL.get(), 100))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "melting/crushed_shulker_shell"));

        alloyRecipe(List.of(SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_CRIMSON_IRON, 810), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_BLAZE, 200), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_MAGMA_CREAM, 100)), 1, new FluidStack(SGearMetalworksRegistrator.MOLTEN_CRIMSON_STEEL, 270), recipeOutput);
        alloyRecipe(List.of(SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_GOLD, 90), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_BLAZE, 200)), 1, new FluidStack(SGearMetalworksRegistrator.MOLTEN_BLAZE_GOLD, 90), recipeOutput);
        alloyRecipe(List.of(SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_AZURE_SILVER, 360), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_GOLD, 180), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_ENDER, 100)), 1, new FluidStack(SGearMetalworksRegistrator.MOLTEN_AZURE_ELECTRUM, 90), recipeOutput);
        alloyRecipe(List.of(SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_CRIMSON_STEEL, 90), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_AZURE_ELECTRUM, 90), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_SHULKER_SHELL, 100), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_ANCIENT_DEBRIS, 100)), 1, new FluidStack(SGearMetalworksRegistrator.MOLTEN_TYRIAN_STEEL, 360), recipeOutput);

        alloyRecipe(List.of(
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_HEAVY_CORE, 180),
                SizedFluidIngredient.of(ModTags.Fluids.MEAT, 180),
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_AZURE_ELECTRUM, 90),
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_TYRIAN_STEEL, 90),
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_SHULKER_SHELL, 100),
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_ANCIENT_DEBRIS, 100)
        ), 1, new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL, 90),
                recipeOutput.withConditions(new NotCondition(new ModLoadedCondition("allthemodium"))));
        alloyRecipe(List.of(
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_HEAVY_CORE, 180),
                SizedFluidIngredient.of(ModTags.Fluids.MEAT, 180),
                SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_TYRIAN_STEEL, 90),
                SizedFluidIngredient.of(BuiltInRegistries.FLUID.get(ResourceLocation.parse("allthemodium:molten_allthemodium")), 90),
                SizedFluidIngredient.of(BuiltInRegistries.FLUID.get(ResourceLocation.parse("allthemodium:molten_vibranium")), 90),
                SizedFluidIngredient.of(BuiltInRegistries.FLUID.get(ResourceLocation.parse("allthemodium:molten_unobtainium")), 90)
        ), 1, new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL, 90),
                recipeOutput.withConditions(new ModLoadedCondition("allthemodium")), "alloying/atm/molten_uru_metal");

        ItemMeltingRecipeBuilder.of(Ingredient.of(ModTags.Items.URU_METAL_STORAGE_BLOCKS), new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL, 800), 3000, 30000)
                .save(recipeOutput, "melting/uru_metal_block");
        ItemMeltingRecipeBuilder.of(Ingredient.of(ModTags.Items.URU_METAL_INGOTS), new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL, 90), 3000, 30000)
                .save(recipeOutput, "melting/uru_metal_ingot");
        ItemMeltingRecipeBuilder.of(Ingredient.of(ModTags.Items.URU_METAL_NUGGETS), new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL, 10), 3000, 30000)
                .save(recipeOutput, "melting/uru_metal_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SGearMetalworksRegistrator.URU_METAL_NUGGET.get(), 9)
                .requires(SGearMetalworksRegistrator.URU_METAL_INGOT.get())
                .unlockedBy(getHasName(SGearMetalworksRegistrator.URU_METAL_INGOT.get()), has(SGearMetalworksRegistrator.URU_METAL_INGOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "crafting/uru_metal_nugget_from_ingot"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SGearMetalworksRegistrator.URU_METAL_INGOT.get(), 9)
                .requires(SGearMetalworksRegistrator.URU_METAL_BLOCK.get())
                .unlockedBy(getHasName(SGearMetalworksRegistrator.URU_METAL_BLOCK.get()), has(SGearMetalworksRegistrator.URU_METAL_BLOCK.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "crafting/uru_metal_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SGearMetalworksRegistrator.URU_METAL_INGOT.get(), 1)
                .requires(SGearMetalworksRegistrator.URU_METAL_NUGGET.get(), 9)
                .unlockedBy(getHasName(SGearMetalworksRegistrator.URU_METAL_NUGGET.get()), has(SGearMetalworksRegistrator.URU_METAL_NUGGET.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "crafting/uru_metal_ingot_from_nugget"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SGearMetalworksRegistrator.URU_METAL_BLOCK.get(), 1)
                .requires(SGearMetalworksRegistrator.URU_METAL_INGOT.get(), 9)
                .unlockedBy(getHasName(SGearMetalworksRegistrator.URU_METAL_INGOT.get()), has(SGearMetalworksRegistrator.URU_METAL_INGOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "crafting/uru_metal_block_from_ingot"));

        cy.jdkdigital.productivemetalworks.datagen.RecipeProvider.metalCompat(SGearMetalworks.MODID, SG_METALS, recipeOutput);
        cy.jdkdigital.productivemetalworks.datagen.RecipeProvider.modMetalCompat(SGearMetalworks.MODID, SGM_METALS, recipeOutput);
        cy.jdkdigital.productivemetalworks.datagen.RecipeProvider.modMetalCompat("silentgear", SG_METALS, recipeOutput);

        sgemsCompat(recipeOutput);

        gearCasting(recipeOutput);
        gearCrafting(recipeOutput);
        georeCompat(recipeOutput);

        // Uru metal bee
        ItemCastingRecipeBuilder.of(ComponentIngredient.of(getBeeSpawnEgg("productivebees:beebee")), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_URU_METAL, 800), getBeeSpawnEgg("productivebees:uru_metal"), true)
                .save(recipeOutput.withConditions(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:beebee")))).withConditions(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:uru_metal")))), ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/uru/bee_spawn_egg_from_bee"));
        ItemCastingRecipeBuilder.of(ComponentIngredient.of(getBeeSpawnEgg("productivebees:netherite")), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_URU_METAL, 800), getBeeSpawnEgg("productivebees:uru_metal"), true)
                .save(recipeOutput.withConditions(new NotCondition(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:beebee"))))).withConditions(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:netherite")))).withConditions(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:uru_metal")))), ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/uru/bee_spawn_egg_from_netherite"));

        var combStack = ModItems.CONFIGURABLE_HONEYCOMB.get().getDefaultInstance();
        combStack.set(ModDataComponents.BEE_TYPE, ResourceLocation.parse("productivebees:uru_metal"));
        ItemMeltingRecipeBuilder.of(ComponentIngredient.of(combStack), new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL.get(), 10), 3000, 30000)
                .save(recipeOutput.withConditions(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:uru_metal")))), ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "melting/pbees/uru_metal_comb"));

        var combBlockStack = ModItems.CONFIGURABLE_COMB_BLOCK.get().getDefaultInstance();
        combBlockStack.set(ModDataComponents.BEE_TYPE, ResourceLocation.parse("productivebees:uru_metal"));
        ItemMeltingRecipeBuilder.of(ComponentIngredient.of(combBlockStack), new FluidStack(SGearMetalworksRegistrator.MOLTEN_URU_METAL.get(), 40), 3000, 30000)
                .save(recipeOutput.withConditions(new LazyCondition(new BeeExistsCondition(ResourceLocation.parse("productivebees:uru_metal")))), ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "melting/pbees/uru_metal_comb_block"));

    }

    private static ItemStack getBeeSpawnEgg(String type) {
        var inputEgg = new ItemStack(ModItems.CONFIGURABLE_SPAWN_EGG.get());
        var inputTag = new CompoundTag();
        inputTag.putString("id", ModEntities.CONFIGURABLE_BEE.getId().toString());
        inputTag.putString("type", type);
        inputEgg.set(DataComponents.ENTITY_DATA, CustomData.of(inputTag));
        return inputEgg;
    }

    private void sgemsCompat(RecipeOutput recipeOutput) {
        for(Gems gem: Gems.values()) {
            var fluid = BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "molten_" + gem.getName()));
            var fluidTag = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_" + gem.getName()));

            // melt ore, block, gem
            ItemMeltingRecipeBuilder.of(Ingredient.of(gem.getBlockItemTag()), new FluidStack(fluid, 900))
                    .save(recipeOutput.withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "melting/storage_blocks/" + gem.getName() + "_block"));
            ItemMeltingRecipeBuilder.of(Ingredient.of(gem.getOreItemTag()), new FluidStack(fluid, 300))
                    .save(recipeOutput.withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "melting/ores/" + gem.getName()));
            ItemMeltingRecipeBuilder.of(Ingredient.of(gem.getItemTag()), new FluidStack(fluid, 100))
                    .save(recipeOutput.withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "melting/gems/" + gem.getName()));

            // cast block, gem
            BlockCastingRecipeBuilder.of(SizedFluidIngredient.of(fluidTag, 900), gem.getBlock().asItem().getDefaultInstance())
                    .save(recipeOutput.withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "casting/storage_blocks/" + gem.getName()));
            ItemCastingRecipeBuilder.of(MetalworksRegistrator.CAST_GEM.get().getDefaultInstance(), SizedFluidIngredient.of(fluidTag, 100), gem.getItem().getDefaultInstance())
                    .save(recipeOutput.withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "casting/gems/" + gem.getName()));
        }
    }

    private static void gearCasting(RecipeOutput recipeOutput) {
        var compatRecipeOutput = recipeOutput.withConditions(new ModLoadedCondition("silentgear"));

        Map<String, Pair<PartMaterialIngredient, Integer>> materialCost = new HashMap<>() {{
            put("sword", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("katana", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("machete", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("spear", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 1));
            put("trident", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("mace", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("knife", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 1));
            put("dagger", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 1));
            put("pickaxe", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("shovel", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 1));
            put("axe", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("paxel", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 5));
            put("hammer", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 6));
            put("excavator", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 5));
            put("hoe", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("mattock", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 4));
            put("prospector_hammer", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("saw", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 5));
            put("sickle", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("shears", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("fishing_rod", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("bow", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("crossbow", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("slingshot", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("arrow", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.PROJECTILE.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 1));

            put("ring", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
            put("bracelet", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("necklace", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3));
            put("helmet", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.HELMET.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 5));
            put("chestplate", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CHESTPLATE.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 8));
            put("leggings", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.LEGGINGS.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 7));
            put("boots", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.BOOTS.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 4));
            put("shield", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.ARMOR.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2));
        }};

        // Iterate blueprints and use them as the cast
        GearItemSets.getIterator().forEachRemaining(gearItemSet -> {
            var cast = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, gearItemSet.name() + "_cast")).getDefaultInstance();
            // cast recipe
            if (!gearItemSet.partName().equals("mace_core") && !gearItemSet.partName().equals("elytra_wings")) {
                ItemCastingRecipeBuilder.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("silentgear", gearItemSet.partName())).getDefaultInstance(), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_STEEL, 360), cast, true)
                        .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/" + gearItemSet.name() + "_cast"));
            }
            // casting recipe
            var amount = materialCost.get(gearItemSet.name());
            if (gearItemSet.partName().equals("mace_core")) {
                SilentGearCastingRecipeBuilder.of(Items.HEAVY_CORE.getDefaultInstance(), amount.getFirst(), amount.getSecond(), gearItemSet.mainPart().getDefaultInstance(), true)
                    .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/" + gearItemSet.partName()));
            } else if (!gearItemSet.partName().equals("elytra_wings")) {
                SilentGearCastingRecipeBuilder.of(cast, amount.getFirst(), amount.getSecond(), gearItemSet.mainPart().getDefaultInstance(), false)
                    .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/" + gearItemSet.partName()));
            }

            atmCompat(recipeOutput, gearItemSet, amount);
        });
        SilentGearCastingRecipeBuilder.of(SGearMetalworksRegistrator.CAST_TOOL_ROD.get().getDefaultInstance(), PartMaterialIngredient.of(PartTypes.ROD.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2, SgItems.ROD.get().getDefaultInstance(), false)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/tool_rod"));
        SilentGearCastingRecipeBuilder.of(SGearMetalworksRegistrator.CAST_TIP.get().getDefaultInstance(), PartMaterialIngredient.of(PartTypes.TIP.get(), CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2, SgItems.TIP.get().getDefaultInstance(), false)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/tip"));

        ItemCastingRecipeBuilder.of(SgItems.ROD.get().getDefaultInstance(), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_STEEL, 360), SGearMetalworksRegistrator.CAST_TOOL_ROD.get().getDefaultInstance(), true)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/rod_cast"));
        ItemCastingRecipeBuilder.of(SgItems.TIP.get().getDefaultInstance(), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_STEEL, 360), SGearMetalworksRegistrator.CAST_TIP.get().getDefaultInstance(), true)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/tip_cast"));

        ItemCastingRecipeBuilder.of(SgItems.NETHER_BANANA.get().getDefaultInstance(), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_GOLD, 720), SgItems.GOLDEN_NETHER_BANANA.get().getDefaultInstance(), true)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/golden_nether_banana"));
    }

    private static void gearCrafting(RecipeOutput recipeOutput) {
        registerCompoundParts(recipeOutput);

        toolRecipes(recipeOutput, "sword", 2, GearItemSets.SWORD);
        toolRecipes(recipeOutput, "katana", 3, GearItemSets.KATANA);
        toolRecipes(recipeOutput, "machete", 3, GearItemSets.MACHETE);
        toolRecipes(recipeOutput, "spear", 1, GearItemSets.SPEAR);
        toolRecipes(recipeOutput, "trident", 3, GearItemSets.TRIDENT);
        maceRecipes(recipeOutput);
        toolRecipes(recipeOutput, "knife", 1, GearItemSets.KNIFE);
        toolRecipes(recipeOutput, "dagger", 1, GearItemSets.DAGGER);
        toolRecipes(recipeOutput, "pickaxe", 3, GearItemSets.PICKAXE);
        toolRecipes(recipeOutput, "shovel", 1, GearItemSets.SHOVEL);
        toolRecipes(recipeOutput, "axe", 3, GearItemSets.AXE);
        toolRecipes(recipeOutput, "paxel", 5, GearItemSets.PAXEL);
        toolRecipes(recipeOutput, "hammer", 6, GearItemSets.HAMMER);
        toolRecipes(recipeOutput, "excavator", 5, GearItemSets.EXCAVATOR);
        toolRecipes(recipeOutput, "hoe", 2, GearItemSets.HOE);
        toolRecipes(recipeOutput, "mattock", 4, GearItemSets.MATTOCK);
        toolRecipes(recipeOutput, "prospector_hammer", 2, GearItemSets.PROSPECTOR_HAMMER);
        toolRecipes(recipeOutput, "saw", 5, GearItemSets.SAW);
        toolRecipes(recipeOutput, "sickle", 3, GearItemSets.SICKLE);
        toolRecipes(recipeOutput, "shears", 2, GearItemSets.SHEARS);
        bowRecipes(recipeOutput, "fishing_rod", 2, GearItemSets.FISHING_ROD);
        bowRecipes(recipeOutput, "bow", 3, GearItemSets.BOW);
        bowRecipes(recipeOutput, "crossbow", 3, GearItemSets.CROSSBOW);
        bowRecipes(recipeOutput, "slingshot", 2, GearItemSets.SLINGSHOT);
        arrowRecipes(recipeOutput, "arrow", GearItemSets.ARROW);
        curioRecipes(recipeOutput, "ring", 2, GearItemSets.RING);
        curioRecipes(recipeOutput, "bracelet", 3, GearItemSets.BRACELET);
        curioRecipes(recipeOutput, "necklace", 3, GearItemSets.NECKLACE);

        armorRecipes(recipeOutput, 5, GearItemSets.HELMET);
        armorRecipes(recipeOutput, 8, GearItemSets.CHESTPLATE);
        armorRecipes(recipeOutput, 7, GearItemSets.LEGGINGS);
        armorRecipes(recipeOutput, 4, GearItemSets.BOOTS);

        // Shield
        shapelessPart(RecipeCategory.COMBAT, GearItemSets.SHIELD.mainPart())
                .requires(BlueprintIngredient.of(GearItemSets.SHIELD))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.SHIELD.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2)
                .save(recipeOutput, SilentGear.getId("gear/shield_plate"));
        shapelessGear(RecipeCategory.COMBAT, GearItemSets.SHIELD.gearItem())
                .requires(BlueprintIngredient.of(GearItemSets.SHIELD))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.SHIELD.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .save(recipeOutput, SilentGear.getId("gear/shield_quick"));
        // alloy shield parts
        shapelessGear(RecipeCategory.TOOLS, GearItemSets.SHIELD.gearItem())
                .requires(BlueprintIngredient.of(GearItemSets.SHIELD))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), 2)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/alloy_shield"));

        // Disable conversion recipes
        for (String material: new String[]{"iron", "golden", "diamond", "netherite"}) {
            for (String gear: new String[]{"axe", "boots", "chestplate", "helmet", "hoe", "leggings", "pickaxe", "shoves", "sword"}) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICK).requires(Items.STICK).unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .save(recipeOutput.withConditions(new NotCondition(new ModLoadedCondition(SGearMetalworks.MODID))), ResourceLocation.fromNamespaceAndPath("silentgear", "gear/convert/" + material + "_" + gear));
            }
        }

        // Rough recipes
        shapedGear(RecipeCategory.COMBAT, GearItemSets.SWORD.gearItem())
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .define('#', PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .define('/', SgTags.Items.RODS_ROUGH)
                .save(recipeOutput, SilentGear.getId("gear/rough/sword"));
        shapedGear(RecipeCategory.COMBAT, GearItemSets.DAGGER.gearItem())
                .pattern("#")
                .pattern("/")
                .define('#', PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .define('/', SgTags.Items.RODS_ROUGH)
                .save(recipeOutput, SilentGear.getId("gear/rough/dagger"));
        shapedGear(RecipeCategory.COMBAT, GearItemSets.KNIFE.gearItem())
                .pattern(" #")
                .pattern("/ ")
                .define('#', PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .define('/', SgTags.Items.RODS_ROUGH)
                .save(recipeOutput, SilentGear.getId("gear/rough/knife"));
        shapedGear(RecipeCategory.TOOLS, GearItemSets.PICKAXE.gearItem())
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .define('#', PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .define('/', SgTags.Items.RODS_ROUGH)
                .save(recipeOutput, SilentGear.getId("gear/rough/pickaxe"));
        shapedGear(RecipeCategory.TOOLS, GearItemSets.SHOVEL.gearItem())
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .define('#', PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .define('/', SgTags.Items.RODS_ROUGH)
                .save(recipeOutput, SilentGear.getId("gear/rough/shovel"));
        shapedGear(RecipeCategory.TOOLS, GearItemSets.AXE.gearItem())
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .define('#', PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .define('/', SgTags.Items.RODS_ROUGH)
                .save(recipeOutput, SilentGear.getId("gear/rough/axe"));
    }

    private static ExtendedShapelessRecipeBuilder.Basic<ShapelessCompoundPartRecipe> compoundPart(DeferredItem<?> item, int count) {
        return new ExtendedShapelessRecipeBuilder.Basic<>(RecipeCategory.MISC, item.toStack(count), ShapelessCompoundPartRecipe::new);
    }

    private static void registerCompoundParts(RecipeOutput consumer) {
        compoundPart(SgItems.ROD, 4)
                .requires(BlueprintIngredient.of(SgItems.ROD_BLUEPRINT.get()))
                .requires(PartMaterialIngredient.of(PartTypes.ROD.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 2)
                .save(consumer, SilentGear.getId("part/rod"));

        compoundPart(SgItems.TIP, 1)
                .requires(BlueprintIngredient.of(SgItems.TIP_BLUEPRINT.get()))
                .requires(PartMaterialIngredient.of(PartTypes.TIP.get()).not(CastingMaterialCategories.CASTING))
                .save(consumer, SilentGear.getId("part/tip"));

        // alloy tip and rod
        shapelessPart(RecipeCategory.TOOLS, SgItems.ROD)
                .requires(BlueprintIngredient.of(SgItems.ROD_BLUEPRINT.get()))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), 2)
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "part/rod"));
        shapelessPart(RecipeCategory.TOOLS, SgItems.TIP)
                .requires(BlueprintIngredient.of(SgItems.TIP_BLUEPRINT.get()))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "part/tip"));
    }

    private static void toolRecipes(RecipeOutput consumer, String name, int mainCount, GearItemSet<?> itemSet) {
        var ingredient = PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING);
        // Tool head
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(ingredient, mainCount)
                .save(consumer, SilentGear.getId("gear/" + name + "_head"));
        // Quick tool (mains and rods, skipping head)
        shapelessGear(RecipeCategory.TOOLS, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(ingredient, mainCount)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
        // alloy gear parts
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), mainCount)
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/" + name + "_head"));
    }

    private static void maceRecipes(RecipeOutput output) {
        var itemSet = GearItemSets.MACE;
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Items.HEAVY_CORE)
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3)
                .save(output, SilentGear.getId("gear/mace_core"));
        shapelessGear(RecipeCategory.TOOLS, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Items.HEAVY_CORE)
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), 3)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .save(output, SilentGear.getId("gear/mace_quick"));
        // alloy gear parts
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Items.HEAVY_CORE)
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), 3)
                .save(output, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/mace_core"));
    }

    private static void bowRecipes(RecipeOutput consumer, String name, int mainCount, GearItemSet<?> itemSet) {
        // Main part
        shapelessPart(RecipeCategory.COMBAT, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), mainCount)
                .save(consumer, SilentGear.getId("gear/" + name + "_main"));
        // Quick tool (main materials, rod, and cord, skipping main part)
        shapelessGear(RecipeCategory.COMBAT, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), mainCount)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .requires(GearPartIngredient.of(PartTypes.CORD.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
        // alloy gear parts
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), mainCount)
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/" + name + "_main"));
    }

    private static void arrowRecipes(RecipeOutput consumer, String name, GearItemSet<?> itemSet) {
        // Arrow head
        shapelessPart(RecipeCategory.COMBAT, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.PROJECTILE.get()).not(CastingMaterialCategories.CASTING))
                .save(consumer, SilentGear.getId("gear/" + name + "_head"));
        // Quick arrows
        shapelessGear(RecipeCategory.COMBAT, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .requires(GearPartIngredient.of(PartTypes.FLETCHING.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
        // alloy gear parts
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/" + name + "_head"));
    }

    private static void armorRecipes(RecipeOutput consumer, int mainCount, GearItemSet<? extends GearArmorItem> itemSet) {
        shapelessPart(RecipeCategory.COMBAT, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), itemSet.gearItem().getGearType()).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), mainCount)
                .save(consumer, SilentGear.getId("gear/" + NameUtils.fromItem(itemSet.mainPart()).getPath()));
        // alloy gear parts
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), mainCount)
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/" + NameUtils.fromItem(itemSet.mainPart()).getPath() + "_head"));
    }

    private static void curioRecipes(RecipeOutput consumer, String name, int mainCount, GearItemSet<?> itemSet) {
        shapelessPart(RecipeCategory.MISC, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), MaterialCategories.METAL, CastingMaterialCategories.CRUDE).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), mainCount)
                .save(consumer, SilentGear.getId("gear/" + name + "_main_only"));

        shapelessGear(RecipeCategory.MISC, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), MaterialCategories.METAL, CastingMaterialCategories.CRUDE).not(CastingMaterialCategories.CASTING).not(CastingMaterialCategories.INFUSING), mainCount)
                .requires(GearPartIngredient.of(PartTypes.SETTING.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
        // alloy gear parts
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Ingredient.of(SgItems.ALLOY_INGOT, SgItems.SUPER_ALLOY, SgItems.HYBRID_GEM), mainCount)
                .save(consumer, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "gear/" + name + "_main_only"));
    }

    private static ExtendedShapedRecipeBuilder.Basic<ShapedGearRecipe> shapedGear(RecipeCategory category, ItemLike item) {
        return new ExtendedShapedRecipeBuilder.Basic<>(category, new ItemStack(item), ShapedGearRecipe::new);
    }

    private static ExtendedShapelessRecipeBuilder.Basic<ShapelessCompoundPartRecipe> shapelessPart(RecipeCategory category, ItemLike item) {
        return new ExtendedShapelessRecipeBuilder.Basic<>(category, new ItemStack(item), ShapelessCompoundPartRecipe::new);
    }
    private static ExtendedShapelessRecipeBuilder.Basic<ShapelessGearRecipe> shapelessGear(RecipeCategory category, ItemLike item) {
        return new ExtendedShapelessRecipeBuilder.Basic<>(category, new ItemStack(item), ShapelessGearRecipe::new);
    }

    private static void alloyRecipe(List<SizedFluidIngredient> inputs, int speed, FluidStack output, RecipeOutput recipeOutput) {
        alloyRecipe(inputs, speed, output, recipeOutput, "alloying/" + BuiltInRegistries.FLUID.getKey(output.getFluid()).getPath());
    }
    private static void alloyRecipe(List<SizedFluidIngredient> inputs, int speed, FluidStack output, RecipeOutput recipeOutput, String recipeId) {
        var conditionalRecipeOutput = recipeOutput;
        for (SizedFluidIngredient sizedFluidIngredient : inputs) {
            if (sizedFluidIngredient.ingredient() instanceof TagFluidIngredient tagFluidIngredient) {
                conditionalRecipeOutput = conditionalRecipeOutput.withConditions(new NotCondition(new FluidTagEmptyCondition(tagFluidIngredient.tag())));
            }
        }
        FluidAlloyingRecipeBuilder.of(inputs, speed, output).save(conditionalRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, recipeId));
    }

    private void georeCompat(RecipeOutput recipeOutput) {
        for (String resource : new String[]{"ruby", "topaz", "sapphire"}) {
            var fluid = BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "molten_" + (resource.equals("coal") ? "carbon" : resource)));

            var shardItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("geore", resource + "_shard"));
            var blockItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("geore", resource + "_block"));

            // melt block
            ItemMeltingRecipeBuilder.of(
                    Ingredient.of(blockItem),
                    new FluidStack(fluid, 400)
            ).save(recipeOutput.withConditions(new ModLoadedCondition("geore")).withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "melting/geore/" + resource + "_block"));
            // melt shard
            ItemMeltingRecipeBuilder.of(
                    Ingredient.of(shardItem),
                    new FluidStack(fluid, 100)
            ).save(recipeOutput.withConditions(new ModLoadedCondition("geore")).withConditions(new ModLoadedCondition("silentgems")), ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "melting/geore/" + resource + "_shard"));
        }
    }

    private static void atmCompat(RecipeOutput recipeOutput, GearItemSet<?> gearItemSet, Pair<PartMaterialIngredient, Integer> amount) {
        // only be able to cast uru onto unobtainium parts
        if (!gearItemSet.partName().equals("elytra_wings")) {
            ItemStack cast = gearItemSet.mainPart().getDefaultInstance();
            ItemStack output = gearItemSet.mainPart().getDefaultInstance();
            JsonElement inputMaterial = JsonParser.parseString("{\"item\": {\"count\": 1, \"id\": \"silentgear:tyrian_steel_ingot\"}, \"material\": \"silentgear:tyrian_steel\"}");
            JsonElement uruMaterial = JsonParser.parseString("{\"item\": {\"count\": 1, \"id\": \"sgearmetalworks:uru_metal_ingot\"}, \"material\": \"sgearmetalworks:uru_metal\"}");

//            inputMaterial = JsonParser.parseString("{\"item\": {\"count\": 1, \"id\": \"kubejs:silent_unobtainium_plate\"}, \"material\": \"silentgear:unobtainium\"}");

            MaterialInstance INPUT_MATERIAL = MaterialInstance.CODEC.decode(JsonOps.INSTANCE, inputMaterial).getOrThrow().getFirst();
            MaterialInstance URU_MATERIAL = MaterialInstance.CODEC.decode(JsonOps.INSTANCE, uruMaterial).getOrThrow().getFirst();
            List<MaterialInstance> ingredientMats = new ArrayList<>();
            List<MaterialInstance> outputMats = new ArrayList<>();
            for (int i = 0; i < amount.getSecond(); i++) {
                ingredientMats.add(INPUT_MATERIAL);
                outputMats.add(URU_MATERIAL);
            }
            cast.set(SgDataComponents.MATERIAL_LIST, ingredientMats);
            output.set(SgDataComponents.MATERIAL_LIST, outputMats);
            ItemCastingRecipeBuilder.of(GearComponentIngredient.of(cast), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_URU_METAL, 90 * amount.getSecond()), output, true)
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/uru/" + gearItemSet.partName()));
        }
    }
}
