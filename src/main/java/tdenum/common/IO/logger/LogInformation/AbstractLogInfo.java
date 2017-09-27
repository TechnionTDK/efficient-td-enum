package tdenum.common.IO.logger.LogInformation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractLogInfo implements ILogInfo {



    static String filename;
    static String logsFolder;
    static String field;
    static String type;
    static String graph;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH-mm");
    static Date testDate = new Date();
    static int separators;
    static int results;
    static long time;




    public static File createFile(String fileName) {
        StringBuilder sb = new StringBuilder();

        sb.
//                append(Paths.get("").toAbsolutePath().toString()).append(File.separator).
        append(logsFolder).append(File.separator).
                append(field).append("_").append(type).append("_").append(graph).append("_").
                append(dateFormat.format(testDate)).append(File.separator).append(fileName);

        File logFile = new File(sb.toString());
        logFile.getParentFile().mkdirs();
        try {
            logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  logFile;
    }


    public static void setLogsFolder(String logsFolder) {
        AbstractLogInfo.logsFolder = logsFolder;
    }

    public static void setField(String field) {
        AbstractLogInfo.field = field;
    }

    public static void setType(String type) {
        AbstractLogInfo.type = type;
    }

    public static void setGraph(String graph) {
        AbstractLogInfo.graph = graph;
    }

    public static void setSeparators(int separators) {
        AbstractLogInfo.separators = separators;
    }

    public static void setResults(int results) {
        AbstractLogInfo.results = results;
    }

    public static void setTime(long time) {
        AbstractLogInfo.time = time;
    }

    public static void setFilename(String filename) {
        AbstractLogInfo.filename = filename;
    }
}
