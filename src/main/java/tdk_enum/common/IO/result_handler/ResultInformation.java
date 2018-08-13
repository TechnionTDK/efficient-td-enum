package tdk_enum.common.IO.result_handler;

import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

import java.io.PrintWriter;

public abstract class ResultInformation {
    protected int number = 0;
    protected double time = 0;


    public ResultInformation(int index, double time ){
        this.number = index;
        this.time = time;

    }
    

    public int getNumber() {
        return number;
    }

    public double getTime() {
        return time;
    }





    public void printDetails(PrintWriter out)
    {
        out.println(toString());
    }

    public abstract void printCSVByTime(PrintWriter out);

//
//    public static void printCsvHeaderByTime(PrintWriter out)
//    {
//        out.println("time(seconds), width, fill, exponential bags size");
//    }

    public abstract void printCsvHeaderByTime(PrintWriter out);


}
