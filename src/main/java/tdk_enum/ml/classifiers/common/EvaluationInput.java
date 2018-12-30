package tdk_enum.ml.classifiers.common;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class EvaluationInput {

    private double actualValue = Double.NaN;
    private double predictedValue = Double.NaN;

    public EvaluationInput(double actualValue, double predictedValue) {
        this.actualValue = actualValue;
        this.predictedValue = predictedValue;
    }

    public double getActualValue() {
        return actualValue;
    }

    public double getPredictedValue() {
        return predictedValue;
    }

}