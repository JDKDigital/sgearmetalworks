package cy.jdkdigital.sgearmetalworks.datagen;

import cy.jdkdigital.sgearmetalworks.SGearMetalworks;
import cy.jdkdigital.sgearmetalworks.registry.ModTags;
import cy.jdkdigital.sgearmetalworks.registry.SGearMetalworksRegistrator;
import net.minecraft.data.DataGenerator;
import net.silentchaos512.gear.api.data.trait.TraitBuilder;
import net.silentchaos512.gear.api.data.trait.TraitsProviderBase;
import net.silentchaos512.gear.gear.trait.effect.FireproofTraitEffect;
import net.silentchaos512.gear.gear.trait.effect.NegateDamageTraitEffect;
import net.silentchaos512.gear.setup.gear.GearTypes;

import java.util.Collection;
import java.util.List;

public class TraitsProvider extends TraitsProviderBase
{
    public TraitsProvider(DataGenerator generator) {
        super(generator, SGearMetalworks.MODID);
    }

    @Override
    public Collection<TraitBuilder> getTraits() {
        return List.of(TraitBuilder.of(SGearMetalworksRegistrator.NEGATE_STING_TRAIT, 4)
                .withGearTypeCondition(GearTypes.ARMOR)
                .effects(
                        FireproofTraitEffect.INSTANCE,
                        new NegateDamageTraitEffect(ModTags.DamageTypes.URU_NEGATE_DAMAGE, 0.25f)
                ));
    }
}
