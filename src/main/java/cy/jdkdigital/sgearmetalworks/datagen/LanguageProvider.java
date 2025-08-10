package cy.jdkdigital.sgearmetalworks.datagen;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider
{
    public LanguageProvider(PackOutput output, String locale) {
        super(output, SGearMetalworks.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("jei." + SGearMetalworks.MODID + ".sg_casting", "Silent Gear Casting");
        add("material.group.silentgear.casting", "Casting");
        add("material.group.silentgear.infusing", "Infusing");
        add("material.group.silentgear.crude", "Crude");
        add("material." + SGearMetalworks.MODID + ".meat", "Meat");
        add("material." + SGearMetalworks.MODID + ".uru_metal", "Uru Metal");
        add("trait.silentgear.sting_resist", "Sting resist");
        add("trait.silentgear.sting_resist.desc", "Bee stings no longer sting");
        add("tooltip." + SGearMetalworks.MODID + ".blueprint", "All metal and gem tool parts require casting in a foundry from Productive Metalworks");
        add("entity.productivebees.uru_metal_bee", "Uru Metal Bee");
        add("productivebees.ingredient.description.uru_metal_bee", "To acquire this bee, look up the crafting recipe for its spawn egg.");

        SGearMetalworks.ITEMS.getEntries().forEach(registryObject -> {
            if (registryObject.get() instanceof BucketItem) {
                add(registryObject.get(), "Bucket of " + capName(BuiltInRegistries.ITEM.getKey(registryObject.get()).getPath().replace("_bucket", "")));
            } else if (!(registryObject.get() instanceof BlockItem)) {
                add(registryObject.get(), capName(BuiltInRegistries.ITEM.getKey(registryObject.get()).getPath()));
            }
        });
        SGearMetalworks.BLOCKS.getEntries().forEach(registryObject -> {
            add(registryObject.get(), capName(BuiltInRegistries.BLOCK.getKey(registryObject.get()).getPath()));
        });
        SGearMetalworks.FLUID_TYPES.getEntries().forEach(registryObject -> {
            add(registryObject.get().getDescriptionId(), capName(NeoForgeRegistries.FLUID_TYPES.getKey(registryObject.get()).getPath()));
        });
    }

    @Override
    public String getName() {
        return "SGear Metalworks translation provider";
    }

    private String capName(String name) {
        String[] nameParts = name.split("_");

        for (int i = 0; i < nameParts.length; i++) {
            nameParts[i] = nameParts[i].substring(0, 1).toUpperCase() + nameParts[i].substring(1);
        }

        return String.join(" ", nameParts);
    }
}
