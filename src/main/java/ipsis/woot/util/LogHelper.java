package ipsis.woot.util;

import ipsis.Woot;
import org.apache.logging.log4j.Level;

public class LogHelper {

    public static void error(Object object) {
        Woot.logger.log(Level.ERROR, object);
    }

    public static void info(Object object) {
        Woot.logger.log(Level.INFO, object);
    }
}
