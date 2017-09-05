package tdenum.common.IO;

import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.AlgorithmStep;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Logger {


    private static Logger instance;

    private static boolean logDuplicateMIS = false;
    private static boolean logDuplicateSetsToExtend = false;
    private static boolean logDuplicatesSaturatedGraphs = false;

    private static boolean logExtendRuntime = false;
    private static boolean logForLoopsRuntime = false;

    private static boolean logResultData = false;

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


    class DuplicateSetsToExtend<T> {
        Map<Set<T>, Integer> extendedSets = new HashMap<>();

        void logSetToExtend(Set setToExetend) {
            if (!extendedSets.containsKey(setToExetend)) {
                extendedSets.put(setToExetend, 1);
            } else {
                extendedSets.put(setToExetend, extendedSets.get(setToExetend) + 1);
            }
        }

        void printLog() {
            File logFile = createFile("Sets_To_Extend_Duplications.csv");
            try (PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {

                String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                        "sets to extend set size", "total sets generated", "max duplications", "min duplications", "avg duplications","duplication%", "results-duplication ratio" );

                List<Integer> duplications = extendedSets.values().stream().filter(e->e>1).collect(Collectors.toList());

                String data = dataToCSV(field, type, graph, separators, results, time,
                        extendedSets.size(),
                        extendedSets.values().stream().mapToInt(Integer::intValue).sum(),
                        duplications.stream().max(Comparator.naturalOrder()).get(),
                        duplications.stream().min(Comparator.naturalOrder()).get(),
                        duplications.stream().mapToInt(Integer::intValue).average().getAsDouble(),
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

    DuplicateSetsToExtend duplicateSetsToExtend = new DuplicateSetsToExtend();


    class DuplicatedSaturatedGraphs
    {
        Map<IGraph, Integer> saturatedGraphs = new HashMap<>();
        Map<IGraph, Set<Set>> generatedFromMap = new HashMap<>();

        void logSaturatedGraph(IGraph saturatedGraph, Set generatedFrom)
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



        void printLog()
        {
            File logfile = createFile("Saturated_Graphs_Duplications.csv");
            try(PrintWriter output = new PrintWriter(new FileOutputStream(logfile)))
            {

                List<Integer> duplications = saturatedGraphs.values().stream().filter(e->e>1).collect(Collectors.toList());
                String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                                            "generated graphs set size", "total saturated graphs generated",
                                            "max duplications", "min duplications", "avg duplications",
                                            "duplication%", "results-duplication ratio",
                                            "graphs with more that one source set",
                                            "graphs with duplicated source set");
                String data = dataToCSV(field, type, graph, separators, results, time,
                                        saturatedGraphs.size(), saturatedGraphs.values().stream().mapToInt(Integer::intValue).sum(),
                                        duplications.stream().max(Comparator.naturalOrder()).get(),
                                        duplications.stream().min(Comparator.naturalOrder()).get(),
                                        duplications.stream().mapToInt(Integer::intValue).average().getAsDouble(),
                                        (double)duplications.stream().mapToInt(Integer::intValue).sum()/
                                                saturatedGraphs.values().stream().mapToInt(Integer::intValue).sum()*100,
                                        (double)results / duplications.stream().mapToInt(Integer::intValue).sum()*100,
                                        generatedFromMap.values().stream().filter(e->e.size()>1).collect(Collectors.toList()).size(),
                                        generatedFromMap.values().stream().filter(e-> {
                                            for(Set s : e)
                                            {
                                               if( duplicateSetsToExtend.extendedSets.containsKey(s))
                                               {
                                                   return true;
                                               }
                                            }
                                            return false;
                                        })
                                            .collect(Collectors.toList()).size()

                        );
                output.println(fields);
                output.println(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    DuplicatedSaturatedGraphs duplicatedSaturatedGraphs = new DuplicatedSaturatedGraphs();

    class ExtendCallTiming
    {
        long startTime;
        List<Long> callTimes = new ArrayList<>();

        void startCall()
        {

            startTime = System.nanoTime();
        }

        void finishCall()
        {
            callTimes.add(TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startTime));
        }


        void printLog()
        {



          String fields = dataToCSV("total calls", "avarage call time (ns)", "max call time (ns)",
                  "min call time ");
          String data = dataToCSV(callTimes.size(), callTimes.stream().mapToLong(Long::longValue).average().getAsDouble(),
                   callTimes.stream().max(Comparator.naturalOrder()).get(),
                  callTimes.stream().min(Comparator.naturalOrder()).get());




            File logFile = createFile("Extend_Call_Times_Summary.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            fields = dataToCSV("call, time(ns)");
            StringBuilder sb = new StringBuilder();
            for (int i =0; i< callTimes.size(); i++)
            {
                sb.append(i+1).append(",").append(callTimes.get(i)).append(System.lineSeparator());
            }
            data = sb.toString();

            logFile = createFile("Extend_Call_Times.csv");
            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    ExtendCallTiming extendCallTiming = new ExtendCallTiming();


    class ForLoopsRuntime
    {

        class Phase
        {
            AlgorithmStep algorithmStep;
            long duration;
            int results;

            public Phase(AlgorithmStep algorithmStep, long duration, int results) {
                this.algorithmStep = algorithmStep;
                this.duration = duration;
                this.results = results;
            }

            public AlgorithmStep getAlgorithmStep() {
                return algorithmStep;
            }

            public long getDuration() {
                return duration;
            }

            public int getResults() {
                return results;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Phase)) return false;

                Phase phase = (Phase) o;

                if (getDuration() != phase.getDuration()) return false;
                if (getResults() != phase.getResults()) return false;
                return getAlgorithmStep() == phase.getAlgorithmStep();
            }

            @Override
            public int hashCode() {
                int result = getAlgorithmStep().hashCode();
                result = 31 * result + (int) (getDuration() ^ (getDuration() >>> 32));
                result = 31 * result + getResults();
                return result;
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Phase{");
                sb.append("algorithmStep=").append(algorithmStep);
                sb.append(", duration=").append(duration);
                sb.append(", results=").append(results);
                sb.append('}');
                return sb.toString();
            }

            public String toCsv()
            {
                StringBuilder sb = new StringBuilder();
                sb.append(algorithmStep).append(",").append(duration).append(",").append(results);
                return sb.toString();
            }
        }

        List<Long> durations = new ArrayList<>();

        Map<Integer, Long> setIterationsDurations = new HashMap<>();
        Map<Integer, Long> nodesIterationsDurations = new HashMap<>();
        Map<Integer, List> setIterationResultsTime = new HashMap<>();
        Map<Integer, List> nodesIterationsResultsTime = new HashMap<>();

        Map<Integer, Phase> phases = new HashMap<>();


        long startTime;


        void startLoop()
        {
            startTime = System.nanoTime();
        }

        void newResult()
        {
            durations.add( TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
        }

        void stopLoop(AlgorithmStep algorithmStep)
        {

            long totalDuration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            if(algorithmStep == AlgorithmStep.ITERATING_SETS)
            {
                setIterationsDurations.put(setIterationsDurations.size()+1, totalDuration);
                setIterationResultsTime.put(setIterationResultsTime.size()+1, new ArrayList(durations));
            }
            else
            {
                nodesIterationsDurations.put(nodesIterationsDurations.size()+1, totalDuration);
                nodesIterationsResultsTime.put(nodesIterationsResultsTime.size()+1, new ArrayList(durations));
            }

            Phase phase = new Phase(algorithmStep, totalDuration, durations.size());
            phases.put(phases.size()+1, phase);
            durations.clear();
        }

        void printLog()
        {
            String fields = dataToCSV("sets iteration phases", "nodes iterations phases",
                    "avg sets iteration phase time",
                    "avg nodes iteration phase time",
                    "max sets iteration phase time",
                    "max nodes iteration phase time",
                    "min sets iteration phase time",
                    "min nodes iteration phase time",
                    "avg sets iteration phase results",
                    "avg nodes iteration phase results",
                    "max sets iteration phase results",
                    "max nodes iteration phase results",
                    "min sets iteration phase results",
                    "min nodes iteration phase results");

            String data = dataToCSV(setIterationsDurations.size(), nodesIterationsDurations.size(),
                    setIterationsDurations.values().stream().mapToLong(Long::longValue).average().getAsDouble(),
                    nodesIterationsDurations.values().stream().mapToLong(Long::longValue).average().getAsDouble(),
                    setIterationsDurations.values().stream().max(Comparator.naturalOrder()).get(),
                    nodesIterationsDurations.values().stream().max(Comparator.naturalOrder()).get(),
                    setIterationsDurations.values().stream().min(Comparator.naturalOrder()).get(),
                    nodesIterationsDurations.values().stream().min(Comparator.naturalOrder()).get(),
                    setIterationResultsTime.values().stream().mapToInt(e->e.size()).average().getAsDouble(),
                    nodesIterationsResultsTime.values().stream().mapToInt(e->e.size()).average().getAsDouble(),
                    setIterationResultsTime.values().stream().mapToInt(e->e.size()).max().getAsInt(),
                    nodesIterationsResultsTime.values().stream().mapToInt(e->e.size()).max().getAsInt(),
                    setIterationResultsTime.values().stream().mapToInt(e->e.size()).min().getAsInt(),
                    nodesIterationsResultsTime.values().stream().mapToInt(e->e.size()).min().getAsInt());

            File logFile = createFile("Iterations_Phases_Summary.csv");


            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            fields = "phase, duration(ms), results,";

            StringBuilder sb = new StringBuilder();
            for (Integer phase : setIterationsDurations.keySet().stream().sorted().collect(Collectors.toList()))
            {
                sb.append(phase).append(",").append( setIterationsDurations.get(phase)).append(",").
                        append( setIterationResultsTime.get(phase).size()).append(System.lineSeparator());
            }
            data = sb.toString();

            logFile = createFile("Sets_Iterations_Phases.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            sb = new StringBuilder();
            for (Integer phase : nodesIterationsDurations.keySet().stream().sorted().collect(Collectors.toList()))
            {
                sb.append(phase).append(",").append( nodesIterationsDurations.get(phase)).append(",").
                        append( nodesIterationsResultsTime.get(phase).size()).append(System.lineSeparator());
            }
            data = sb.toString();

            logFile = createFile("Nodes_Iterations_Phases.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            fields = "phase, type, duration, results";
            sb = new StringBuilder();
            for (Integer phase : phases.keySet().stream().sorted().collect(Collectors.toList()))
            {
                sb.append(phase).append(",").append(phases.get(phase).toCsv()).append(System.lineSeparator());
            }

            data = sb.toString();

            logFile = createFile("General_Iterations_Phases.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            fields = "iteration,result time(ms), result duration(ms)";

            sb = new StringBuilder();
            List<Long> bestPhase = setIterationResultsTime.values().stream().filter(e->e.size() ==
                    setIterationResultsTime.values().stream().mapToInt(e1->e1.size()).max().getAsInt()).
                    collect(Collectors.toList()).get(0);

            for (int i = 0; i < bestPhase.size(); i++)
            {
                sb.append(i+1).append(",").append(bestPhase.get(i));
                if(i!=0)
                {
                    sb.append(",").append(bestPhase.get(i)-bestPhase.get(i-1));
                }
                else
                {
                    sb.append(",").append(bestPhase.get(i));
                }
                sb.append(System.lineSeparator());
            }

            data = sb.toString();

            logFile = createFile("Best_Sets_Iteration_Phase.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            sb = new StringBuilder();
           bestPhase = nodesIterationsResultsTime.values().stream().filter(e->e.size() ==
                   nodesIterationsResultsTime.values().stream().mapToInt(e1->e1.size()).max().getAsInt()).
                    collect(Collectors.toList()).get(0);


            for (int i = 0; i < bestPhase.size(); i++)
            {
                sb.append(i+1).append(",").append(bestPhase.get(i));
                if(i!=0)
                {
                    sb.append(",").append(bestPhase.get(i)-bestPhase.get(i-1));
                }
                else
                {
                    sb.append(",").append(bestPhase.get(i));
                }
                sb.append(System.lineSeparator());
            }

            data = sb.toString();

            logFile = createFile("Best_Nodes_Iteration_Phase.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }






        }
    }


    ForLoopsRuntime forLoopsRuntime = new ForLoopsRuntime();

    class DuplicatedResultsRootCause<T>
    {
        class ResultData<T>
        {
            Set<T> result;
            Set<T> sourceMIS;
            T v;
            Set<T> jv;

            public ResultData(Set<T> result, Set<T> sourceMIS, T v, Set<T> jv) {
                this.result = result;
                this.sourceMIS = sourceMIS;
                this.v = v;
                this.jv = jv;
            }

            public boolean isResultEqualsSourceMIS()
            {
                return result.equals(sourceMIS);
            }

            public boolean isJvEqualsSourceMIS()
            {
                return jv.equals(sourceMIS);
            }

            public boolean isResultEqualsSourceMISAndJv()
            {
                return isJvEqualsSourceMIS() && result.equals(jv);
            }

            public boolean isVinSourceMIS()
            {
                return sourceMIS.contains(v);
            }

            public boolean isVinResult()
            {
                return result.contains(v);
            }


            public boolean isVInSourceMISAndNotInResult()
            {
                return  isVinSourceMIS() && !isVinResult();
            }

            public boolean isVInResultAndNotInSourceMIS()
            {
                return  !isVinSourceMIS() && isVinResult();
            }


            public Set<T> getResult() {
                return result;
            }

            public void setResult(Set<T> result) {
                this.result = result;
            }

            public Set<T> getSourceMIS() {
                return sourceMIS;
            }

            public void setSourceMIS(Set<T> sourceMIS) {
                this.sourceMIS = sourceMIS;
            }

            public T getV() {
                return v;
            }

            public void setV(T v) {
                this.v = v;
            }

            public Set<T> getJv() {
                return jv;
            }

            public void setJv(Set<T> jv) {
                this.jv = jv;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof ResultData)) return false;

                ResultData<?> that = (ResultData<?>) o;

                if (getResult() != null ? !getResult().equals(that.getResult()) : that.getResult() != null)
                    return false;
                if (getSourceMIS() != null ? !getSourceMIS().equals(that.getSourceMIS()) : that.getSourceMIS() != null)
                    return false;
                if (getV() != null ? !getV().equals(that.getV()) : that.getV() != null) return false;
                return getJv() != null ? getJv().equals(that.getJv()) : that.getJv() == null;
            }

            @Override
            public int hashCode() {
                int result1 = getResult() != null ? getResult().hashCode() : 0;
                result1 = 31 * result1 + (getSourceMIS() != null ? getSourceMIS().hashCode() : 0);
                result1 = 31 * result1 + (getV() != null ? getV().hashCode() : 0);
                result1 = 31 * result1 + (getJv() != null ? getJv().hashCode() : 0);
                return result1;
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("ResultData{");
                sb.append("result=").append(result);
                sb.append(", sourceMIS=").append(sourceMIS);
                sb.append(", v=").append(v);
                sb.append(", jv=").append(jv);
                sb.append('}');
                return sb.toString();
            }
        }

        Map<ResultData, Integer> resultDataMap = new HashMap<>();
        public void logResultData(Set<T> result, Set<T> sourceMIS, T v, Set<T> jv )
        {
            ResultData resultData = new ResultData(result, sourceMIS, v, jv);
            if (!resultDataMap.containsKey(resultData))
            {
                resultDataMap.put(resultData, 1);
            }
            else
            {
                resultDataMap.put(resultData, resultDataMap.get(resultData)+1);
            }
        }


        public void writeLog()
        {
            String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                    "total results generated",
                    "results where final result equals source MIS",
                    "duplicated results where final result equals source MIS",
                    "results where Jv equals source MIS",
                    "duplicated results where Jv equals source MIS",
                    "results where v in source MIS",
                    "duplicated results where v in source MIS");

            String data = dataToCSV(field, type, graph, separators, results, time,
                    //"total results generated"
                    resultDataMap.values().stream().mapToInt(Integer::intValue).sum(),

                    //  "results where final result equals source MIS"
                    resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                    {
                        return resultDataIntegerEntry.getKey().isResultEqualsSourceMIS();
                    }).mapToInt(value -> value.getValue()).sum(),

                    //"duplicated results where final result equals source MIS"
                    resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                    {
                        return resultDataIntegerEntry.getKey().isResultEqualsSourceMIS() && resultDataIntegerEntry.getValue()>1;
                    }).mapToInt(entry -> entry.getValue()).sum(),

                    //"results where Jv equals source MIS"

                    resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                    {
                        return  resultDataIntegerEntry.getKey().isJvEqualsSourceMIS();
                    }).mapToInt(entry->entry.getValue()).sum(),

                    // "duplicated results where Jv equals source MIS",

                    resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                    {
                        return  resultDataIntegerEntry.getKey().isJvEqualsSourceMIS() && resultDataIntegerEntry.getValue()>1;
                    }).mapToInt(entry->entry.getValue()).sum(),

                    //"results where v in source MIS",
                    resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                    {
                        return  resultDataIntegerEntry.getKey().isVinSourceMIS();
                    }).mapToInt(entry->entry.getValue()).sum(),

                    //"duplicated results where v in source MIS"
                    resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                    {
                        return  resultDataIntegerEntry.getKey().isVinSourceMIS() && resultDataIntegerEntry.getValue()>1;
                    }).mapToInt(entry->entry.getValue()).sum()




                    );

            File logFile = createFile("Duplication_Results_Root_Cause_Summary.csv");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile))) {
                output.println(fields.toString());
                output.println(data.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            logFile = createFile("duplicated results where final result equals source MIS.json");
            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {
                resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                {
                    return resultDataIntegerEntry.getKey().isResultEqualsSourceMIS() && resultDataIntegerEntry.getValue()>1;
                }).forEach(resultDataIntegerEntry -> output.println(resultDataIntegerEntry.getKey()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            logFile = createFile("duplicated results where Jv equals source MIS.json");
            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {
                resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                {
                    return resultDataIntegerEntry.getKey().isJvEqualsSourceMIS() && resultDataIntegerEntry.getValue()>1;
                }).forEach(resultDataIntegerEntry -> output.println(resultDataIntegerEntry.getKey()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            logFile = createFile("duplicated results where v in source MIS.json");

            try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {
                resultDataMap.entrySet().stream().filter(resultDataIntegerEntry ->
                {
                    return resultDataIntegerEntry.getKey().isVinSourceMIS() && resultDataIntegerEntry.getValue()>1;
                }).forEach(resultDataIntegerEntry -> output.println(resultDataIntegerEntry.getKey()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    DuplicatedResultsRootCause duplicatedResultsRootCause = new DuplicatedResultsRootCause();

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
            logResultData = Boolean.valueOf(prop.getProperty("logResultData"));

            logsFolder = prop.getProperty("logsFolder");

            if(logDuplicatesSaturatedGraphs)
            {
                logDuplicateSetsToExtend = true;
            }


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
        getInstance().printDuplicateSetsToExtendLog();
        getInstance().printDuplicatedSaturatedGraphs();
        getInstance().printExtendCallTimes();
        getInstance().printForLoopsRuntime();
        getInstance().printDuplicatedResultsRootCause();
    }

    private void printDuplicatedResultsRootCause() {
        if(logResultData)
        {
            duplicatedResultsRootCause.writeLog();
        }
    }

    private void printDuplicateMISLog()
    {
        if (logDuplicateMIS)
        {
            duplicatMISRecord.printLog();
        }
    }


    private void printDuplicateSetsToExtendLog()
    {
        if(logDuplicateSetsToExtend)
        {
            duplicateSetsToExtend.printLog();
        }
    }

    private void printDuplicatedSaturatedGraphs()
    {
        if(logDuplicatesSaturatedGraphs)
        {
            duplicatedSaturatedGraphs.printLog();
        }
    }

    private void printExtendCallTimes()
    {
        if (logExtendRuntime)
        {
            extendCallTiming.printLog();
        }
    }

    private void printForLoopsRuntime()
    {
        if(logForLoopsRuntime)
        {
            forLoopsRuntime.printLog();
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

    public static void addSetToExtend(Set setToExtend)
    {
        if(logDuplicateSetsToExtend)
        {
            getInstance().duplicateSetsToExtend.logSetToExtend(setToExtend);
        }
    }


    public static void addSaturatedGraph(IGraph graph, Set generatedFrom)
    {
        if(logDuplicatesSaturatedGraphs)
        {
            getInstance().duplicatedSaturatedGraphs.logSaturatedGraph(graph,generatedFrom);
        }
    }


    public static void startExtendCall()
    {
        if (logExtendRuntime)
        {
            getInstance().extendCallTiming.startCall();
        }
    }

    public static void finishExtendCall()
    {
        if(logExtendRuntime)
        {
            getInstance().extendCallTiming.finishCall();
        }
    }



    public static void startForLoop()
    {
        if (logForLoopsRuntime)
        {
            getInstance().forLoopsRuntime.startLoop();
        }
    }

    public static void addForLoopResult()
    {
        if(logForLoopsRuntime)
        {
            getInstance().forLoopsRuntime.newResult();
        }
    }

    public static void finishIterationPhase(AlgorithmStep algorithmStep)
    {
        if(logForLoopsRuntime)
        {
            getInstance().forLoopsRuntime.stopLoop(algorithmStep);
        }
    }

    public static void logResultData(Set result, Set sourceMIS, Object v, Set jv )
    {
        if (logResultData)
        {
            getInstance().duplicatedResultsRootCause.logResultData(result, sourceMIS, v, jv);
        }
    }






}
