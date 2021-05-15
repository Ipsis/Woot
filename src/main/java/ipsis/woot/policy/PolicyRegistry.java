package ipsis.woot.policy;

import ipsis.woot.Woot;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines what you are not allowed to do.
 *
 * Internal policy handles what is broken or does not work well - ie hardcoded into the mod
 * Custom and default policy can be modified by the user
 *
 * 'capture' - cannot capture and the factory cannot simulate
 * 'learn' - items we cannot learn from simulated kills
 * 'generate' - factory can generate these items
 */

public class PolicyRegistry {

    public static final Logger LOGGER = LogManager.getLogger();

    private static PolicyRegistry INSTANCE = new PolicyRegistry();
    public static PolicyRegistry get() { return INSTANCE; }

    public PolicyRegistry() {
        loadInternalConfig();
    }

    private void loadInternalConfig() {
        // ignore  entities from the following mods
        InternalPolicy.CAPTURED_MOD.forEach(s -> internalPolicy.addToCaptureMod(s));

        // ignore the following entities
        InternalPolicy.CAPTURED_ENTITY.forEach(s -> internalPolicy.addToCaptureEntity(s));

        // do not learn items from the following mods
        InternalPolicy.LEARN_MOD.forEach(s -> internalPolicy.addToLearnMod(s));

        // do not learn the following items
        InternalPolicy.LEARN_ITEM.forEach(s -> internalPolicy.addToLearnItem(s));
        internalPolicy.addToLearnMod("cyberware");

        /**
         * There is no internal generate blacklist as by default
         * if you can learn it then you can drop it
         */
    }

    public void loadFromConfig() {
        customPolicy.clear();
        PolicyConfiguration.CAPTURE_BLACKLIST_FULL_MOD.get().forEach(s -> customPolicy.addToCaptureMod(s));
        PolicyConfiguration.CAPTURE_BLACKLIST_ENTITY.get().forEach(s -> customPolicy.addToCaptureEntity(s));
        PolicyConfiguration.LEARN_BLACKLIST_FULL_MOD.get().forEach(s -> customPolicy.addToLearnMod(s));
        PolicyConfiguration.LEARN_BLACKLIST_ITEM.get().forEach(s -> customPolicy.addToLearnItem(s));
        PolicyConfiguration.GENERATE_BLACKLIST_FULL_MOD.get().forEach(s -> customPolicy.addToGenerateMod(s));
        PolicyConfiguration.GENERATE_BLACKLIST_ITEM.get().forEach(s -> customPolicy.addToGenerateItem(s));
    }

    private A internalPolicy = new A();
    private A customPolicy = new A();

    private class A {
        private List<String> captureMod = new ArrayList<>();
        private List<String> captureEntity = new ArrayList<>();
        private List<String> learnMod = new ArrayList<>();
        private List<String> learnItem = new ArrayList<>();
        private List<String> generateMod = new ArrayList<>();
        private List<String> generateItem = new ArrayList<>();

        public void clear() {
            captureMod.clear();
            captureEntity.clear();
            learnMod.clear();
            learnItem.clear();
            generateMod.clear();
            generateItem.clear();
        }

        public void addToCaptureMod(String s) {
            LOGGER.info("Blacklisting capturing of all entities from {}", s);
            captureMod.add(s);
        }

        public void addToCaptureEntity(String s) {
            LOGGER.info("Blacklisting capturing of {}", s);
            captureEntity.add(s);
        }

        public void addToLearnMod(String s) {
            LOGGER.info("Blacklisting learning of all items from {}", s);
            learnMod.add(s);
        }

        public void addToLearnItem(String s) {
            LOGGER.info("Blacklisting learning of {}", s);
            learnItem.add(s);
        }

        public void addToGenerateMod(String s) {
            LOGGER.info("Blacklisting generating of all items from {}", s);
            generateMod.add(s);
        }

        public void addToGenerateItem(String s) {
            LOGGER.info("Blacklisting generating of {}", s);
            generateItem.add(s);
        }
    }

    /**
     * Can capture or learn
     *
     * Priority:
     *  Highest -> Internal complete MOD blacklisted
     *          -> Internal specific ENTITY/ITEM blacklisted
     *          -> External complete MOD blacklisted
     *          -> External specific ENTITY/ITEM blacklisted
     */
    public boolean canCaptureEntity(ResourceLocation rl) {
        if (rl == null)
            return false;

        for (String s : internalPolicy.captureMod) {
            if (rl.getNamespace().equalsIgnoreCase(s)) {
                return false;
            }
        }

        for (String s : internalPolicy.captureEntity) {
            if (s.equalsIgnoreCase(rl.toString())) {
                return false;
            }
        }

        for (String s : customPolicy.captureMod) {
            if (rl.getNamespace().equalsIgnoreCase(s)) {
                return false;
            }
        }

        for (String s : customPolicy.captureEntity) {
            if (s.equalsIgnoreCase(rl.toString())) {
                return false;
            }
        }

        return true;
    }

    public boolean canGenerateItem(ResourceLocation rl) {
        if (rl == null)
            return false;

        for (String s : customPolicy.generateMod) {
            if (rl.getNamespace().equalsIgnoreCase(s)) {
                return false;
            }
        }

        for (String s : customPolicy.generateItem) {
            if (s.equalsIgnoreCase(rl.toString())) {
                return false;
            }
        }

        return true;
    }

    public boolean canLearnItem(ResourceLocation rl) {
        if (rl == null)
            return false;

        for (String s : internalPolicy.learnMod) {
            if (rl.getNamespace().equalsIgnoreCase(s)) {
                return false;
            }
        }

        for (String s : customPolicy.learnMod) {
            if (rl.getNamespace().equalsIgnoreCase(s)) {
                return false;
            }
        }

        for (String s : internalPolicy.learnItem) {
            if (s.equalsIgnoreCase(rl.toString())) {
                return false;
            }
        }

        for (String s : customPolicy.learnItem) {
            if (s.equalsIgnoreCase(rl.toString())) {
                return false;
            }
        }

        return true;
    }

    public boolean canSimulate(ResourceLocation rl) {
        if (rl == null)
            return false;

        for (String s : PolicyConfiguration.CUSTOM_DROPS_ONLY.get()) {
            if (s.equalsIgnoreCase(rl.toString()))
                return false;
        }

        return true;
    }
}
