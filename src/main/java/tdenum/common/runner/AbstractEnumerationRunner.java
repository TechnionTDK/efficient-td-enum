package tdenum.common.runner;

import tdenum.common.IO.result_handler.IResultHandler;
import tdenum.graph.triangulation.IMinimalTriangulationsEnumerator;

import java.util.Date;

public abstract class AbstractEnumerationRunner implements  IEnumerationRunner{

    protected IMinimalTriangulationsEnumerator minimalTriangulationsEnumerator;

    protected IResultHandler resultHandler;



    @Override
    public void setMinimalTriangulationsEnumerator(IMinimalTriangulationsEnumerator minimalTriangulationsEnumerator) {
        this.minimalTriangulationsEnumerator = minimalTriangulationsEnumerator;
    }

    @Override
    public int getNumberOfMinimalSeparators()
    {
        return minimalTriangulationsEnumerator.getNumberOfMinimalSeperatorsGenerated();
    }

    @Override
    public void setResultHandler(IResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    @Override
    public IResultHandler getResultHandler()
    {
        return resultHandler;
    }


}
