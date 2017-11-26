package tdenum.common.IO.logger.LogInformation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static tdenum.common.IO.CSVOperations.dataToCSV;

public class LogInfoDuplicatedResultsRootCause<T> extends AbstractLogInfo {



    Map<T, Integer> idMap = new HashMap<>();

    int id = 1;

    class ResultData<T>
    {
        class ResultDataTuple<T>
        {
            Set<T> mis;
            Set<T> jv;
            T v;

            public ResultDataTuple(Set<T> mis, Set<T> jv, T v) {
                this.mis = mis;
                this.jv = jv;
                this.v = v;
            }


            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof ResultDataTuple)) return false;

                ResultDataTuple that = (ResultDataTuple) o;

                if (mis != null ? !mis.equals(that.mis) : that.mis != null) return false;
                if (jv != null ? !jv.equals(that.jv) : that.jv != null) return false;
                return v != null ? v.equals(that.v) : that.v == null;
            }

            @Override
            public int hashCode() {
                int result = mis != null ? mis.hashCode() : 0;
                result = 31 * result + (jv != null ? jv.hashCode() : 0);
                result = 31 * result + (v != null ? v.hashCode() : 0);
                return result;
            }


            @Override
            public String toString() {
//                final StringBuilder sb = new StringBuilder("ResultDataTuple{");
//                sb.append("mis=").append(mis);
//                sb.append(", jv=").append(jv);
//                sb.append(", v=").append(v);
//                sb.append('}');
//                return sb.toString();
                return toJson().toString();
            }

            public JsonObject toJson()
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("mis", mis.toString());
                jsonObject.addProperty("jv", jv.toString());
                jsonObject.addProperty("v", v.toString());
                return jsonObject;
            }
        }

        Set<T> result;
        Map<Set<T>, Integer> sources = new HashMap<>();
        Map<Set<T>, Integer> jvs =new HashMap();

        Set<ResultDataTuple> generatedFrom = new HashSet<>();

        int count = 0;

        int sameSource = 0;
        int sameJv = 0;

        int sourceEqualsJv = 0;
        int sourceContainsV = 0;
        int resultContainsV = 0;

        public ResultData(Set<T> result)
        {
            this.result = result;
        }

        public void log(Set<T> source, Set<T> jv, T v)
        {

            count++;
            logSource(source);
            logJv(jv);
            generatedFrom.add(new ResultDataTuple(source, jv,v));


            if (source.equals(jv))
            {
                sourceEqualsJv++;
            }
            if(source.contains(v))
            {
                sourceContainsV++;
            }

            if(result.contains(v))
            {
                resultContainsV++;
            }

            if(result.equals(jv))
            {
                sameJv++;
            }
        }

        void logSource(Set<T> source)
        {
            if(!sources.containsKey(source))
            {
                sources.put(source,0);
            }
            sources.put(source, sources.get(source)+1);
            if(result.equals(source))
            {
                sameSource++;
            }
        }

        void logJv(Set<T> jv)
        {
            if (!jvs.containsKey(jv))
            {
                jvs.put(jv,0);
            }
            jvs.put(jv, jvs.get(jv)+1);
        }



        public int getAmountOfSourceMIS()
        {
            return sources.values().stream().mapToInt(Integer::intValue).sum();
        }
        public int getAmountOfDuplicatedSourceMIS()
        {
            return sources.values().stream().mapToInt(Integer::intValue).filter(i->i>1).sum();
        }

        @Override
        public String toString() {
//            final StringBuilder sb = new StringBuilder("ResultData{");
//            sb.append("result=").append(result);
//            sb.append(", generatedFrom=").append(generatedFrom);
//            sb.append('}');
//            return sb.toString();
            return toJson().toString();
        }

        public JsonObject toJson()
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("result", result.toString());
            JsonArray jsonArray = new JsonArray();
            for (ResultDataTuple tuple: generatedFrom
                 ) {
                jsonArray.add(tuple.toJson());
            }
            jsonObject.add("generatedFrom",jsonArray);
            return jsonObject;
        }
    }

    Map<Set<T>, ResultData> resultDataMap = new HashMap<>();



    public void logResultData(Set<T> result, Set<T> sourceMIS, T v, Set<T> jv )
    {

        if (!resultDataMap.containsKey(result))
        {
            resultDataMap.put(result, new ResultData(result));
        }
        resultDataMap.get(result).log(sourceMIS,jv,v);

        for (T t : result)
        {
            if (!idMap.containsKey(t))
            {
                idMap.put(t, id);
                id++;
            }
        }

        for (T t: sourceMIS)
        {
            if (!idMap.containsKey(t))
            {
                idMap.put(t, id);
                id++;
            }
        }

        for (T t: jv)
        {
            if (!idMap.containsKey(t))
            {
                idMap.put(t, id);
                id++;
            }
        }

        if (!idMap.containsKey(v))
        {
            idMap.put(v, id);
            id++;
        }


    }
    @Override
    public void printLog() {
        String fields = dataToCSV("field", "type", "graph", "separators", "results", "time",
                "total results generated",
                "results where final result equals source MIS",
                "generated results where final result equals source MIS",
                "average duplication where source MIS equals result",
                "results where Jv equals source MIS",
                "generated results where Jv equals source MIS",
                "average duplication where Jv equals source MIS",
                "results where v in source MIS",
                "generated results where v in source MIS",
                "average duplication where v in source MIS");

        String data = dataToCSV(field, type, graph, separators, results, time,
                //"total results generated"
                resultDataMap.values().stream().mapToInt(e->e.count).sum(),

                //  "results where final result equals source MIS"
                resultDataMap.values().stream().mapToInt(e->e.sameSource).filter(i->i>0).count(),
                //"generated results where final result equals source MIS"
                resultDataMap.values().stream().mapToInt(e->e.sameSource).filter(i->i>0).sum(),
                //"average duplication where source MIS equals result"
                resultDataMap.values().stream().mapToInt(e->e.sameSource).filter(i->i>0).average().orElse(0),

                //"results where Jv equals source MIS",
                resultDataMap.values().stream().mapToInt(e->e.sourceEqualsJv).filter(i->i>0).count(),
                //"generated results where Jv equals source MIS",
                resultDataMap.values().stream().mapToInt(e->e.sourceEqualsJv).filter(i->i>0).sum(),
                //"average duplication where Jv equals source MIS",
                resultDataMap.values().stream().mapToInt(e->e.sourceEqualsJv).filter(i->i>0).average().orElse(0),

                //"results where v in source MIS",
                resultDataMap.values().stream().mapToInt(e->e.sourceContainsV).filter(i->i>0).count(),
                //"generated results where v in source MIS",
                resultDataMap.values().stream().mapToInt(e->e.sourceContainsV).filter(i->i>0).sum(),
                //"average duplication where v in source MIS"
                resultDataMap.values().stream().mapToInt(e->e.sourceContainsV).filter(i->i>0).average().orElse(0)
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
        output.println("[");
            resultDataMap.values().stream().filter(e->e.sameSource>1).limit(5)
                    .forEach(e -> {output.print(mapToInt(e)); output.println(",");});
            output.println("]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        logFile = createFile("duplicated results where Jv equals source MIS.json");
        try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {
            output.println("[");
            resultDataMap.values().stream().filter(e->e.sourceEqualsJv>1).limit(5)
                    .forEach(e -> {output.print(mapToInt(e)); output.println(",");});
            output.println("]");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        logFile = createFile("duplicated results where v in source MIS.json");

        try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {
            JsonObject printResults = getJsonWithMapping();
            JsonArray topResults = new JsonArray();

            resultDataMap.values().stream().filter(e->e.sourceContainsV>1).limit(5)
                    .forEach(e -> {
                                topResults.add(e.toJson());
                            });
            printResults.add("topResults", topResults);
            output.println(printResults);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        logFile = createFile("most duplicated results sources.json");
        try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {

            JsonObject printResults = getJsonWithMapping();
            JsonArray topResults = new JsonArray();
            resultDataMap.values().stream().
                    sorted((o1, o2) -> o2.count-o1.count).limit(20).forEach((e -> topResults.add(mapToInt(e).toJson())));
            printResults.add("topResults",topResults);
            output.println(printResults);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    JsonObject getJsonWithMapping()
    {
        JsonObject printResults = new JsonObject();
        JsonObject mapping = new JsonObject();
        for(Map.Entry<T,Integer> entry: idMap.entrySet())
        {
            JsonArray array = new JsonArray();
            NodeSet nodes = (NodeSet) entry.getKey();
            for (Node node : nodes)
            {
                array.add(node.intValue());
            }
            mapping.add(entry.getValue().toString(), array);
        }
        printResults.add("mapping", mapping);
        return printResults;
    }

    ResultData<Integer> mapToInt(ResultData<T> tResultData)
    {
        Set resultsId = getSetIds(tResultData.result);
        ResultData<Integer> intResultData = new ResultData<Integer>(resultsId);

        for(ResultData.ResultDataTuple resultDataTuple : tResultData.generatedFrom)
        {
            Set<Integer> mis = getSetIds(resultDataTuple.mis);
            Set<Integer> jv = getSetIds(resultDataTuple.jv);
            Integer v = idMap.get(resultDataTuple.v);
            intResultData.log(mis,jv,v);
        }


        intResultData.sameJv = tResultData.sameJv;
        intResultData.resultContainsV =tResultData.resultContainsV;
        intResultData.sourceEqualsJv = tResultData.sourceEqualsJv;
        intResultData.sourceContainsV = tResultData.sourceContainsV;
        intResultData.sameSource = tResultData.sameSource;
        intResultData.count = tResultData.count;






        return intResultData;




    }

    Set<Integer> getSetIds(Set<T> tSet)
    {
        Set<Integer> ids = new HashSet<>();
        for (T t : tSet)
        {
            ids.add(idMap.get(t));
        }
        return ids;
    }



}
