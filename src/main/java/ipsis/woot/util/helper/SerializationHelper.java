package ipsis.woot.util.helper;

import ipsis.woot.Woot;

import java.io.*;

/**
 * Based off Pahimar's EE3 SerializationHelper.java
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/1.9.4/src/main/java/com/pahimar/ee3/util/SerializationHelper.java
 */
public class SerializationHelper {

    public static String readJsonFile(File file) throws FileNotFoundException {

        StringBuilder jsonStringBuilder = new StringBuilder();
        if (file != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

                jsonStringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }
            }
            catch (IOException exception) {
                if (exception instanceof FileNotFoundException) {
                    throw (FileNotFoundException) exception;
                }
                else {
                    exception.printStackTrace();
                }
            }
        }
        return jsonStringBuilder.toString();
    }

    public static void writeJsonFile(File file, String fileContents) {

        if (file != null) {

            file.getParentFile().mkdirs();
            File tempFile = new File(file.getAbsolutePath() + "_tmp");

            if (tempFile.exists()) {
                tempFile.delete();
            }

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {

                bufferedWriter.write(fileContents);
                bufferedWriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            if (file.exists()) {
                file.delete();
            }

            if (file.exists()) {
                Woot.setup.getLogger().warn("Failed to delete " + file);
            } else {
                tempFile.renameTo(file);
            }
        }
    }

}
