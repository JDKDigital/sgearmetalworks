package cy.jdkdigital.sgearmetalworks.registry;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags
{

    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS = BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks"));
        public static final TagKey<Block> URU_METAL_STORAGE_BLOCKS = BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/uru_metal"));
    }

    public static class Items
    {
        public static final TagKey<Item> STORAGE_BLOCKS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks"));
        public static final TagKey<Item> INGOTS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots"));
        public static final TagKey<Item> NUGGETS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "nuggets"));
        public static final TagKey<Item> URU_METAL_STORAGE_BLOCKS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/uru_metal"));
        public static final TagKey<Item> URU_METAL_INGOTS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/uru_metal"));
        public static final TagKey<Item> URU_METAL_NUGGETS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "nuggets/uru_metal"));

        public static final TagKey<Item> CASTS = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "casts"));
        public static final TagKey<Item> SG_CASTS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, "casts"));
        public static final TagKey<Item> INGOTS_MEAT = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/meat"));
        public static final TagKey<Item> BLUEPRINT_OVERRIDE = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "blueprint_override"));
    }

    public static class Fluids
    {
        public static final TagKey<Fluid> MOLTEN_CRIMSON_IRON = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_crimson_iron"));
        public static final TagKey<Fluid> MOLTEN_CRIMSON_STEEL = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_crimson_steel"));
        public static final TagKey<Fluid> MOLTEN_BLAZE_GOLD = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_blaze_gold"));
        public static final TagKey<Fluid> MOLTEN_AZURE_SILVER = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_azure_silver"));
        public static final TagKey<Fluid> MOLTEN_AZURE_ELECTRUM = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_azure_electrum"));
        public static final TagKey<Fluid> MOLTEN_TYRIAN_STEEL = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_tyrian_steel"));
        public static final TagKey<Fluid> MOLTEN_URU_METAL = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_uru_metal"));

        public static final TagKey<Fluid> MOLTEN_HEAVY_CORE = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_heavy_core"));
        public static final TagKey<Fluid> MOLTEN_GOLD = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_gold"));
        public static final TagKey<Fluid> MOLTEN_STEEL = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_steel"));
        public static final TagKey<Fluid> MOLTEN_ENDER = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_ender"));
        public static final TagKey<Fluid> MOLTEN_ANCIENT_DEBRIS = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_ancient_debris"));
        public static final TagKey<Fluid> MOLTEN_SHULKER_SHELL = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_shulker_shell"));
        public static final TagKey<Fluid> MOLTEN_BLAZE = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_blaze"));
        public static final TagKey<Fluid> MOLTEN_MAGMA_CREAM = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "molten_magma_cream"));
        public static final TagKey<Fluid> MEAT = FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "meat"));
    }
}
