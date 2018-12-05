package tdk_enum.ml.feature_extractor.abseher.comperator;

import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;

import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class EuclideanDistanceFeatureComparator {

    public static double computeDistance(FeatureExtractionResult features1,
                                         FeatureExtractionResult features2) {

        return computeDistance(features1, features2, Double.NaN);
    }

    public static double computeDistance(FeatureExtractionResult features1,
                                         FeatureExtractionResult features2,
                                         double defaultDistance) {

        double ret = 0;

        if (features1 != null && features2 != null) {
            int measurementCount1 = features1.getMeasurementCount();
            int measurementCount2 = features2.getMeasurementCount();

            if (measurementCount1 == measurementCount2) {
                for (int i = 0; i < measurementCount1; i++) {
                    FeatureMeasurement measurement1 = features1.accessMeasurement(i);
                    FeatureMeasurement measurement2 = features2.accessMeasurement(i);

                    if (measurement1 != null && measurement2 != null) {
                        StatisticsSummary result1 = measurement1.getMeasurementResult();
                        StatisticsSummary result2 = measurement2.getMeasurementResult();

                        if (measurement1.isAtomic() && measurement2.isAtomic()) {
                            if (result1 != null && result2 != null) {
                                double value1 = result1.getAverage();
                                double value2 = result2.getAverage();

                                if (Double.isNaN(value1) || Double.isNaN(value2)) {
                                    if (Double.isNaN(value1) && Double.isNaN(value2)) {

                                    }
                                    else {
                                        ret += defaultDistance;
                                    }
                                }
                                else {
                                    if (Double.isInfinite(value1) || Double.isInfinite(value2)) {
                                        if (Double.isInfinite(value1) && Double.isInfinite(value2)) {

                                        }
                                        else {
                                            ret += defaultDistance;
                                        }
                                    }
                                    else {
                                        ret += value1 - value2;
                                    }
                                }
                            }
                            else if (result1 != null || result2 != null) {
                                ret += Math.pow(defaultDistance, 2);
                            }
                        }
                        else {
                            if (result1 != null && result2 != null) {
                                ret += Math.pow(result1.getEuclideanDistance(result2, defaultDistance), 2);
                            }
                            else if (result1 != null || result2 != null) {
                                ret += Math.pow(defaultDistance, 2);
                            }
                        }
                    }
                    else if (measurement1 != null || measurement2 != null) {
                        ret += Math.pow(defaultDistance, 2);
                    }
                }

                ret = Math.sqrt(ret);
            }
            else {
                ret = Double.NaN;
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }

    public static double computeDistance(List<Double> features1,
                                         List<Double> features2) {

        return computeDistance(features1, features2, Double.NaN);
    }

    public static double computeDistance(List<Double> features1,
                                         List<Double> features2,
                                         double defaultDistance) {

        double ret = 0;

        if (features1 != null && features2 != null) {
            int measurementCount1 = features1.size();
            int measurementCount2 = features2.size();

            if (measurementCount1 == measurementCount2) {
                if (measurementCount1 == 1) {
                    double value1 = features1.get(0);
                    double value2 = features2.get(0);

                    if (Double.isNaN(value1) || Double.isNaN(value2)) {
                        if (Double.isNaN(value1) && Double.isNaN(value2)) {

                        }
                        else {
                            ret += defaultDistance;
                        }
                    }
                    else {
                        if (Double.isInfinite(value1) || Double.isInfinite(value2)) {
                            if (Double.isInfinite(value1) && Double.isInfinite(value2)) {

                            }
                            else {
                                ret += defaultDistance;
                            }
                        }
                        else {
                            ret += value1 - value2;
                        }
                    }
                }
                else {
                    for (int i = 0; i < measurementCount1; i++) {
                        double value1 = features1.get(i);
                        double value2 = features2.get(i);

                        if (Double.isNaN(value1) || Double.isNaN(value2)) {
                            if (Double.isNaN(value1) && Double.isNaN(value2)) {

                            }
                            else {
                                ret += Math.pow(defaultDistance, 2);
                            }
                        }
                        else {
                            if (Double.isInfinite(value1) || Double.isInfinite(value2)) {
                                if (Double.isInfinite(value1) && Double.isInfinite(value2)) {

                                }
                                else {
                                    ret += Math.pow(defaultDistance, 2);
                                }
                            }
                            else {
                                ret += Math.pow(value1 - value2, 2);
                            }
                        }
                    }
                }

                ret = Math.sqrt(ret);
            }
            else {
                ret = Double.NaN;
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }
}