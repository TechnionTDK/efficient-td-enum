package tdk_enum.ml.feature_extractor;

import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;

import java.io.File;
import java.util.Map;

public abstract class AbstractFeatureExtractor implements IFeatureExtractor {

    @Override
    public void toCSV(File csv, FeatureExtractionResult features)
    {
        toCSV(csv, features.toFlatMap());
    }
    @Override
    public void toCSV (File csv, Map<String, Double> flatFeatures)
    {

    }
}
