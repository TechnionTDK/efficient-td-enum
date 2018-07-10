package tdk_enum.factories.enumeration_runner_factory;

import tdk_enum.common.runner.IEnumerationRunner;
import tdk_enum.common.runner.SingleThreadEnumerationRunner;
import tdk_enum.factories.TDEnumFactory;

public class SingleThreadEnumerationRunnerFactory implements IEnumerationRunnerFactory{
    @Override
    public IEnumerationRunner produce() {
        return inject(new SingleThreadEnumerationRunner());
    }

    IEnumerationRunner inject(IEnumerationRunner enumerationRunner)
    {
        enumerationRunner.setMinimalTriangulationsEnumerator(TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce());
        enumerationRunner.setResultHandler(TDEnumFactory.getResultHandlerFactory().produce());
        return enumerationRunner;
    }

}
