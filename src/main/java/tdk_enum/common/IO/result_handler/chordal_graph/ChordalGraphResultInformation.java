package tdk_enum.common.IO.result_handler.chordal_graph;

import tdk_enum.common.IO.result_handler.ResultInformation;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import java.io.PrintWriter;

public class ChordalGraphResultInformation extends ResultInformation {

    int fill = 0;
    int width = 0;
    long expBagSize = 0;

    public ChordalGraphResultInformation(int index, double time, IGraph input, IChordalGraph result) {
        super(index, time);
        fill = result.getFillIn(input);
        width = result.getTreeWidth();
        expBagSize = result.getExpBagsSize();
    }


    public int getFill() {
        return fill;
    }

    public int getWidth() {
        return width;
    }

    public long getExpBagSize() {
        return expBagSize;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultInformation{");
        sb.append("number=").append(number);
        sb.append(", time=").append(time);
        sb.append(", fill=").append(fill);
        sb.append(", width=").append(width);
        sb.append(", expBagSize=").append(expBagSize);
        sb.append('}');
        return sb.toString();
    }



    public void printCSVByTime(PrintWriter out)
    {
        StringBuilder sb = new StringBuilder();
        sb.
                append(time).append(", ").
                append(width).append(", ").
                append(fill).append(", ").
                append(expBagSize);
        out.println(sb.toString());
    }

    @Override
    public void printCsvHeaderByTime(PrintWriter out) {
        out.println("time(seconds), width, fill, exponential bags size");
    }

//    public static void printCsvHeaderByTime(PrintWriter out)
//    {
//        out.println("time(seconds), width, fill, exponential bags size");
//    }

}
