package ipsis.woot.items;

import ipsis.woot.machines.squeezer.SqueezerManager;
import ipsis.woot.util.WootItem;

public class ItemDyePlate extends WootItem {

    private final String basename;
    private final SqueezerManager.DyeMakeup dyeMakeup;

    public ItemDyePlate(SqueezerManager.DyeMakeup dyeMakeup, String basename) {
        super(basename);
        this.dyeMakeup = dyeMakeup;
        this.basename = basename;
    }

    public SqueezerManager.DyeMakeup getDyeMakeup() { return this.dyeMakeup; }


}
