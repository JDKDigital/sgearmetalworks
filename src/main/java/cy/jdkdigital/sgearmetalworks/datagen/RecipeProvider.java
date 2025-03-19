package cy.jdkdigital.sgearmetalworks.datagen;

import com.mojang.datafixers.util.Pair;
import cy.jdkdigital.productivelib.crafting.condition.FluidTagEmptyCondition;
import cy.jdkdigital.productivemetalworks.ProductiveMetalworks;
import cy.jdkdigital.productivemetalworks.datagen.recipe.FluidAlloyingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.datagen.recipe.ItemCastingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.datagen.recipe.ItemMeltingRecipeBuilder;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.datagen.recipe.SilentGearCastingRecipeBuilder;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.util.CastingMaterialCategories;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.TagFluidIngredient;
import net.neoforged.neoforge.registries.DeferredItem;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.crafting.ingredient.BlueprintIngredient;
import net.silentchaos512.gear.crafting.ingredient.GearPartIngredient;
import net.silentchaos512.gear.crafting.ingredient.PartMaterialIngredient;
import net.silentchaos512.gear.crafting.recipe.ShapelessCompoundPartRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapelessGearRecipe;
import net.silentchaos512.gear.gear.material.MaterialCategories;
import net.silentchaos512.gear.item.GearItemSet;
import net.silentchaos512.gear.item.MainPartItem;
import net.silentchaos512.gear.item.gear.GearArmorItem;
import net.silentchaos512.gear.setup.GearItemSets;
import net.silentchaos512.gear.setup.SgItems;
import net.silentchaos512.gear.setup.gear.GearTypes;
import net.silentchaos512.gear.setup.gear.PartTypes;
import net.silentchaos512.lib.data.recipe.ExtendedShapelessRecipeBuilder;
import net.silentchaos512.lib.util.NameUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder
{
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

        cy.jdkdigital.productivemetalworks.datagen.RecipeProvider.metalCompat(SGearMetalworks.MODID, SG_METALS, recipeOutput);
        cy.jdkdigital.productivemetalworks.datagen.RecipeProvider.modMetalCompat("silentgear", SG_METALS, recipeOutput);

        gearCasting(recipeOutput);
        gearCrafting(recipeOutput);
    }

    private static void gearCasting(RecipeOutput recipeOutput) {
        var compatRecipeOutput = recipeOutput.withConditions(new ModLoadedCondition("silentgear"));

        Map<String, Pair<PartMaterialIngredient, Integer>> materialCost = new HashMap<>() {{
            put("sword", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 2));
            put("katana", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("machete", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("spear", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 1));
            put("trident", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("mace", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("knife", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 1));
            put("dagger", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 1));
            put("pickaxe", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("shovel", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 1));
            put("axe", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("paxel", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 5));
            put("hammer", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 6));
            put("excavator", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 5));
            put("hoe", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 2));
            put("mattock", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 4));
            put("prospector_hammer", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 2));
            put("saw", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 5));
            put("sickle", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("shears", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 2));
            put("fishing_rod", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 2));
            put("bow", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("crossbow", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 3));
            put("slingshot", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get(), CastingMaterialCategories.CASTING), 2));
            put("arrow", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.PROJECTILE.get(), CastingMaterialCategories.CASTING), 1));

            put("ring", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), CastingMaterialCategories.CASTING), 2));
            put("bracelet", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), CastingMaterialCategories.CASTING), 3));
            put("necklace", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), CastingMaterialCategories.CASTING), 3));
            put("helmet", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.HELMET.get(), CastingMaterialCategories.CASTING), 5));
            put("chestplate", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CHESTPLATE.get(), CastingMaterialCategories.CASTING), 8));
            put("leggings", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.LEGGINGS.get(), CastingMaterialCategories.CASTING), 7));
            put("boots", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.BOOTS.get(), CastingMaterialCategories.CASTING), 4));
            put("shield", Pair.of(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.ARMOR.get(), CastingMaterialCategories.CASTING), 2));
        }};

        // Iterate blueprints and use them as the cast
        GearItemSets.getIterator().forEachRemaining(gearItemSet -> {
            var cast = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, gearItemSet.name() + "_cast")).getDefaultInstance();
            ProductiveMetalworks.LOGGER.info("creating recipes for " + gearItemSet.name() + "_cast " + cast);
            // blueprint recipe
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
        });
        SilentGearCastingRecipeBuilder.of(SGearMetalworksRegistrator.CAST_TOOL_ROD.get().getDefaultInstance(), PartMaterialIngredient.of(PartTypes.ROD.get(), CastingMaterialCategories.CASTING), 2, SgItems.ROD.get().getDefaultInstance(), false)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/tool_rod"));
        SilentGearCastingRecipeBuilder.of(SGearMetalworksRegistrator.CAST_TIP.get().getDefaultInstance(), PartMaterialIngredient.of(PartTypes.TIP.get(), CastingMaterialCategories.CASTING), 2, SgItems.TIP.get().getDefaultInstance(), false)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/silentgear/tip"));

        ItemCastingRecipeBuilder.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("silentgear", "rod")).getDefaultInstance(), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_STEEL, 360), SGearMetalworksRegistrator.CAST_TOOL_ROD.get().getDefaultInstance(), true)
                .save(compatRecipeOutput, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casting/rod_cast"));
        ItemCastingRecipeBuilder.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("silentgear", "tip")).getDefaultInstance(), SizedFluidIngredient.of(ModTags.Fluids.MOLTEN_STEEL, 360), SGearMetalworksRegistrator.CAST_TIP.get().getDefaultInstance(), true)
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

        shapelessGear(RecipeCategory.COMBAT, GearItemSets.SHIELD.gearItem())
                .requires(BlueprintIngredient.of(GearItemSets.SHIELD))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.ARMOR.get()), 2)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .save(recipeOutput, SilentGear.getId("gear/shield"));

        armorRecipes(recipeOutput, 5, GearItemSets.HELMET);
        armorRecipes(recipeOutput, 8, GearItemSets.CHESTPLATE);
        armorRecipes(recipeOutput, 7, GearItemSets.LEGGINGS);
        armorRecipes(recipeOutput, 4, GearItemSets.BOOTS);

        // Disable conversion recipes
        for (String material: new String[]{"iron", "golden", "diamond", "netherite"}) {
            for (String gear: new String[]{"axe", "boots", "chestplate", "helmet", "hoe", "leggings", "pickaxe", "shoves", "sword"}) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STICK).requires(Items.STICK).unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .save(recipeOutput.withConditions(new NotCondition(new ModLoadedCondition(SGearMetalworks.MODID))), ResourceLocation.fromNamespaceAndPath("silentgear", "gear/convert/" + material + "_" + gear));
            }
        }
    }

    private static ExtendedShapelessRecipeBuilder.Basic<ShapelessCompoundPartRecipe> compoundPart(DeferredItem<?> item, int count) {
        return new ExtendedShapelessRecipeBuilder.Basic<>(RecipeCategory.MISC, item.toStack(count), ShapelessCompoundPartRecipe::new);
    }

    private static void registerCompoundParts(RecipeOutput consumer) {
        compoundPart(SgItems.ROD, 4)
                .requires(BlueprintIngredient.of(SgItems.ROD_BLUEPRINT.get()))
                .requires(PartMaterialIngredient.of(PartTypes.ROD.get()).not(CastingMaterialCategories.CASTING), 2)
                .save(consumer, SilentGear.getId("part/rod"));

        compoundPart(SgItems.TIP, 1)
                .requires(BlueprintIngredient.of(SgItems.TIP_BLUEPRINT.get()))
                .requires(PartMaterialIngredient.of(PartTypes.TIP.get()).not(CastingMaterialCategories.CASTING))
                .save(consumer, SilentGear.getId("part/tip"));
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
    }

    private static void maceRecipes(RecipeOutput output) {
        var itemSet = GearItemSets.MACE;
        shapelessPart(RecipeCategory.TOOLS, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Items.HEAVY_CORE)
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING), 3)
                .save(output, SilentGear.getId("gear/mace_core"));
        shapelessGear(RecipeCategory.TOOLS, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(Items.HEAVY_CORE)
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING), 3)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .save(output, SilentGear.getId("gear/mace_quick"));
    }

    private static void bowRecipes(RecipeOutput consumer, String name, int mainCount, GearItemSet<?> itemSet) {
        // Main part
        shapelessPart(RecipeCategory.COMBAT, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING), mainCount)
                .save(consumer, SilentGear.getId("gear/" + name + "_main"));
        // Quick tool (main materials, rod, and cord, skipping main part)
        shapelessGear(RecipeCategory.COMBAT, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING), mainCount)
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .requires(GearPartIngredient.of(PartTypes.CORD.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    private static void arrowRecipes(RecipeOutput consumer, String name, GearItemSet<?> itemSet) {
        BlueprintIngredient blueprint = BlueprintIngredient.of(itemSet);
        // Arrow head
        shapelessPart(RecipeCategory.COMBAT, itemSet.mainPart())
                .requires(blueprint)
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.PROJECTILE.get()).not(CastingMaterialCategories.CASTING))
                .save(consumer, SilentGear.getId("gear/" + name + "_head"));
        // Quick arrows
        shapelessGear(RecipeCategory.COMBAT, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.TOOL.get()).not(CastingMaterialCategories.CASTING))
                .requires(GearPartIngredient.of(PartTypes.ROD.get()))
                .requires(GearPartIngredient.of(PartTypes.FLETCHING.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    private static void armorRecipes(RecipeOutput consumer, int mainCount, GearItemSet<? extends GearArmorItem> itemSet) {
        shapelessPart(RecipeCategory.COMBAT, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), itemSet.gearItem().getGearType()).not(CastingMaterialCategories.CASTING), mainCount)
                .save(consumer, SilentGear.getId("gear/" + NameUtils.fromItem(itemSet.mainPart()).getPath()));
    }

    private static void curioRecipes(RecipeOutput consumer, String name, int mainCount, GearItemSet<?> itemSet) {
        shapelessPart(RecipeCategory.MISC, itemSet.mainPart())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), MaterialCategories.METAL).not(CastingMaterialCategories.CASTING), mainCount)
                .save(consumer, SilentGear.getId("gear/" + name + "_main_only"));

        shapelessGear(RecipeCategory.MISC, itemSet.gearItem())
                .requires(BlueprintIngredient.of(itemSet))
                .requires(PartMaterialIngredient.of(PartTypes.MAIN.get(), GearTypes.CURIO.get(), MaterialCategories.METAL).not(CastingMaterialCategories.CASTING), mainCount)
                .requires(GearPartIngredient.of(PartTypes.SETTING.get()))
                .save(consumer, SilentGear.getId("gear/" + name + "_quick"));
    }

    private static ExtendedShapelessRecipeBuilder.Basic<ShapelessCompoundPartRecipe> shapelessPart(RecipeCategory category, ItemLike item) {
        return new ExtendedShapelessRecipeBuilder.Basic<>(category, new ItemStack(item), ShapelessCompoundPartRecipe::new);
    }
    private static ExtendedShapelessRecipeBuilder.Basic<ShapelessGearRecipe> shapelessGear(RecipeCategory category, ItemLike item) {
        return new ExtendedShapelessRecipeBuilder.Basic<>(category, new ItemStack(item), ShapelessGearRecipe::new);
    }

    private static void alloyRecipe(List<SizedFluidIngredient> inputs, int speed, FluidStack output, RecipeOutput recipeOutput) {
        var conditionalRecipeOutput = recipeOutput;
        for (SizedFluidIngredient sizedFluidIngredient : inputs) {
            if (sizedFluidIngredient.ingredient() instanceof TagFluidIngredient tagFluidIngredient) {
                conditionalRecipeOutput = conditionalRecipeOutput.withConditions(new NotCondition(new FluidTagEmptyCondition(tagFluidIngredient.tag())));
            }
        }
        FluidAlloyingRecipeBuilder.of(inputs, speed, output).save(conditionalRecipeOutput, ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "alloying/" + BuiltInRegistries.FLUID.getKey(output.getFluid()).getPath()));
    }
}
