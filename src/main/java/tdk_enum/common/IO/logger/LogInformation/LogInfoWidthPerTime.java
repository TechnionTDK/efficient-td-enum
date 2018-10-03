package tdk_enum.common.IO.logger.LogInformation;



import tdk_enum.graph.data_structures.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;

public class LogInfoWidthPerTime extends AbstractLogInfo {



    List<Pair<Integer, Long>> widthTimeList = new ArrayList<>();

    public void logWidthTime (int width, long timeInMilis)
    {
        widthTimeList.add(new Pair<>(width,timeInMilis));
    }

    @Override
    public void printLog() {

        String headers = dataToCSV("width", "time in milis");
        File logFile  = createFile("width_time.csv");
        try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {

            output.println(headers);
            widthTimeList.stream().forEach(e->output.println(e.getKey() + "," + e.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        logFile = createFile("width_time_sorted_by_time.csv");
        try(PrintWriter output = new PrintWriter(new FileOutputStream(logFile, true))) {

            output.println(headers);
            widthTimeList.stream().sorted((o1, o2) -> ((Long)(o2.getValue()-o1.getValue())).intValue())
                    .forEach(e->output.println(e.getKey() + "," + e.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}
