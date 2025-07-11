package cy.jdkdigital.sgearmetalworks.datagen;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import cy.jdkdigital.productivemetalworks.ProductiveMetalworks;
import cy.jdkdigital.productivemetalworks.common.block.HotLiquidBlock;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockModelProvider implements DataProvider
{
    protected final PackOutput packOutput;

    protected final Map<ResourceLocation, Supplier<JsonElement>> models = new HashMap<>();

    public BlockModelProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<Block, BlockStateGenerator> blockModels = Maps.newHashMap();
        Consumer<BlockStateGenerator> blockStateOutput = (blockStateGenerator) -> {
            Block block = blockStateGenerator.getBlock();
            BlockStateGenerator blockstategenerator = blockModels.put(block, blockStateGenerator);
            if (blockstategenerator != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + block);
            }
        };
        Map<ResourceLocation, Supplier<JsonElement>> itemModels = Maps.newHashMap();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput = (resourceLocation, elementSupplier) -> {
            Supplier<JsonElement> supplier = itemModels.put(resourceLocation, elementSupplier);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + resourceLocation);
            }
        };

        ModelGenerator generator = new ModelGenerator();
        try {
            generator.registerStatesAndModels(blockStateOutput, modelOutput);
        } catch (Exception e) {
            ProductiveMetalworks.LOGGER.error("Error registering states and models", e);
        }

        addBlockItemParentModel(SGearMetalworksRegistrator.URU_METAL_BLOCK.get(), "", "", itemModels);

        PackOutput.PathProvider blockstatePathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        PackOutput.PathProvider modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");

        List<CompletableFuture<?>> output = new ArrayList<>();
        blockModels.forEach((block, supplier) -> {
            output.add(DataProvider.saveStable(cache, supplier.get(), blockstatePathProvider.json(BuiltInRegistries.BLOCK.getKey(block))));
        });
        itemModels.forEach((rLoc, supplier) -> {
            output.add(DataProvider.saveStable(cache, supplier.get(), modelPathProvider.json(rLoc)));
        });

        return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
    }

    private void generateFlatItem(Item item, String prefix, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), getFlatItemTextureMap(item, prefix), modelOutput);
    }

    private static TextureMapping getFlatItemTextureMap(Item item, String prefix) {
        return getFlatItemTextureMap(item, prefix, "");
    }

    private static TextureMapping getFlatItemTextureMap(Item item, String prefix, String suffix) {
        return getFlatItemTextureMap(BuiltInRegistries.ITEM.getKey(item), prefix, suffix);
    }

    private static TextureMapping getFlatItemTextureMap(ResourceLocation resourceLocation, String prefix, String suffix) {
        return (new TextureMapping()).put(TextureSlot.LAYER0, resourceLocation.withPrefix(prefix).withSuffix(suffix));
    }

    private void addItemModel(Item item, Supplier<JsonElement> supplier, Map<ResourceLocation, Supplier<JsonElement>> itemModels) {
        if (item != null) {
            ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
            if (!itemModels.containsKey(resourcelocation)) {
                itemModels.put(resourcelocation, supplier);
            }
        }
    }

    private void addBlockItemParentModel(Block block, String prefix, String suffix, Map<ResourceLocation, Supplier<JsonElement>> itemModels) {
        Item item = Item.BY_BLOCK.get(block);
        if (item != null) {
            var rl = BuiltInRegistries.BLOCK.getKey(block);
            addItemParentModel(item, rl, "block/" + prefix, suffix, itemModels);
        }
    }

    private void addItemParentModel(Item item, ResourceLocation rl, String prefix, String suffix, Map<ResourceLocation, Supplier<JsonElement>> itemModels) {
        addItemModel(item, new DelegatedModel(ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), prefix + rl.getPath() + suffix)), itemModels);
    }

    @Override
    public String getName() {
        return "Sgear Metalworks Blockstate and Model generator";
    }

    static class ModelGenerator
    {
        Consumer<BlockStateGenerator> blockStateOutput;
        BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

        protected void registerStatesAndModels(Consumer<BlockStateGenerator> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput) {
            this.blockStateOutput = blockStateOutput;
            this.modelOutput = modelOutput;

            this.blockStateOutput.accept(createFullBlock(SGearMetalworksRegistrator.URU_METAL_BLOCK.get()));

            SGearMetalworks.BLOCKS.getEntries().stream().filter(h -> h.get() instanceof HotLiquidBlock).forEach(block -> {
                this.blockStateOutput.accept(createSimpleBlock(block.get(), ModelTemplates.CUBE_ALL.create(block.get(), TextureMapping.cube(ResourceLocation.fromNamespaceAndPath(ProductiveMetalworks.MODID, "block/fluid/molten_metal")), this.modelOutput)));
            });
        }


        MultiVariantGenerator createFullBlock(Block block) {
            TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, TextureMapping.getBlockTexture(block));
            ResourceLocation model = ModelTemplates.CUBE_ALL.create(block, mapping, this.modelOutput);
            return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model));
        }

        MultiVariantGenerator createSimpleBlock(Block block, ResourceLocation modelLocation) {
            return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, modelLocation));
        }
    }
}
