package tdk_enum.ml.feature_extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class StatisticsSummary {
    private int count = 0;
    private double minimum = 0.0;
    private double maximum = 0.0;
    private double average = 0.0;
    private double quantile10 = 0.0;
    private double quantile25 = 0.0;
    private double quantile50 = 0.0;
    private double quantile75 = 0.0;
    private double quantile90 = 0.0;
    private double standardDeviation = 0.0;
    private double medianAbsoluteDeviation = 0.0;

    public StatisticsSummary(int count,
                             double minimum,
                             double maximum,
                             double average,
                             double quantile10,
                             double quantile25,
                             double quantile50,
                             double quantile75,
                             double quantile90,
                             double standardDeviation,
                             double medianAbsoluteDeviation) {
        this.count = count;
        this.minimum = minimum;
        this.maximum = maximum;
        this.average = average;
        this.quantile10 = quantile10;
        this.quantile25 = quantile25;
        this.quantile50 = quantile50;
        this.quantile75 = quantile75;
        this.quantile90 = quantile90;
        this.standardDeviation = standardDeviation;
        this.medianAbsoluteDeviation = medianAbsoluteDeviation;
    }

    public StatisticsSummary(List<Double> values) {
        if (values != null) {
            this.count = getCount(values);
            this.minimum = getMinimum(values);
            this.maximum = getMaximum(values);
            this.average = getAverageValue(values);
            this.quantile10 = getQuantile(values, 10);
            this.quantile25 = getQuantile(values, 25);
            this.quantile50 = getQuantile(values, 50);
            this.quantile75 = getQuantile(values, 75);
            this.quantile90 = getQuantile(values, 90);
            this.standardDeviation = getStandardDeviation(values);
            this.medianAbsoluteDeviation = getMedianAbsoluteDeviation(values);
        }
    }

    public int getCount() {
        return count;
    }

    public double getMedian() {
        return quantile50;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getAverage() {
        return average;
    }

    public double getQuantile10() {
        return quantile10;
    }

    public double getQuantile25() {
        return quantile25;
    }

    public double getQuantile50() {
        return quantile50;
    }

    public double getQuantile75() {
        return quantile75;
    }

    public double getQuantile90() {
        return quantile90;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double getMedianAbsoluteDeviation() {
        return medianAbsoluteDeviation;
    }

    public void print() {
        print(0);
    }

    public void print(int indentationWidth) {
        DecimalFormat format =
                new DecimalFormat("#,##0.0000");

        System.out.println(indentString("Median:                         " + String.format("%14s", format.format(getMedian())), indentationWidth));
        System.out.println(indentString("Arithmetic Average:             " + String.format("%14s", format.format(getAverage())), indentationWidth));

        System.out.println(indentString("Median Absolute Deviation:      " + String.format("%14s", format.format(getMedianAbsoluteDeviation())), indentationWidth));
        System.out.println(indentString("Statistical Standard Deviation: " + String.format("%14s", format.format(getStandardDeviation())), indentationWidth));

        System.out.println();

        System.out.println(indentString("Interpolated Quantiles:", indentationWidth));
        System.out.println(indentString("   Minimum:                     " + String.format("%14s", format.format(getMinimum())), indentationWidth));
        System.out.println(indentString("   10%-Quantile:                " + String.format("%14s", format.format(getQuantile10())), indentationWidth));
        System.out.println(indentString("   25%-Quantile:                " + String.format("%14s", format.format(getQuantile25())), indentationWidth));
        System.out.println(indentString("   50%-Quantile (Median):       " + String.format("%14s", format.format(getQuantile50())), indentationWidth));
        System.out.println(indentString("   75%-Quantile:                " + String.format("%14s", format.format(getQuantile75())), indentationWidth));
        System.out.println(indentString("   90%-Quantile:                " + String.format("%14s", format.format(getQuantile90())), indentationWidth));
        System.out.println(indentString("   Maximum:                     " + String.format("%14s", format.format(getMaximum())), indentationWidth));

    }

    public String toCSV() {
        DecimalFormat format =
                new DecimalFormat("0.0000");

        StringBuilder builder = new StringBuilder();

        builder.append(format.format(getMedian()));
        builder.append(",");
        builder.append(format.format(getAverage()));
        builder.append(",");
        builder.append(format.format(getMedianAbsoluteDeviation()));
        builder.append(",");
        builder.append(format.format(getStandardDeviation()));
        builder.append(",");
        builder.append(format.format(getMinimum()));
        builder.append(",");
        builder.append(format.format(getQuantile10()));
        builder.append(",");
        builder.append(format.format(getQuantile25()));
        builder.append(",");
        builder.append(format.format(getQuantile50()));
        builder.append(",");
        builder.append(format.format(getQuantile75()));
        builder.append(",");
        builder.append(format.format(getQuantile90()));
        builder.append(",");
        builder.append(format.format(getMaximum()));

        return builder.toString();
    }

    public String getCSVHeaders() {
        StringBuilder builder = new StringBuilder();

        builder.append("Median,");
        builder.append("Arithmetic Average,");
        builder.append("Median Absolute Deviation,");
        builder.append("Statistical Standard Deviation,");

        builder.append("Minimum,");
        builder.append("10%-Quantile,");
        builder.append("25%-Quantile,");
        builder.append("50%-Quantile (Median),");
        builder.append("75%-Quantile,");
        builder.append("90%-Quantile,");
        builder.append("Maximum");

        return builder.toString();
    }

    public double getEuclideanDistance(StatisticsSummary other) {
        return getEuclideanDistance(other, Double.NaN);
    }

    public double getEuclideanDistance(StatisticsSummary other, double defaultDistance) {
        double ret = 0;

        if (other != null) {
            ret += Math.pow(getDifference(count, other.getCount(), defaultDistance), 2);

            ret += Math.pow(getDifference(minimum, other.getMinimum(), defaultDistance), 2);
            ret += Math.pow(getDifference(maximum, other.getMaximum(), defaultDistance), 2);
            ret += Math.pow(getDifference(average, other.getAverage(), defaultDistance), 2);

            ret += Math.pow(getDifference(quantile10, other.getQuantile10(), defaultDistance), 2);
            ret += Math.pow(getDifference(quantile25, other.getQuantile25(), defaultDistance), 2);
            ret += Math.pow(getDifference(quantile50, other.getQuantile50(), defaultDistance), 2);
            ret += Math.pow(getDifference(quantile75, other.getQuantile75(), defaultDistance), 2);
            ret += Math.pow(getDifference(quantile90, other.getQuantile90(), defaultDistance), 2);

            ret += Math.pow(getDifference(standardDeviation, other.getStandardDeviation(), defaultDistance), 2);
            ret += Math.pow(getDifference(medianAbsoluteDeviation, other.getMedianAbsoluteDeviation(), defaultDistance), 2);

            ret = Math.sqrt(ret);
        }
        else {
            ret = defaultDistance;
        }

        return ret;
    }

    private static int getCount(List<Double> values) {
        int ret = 0;

        if (values != null) {
            for (Double value : values) {
                if (value != null) {
                    ret++;
                }
            }
        }

        return ret;
    }

    private static double getMinimum(List<Double> values) {
        double ret = Double.MAX_VALUE;

        if (values != null) {
            for (Double value : values) {
                if (value != null) {
                    if (value < ret) {
                        ret = value;
                    }
                }
            }
        }

        return ret;
    }

    private static double getMaximum(List<Double> values) {
        double ret = Double.MIN_VALUE;

        if (values != null) {
            for (Double value : values) {
                if (value != null) {
                    if (value > ret) {
                        ret = value;
                    }
                }
            }
        }

        return ret;
    }

    private double getTotalSum(List<Double> values) {
        double ret = 0.0;

        if (values != null) {
            for (Double value : values) {
                if (value != null) {
                    ret += value;
                }
            }
        }

        return ret;
    }

    private double getAverageValue(List<Double> values) {
        double ret = getTotalSum(values);

        if (ret != 0) {
            int items = 0;

            if (values != null) {
                for (Double value : values) {
                    if (value != null) {
                        items++;
                    }
                }
            }

            ret = Math.round(ret / ((double)items));
        }

        return ret;
    }

    private double getStandardDeviation(List<Double> values) {
        double ret = 0.0;
        double avg = getAverageValue(values);

        if (avg != 0) {
            int items = 0;

            if (values != null) {
                for (Double value : values) {
                    if (value != null) {
                        ret += (long)Math.pow(value - avg, 2);

                        items++;
                    }
                }
            }

            if (getCount() > 1) {
                ret = ret / ((double)getCount() - 1);

                ret = Math.sqrt(ret);
            }
        }
        else {
            ret = -1;
        }

        return ret;
    }

    private static double getMedian(List<Double> values) {
        return getQuantile(values, 50);
    }

    private static double getQuantile(List<Double> values, int percentage) {
        double ret = 0.0;

        if (values != null) {
            if (percentage < 0) {
                percentage = 0;
            }

            if (percentage > 100) {
                percentage = 100;
            }

            if (values.size() > 0) {
                List<Double> sortedValues =
                        new ArrayList<>();

                for (Double value : values) {
                    if (value != null) {
                        sortedValues.add(value);
                    }
                }

                Collections.sort(sortedValues);

                double targetIndex =
                        ((0.0 + sortedValues.size() - 1) * percentage) / 100;

                if (targetIndex % 1 == 0.0) {
                    ret = sortedValues.get((int)Math.round(targetIndex));
                }
                else {
                    int x0 = (int)Math.floor(targetIndex);
                    int x1 = (int)Math.ceil(targetIndex);

                    double f0 = sortedValues.get(x0);
                    double f1 = sortedValues.get(x1);

                    ret = getLinearInterpolation(targetIndex, x0, x1, f0, f1);
                }
            }
        }

        return ret;
    }

    private double getMedianAbsoluteDeviation(List<Double> values) {
        double ret = -1;
        double median = getMedian(values);

        if (median > 0) {
            List<Double> deviations =
                    new ArrayList<>();

            if (values != null) {
                for (Double value : values) {
                    if (value != null) {
                        deviations.add(Math.abs(value - median));
                    }
                }

                Collections.sort(deviations);
            }

            ret = getMedian(deviations);
        }

        return ret;
    }

    private static double getLinearInterpolation(double x,
                                                 double x0, double x1,
                                                 double f0, double f1) {
        double ret = f0;

        if (x0 != x1) {
            ret += ((f1 - f0) / (x1 - x0)) * (x - x0);
        }
        else {
            ret = (f0 + f1) / 2;
        }

        return ret;
    }

    private static String indentString(String value, int indentationWidth) {
        String ret = value;

        if (ret != null && indentationWidth > 0) {
            String spaces = String.format("%" + indentationWidth + "s", "");

            ret = spaces + ret;
        }

        return ret;
    }

    private static double getDifference(double value1, double value2, double defaultDistance) {
        double ret = defaultDistance;

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

        return ret;
    }

}