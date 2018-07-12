package tdk_enum.common.IO.logger.LogInformation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class LogInfoCache extends AbstractLogInfo {


    Map<Set, List<Integer>> distanceMap = new HashMap<>();

    public void logHit(Set hit, int count)
    {
        if (!distanceMap.containsKey(hit))
        {
            distanceMap.put(hit, new ArrayList<>());
        }
        List<Integer> hits = distanceMap.get(hit);
        hits.add(count);
    }

    @Override
    public void printLog() {

        File logFile = createFile("Cache distances.json");

        Map<Set,List<Integer>> cacheHitDeltas = new HashMap<>();
        Map<Set, Double> avgDistanceMap = new HashMap<>();


        for(Map.Entry<Set, List<Integer>> cacheHits : distanceMap.entrySet())
        {
            List<Integer> deltas = new ArrayList<>();
            List<Integer> hits = cacheHits.getValue();
            deltas.add(hits.get(hits.size()-1));
            for (int i =1; i < hits.size(); i++)
            {
                deltas.add(hits.get(i)-hits.get(i-1));
            }
            if(!deltas.isEmpty())
            {
                cacheHitDeltas.put(cacheHits.getKey(), deltas);
                avgDistanceMap.put(cacheHits.getKey(), deltas.stream().mapToInt(e->e).average().orElse(0));
            }

        }



        double avarageDistance = avgDistanceMap.values().stream().mapToDouble(e->e).average().orElse(0);
        double maxDistance = avgDistanceMap.values().stream().mapToDouble(e->e).max().orElse(0);
        double minDsitance = avgDistanceMap.values().stream().mapToDouble(e->e).min().orElse(0);
        JsonObject printResults = new JsonObject();
        printResults.addProperty("average distance", avarageDistance);
        printResults.addProperty("maximum distance", maxDistance);
        printResults.addProperty("minimum distance", minDsitance);
        printResults.addProperty("cache size", distanceMap.size());
        JsonArray distances = new JsonArray();
        for (List<Integer> distance: cacheHitDeltas.values())
        {
            JsonArray deltas = new JsonArray();
            for(int i = 0; i < distance.size(); i++)
            {
                deltas.add(distance.get(i));
            }


            distances.add(deltas);
        }
        printResults.add("distances", distances);
        try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {

            output.println(printResults);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
