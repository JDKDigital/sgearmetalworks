package cy.jdkdigital.sgearmetalworks.datagen;

import com.google.common.collect.Lists;
import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import cy.jdkdigital.sgearmetalworks.util.CastingMaterialCategories;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.data.material.MaterialBuilder;
import net.silentchaos512.gear.api.data.material.MaterialsProviderBase;
import net.silentchaos512.gear.api.data.trait.TraitBuilder;
import net.silentchaos512.gear.api.material.IMaterialCategory;
import net.silentchaos512.gear.api.material.MaterialCraftingData;
import net.silentchaos512.gear.api.material.TextureType;
import net.silentchaos512.gear.api.property.BooleanPropertyValue;
import net.silentchaos512.gear.api.property.HarvestTier;
import net.silentchaos512.gear.api.property.HarvestTierPropertyValue;
import net.silentchaos512.gear.api.property.NumberProperty;
import net.silentchaos512.gear.api.traits.ITraitCondition;
import net.silentchaos512.gear.api.util.DataResource;
import net.silentchaos512.gear.api.util.PartGearKey;
import net.silentchaos512.gear.core.BuiltinMaterials;
import net.silentchaos512.gear.gear.material.MaterialCategories;
import net.silentchaos512.gear.gear.material.SimpleMaterial;
import net.silentchaos512.gear.gear.trait.condition.MaterialCountTraitCondition;
import net.silentchaos512.gear.gear.trait.condition.MaterialRatioTraitCondition;
import net.silentchaos512.gear.gear.trait.condition.NotTraitCondition;
import net.silentchaos512.gear.gear.trait.condition.OrTraitCondition;
import net.silentchaos512.gear.gear.trait.effect.FireproofTraitEffect;
import net.silentchaos512.gear.gear.trait.effect.NegateDamageTraitEffect;
import net.silentchaos512.gear.item.CraftingItems;
import net.silentchaos512.gear.setup.SgTags;
import net.silentchaos512.gear.setup.gear.GearProperties;
import net.silentchaos512.gear.setup.gear.GearTypes;
import net.silentchaos512.gear.setup.gear.PartTypes;
import net.silentchaos512.gear.util.Const;
import net.silentchaos512.gear.util.TextUtil;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.setup.GemsTraits;
import net.silentchaos512.gems.util.Gems;
import net.silentchaos512.lib.util.Color;

import java.util.*;

public class MaterialsProvider extends MaterialsProviderBase
{
    public MaterialsProvider(DataGenerator generator) {
        super(generator, SGearMetalworks.MODID);
    }

    @Override
    protected Collection<MaterialBuilder<?>> getMaterials() {
        Collection<MaterialBuilder<?>> materials = new ArrayList<>();

        addModMetals(materials);
        addVanillaMetals(materials);
        addGems(materials);
        addDusts(materials);
        addExtraMetals(materials);
        addSilentGems(materials);

        // Obsidian
        materials.add(MaterialBuilder.builtin(BuiltinMaterials.OBSIDIAN)
                .crafting(Tags.Items.OBSIDIANS, MaterialCategories.ROCK, MaterialCategories.ADVANCED, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0x443464, TextureType.LOW_CONTRAST)
                //main
                .mainStatsCommon(1024, 13, 7, 10, 0.6f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, -0.25f)
                .mainStatsHarvest(6)
                .mainStatsMelee(3, 2, -0.4f)
                .mainStatsRanged(0, -0.4f, 0.7f, 0.7f)
                .mainStatsArmor(3, 8, 6, 3, 4, 8) //20
                .trait(PartTypes.MAIN, Const.Traits.JAGGED, 3)
                .trait(PartTypes.MAIN, Const.Traits.CRUSHING, 2)
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 2)
                .trait(PartTypes.ROD, Const.Traits.CHIPPING, 3)
        );

