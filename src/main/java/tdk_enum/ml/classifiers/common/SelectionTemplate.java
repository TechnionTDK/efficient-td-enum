package tdk_enum.ml.classifiers.common;

import tdk_enum.ml.classifiers.IClassifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class SelectionTemplate {
    private int firstId = -1;
    private int optimalId = -1;
    private int preferredId = -1;
    private int beneficialId = -1;

    private double firstPredictedRuntime = -1;
    private double optimalPredictedRuntime = -1;
    private double preferredPredictedRuntime = -1;
    private double beneficialPredictedRuntime = -1;

    private PredictedDecompositionPool decompositions = null;

    private double selectionOverhead = -1;

    public SelectionTemplate(int firstId,
                              int optimalId,
                              int preferredId,
                              int beneficialId,
                              double firstPredictedRuntime,
                              double optimalPredictedRuntime,
                              double preferredPredictedRuntime,
                              double beneficialPredictedRuntime,
                              PredictedDecompositionPool decompositions,
                              double selectionOverhead) {

        this.firstId = firstId;
        this.optimalId = optimalId;
        this.preferredId = preferredId;
        this.beneficialId = beneficialId;

        this.firstPredictedRuntime = firstPredictedRuntime;
        this.optimalPredictedRuntime = optimalPredictedRuntime;
        this.preferredPredictedRuntime = preferredPredictedRuntime;
        this.beneficialPredictedRuntime = beneficialPredictedRuntime;

        this.decompositions = decompositions;

        this.selectionOverhead = selectionOverhead;
    }

    public int getFirstId() {
        return firstId;
    }

    public int getOptimalId() {
        return optimalId;
    }

    public int getPreferredId() {
        return preferredId;
    }

    public int getBeneficialId() {
        return beneficialId;
    }

    public double getFirstPredictedRuntime() {
        return firstPredictedRuntime;
    }

    public double getOptimalPredictedRuntime() {
        return optimalPredictedRuntime;
    }

    public double getPreferredPredictedRuntime() {
        return preferredPredictedRuntime;
    }

    public double getBeneficialPredictedRuntime() {
        return beneficialPredictedRuntime;
    }

    public double getSelectionOverhead() {
        return selectionOverhead;
    }

    public double getPredictedRuntimeForId(int id) {
        double ret = Double.NaN;

        if (decompositions != null) {
            ret = decompositions.getPredictedRuntimeFortdId(id);
        }

        return ret;
    }

    public double getPredictedRuntimeForIndex(int index) {
        double ret = Double.NaN;

        if (decompositions != null) {
            ret = decompositions.getPredictedRuntimeForIndex(index);
        }

        return ret;
    }

    public int getPredictedRankForId(int id) {
        int ret = -1;

        if (decompositions != null) {
            double predictedRuntime =
                    decompositions.getPredictedRuntimeFortdId(id);

            ret = PerformanceRanking.getRank(predictedRuntime, getPredictedRuntimes());
        }

        return ret;
    }

    public int getPredictedRankForIndex(int index) {
        int ret = -1;

        if (decompositions != null) {
            double predictedRuntime =
                    decompositions.getPredictedRuntimeForIndex(index);

            ret = PerformanceRanking.getRank(predictedRuntime, getPredictedRuntimes());
        }

        return ret;
    }

//    public static SelectionTemplate generateSelectionTemplate(IClassifier classifier , DecompositionPool decompositions, File modelFile) {
//        SelectionTemplate ret = null;
//
//        if (decompositions != null && modelFile != null && decompositions.getSize() > 0) {
//            long start =
//                    System.currentTimeMillis();
//
//            PredictedDecompositionPool predictedInstances =
//                    classifier.predictDecompositions(decompositions, modelFile);
//
//            ret = generateSelectionTemplate(predictedInstances, start);
//        }
//
//        return ret;
//    }
//
//    public static SelectionTemplate generateSelectionTemplate(IClassifier classifier, DecompositionPool decompositions, List<File> modelFiles) {
//        SelectionTemplate ret = null;
//
//        if (decompositions != null && modelFiles != null && decompositions.getSize() > 0) {
//            long start =
//                    System.currentTimeMillis();
//
//            PredictedDecompositionPool predictedInstances =
//                    classifier.predictDecompositions(decompositions, modelFiles);
//
//            ret = generateSelectionTemplate(predictedInstances, start);
//        }
//
//        return ret;
//    }
//
//    public static SelectionTemplate generateSelectionTemplate(IClassifier classifier, DecompositionPool decompositions, List<File> modelFiles, List<Double> weights) {
//        SelectionTemplate ret = null;
//
//        if (decompositions != null && modelFiles != null && decompositions.getSize() > 0) {
//            long start =
//                    System.currentTimeMillis();
//
//            PredictedDecompositionPool predictedInstances =
//                    classifier.predictDecompositions(decompositions, modelFiles, weights);
//
//            ret = generateSelectionTemplate(predictedInstances, start);
//        }
//
//        return ret;
//    }

    public static SelectionTemplate generateSelectionTemplate(PredictedDecompositionPool predictedInstances, long startTime) {
        SelectionTemplate ret = null;

        if (predictedInstances != null && predictedInstances.getSize() > 0) {
            double minimum =
                    predictedInstances.getMinimumRuntime();

            double maximum =
                    predictedInstances.getMaximumRuntime();

            double limit = (maximum - minimum) * 0.05;

            int firstId =
                    predictedInstances.getFirstId();

            int optimalId =
                    predictedInstances.getOptimalId();

            int preferredId =
                    predictedInstances.getPreferredId(limit);

            int beneficialId =
                    predictedInstances.getBeneficialId();

            double firstPredictedRuntime =
                    predictedInstances.getPredictedRuntimeFortdId(firstId);

            double optimalPredictedRuntime =
                    predictedInstances.getPredictedRuntimeFortdId(optimalId);

            double preferredPredictedRuntime =
                    predictedInstances.getPredictedRuntimeFortdId(preferredId);

            double beneficialPredictedRuntime =
                    predictedInstances.getPredictedRuntimeFortdId(beneficialId);

            double overhead =
                    (double)(System.currentTimeMillis() - startTime) / 1000;

            ret = new SelectionTemplate(firstId,
                    optimalId,
                    preferredId,
                    beneficialId,
                    firstPredictedRuntime,
                    optimalPredictedRuntime,
                    preferredPredictedRuntime,
                    beneficialPredictedRuntime,
                    predictedInstances,
                    overhead);
        }

        return ret;
    }

    private List<Double> getPredictedRuntimes() {
        List<Double> ret = new ArrayList<>();

        if (decompositions != null) {
            int count =
                    decompositions.getSize();

            for (int i = 0; i < count; i++) {
                ret.add(decompositions.getPredictedRuntimeForIndex(i));
            }
        }

        return ret;
    }

}
