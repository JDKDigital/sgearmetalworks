package cy.jdkdigital.sgearmetalworks.datagen;

import cy.jdkdigital.productivemetalworks.common.datamap.UnitMap;
import cy.jdkdigital.productivemetalworks.registry.MetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.silentchaos512.gems.util.Gems;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class DataMapProvider extends net.neoforged.neoforge.common.data.DataMapProvider
{
    protected DataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        final var units = builder(MetalworksRegistrator.UNIT_MAP);

        SGearMetalworks.FLUIDS.getEntries().forEach(fluidHolder -> {
            if (fluidHolder.get().defaultFluidState().isSource()) {
                if (EnumUtils.isValidEnum(Gems.class, fluidHolder.getId().getPath().replace("molten_", "").toUpperCase(Locale.ROOT))) {
                    units.add(fluidHolder, new UnitMap(List.of(new UnitMap.Unit(100, "gem"), new UnitMap.Unit(900, "block"))), false);
                } else {
                    units.add(fluidHolder, new UnitMap(List.of(new UnitMap.Unit(10, "nugget"), new UnitMap.Unit(90, "ingot"), new UnitMap.Unit(810, "block"))), false);
                }
            }
        });
    }
}
