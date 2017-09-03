package tdenum.common.IO;

import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logger {


    private static Logger instance;

    private static boolean logDuplicateMIS = false;
    private static boolean logDuplicateSetsToExtend = false;
    private static boolean logDuplicatesSaturatedGraphs = false;

    private static boolean logExtendRuntime = false;
    private static boolean logForLoopsRuntime = false;

    private String filename;
    private String logsFolder;
    private String field;
    private String type;
    private String graph;
    private int separators;
    private int results;
    private long time;

    private Date testDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH-mm");

    public enum MIS_DUPLICATION_HIT
    {
        SETS_EXTENDED, SETS_NOT_EXTENDED
    }

    class DuplicateMISRecord<T>
    {


        //dvirdu-testing membership time
        long membershipTime = 0;
        //dvirdu-testing membership hit miss
        int setsExtendedHits = 0;
        int setsNotExtendedHits = 0;
        Map<Set<T>, Integer> duplicatationMap = new HashMap<>();

        Date checkStart;

        void printLog()
        {


            File logFile = createFile("MIS_Duplications.csv");
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logFile.mkdirs();
            try (PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {

                String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                        "duplication set size", "sets extended hits", "sets not extended hits", "total hits",
                        "max duplications", "min duplications", "avg duplications", "time in membership",
                        "duplication%", "results-duplication ratio");
                String data =dataToCSV(field, type, graph, separators, results, time, duplicatationMap.size(),
                        setsExtendedHits, setsNotExtendedHits, setsExtendedHits+setsNotExtendedHits,
                        duplicatationMap.values().stream().max(Comparator.naturalOrder()).get(),
                        duplicatationMap.values().stream().min(Comparator.naturalOrder()).get(),
                        duplicatationMap.values().stream().mapToInt(Integer::intValue).average().getAsDouble(),
                        membershipTime/1000, (double)duplicatationMap.size()/results*100,
                        (double)results/(setsExtendedHits+setsNotExtendedHits)*100);


                output.println(fields);
                output.println(data);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        void addDuplication(Set<T> duplication, MIS_DUPLICATION_HIT mis_duplication_hit)
        {
            if(!duplicatationMap.containsKey(duplication))
            {
                duplicatationMap.put(duplication,0);
            }
            duplicatationMap.put(duplication,duplicatationMap.get(duplication)+1);

            if (mis_duplication_hit == MIS_DUPLICATION_HIT.SETS_EXTENDED)
            {
                setsExtendedHits++;
            }
            else
            {
                setsNotExtendedHits++;
            }
        }
    }

    DuplicateMISRecord duplicatMISRecord = new DuplicateMISRecord();

    private Logger()
    {
            readProperties();
    }

    private void readProperties()
    {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            logDuplicateMIS = Boolean.valueOf(prop.getProperty("logDuplicateMIS"));
            logDuplicateSetsToExtend = Boolean.valueOf(prop.getProperty("logDuplicateSetsToExtend"));
            logDuplicatesSaturatedGraphs = Boolean.valueOf(prop.getProperty("logDuplicatesSaturatedGraphs"));
            logExtendRuntime = Boolean.valueOf(prop.getProperty("logExtendRuntime"));
            logForLoopsRuntime = Boolean.valueOf(prop.getProperty("logForLoopsRuntime"));

            logsFolder = prop.getProperty("logsFolder");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static Logger getInstance()
    {
        if (instance == null)
        {
            instance = new Logger();
        }
        return  instance;
    }

    public static void setFileName(String filename)
    {
        getInstance().filename = filename;
    }

    public static void setField(String field)
    {
        getInstance().field = field;
    }

    public static void setType(String type)
    {
        getInstance().type = type;
    }

    public static void setGraph(String graph)
    {
        getInstance().graph =graph;
    }

    public static void setSeparators(int separators)
    {
        getInstance().separators = separators;
    }

    public static void setResults(int results)
    {
        getInstance().results = results;
    }

    public static void setTime(long time)
    {
        getInstance().time = time;
    }


    public static void printLogs()
    {
        getInstance().printDuplicateMISLog();
    }

    private void printDuplicateMISLog()
    {
        if (logDuplicateMIS)
        {
            duplicatMISRecord.printLog();
        }
    }

    private File createFile(String fileName) {
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

    private void printDuplicateSetsToExtendLog()
    {

    }

    static String dataToCSV(Object... args)
    {
        StringBuilder sb = new StringBuilder();
        for (Object o :args)
        {
            sb.append(o).append(",");

        }
        return sb.toString();
    }

    public static void startMISDuplicationCheck()
    {
        if (logDuplicateMIS)
        {
            getInstance().duplicatMISRecord.checkStart = new Date();
        }

    }

    public static void finishMISDuplicationCheck()
    {
        if (logDuplicateMIS)
        {
            Date finish = new Date();
            getInstance().duplicatMISRecord.membershipTime += finish.getTime() -
                    getInstance().duplicatMISRecord.checkStart.getTime();
        }
    }

    public static void  addMISDuplication(Set duplication, MIS_DUPLICATION_HIT mis_duplication_hit)
    {
        if(logDuplicateMIS)
        {
            getInstance().duplicatMISRecord.addDuplication(duplication, mis_duplication_hit);
        }
    }







}
