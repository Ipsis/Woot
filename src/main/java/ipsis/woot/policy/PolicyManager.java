package ipsis.woot.policy;

import ipsis.Woot;
import ipsis.woot.util.Debug;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public class PolicyManager implements IPolicy {

    private Policy internalPolicy = new Policy();
    private Policy externalPolicy = new Policy();

    public void init() {
        loadInternal();
    }

    private void loadInternal() {
        for (String s : InternalPolicy.NO_CAPTURE_FROM_MOD) {
            Woot.logger.log(Level.INFO, "Internally blacklist mod for entity capture: " + s);
            internalPolicy.blacklistCaptureFromMod(s);
        }

        for (String s : InternalPolicy.NO_LEARN_FROM_MOD) {
            Woot.logger.log(Level.INFO, "Internally blacklist mod for learning: " + s);
            internalPolicy.blacklistLearnFromMod(s);
        }

        for (String s : InternalPolicy.NO_CAPTURE_ENTITY) {
            Woot.logger.log(Level.INFO, "Internally blacklist entity for capture: " + s);
            internalPolicy.blacklistCaptureEntity(s);
        }

        for (String s : InternalPolicy.NO_LEARN) {
            Woot.logger.log(Level.INFO, "Internally blacklist item for learning: " + s);
            internalPolicy.blacklistLearnItem(s);
        }

    }

    private class Policy {

        /**
         * Mod restrictions
         *
         * There are learn and drop blacklists, since there may be a perfectly sensible drop
         * that we can learn, but external policy dictates that we don't drop it.
         */
        private List<String> captureFromModBlacklist = new ArrayList<>();
        private List<String> learnFromModBlacklist = new ArrayList<>();
        private List<String> dropFromModBlacklist = new ArrayList<>();

        /**
         * Entity restrictions
         */
        private List<String> captureEntityBlacklist = new ArrayList<>();

        /**
         * Item restrictions
         */
        private List<String> learnItemBlacklist = new ArrayList<>();
        private List<String> dropItemBlacklist = new ArrayList<>();

        public void blacklistCaptureFromMod(String modid) {
            captureFromModBlacklist.add(modid);
        }

        public void blacklistLearnFromMod(String modid) {
            learnFromModBlacklist.add(modid);
        }

        public void blacklistDropFromMod(String modid) {
            dropFromModBlacklist.add(modid);
        }

        public void blacklistCaptureEntity(String entity) {
            captureEntityBlacklist.add(entity);
        }

        public void blacklistLearnItem(String item) {
            learnItemBlacklist.add(item);
        }

        public void blacklistDropItem(String item) {
            dropItemBlacklist.add(item);
        }
    }

    /**
     * IPolicy
     */
    @Override
    public boolean canCaptureMob(ResourceLocation resourceLocation) {
        /**
         * Highest priority
         * Internal
         *     Complete MOD blacklisted
         *     Specific entity blacklisted
         * Repeat for external
         */

        if (resourceLocation == null)
            return false;

        if (internalPolicy.captureFromModBlacklist.contains(resourceLocation.getResourceDomain())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canCaptureMob: mod %s blacklisted (internal)", resourceLocation.getResourceDomain());
            return false;
        }

        if (internalPolicy.captureEntityBlacklist.contains(resourceLocation.getResourcePath())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canCaptureMob: entity %s blacklisted (internal)", resourceLocation.getResourcePath());
            return false;
        }

        if (externalPolicy.captureFromModBlacklist.contains(resourceLocation.getResourceDomain())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canCaptureMob: mod %s blacklisted (external)", resourceLocation.getResourceDomain());
            return false;
        }

        if (externalPolicy.captureEntityBlacklist.contains(resourceLocation.getResourcePath())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canCaptureMob: entity %s blacklisted (external)", resourceLocation.getResourcePath());
            return false;
        }

        return true;
    }

    @Override
    public boolean canCaptureMob(EntityLiving entityLiving) {

        if (entityLiving == null)
            return false;

        ResourceLocation resourceLocation = EntityList.getKey(entityLiving);
        if (resourceLocation == null)
            return false;

        return canCaptureMob(resourceLocation);
    }

    @Override
    public boolean canLearnItem(ItemStack itemStack) {

        if (itemStack == null)
            return false;

        ResourceLocation resourceLocation = itemStack.getItem().getRegistryName();
        if (resourceLocation == null)
            return false;

        if (internalPolicy.learnFromModBlacklist.contains(resourceLocation.getResourceDomain())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canLearnItem: mod %s blacklisted (internal)", resourceLocation.getResourceDomain());
            return false;
        }

        if (internalPolicy.learnItemBlacklist.contains(resourceLocation.getResourcePath())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canLearnItem: item %s blacklisted (internal)", resourceLocation.getResourcePath());
            return false;
        }

        if (externalPolicy.learnFromModBlacklist.contains(resourceLocation.getResourceDomain())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canLearnItem: mod %s blacklisted (external)", resourceLocation.getResourceDomain());
            return false;
        }

        if (externalPolicy.learnItemBlacklist.contains(resourceLocation.getResourcePath())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canLearnItem: item %s blacklisted (external)", resourceLocation.getResourcePath());
            return false;
        }

        return true;
    }

    @Override
    public boolean canDropItem(ItemStack itemStack) {

        if (itemStack == null)
            return false;

        ResourceLocation resourceLocation = itemStack.getItem().getRegistryName();
        if (resourceLocation == null)
            return false;

        if (externalPolicy.dropFromModBlacklist.contains(resourceLocation.getResourceDomain())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canDropItem: mod %s blacklisted (external)", resourceLocation.getResourceDomain());
            return false;
        }

        if (externalPolicy.dropItemBlacklist.contains(resourceLocation.getResourcePath())) {
            if (Woot.debugging.isEnabled(Debug.Group.POLICY))
                Woot.debugging.trace(Debug.Group.POLICY, "canDropItem: item %s blacklisted (external)", resourceLocation.getResourcePath());
            return false;
        }

        return false;
    }
}
