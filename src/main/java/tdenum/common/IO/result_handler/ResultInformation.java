package tdenum.common.IO.result_handler;

import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.IGraph;

import java.io.PrintWriter;

public class ResultInformation {
    int number = 0;
    double time = 0;
    int fill = 0;
    int width = 0;
    long expBagSize = 0;

    public ResultInformation(int index, double time, final IGraph input, final IChordalGraph result) {
        this.number = index;
        this.time = time;
        fill = result.getFillIn(input);
        width = result.getTreeWidth();
        expBagSize = result.getExpBagsSize();
    }
    

    public int getNumber() {
        return number;
    }

    public double getTime() {
        return time;
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

    public void printDetails(PrintWriter out)
    {
        out.println(toString());
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

    public static void printCsvHeaderByTime(PrintWriter out)
    {
        out.println("time(seconds), width, fill, exponential bags size");
    }


}
