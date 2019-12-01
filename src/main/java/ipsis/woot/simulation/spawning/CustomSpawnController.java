package ipsis.woot.simulation.spawning;

import ipsis.woot.util.FakeMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CustomSpawnController {

    public static CustomSpawnController get() { return INSTANCE; }
    static CustomSpawnController INSTANCE;
    static { INSTANCE = new CustomSpawnController(); }

    private List<AbstractMobSpawner> spawnerList = new ArrayList<>();
    private CustomSpawnController() {
        spawnerList.add(new SlimeSpawner());
        spawnerList.add(new MagmaCubeSpawner());
        spawnerList.add(new ChargedCreeperSpawner());
    }

    public void apply(LivingEntity livingEntity, FakeMob fakeMob, World world) {
        if (!fakeMob.isValid() || world == null)
            return;

        for (AbstractMobSpawner abstractMobSpawner : spawnerList)
            abstractMobSpawner.apply(livingEntity, fakeMob, world);
    }
}
