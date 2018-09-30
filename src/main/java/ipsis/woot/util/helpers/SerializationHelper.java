package ipsis.woot.util.helpers;

import java.io.*;

/**
 * Based off Pahimar's EE3 SerializationHelper.java
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/1.9.4/src/main/java/com/pahimar/ee3/util/SerializationHelper.java
 */

public class SerializationHelper {

    public static String readJsonFile(File file) throws FileNotFoundException {

        StringBuilder sb = new StringBuilder();
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
            } catch (IOException e) {
                if (e instanceof FileNotFoundException)
                    throw (FileNotFoundException)e;
                else
                    e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static void writeJsonFile(File file, String data) {

        if (file == null)
            return;

        file.getParentFile().mkdirs();
        File tmpFile = new File(file.getAbsolutePath() + "_tmp");

        if (tmpFile.exists())
            tmpFile.delete();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists())
            file.delete();

        if (!file.exists())
            tmpFile.renameTo(file);
    }
}
