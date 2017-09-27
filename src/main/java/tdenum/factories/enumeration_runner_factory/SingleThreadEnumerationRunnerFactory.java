package tdenum.factories.enumeration_runner_factory;

import tdenum.common.runner.IEnumerationRunner;
import tdenum.common.runner.SingleThreadEnumerationRunner;
import tdenum.factories.TDEnumFactory;

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
