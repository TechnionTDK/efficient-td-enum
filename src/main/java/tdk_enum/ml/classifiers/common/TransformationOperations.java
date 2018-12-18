package tdk_enum.ml.classifiers.common;

import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class TransformationOperations {

    public static List<Double> normalize(List<Double> values) {
        List<Double> ret = new ArrayList<>();

        if (values != null && !values.isEmpty()) {
            StatisticsSummary summary =
                    new StatisticsSummary(values);

            if (getDistinctNumberCount(values) == 1) {
                for (Double value : values) {
                    ret.add(0.5);
                }
            }
            else {
                double minimum =
                        summary.getMinimum();

                double maximum =
                        summary.getMaximum();

                for (Double value : values) {
                    if (value != null && !value.isNaN()) {
                        ret.add((value - minimum) / (maximum - minimum));
                    }
                    else {
                        ret.add(Double.NaN);
                    }
                }
            }
        }

        return ret;
    }

    public static List<Double> standardize(List<Double> values) {
        List<Double> ret = new ArrayList<>();

        if (values != null && !values.isEmpty()) {
            StatisticsSummary summary =
                    new StatisticsSummary(values);

            if (getDistinctNumberCount(values) == 1) {
                for (Double value : values) {
                    ret.add(0.0);
                }
            }
            else {
                double average =
                        summary.getAverage();

                double standardDeviation =
                        summary.getStandardDeviation();

                for (Double value : values) {
                    if (value != null && !value.isNaN()) {
                        ret.add((value - average) / standardDeviation);
                    }
                    else {
                        ret.add(Double.NaN);
                    }
                }
            }
        }

        return ret;
    }

    private static int getDistinctNumberCount(List<Double> values) {
        int ret = 0;

        if (values != null) {
            List<Double> visited = new ArrayList<>();

            for (Double value : values) {
                if (!visited.contains(value)) {
                    visited.add(value);
                }
            }

            ret = visited.size();
        }

        return ret;
    }

}
