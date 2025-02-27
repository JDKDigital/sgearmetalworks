package cy.jdkdigital.sgearmetalworks.datagen;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider
{
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SGearMetalworks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        SGearMetalworks.ITEMS.getEntries().forEach(itemHolder -> {
            if (itemHolder.getId().getPath().contains("_bucket")) {
                withExistingParent(itemHolder.getId().getPath(), ResourceLocation.fromNamespaceAndPath(NeoForgeVersion.MOD_ID, "item/bucket_drip"))
                        .customLoader(DynamicFluidContainerModelBuilder::begin)
                        .fluid(BuiltInRegistries.FLUID.get(itemHolder.getId().withPath(p -> p.replace("_bucket", ""))));
            }
        });

//        basicItem(SGearMetalworksRegistrator.CAST_INGOT.get());
    }
}
