package ipsis.woot.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeLog {

    private List<Changes> library = new ArrayList<>();

    public Changes addVersion(String v) {

        Changes c = new Changes(v);
        library.add(c);
        return c;

    }

    public List<Changes> getLibrary() {
        return library;
    }

    public static class Changes {

        public Map<Integer, String> featureMap = new HashMap<>();
        public Map<Integer, String> fixMap = new HashMap<>();

        public String version;
        public Changes(String version) {
            this.version = version;
        }

        private static int currFeatureId = -1;
        public void addFeature(String desc) {
            featureMap.put(currFeatureId, desc);
            currFeatureId--;
        }
        public void addFeature(int id, String desc) {
            if (id > 0)
                featureMap.put(id, desc);
            else
                addFeature(desc);
        }

        private static int currFixId = -1;
        public void addFix(String desc) {
            fixMap.put(currFixId, desc);
            currFixId--;
        }
        public void addFix(int id, String desc) {
            if (id > 0)
                fixMap.put(id, desc);
            else
                addFix(desc);
        }
    }
}
