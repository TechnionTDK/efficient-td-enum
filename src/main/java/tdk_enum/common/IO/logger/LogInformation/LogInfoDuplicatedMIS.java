package tdk_enum.common.IO.logger.LogInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;
import static tdk_enum.common.IO.logger.LogInformation.LogInfoDuplicatedMIS.MIS_DUPLICATION_HIT.SETS_EXTENDED;

public class LogInfoDuplicatedMIS<T> extends AbstractLogInfo{


    public enum MIS_DUPLICATION_HIT
    {
        SETS_EXTENDED, SETS_NOT_EXTENDED
    }


    //dvirdu-testing membership time
    long membershipTime = 0;
    //dvirdu-testing membership hit miss
    int setsExtendedHits = 0;
    int setsNotExtendedHits = 0;
    Map<Set<T>, Integer> duplicatationMap = new HashMap<>();

    Date checkStart;

    @Override
    public void printLog()
    {


        File logFile = createFile("MIS_Duplications.csv");

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


    public void addDuplication(Set<T> duplication, MIS_DUPLICATION_HIT mis_duplication_hit)
    {
        if(!duplicatationMap.containsKey(duplication))
        {
            duplicatationMap.put(duplication,0);
        }
        duplicatationMap.put(duplication,duplicatationMap.get(duplication)+1);

        if (mis_duplication_hit == SETS_EXTENDED)
        {
            setsExtendedHits++;
        }
        else
        {
            setsNotExtendedHits++;
        }
    }


}
