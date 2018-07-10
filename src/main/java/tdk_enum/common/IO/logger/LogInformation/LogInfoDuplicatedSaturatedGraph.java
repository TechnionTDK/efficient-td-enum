package tdk_enum.common.IO.logger.LogInformation;

import tdk_enum.graph.graphs.IGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;

public class LogInfoDuplicatedSaturatedGraph extends AbstractLogInfo {

    Map<IGraph, Integer> saturatedGraphs = new HashMap<>();
    Map<IGraph, Set<Set>> generatedFromMap = new HashMap<>();

    public void logSaturatedGraph(IGraph saturatedGraph, Set generatedFrom)
    {
        if(!saturatedGraphs.containsKey(saturatedGraph))
        {
            saturatedGraphs.put(saturatedGraph, 1);
        }
        else
        {
            saturatedGraphs.put(saturatedGraph, saturatedGraphs.get(saturatedGraph)+1);
        }

        if (!generatedFromMap.containsKey(saturatedGraph))
        {
            generatedFromMap.put(saturatedGraph, new HashSet<>());
        }
        generatedFromMap.get(saturatedGraph).add(generatedFrom);
    }


    @Override
    public void printLog() {

        File logfile = createFile("Saturated_Graphs_Duplications.csv");
        try(PrintWriter output = new PrintWriter(new FileOutputStream(logfile)))
        {

            List<Integer> duplications = saturatedGraphs.values().stream().filter(e->e>1).collect(Collectors.toList());
            String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                    "generated graphs set size", "total saturated graphs generated",
                    "max duplications", "min duplications", "avg duplications",
                    "duplication%", "results-duplication ratio",
                    "graphs with more that one source set"
                    );
            String data = dataToCSV(field, type, graph, separators, results, time,
                    saturatedGraphs.size(), saturatedGraphs.values().stream().mapToInt(Integer::intValue).sum(),
                    duplications.stream().max(Comparator.naturalOrder()).orElse(0),
                    duplications.stream().min(Comparator.naturalOrder()).orElse(0),
                    duplications.stream().mapToInt(Integer::intValue).average().orElse(0),
                    (double)duplications.stream().mapToInt(Integer::intValue).sum()/
                            saturatedGraphs.values().stream().mapToInt(Integer::intValue).sum()*100,
                    (double)results / duplications.stream().mapToInt(Integer::intValue).sum()*100,
                    generatedFromMap.values().stream().filter(e->e.size()>1).collect(Collectors.toList()).size()


            );
            output.println(fields);
            output.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
