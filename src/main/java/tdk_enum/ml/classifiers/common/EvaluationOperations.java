package tdk_enum.ml.classifiers.common;

import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class EvaluationOperations {

    public static double getMeanAbsoluteError(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            int count = 0;
            double sum = 0.0;

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    sum += Math.abs(input.getPredictedValue() - input.getActualValue());

                    count++;
                }
            }

            if (count > 0) {
                ret = sum / count;
            }
        }

        return ret;
    }

    public static double getRelativeAbsoluteError(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            int count = 0;
            double sum = 0.0;

            double average =
                    getAverageActualValue(inputValues);

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    sum += Math.abs(average - input.getActualValue());

                    count++;
                }
            }

            if (count > 0) {
                double error =
                        getMeanAbsoluteError(inputValues);

                if (sum == 0.0 && error == 0.0) {
                    ret = 0.0;
                }
                else {
                    ret = error / (sum / count);
                }
            }
        }

        return ret;
    }

    public static double getRootMeanSquareError(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            int count = 0;
            double sum = 0.0;

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    sum += Math.pow(input.getPredictedValue() - input.getActualValue(), 2);

                    count++;
                }
            }

            if (count > 0) {
                ret = Math.sqrt(sum / count);
            }
        }

        return ret;
    }

    public static double getRootRelativeSquareError(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            int count = 0;
            double sum = 0.0;

            double average =
                    getAverageActualValue(inputValues);

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    sum += Math.pow(average - input.getActualValue(), 2);

                    count++;
                }
            }

            if (count > 0) {
                double error =
                        getRootMeanSquareError(inputValues);

                if (sum == 0.0 && error == 0.0) {
                    ret = 0.0;
                }
                else {
                    ret = error / Math.sqrt(sum / count);
                }
            }
        }

        return ret;
    }

    public static double getPearsonsCorrelationCoefficient(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            double dividend = 0.0;
            double divisor1 = 0.0;
            double divisor2 = 0.0;

            double averageValue =
                    getAverageActualValue(inputValues);

            double averagePrediction =
                    getAveragePredictedValue(inputValues);

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    dividend += (input.getPredictedValue() - averagePrediction) *
                            (input.getActualValue() - averageValue);

                    divisor1 += Math.pow(input.getPredictedValue() - averagePrediction, 2);
                    divisor2 += Math.pow(input.getActualValue() - averageValue, 2);
                }
            }

            if (dividend == 0.0) {
                ret = 0.0;
            }
            else {
                ret = dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
            }
        }

        return ret;
    }

    private static double getAveragePredictedValue(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            List<Double> values = new ArrayList<>();

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    values.add(input.getPredictedValue());
                }
            }

            StatisticsSummary summary =
                    new StatisticsSummary(values);

            ret = summary.getAverage();
        }

        return ret;
    }

    private static double getAverageActualValue(List<EvaluationInput> inputValues) {
        double ret = Double.NaN;

        if (inputValues != null) {
            List<Double> values = new ArrayList<>();

            for (EvaluationInput input : inputValues) {
                if (input != null) {
                    values.add(input.getActualValue());
                }
            }

            StatisticsSummary summary =
                    new StatisticsSummary(values);

            ret = summary.getAverage();
        }

        return ret;
    }

}
