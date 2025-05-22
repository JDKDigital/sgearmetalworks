package cy.jdkdigital.sgearmetalworks.registry;

import net.silentchaos512.gems.util.Gems;

public class SGemsMetalworksRegistrator
{
    public static void register() {
        for(Gems gem: Gems.values()) {
            // Register fluids
            SGearMetalworksRegistrator.registerFluid("molten_" + gem.getName(), gem.getColor());
        }
    }
}
