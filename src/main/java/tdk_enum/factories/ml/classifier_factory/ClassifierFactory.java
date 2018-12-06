package tdk_enum.factories.ml.classifier_factory;

import tdk_enum.common.configuration.TDKMLConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.ml.classifiers.IClassifier;
import tdk_enum.ml.classifiers.WekaClassifier;

public class ClassifierFactory implements IClassifierFactory {
    @Override
    public IClassifier produce() {
        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        switch (configuration.getMlClassifierType())
        {
            case WEKA:
                return inject(new WekaClassifier());
            default:
                return inject(new WekaClassifier());
        }
    }

    private IClassifier inject(IClassifier classifier) {
        return classifier;
    }
}
