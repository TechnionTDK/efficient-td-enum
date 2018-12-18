package tdk_enum.ml.classifiers.common;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class MathOperations {

    public static double round(double value, int relevantFractionDigits) {
        double ret = Double.NaN;

        if (!Double.isNaN(value) && relevantFractionDigits >= 0) {
            double multiplier = Math.pow(10, relevantFractionDigits);

            ret = Math.round(value * multiplier) / multiplier;
        }

        return ret;
    }

    public static double floor(double value, int relevantFractionDigits) {
        double ret = Double.NaN;

        if (!Double.isNaN(value) && relevantFractionDigits >= 0) {
            double multiplier =
                    Math.pow(10, relevantFractionDigits);

            value *= multiplier;

            if ((value * 10) % 1 != 0.0) {
                value = Math.round(value * 10) / 10;
            }

            ret = Math.floor(value) / multiplier;
        }

        return ret;
    }

    public static double ceil(double value, int relevantFractionDigits) {
        double ret = Double.NaN;

        if (!Double.isNaN(value) && relevantFractionDigits >= 0) {
            double multiplier =
                    Math.pow(10, relevantFractionDigits);

            value *= multiplier;

            if ((value * 10) % 1 != 0.0) {
                value = Math.ceil(value * 10) / 10;
            }

            ret = Math.floor(value) / multiplier;
        }

        return ret;
    }

    public static double getNearestValue(double value, double divisor) {
        double ret = Double.NaN;

        if (!Double.isNaN(value) && !Double.isNaN(divisor) && divisor > 0) {
            ret = Math.round(value / divisor) * divisor;
        }

        return ret;
    }

}