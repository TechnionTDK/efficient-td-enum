package tdk_enum.common.IO.result_handler.minimal_seperator;

import tdk_enum.common.IO.result_handler.ResultInformation;

import java.io.PrintWriter;

public class MinimalSeperatorResultInformation extends ResultInformation {


    public MinimalSeperatorResultInformation(int index, double time) {
        super(index, time);
    }





    public void printCSVByTime(PrintWriter out)
    {
        StringBuilder sb = new StringBuilder();
        sb.
                append(time).append(", ");

        out.println(sb.toString());
    }

    @Override
    public void printCsvHeaderByTime(PrintWriter out) {
        out.println("time(seconds)");
    }
}
