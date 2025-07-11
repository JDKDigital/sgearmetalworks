package cy.jdkdigital.sgearmetalworks.registry;

import cy.jdkdigital.productivemetalworks.common.block.HotLiquidBlock;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.datagen.recipe.GearComponentIngredient;
import cy.jdkdigital.sgearmetalworks.recipe.SilentGearCastingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SGearMetalworksRegistrator
{
    public static void register() {}

    public static Map<String, Integer> FLUID_COLORS = new HashMap<>();

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SG_GEAR_CASTING = SGearMetalworks.RECIPE_SERIALIZERS.register("sg_gear_casting", SilentGearCastingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<SilentGearCastingRecipe>> SG_GEAR_CASTING_TYPE = SGearMetalworks.RECIPE_TYPES.register("sg_gear_casting", () -> new RecipeType<>() {});
    public static final DeferredHolder<IngredientType<?>, IngredientType<GearComponentIngredient>> GEAR_INGREDIENT_TYPE = SGearMetalworks.INGREDIENT_TYPES.register("component", () -> new IngredientType<>(GearComponentIngredient.CODEC));

    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_CRIMSON_IRON = registerFluid("molten_crimson_iron", 0xfff0466e);
    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_CRIMSON_STEEL = registerFluid("molten_crimson_steel", 0xffd9143b);
    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_BLAZE_GOLD = registerFluid("molten_blaze_gold", 0xffd94708);
    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_AZURE_SILVER = registerFluid("molten_azure_silver", 0xffcfa0f3);
    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_AZURE_ELECTRUM = registerFluid("molten_azure_electrum", 0xff0c21dd);
    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_TYRIAN_STEEL = registerFluid("molten_tyrian_steel", 0xffae107e);
    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> MOLTEN_URU_METAL = registerFluid("molten_uru_metal", 0xff0b2037);

    // Uru metal
    public static DeferredHolder<Block, Block> URU_METAL_BLOCK = registerBlock("uru_metal_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)), true);
    public static DeferredHolder<Item, Item> URU_METAL_INGOT = registerItem("uru_metal_ingot");
    public static DeferredHolder<Item, Item> URU_METAL_NUGGET = registerItem("uru_metal_nugget");


    public static DeferredHolder<Item, Item> CAST_SWORD = registerItem("sword_cast");
    public static DeferredHolder<Item, Item> CAST_KATANA = registerItem("katana_cast");
    public static DeferredHolder<Item, Item> CAST_MACHETE = registerItem("machete_cast");
    public static DeferredHolder<Item, Item> CAST_SPEAR = registerItem("spear_cast");
    public static DeferredHolder<Item, Item> CAST_TRIDENT = registerItem("trident_cast");
    public static DeferredHolder<Item, Item> CAST_KNIFE = registerItem("knife_cast");
    public static DeferredHolder<Item, Item> CAST_DAGGER = registerItem("dagger_cast");
    public static DeferredHolder<Item, Item> CAST_PICKAXE = registerItem("pickaxe_cast");
    public static DeferredHolder<Item, Item> CAST_SHOVEL = registerItem("shovel_cast");
    public static DeferredHolder<Item, Item> CAST_AXE = registerItem("axe_cast");
    public static DeferredHolder<Item, Item> CAST_PAXEL = registerItem("paxel_cast");
    public static DeferredHolder<Item, Item> CAST_HAMMER = registerItem("hammer_cast");
    public static DeferredHolder<Item, Item> CAST_EXCAVATOR = registerItem("excavator_cast");
    public static DeferredHolder<Item, Item> CAST_HOE = registerItem("hoe_cast");
    public static DeferredHolder<Item, Item> CAST_MATTOCK = registerItem("mattock_cast");
    public static DeferredHolder<Item, Item> CAST_PROSPECTOR_HAMMER = registerItem("prospector_hammer_cast");
    public static DeferredHolder<Item, Item> CAST_SAW = registerItem("saw_cast");
    public static DeferredHolder<Item, Item> CAST_SICKLE = registerItem("sickle_cast");
    public static DeferredHolder<Item, Item> CAST_SHEARS = registerItem("shears_cast");
    public static DeferredHolder<Item, Item> CAST_FISHING_ROD = registerItem("fishing_rod_cast");
    public static DeferredHolder<Item, Item> CAST_BOW = registerItem("bow_cast");
    public static DeferredHolder<Item, Item> CAST_CROSSBOW = registerItem("crossbow_cast");
    public static DeferredHolder<Item, Item> CAST_SLINGSHOT = registerItem("slingshot_cast");
    public static DeferredHolder<Item, Item> CAST_ARROW = registerItem("arrow_cast");
    public static DeferredHolder<Item, Item> CAST_RING = registerItem("ring_cast");
    public static DeferredHolder<Item, Item> CAST_BRACELET = registerItem("bracelet_cast");
    public static DeferredHolder<Item, Item> CAST_NECKLACE = registerItem("necklace_cast");
    public static DeferredHolder<Item, Item> CAST_HELMET = registerItem("helmet_cast");
    public static DeferredHolder<Item, Item> CAST_CHESTPLATE = registerItem("chestplate_cast");
    public static DeferredHolder<Item, Item> CAST_LEGGINGS = registerItem("leggings_cast");
    public static DeferredHolder<Item, Item> CAST_BOOTS = registerItem("boots_cast");
    public static DeferredHolder<Item, Item> CAST_SHIELD = registerItem("shield_cast");
    public static DeferredHolder<Item, Item> CAST_TOOL_ROD = registerItem("tool_rod_cast");
    public static DeferredHolder<Item, Item> CAST_TIP = registerItem("tip_cast");

    public static DeferredHolder<Item, Item> registerItem(String name) {
        return registerItem(name, () -> new Item(new Item.Properties()));
    }

    public static DeferredHolder<Item, Item> registerItem(String name, Supplier<Item> supplier) {
        return SGearMetalworks.ITEMS.register(name, supplier);
    }

    public static DeferredHolder<Block, Block> registerBlock(String name, Supplier<Block> supplier, boolean hasItem) {
        return registerBlock(name, supplier, hasItem ? new Item.Properties() : null);
    }

    public static DeferredHolder<Block, Block> registerBlock(String name, Supplier<Block> supplier, Item.Properties properties) {
        var block = SGearMetalworks.BLOCKS.register(name, supplier);
        if (properties != null) {
            registerItem(name, () -> new BlockItem(block.get(), properties));
        }
        return block;
    }

    public static DeferredHolder<Fluid, BaseFlowingFluid.Source> registerFluid(String name, int color) {
        FLUID_COLORS.put(name, color);
        // fluid type
        var TYPE = SGearMetalworks.FLUID_TYPES.register(name, () -> new FluidType(MetalworksRegistrator.MOLTEN_FLUID_TYPE_PROPERTIES));
        // fluid
        var MOLTEN = SGearMetalworks.FLUIDS.register(name, () -> new BaseFlowingFluid.Source(makeMoltenProperties(TYPE, name)));
        // flowing fluid
        SGearMetalworks.FLUIDS.register(String.format("flowing_%s", name), () -> new BaseFlowingFluid.Flowing(makeMoltenProperties(TYPE, name)));
        // fluid bucket
        registerItem(String.format("%s_bucket", name), () -> new BucketItem(MOLTEN.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        // fluid block
        registerBlock(name, () -> new HotLiquidBlock(MOLTEN.get(), Block.Properties.of()
                .strength(100.0F)
                .speedFactor(0.7F)
                .noCollission()
                .liquid()
                .replaceable()
        ), false);

        return MOLTEN;
    }

    static private BaseFlowingFluid.Properties makeMoltenProperties(Supplier<? extends FluidType> fluidType, String name) {
        return new BaseFlowingFluid.Properties(
                fluidType,
                DeferredHolder.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, name)),
                DeferredHolder.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, String.format("flowing_%s", name)))
        )
                .bucket(DeferredHolder.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, String.format("%s_bucket", name))))
                .block(DeferredHolder.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, name)))
                .tickRate(30)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(2);
    }
}
