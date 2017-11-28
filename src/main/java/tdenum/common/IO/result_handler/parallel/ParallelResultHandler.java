package tdenum.common.IO.result_handler.parallel;

import tdenum.common.IO.result_handler.AbstractResultHandler;
import tdenum.common.IO.result_handler.ResultInformation;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelResultHandler extends AbstractResultHandler {


    ConcurrentHashMap<Integer, Integer> widthMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, Integer> fillMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Long, Integer> expBagMap = new ConcurrentHashMap<>();
    AtomicInteger atomiceResultsFound = new AtomicInteger();
    public ParallelResultHandler()
    {

    }

    @Override
    public void newResult(IChordalGraph triangulation) {

        ResultInformation newResult = new ResultInformation(atomiceResultsFound.incrementAndGet(), getTime(), graph, triangulation);
        if (atomiceResultsFound.intValue()==1)
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
        minWidthCount = widthMap.get(minWidth);
        firstWidth = firstResult.getWidth();
        goodWidthCount = widthMap.get(firstResult.getWidth());


        minFill = fillMap.keySet().stream().min(Comparator.naturalOrder()).orElse(0);
        maxFill = fillMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        minFillCount = fillMap.get(maxFill);
        firstFill = firstResult.getFill();
        goodFillCount = fillMap.get(firstResult.getFill());

        minBagExpSize = expBagMap.keySet().stream().min(Comparator.naturalOrder()).orElse((long)0);
        maxBagExpSize = expBagMap.keySet().stream().max(Comparator.naturalOrder()).orElse((long)0);


        super.printSummaryTable();
    }
}
