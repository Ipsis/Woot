package ipsis.woot.oss;

import ipsis.woot.reference.Reference;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {

    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    /**
     * Pahimar's logging
     * https://github.com/pahimar/Equivalent-Exchange-3
     * Equivalent-Exchange-3/src/main/java/com/pahimar/ee3/util/LogHelper.java
     */
    public static void log(Level logLevel, Object object) {

        LOGGER.log(logLevel, Reference.MOD_NAME + ": "+ object);
    }

    public static void all(Object object) {
        log(Level.ALL, object);
    }

    public static void debug(Object object) {
        log(Level.DEBUG, object);
    }

    public static void error(Object object) {
        log(Level.ERROR, object);
    }

    public static void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public static void info(Object object) {
        log(Level.INFO, object);
    }

    public static void off(Object object) {
        log(Level.OFF, object);
    }

    public static void trace(Object object) {
        log(Level.TRACE, object);
    }

    public static void warn(Object object) {
        log(Level.WARN, object);
    }
}
