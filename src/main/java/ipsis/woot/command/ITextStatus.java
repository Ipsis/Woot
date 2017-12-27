package ipsis.woot.command;

import net.minecraft.world.WorldServer;

import java.util.List;

public interface ITextStatus {

    // Returns a list of strings to be dumped to the user
    List<String> getStatus();
    List<String> getStatus(WorldServer worldServer);
}
