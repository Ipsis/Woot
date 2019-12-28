package ipsis.woot.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IntReferenceHolder;

import javax.annotation.Nullable;

/**
 * This is from McJty. src/main/java/mcjty/lib/container/GenericContainer.java
 * It splits the integer over two shorts which are synced via the standard vanilla code
 * Vanilla does not send integer data over for the containrs
 */

public class WootContainer extends Container {

    protected WootContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    public void addShortListener(IntReferenceHolder holder) {
        trackInt(holder);
    }

    public void addIntegerListener(IntReferenceHolder holder) {
        trackInt(new IntReferenceHolder() {
            private int lastKnown;

            @Override
            public int get() {
                return holder.get() & 0xffff;
            }

            @Override
            public void set(int val) {
                int full = holder.get();
                holder.set((full & 0xffff0000) | (val & 0xffff));
            }

            @Override
            public boolean isDirty() {
                int i = this.get();
                boolean flag = i != this.lastKnown;
                this.lastKnown = i;
                return flag;
            }
        });
        trackInt(new IntReferenceHolder() {
            private int lastKnown;

            @Override
            public int get() {
                return (holder.get() >> 16) & 0xffff;
            }

            @Override
            public void set(int val) {
                int full = holder.get();
                holder.set((full & 0x0000ffff) | ((val & 0xffff) << 16));
            }

            @Override
            public boolean isDirty() {
                int i = this.get();
                boolean flag = i != this.lastKnown;
                this.lastKnown = i;
                return flag;
            }
        });
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
