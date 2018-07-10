package tdk_enum.common.IO.logger.LogInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;

public class LogInfoDuplicatedSetsToExtend<T> extends AbstractLogInfo {

    Map<Set<T>, Integer> extendedSets = new HashMap<>();

    public void logSetToExtend(Set setToExetend) {
        if (!extendedSets.containsKey(setToExetend)) {
            extendedSets.put(setToExetend, 1);
        } else {
            extendedSets.put(setToExetend, extendedSets.get(setToExetend) + 1);
        }
    }

    @Override
    public void printLog() {
        File logFile = createFile("Sets_To_Extend_Duplications.csv");
        try (PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {

            String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                    "sets to extend set size", "total sets generated", "max duplications", "min duplications", "avg duplications","duplication%", "results-duplication ratio" );

            List<Integer> duplications = extendedSets.values().stream().filter(e->e>1).collect(Collectors.toList());

            String data = dataToCSV(field, type, graph, separators, results, time,
                    extendedSets.size(),
                    extendedSets.values().stream().mapToInt(Integer::intValue).sum(),
                    duplications.stream().max(Comparator.naturalOrder()).orElse(0),
                    duplications.stream().min(Comparator.naturalOrder()).orElse(0),
                    duplications.stream().mapToInt(Integer::intValue).average().orElse(0),
                    (double)duplications.stream().mapToInt(Integer::intValue).sum()/
                            extendedSets.values().stream().mapToInt(Integer::intValue).sum()*100,
                    (double)results / duplications.stream().mapToInt(Integer::intValue).sum()*100
            );

            output.println(fields);
            output.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
