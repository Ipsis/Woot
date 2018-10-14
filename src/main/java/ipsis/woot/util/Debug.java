package ipsis.woot.util;

import ipsis.Woot;
import ipsis.woot.util.helpers.LogHelper;
import org.apache.logging.log4j.Level;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Debug {

    // All debug disabled by default
    //private EnumSet<Group> flags = EnumSet.noneOf(Group.class);
    private EnumSet<Group> flags = EnumSet.of(Group.POLICY);

    public enum Group {
        LEARN,
        GEN_ITEMS,
        TARTARUS,
        EVENT,
        POLICY,
        BUILDING
        ;

        private String name;

        private static final Map<String, Group> ENUM_MAP;
        Group(String name) {
            this.name = name;
        }

        Group() {
            this.name = toString();
        }

        static {
            Map<String, Group> map = new ConcurrentHashMap<String, Group>();
            for (Group g : Group.values())
                map.put(g.name, g);
            ENUM_MAP = Collections.unmodifiableMap(map);
        }

        public static Group get(String name) {
            return ENUM_MAP.get(name.toUpperCase());
        }
    }

    public static final EnumSet<Group> ALL_GROUPS = EnumSet.allOf(Group.class);

    public void set(String s) {
        Group g = Group.get(s);
        if (g != null)
            flags.add(g);
    }

    public void clear(String s) {
        Group g = Group.get(s);
        if (g != null)
            flags.remove(g);
    }

    public void set(Group g) {
        flags.add(g);
    }

    public void clear(Group g) {
        flags.remove(g);
    }

    public boolean isEnabled(Group g) {
        return flags.contains(g);
    }

    public void trace(Group g,  Object object) {
        if (flags.contains(g))
            LogHelper.info(object);
    }

    public void trace(Group g, String message, Object... params) {

        if (flags.contains(g))
            Woot.logger.log(Level.INFO, message, params);
    }

    @Override
    public String toString() {
        return flags.toString();
    }
}
