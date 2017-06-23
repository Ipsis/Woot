package ipsis.woot.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandHelper {

    public static void display(ICommandSender sender, String unlocalisedString, Object... args) {

        sender.sendMessage(new TextComponentTranslation(unlocalisedString, args));
    }
}
