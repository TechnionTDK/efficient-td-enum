package tdk_enum.ml.classifiers.common;

import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsComparator;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class DecompositionDetails {

    private int tdId = 0;
    private String instance = null;
    private double[] features = null;

    protected DecompositionDetails(String instance, int tdId, double[] features) {
        this.tdId = tdId;
        this.features = features;
        this.instance = instance;
    }

    public int getTdId() {
        return tdId;
    }

    public double getRuntime() {
        double ret = Double.NaN;

        if (features != null && features.length == getFeatureCount()) {
            ret = features[features.length - 1];
        }

        return ret;
    }

    public File getInstanceFile() {
        File ret = null;

        if (instance != null) {
            ret = new File(instance);
        }

        return ret;
    }

    public String getInstancePath() {
        return instance;
    }

    public double getFeatureValue(int featureIndex) {
        return features[featureIndex];
    }

    public double getEuclideanDistance(DecompositionDetails other) {
        return getEuclideanDistance(other, Double.NaN);
    }

    public double getEuclideanDistance(DecompositionDetails other, double defaultDistance) {
        double ret = 0;

        if (other != null) {
            int featureCount = getFeatureCount();

            for (int i = 0; i < featureCount; i++) {
                ret += Math.pow(StatisticsComparator.getDifference(features[i], other.getFeatureValue(i), defaultDistance), 2);
            }

            ret = Math.sqrt(ret);
        }
        else {
            ret = defaultDistance;
        }

        return ret;
    }

    public double[] getRelevantAttributeValues() {
        return getRelevantAttributeValues(false);
    }

    public double[] getRelevantAttributeValues(boolean resetRuntime) {
        double[] ret = new double[getFeatureCount()];

        System.arraycopy(features, 0, ret, 0, ret.length);

        if (resetRuntime) {
            ret[ret.length - 1] = Double.NaN;
        }

        return ret;
    }

    public String toCSV() {
        return toCSV(getRuntime());
    }

    public String toCSV(double actualRuntime) {
        StringBuilder sb =
                new StringBuilder();

        DecimalFormat format =
                new DecimalFormat("0.000");

        int featureCount = getFeatureCount();

        sb.append("\"");
        if (instance != null) {
            sb.append(instance);
        }
        else {
            sb.append("<UNKNOWN>");
        }
        sb.append("\"");

        sb.append(",");
        sb.append(tdId);
        sb.append(",");

        for (int i = 0; i < featureCount; i++) {
            double value =
                    features[i];

            if (i == featureCount - 1) {
                value = actualRuntime;
            }

            if (Double.isNaN(value)) {
                sb.append("?");
            }
            else {
                sb.append(format.format(value));
            }

            if (i < featureCount - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public static int getFeatureCount() {
        return getFeatureNames().size();
    }

    public static List<String> getFeatureNames() {
        List<String> ret = new ArrayList<>();

//        ret.add("BagSizeActiveCount");
//        ret.add("BagSizeStatisticMedian");
//        ret.add("BagSizeArithmeticAverage");
//        ret.add("BagSizeStatisticalStandardDeviation");
//        ret.add("BagSizeMinimum");
//        ret.add("BagSizeMaximum");
//
//        ret.add("NonLeafNodeBagSizeActiveCount");
//        ret.add("NonLeafNodeBagSizeStatisticMedian");
//        ret.add("NonLeafNodeBagSizeArithmeticAverage");
//        ret.add("NonLeafNodeBagSizeStatisticalStandardDeviation");
//        ret.add("NonLeafNodeBagSizeMinimum");
//        ret.add("NonLeafNodeBagSizeMaximum");
//
//        ret.add("NonEmptyNodeBagSizeActiveCount");
//        ret.add("NonEmptyNodeBagSizeStatisticMedian");
//        ret.add("NonEmptyNodeBagSizeArithmeticAverage");
//        ret.add("NonEmptyNodeBagSizeStatisticalStandardDeviation");
//        ret.add("NonEmptyNodeBagSizeMinimum");
//        ret.add("NonEmptyNodeBagSizeMaximum");
//
//        ret.add("NodeDepthStatisticMedian");
//        ret.add("NodeDepthArithmeticAverage");
//        ret.add("NodeDepthStatisticalStandardDeviation");
//        ret.add("NodeDepthMinimum");
//        ret.add("NodeDepthMaximum");
//
//        ret.add("NonLeafNodeDepthStatisticMedian");
//        ret.add("NonLeafNodeDepthArithmeticAverage");
//        ret.add("NonLeafNodeDepthStatisticalStandardDeviation");
//        ret.add("NonLeafNodeDepthMinimum");
//        ret.add("NonLeafNodeDepthMaximum");
//
//        ret.add("NonEmptyNodeDepthStatisticMedian");
//        ret.add("NonEmptyNodeDepthArithmeticAverage");
//        ret.add("NonEmptyNodeDepthStatisticalStandardDeviation");
//        ret.add("NonEmptyNodeDepthMinimum");
//        ret.add("NonEmptyNodeDepthMaximum");
//
//        ret.add("IntroduceNodeDepthActiveCount");
//        ret.add("IntroduceNodeDepthStatisticMedian");
//        ret.add("IntroduceNodeDepthArithmeticAverage");
//        ret.add("IntroduceNodeDepthStatisticalStandardDeviation");
//        ret.add("IntroduceNodeDepthMinimum");
//        ret.add("IntroduceNodeDepthMaximum");
//
//        ret.add("ExchangeNodeDepthActiveCount");
//        ret.add("ExchangeNodeDepthStatisticMedian");
//        ret.add("ExchangeNodeDepthArithmeticAverage");
//        ret.add("ExchangeNodeDepthStatisticalStandardDeviation");
//        ret.add("ExchangeNodeDepthMinimum");
//        ret.add("ExchangeNodeDepthMaximum");
//
//        ret.add("ForgetNodeDepthActiveCount");
//        ret.add("ForgetNodeDepthStatisticMedian");
//        ret.add("ForgetNodeDepthArithmeticAverage");
//        ret.add("ForgetNodeDepthStatisticalStandardDeviation");
//        ret.add("ForgetNodeDepthMinimum");
//        ret.add("ForgetNodeDepthMaximum");
//
//        ret.add("JoinNodeDepthActiveCount");
//        ret.add("JoinNodeDepthStatisticMedian");
//        ret.add("JoinNodeDepthArithmeticAverage");
//        ret.add("JoinNodeDepthStatisticalStandardDeviation");
//        ret.add("JoinNodeDepthMinimum");
//        ret.add("JoinNodeDepthMaximum");
//
//        ret.add("LeafNodeDepthActiveCount");
//        ret.add("LeafNodeDepthStatisticMedian");
//        ret.add("LeafNodeDepthArithmeticAverage");
//        ret.add("LeafNodeDepthStatisticalStandardDeviation");
//        ret.add("LeafNodeDepthMinimum");
//        ret.add("LeafNodeDepthMaximum");
//
//        ret.add("IntroduceNodePercentage");
//        ret.add("ExchangeNodePercentage");
//        ret.add("ForgetNodePercentage");
//        ret.add("JoinNodePercentage");
//        ret.add("LeafNodePercentage");
//
//        ret.add("IntroduceNodeBagSizeStatisticMedian");
//        ret.add("IntroduceNodeBagSizeArithmeticAverage");
//        ret.add("IntroduceNodeBagSizeStatisticalStandardDeviation");
//        ret.add("IntroduceNodeBagSizeMinimum");
//        ret.add("IntroduceNodeBagSizeMaximum");
//
//        ret.add("ExchangeNodeBagSizeStatisticMedian");
//        ret.add("ExchangeNodeBagSizeArithmeticAverage");
//        ret.add("ExchangeNodeBagSizeStatisticalStandardDeviation");
//        ret.add("ExchangeNodeBagSizeMinimum");
//        ret.add("ExchangeNodeBagSizeMaximum");
//
//        ret.add("ForgetNodeBagSizeStatisticMedian");
//        ret.add("ForgetNodeBagSizeArithmeticAverage");
//        ret.add("ForgetNodeBagSizeStatisticalStandardDeviation");
//        ret.add("ForgetNodeBagSizeMinimum");
//        ret.add("ForgetNodeBagSizeMaximum");
//
//        ret.add("JoinNodeBagSizeStatisticMedian");
//        ret.add("JoinNodeBagSizeArithmeticAverage");
//        ret.add("JoinNodeBagSizeStatisticalStandardDeviation");
//        ret.add("JoinNodeBagSizeMinimum");
//        ret.add("JoinNodeBagSizeMaximum");
//
//        ret.add("LeafNodeBagSizeStatisticMedian");
//        ret.add("LeafNodeBagSizeArithmeticAverage");
//        ret.add("LeafNodeBagSizeStatisticalStandardDeviation");
//        ret.add("LeafNodeBagSizeMinimum");
//        ret.add("LeafNodeBagSizeMaximum");
//
//        ret.add("JoinNodeDistanceStatisticMedian");
//        ret.add("JoinNodeDistanceArithmeticAverage");
//        ret.add("JoinNodeDistanceStatisticalStandardDeviation");
//        ret.add("JoinNodeDistanceMinimum");
//        ret.add("JoinNodeDistanceMaximum");
//
//        ret.add("JoinNodeChildrenCountStatisticMedian");
//        ret.add("JoinNodeChildrenCountArithmeticAverage");
//        ret.add("JoinNodeChildrenCountStatisticalStandardDeviation");
//        ret.add("JoinNodeChildrenCountMinimum");
//        ret.add("JoinNodeChildrenCountMaximum");
//
//        ret.add("ItemLifetimeStatisticMedian");
//        ret.add("ItemLifetimeArithmeticAverage");
//        ret.add("ItemLifetimeStatisticalStandardDeviation");
//        ret.add("ItemLifetimeMinimum");
//        ret.add("ItemLifetimeMaximum");
//
//        ret.add("ContainerCountStatisticMedian");
//        ret.add("ContainerCountArithmeticAverage");
//        ret.add("ContainerCountStatisticalStandardDeviation");
//        ret.add("ContainerCountMinimum");
//        ret.add("ContainerCountMaximum");
//
//        ret.add("BranchingFactorStatisticMedian");
//        ret.add("BranchingFactorArithmeticAverage");
//        ret.add("BranchingFactorStatisticalStandardDeviation");
//        ret.add("BranchingFactorMinimum");
//        ret.add("BranchingFactorMaximum");
//
//        ret.add("BalancednessFactor");
//        ret.add("CummulativeBagSize");
//        ret.add("CummulativeIntroduceNodeBagSize");
//        ret.add("CummulativeExchangeNodeBagSize");
//        ret.add("CummulativeForgetNodeBagSize");
//        ret.add("CummulativeJoinNodeBagSize");
//        ret.add("CummulativeLeafNodeBagSize");
//
//        ret.add("IntroduceNodeIntroductionCountStatisticMedian");
//        ret.add("IntroduceNodeIntroductionCountArithmeticAverage");
//        ret.add("IntroduceNodeIntroductionCountStatisticalStandardDeviation");
//        ret.add("IntroduceNodeIntroductionCountMinimum");
//        ret.add("IntroduceNodeIntroductionCountMaximum");
//
//        ret.add("ForgetNodeRemovalCountStatisticMedian");
//        ret.add("ForgetNodeRemovalCountArithmeticAverage");
//        ret.add("ForgetNodeRemovalCountStatisticalStandardDeviation");
//        ret.add("ForgetNodeRemovalCountMinimum");
//        ret.add("ForgetNodeRemovalCountMaximum");
//
//        ret.add("IntroducedVertexNeighborCountStatisticMedian");
//        ret.add("IntroducedVertexNeighborCountArithmeticAverage");
//        ret.add("IntroducedVertexNeighborCountStatisticalStandardDeviation");
//        ret.add("IntroducedVertexNeighborCountMinimum");
//        ret.add("IntroducedVertexNeighborCountMaximum");
//
//        ret.add("ForgottenVertexNeighborCountStatisticMedian");
//        ret.add("ForgottenVertexNeighborCountArithmeticAverage");
//        ret.add("ForgottenVertexNeighborCountStatisticalStandardDeviation");
//        ret.add("ForgottenVertexNeighborCountMinimum");
//        ret.add("ForgottenVertexNeighborCountMaximum");
//
//        ret.add("IntroducedVertexConnectednessFactorStatisticMedian");
//        ret.add("IntroducedVertexConnectednessFactorArithmeticAverage");
//        ret.add("IntroducedVertexConnectednessFactorStatisticalStandardDeviation");
//        ret.add("IntroducedVertexConnectednessFactorMinimum");
//        ret.add("IntroducedVertexConnectednessFactorMaximum");
//
//        ret.add("ForgottenVertexConnectednessFactorStatisticMedian");
//        ret.add("ForgottenVertexConnectednessFactorArithmeticAverage");
//        ret.add("ForgottenVertexConnectednessFactorStatisticalStandardDeviation");
//        ret.add("ForgottenVertexConnectednessFactorMinimum");
//        ret.add("ForgottenVertexConnectednessFactorMaximum");
//
//        ret.add("ExchangeNodeIntroductionCountStatisticMedian");
//        ret.add("ExchangeNodeIntroductionCountArithmeticAverage");
//        ret.add("ExchangeNodeIntroductionCountStatisticalStandardDeviation");
//        ret.add("ExchangeNodeIntroductionCountMinimum");
//        ret.add("ExchangeNodeIntroductionCountMaximum");
//
//        ret.add("ExchangeNodeRemovalCountStatisticMedian");
//        ret.add("ExchangeNodeRemovalCountArithmeticAverage");
//        ret.add("ExchangeNodeRemovalCountStatisticalStandardDeviation");
//        ret.add("ExchangeNodeRemovalCountMinimum");
//        ret.add("ExchangeNodeRemovalCountMaximum");
//
//        ret.add("ExchangeNodeChangeFactorStatisticMedian");
//        ret.add("ExchangeNodeChangeFactorArithmeticAverage");
//        ret.add("ExchangeNodeChangeFactorStatisticalStandardDeviation");
//        ret.add("ExchangeNodeChangeFactorMinimum");
//        ret.add("ExchangeNodeChangeFactorMaximum");
//
//        ret.add("BagAdjacencyFactorStatisticMedian");
//        ret.add("BagAdjacencyFactorArithmeticAverage");
//        ret.add("BagAdjacencyFactorStatisticalStandardDeviation");
//        ret.add("BagAdjacencyFactorMinimum");
//        ret.add("BagAdjacencyFactorMaximum");
//
//        ret.add("BagConnectednessFactorStatisticMedian");
//        ret.add("BagConnectednessFactorArithmeticAverage");
//        ret.add("BagConnectednessFactorStatisticalStandardDeviation");
//        ret.add("BagConnectednessFactorMinimum");
//        ret.add("BagConnectednessFactorMaximum");
//
//        ret.add("BagNeighborhoodCoverageFactorStatisticMedian");
//        ret.add("BagNeighborhoodCoverageFactorArithmeticAverage");
//        ret.add("BagNeighborhoodCoverageFactorStatisticalStandardDeviation");
//        ret.add("BagNeighborhoodCoverageFactorMinimum");
//        ret.add("BagNeighborhoodCoverageFactorMaximum");
//
//        ret.add("DecompositionOverheadRatio");
//
//        ret.add("CummulativeIntroducedVertexNeighborCount");
//        ret.add("CummulativeForgottenVertexNeighborCount");
//
//        ret.add("UserTime");
        ret.add("UserTime");
        ret.add("BagSizeActiveCount");
        ret.add("BagSizeStatisticMedian");
        ret.add("BagSizeArithmeticAverage");
        ret.add("BagSizeStatisticalStandardDeviation");
        ret.add("BagSizeMaximum");
        ret.add("NonLeafNodeBagSizeActiveCount");
        ret.add("NonLeafNodeBagSizeStatisticMedian");
        ret.add("NonLeafNodeBagSizeArithmeticAverage");
        ret.add("NonLeafNodeBagSizeStatisticalStandardDeviation");
        ret.add("NonLeafNodeBagSizeMaximum");
        ret.add("NodeDepthStatisticMedian");
        ret.add("NodeDepthArithmeticAverage");
        ret.add("NodeDepthStatisticalStandardDeviation");
        ret.add("NodeDepthMaximum");
        ret.add("NonLeafNodeDepthStatisticMedian");
        ret.add("NonLeafNodeDepthArithmeticAverage");
        ret.add("NonLeafNodeDepthStatisticalStandardDeviation");
        ret.add("NonLeafNodeDepthMaximum");
        ret.add("IntroduceNodeDepthActiveCount");
        ret.add("IntroduceNodeDepthStatisticMedian");
        ret.add("IntroduceNodeDepthArithmeticAverage");
        ret.add("IntroduceNodeDepthStatisticalStandardDeviation");
        ret.add("IntroduceNodeDepthMaximum");
        ret.add("ForgetNodeDepthActiveCount");
        ret.add("ForgetNodeDepthStatisticMedian");
        ret.add("ForgetNodeDepthArithmeticAverage");
        ret.add("ForgetNodeDepthStatisticalStandardDeviation");
        ret.add("ForgetNodeDepthMaximum");
        ret.add("JoinNodeDepthActiveCount");
        ret.add("JoinNodeDepthStatisticMedian");
        ret.add("JoinNodeDepthArithmeticAverage");
        ret.add("JoinNodeDepthStatisticalStandardDeviation");
        ret.add("JoinNodeDepthMaximum");
        ret.add("LeafNodeDepthActiveCount");
        ret.add("LeafNodeDepthStatisticMedian");
        ret.add("LeafNodeDepthArithmeticAverage");
        ret.add("LeafNodeDepthStatisticalStandardDeviation");
        ret.add("LeafNodeDepthMaximum");
        ret.add("IntroduceNodePercentage");
        ret.add("ForgetNodePercentage");
        ret.add("JoinNodePercentage");
        ret.add("LeafNodePercentage");
        ret.add("IntroduceNodeBagSizeStatisticMedian");
        ret.add("IntroduceNodeBagSizeArithmeticAverage");
        ret.add("IntroduceNodeBagSizeStatisticalStandardDeviation");
        ret.add("IntroduceNodeBagSizeMaximum");
        ret.add("ForgetNodeBagSizeStatisticMedian");
        ret.add("ForgetNodeBagSizeArithmeticAverage");
        ret.add("ForgetNodeBagSizeStatisticalStandardDeviation");
        ret.add("ForgetNodeBagSizeMaximum");
        ret.add("JoinNodeBagSizeStatisticMedian");
        ret.add("JoinNodeBagSizeArithmeticAverage");
        ret.add("JoinNodeBagSizeStatisticalStandardDeviation");
        ret.add("JoinNodeBagSizeMaximum");
        ret.add("LeafNodeBagSizeStatisticMedian");
        ret.add("LeafNodeBagSizeArithmeticAverage");
        ret.add("LeafNodeBagSizeStatisticalStandardDeviation");
        ret.add("LeafNodeBagSizeMaximum");
        ret.add("JoinNodeDistanceStatisticMedian");
        ret.add("JoinNodeDistanceArithmeticAverage");
        ret.add("JoinNodeDistanceStatisticalStandardDeviation");
        ret.add("JoinNodeDistanceMaximum");
        ret.add("ItemLifetimeStatisticMedian");
        ret.add("ItemLifetimeArithmeticAverage");
        ret.add("ItemLifetimeStatisticalStandardDeviation");
        ret.add("ItemLifetimeMaximum");
        ret.add("ContainerCountStatisticMedian");
        ret.add("ContainerCountArithmeticAverage");
        ret.add("ContainerCountStatisticalStandardDeviation");
        ret.add("ContainerCountMaximum");
        ret.add("BranchingFactorStatisticMedian");
        ret.add("BranchingFactorArithmeticAverage");
        ret.add("BranchingFactorStatisticalStandardDeviation");
        ret.add("BranchingFactorMaximum");
        ret.add("BalancednessFactor");
        ret.add("CummulativeBagSize");
        ret.add("CummulativeIntroduceNodeBagSize");
        ret.add("CummulativeForgetNodeBagSize");
        ret.add("CummulativeJoinNodeBagSize");
        ret.add("CummulativeLeafNodeBagSize");
        ret.add("IntroducedVertexNeighborCountStatisticMedian");
        ret.add("IntroducedVertexNeighborCountArithmeticAverage");
        ret.add("IntroducedVertexNeighborCountStatisticalStandardDeviation");
        ret.add("IntroducedVertexNeighborCountMaximum");
        ret.add("ForgottenVertexNeighborCountStatisticMedian");
        ret.add("ForgottenVertexNeighborCountArithmeticAverage");
        ret.add("ForgottenVertexNeighborCountStatisticalStandardDeviation");
        ret.add("ForgottenVertexNeighborCountMaximum");
        ret.add("IntroducedVertexConnectednessFactorStatisticMedian");
        ret.add("IntroducedVertexConnectednessFactorArithmeticAverage");
        ret.add("IntroducedVertexConnectednessFactorStatisticalStandardDeviation");
        ret.add("IntroducedVertexConnectednessFactorMaximum");
        ret.add("ForgottenVertexConnectednessFactorStatisticMedian");
        ret.add("ForgottenVertexConnectednessFactorArithmeticAverage");
        ret.add("ForgottenVertexConnectednessFactorStatisticalStandardDeviation");
        ret.add("ForgottenVertexConnectednessFactorMaximum");
        ret.add("BagAdjacencyFactorStatisticMedian");
        ret.add("BagAdjacencyFactorArithmeticAverage");
        ret.add("BagAdjacencyFactorStatisticalStandardDeviation");
        ret.add("BagAdjacencyFactorMaximum");
        ret.add("BagConnectednessFactorStatisticMedian");
        ret.add("BagConnectednessFactorArithmeticAverage");
        ret.add("BagConnectednessFactorStatisticalStandardDeviation");
        ret.add("BagConnectednessFactorMaximum");
        ret.add("BagNeighborhoodCoverageFactorStatisticMedian");
        ret.add("BagNeighborhoodCoverageFactorArithmeticAverage");
        ret.add("BagNeighborhoodCoverageFactorStatisticalStandardDeviation");
        ret.add("BagNeighborhoodCoverageFactorMaximum");
        ret.add("DecompositionOverheadRatio");
        ret.add("CummulativeDepthWeightedBagSize");
        ret.add("CummulativeRootDistanceWeightedBagSize");
        ret.add("CummulativeDepthWeightedIntroduceNodeBagSize");
        ret.add("CummulativeRootDistanceWeightedIntroduceNodeBagSize");
        ret.add("CummulativeDepthWeightedForgetNodeBagSize");
        ret.add("CummulativeRootDistanceWeightedForgetNodeBagSize");
        ret.add("CummulativeDepthWeightedJoinNodeBagSize");
        ret.add("CummulativeRootDistanceWeightedJoinNodeBagSize");
        ret.add("CummulativeIntroducedVertexNeighborCount");
        ret.add("CummulativeDepthWeightedIntroducedVertexNeighborCount");
        ret.add("CummulativeRootDistanceWeightedIntroducedVertexNeighborCount");
        ret.add("CummulativeForgottenVertexNeighborCount");
        ret.add("CummulativeDepthWeightedForgottenVertexNeighborCount");
        ret.add("CummulativeRootDistanceWeightedForgottenVertexNeighborCount");

        return ret;


    }

    public static String getCSVHeader() {
        StringBuilder sb =
                new StringBuilder();

        List<String> featureNames = getFeatureNames();

        sb.append("Instance,");
        sb.append("TdID,");

        for (int i = 0; i < featureNames.size(); i++) {
            sb.append(featureNames.get(i));

            if (i < featureNames.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public static DecompositionDetails getInstance(FeatureExtractionResult result) {
        DecompositionDetails ret = null;

        if (result != null) {
            ret = new DecompositionDetails(result.getInstancePath(), result.getTdID(), getRelevantAttributeValues(result));
        }

        return ret;
    }

    public static DecompositionDetails getInstance(String instancePath, int tdId, double[] attributeValues) {
        DecompositionDetails ret = null;

        if (instancePath != null && attributeValues != null && attributeValues.length == getFeatureCount()) {
            ret = new DecompositionDetails(instancePath, tdId, attributeValues);
        }

        return ret;
    }

    public static double[] getRelevantAttributeValues(FeatureExtractionResult result) {
//        double[] ret = new double[getFeatureCount()];
//
//        for (int i = 0; i < ret.length; i++) {
//            ret[i] = Double.NaN;
//        }
//
//        if (result != null) {
//            List<String> featureNames = getFeatureNames();
//
//            for (int i = 0; i < result.getMeasurementCount(); i++) {
//                FeatureMeasurement measurement =
//                        result.accessMeasurement(i);
//
//                if (measurement != null) {
//                    StatisticsSummary summary =
//                            measurement.getMeasurementResult();
//
//                    String featureName = measurement.getFeatureName();
//
//                    switch (featureName) {
//                        case "BagSize":
//                        case "NonLeafNodeBagSize":
//                        case "NonEmptyNodeBagSize":
//                        case "IntroduceNodeDepth":
//                        case "ExchangeNodeDepth":
//                        case "ForgetNodeDepth":
//                        case "JoinNodeDepth":
//                        case "LeafNodeDepth": {
//                            ret[featureNames.indexOf(featureName + "ActiveCount")] = summary.getActiveCount();
//                            ret[featureNames.indexOf(featureName + "StatisticMedian")] = summary.getMedian();
//                            ret[featureNames.indexOf(featureName + "ArithmeticAverage")] = summary.getAverage();
//                            ret[featureNames.indexOf(featureName + "StatisticalStandardDeviation")] = summary.getStandardDeviation();
//                            ret[featureNames.indexOf(featureName + "Minimum")] = summary.getMinimum();
//                            ret[featureNames.indexOf(featureName + "Maximum")] = summary.getMaximum();
//
//                            break;
//                        }
//                        case "NodeDepth":
//                        case "NonLeafNodeDepth":
//                        case "NonEmptyNodeDepth":
//                        case "IntroduceNodeBagSize":
//                        case "ExchangeNodeBagSize":
//                        case "ForgetNodeBagSize":
//                        case "JoinNodeBagSize":
//                        case "LeafNodeBagSize":
//                        case "JoinNodeDistance":
//                        case "JoinNodeChildrenCount":
//                        case "ItemLifetime":
//                        case "ContainerCount":
//                        case "BranchingFactor":
//                        case "IntroduceNodeIntroductionCount":
//                        case "ForgetNodeRemovalCount":
//                        case "IntroducedVertexNeighborCount":
//                        case "ForgottenVertexNeighborCount":
//                        case "IntroducedVertexConnectednessFactor":
//                        case "ForgottenVertexConnectednessFactor":
//                        case "ExchangeNodeIntroductionCount":
//                        case "ExchangeNodeRemovalCount":
//                        case "ExchangeNodeChangeFactor":
//                        case "BagAdjacencyFactor":
//                        case "BagConnectednessFactor":
//                        case "BagNeighborhoodCoverageFactor": {
//                            ret[featureNames.indexOf(featureName + "StatisticMedian")] = summary.getMedian();
//                            ret[featureNames.indexOf(featureName + "ArithmeticAverage")] = summary.getAverage();
//                            ret[featureNames.indexOf(featureName + "StatisticalStandardDeviation")] = summary.getStandardDeviation();
//                            ret[featureNames.indexOf(featureName + "Minimum")] = summary.getMinimum();
//                            ret[featureNames.indexOf(featureName + "Maximum")] = summary.getMaximum();
//
//                            break;
//                        }
//                        case "IntroduceNodePercentage":
//                        case "ExchangeNodePercentage":
//                        case "ForgetNodePercentage":
//                        case "JoinNodePercentage":
//                        case "LeafNodePercentage":
//                        case "BalancednessFactor":
//                        case "CummulativeBagSize":
//                        case "CummulativeIntroduceNodeBagSize":
//                        case "CummulativeExchangeNodeBagSize":
//                        case "CummulativeForgetNodeBagSize":
//                        case "CummulativeJoinNodeBagSize":
//                        case "CummulativeLeafNodeBagSize":
//                        case "DecompositionOverheadRatio":
//                        case "CummulativeIntroducedVertexNeighborCount":
//                        case "CummulativeForgottenVertexNeighborCount": {
//                            ret[featureNames.indexOf(featureName)] = summary.getAverage();
//
//                            break;
//                        }
//                        default: {
//                            break;
//                        }
//                    }
//                }
//            }
//
//            ret[ret.length - 1] = result.getUserTime();
//        }
//
//        return ret;
//    }
        double[] ret = new double[getFeatureCount()];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = Double.NaN;
        }

        List<String> featureNames = getFeatureNames();

        for (int i = 0; i < result.getMeasurementCount(); i++) {
            FeatureMeasurement measurement =
                    result.accessMeasurement(i);

            if (measurement != null) {
                StatisticsSummary summary =
                        measurement.getMeasurementResult();

                String featureName = measurement.getFeatureName();

                switch (featureName) {
                    case "BagSize":
                    case "NonLeafNodeBagSize":
                        //   case "NonEmptyNodeBagSize":
                    case "IntroduceNodeDepth":
                    case "ExchangeNodeDepth":
                    case "ForgetNodeDepth":
                    case "JoinNodeDepth":
                    case "LeafNodeDepth": {
                        try
                        {
                            ret = updateAtributeValues(ret, featureNames.indexOf(featureName + "ActiveCount"), summary.getActiveCount());
                            //ret[featureNames.indexOf(featureName + "ActiveCount")] = summary.getActiveCount();

                            ret = updateAtributeValues(ret, featureNames.indexOf(featureName + "StatisticMedian"), summary.getMedian());
//                            ret[featureNames.indexOf(featureName + "StatisticMedian")] = summary.getMedian();

                            ret = updateAtributeValues(ret,featureNames.indexOf(featureName + "ArithmeticAverage"),  summary.getAverage());
//                            ret[featureNames.indexOf(featureName + "ArithmeticAverage")] = summary.getAverage();

                            ret = updateAtributeValues(ret,featureNames.indexOf(featureName + "StatisticalStandardDeviation"), summary.getStandardDeviation() );
//                            ret[featureNames.indexOf(featureName + "StatisticalStandardDeviation")] = summary.getStandardDeviation();

                            ret = updateAtributeValues(ret, featureNames.indexOf(featureName + "Minimum"), summary.getMinimum());
                            // ret[featureNames.indexOf(featureName + "Minimum")] = summary.getMinimum();

                            ret = updateAtributeValues(ret,featureNames.indexOf(featureName + "Maximum"), summary.getMaximum() );
//                            ret[featureNames.indexOf(featureName + "Maximum")] = summary.getMaximum();
                        }
                        catch (Exception e)
                        {
                            System.out.println(featureName);
                            e.printStackTrace();
                        }


                        break;
                    }
                    case "NodeDepth":
                    case "NonLeafNodeDepth":
                    case "NonEmptyNodeDepth":
                    case "IntroduceNodeBagSize":
                    case "ExchangeNodeBagSize":
                    case "ForgetNodeBagSize":
                    case "JoinNodeBagSize":
                    case "LeafNodeBagSize":
                    case "JoinNodeDistance":
                    case "JoinNodeChildrenCount":
                    case "ItemLifetime":
                    case "ContainerCount":
                    case "BranchingFactor":
                    case "IntroduceNodeIntroductionCount":
                    case "ForgetNodeRemovalCount":
                    case "IntroducedVertexNeighborCount":
                    case "ForgottenVertexNeighborCount":
                    case "IntroducedVertexConnectednessFactor":
                    case "ForgottenVertexConnectednessFactor":
                    case "ExchangeNodeIntroductionCount":
                    case "ExchangeNodeRemovalCount":
                    case "ExchangeNodeChangeFactor":
                    case "BagAdjacencyFactor":
                    case "BagConnectednessFactor":
                    case "BagNeighborhoodCoverageFactor": {
                        try
                        {
                            ret = updateAtributeValues(ret, featureNames.indexOf(featureName + "StatisticMedian"), summary.getMedian());
//                            ret[featureNames.indexOf(featureName + "StatisticMedian")] = summary.getMedian();
                            ret = updateAtributeValues(ret,featureNames.indexOf(featureName + "ArithmeticAverage"), summary.getAverage() );
//                            ret[featureNames.indexOf(featureName + "ArithmeticAverage")] = summary.getAverage();
                            ret = updateAtributeValues(ret,featureNames.indexOf(featureName + "StatisticalStandardDeviation"), summary.getStandardDeviation() );
//                            ret[featureNames.indexOf(featureName + "StatisticalStandardDeviation")] = summary.getStandardDeviation();
                            ret = updateAtributeValues(ret, featureNames.indexOf(featureName + "Minimum"), summary.getMinimum());
                            // ret[featureNames.indexOf(featureName + "Minimum")] = summary.getMinimum();

                            ret = updateAtributeValues(ret, featureNames.indexOf(featureName + "Maximum"), summary.getMaximum() );
//                            ret[featureNames.indexOf(featureName + "Maximum")] = summary.getMaximum();
                        }
                        catch (Exception e)
                        {
                            System.out.println(featureName);
                            e.printStackTrace();
                        }


                        break;
                    }
                    case "IntroduceNodePercentage":
                    case "ExchangeNodePercentage":
                    case "ForgetNodePercentage":
                    case "JoinNodePercentage":
                    case "LeafNodePercentage":
                    case "BalancednessFactor":
                    case "CummulativeBagSize":
                    case "CummulativeIntroduceNodeBagSize":
                    case "CummulativeExchangeNodeBagSize":
                    case "CummulativeForgetNodeBagSize":
                    case "CummulativeJoinNodeBagSize":
                    case "CummulativeLeafNodeBagSize":
                    case "DecompositionOverheadRatio":
                    case "CummulativeForgottenVertexNeighborCount":
                    case "CummulativeDepthWeightedBagSize":
                    case "CummulativeRootDistanceWeightedBagSize":
                    case "CummulativeDepthWeightedIntroduceNodeBagSize":
                    case "CummulativeRootDistanceWeightedIntroduceNodeBagSize":
                    case "CummulativeDepthWeightedForgetNodeBagSize":
                    case "CummulativeRootDistanceWeightedForgetNodeBagSize":
                    case "CummulativeDepthWeightedJoinNodeBagSize":
                    case "CummulativeRootDistanceWeightedJoinNodeBagSize":
                    case "CummulativeIntroducedVertexNeighborCount":
                    case "CummulativeDepthWeightedIntroducedVertexNeighborCount":
                    case "CummulativeRootDistanceWeightedIntroducedVertexNeighborCount":
                    case "CummulativeDepthWeightedForgottenVertexNeighborCount":
                    case "CummulativeRootDistanceWeightedForgottenVertexNeighborCount":


                    {
                        ret = updateAtributeValues(ret, featureNames.indexOf(featureName),summary.getAverage() );
                        //   ret[featureNames.indexOf(featureName)] = summary.getAverage();

                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }

        ret = updateAtributeValues(ret, featureNames.indexOf("UsetTime"), result.getUserTime());
//        ret[ret.length - 1] = getUserTime();

        return ret;
    }

    static double[] updateAtributeValues(double[] values, int index, double value)
    {
        if(index >= 0 && index < values.length)
        {
            values[index] = value;
        }
        return values;
    }

}
