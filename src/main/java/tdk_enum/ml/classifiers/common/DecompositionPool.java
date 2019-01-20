package tdk_enum.ml.classifiers.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class DecompositionPool {
    private static final double DEFAULT_DISTANCE = 1.0;

    private File instanceFile = null;

    protected List<DecompositionDetails> decompositions = null;

    private DecompositionPool(File instanceFile, List<DecompositionDetails> decompositions) {
        this.instanceFile = instanceFile;

        this.decompositions = decompositions;
    }

    public DecompositionPool(DecompositionPool template) {
        this.instanceFile = template.instanceFile;

        this.decompositions = new ArrayList<>(template.decompositions);
    }

    public DecompositionPool()
    {
        this.decompositions = new ArrayList<>();
    }

    public int getSize() {
        return decompositions.size();
    }

    public File getInstanceFile() {
        return instanceFile;
    }

    public DecompositionPool normalize() {
        List<DecompositionDetails> ret = new ArrayList<>();

        int featureCount =
                DecompositionDetails.getFeatureCount();

        List<List<Double>> normalizedValues = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            normalizedValues.add(new ArrayList<Double>());
        }

        for (DecompositionDetails result : decompositions) {
            if (result != null) {
                for (int i = 0; i < featureCount; i++) {
                    normalizedValues.get(i).add(result.getFeatureValue(i));
                }
            }
        }

        for (int i = 0; i < featureCount; i++) {
            normalizedValues.set(i, TransformationOperations.normalize(normalizedValues.get(i)));
        }

        for (int i = 0; i < decompositions.size(); i++) {
            int seed =
                    decompositions.get(i).getTdId();

            double[] values =
                    extractVector(normalizedValues, i);

            String instance =
                    decompositions.get(i).getInstancePath();

            DecompositionDetails normalizedDecompositionDetails =
                    DecompositionDetails.getInstance(instance, seed, values);

            if (normalizedDecompositionDetails != null) {
                ret.add(normalizedDecompositionDetails);
            }
        }

        return new DecompositionPool(instanceFile, ret);
    }

    public DecompositionPool standardize() {
        List<DecompositionDetails> ret = new ArrayList<>();

        int featureCount =
                DecompositionDetails.getFeatureCount();

        List<List<Double>> standardizedValues = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            standardizedValues.add(new ArrayList<Double>());
        }

        for (DecompositionDetails result : decompositions) {
            if (result != null) {
                for (int i = 0; i < featureCount; i++) {
                    standardizedValues.get(i).add(result.getFeatureValue(i));
                }
            }
        }

        for (int i = 0; i < featureCount; i++) {
            standardizedValues.set(i, TransformationOperations.standardize(standardizedValues.get(i)));
        }

        for (int i = 0; i < decompositions.size(); i++) {
            int seed =
                    decompositions.get(i).getTdId();

            double[] values =
                    extractVector(standardizedValues, i);

            String instance =
                    decompositions.get(i).getInstancePath();

            DecompositionDetails standardizedDecompositionDetails =
                    DecompositionDetails.getInstance(instance, seed, values);

            if (standardizedDecompositionDetails != null) {
                ret.add(standardizedDecompositionDetails);
            }
        }

        return new DecompositionPool( instanceFile, ret);
    }

    public List<DecompositionDetails> accessDecompositions() {
        return decompositions;
    }

    public DecompositionDetails accessDecomposition(int index) {
        DecompositionDetails ret = null;

        if (index >= 0 && index < getSize()) {
            ret = decompositions.get(index);
        }

        return ret;
    }

    public void removeIndenticalInstances() {
        List<Integer> excludedIds = new ArrayList<>();

        evaluateResults(decompositions, 0.0, DEFAULT_DISTANCE, excludedIds);

        for (int i = decompositions.size() - 1; i >= 0; i--) {
            int tdId = decompositions.get(i).getTdId();

            if (excludedIds.contains(tdId)) {
                decompositions.remove(i);
            }
        }
    }

    public void removeSimilarInstances(double threshold) {
        List<Integer> excludedIds = new ArrayList<>();

        evaluateResults(decompositions, threshold, DEFAULT_DISTANCE, excludedIds);

        for (int i = decompositions.size() - 1; i >= 0; i--) {
            int tdId = decompositions.get(i).getTdId();

            if (excludedIds.contains(tdId)) {
                decompositions.remove(i);
            }
        }
    }

//    public BenchmarkTemplate getBenchmarkTemplate() {
//        BenchmarkDetails details =
//                new BenchmarkDetails();
//
//        for (DecompositionDetails decomposition : decompositions) {
//            if (decomposition != null) {
//                BenchmarkRun benchmarkRun =
//                        BenchmarkRun.getInstance(decomposition.getTdId(),
//                                decomposition.getInstanceFile());
//
//                if (benchmarkRun != null) {
//                    details.addBenchmarkRun(benchmarkRun);
//                }
//            }
//        }
//
//        return BenchmarkTemplate.createInstance(details);
//    }

