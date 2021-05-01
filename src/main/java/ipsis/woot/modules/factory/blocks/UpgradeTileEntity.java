package ipsis.woot.modules.factory.blocks;

import ipsis.woot.Woot;
import ipsis.woot.advancements.Advancements;
import ipsis.woot.modules.factory.FactorySetup;
import ipsis.woot.modules.factory.perks.Perk;
import ipsis.woot.modules.factory.items.PerkItem;
import ipsis.woot.modules.factory.multiblock.MultiBlockTileEntity;
import ipsis.woot.modules.factory.multiblock.MultiBlockTracker;
import ipsis.woot.util.WootDebug;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeTileEntity extends MultiBlockTileEntity implements WootDebug {

    public UpgradeTileEntity() {
        super(FactorySetup.FACTORY_UPGRADE_BLOCK_TILE.get());
    }

    public boolean tryAddUpgrade(World world, PlayerEntity playerEntity, BlockState state, Perk type) {

        if (state.getValue(UpgradeBlock.UPGRADE) == Perk.EMPTY) {
            // Add to empty must be level 1
            if (Perk.LEVEL_1_PERKS.contains(type)) {
                world.setBlock(worldPosition,
                        state.setValue(UpgradeBlock.UPGRADE, type), 2);
                glue.onGoodbye();
                MultiBlockTracker.get().addEntry(world, worldPosition);
                Woot.setup.getLogger().debug("tryAddUpgrade: added {}", type);
                if (playerEntity instanceof ServerPlayerEntity)
                    Advancements.APPLY_PERK_TRIGGER.trigger((ServerPlayerEntity) playerEntity, type);
                return true;
            } else {
                playerEntity.displayClientMessage(new TranslationTextComponent("chat.woot.perk.fail.0"), false);
                return false;
            }
        } else {
            // Add to non-empty, must be same type and level + 1
            Perk upgrade = getBlockState().getValue(UpgradeBlock.UPGRADE);
            Perk.Group currType = Perk.getGroup(upgrade);
            Perk.Group addType = Perk.getGroup(type);
            int currLevel = Perk.getLevel(upgrade);
            int addLevel = Perk.getLevel(type);
            if (currType != addType) {
                playerEntity.displayClientMessage(new TranslationTextComponent("chat.woot.perk.fail.1"), false);
                return false;
            }

            if (currLevel == 3) {
                playerEntity.displayClientMessage(new TranslationTextComponent("chat.woot.perk.fail.2"), false);
                return false;
            }

            if (currLevel == addLevel) {
                playerEntity.displayClientMessage(new TranslationTextComponent("chat.woot.perk.fail.4"), false);
                return false;
            }

            if (currLevel + 1 != addLevel) {
                playerEntity.displayClientMessage(new TranslationTextComponent("chat.woot.perk.fail.4", currLevel + 1), false);
                return false;
            }

            world.setBlock(worldPosition,
                    state.setValue(UpgradeBlock.UPGRADE, type), 2);
            glue.onGoodbye();
            MultiBlockTracker.get().addEntry(world, worldPosition);
            Woot.setup.getLogger().debug("tryAddUpgrade: added {}", type);
            return true;
        }
    }

    public void dropItems(BlockState state, World world, BlockPos pos) {
        Perk upgrade = state.getValue(UpgradeBlock.UPGRADE);
        if (upgrade == Perk.EMPTY)
            return;

        int currLevel = Perk.getLevel(upgrade);
        for (int i = 1; i <= 3; i++) {
            if (i <= currLevel) {
                Perk.Group type = Perk.getGroup(upgrade);
                ItemStack itemStack = PerkItem.getItemStack(type, i);
                if (!itemStack.isEmpty()) {
                    itemStack.setCount(1);
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                }
            }
        }
    }

    public @Nullable
    Perk getUpgrade(BlockState state) {
        return state.getValue(UpgradeBlock.UPGRADE);
    }

    /**
     * WootDebug
     */
    @Override
    public List<String> getDebugText(List<String> debug, ItemUseContext itemUseContext) {
        debug.add("====> UpgradeTileEntity");
        debug.add("      hasMaster: " + glue.hasMaster());
        debug.add("      upgrade: " + level.getBlockState(worldPosition).getValue(UpgradeBlock.UPGRADE));
        return debug;
    }
}
