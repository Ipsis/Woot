package ipsis.woot.modules.factory.items;

import ipsis.woot.modules.factory.FactoryTier;

import java.util.Arrays;

public enum InternModes {

    BUILD_TIER_1("build_tier_1", FactoryTier.TIER_1),
    BUILD_TIER_2("build_tier_2", FactoryTier.TIER_2),
    BUILD_TIER_3("build_tier_3", FactoryTier.TIER_3),
    BUILD_TIER_4("build_tier_4", FactoryTier.TIER_4),
    BUILD_TIER_5("build_tier_5", FactoryTier.TIER_5),
    VALIDATE_TIER_1("validate_tier_1", FactoryTier.TIER_1),
    VALIDATE_TIER_2("validate_tier_2", FactoryTier.TIER_2),
    VALIDATE_TIER_3("validate_tier_3", FactoryTier.TIER_3),
    VALIDATE_TIER_4("validate_tier_4", FactoryTier.TIER_4),
    VALIDATE_TIER_5("validate_tier_5", FactoryTier.TIER_5),
    DESTROY("destroy", FactoryTier.UNKNOWN);

    private String name;
    private FactoryTier tier;
    InternModes(String name, FactoryTier tier) {
        this.name = name;
        this.tier = tier;
    }

    public boolean isBuildMode() {
        return this == BUILD_TIER_1 || this == BUILD_TIER_2 || this == BUILD_TIER_3 || this == BUILD_TIER_4 || this == BUILD_TIER_5;
    }

    public boolean isValidateMode() {
        return this == VALIDATE_TIER_1 || this == VALIDATE_TIER_2 || this == VALIDATE_TIER_3 || this == VALIDATE_TIER_4 || this == VALIDATE_TIER_5;
    }

    public String getName() {
        return name;
    }

    public FactoryTier getTier() {
        return tier;
    }

    public static InternModes getFromName(String name) {
        return Arrays.stream(InternModes.values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(BUILD_TIER_1);
    }

    public InternModes getNextMode() {
        return InternModes.values()[(this.ordinal() + 1) % InternModes.values().length];
    }

    public String getDescriptionId() {
        return "info.woot.intern.modes." + name;
    }

}
