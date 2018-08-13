package tdk_enum.legacy;

import tdk_enum.common.IO.*;
import tdk_enum.common.IO.logger.Logger;
import tdk_enum.common.configuration.config_types.WhenToPrint;
import tdk_enum.legacy.IO.result_handler.LegacyResultHandler;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import static tdk_enum.common.configuration.config_types.WhenToPrint.*;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.*;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.*;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.*;

public class LegacyMinTriangulationEnumeration {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("No graph file specified");
            return;
        }

        InputFile inputFile = new InputFile(args[0]);
        IGraph g = GraphReader.read(inputFile.getPath());

        boolean isTimeLimited = false;
        int timeLimitInSeconds = -1;
        WhenToPrint print = NEVER;
        String algorithm = "";
        TriangulationAlgorithm heuristic = MCS_M;
        TriangulationScoringCriterion trianguationsOrder = NONE;
        SeparatorsScoringCriterion separatorsOrder = UNIFORM;

        for (int i = 1; i < args.length; i++) {
            String[] arguments = args[i].split("=");
            String flagName = arguments[0];
            String flagValue = arguments[1];
            switch (flagName) {
                case "time_limit": {
                    timeLimitInSeconds = Integer.parseInt(flagValue);
                    if (timeLimitInSeconds >= 0) {
                        isTimeLimited = true;
                    }
                    break;
                }
                case "print": {
                    switch (flagValue) {
                        case "all": {
                            print = ALWAYS;
                            break;

                        }
                        case "none":
                        {
                            print = NEVER;
                            break;
                        }
                        case "improved": {
                            print = IF_IMPROVED;
                            break;
                        }
                    }
                    break;
                }

                case "alg": {
                    algorithm = algorithm + "." + flagValue;
                    switch (flagValue) {
                        case "mcs": {
                            heuristic = MCS_M;
                            break;
                        }
                        case "degree": {
                            heuristic = MIN_DEGREE_LB_TRIANG;
                            break;
                        }
                        case "fill": {
                            heuristic = MIN_FILL_LB_TRIANG;
                            break;
                        }
                        case "initialDegree": {
                            heuristic = INITIAL_DEGREE_LB_TRIANG;
                            break;
                        }
                        case "initialFill": {
                            heuristic = INITIAL_FILL_LB_TRIANG;
                            break;
                        }
                        case "lb": {
                            heuristic = LB_TRIANG;
                            break;

                        }
                        case "combined": {
                            heuristic = COMBINED;
                            break;
                        }
                        case "separators": {
                            heuristic = SEPARATORS;
                            break;
                        }
                        default: {
                            System.out.println("Triangulation algorithm not recognized");
                            return;
                        }
                    }
                    break;
                }
                case "t_order": {
                    algorithm = algorithm + "." + flagValue;
                    switch (flagValue) {
                        case "fill": {
                            trianguationsOrder = FILL;
                            break;
                        }
                        case "width": {
                            trianguationsOrder = WIDTH;
                            break;
                        }
                        case "difference": {
                            trianguationsOrder = DIFFERENECE;
                            break;
                        }
                        case "sepsize": {
                            trianguationsOrder = MAX_SEP_SIZE;
                            break;
                        }
                        case "none": {
                            trianguationsOrder = NONE;
                            break;
                        }
                        default: {
                            System.out.println("Triangulation scoring criterion not recognized");
                            return;
                        }
                    }
                    break;
                }

                case "s_order": {
                    algorithm = algorithm + "." + flagValue;
                    switch (flagValue) {
                        case "size": {
                            separatorsOrder = ASCENDING_SIZE;
                            break;
                        }
                        case "none": {
                            separatorsOrder = UNIFORM;
                            break;
                        }
                        case "fill": {
                            separatorsOrder = FILL_EDGES;
                            break;
                        }
                        default: {
                            System.out.println("Seperators scoring criterion not recognized");
                            return;
                        }
                    }
                }
            }
        }

        PrintWriter detailedOutput = null;
        if (print != NEVER) {
            algorithm = algorithm != "" ? algorithm : "mcs";
            StringBuilder sb = new StringBuilder().
                    append(inputFile.getField()).append(".").append(inputFile.getType()).append(".").
                    append(inputFile.getName()).append(".").append(algorithm).append(".csv");
            File file = new File(sb.toString());
//            file.getParentFile().mkdirs();
            try {
                detailedOutput = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        File summaryFile = new File("summary.csv");
        PrintWriter summaryOutput = null;
        if (!summaryFile.exists()) {
            try {
                summaryOutput = new PrintWriter(summaryFile);
                printSummaryHeader(summaryOutput);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                summaryOutput = new PrintWriter(new FileOutputStream(summaryFile, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        Logger.setField(inputFile.getField());
        Logger.setType(inputFile.getType());
        Logger.setGraph(inputFile.getName());
        Logger.setFileName(inputFile.getPath());


        StringBuilder sb = new StringBuilder();
        sb.
                append("Starting enumeration for ").append(inputFile.getField()).append("\\").
                append(inputFile.getType()).append("\\").
                append(inputFile.getName());
        System.out.println(sb.toString());
        Date statTime = new Date();
        LegacyResultHandler results = new LegacyResultHandler(g, detailedOutput, print);
        double totalTimeInSeconds = 0;
        boolean timeLimitExeeded = false;

        LegacyMinimalTriangulationsEnumerator enumerator = new LegacyMinimalTriangulationsEnumerator(g, trianguationsOrder, separatorsOrder, heuristic);
        while (enumerator.hasNext())
        {
            IChordalGraph triangulation = enumerator.next();
//            System.out.println(triangulation);
            results.newResult(triangulation);
            totalTimeInSeconds = (double)(new Date().getTime() - statTime.getTime()) / 1000;
            if (isTimeLimited && totalTimeInSeconds >= timeLimitInSeconds)
            {
                timeLimitExeeded = true;
                break;
            }
        }


        if (print != NEVER)
        {
            detailedOutput.close();
        }

        int separators = enumerator.getNumberOfMinimalSeperatorsGenerated();
        printSummary(summaryOutput, inputFile, g, timeLimitExeeded, totalTimeInSeconds, algorithm, separators, results);
        summaryOutput.close();

        if (timeLimitExeeded)
        {
            System.out.println("Time limit reached");
        }
        else
        {
            System.out.println("All minimal triangulations were generated!");
        }

        results.printReadableSummary();
        sb = new StringBuilder();
        sb.
                append("The graph has ").append(g.getNumberOfNodes()).append(" nodes and ").
                append(g.getNumberOfEdges()).append(" edges. ").
                append(separators).append(" minimal separators were generated in the process.");
        System.out.println(sb.toString());


        Logger.setResults(results.getResultsFound());
        Logger.setTime((long)totalTimeInSeconds);
        Logger.setSeparators(separators);

        Logger.printLogs();



    }

    private static void printSummaryHeader(PrintWriter summaryOutput) {
        summaryOutput.print("Field,Type,Graph,Nodes,Edges,Finished,Time,Algorithm,Separators generated,");
        LegacyResultHandler.printTableSummaryHeader(summaryOutput);
    }

    private static void printSummary(PrintWriter summaryOutput, InputFile inputFile, IGraph g,
                                     boolean timeLimitExeeded, double totalTimeInSeconds, String algorithm,
                                     int separators, LegacyResultHandler results)
    {
        StringBuilder sb = new StringBuilder();
        sb.
                append(inputFile.getField()).append(", ").append(inputFile.getType()).append(", ").
                append(inputFile.getName()).append(", ").append(g.getNumberOfNodes()).append(", ").
                append(g.getNumberOfEdges()).append(", ").append(timeLimitExeeded? "No" : "Yes").append(", ").
                append(totalTimeInSeconds).append(", ").append(algorithm).append(", ").append(separators).append(",");
        summaryOutput.print(sb.toString());
        results.printTableSummary(summaryOutput);
    }

}
