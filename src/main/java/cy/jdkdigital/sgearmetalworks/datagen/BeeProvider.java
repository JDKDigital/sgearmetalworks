package cy.jdkdigital.sgearmetalworks.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BeeProvider extends cy.jdkdigital.productivebees.datagen.BeeProvider
{
    public BeeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output);
    }

    @Override
    public @NotNull String getName() {
        return "Sgear Metaworks bee data provider";
    }

    @Override
    protected List<BeeConfig> getBeeConfigs() {
        return new ArrayList<>()
        {{
            add(new BeeConfig("uru_metal").primaryColor("#0b2037").secondaryColor("#064f2c").renderer("default").beeTexture("uru_metal").flowerTag("c:storage_blocks/uru_metal").size(1.1));
        }};
    }
}
