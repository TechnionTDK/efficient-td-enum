package tdk_enum.factories.enumeration.enumeration_runner_factory;

import tdk_enum.common.runner.IEnumerationRunner;
import tdk_enum.common.runner.ParallelEnumerationRunner;

public class ParallelEnumerationRunnerFactory implements IEnumerationRunnerFactory {
    @Override
    public IEnumerationRunner produce() {
        return inject(new ParallelEnumerationRunner());
    }

    IEnumerationRunner inject(IEnumerationRunner enumerationRunner)
    {
//        enumerationRunner.setMinimalTriangulationsEnumerator(TDKEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce());
//        enumerationRunner.setResultHandler(TDKEnumFactory.getResultHandlerFactory().produce());
        return enumerationRunner;
    }
}
