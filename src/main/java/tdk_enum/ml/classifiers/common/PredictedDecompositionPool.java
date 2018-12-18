package tdk_enum.ml.classifiers.common;

import java.util.*;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class PredictedDecompositionPool extends DecompositionPool {
    private List<Double> predictedRuntimes = null;

    private PredictedDecompositionPool(DecompositionPool decompositionPool, List<Double> predictedRuntimes) {
        super(decompositionPool);

        this.predictedRuntimes = predictedRuntimes;
    }

    @Override
    public PredictedDecompositionDetails accessDecomposition(int index) {
        PredictedDecompositionDetails ret = null;

        if (index >= 0 && index < super.getSize()) {
            DecompositionDetails decomposition =
                    super.accessDecomposition(index);

            if (decomposition != null) {
                ret = PredictedDecompositionDetails.getInstance(decomposition, predictedRuntimes.get(index));
            }
        }

        return ret;
    }

    public double getPredictedRuntimeFortdId(int tdId) {
        Double ret = Double.NaN;

        int index = -1;

        List<DecompositionDetails> decompositionDetails = super.accessDecompositions();

        for (int i = 0; i < decompositionDetails.size(); i++) {
            DecompositionDetails decomposition = decompositionDetails.get(i);

            if (decomposition != null && decomposition.getTdId() == tdId) {
                index = i;
                break;
            }
        }


        if (index >= 0 && index < predictedRuntimes.size()) {
            ret = predictedRuntimes.get(index);

            if (ret == null) {
                ret = Double.NaN;
            }
        }

        return ret;
    }

    public double getPredictedRuntimeForIndex(int index) {
        Double ret = Double.NaN;

        if (index >= 0 && index < predictedRuntimes.size()) {
            ret = predictedRuntimes.get(index);

            if (ret == null) {
                ret = Double.NaN;
            }
        }

        return ret;
    }

    public double getMaximumRuntime() {
        double ret = Double.NEGATIVE_INFINITY;

        for (Double predictedRuntime : predictedRuntimes) {
            if (predictedRuntime != null && !predictedRuntime.isNaN() && predictedRuntime > ret) {
                ret = predictedRuntime;
            }
        }

        return ret;
    }

    public double getMinimumRuntime() {
        double ret = Double.POSITIVE_INFINITY;

        for (Double predictedRuntime : predictedRuntimes) {
            if (predictedRuntime != null && !predictedRuntime.isNaN() && predictedRuntime < ret) {
                ret = predictedRuntime;
            }
        }

        return ret;
    }

    public int getFirstId
            () {
        int ret = -1;

        PredictedDecompositionDetails decompositionDetails = accessFirstDecomposition();

        if (decompositionDetails != null) {
            ret = decompositionDetails.getTdId();
        }

        return ret;
    }

    public PredictedDecompositionDetails accessFirstDecomposition() {
        PredictedDecompositionDetails ret = null;

        if (super.getSize() > 0) {
            DecompositionDetails decomposition =
                    super.accessDecomposition(0);

            if (decomposition != null) {
                ret = PredictedDecompositionDetails.getInstance(decomposition, predictedRuntimes.get(0));
            }
        }

        return ret;
    }

    public int getOptimalId() {
        int ret = -1;

        PredictedDecompositionDetails decompositionDetails = accessOptimalDecomposition();

        if (decompositionDetails != null) {
            ret = decompositionDetails.getTdId();
        }

        return ret;
    }

    public int getPreferredId(double limit) {
        int ret = -1;

        PredictedDecompositionDetails decompositionDetails = accessPreferredDecomposition(limit);

        if (decompositionDetails != null) {
            ret = decompositionDetails.getTdId();
        }

        return ret;
    }

    public PredictedDecompositionDetails accessOptimalDecomposition() {
        return accessPreferredDecomposition(0);
    }

    public PredictedDecompositionDetails accessPreferredDecomposition(double limit) {
        PredictedDecompositionDetails ret = null;

        if (super.getSize() > 0) {
            double optimalTime =
                    Double.POSITIVE_INFINITY;

            List<DecompositionDetails> decompositions =
                    super.accessDecompositions();

            if (decompositions != null) {
                if (predictedRuntimes.size() == decompositions.size()) {
                    List<Integer> selectedDecompositions = new ArrayList<>();

                    for (int i = 0; i < decompositions.size(); i++) {
                        double runtime =
                                predictedRuntimes.get(i);

                        selectedDecompositions.add(i);

                        if (!Double.isNaN(runtime) && runtime < optimalTime) {
                            optimalTime = runtime;
                        }
                    }

                    Collections.sort(selectedDecompositions, new Comparator<Integer>() {
                        @Override
                        public int compare(Integer index1, Integer index2) {
                            int ret = 0;

                            double runtime1 =
                                    predictedRuntimes.get(index1);

                            double runtime2 =
                                    predictedRuntimes.get(index2);

                            if (!Double.isNaN(runtime1) && !Double.isNaN(runtime2)) {
                                ret = Double.compare(runtime1, runtime2);
                            }
                            else {
                                if (Double.isNaN(runtime1) && !Double.isNaN(runtime2)) {
                                    ret = 1;
                                }
                                else if (!Double.isNaN(runtime1) && Double.isNaN(runtime2)) {
                                    ret = -1;
                                }
                            }

                            return ret;
                        }
                    });

                    if (!selectedDecompositions.isEmpty()) {
                        for (int i = selectedDecompositions.size() - 1; i >= 0; i--) {
                            int index =
                                    selectedDecompositions.get(i);

                            double runtime = predictedRuntimes.get(index);

                            if (Double.isNaN(runtime) || runtime > optimalTime + limit) {
                                if (selectedDecompositions.size() > 1) {
                                    selectedDecompositions.remove(i);
                                }
                            }
                        }

                        Random random =
                                new Random();

                        int index = selectedDecompositions.get(random.nextInt(selectedDecompositions.size()));

                        ret = PredictedDecompositionDetails.getInstance(decompositions.get(index), predictedRuntimes.get(index));
                    }
                }
            }
        }

        return ret;
    }

    public int getBeneficialId() {
        int ret = -1;

        PredictedDecompositionDetails decompositionDetails = accessBeneficialDecomposition();

        if (decompositionDetails != null) {
            ret = decompositionDetails.getTdId();
        }

        return ret;
    }

    public PredictedDecompositionDetails accessBeneficialDecomposition() {
        PredictedDecompositionDetails ret = null;

        if (super.getSize() > 0) {
            List<DecompositionDetails> decompositions =
                    super.accessDecompositions();

            if (decompositions != null) {
                if (predictedRuntimes.size() == decompositions.size()) {
                    List<Integer> selectedDecompositions = new ArrayList<>();

                    for (int i = 0; i < decompositions.size(); i++) {
                        selectedDecompositions.add(i);
                    }

                    Collections.sort(selectedDecompositions, new Comparator<Integer>() {
                        @Override
                        public int compare(Integer index1, Integer index2) {
                            int ret = 0;

                            double runtime1 =
                                    predictedRuntimes.get(index1);

                            double runtime2 =
                                    predictedRuntimes.get(index2);

                            if (!Double.isNaN(runtime1) && !Double.isNaN(runtime2)) {
                                ret = Double.compare(runtime1, runtime2);
                            }
                            else {
                                if (Double.isNaN(runtime1) && !Double.isNaN(runtime2)) {
                                    ret = 1;
                                }
                                else if (!Double.isNaN(runtime1) && Double.isNaN(runtime2)) {
                                    ret = -1;
                                }
                            }

                            return ret;
                        }
                    });

                    if (!selectedDecompositions.isEmpty()) {
                        if (selectedDecompositions.size() < 10) {
                            ret = PredictedDecompositionDetails.getInstance(decompositions.get(0), predictedRuntimes.get(0));
                        }
                        else {
                            Random random =
                                    new Random();

                            int index = selectedDecompositions.get(random.nextInt((int)(Math.round((double)selectedDecompositions.size() / 10))));

                            ret = PredictedDecompositionDetails.getInstance(decompositions.get(index), predictedRuntimes.get(index));
                        }
                    }
                }
            }
        }

        return ret;
    }

    public static PredictedDecompositionPool createPredictedDecompositionPool(DecompositionPool decompositionPool, List<Double> predictedRuntimes) {
        PredictedDecompositionPool ret = null;

        if (decompositionPool != null && predictedRuntimes != null) {
            if (decompositionPool.getSize() == predictedRuntimes.size()) {
                ret = new PredictedDecompositionPool(decompositionPool, predictedRuntimes);
            }
        }

        return ret;
    }

}