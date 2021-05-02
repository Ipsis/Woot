package ipsis.woot.modules;

import java.util.ArrayList;
import java.util.List;

public class Modules {

    private final List<Module> modules = new ArrayList<>();

    public void register(Module module) {
        modules.add(module);
    }
}
