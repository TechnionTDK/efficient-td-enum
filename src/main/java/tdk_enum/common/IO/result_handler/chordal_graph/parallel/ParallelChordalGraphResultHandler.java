package tdk_enum.common.IO.result_handler.chordal_graph.parallel;

import tdk_enum.common.IO.result_handler.chordal_graph.ChordalGraphResultInformation;
import tdk_enum.common.IO.result_handler.chordal_graph.single_thread.SingleThreadChordalGraphResultHandler;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelChordalGraphResultHandler extends SingleThreadChordalGraphResultHandler {


    ConcurrentHashMap<Integer, Integer> widthMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, Integer> fillMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, Integer> expBagMap = new ConcurrentHashMap<>();
    AtomicInteger atomiceResultsFound = new AtomicInteger();
    public ParallelChordalGraphResultHandler()
    {

    }

    @Override
    public void newResult(IChordalGraph triangulation) {

        int index = atomiceResultsFound.incrementAndGet();
        ChordalGraphResultInformation newResult = new ChordalGraphResultInformation(index, getTime(), graph, triangulation);
        if (index==1)
        {
            firstResult=minFillResult=minWidthResult=minBagExpSizeResult=newResult;
        }
        handleWidth(newResult.getWidth());
        handleFill(newResult.getFill());
        handleExpBag(newResult.getExpBagSize());

    }

    private void handleExpBag(long expBagSize) {


        expBagMap.putIfAbsent(expBagSize,0);
        expBagMap.compute(expBagSize, (key, value) -> value+1);

    }

    private void handleFill(int fill) {
        fillMap.putIfAbsent(fill,0);
        fillMap.compute(fill, (key, value) -> value+1) ;
    }

    private void handleWidth(int width) {

        widthMap.putIfAbsent(width,0);
        widthMap.compute(width, (key, value) -> value+1 );
    }

    @Override
    public void print(IChordalGraph result) {
        newResult(result);

    }

    @Override
    public void printSummaryTable()
    {

        resultsFound = atomiceResultsFound.intValue();
        minWidth = widthMap.keySet().stream().min(Comparator.naturalOrder()).orElse(0);
        maxWidth = widthMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        minWidthCount = widthMap.getOrDefault(minWidth, 0);
        firstWidth = firstResult!= null ?((ChordalGraphResultInformation)firstResult).getWidth() :0;
        goodWidthCount = widthMap.getOrDefault(firstWidth,0);


        minFill = fillMap.keySet().stream().min(Comparator.naturalOrder()).orElse(0);
        maxFill = fillMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        minFillCount = fillMap.getOrDefault(minFill, 0);
        firstFill = firstResult!=null ?((ChordalGraphResultInformation)firstResult).getFill() :0;
        goodFillCount = fillMap.getOrDefault(firstFill,0);

        minBagExpSize = expBagMap.keySet().stream().min(Comparator.naturalOrder()).orElse((long)0);
        maxBagExpSize = expBagMap.keySet().stream().max(Comparator.naturalOrder()).orElse((long)0);


        super.printSummaryTable();
    }
}
