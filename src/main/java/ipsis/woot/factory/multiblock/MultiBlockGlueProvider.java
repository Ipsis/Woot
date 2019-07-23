package ipsis.woot.factory.multiblock;

import javax.annotation.Nonnull;

public interface MultiBlockGlueProvider {

    @Nonnull
    MultiBlockGlue getGlue();
}
