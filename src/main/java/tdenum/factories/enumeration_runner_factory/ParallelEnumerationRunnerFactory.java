package tdenum.factories.enumeration_runner_factory;

import tdenum.common.runner.IEnumerationRunner;
import tdenum.common.runner.ParallelEnumerationRunner;
import tdenum.common.runner.SingleThreadEnumerationRunner;
import tdenum.factories.TDEnumFactory;

public class ParallelEnumerationRunnerFactory implements IEnumerationRunnerFactory {
    @Override
    public IEnumerationRunner produce() {
        return inject(new ParallelEnumerationRunner());
    }

    IEnumerationRunner inject(IEnumerationRunner enumerationRunner)
    {
        enumerationRunner.setMinimalTriangulationsEnumerator(TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce());
        enumerationRunner.setResultHandler(TDEnumFactory.getResultHandlerFactory().produce());
        return enumerationRunner;
    }
}
