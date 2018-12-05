package tdk_enum.ml.solvers.execution;

import java.io.File;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class FileSystemOperations {

    public static File getFile(String filePattern) {

        File ret = null;

        if (filePattern != null) {

            String targetFile = filePattern;

            if (targetFile.startsWith(".")) {
                targetFile =
                        targetFile.replaceFirst(".",System.getProperty("user.dir"));
            }
            else if (targetFile.startsWith("~")) {
                targetFile =
                        targetFile.replaceFirst("~",System.getProperty("user.home"));
            }

            if (!targetFile.startsWith("/")) {
                targetFile = System.getProperty("user.dir") + "/" + targetFile;
            }

            ret = new File(targetFile);
        }

        return ret;
    }

}