        // Uru
        materials.add(MaterialBuilder.simple(modId("uru_metal"))
                .crafting(new MaterialCraftingData(Ingredient.of(ModTags.Items.URU_METAL_INGOTS), Lists.newArrayList(MaterialCategories.METAL, MaterialCategories.ENDGAME, CastingMaterialCategories.CASTING, CastingMaterialCategories.INFUSING), Collections.emptyList(), Collections.emptyMap(), false))
                .displayWithDefaultName(0x0b2037, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(15000, 50000, 1500, 200, 1.5f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.5f)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.NETHERITE)
                .mainStatsHarvest(29)
                .mainStatsMelee(12, 11, 0.0f)
                .mainStatsRanged(8, 0.5f)
                .mainStatsProjectile(2f, 2.5f)
                .mainStatsArmor(8, 14, 12, 8, 100, 600)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 6)
                .trait(PartTypes.MAIN, Const.Traits.INDESTRUCTIBLE, 1)
                .trait(PartTypes.MAIN, Const.Traits.AQUATIC, 1)
                .trait(PartTypes.MAIN, Const.Traits.BRILLIANT, 1)
                .trait(PartTypes.MAIN, Const.Traits.FLAME_WARD, 1)
                .trait(PartTypes.MAIN, Const.Traits.CURE_WITHER, 1)
                .trait(PartTypes.MAIN, Const.Traits.CURE_POISON, 1)
                .trait(PartTypes.MAIN, SGearMetalworksRegistrator.NEGATE_STING_TRAIT, 1)
                //rod
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 3.5f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.ENCHANTMENT_VALUE, 23, NumberProperty.Operation.ADD)
                .stat(PartTypes.ROD, GearProperties.RARITY, 99, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.HARD, 5, new MaterialRatioTraitCondition(0.66f))
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 16, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 60, NumberProperty.Operation.MULTIPLY_TOTAL)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 6, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.PROJECTILE_SPEED, 0.7f, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 5)
                .trait(PartTypes.TIP, Const.Traits.HARD, 5, new MaterialRatioTraitCondition(0.66f))
                //coating
                .stat(PartTypes.COATING, GearProperties.DURABILITY, 2.5f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.ARMOR_DURABILITY, 4f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.HARVEST_TIER, new HarvestTierPropertyValue(
                        HarvestTier.create(
                                "netherite",
                                "4",
                                BlockTags.INCORRECT_FOR_NETHERITE_TOOL
                        )
                ))
                .stat(PartTypes.COATING, GearProperties.ENCHANTMENT_VALUE, 500, NumberProperty.Operation.ADD)
                .trait(PartTypes.COATING, SGearMetalworksRegistrator.NEGATE_STING_TRAIT, 1)
        );

        // Meat
        materials.add(MaterialBuilder.simple(modId("meat"))
                .crafting(ModTags.Items.INGOTS_MEAT, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xfd4e67, TextureType.LOW_CONTRAST)
                //main
                .mainStatsCommon(1024, 13, 7, 10, 0.6f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, -0.25f)
                .mainStatsHarvest(6)
                .mainStatsMelee(3, 2, -0.4f)
                .mainStatsRanged(0, -0.4f, 0.7f, 0.7f)
                .mainStatsArmor(3, 8, 6, 3, 4, 8) //20
                .trait(PartTypes.MAIN, Const.Traits.ORGANIC, 2)
                .trait(PartTypes.MAIN, Const.Traits.BENDING, 3)
                .trait(PartTypes.MAIN, Const.Traits.YUMMY, 3)
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.ORGANIC, 2)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 5)
                .trait(PartTypes.ROD, Const.Traits.YUMMY, 3)
                .trait(PartTypes.ROD, Const.Traits.BULKY, 1)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 68, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, -0.1f, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.YUMMY, 3)
                .trait(PartTypes.TIP, Const.Traits.SOFT, 3)
        );

        // Flint
        materials.add(MaterialBuilder.builtin(BuiltinMaterials.FLINT)
                .crafting(Items.FLINT, MaterialCategories.ROCK, MaterialCategories.BASIC)
                .displayWithDefaultName(0x969696, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(124, 4, 3, 6, 0.8f)
                .mainStatsHarvest(5)
                .mainStatsMelee(2, 0, -0.1f)
                .mainStatsRanged(1, -0.3f, 1.0f, 1.0f)
                .mainStatsArmor(0.5f, 2f, 1f, 0.5f, 0, 0) //4
                .trait(PartTypes.MAIN, Const.Traits.JAGGED, 3)
                //rod
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.ROD, Const.Traits.JAGGED, 2)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 31, NumberProperty.Operation.ADD)
                //setting (deliberately has no traits lol)
                .noProperties(PartTypes.SETTING)
        );

        // Bone
        materials.add(MaterialBuilder.builtin(BuiltinMaterials.BONE)
                .crafting(new MaterialCraftingData(
                        Ingredient.of(Items.BONE_BLOCK),
                        List.of(MaterialCategories.ORGANIC, MaterialCategories.BASIC, CastingMaterialCategories.CRUDE),
                        List.of(),
                        Map.of(PartTypes.ROD.get(), Ingredient.of(Items.BONE)),
                        true
                ))
                .displayWithDefaultName(0xFCFBED, TextureType.LOW_CONTRAST)
                //main
                .mainStatsCommon(108, 4, 5, 8, 0.9f)
                .mainStatsHarvest(4)
                .mainStatsMelee(2, 1, 0.1f)
                .mainStatsRanged(1f, 0f, 0.9f, 1f)
                .mainStatsArmor(1, 2, 1, 1, 0, 1) //5
                .trait(PartTypes.MAIN, Const.Traits.CHIPPING, 2)
                //rod
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 2)
        );

        return materials;
    }

    private void addModMetals(Collection<MaterialBuilder<?>> ret) {
        // Azure Electrum
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.AZURE_ELECTRUM)
                .craftingWithCommonRod(SgTags.Items.INGOTS_AZURE_ELECTRUM, MaterialCategories.METAL, MaterialCategories.ENDGAME, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0x4575E3, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(1259, 61, 37, 109, 1.5f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.5f)
                .mainStatsHarvest(29)
                .mainStatsMelee(7, 11, 0.0f)
                .mainStatsRanged(3, 0.0f)
                .mainStatsProjectile(2f, 1.5f)
                .mainStatsArmor(3, 7, 6, 3, 8, 19) //19
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.ACCELERATE, 3, new MaterialRatioTraitCondition(0.35f))
                .trait(PartTypes.MAIN, Const.Traits.LIGHT, 4, new MaterialRatioTraitCondition(0.5f))
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 3, NumberProperty.Operation.ADD)
                .stat(PartTypes.ROD, GearProperties.PROJECTILE_SPEED, 1.5f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 2)
                .trait(PartTypes.ROD, Const.Traits.ACCELERATE, 5, new MaterialRatioTraitCondition(0.66f))
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 401, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 11, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 5, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 3)
        );
        // Azure Silver
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.AZURE_SILVER)
                .craftingWithCommonRod(SgTags.Items.INGOTS_AZURE_SILVER, MaterialCategories.METAL, MaterialCategories.ADVANCED, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xCBBAFF, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(197, 17, 29, 83, 1.4f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.5f)
                .mainStatsHarvest(19)
                .mainStatsMelee(5, 7, 0.0f)
                .mainStatsRanged(2, 0.0f)
                .mainStatsProjectile(1.2f, 1.1f)
                .mainStatsArmor(2, 5, 4, 2, 0, 13) //13
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 2)
                .trait(PartTypes.MAIN, Const.Traits.MOONWALKER, 4, new MaterialRatioTraitCondition(0.5f))
                //rod
                .stat(PartTypes.ROD, GearProperties.DURABILITY, -0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 3, NumberProperty.Operation.ADD)
                .trait(PartTypes.ROD, Const.Traits.BENDING, 2)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 83, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 3, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 3, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_SPEED, 0.2f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 31, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.TIP, Const.Traits.FORTUNATE, 3)
        );
        // Blaze Gold
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.BLAZE_GOLD)
                .craftingWithCommonRod(SgTags.Items.INGOTS_BLAZE_GOLD, MaterialCategories.METAL, MaterialCategories.ADVANCED, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xDD8500)
                //main
                .mainStatsCommon(69, 9, 24, 45, 1.2f)
                .mainStatsHarvest(15)
                .mainStatsMelee(2, 5, 0.1f)
                .mainStatsRanged(1, 0.2f)
                .mainStatsProjectile(1.2f, 0.9f)
                .mainStatsArmor(2, 5, 4, 2, 1, 10) //13
                .trait(PartTypes.MAIN, Const.Traits.BRILLIANT, 1, new MaterialRatioTraitCondition(0.7f))
                .trait(PartTypes.MAIN, Const.Traits.GREEDY, 3)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 3)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 32, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 3, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 4, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 14, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.SOFT, 2)
                .trait(PartTypes.TIP, Const.Traits.FIERY, 2)
                //coating
                .stat(PartTypes.COATING, GearProperties.DURABILITY, -0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.ARMOR_DURABILITY, -0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.RARITY, 20, NumberProperty.Operation.ADD)
                .trait(PartTypes.COATING, Const.Traits.BRILLIANT, 1)
                .trait(PartTypes.COATING, Const.Traits.SOFT, 2)
        );
        // Bronze
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.BRONZE)
                .craftingWithCommonRod(SgTags.Items.INGOTS_BRONZE, MaterialCategories.METAL, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xD6903B, TextureType.HIGH_CONTRAST)
                .mainStatsCommon(300, 13, 12, 15, 1.1f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .mainStatsHarvest(6)
                .mainStatsMelee(2.5f, 1f, 0.2f)
                .mainStatsRanged(2, -0.2f)
                .mainStatsArmor(3, 6, 4, 2, 1, 6) //15
                .trait(PartTypes.MAIN, Const.Traits.SHARP, 1)
                // rod
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 1)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 96, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 2, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 0.75f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 0.75f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_SPEED, 0.1f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 6, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 2)
        );
        // Crimson Iron
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.CRIMSON_IRON)
                .craftingWithCommonRod(SgTags.Items.INGOTS_CRIMSON_IRON, MaterialCategories.METAL, MaterialCategories.ADVANCED, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xFF6189, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(420, 27, 14, 31, 0.7f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.5f)
                .mainStatsHarvest(10)
                .mainStatsMelee(3, 3, -0.1f)
                .mainStatsRanged(2, -0.1f)
                .mainStatsProjectile(1, 1.1f)
                .mainStatsArmor(3, 7, 5, 3, 2, 6) //18
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.HARD, 2)
                .trait(PartTypes.MAIN, Const.Traits.HEAT_RESISTANT, 4)
                //rod
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 3)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 224, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 8, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 10, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.FIERY, 1)
        );
        // Crimson Steel
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.CRIMSON_STEEL)
                .craftingWithCommonRod(SgTags.Items.INGOTS_CRIMSON_STEEL, MaterialCategories.METAL, MaterialCategories.ENDGAME, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xDC143C, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(2400, 42, 19, 83, 0.9f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.5f)
                .mainStatsHarvest(15)
                .mainStatsMelee(6, 6, -0.1f)
                .mainStatsRanged(3, -0.1f)
                .mainStatsProjectile(1f, 1.3f)
                .mainStatsArmor(4, 8, 6, 4, 10, 10) //22
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 5)
                .trait(PartTypes.MAIN, Const.Traits.HARD, 3)
                .trait(PartTypes.MAIN, Const.Traits.FLAME_WARD, 1, materialCountOrRatio(3, 0.33f))
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RANGED_DAMAGE, 1, NumberProperty.Operation.ADD)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 4)
                .trait(PartTypes.ROD, Const.Traits.STURDY, 1, new MaterialRatioTraitCondition(0.5f))
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 448, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 16, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.RARITY, 20, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MAGMATIC, 1)
        );
        // Tyrian Steel
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.TYRIAN_STEEL)
                .craftingWithCommonRod(SgTags.Items.INGOTS_TYRIAN_STEEL, MaterialCategories.METAL, MaterialCategories.ENDGAME, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xB01080, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(3652, 81, 16, 100, 1.1f)
                .mainStatsHarvest(18)
                .mainStatsMelee(8, 6, 0.0f)
                .mainStatsRanged(4, 0.0f)
                .mainStatsProjectile(1.1f, 1.1f)
                .mainStatsArmor(5, 9, 7, 4, 12, 12) //25
                .trait(PartTypes.MAIN, Const.Traits.STURDY, 3, new MaterialRatioTraitCondition(0.5f))
                .trait(PartTypes.MAIN, Const.Traits.VOID_WARD, 1, materialCountOrRatio(3, 0.5f))
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, -2, NumberProperty.Operation.ADD)
                .trait(PartTypes.ROD, Const.Traits.STURDY, 4, new MaterialRatioTraitCondition(0.5f))
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 251, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 30, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .trait(PartTypes.TIP, Const.Traits.IMPERIAL, 3)
                .trait(PartTypes.TIP, Const.Traits.GOLD_DIGGER, 3)
        );
    }

    private void addVanillaMetals(Collection<MaterialBuilder<?>> ret) {
        // Copper
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.COPPER)
                .craftingWithCommonRod(Tags.Items.INGOTS_COPPER, MaterialCategories.METAL, MaterialCategories.BASIC, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xFD804C, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(151, 12, 15, 12, 1.3f)
                .mainStatsHarvest(5)
                .mainStatsMelee(1.5f, 1.0f, 0.1f)
                .stat(PartGearKey.ofMain(GearTypes.AXE), GearProperties.ATTACK_SPEED, -0.1f)
                .stat(PartGearKey.ofMain(GearTypes.HOE), GearProperties.ATTACK_SPEED, 0f)
                .mainStatsRanged(0.1f, 0.0f)
                .mainStatsArmor(2, 4, 3, 1, 0, 8) //10
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 1, new MaterialRatioTraitCondition(0.5f))
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.BENDING, 3)
                .trait(PartTypes.ROD, Const.Traits.SOFT, 3)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 32, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 1, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 4, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 1)
                .trait(PartTypes.TIP, Const.Traits.DULLING, 1)
        );
        // Gold
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.GOLD)
                .craftingWithCommonRod(Tags.Items.INGOTS_GOLD, MaterialCategories.METAL, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xFDFF70, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(32, 7, 22, 50, 1.2f)
                .mainStatsHarvest(12)
                .mainStatsMelee(0, 4, 0.0f)
                .stat(PartGearKey.ofMain(GearTypes.HOE), GearProperties.ATTACK_SPEED, -2f)
                .mainStatsRanged(0, 0.3f)
                .mainStatsProjectile(1.1f, 1.0f)
                .mainStatsArmor(2, 5, 3, 1, 0, 8) //11
                .trait(PartTypes.MAIN, Const.Traits.BRILLIANT, 1, new MaterialRatioTraitCondition(0.7f))
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 1)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 3)
                //rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 3, NumberProperty.Operation.ADD)
                .trait(PartTypes.ROD, Const.Traits.BENDING, 4)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 16, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 6, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.DRAW_SPEED, 0.2f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 30, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 1)
                .trait(PartTypes.TIP, Const.Traits.SOFT, 3)
                //coating
                .stat(PartTypes.COATING, GearProperties.DURABILITY, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.ARMOR_DURABILITY, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.RARITY, 10, NumberProperty.Operation.ADD)
                .trait(PartTypes.COATING, Const.Traits.BRILLIANT, 1)
                .trait(PartTypes.COATING, Const.Traits.SOFT, 3)
        );
        // Iron
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.IRON)
                .craftingWithCommonRod(Tags.Items.INGOTS_IRON, MaterialCategories.METAL, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(Color.VALUE_WHITE, TextureType.HIGH_CONTRAST)
                //main
                .mainStatsCommon(250, 15, 14, 20, 0.7f)
                .mainStatsHarvest(6)
                .mainStatsMelee(2, 1, 0.0f)
                .stat(PartGearKey.ofMain(GearTypes.AXE), GearProperties.ATTACK_SPEED, -0.1f)
                .stat(PartGearKey.ofMain(GearTypes.HOE), GearProperties.ATTACK_SPEED, 0f)
                .mainStatsRanged(1, 0.1f)
                .mainStatsProjectile(1.0f, 1.1f)
                .mainStatsArmor(2, 6, 5, 2, 0, 6) //15
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                //rod
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 2)
                //tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 128, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 4, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.DRAW_SPEED, 0.2f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 8, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.MALLEABLE, 2)
        );
        // Netherite
        ret.add(MaterialBuilder.simple(DataResource.material(ResourceLocation.fromNamespaceAndPath("silentgear", "netherite")))
                .crafting(Tags.Items.INGOTS_NETHERITE, MaterialCategories.METAL, MaterialCategories.ENDGAME, CastingMaterialCategories.CASTING)
                .display(
                        TextUtil.translate("material", "netherite"),
                        TextUtil.translate("material", "netherite"),
                        0x867B86
                )
                //coating
                .stat(PartTypes.COATING, GearProperties.DURABILITY, 0.3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.DURABILITY, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.COATING, GearProperties.ARMOR_DURABILITY, 37f / 33f - 1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.HARVEST_TIER, new HarvestTierPropertyValue(
                        HarvestTier.create(
                                "netherite",
                                "4",
                                BlockTags.INCORRECT_FOR_NETHERITE_TOOL
                        )
                ))
                .stat(PartTypes.COATING, GearProperties.HARVEST_SPEED, 0.125f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.ATTACK_DAMAGE, 1f / 3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.MAGIC_DAMAGE, 1f / 3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.RANGED_DAMAGE, 1f / 3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.COATING, GearProperties.ARMOR_TOUGHNESS, 4, NumberProperty.Operation.ADD)
                .stat(PartTypes.COATING, GearProperties.KNOCKBACK_RESISTANCE, 1f, NumberProperty.Operation.ADD)
                .stat(PartTypes.COATING, GearProperties.ENCHANTMENT_VALUE, 5, NumberProperty.Operation.ADD)
                .trait(PartTypes.COATING, Const.Traits.FIREPROOF, 1)
        );
    }

    private void addGems(Collection<MaterialBuilder<?>> ret) {
        // Diamond
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.DIAMOND)
                .craftingWithCommonRod(Tags.Items.GEMS_DIAMOND, MaterialCategories.GEM, MaterialCategories.ADVANCED, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0x33EBCB, TextureType.HIGH_CONTRAST)
                // main
                .mainStatsCommon(1561, 33, 10, 70, 0.8f)
                .stat(PartGearKey.ofMain(GearTypes.HOE), GearProperties.ATTACK_SPEED, 1f)
                .mainStatsHarvest(8)
                .mainStatsMelee(3, 1, 0.0f)
                .mainStatsRanged(2, -0.2f)
                .mainStatsProjectile(0.9f, 1.1f)
                .mainStatsArmor(3, 8, 6, 3, 8, 4) //20
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.LUSTROUS, 1, materialCountOrRatio(3, 0.5f))
                // rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 5, new MaterialRatioTraitCondition(0.5f))
                .trait(PartTypes.ROD, Const.Traits.LUSTROUS, 4, new MaterialRatioTraitCondition(0.5f))
                // tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 256, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 9, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RANGED_DAMAGE, 0.5f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 20, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.BRITTLE, 2)
                .trait(PartTypes.TIP, Const.Traits.LUSTROUS, 2)
                // adornment
                .trait(PartTypes.SETTING, Const.Traits.BASTION, 1)
        );
        // Emerald
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.EMERALD)
                .craftingWithCommonRod(Tags.Items.GEMS_EMERALD, MaterialCategories.GEM, MaterialCategories.ADVANCED, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0x00B038, TextureType.HIGH_CONTRAST)
                // main
                .mainStatsCommon(1080, 24, 16, 40, 1.0f)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.25f)
                .mainStatsHarvest(10)
                .mainStatsMelee(2, 2, 0.0f)
                .stat(PartGearKey.ofMain(GearTypes.HOE), GearProperties.ATTACK_SPEED, 1f)
                .mainStatsRanged(1, -0.1f)
                .mainStatsProjectile(1.1f, 0.9f)
                .mainStatsArmor(3, 6, 4, 3, 4, 6) //16
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 1)
                .trait(PartTypes.MAIN, Const.Traits.SYNERGISTIC, 2)
                // rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 4, new MaterialRatioTraitCondition(0.5f))
                // tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 512, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 12, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RANGED_DAMAGE, 1, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 20, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.BRITTLE, 1)
                .trait(PartTypes.TIP, Const.Traits.SYNERGISTIC, 2)
                // adornment
                .trait(PartTypes.SETTING, Const.Traits.REACH, 2)
        );
        // Lapis Lazuli
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.LAPIS_LAZULI)
                .crafting(Tags.Items.GEMS_LAPIS, MaterialCategories.GEM, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0x224BAF, TextureType.HIGH_CONTRAST)
                // main
                .mainStatsCommon(200, 13, 17, 30, 1.3f)
                .mainStatsHarvest(5)
                .mainStatsMelee(2, 3, 0.0f)
                .mainStatsRanged(0, -0.1f)
                .mainStatsProjectile(1.0f, 0.8f)
                .mainStatsArmor(2, 6, 5, 2, 0, 10) //15
                // tip
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_SPEED, 0.3f, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.HOLY, 1, new MaterialRatioTraitCondition(0.75f))
                .trait(PartTypes.TIP, Const.Traits.LUCKY, 4, new MaterialRatioTraitCondition(0.75f))
                // adornment
                .trait(PartTypes.SETTING, Const.Traits.LUCKY, 3, new MaterialRatioTraitCondition(0.75f))
        );
        // Quartz
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.QUARTZ)
                .craftingWithCommonRod(Tags.Items.GEMS_QUARTZ, MaterialCategories.GEM, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xD4CABA, TextureType.HIGH_CONTRAST)
                // main
                .mainStatsCommon(330, 13, 10, 40, 1.2f)
                .mainStatsHarvest(7)
                .mainStatsMelee(2, 0, 0.1f)
                .mainStatsRanged(0, 0.1f)
                .mainStatsProjectile(1f, 1f)
                .mainStatsArmor(3, 5, 4, 2, 0, 4) //14
                .trait(PartTypes.MAIN, Const.Traits.CRUSHING, 3)
                .trait(PartTypes.MAIN, Const.Traits.JAGGED, 2)
                // rod
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                // tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 64, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ARMOR_DURABILITY, 4, NumberProperty.Operation.ADD)
                .harvestTierBuiltin(PartTypes.TIP)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 4, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RANGED_DAMAGE, 1.5f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 20, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.CHIPPING, 1)
                .trait(PartTypes.TIP, Const.Traits.JAGGED, 3)
                // adornment
                .trait(PartTypes.SETTING, Const.Traits.MIGHTY, 2, new MaterialRatioTraitCondition(0.5f))
        );
        // Amethyst
        ret.add(MaterialBuilder.builtin(BuiltinMaterials.AMETHYST)
                .crafting(Tags.Items.GEMS_AMETHYST, MaterialCategories.GEM, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xA31DE6, TextureType.HIGH_CONTRAST)
                // main
                .mainStatsCommon(210, 10, 16, 35, 1.3f)
                .mainStatsHarvest(6)
                .mainStatsMelee(1, 3, 0)
                .mainStatsRanged(1, 0)
                .mainStatsProjectile(1, 1)
                .mainStatsArmor(3, 5, 4, 3, 0, 10) //15
                .trait(PartTypes.MAIN, Const.Traits.RENEW, 1, new MaterialRatioTraitCondition(0.7f))
                // tip
                .stat(PartTypes.TIP, GearProperties.DURABILITY, -0.25f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .harvestTierBuiltin(PartTypes.TIP)
                .trait(PartTypes.TIP, Const.Traits.SILKY, 1, new MaterialRatioTraitCondition(0.66f))
                //adornment
                .trait(PartTypes.SETTING, Const.Traits.CURSED, 4)
        );
    }

    private void addDusts(Collection<MaterialBuilder<?>> ret) {
        // Glowstone
        ret.add(MaterialBuilder.simple(DataResource.material(ResourceLocation.fromNamespaceAndPath("silentgear", "glowstone")))
                .crafting(Tags.Items.DUSTS_GLOWSTONE, MaterialCategories.GEM, MaterialCategories.DUST, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xD2D200, TextureType.HIGH_CONTRAST)
                // main (additive)
                .stat(PartTypes.MAIN, GearProperties.ADDITIVE, new BooleanPropertyValue(true))
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.15f, NumberProperty.Operation.MULTIPLY_BASE)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 40)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 15)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 3)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.4f)
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 3)
                .trait(PartTypes.MAIN, Const.Traits.RENEW, 3)
                //tip
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 0.4f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.MAGIC_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.DRAW_SPEED, 0.3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.TIP, GearProperties.RARITY, 15, NumberProperty.Operation.ADD)
                .trait(PartTypes.TIP, Const.Traits.REFRACTIVE, 1)
                .trait(PartTypes.TIP, Const.Traits.LUSTROUS, 4)
        );
        // Redstone
        ret.add(MaterialBuilder.simple(DataResource.material(ResourceLocation.fromNamespaceAndPath("silentgear", "redstone")))
                .crafting(Tags.Items.DUSTS_REDSTONE, MaterialCategories.METAL, MaterialCategories.DUST, MaterialCategories.INTERMEDIATE, CastingMaterialCategories.CASTING)
                .displayWithDefaultName(0xBB0000, TextureType.HIGH_CONTRAST)
                // main (additive)
                .stat(PartTypes.MAIN, GearProperties.ADDITIVE, new BooleanPropertyValue(true))
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, -0.1f, NumberProperty.Operation.MULTIPLY_BASE)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.1f, NumberProperty.Operation.MULTIPLY_BASE)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 30)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 12)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 4)
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2)
                .trait(PartTypes.MAIN, Const.Traits.IMPERIAL, 3)
                //tip
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.TIP, GearProperties.ATTACK_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.ATTACK_SPEED, 0.3f, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RANGED_DAMAGE, 2, NumberProperty.Operation.ADD)
                .stat(PartTypes.TIP, GearProperties.RARITY, 10, NumberProperty.Operation.ADD)
        );
    }

    private void addExtraMetals(Collection<MaterialBuilder<?>> ret) {
        ret.add(extraMetal("aluminum", 2, commonId("ingots/aluminum"))
                .displayWithDefaultName(0xBFD4DE, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 365)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 15)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 14)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 8)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 2)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 2)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.2f)
                .mainStatsArmor(2, 6, 4, 2, 0.5f, 3) //14
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.2f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 25)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.9f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RANGED_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 30)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 2, new MaterialRatioTraitCondition(0.5f))
                .trait(PartTypes.MAIN, Const.Traits.SYNERGISTIC, 1)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.ROD, Const.Traits.SYNERGISTIC, 2)
        );
        // Brass
        ret.add(extraMetal("brass", 2, commonId("ingots/brass"))
                .displayWithDefaultName(0xF2D458, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 240)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 8)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 13)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 7)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 1.5f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 1.5f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.1f)
                .mainStatsArmor(2, 6, 4, 2, 1, 6) //14
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.2f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 25)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.2f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 25)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.SILKY, 1, new MaterialRatioTraitCondition(0.66f))
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
        );
        // Electrum
        ret.add(extraMetal("electrum", 2, commonId("ingots/electrum"))
                .displayWithDefaultName(0xD6E037, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 96)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 10)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 25)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 14)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 5f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.3f)
                .mainStatsArmor(2, 6, 5, 2, 0, 11) //15
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 1)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.4f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 40)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.5f)
                .stat(PartTypes.ROD, GearProperties.ATTACK_SPEED, 0.1f, NumberProperty.Operation.ADD)
                .stat(PartTypes.ROD, GearProperties.DRAW_SPEED, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 40)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 2, new MaterialRatioTraitCondition(0.5f))
                .trait(PartTypes.ROD, Const.Traits.LUSTROUS, 3, new MaterialRatioTraitCondition(0.5f))
        );
        // Enderium
        ret.add(extraMetal("enderium", 4, commonId("ingots/enderium"))
                .displayWithDefaultName(0x468C75, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 1200)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 34)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 13)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.NETHERITE)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 18)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 6f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(3, 9, 7, 3, 8, 10) //22
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 3)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 80)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.2f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 60)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
        );
        // Invar
        ret.add(extraMetal("invar", 2, commonId("ingots/invar"))
                .displayWithDefaultName(0xC2CBB8, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 640)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 20)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 13)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 7)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 3.5f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 3f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(2, 8, 6, 2, 2, 6) //18
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 50)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.2f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 50)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.ADAMANT, 2, materialCountOrRatio(3, 0.35f))
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 3)
        );
        // Lead
        ret.add(extraMetal("lead", 2, commonId("ingots/lead"))
                .displayWithDefaultName(0xC2CBB8, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 260)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 14)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 15)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 4)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, -0.4f)
                .mainStatsArmor(2, 5, 4, 2, 0, 4) //13
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 1)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, -0.3f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 40)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.8f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 40)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.AQUATIC, 2, materialCountOrRatio(3, 0.35f))
                .trait(PartTypes.ROD, Const.Traits.SOFT, 4)
        );
        // Lumium
        ret.add(extraMetal("lumium", 3, commonId("ingots/lumium"))
                .displayWithDefaultName(0xFFD789, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 920)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 20)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 14)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.DIAMOND)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 15)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 3f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.2f)
                .mainStatsArmor(2, 8, 6, 2, 4, 10) //18
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 75)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.3f)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RANGED_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 75)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.REFRACTIVE, 1, new MaterialRatioTraitCondition(0.5f))
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.ROD, Const.Traits.REFRACTIVE, 1, new MaterialRatioTraitCondition(0.5f))
        );
        // Nickel
        ret.add(extraMetal("nickel", 2, commonId("ingots/nickel"))
                .displayWithDefaultName(0xEFE87B, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 380)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 17)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 12)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 7)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 2.5f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 1f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.1f)
                .mainStatsArmor(2, 5, 4, 2, 0, 6) //13
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 0.5f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.1f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 40)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.0f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 40)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.ADAMANT, 1, materialCountOrRatio(3, 0.35f))
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 3)
        );
        // Osmium
        ret.add(extraMetal("osmium", 2, commonId("ingots/osmium"))
                .displayWithDefaultName(0x92A6B8, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 500)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 30)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 12)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 10)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.1f)
                .mainStatsArmor(3, 6, 5, 3, 0, 4) //17
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 1f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 35)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.1f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.REPAIR_EFFICIENCY, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 35)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
        );
        // Platinum
        ret.add(extraMetal("platinum", 3, commonId("ingots/platinum"))
                .displayWithDefaultName(0xB3B3FF, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 900)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 21)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 14)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.DIAMOND)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 12)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(2, 8, 6, 2, 2, 12) //18
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 1f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 80)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.2f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.REPAIR_EFFICIENCY, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 70)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 2)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.ROD, Const.Traits.SOFT, 4)
        );
        // Refined glowstone
        ret.add(extraMetal("refined_glowstone", 3, commonId("ingots/refined_glowstone"))
                .displayWithDefaultName(0xFDE054, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 300)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 18)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 18)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 14)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 5f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 3f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(3, 7, 6, 3, 0, 8) //19
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 3)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 45)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.8f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.2f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 45)
                .stat(PartTypes.TIP, GearProperties.HARVEST_SPEED, 5, NumberProperty.Operation.ADD)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.LUSTROUS, 4)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 4)
                .trait(PartTypes.ROD, Const.Traits.LUSTROUS, 1, new MaterialRatioTraitCondition(0.75f))
                .trait(PartTypes.TIP, Const.Traits.REFRACTIVE, 1)
        );
        // Refined obsidian
        ret.add(extraMetal("refined_obsidian", 4, commonId("ingots/refined_obsidian"))
                .displayWithDefaultName(0x665482, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 2500)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 50)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, -0.3f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 40)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.DIAMOND)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 20)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 10f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.3f)
                .mainStatsArmor(5, 12, 8, 5, 16, 6) //30
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 4)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.2f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 70)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.8f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.25f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 70)
                .stat(PartTypes.TIP, GearProperties.DURABILITY, 600, NumberProperty.Operation.ADD)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 3)
                .trait(PartTypes.MAIN, Const.Traits.HARD, 4)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 4)
                .trait(PartTypes.ROD, Const.Traits.HARD, 3)
                .trait(PartTypes.TIP, Const.Traits.VULCAN, 1)
        );
        // Signalum
        ret.add(extraMetal("signalum", 4, commonId("ingots/signalum"))
                .displayWithDefaultName(0xFF5E28, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 800)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 25)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 16)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.DIAMOND)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 13)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 3f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(2, 7, 5, 2, 2, 4) //16
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.2f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 50)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.2f)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.3f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.REPAIR_EFFICIENCY, -0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 50)
                .trait(PartTypes.MAIN, Const.Traits.FLEXIBLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.LUSTROUS, 4)
                .trait(PartTypes.ROD, Const.Traits.FLEXIBLE, 4)
                .trait(PartTypes.ROD, Const.Traits.LUSTROUS, 2)
        );
        // Silver
        ret.add(extraMetal("silver", 2, commonId("ingots/silver"))
                .displayWithDefaultName(0xCBCCEA, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 64)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 9)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 20)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.GOLD)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 11)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 0f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.2f)
                .mainStatsArmor(2, 5, 3, 1, 0, 10) //11
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 0f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.3f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 40)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.1f)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.MAGIC_DAMAGE, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 40)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 1)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 1)
                .trait(PartTypes.ROD, Const.Traits.SOFT, 2)
        );
        // Steel
        ret.add(extraMetal("steel", 2, commonId("ingots/steel"))
                .displayWithDefaultName(0x929292, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 500)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 20)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, 0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 11)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.IRON)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 6)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 4f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 1f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, -0.2f)
                .mainStatsArmor(3, 8, 6, 3, 2, 6) //20
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, -0.2f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 40)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 0.8f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, 0.25f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 40)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 5)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 5)
        );
        // Tin
        ret.add(extraMetal("tin", 1, commonId("ingots/tin"))
                .displayWithDefaultName(0x89A5B4, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 192)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 13)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 12)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.STONE)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 5)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 1.5f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 1f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(2, 5, 3, 2, 0, 2) //12
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 0.5f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 15)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.1f)
                .stat(PartTypes.ROD, GearProperties.ATTACK_SPEED, 0.2f, NumberProperty.Operation.ADD)
                .stat(PartTypes.ROD, GearProperties.RARITY, 15)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 1)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 2)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.ROD, Const.Traits.SOFT, 1)
        );
        // Uranium
        ret.add(extraMetal("uranium", 3, commonId("ingots/uranium"))
                .displayWithDefaultName(0x21FF0F, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 800)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 20)
                .stat(PartTypes.MAIN, GearProperties.REPAIR_VALUE, -0.15f)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 17)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.DIAMOND)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 6)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 2f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.1f)
                .mainStatsArmor(2, 5, 4, 2, 1, 3) //13
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 0.5f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.1f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 50)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.5f)
                .stat(PartTypes.ROD, GearProperties.HARVEST_SPEED, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.DRAW_SPEED, 0.1f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 40)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
        );
        // Zinc
        ret.add(extraMetal("zinc", 1, commonId("ingots/zinc"))
                .displayWithDefaultName(0xC9D3CE, TextureType.HIGH_CONTRAST)
                .stat(PartTypes.MAIN, GearProperties.DURABILITY, 192)
                .stat(PartTypes.MAIN, GearProperties.ARMOR_DURABILITY, 10)
                .stat(PartTypes.MAIN, GearProperties.ENCHANTMENT_VALUE, 15)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_TIER, HarvestTier.STONE)
                .stat(PartTypes.MAIN, GearProperties.HARVEST_SPEED, 3)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_DAMAGE, 1.5f)
                .stat(PartTypes.MAIN, GearProperties.MAGIC_DAMAGE, 1f)
                .stat(PartTypes.MAIN, GearProperties.ATTACK_SPEED, 0.0f)
                .mainStatsArmor(1, 5, 3, 1, 0, 2) //11
                .stat(PartTypes.MAIN, GearProperties.RANGED_DAMAGE, 0f)
                .stat(PartTypes.MAIN, GearProperties.DRAW_SPEED, 0.0f)
                .stat(PartTypes.MAIN, GearProperties.RARITY, 10)
                .stat(PartTypes.MAIN, GearProperties.CHARGING_VALUE, 1.1f)
                .stat(PartTypes.ROD, GearProperties.DURABILITY, -0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.ATTACK_DAMAGE, 0.05f, NumberProperty.Operation.MULTIPLY_TOTAL)
                .stat(PartTypes.ROD, GearProperties.RARITY, 15)
                .trait(PartTypes.MAIN, Const.Traits.MALLEABLE, 1)
                .trait(PartTypes.MAIN, Const.Traits.SOFT, 2)
                .trait(PartTypes.ROD, Const.Traits.MALLEABLE, 2)
                .trait(PartTypes.ROD, Const.Traits.SOFT, 3)
        );
    }

    private void addSilentGems(Collection<MaterialBuilder<?>> ret) {
        ret.add(gem(Gems.RUBY, MaterialCategories.INTERMEDIATE) // durability
                .mainStatsCommon(1024, 34, 12, 30, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.RUBY), 6) // iron
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(2, 6, 5, 2, 4, 6) //15
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.IMPERIAL, 2)
        );
        ret.add(gem(Gems.CARNELIAN, MaterialCategories.INTERMEDIATE) // durability
                .mainStatsCommon(1280, 37, 10, 45, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.CARNELIAN), 7) // iron
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 6, 5, 2, 4, 8) //16
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.GOLD_DIGGER, 2)
        );
        ret.add(gem(Gems.TOPAZ, MaterialCategories.INTERMEDIATE) // speed
                .mainStatsCommon(512, 21, 12, 30, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.TOPAZ), 8) // iron
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(2, 6, 5, 2, 4, 6) //15
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.LUSTROUS, 1)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 2)
        );
        ret.add(gem(Gems.CITRINE, MaterialCategories.INTERMEDIATE) // speed
                .mainStatsCommon(768, 26, 10, 45, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.CITRINE), 10) // iron
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 6, 5, 2, 4, 8) //16
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.ANCIENT, 4)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.SETTING, GemsTraits.BOOSTER, 1)
        );
        ret.add(gem(Gems.HELIODOR, MaterialCategories.ADVANCED) // speed
                .mainStatsCommon(1024, 34, 10, 60, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.HELIODOR), 14) // diamond
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(3, 6, 5, 2, 4, 8) //16
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.CHIPPING, 4)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 2)
                .trait(PartTypes.SETTING, Const.Traits.MIGHTY, 4)
        );
        ret.add(gem(Gems.MOLDAVITE, MaterialCategories.INTERMEDIATE) // damage
                .mainStatsCommon(512, 26, 10, 45, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.MOLDAVITE), 6) // iron
                .mainStatsMelee(4, 0, 0)
                .mainStatsRanged(3, 0)
                .mainStatsArmor(3, 6, 5, 2, 4, 8) //16
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.SHARP, 3)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 3)
        );
        ret.add(gem(Gems.PERIDOT, MaterialCategories.INTERMEDIATE) // damage
                .mainStatsCommon(512, 21, 12, 30, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.PERIDOT), 6) // iron
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(2, 6, 5, 2, 4, 6) //15
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.JAGGED, 2)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.SETTING, GemsTraits.HEARTY, 2)
        );
        ret.add(gem(Gems.TURQUOISE, MaterialCategories.ADVANCED) // all-rounder, magic armor
                .mainStatsCommon(1536, 40, 15, 45, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.TURQUOISE), 8) // diamond
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(2, 6, 5, 2, 6, 20) //15
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.TURTLE, 1)
                .trait(PartTypes.MAIN, GemsTraits.BARRIER_JACKET, 5)
                .trait(PartTypes.SETTING, GemsTraits.LEAPING, 3)
        );
        ret.add(gem(Gems.KYANITE, MaterialCategories.ADVANCED) // all-rounder
                .mainStatsCommon(1280, 38, 17, 60, 1.4f)
                .mainStatsHarvest(harvestTier(Gems.KYANITE), 12) // netherite
                .mainStatsMelee(4, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 8, 6, 3, 8, 14) //20
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.CHILLED, 4)
                .trait(PartTypes.MAIN, Const.Traits.STELLAR, 2)
        );
        ret.add(gem(Gems.SAPPHIRE, MaterialCategories.INTERMEDIATE) // armor
                .mainStatsCommon(512, 28, 12, 30, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.SAPPHIRE), 6) // iron
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(3, 8, 5, 2, 6, 10) //18
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.AQUATIC, 2)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 2)
        );
        ret.add(gem(Gems.IOLITE, MaterialCategories.INTERMEDIATE) // armor
                .mainStatsCommon(768, 32, 10, 45, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.IOLITE), 7) // iron
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 8, 6, 3, 6, 14) //20
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.CRUSHING, 3)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 2)
                .trait(PartTypes.SETTING, Const.Traits.CURE_POISON, 1)
        );
        ret.add(gem(Gems.ALEXANDRITE, MaterialCategories.INTERMEDIATE) // all-rounder
                .mainStatsCommon(1024, 29, 15, 45, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.ALEXANDRITE), 9) // diamond
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 8, 5, 2, 5, 10) //18
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.IMPERIAL, 5)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 1)
                .trait(PartTypes.SETTING, GemsTraits.CLOAKING, 1)
        );
        ret.add(gem(Gems.AMMOLITE, MaterialCategories.ADVANCED) // armor
                .mainStatsCommon(1024, 40, 12, 60, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.AMMOLITE), 8) // diamond
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(4, 9, 6, 3, 12, 16) //22
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, GemsTraits.FRACTAL, 4)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 3)
        );
        ret.add(gem(Gems.ROSE_QUARTZ, MaterialCategories.ADVANCED) // damage
                .mainStatsCommon(1024, 34, 12, 60, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.ROSE_QUARTZ), 14) // netherite
                .mainStatsMelee(6, 0, 0)
                .mainStatsRanged(3, 0)
                .mainStatsArmor(3, 6, 5, 2, 4, 8) //16
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, GemsTraits.CRITICAL_STRIKE, 3)
                .trait(PartTypes.MAIN, Const.Traits.BRITTLE, 3)
        );
        ret.add(gem(Gems.BLACK_DIAMOND, MaterialCategories.ADVANCED) // super
                .mainStatsCommon(1792, 39, 12, 55, 1.0f)
                .mainStatsHarvest(harvestTier(Gems.BLACK_DIAMOND), 9) // netherite
                .mainStatsMelee(4, 0, 0)
                .mainStatsRanged(3, 0)
                .mainStatsArmor(3, 8, 6, 3, 8, 10) //20
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.FLAME_WARD, 1,
                        new MaterialRatioTraitCondition(0.7f))
                .trait(PartTypes.MAIN, Const.Traits.STURDY, 2)
                .trait(PartTypes.SETTING, Const.Traits.CURE_WITHER, 1)
        );
        ret.add(gem(Gems.WHITE_DIAMOND, MaterialCategories.ADVANCED) // super
                .mainStatsCommon(2048, 44, 14, 70, 1.0f)
                .mainStatsHarvest(harvestTier(Gems.WHITE_DIAMOND), 12) // netherite
                .mainStatsMelee(5, 0, 0)
                .mainStatsRanged(3, 0)
                .mainStatsArmor(4, 9, 7, 4, 16, 20) //24
                .noProperties(PartTypes.ROD)
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, Const.Traits.VOID_WARD, 1,
                        new MaterialRatioTraitCondition(0.7f))
                .trait(PartTypes.MAIN, Const.Traits.STURDY, 2)
                .trait(PartTypes.SETTING, Const.Traits.MOONWALKER, 2)
        );
        ret.add(gem(Gems.GARNET, MaterialCategories.INTERMEDIATE) // damage
                .mainStatsCommon(512, 21, 12, 40, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.PERIDOT), 6) // iron
                .mainStatsMelee(3, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(2, 6, 5, 2, 4, 6) //15
                .trait(PartTypes.MAIN, GemsTraits.CRITICAL_STRIKE, 1)
                .trait(PartTypes.ROD, Const.Traits.JAGGED, 3)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.SETTING, GemsTraits.POWER, 2)
        );
        ret.add(gem(Gems.AQUAMARINE, MaterialCategories.INTERMEDIATE) // durability
                .mainStatsCommon(1024, 34, 12, 40, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.AQUAMARINE), 6) // iron
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(2, 6, 5, 2, 4, 6) //15
                .trait(PartTypes.MAIN, Const.Traits.FORTUNATE, 1)
                .trait(PartTypes.ROD, Const.Traits.FORTUNATE, 1)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.SETTING, GemsTraits.STEP_UP, 1)
        );
        ret.add(gem(Gems.TANZANITE, MaterialCategories.INTERMEDIATE) // all-rounder
                .mainStatsCommon(768, 28, 15, 50, 1.2f)
                .mainStatsHarvest(harvestTier(Gems.TANZANITE), 9) // diamond
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 8, 5, 2, 5, 10) //18
                .trait(PartTypes.MAIN, Const.Traits.LIGHT, 2)
                .trait(PartTypes.ROD, Const.Traits.SHARP, 2)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.SETTING, GemsTraits.TWINKLETOES, 4)
        );
        ret.add(gem(Gems.OPAL, MaterialCategories.ADVANCED) // speed/armor
                .mainStatsCommon(1024, 36, 10, 60, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.OPAL), 13) // diamond
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(1, 0)
                .mainStatsArmor(3, 8, 5, 2, 4, 8) //18
                .trait(PartTypes.MAIN, GemsTraits.FREEZE_RESISTANT, 4)
                .trait(PartTypes.MAIN, GemsTraits.ENDERBANE, 2)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.ROD, GemsTraits.ENDERBANE, 4)
                .trait(PartTypes.SETTING, GemsTraits.HASTY, 3)
        );
        ret.add(gem(Gems.PEARL, MaterialCategories.ADVANCED) // armor
                .mainStatsCommon(1024, 44, 12, 65, 1.3f)
                .mainStatsHarvest(harvestTier(Gems.PEARL), 8) // diamond
                .mainStatsMelee(2, 0, 0)
                .mainStatsRanged(2, 0)
                .mainStatsArmor(3, 8, 6, 3, 12, 16) //20
                .noProperties(PartTypes.SETTING)
                .trait(PartTypes.MAIN, GemsTraits.NEPTUNES_BLESSING, 3)
                .trait(PartTypes.ROD, Const.Traits.AQUATIC, 4)
                .trait(PartTypes.ROD, Const.Traits.BRITTLE, 3)
                .trait(PartTypes.SETTING, GemsTraits.NEPTUNES_BLESSING, 4)
        );
    }

    private static MaterialBuilder<SimpleMaterial> extraMetal(String name, int tier, ResourceLocation tag) {
        var tierCategory = List.of(MaterialCategories.BASIC, MaterialCategories.BASIC, MaterialCategories.INTERMEDIATE, MaterialCategories.ADVANCED, MaterialCategories.ENDGAME)
                .get(tier);
        return MaterialBuilder.simple(DataResource.material(SilentGear.getId(name)))
                .crafting(Ingredient.of(TagKey.create(Registries.ITEM, tag)), MaterialCategories.METAL, CastingMaterialCategories.CASTING, tierCategory);
    }

    private static MaterialBuilder<SimpleMaterial> customMaterial(String name, ResourceLocation tag) {
        return MaterialBuilder.simple(DataResource.material(ResourceLocation.fromNamespaceAndPath(SGearMetalworks.MODID, name)))
                .crafting(Ingredient.of(TagKey.create(Registries.ITEM, tag)), MaterialCategories.METAL, CastingMaterialCategories.CASTING);
    }

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    protected static ITraitCondition materialCountOrRatio(int count, float ratio) {
        return new OrTraitCondition(new MaterialCountTraitCondition(count), new MaterialRatioTraitCondition(ratio));
    }

    private static MaterialBuilder<?> gem(Gems gem, IMaterialCategory tier) {
        return MaterialBuilder.simple(DataResource.material(SilentGems.getId(gem.getName())))
                .crafting(
                        gem.getItemTag(),
                        MaterialCategories.GEM, tier, CastingMaterialCategories.CASTING
                )
                .display(
                        gem.getDisplayName(),
                        gem.getColor(),
                        TextureType.HIGH_CONTRAST
                );
    }

    public static HarvestTier harvestTier(Gems gem) {
        return new HarvestTier(
                gem.getName(),
                Optional.of(gem.getHarvestTierLevelHint()),
                TagKey.create(Registries.BLOCK, SilentGems.getId("incorrect_for_" + gem.getName() + "_tools"))
        );
    }
}
