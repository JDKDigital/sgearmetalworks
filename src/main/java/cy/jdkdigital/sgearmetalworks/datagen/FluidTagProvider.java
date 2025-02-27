package cy.jdkdigital.sgearmetalworks.datagen;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class FluidTagProvider extends FluidTagsProvider
{
    public FluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, future, SGearMetalworks.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Fluids.MOLTEN_CRIMSON_IRON).add(SGearMetalworksRegistrator.MOLTEN_CRIMSON_IRON.get());
        tag(ModTags.Fluids.MOLTEN_CRIMSON_STEEL).add(SGearMetalworksRegistrator.MOLTEN_CRIMSON_STEEL.get());
        tag(ModTags.Fluids.MOLTEN_BLAZE_GOLD).add(SGearMetalworksRegistrator.MOLTEN_BLAZE_GOLD.get());
        tag(ModTags.Fluids.MOLTEN_AZURE_SILVER).add(SGearMetalworksRegistrator.MOLTEN_AZURE_SILVER.get());
        tag(ModTags.Fluids.MOLTEN_AZURE_ELECTRUM ).add(SGearMetalworksRegistrator.MOLTEN_AZURE_ELECTRUM.get());
        tag(ModTags.Fluids.MOLTEN_TYRIAN_STEEL).add(SGearMetalworksRegistrator.MOLTEN_TYRIAN_STEEL.get());
    }

    @Override
    public String getName() {
        return "SGear Metalworks Fluid Tags Provider";
    }
}
