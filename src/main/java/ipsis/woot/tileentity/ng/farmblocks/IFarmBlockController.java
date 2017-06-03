package ipsis.woot.tileentity.ng.farmblocks;

import ipsis.woot.tileentity.ng.WootMob;

import javax.annotation.Nonnull;

public interface IFarmBlockController {

    @Nonnull WootMob getWootMob();
    boolean isProgrammed();
}
