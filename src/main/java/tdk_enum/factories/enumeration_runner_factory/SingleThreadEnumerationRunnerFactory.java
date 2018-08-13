package tdk_enum.factories.enumeration_runner_factory;

import tdk_enum.common.runner.IEnumerationRunner;
import tdk_enum.common.runner.SingleThreadEnumerationRunner;
import tdk_enum.factories.TDKEnumFactory;

public class SingleThreadEnumerationRunnerFactory implements IEnumerationRunnerFactory{
    @Override
    public IEnumerationRunner produce() {
        return inject(new SingleThreadEnumerationRunner());
    }

    IEnumerationRunner inject(IEnumerationRunner enumerationRunner)
    {
//        enumerationRunner.setMinimalTriangulationsEnumerator(TDKEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce());
//        enumerationRunner.setResultHandler(TDKEnumFactory.getResultHandlerFactory().produce());
        return enumerationRunner;
    }

}
