package tdk_enum.ml.classifiers.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class PerformanceRanking {

    public static int getRank(double value, List<Double> results) {
        int ret = -1;

        if (results != null) {
            List<Double> values =
                    new ArrayList<>(results);

            Comparator<Double> comparator = new Comparator<Double>() {
                @Override
                public int compare(Double value1, Double value2) {
                    int ret = 0;

                    if (value1 != null && value2 != null) {
                        if (!Double.isNaN(value1) && !Double.isNaN(value2)) {
                            ret = Double.compare(value1, value2);
                        }
                        else {
                            if (Double.isNaN(value1) && !Double.isNaN(value2)) {
                                ret = 1;
                            }
                            else if (!Double.isNaN(value1) && Double.isNaN(value2)) {
                                ret = -1;
                            }
                        }
                    }
                    else {
                        if (value1 == null && value2 != null) {
                            ret = 1;
                        }
                        else if (value1 != null && value2 == null) {
                            ret = -1;
                        }
                    }

                    return ret;
                }
            };

            Collections.sort(values, comparator);

            if (values.isEmpty()) {
                ret = 1;
            }
            else {
                ret = 1;

                boolean firstValue = true;

                Double lastValue =
                        values.get(0);

                int offset = 0;

                for (Double tmp : values) {
                    if (comparator.compare(lastValue, tmp) < 0 && comparator.compare(lastValue, value) < 0) {
                        ret += offset + 1;

                        lastValue = tmp;

                        offset = 0;
                    }
                    else if (!firstValue && comparator.compare(lastValue, tmp) == 0 && comparator.compare(lastValue, value) < 0) {
                        offset++;
                    }

                    firstValue = false;
                }

                if (comparator.compare(lastValue, value) < 0) {
                    ret += offset + 1;
                }
            }
        }

        return ret;
    }

    public static int getRank(double value, List<Double> results, int relevantFractionDigits) {
        int ret = -1;

        if (results != null) {
            if (relevantFractionDigits >= 0) {
                double newValue =
                        MathOperations.round(value, relevantFractionDigits);

                List<Double> newResults = new ArrayList<>();

                for (Double oldValue : results) {
                    if (oldValue != null) {
                        newResults.add(MathOperations.round(oldValue, relevantFractionDigits));
                    }
                    else {
                        newResults.add(null);
                    }
                }

                ret = getRank(newValue, newResults);
            }
            else {
                ret = getRank(value, results);
            }
        }

        return ret;
    }

    public static int getRank(double value, List<Double> results, double divisor) {
        int ret = -1;

        if (results != null) {
            if (divisor > 0) {
                double newValue =
                        MathOperations.getNearestValue(value, divisor);

                List<Double> newResults = new ArrayList<>();

                for (Double oldValue : results) {
                    if (oldValue != null) {
                        newResults.add(MathOperations.getNearestValue(oldValue, divisor));
                    }
                    else {
                        newResults.add(null);
                    }
                }

                ret = getRank(newValue, newResults);
            }
            else {
                ret = getRank(value, results);
            }
        }

        return ret;
    }

}