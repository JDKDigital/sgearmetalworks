package cy.jdkdigital.sgearmetalworks.datagen.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.silentchaos512.gear.setup.SgDataComponents;

import java.util.Arrays;

public class GearComponentIngredient extends DataComponentIngredient
{
    public static final MapCodec<GearComponentIngredient> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            HolderSetCodec.create(Registries.ITEM, BuiltInRegistries.ITEM.holderByNameCodec(), false).fieldOf("items").forGetter(GearComponentIngredient::items),
                            DataComponentPredicate.CODEC.fieldOf("components").forGetter(GearComponentIngredient::components)
                    )
                    .apply(builder, GearComponentIngredient::new));


    public GearComponentIngredient(HolderSet<Item> items, DataComponentPredicate components) {
        super(items, components, false);
    }

    @Override
    public IngredientType<?> getType() {
        return SGearMetalworksRegistrator.GEAR_INGREDIENT_TYPE.get();
    }

    public static Ingredient of(ItemStack stack) {
        var builder = DataComponentMap.builder();
        if (stack.has(SgDataComponents.MATERIAL_LIST)) {
            builder.set(SgDataComponents.MATERIAL_LIST, stack.get(SgDataComponents.MATERIAL_LIST));
        }
        return of(DataComponentPredicate.allOf(builder.build()), stack.getItem());
    }

    public static Ingredient of(DataComponentPredicate predicate, ItemLike... items) {
        return of(predicate, HolderSet.direct(Arrays.stream(items).map(ItemLike::asItem).map(Item::builtInRegistryHolder).toList()));
    }

    public static Ingredient of(DataComponentPredicate predicate, HolderSet<Item> items) {
        return new GearComponentIngredient(items, predicate).toVanilla();
    }
}
