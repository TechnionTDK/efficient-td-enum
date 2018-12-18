package tdk_enum.ml.classifiers.common;

/**
 *
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class PredictedDecompositionDetails extends DecompositionDetails {

    private double predictedRuntime = Double.NaN;

    protected PredictedDecompositionDetails(DecompositionDetails decompositionDetails) {
        this(decompositionDetails, Double.NaN);
    }

    protected PredictedDecompositionDetails(DecompositionDetails decompositionDetails, double predictedRuntime) {
        super(decompositionDetails.getInstancePath(), decompositionDetails.getTdId(), decompositionDetails.getRelevantAttributeValues());

        this.predictedRuntime = predictedRuntime;
    }

    protected PredictedDecompositionDetails(String instance, int seed, double[] features) {
        this(instance, seed, features, Double.NaN);
    }

    protected PredictedDecompositionDetails(String instance, int seed, double[] features, double predictedRuntime) {
        super(instance, seed, features);

        this.predictedRuntime = predictedRuntime;
    }

    public double getPredictedRuntime() {
        return predictedRuntime;
    }

    public static PredictedDecompositionDetails getInstance(DecompositionDetails decompositionDetails) {
        return getInstance(decompositionDetails, Double.NaN);
    }

    public static PredictedDecompositionDetails getInstance(DecompositionDetails decompositionDetails, double predictedRuntime) {
        PredictedDecompositionDetails ret = null;

        if (decompositionDetails != null) {
            ret = new PredictedDecompositionDetails(decompositionDetails, predictedRuntime);
        }

        return ret;
    }

}