package ipsis.woot.oss;

import ipsis.woot.reference.Files;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    /**
     * EnderIO/src/main/java/crazypants/util/IoUtil.java
     */
    public static String copyConfigFileFromJar(String filename, boolean replaceIfExists) throws IOException {

        if (replaceIfExists || !getConfigFile(filename).exists()) {
            // copy it from the jar file to the config dir
            IOUtils.copy(FileUtils.class.getResourceAsStream(getConfigResourcePath(filename)),
                    new FileOutputStream(getConfigFile(filename)));
        }

        return IOUtils.toString(new FileReader(getConfigFile(filename)));
    }

    public static String getFileFromJar(String filename) throws IOException {

        return IOUtils.toString(FileUtils.class.getResourceAsStream(getTextResourcePath(filename)), StandardCharsets.UTF_8);
    }

    private static String getConfigResourcePath(String filename) {

        return "/assets/woot/config/" + filename;
    }

    private static String getTextResourcePath(String filename) {

        return "/assets/woot/text/" + filename;
    }

    public static File getConfigFile(String filename) {

        return new File(Files.getConfigDirectory(), filename);
    }
}
