package tdk_enum.factories.ml.ml_runner_factory;

import tdk_enum.common.configuration.TDKMLConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.ml.classifier_factory.ClassifierFactory;
import tdk_enum.factories.ml.feature_extractor_factory.FeatureExtractorFactory;
import tdk_enum.factories.ml.solver_factory.SolverFactory;
import tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory.TreeDecompositionEnumeratorFactory;
import tdk_enum.ml.TDMLRunner;

public class TDMLRunnerFactory implements ITDMLRunnerFactory {
    @Override
    public TDMLRunner produce() {
        return inject(new TDMLRunner());

    }

    private TDMLRunner inject(TDMLRunner tdmlRunner) {
        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        tdmlRunner.setEnumerator(new TreeDecompositionEnumeratorFactory().produce());
        tdmlRunner.setSolver(new SolverFactory().produce());
        tdmlRunner.setFeatureExtractor(new FeatureExtractorFactory().produce());
        tdmlRunner.setClassifier(new ClassifierFactory().produce());

        tdmlRunner.setMlModelInput(configuration.getMlModelInput());
        tdmlRunner.setInputPath(configuration.getInputPath());
        tdmlRunner.setModelStorePath(configuration.getModelStorePath());
        tdmlRunner.setModelLoadPath(configuration.getModelLoadPath());

        tdmlRunner.setTdLimit(configuration.getTdLimitPerGraph());

        tdmlRunner.setMlSortTD(configuration.getMlSortTD());

        return tdmlRunner;

    }
}
