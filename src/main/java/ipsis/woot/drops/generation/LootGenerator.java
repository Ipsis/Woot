package ipsis.woot.drops.generation;

import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helpers.ConnectedCapHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LootGenerator {

    List<IGenerator> generators = new LinkedList<>();

    public void initialise() {

        generators.add(new ItemGenerator());
        // TODO different generators
    }

    public void generate(World world, Setup setup) {

        if (!setup.fakeMobKey.isValid() || setup.numMobs <= 0)
            return;

        for (IGenerator generator : generators)
            generator.generate(world, setup);

    }

    public Setup getNewSetup(FakeMobKey fakeMobKey, int looting, int numMobs, DifficultyInstance difficulty) {
        Setup setup = new Setup();
        setup.numMobs = numMobs;
        setup.fakeMobKey = fakeMobKey;
        setup.looting = looting;
        setup.difficulty = difficulty;
        return setup;
    }

    public class Setup {

        private Setup(){}

        public List<ConnectedCapHelper.ConnectedFluidHandler> fuildHandlers = new ArrayList<>();
        public List<ConnectedCapHelper.ConnectedItemHandler> itemHandlers = new ArrayList<>();
        public DifficultyInstance difficulty;
        public int numMobs = 0;
        public int looting = 0;
        public FakeMobKey fakeMobKey = new FakeMobKey();
    }
}
