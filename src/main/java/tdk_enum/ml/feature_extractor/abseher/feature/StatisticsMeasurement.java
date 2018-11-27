package tdk_enum.ml.feature_extractor.abseher.feature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public enum StatisticsMeasurement {
    INVALID(0),
    SUM(1),
    TOTAL_COUNT(2),
    ACTIVE_COUNT(3),
    MINIMUM(4),
    MAXIMUM(5),
    AVERAGE(6),
    QUANTILE_05(7),
    QUANTILE_10(8),
    QUANTILE_25(9),
    QUANTILE_50(10),
    QUANTILE_75(11),
    QUANTILE_90(12),
    QUANTILE_95(13),
    TRIMMED_AVERAGE_01(14),
    TRIMMED_AVERAGE_05(15),
    TRIMMED_AVERAGE_10(16),
    TRIMMED_AVERAGE_25(17),
    STANDARD_DEVIATION(18),
    MEDIAN_ABSOLUTE_DEVIATION(19);

    private int value = 0;

    StatisticsMeasurement(int value) {
        this.value = value;
    }

    public int getStatusValue() {
        return value;
    }

    public static StatisticsMeasurement getFromStatusValue(int value) {
        switch (value) {
            case 0: {
                return INVALID;
            }
            case 1: {
                return SUM;
            }
            case 2: {
                return TOTAL_COUNT;
            }
            case 3: {
                return ACTIVE_COUNT;
            }
            case 4: {
                return MINIMUM;
            }
            case 5: {
                return MAXIMUM;
            }
            case 6: {
                return AVERAGE;
            }
            case 7: {
                return QUANTILE_05;
            }
            case 8: {
                return QUANTILE_10;
            }
            case 9: {
                return QUANTILE_25;
            }
            case 10: {
                return QUANTILE_50;
            }
            case 11: {
                return QUANTILE_75;
            }
            case 12: {
                return QUANTILE_90;
            }
            case 13: {
                return QUANTILE_95;
            }
            case 14: {
                return TRIMMED_AVERAGE_01;
            }
            case 15: {
                return TRIMMED_AVERAGE_05;
            }
            case 16: {
                return TRIMMED_AVERAGE_10;
            }
            case 17: {
                return TRIMMED_AVERAGE_25;
            }
            case 18: {
                return STANDARD_DEVIATION;
            }
            case 19: {
                return MEDIAN_ABSOLUTE_DEVIATION;
            }
            default:
                return INVALID;
        }
    }

    public static StatisticsMeasurement[] getDefaultMeasurementValues() {
        StatisticsMeasurement[] ret = new StatisticsMeasurement[StatisticsMeasurement.values().length - 2];

        for (int i = 2; i < StatisticsMeasurement.values().length; i++) {
            ret[i - 2] = StatisticsMeasurement.values()[i];
        }

        return ret;
    }

}
