package tdk_enum.ml.feature_extractor.abseher.feature;


import java.text.DecimalFormat;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class FeatureMeasurement {

    private boolean atomic = false;

    private String featureName = "<UNKNOWN>";

    private StatisticsSummary measurementResult = null;

    private FeatureMeasurement(String featureName, StatisticsSummary measurementResult, boolean atomic) {
        this.atomic = atomic;
        this.featureName = featureName;
        this.measurementResult = measurementResult;
    }

    public boolean isAtomic() {
        return atomic;
    }

    public String getFeatureName() {
        return featureName;
    }

    public StatisticsSummary getMeasurementResult() {
        return measurementResult;
    }

    public int getSubFeatureCount() {
        return getSubFeatureCount(false);
    }

    public int getSubFeatureCount(boolean withoutSum) {
        int ret = 1;

        if (!atomic) {
            ret = StatisticsMeasurement.values().length - 1;

            if (withoutSum) {
                ret--;
            }
        }

        return ret;
    }

    public void print() {
        print(0);
    }

    public void print(int indentationWidth) {
        if (measurementResult != null) {
            measurementResult.print(indentationWidth + 3);
        }
        else {
            System.out.println(StringOperations.indentString("EXTRACTION FAILED!",
                    indentationWidth + 3));
        }
    }

    public String toCSV() {
        String ret = "";

        DecimalFormat format =
                new DecimalFormat("0.0000");

        if (measurementResult == null) {
            ret = "UNKNOWN";
        }
        else {
            if (isAtomic()) {
                if (measurementResult.getActiveCount() == 0) {
                    ret = "UNKNOWN";
                }
                else {
                    ret = StringOperations.formatNumber(format, measurementResult.getAverage());
                }
            }
            else {
                if (measurementResult.getActiveCount() == 0) {
                    ret = getDefaultCSVContent();
                }
                else {
                    ret = measurementResult.toCSV();
                }
            }
        }

        return ret;
    }

    public String getCSVHeaders() {
        StringBuilder builder =
                new StringBuilder();

        if (measurementResult == null) {
            builder.append("\"");
            builder.append(featureName);
            builder.append("\"");
        }
        else {
            if (isAtomic()) {
                builder.append("\"");
                builder.append(featureName);
                builder.append("\"");
            }
            else {
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Total Count\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Active Count\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Statistic Median\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Arithmetic Average\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Median Absolute Deviation\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Statistical Standard Deviation\",");

                builder.append("\"");
                builder.append(featureName);
                builder.append(": Minimum\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 5%-Quantile\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 10%-Quantile\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 25%-Quantile\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 50%-Quantile (Median)\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 75%-Quantile\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 90%-Quantile\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": 95%-Quantile\",");
                builder.append("\"");
                builder.append(featureName);
                builder.append(": Maximum\"");
            }
        }

        return builder.toString();
    }

    public FeatureMeasurement getDeepCopy() {
        return new FeatureMeasurement(featureName, measurementResult.getDeepCopy(), atomic);
    }

    public static FeatureMeasurement getInstance(String featureName, StatisticsSummary measurementResult) {
        FeatureMeasurement ret = null;

        if (featureName != null) {
            ret = new FeatureMeasurement(featureName, measurementResult, false);
        }

        return ret;
    }

    public static FeatureMeasurement getInstance(String featureName, StatisticsSummary measurementResult, boolean atomic) {
        FeatureMeasurement ret = null;

        if (featureName != null) {
            ret = new FeatureMeasurement(featureName, measurementResult, atomic);
        }

        return ret;
    }

    public static String getDefaultCSVContent() {
        StringBuilder builder = new StringBuilder();

        builder.append("0,");
        builder.append("0,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");

        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN,");
        builder.append("UNKNOWN");

        return builder.toString();
    }

}