//    public static DecompositionPool createDecompositionPool( File instanceFile, int size) {
//        DecompositionPool ret = null;
//
//        if (instanceFile != null && size > 0) {
//            if (instanceFile.exists() && instanceFile.isFile() && instanceFile.canRead()) {
//                final ExtractorInstance instance =
//                        configuration.getExtractorInstance();
//
//                if (instance != null) {
//                    if (instance.isValid()) {
//                        Runtime runtime =
//                                Runtime.getRuntime();
//
//                        if (runtime != null) {
//                            runtime.addShutdownHook(new Thread() {
//                                @Override
//                                public void run() {
//                                    instance.abortCurrentProcess();
//                                }
//                            });
//                        }
//
//                        FeatureCollection features =
//                                FeatureCollection.getDefaultFeatureCollection(false);
//
//                        List<DecompositionDetails> decompositions = new ArrayList<>();
//
//                        List<Integer> seeds =
//                                new ArrayList<>();
//
//                        Random random = new Random();
//
//                        while (seeds.size() < size) {
//                            int seed = random.nextInt(1000000);
//
//                            if (!seeds.contains(seed)) {
//                                seeds.add(seed);
//                            }
//                        }
//
//                        Collections.sort(seeds);
//
//                        for (int i = 0; i < size; i++) {
//                            FeatureExtractionResult result =
//                                    instance.executeExtraction(instanceFile, features, seeds.get(i));
//
//                            if (result != null) {
//                                decompositions.add(DecompositionDetails.getInstance(result));
//                            }
//                        }
//
//                        ret = new DecompositionPool(configuration, instanceFile, decompositions);
//                    }
//                }
//            }
//        }
//
//        return ret;
//    }

//    public static DecompositionPool createDecompositionPool(Configuration configuration, BenchmarkDetails benchmarkDetails) {
//        DecompositionPool ret = null;
//
//        if (configuration != null && benchmarkDetails != null) {
//            List<File> instances =
//                    benchmarkDetails.getInstances();
//
//            if (instances != null && !instances.isEmpty()) {
//                final ExtractorInstance instance =
//                        configuration.getExtractorInstance();
//
//                if (instance != null) {
//                    if (instance.isValid()) {
//                        Runtime runtime =
//                                Runtime.getRuntime();
//
//                        if (runtime != null) {
//                            runtime.addShutdownHook(new Thread() {
//                                @Override
//                                public void run() {
//                                    instance.abortCurrentProcess();
//                                }
//                            });
//                        }
//
//                        File instanceFile = instances.get(0);
//
//                        List<BenchmarkRun> benchmarkRuns =
//                                benchmarkDetails.getBenchmarkRuns(instanceFile);
//
//                        if (benchmarkRuns != null && !benchmarkRuns.isEmpty()) {
//                            FeatureCollection features =
//                                    FeatureCollection.getDefaultFeatureCollection(false);
//
//                            List<DecompositionDetails> decompositions = new ArrayList<>();
//
//                            for (BenchmarkRun benchmarkRun : benchmarkRuns) {
//                                FeatureExtractionResult result =
//                                        instance.executeExtraction(benchmarkRun.getInstance(), features, benchmarkRun.getSeed());
//
//                                if (result != null) {
//                                    decompositions.add(DecompositionDetails.getInstance(result));
//                                }
//                            }
//
//                            ret = new DecompositionPool(configuration, instanceFile, decompositions);
//                        }
//                    }
//                }
//            }
//        }
//
//        return ret;
//    }

    public static DecompositionPool createDecompositionPool( List<DecompositionDetails> decompositionDetails, File instance) {
        DecompositionPool ret = null;

        if ( decompositionDetails != null) {
            ret = new DecompositionPool( instance, decompositionDetails);
        }

        return ret;
    }

    private void evaluateResults(List<DecompositionDetails> decompositionDetails, double threshold, double defaultDistance, List<Integer> excludedSeeds) {
        if (decompositionDetails != null && excludedSeeds != null) {
            int size =
                    decompositionDetails.size() - excludedSeeds.size();

            int index = 0;
            int[] seeds = new int[size];

            for (DecompositionDetails extractionResult : decompositionDetails) {
                int seed = extractionResult.getTdId();

                if (!excludedSeeds.contains(seed)) {
                    seeds[index] = seed;

                    index++;
                }
            }

            List<List<Double>> distances = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                DecompositionDetails features1 =
                        decompositionDetails.get(i);

                List<Double> featureDistance = new ArrayList<>();

                for (int j = i + 1; j < size; j++) {
                    DecompositionDetails features2 =
                            decompositionDetails.get(j);

                    double distance =
                            features1.getEuclideanDistance(features2, defaultDistance);

                    featureDistance.add(distance);
                }

                distances.add(featureDistance);
            }

            boolean changed = false;

            for (int i = 0; i < distances.size(); i++) {
                List<Double> featureDistance = distances.get(i);

                for (int j = 0; j < featureDistance.size(); j++) {
                    Double value = featureDistance.get(j);

                    if (value != null && !value.isNaN() && value <= threshold) {
                        if (!excludedSeeds.contains(seeds[i + j + 1])) {
                            excludedSeeds.add(seeds[i + j + 1]);
                        }

                        changed = true;
                    }
                }
            }

            if (changed) {
                evaluateResults(decompositionDetails, threshold, defaultDistance, excludedSeeds);
            }
        }
    }

    private static double[] extractVector(List<List<Double>> values, int row) {
        int featureCount =
                DecompositionDetails.getFeatureCount();

        double[] ret = new double[featureCount];

        for (int i = 0; i < featureCount; i++) {
            ret[i] = Double.NaN;
        }

        if (values != null && row >= 0) {
            for (int i = 0; i < values.size(); i++) {
                List<Double> value = values.get(i);

                if (value != null && row < value.size()) {
                    ret[i] = value.get(row);
                }
            }
        }

        return ret;
    }

}
