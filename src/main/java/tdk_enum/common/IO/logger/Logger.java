package tdk_enum.common.IO.logger;

import tdk_enum.common.IO.logger.LogInformation.*;
import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.graphs.IGraph;

import java.util.*;

public class Logger {


    private static Logger instance = null;



    private static boolean logDuplicateMIS = false;
    private static boolean logDuplicateSetsToExtend = false;
    private static boolean logDuplicatesSaturatedGraphs = false;
    private static boolean logResultData = false;
    private static boolean logWidthPerTime = false;

    private static boolean logCache = false;


    LogInfoDuplicatedMIS logInfoDuplicatedMIS = new LogInfoDuplicatedMIS();
    LogInfoDuplicatedSetsToExtend logInfoDuplicatedSetsToExtend = new LogInfoDuplicatedSetsToExtend();
    LogInfoDuplicatedSaturatedGraph logInfoDuplicatedSaturatedGraph = new LogInfoDuplicatedSaturatedGraph();
    LogInfoDuplicatedResultsRootCause logInfoDuplicatedResultsRootCause = new LogInfoDuplicatedResultsRootCause();
    LogInfoWidthPerTime logInfoWidthPerTime = new LogInfoWidthPerTime();
    LogInfoCache logInfoCache = new LogInfoCache();



    private Logger()
    {
            readProperties();
    }

    private void readProperties()
    {
        Properties prop = TDEnumFactory.getProperties();
        logDuplicateMIS = Boolean.valueOf(prop.getProperty("logDuplicateMIS"));
        logDuplicateSetsToExtend = Boolean.valueOf(prop.getProperty("logDuplicateSetsToExtend"));
        logDuplicatesSaturatedGraphs = Boolean.valueOf(prop.getProperty("logDuplicatesSaturatedGraphs"));
        logResultData = Boolean.valueOf(prop.getProperty("logResultData"));
        logWidthPerTime = Boolean.valueOf(prop.getProperty("logWidthPerTime"));

        logCache =Boolean.valueOf(prop.getProperty("logCache"));
        AbstractLogInfo.setLogsFolder(prop.getProperty("logsFolder"));

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
        AbstractLogInfo.setFilename(filename);
    }

    public static void setField(String field)
    {
        AbstractLogInfo.setField(field);
    }

    public static void setType(String type)
    {
        AbstractLogInfo.setType(type);
    }

    public static void setGraph(String graph)
    {
        AbstractLogInfo.setGraph(graph);
    }

    public static void setSeparators(int separators)
    {
        AbstractLogInfo.setSeparators(separators);
    }

    public static void setResults(int results)
    {
        AbstractLogInfo.setResults(results);
    }

    public static void setTime(long time)
    {
        AbstractLogInfo.setTime(time);
    }


    public static void init()
    {
        getInstance();
    }

    public static void printLogs()
    {
        getInstance().printDuplicateMISLog();
        getInstance().printDuplicateSetsToExtendLog();
        getInstance().printDuplicatedSaturatedGraphs();
        getInstance().printDuplicatedResultsRootCause();
        getInstance().printWidthPerTime();
        getInstance().printCacheLog();
    }

    private void printCacheLog() {

        if (logCache)
        {
            logInfoCache.printLog();
        }
    }

    private void printWidthPerTime() {
        if(logWidthPerTime)
        {
            logInfoWidthPerTime.printLog();
        }
    }

    private void printDuplicatedResultsRootCause() {
        if(logResultData)
        {
            logInfoDuplicatedResultsRootCause.printLog();
        }
    }

    private void printDuplicateMISLog()
    {
        if (logDuplicateMIS)
        {
            logInfoDuplicatedMIS.printLog();
        }
    }


    private void printDuplicateSetsToExtendLog()
    {
        if(logDuplicateSetsToExtend)
        {
            logInfoDuplicatedSetsToExtend.printLog();
        }
    }

    private void printDuplicatedSaturatedGraphs()
    {
        if(logDuplicatesSaturatedGraphs)
        {
            logInfoDuplicatedSaturatedGraph.printLog();
        }
    }


    public static void  addMISDuplication(Set duplication, LogInfoDuplicatedMIS.MIS_DUPLICATION_HIT mis_duplication_hit)
    {
        if(logDuplicateMIS)
        {
            getInstance().logInfoDuplicatedMIS.addDuplication(duplication, mis_duplication_hit);
        }
    }

    public static void addSetToExtend(Set setToExtend)
    {
        if(logDuplicateSetsToExtend)
        {
            getInstance().logInfoDuplicatedSetsToExtend.logSetToExtend(setToExtend);
        }
    }


    public static void addSaturatedGraph(IGraph graph, Set generatedFrom)
    {
        if(logDuplicatesSaturatedGraphs)
        {
            getInstance().logInfoDuplicatedSaturatedGraph.logSaturatedGraph(graph,generatedFrom);
        }
    }




    public static void logResultData(Set result, Set sourceMIS, Object v, Set jv )
    {
        if (logResultData)
        {
            getInstance().logInfoDuplicatedResultsRootCause.logResultData(result, sourceMIS, v, jv);
        }
    }


    public static void logWidthPerTime(int treeWidth, long milis) {
        if(logWidthPerTime)
        {
            getInstance().logInfoWidthPerTime.logWidthTime(treeWidth,milis);
        }
    }

    public static void logCacheHit(Set hit,int distance)
    {
        if(logCache)
        {
            getInstance().logInfoCache.logHit(hit,distance);
        }
    }
}
