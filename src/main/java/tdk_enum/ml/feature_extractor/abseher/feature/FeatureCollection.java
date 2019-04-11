package tdk_enum.ml.feature_extractor.abseher.feature;

import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.GraphFeature;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.internal.ComponentSizeFeature;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.internal.EdgeProbabilityFeature;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.internal.GraphEccentricityFeature;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.internal.GraphSizeFeature;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.TreeDecompositionFeature;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class FeatureCollection {

    private List<Feature> features = null;

    public FeatureCollection() {
        this.features = new ArrayList<>();
    }

    public void addFeature(Feature feature) {
        if (feature != null && !features.contains(feature)) {
            features.add(feature);
        }
    }

    public void removeFeature(Feature feature) {
        if (feature != null && features.contains(feature)) {
            features.remove(feature);
        }
    }

    public FeatureExtractionResult getEvaluationResult(int tdID,
                                                       IMLGraph instance,
                                                       ITreeDecomposition decomposition) {
        FeatureExtractionResult ret = null;

        if (instance != null && decomposition != null) {
            ret = new FeatureExtractionResult(tdID,
                    instance);

            for (Feature feature : features) {
                if (feature != null) {
                    if (feature instanceof GraphFeature) {
                        FeatureMeasurement measurement =
                                ((GraphFeature)feature).extractMeasurement(instance);

                        if (measurement != null) {
                            ret.addMeasurement(measurement);
                        }
                    }
                    else if (feature instanceof TreeDecompositionFeature) {
                        FeatureMeasurement measurement =
                                ((TreeDecompositionFeature)feature).extractMeasurement(instance,
                                        decomposition);

                        if (measurement != null) {
                            ret.addMeasurement(measurement);
                        }
                    }
                }
            }
        }

        return ret;
    }

    public FeatureExtractionResult getEvaluationResult(int tdID,
                                                       IMLGraph instance,
                                                       double userTime,
                                                       double systemTime,
                                                       double wallClockTime,
                                                       double memoryConsumption,
                                                       boolean memoryError,
                                                       boolean timeoutError,
                                                       boolean exitCodeError,
                                                       ITreeDecomposition decomposition) {
        FeatureExtractionResult ret = null;

        if (instance != null && decomposition != null) {
            ret = new FeatureExtractionResult(tdID,
                    instance,
                    userTime,
                    systemTime,
                    wallClockTime,
                    memoryConsumption,
                    memoryError,
                    timeoutError,
                    exitCodeError);

            for (Feature feature : features) {
                if (feature != null) {
                    if (feature instanceof GraphFeature) {
                        FeatureMeasurement measurement =
                                ((GraphFeature)feature).extractMeasurement(instance);

                        if (measurement != null) {
                            ret.addMeasurement(measurement);
                        }
                    }
                    else if (feature instanceof TreeDecompositionFeature) {
                        FeatureMeasurement measurement =
                                ((TreeDecompositionFeature)feature).extractMeasurement(instance,
                                        decomposition);

                        if (measurement != null) {
                            ret.addMeasurement(measurement);
                        }
                    }
                }
            }
        }

        return ret;
    }

    public void printEvaluationResult(IMLGraph instance, ITreeDecomposition td) {
        printEvaluationResult(instance, td, 0);
    }

    public void printEvaluationResult(IMLGraph instance, ITreeDecomposition td, int indentationWidth) {
        if (td != null) {
            for (int i = 0; i < features.size(); i++) {
                Feature feature = features.get(i);

                System.out.println(StringOperations.indentString(feature.getName(),
                        indentationWidth));

                FeatureMeasurement measurement = null;

                if (feature instanceof GraphFeature) {
                    measurement =
                            ((GraphFeature)feature).extractMeasurement(instance);
                }
                else if (feature instanceof TreeDecompositionFeature) {
                    measurement =
                            ((TreeDecompositionFeature)feature).extractMeasurement(instance, td);
                }

                if (measurement != null) {
                    measurement.print(indentationWidth + 3);
                }
                else {
                    System.out.println(StringOperations.indentString("EXTRACTION FAILED!",
                            indentationWidth + 3));
                }

                if (i < features.size() - 1) {
                    System.out.println();
                }
            }
        }
    }

    public static FeatureCollection getDefaultFeatureCollection() {
        return getDefaultFeatureCollection(true);
    }

    public static FeatureCollection getDefaultFeatureCollection(boolean includeInstanceFeatures) {
        FeatureCollection ret = new FeatureCollection();

        if (includeInstanceFeatures) {
            ret.addFeature(new GraphSizeFeature());
            ret.addFeature(new ComponentSizeFeature());
            ret.addFeature(new EdgeProbabilityFeature());
            ret.addFeature(new GraphEccentricityFeature());
        }

        ret.addFeature(new BagSizeFeature());
        ret.addFeature(new NonLeafNodeBagSizeFeature());
        ret.addFeature(new NonEmptyNodeBagSizeFeature());

        ret.addFeature(new NodeDepthFeature());
        ret.addFeature(new NonLeafNodeDepthFeature());
        ret.addFeature(new NonEmptyNodeDepthFeature());

        ret.addFeature(new IntroduceNodeDepthFeature());
        ret.addFeature(new ExchangeNodeDepthFeature());
        ret.addFeature(new ForgetNodeDepthFeature());
        ret.addFeature(new JoinNodeDepthFeature());
        ret.addFeature(new LeafNodeDepthFeature());

        ret.addFeature(new IntroduceNodePercentageFeature());
        ret.addFeature(new ExchangeNodePercentageFeature());
        ret.addFeature(new ForgetNodePercentageFeature());
        ret.addFeature(new JoinNodePercentageFeature());
        ret.addFeature(new LeafNodePercentageFeature());

        ret.addFeature(new IntroduceNodeBagSizeFeature());
        ret.addFeature(new ExchangeNodeBagSizeFeature());
        ret.addFeature(new ForgetNodeBagSizeFeature());
        ret.addFeature(new JoinNodeBagSizeFeature());
        ret.addFeature(new LeafNodeBagSizeFeature());

        ret.addFeature(new JoinNodeDistanceFeature());
        ret.addFeature(new JoinNodeChildrenCountFeature());
        ret.addFeature(new ItemLifetimeFeature());
        ret.addFeature(new ContainerCountFeature());
        ret.addFeature(new BranchingFactorFeature());
        ret.addFeature(new BalancednessFactorFeature());

        ret.addFeature(new CummulativeBagSizeFeature());
        ret.addFeature(new CummulativeIntroduceNodeBagSizeFeature());
        ret.addFeature(new CummulativeExchangeNodeBagSizeFeature());
        ret.addFeature(new CummulativeForgetNodeBagSizeFeature());
        ret.addFeature(new CummulativeJoinNodeBagSizeFeature());
        ret.addFeature(new CummulativeLeafNodeBagSizeFeature());

        ret.addFeature(new IntroduceNodeIntroductionCountFeature());
        ret.addFeature(new ForgetNodeRemovalCountFeature());

        ret.addFeature(new IntroducedVertexNeighborCountFeature());
        ret.addFeature(new ForgottenVertexNeighborCountFeature());
        ret.addFeature(new IntroducedVertexConnectednessFactorFeature());
        ret.addFeature(new ForgottenVertexConnectednessFactorFeature());

        ret.addFeature(new ExchangeNodeIntroductionCountFeature());
        ret.addFeature(new ExchangeNodeRemovalCountFeature());
        ret.addFeature(new ExchangeNodeChangeFactorFeature());

        ret.addFeature(new BagAdjacencyFactorFeature());
        ret.addFeature(new BagConnectednessFactorFeature());
        ret.addFeature(new BagNeighborhoodCoverageFactorFeature());

        ret.addFeature(new ComplexityMeasureFeature());
        ret.addFeature(new DecompositionOverheadRatioFeature());
        ret.addFeature(new CummulativeDepthWeightedBagSizeFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedBagSizeFeature());
        ret.addFeature(new CummulativeDepthWeightedIntroduceNodeBagSizeFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedIntroduceNodeBagSizeFeature());
        ret.addFeature(new CummulativeDepthWeightedExchangeNodeBagSizeFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedExchangeNodeBagSizeFeature());
        ret.addFeature(new CummulativeDepthWeightedForgetNodeBagSizeFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedForgetNodeBagSizeFeature());
        ret.addFeature(new CummulativeDepthWeightedJoinNodeBagSizeFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedJoinNodeBagSizeFeature());
        ret.addFeature(new CummulativeDepthWeightedLeafNodeBagSizeFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedLeafNodeBagSizeFeature());

        ret.addFeature(new CummulativeIntroducedVertexNeighborCountFeature());
        ret.addFeature(new CummulativeDepthWeightedIntroducedVertexNeighborCountFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedIntroducedVertexNeighborCountFeature());
        ret.addFeature(new CummulativeForgottenVertexNeighborCountFeature());
        ret.addFeature(new CummulativeDepthWeightedForgottenVertexNeighborCountFeature());
        ret.addFeature(new CummulativeRootDistanceWeightedForgottenVertexNeighborCountFeature());

        return ret;
    }

}