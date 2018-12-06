package tdk_enum.factories.ml.feature_extractor_factory;

import tdk_enum.common.configuration.TDKMLConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.ml.feature_extractor.IFeatureExtractor;
import tdk_enum.ml.feature_extractor.abseher.AbeseherFeatuerExtractor;

public class FeatureExtractorFactory implements IFeatureExtractorFactory {
    @Override
    public IFeatureExtractor produce() {
        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        switch (configuration.getMlFeatureExtractor())
        {
            case ABESEHER:
                return inject(new AbeseherFeatuerExtractor());
            case TDK:
                return null;
            default:
                return inject(new AbeseherFeatuerExtractor());
        }

    }

    private IFeatureExtractor inject(IFeatureExtractor featuerExtractor) {
        return featuerExtractor;

    }
}
