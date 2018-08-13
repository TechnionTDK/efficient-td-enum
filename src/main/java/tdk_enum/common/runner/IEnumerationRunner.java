package tdk_enum.common.runner;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;

import java.util.concurrent.Callable;

public interface IEnumerationRunner extends Callable {




    void setMinimalTriangulationsEnumerator(IMinimalTriangulationsEnumerator minimalTriangulationsEnumerator);


    int getNumberOfMinimalSeparators();

    void setResultHandler(IResultHandler resultHandler);

    IResultHandler getResultHandler();
}
