package tdk_enum.common.runner;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.graph.triangulation.IMinimalTriangulationsEnumerator;

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
