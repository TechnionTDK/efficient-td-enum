package tdenum.common.runner;

import tdenum.common.IO.result_handler.IResultHandler;
import tdenum.graph.triangulation.IMinimalTriangulationsEnumerator;

import java.util.Date;
import java.util.concurrent.Callable;

public interface IEnumerationRunner extends Callable {




    void setMinimalTriangulationsEnumerator(IMinimalTriangulationsEnumerator minimalTriangulationsEnumerator);


    int getNumberOfMinimalSeparators();

    void setResultHandler(IResultHandler resultHandler);

    IResultHandler getResultHandler();
}
