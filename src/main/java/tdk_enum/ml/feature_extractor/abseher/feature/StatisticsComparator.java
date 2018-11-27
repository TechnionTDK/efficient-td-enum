package tdk_enum.ml.feature_extractor.abseher.feature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class StatisticsComparator {

    public static double getDifference(double value1, double value2, double defaultDistance) {
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
