package cy.jdkdigital.sgearmetalworks.datagen;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.setup.SgItems;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider
{
    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, SGearMetalworks.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.CASTS).addTag(ModTags.Items.SG_CASTS);
        tag(ModTags.Items.SG_CASTS).add(
                SGearMetalworksRegistrator.CAST_SWORD.get(),
                SGearMetalworksRegistrator.CAST_KATANA.get(),
                SGearMetalworksRegistrator.CAST_MACHETE.get(),
                SGearMetalworksRegistrator.CAST_SPEAR.get(),
                SGearMetalworksRegistrator.CAST_TRIDENT.get(),
                SGearMetalworksRegistrator.CAST_KNIFE.get(),
                SGearMetalworksRegistrator.CAST_DAGGER.get(),
                SGearMetalworksRegistrator.CAST_PICKAXE.get(),
                SGearMetalworksRegistrator.CAST_SHOVEL.get(),
                SGearMetalworksRegistrator.CAST_AXE.get(),
                SGearMetalworksRegistrator.CAST_PAXEL.get(),
                SGearMetalworksRegistrator.CAST_HAMMER.get(),
                SGearMetalworksRegistrator.CAST_EXCAVATOR.get(),
                SGearMetalworksRegistrator.CAST_HOE.get(),
                SGearMetalworksRegistrator.CAST_MATTOCK.get(),
                SGearMetalworksRegistrator.CAST_PROSPECTOR_HAMMER.get(),
                SGearMetalworksRegistrator.CAST_SAW.get(),
                SGearMetalworksRegistrator.CAST_SICKLE.get(),
                SGearMetalworksRegistrator.CAST_SHEARS.get(),
                SGearMetalworksRegistrator.CAST_FISHING_ROD.get(),
                SGearMetalworksRegistrator.CAST_BOW.get(),
                SGearMetalworksRegistrator.CAST_CROSSBOW.get(),
                SGearMetalworksRegistrator.CAST_SLINGSHOT.get(),
                SGearMetalworksRegistrator.CAST_ARROW.get(),
                SGearMetalworksRegistrator.CAST_RING.get(),
                SGearMetalworksRegistrator.CAST_BRACELET.get(),
                SGearMetalworksRegistrator.CAST_NECKLACE.get(),
                SGearMetalworksRegistrator.CAST_HELMET.get(),
                SGearMetalworksRegistrator.CAST_CHESTPLATE.get(),
                SGearMetalworksRegistrator.CAST_LEGGINGS.get(),
                SGearMetalworksRegistrator.CAST_BOOTS.get(),
                SGearMetalworksRegistrator.CAST_SHIELD.get(),
                SGearMetalworksRegistrator.CAST_TOOL_ROD.get(),
                SGearMetalworksRegistrator.CAST_TIP.get()
        );

        for(String s: new String[]{"rod", "pickaxe", "axe", "shovel", "sword", "sickle", "saw", "knife", "katana", "hoe", "bow", "crossbow", "shield", "helmet", "chestplate", "leggings", "boots", "bracelet", "necklace", "ring", "shears", "tip", "arrow", "machete", "trident", "spear", "dagger", "fishing_rod", "mattock", "slingshot", "mace", "hammer", "paxel", "excavator", "prospector_hammer"}) {
            tag(ModTags.Items.BLUEPRINT_OVERRIDE).add(BuiltInRegistries.ITEM.get(SilentGear.getId(s + "_blueprint")));
        }
    }

    @Override
    public String getName() {
        return "SGear Metalworks Item Tags Provider";
    }
}
