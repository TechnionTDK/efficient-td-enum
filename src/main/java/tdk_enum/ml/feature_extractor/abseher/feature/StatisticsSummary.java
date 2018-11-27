package tdk_enum.ml.feature_extractor.abseher.feature;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class StatisticsSummary {
    private int totalCount = 0;
    private int activeCount = 0;
    private double sum = Double.NaN;
    private double minimum = Double.NaN;
    private double maximum = Double.NaN;
    private double average = Double.NaN;
    private double quantile05 = Double.NaN;
    private double quantile10 = Double.NaN;
    private double quantile25 = Double.NaN;
    private double quantile50 = Double.NaN;
    private double quantile75 = Double.NaN;
    private double quantile90 = Double.NaN;
    private double quantile95 = Double.NaN;
    private double trimmedAverage01 = Double.NaN;
    private double trimmedAverage05 = Double.NaN;
    private double trimmedAverage10 = Double.NaN;
    private double trimmedAverage25 = Double.NaN;
    private double standardDeviation = Double.NaN;
    private double medianAbsoluteDeviation = Double.NaN;

    public StatisticsSummary(double value) {

        this.sum = value;
        this.minimum = value;
        this.maximum = value;
        this.average = value;
        this.quantile05 = value;
        this.quantile10 = value;
        this.quantile25 = value;
        this.quantile50 = value;
        this.quantile75 = value;
        this.quantile90 = value;
        this.quantile95 = value;
        this.totalCount = 1;
        this.trimmedAverage01 = value;
        this.trimmedAverage05 = value;
        this.trimmedAverage10 = value;
        this.trimmedAverage25 = value;
        this.standardDeviation = 0.0;
        this.medianAbsoluteDeviation = 0.0;

        if (Double.isNaN(value)) {
            this.activeCount = 0;
        }
        else {
            this.activeCount = 1;
        }
    }

    public StatisticsSummary(int count,
                             double sum,
                             double minimum,
                             double maximum,
                             double average,
                             double quantile05,
                             double quantile10,
                             double quantile25,
                             double quantile50,
                             double quantile75,
                             double quantile90,
                             double quantile95,
                             double trimmedAverage01,
                             double trimmedAverage05,
                             double trimmedAverage10,
                             double trimmedAverage25,
                             double standardDeviation,
                             double medianAbsoluteDeviation) {

        this.sum = sum;
        this.minimum = minimum;
        this.maximum = maximum;
        this.average = average;
        this.quantile05 = quantile05;
        this.quantile10 = quantile10;
        this.quantile25 = quantile25;
        this.quantile50 = quantile50;
        this.quantile75 = quantile75;
        this.quantile90 = quantile90;
        this.quantile95 = quantile95;
        this.totalCount = count;
        this.activeCount = count;
        this.trimmedAverage01 = trimmedAverage01;
        this.trimmedAverage05 = trimmedAverage05;
        this.trimmedAverage10 = trimmedAverage10;
        this.trimmedAverage25 = trimmedAverage25;
        this.standardDeviation = standardDeviation;
        this.medianAbsoluteDeviation = medianAbsoluteDeviation;
    }

    public StatisticsSummary(int totalCount,
                             int activeCount,
                             double sum,
                             double minimum,
                             double maximum,
                             double average,
                             double quantile05,
                             double quantile10,
                             double quantile25,
                             double quantile50,
                             double quantile75,
                             double quantile90,
                             double quantile95,
                             double trimmedAverage01,
                             double trimmedAverage05,
                             double trimmedAverage10,
                             double trimmedAverage25,
                             double standardDeviation,
                             double medianAbsoluteDeviation) {

        this.sum = sum;
        this.minimum = minimum;
        this.maximum = maximum;
        this.average = average;
        this.quantile05 = quantile05;
        this.quantile10 = quantile10;
        this.quantile25 = quantile25;
        this.quantile50 = quantile50;
        this.quantile75 = quantile75;
        this.quantile90 = quantile90;
        this.quantile95 = quantile95;
        this.totalCount = totalCount;
        this.activeCount = activeCount;
        this.trimmedAverage01 = trimmedAverage01;
        this.trimmedAverage05 = trimmedAverage05;
        this.trimmedAverage10 = trimmedAverage10;
        this.trimmedAverage25 = trimmedAverage25;
        this.standardDeviation = standardDeviation;
        this.medianAbsoluteDeviation = medianAbsoluteDeviation;
    }

    public StatisticsSummary(List<Double> values) {
        if (values != null) {
            List<Double> copy =
                    new ArrayList<>(values);

            this.sum = getSum(copy);
            this.minimum = getMinimum(copy);
            this.maximum = getMaximum(copy);
            this.average = getAverage(copy);
            this.quantile05 = getQuantile(copy, 5);
            this.quantile10 = getQuantile(copy, 10);
            this.quantile25 = getQuantile(copy, 25);
            this.quantile50 = getQuantile(copy, 50);
            this.quantile75 = getQuantile(copy, 75);
            this.quantile90 = getQuantile(copy, 90);
            this.quantile95 = getQuantile(copy, 95);
            this.totalCount = getTotalCount(copy);
            this.activeCount = getActiveCount(copy);
            this.trimmedAverage01 = getTrimmedAverage(copy,  1);
            this.trimmedAverage05 = getTrimmedAverage(copy,  5);
            this.trimmedAverage10 = getTrimmedAverage(copy, 10);
            this.trimmedAverage25 = getTrimmedAverage(copy, 25);
            this.standardDeviation = getStandardDeviation(copy);
            this.medianAbsoluteDeviation = getMedianAbsoluteDeviation(copy);
        }
    }

    public StatisticsSummary(List<Double> values, boolean includeInvalidNumbers) {
        if (values != null) {
            List<Double> copy =
                    new ArrayList<>(values);

            this.sum = getSum(copy);
            this.minimum = getMinimum(copy);
            this.maximum = getMaximum(copy);
            this.average = getAverage(copy);
            this.quantile05 = getQuantile(copy, 5, includeInvalidNumbers);
            this.quantile10 = getQuantile(copy, 10, includeInvalidNumbers);
            this.quantile25 = getQuantile(copy, 25, includeInvalidNumbers);
            this.quantile50 = getQuantile(copy, 50, includeInvalidNumbers);
            this.quantile75 = getQuantile(copy, 75, includeInvalidNumbers);
            this.quantile90 = getQuantile(copy, 90, includeInvalidNumbers);
            this.quantile95 = getQuantile(copy, 95, includeInvalidNumbers);
            this.totalCount = getTotalCount(copy);
            this.activeCount = getActiveCount(copy);
            this.trimmedAverage01 = getTrimmedAverage(copy,  1);
            this.trimmedAverage05 = getTrimmedAverage(copy,  5);
            this.trimmedAverage10 = getTrimmedAverage(copy, 10);
            this.trimmedAverage25 = getTrimmedAverage(copy, 25);
            this.standardDeviation = getStandardDeviation(copy);
            this.medianAbsoluteDeviation = getMedianAbsoluteDeviation(copy);
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public double getSum() {
        return sum;
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

    public double getQuantile05() {
        return quantile05;
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

    public double getQuantile95() {
        return quantile95;
    }

    public double getTrimmedAverage01() {
        return trimmedAverage01;
    }

    public double getTrimmedAverage05() {
        return trimmedAverage05;
    }

    public double getTrimmedAverage10() {
        return trimmedAverage10;
    }

    public double getTrimmedAverage25() {
        return trimmedAverage25;
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

        if (activeCount == 0) {
            System.out.println(StringOperations.indentString("----- NO VALUES AVAILABLE! -----", indentationWidth));
        }
        else if (activeCount == 1) {
            System.out.println(StringOperations.indentString("Value:                          " + String.format("%14s", StringOperations.formatNumber(format, getAverage())), indentationWidth));
        }
        else {
            System.out.println(StringOperations.indentString("Total Count:                    " + String.format("%14s", getTotalCount()), indentationWidth));
            System.out.println(StringOperations.indentString("Active Count:                   " + String.format("%14s", getActiveCount()), indentationWidth));
            System.out.println(StringOperations.indentString("Statistic Median:               " + String.format("%14s", StringOperations.formatNumber(format, getMedian())), indentationWidth));
            System.out.println(StringOperations.indentString("Arithmetic Average:             " + String.format("%14s", StringOperations.formatNumber(format, getAverage())), indentationWidth));

            System.out.println(StringOperations.indentString("Median Absolute Deviation:      " + String.format("%14s", StringOperations.formatNumber(format, getMedianAbsoluteDeviation())), indentationWidth));
            System.out.println(StringOperations.indentString("Statistical Standard Deviation: " + String.format("%14s", StringOperations.formatNumber(format, getStandardDeviation())), indentationWidth));

            System.out.println();

            System.out.println(StringOperations.indentString("Interpolated Quantiles:", indentationWidth));
            System.out.println(StringOperations.indentString("   Minimum:                     " + String.format("%14s", StringOperations.formatNumber(format, getMinimum())), indentationWidth));
            System.out.println(StringOperations.indentString("    5%-Quantile:                " + String.format("%14s", StringOperations.formatNumber(format, getQuantile10())), indentationWidth));
            System.out.println(StringOperations.indentString("   10%-Quantile:                " + String.format("%14s", StringOperations.formatNumber(format, getQuantile10())), indentationWidth));
            System.out.println(StringOperations.indentString("   25%-Quantile:                " + String.format("%14s", StringOperations.formatNumber(format, getQuantile25())), indentationWidth));
            System.out.println(StringOperations.indentString("   50%-Quantile (Median):       " + String.format("%14s", StringOperations.formatNumber(format, getQuantile50())), indentationWidth));
            System.out.println(StringOperations.indentString("   75%-Quantile:                " + String.format("%14s", StringOperations.formatNumber(format, getQuantile75())), indentationWidth));
            System.out.println(StringOperations.indentString("   90%-Quantile:                " + String.format("%14s", StringOperations.formatNumber(format, getQuantile90())), indentationWidth));
            System.out.println(StringOperations.indentString("   95%-Quantile:                " + String.format("%14s", StringOperations.formatNumber(format, getQuantile90())), indentationWidth));
            System.out.println(StringOperations.indentString("   Maximum:                     " + String.format("%14s", StringOperations.formatNumber(format, getMaximum())), indentationWidth));
        }
    }

    public double getMeasurementValue(StatisticsMeasurement measurement) {
        double ret = Double.NaN;

        switch (measurement) {
            case INVALID: {
                break;
            }
            case SUM: {
                ret = getSum();

                break;
            }
            case TOTAL_COUNT: {
                ret = getTotalCount();

                break;
            }
            case ACTIVE_COUNT: {
                ret = getActiveCount();

                break;
            }
            case MINIMUM: {
                ret = getMinimum();

                break;
            }
            case MAXIMUM: {
                ret = getMaximum();

                break;
            }
            case AVERAGE: {
                ret = getAverage();

                break;
            }
            case QUANTILE_05: {
                ret = getQuantile05();

                break;
            }
            case QUANTILE_10: {
                ret = getQuantile10();

                break;
            }
            case QUANTILE_25: {
                ret = getQuantile25();

                break;
            }
            case QUANTILE_50: {
                ret = getQuantile50();

                break;
            }
            case QUANTILE_75: {
                ret = getQuantile75();

                break;
            }
            case QUANTILE_90: {
                ret = getQuantile90();

                break;
            }
            case QUANTILE_95: {
                ret = getQuantile95();

                break;
            }
            case TRIMMED_AVERAGE_01: {
                ret = getTrimmedAverage01();

                break;
            }
            case TRIMMED_AVERAGE_05: {
                ret = getTrimmedAverage05();

                break;
            }
            case TRIMMED_AVERAGE_10: {
                ret = getTrimmedAverage10();

                break;
            }
            case TRIMMED_AVERAGE_25: {
                ret = getTrimmedAverage25();

                break;
            }
            case STANDARD_DEVIATION: {
                ret = getStandardDeviation();

                break;
            }
            case MEDIAN_ABSOLUTE_DEVIATION: {
                ret = getMedianAbsoluteDeviation();

                break;
            }
            default: {
                break;
            }
        }

        return ret;
    }

    public String toCSV() {
        DecimalFormat format =
                new DecimalFormat("0.0000");

        StringBuilder builder = new StringBuilder();

        builder.append(getTotalCount());
        builder.append(",");
        builder.append(getActiveCount());
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getMedian()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getAverage()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getMedianAbsoluteDeviation()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getStandardDeviation()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getMinimum()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile05()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile10()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile25()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile50()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile75()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile90()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getQuantile95()));
        builder.append(",");
        builder.append(StringOperations.formatNumber(format, getMaximum()));

        return builder.toString();
    }

    Map<String, Double> toFlatMap()
    {
        Map<String, Double> data = new LinkedHashMap<>();
        {
            data.put("Total Count",(double) getTotalCount());
            data.put("Active Count",(double) getActiveCount());
            data.put("Statistic Median", getMedian());
            data.put("Arithmetic Average", getAverage());
            data.put("Median Absolute Deviation", getMedianAbsoluteDeviation());
            data.put("Statistical Standard Deviation", getStandardDeviation());
            data.put("Minimum", getMinimum());
            data.put("5%-Quantile", getQuantile05());
            data.put("10%-Quantile", getQuantile10());
            data.put("25%-Quantile", getQuantile25());
            data.put("50%-Quantile (Median)", getQuantile50());
            data.put("75%-Quantile", getQuantile75());
            data.put("90%-Quantile", getQuantile90());
            data.put("95%-Quantile", getQuantile95());
            data.put("Maximum", getMaximum());

        }
        return data;
    }

    public static String getCSVHeaders() {
        StringBuilder builder = new StringBuilder();

        builder.append("\"Total Count\",");
        builder.append("\"Active Count\",");
        builder.append("\"Statistic Median\",");
        builder.append("\"Arithmetic Average\",");
        builder.append("\"Median Absolute Deviation\",");
        builder.append("\"Statistical Standard Deviation\",");

        builder.append("Minimum,");
        builder.append("\"5%-Quantile\",");
        builder.append("\"10%-Quantile\",");
        builder.append("\"25%-Quantile\",");
        builder.append("\"50%-Quantile (Median)\",");
        builder.append("\"75%-Quantile\",");
        builder.append("\"90%-Quantile\",");
        builder.append("\"95%-Quantile\",");
        builder.append("Maximum");

        return builder.toString();
    }

    public double getEuclideanDistance(StatisticsSummary other) {
        return getEuclideanDistance(other, Double.NaN);
    }

    public double getEuclideanDistance(StatisticsSummary other, double defaultDistance) {
        double ret = 0;

        if (other != null) {
            ret += Math.pow(StatisticsComparator.getDifference(sum, other.getSum(), defaultDistance), 2);

            ret += Math.pow(StatisticsComparator.getDifference(totalCount, other.getTotalCount(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(activeCount, other.getActiveCount(), defaultDistance), 2);

            ret += Math.pow(StatisticsComparator.getDifference(minimum, other.getMinimum(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(maximum, other.getMaximum(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(average, other.getAverage(), defaultDistance), 2);

            ret += Math.pow(StatisticsComparator.getDifference(quantile05, other.getQuantile10(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(quantile10, other.getQuantile10(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(quantile25, other.getQuantile25(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(quantile50, other.getQuantile50(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(quantile75, other.getQuantile75(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(quantile90, other.getQuantile90(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(quantile95, other.getQuantile90(), defaultDistance), 2);

            ret += Math.pow(StatisticsComparator.getDifference(trimmedAverage01, other.getTrimmedAverage01(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(trimmedAverage05, other.getTrimmedAverage05(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(trimmedAverage10, other.getTrimmedAverage10(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(trimmedAverage25, other.getTrimmedAverage25(), defaultDistance), 2);

            ret += Math.pow(StatisticsComparator.getDifference(standardDeviation, other.getStandardDeviation(), defaultDistance), 2);
            ret += Math.pow(StatisticsComparator.getDifference(medianAbsoluteDeviation, other.getMedianAbsoluteDeviation(), defaultDistance), 2);

            ret = Math.sqrt(ret);
        }
        else {
            ret = defaultDistance;
        }

        return ret;
    }

    private static int getTotalCount(List<Double> values) {
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

    private static int getActiveCount(List<Double> values) {
        int ret = 0;

        if (values != null) {
            for (Double value : values) {
                if (value != null && !Double.isNaN(value)) {
                    ret++;
                }
            }
        }

        return ret;
    }

    private static double getMinimum(List<Double> values) {
        double ret = Double.POSITIVE_INFINITY;

        if (values != null) {
            if (values.isEmpty()) {
                ret = Double.NaN;
            }
            else {
                boolean found = false;

                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        if (value < ret) {
                            ret = value;

                            found = true;
                        }
                    }
                }

                if (!found) {
                    ret = Double.NaN;
                }
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }

    private static double getMaximum(List<Double> values) {
        double ret = Double.NEGATIVE_INFINITY;

        if (values != null) {
            if (values.isEmpty()) {
                ret = Double.NaN;
            }
            else {
                boolean found = false;

                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        if (value > ret) {
                            ret = value;

                            found = true;
                        }
                    }
                }

                if (!found) {
                    ret = Double.NaN;
                }
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }

    private double getSum(List<Double> values) {
        double ret = 0.0;

        if (values != null) {
            if (values.isEmpty()) {
                ret = Double.NaN;
            }
            else {
                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        ret += value;
                    }
                }
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }

    private double getAverage(List<Double> values) {
        double ret = getSum(values);

        if (!Double.isNaN(ret)) {
            int items = 0;

            if (values != null) {
                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        items++;
                    }
                }
            }

            ret = ret / ((double)items);
        }

        return ret;
    }

    private double getTrimmedAverage(List<Double> values, int trimPercentage) {
        double ret = Double.NaN;

        if (values != null && trimPercentage >= 0 && trimPercentage <= 100) {
            List<Double> sortedValues =
                    new ArrayList<>(values);

            sortedValues.removeAll(Collections.singleton(null));

            sortedValues.removeAll(Collections.singleton(Double.NaN));

            Collections.sort(values);

            int itemCount =
                    sortedValues.size();

            int trimWidth =
                    (itemCount * trimPercentage) / 100;

            for (int i = itemCount - 1; i >= itemCount - trimWidth; i--) {
                sortedValues.remove(i);
            }

            for (int i = 0; i < trimWidth; i++) {
                sortedValues.remove(0);
            }

            ret = getSum(sortedValues);

            if (!Double.isNaN(ret)) {
                int items = 0;

                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        items++;
                    }
                }

                ret = ret / ((double)items);
            }
        }

        return ret;
    }

    private double getStandardDeviation(List<Double> values) {
        double ret = 0.0;
        double avg = getAverage(values);

        if (!Double.isNaN(avg)) {
            if (values != null) {
                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        ret += Math.pow(value - avg, 2);
                    }
                }
            }

            if (getActiveCount() > 1) {
                ret = ret / ((double)getActiveCount() - 1);

                ret = Math.sqrt(ret);
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }

    private static double getMedian(List<Double> values) {
        return getQuantile(values, 50);
    }

    private static double getQuantile(List<Double> values, int percentage) {
        return getQuantile(values, percentage, false);
    }

    private static double getQuantile(List<Double> values, int percentage, boolean includeInvalidNumbers) {
        double ret = 0.0;

        if (values != null) {
            if (values.isEmpty()) {
                ret = Double.NaN;
            }
            else {
                if (percentage < 0) {
                    percentage = 0;
                }

                if (percentage > 100) {
                    percentage = 100;
                }

                List<Double> sortedValues =
                        new ArrayList<>();

                for (Double value : values) {
                    if (value != null) {
                        if (includeInvalidNumbers || !Double.isNaN(value)) {
                            sortedValues.add(value);
                        }
                    }
                    else {
                        if (includeInvalidNumbers) {
                            sortedValues.add(Double.NaN);
                        }
                    }
                }

                if (sortedValues.size() > 0) {
                    Collections.sort(sortedValues, new Comparator<Double>() {
                        @Override
                        public int compare(Double v1, Double v2) {
                            int ret = 0;

                            if (v1 != null && v2 != null) {
                                if (!v1.isNaN() && !v2.isNaN()) {
                                    ret = v1.compareTo(v2);
                                }
                                else {
                                    if (v1.isNaN() && !v2.isNaN()) {
                                        ret = 1;
                                    }
                                    else if (v2.isNaN() && !v1.isNaN()) {
                                        ret = -1;
                                    }
                                }
                            }
                            else {
                                if (v1 == null && v2 != null) {
                                    ret = 1;
                                }
                                else if (v2 == null && v1 != null) {
                                    ret = -1;
                                }
                            }

                            return ret;
                        }
                    });

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

                        if (!Double.isInfinite(f0) && !Double.isInfinite(f1)) {
                            ret = getLinearInterpolation(targetIndex, x0, x1, f0, f1);
                        }
                        else {
                            ret = Double.POSITIVE_INFINITY;
                        }
                    }
                }
                else {
                    ret = Double.NaN;
                }
            }
        }
        else {
            ret = Double.NaN;
        }

        return ret;
    }

    public StatisticsSummary getDeepCopy() {
        return new StatisticsSummary(totalCount,
                activeCount,
                sum,
                minimum,
                maximum,
                average,
                quantile05,
                quantile10,
                quantile25,
                quantile50,
                quantile75,
                quantile90,
                quantile95,
                trimmedAverage01,
                trimmedAverage05,
                trimmedAverage10,
                trimmedAverage25,
                standardDeviation,
                medianAbsoluteDeviation);
    }

    private double getMedianAbsoluteDeviation(List<Double> values) {
        double ret = Double.NaN;
        double med = getMedian(values);

        if (!Double.isNaN(med)) {
            List<Double> deviations =
                    new ArrayList<>();

            if (values != null) {
                for (Double value : values) {
                    if (value != null && !Double.isNaN(value)) {
                        deviations.add(Math.abs(value - med));
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

}
