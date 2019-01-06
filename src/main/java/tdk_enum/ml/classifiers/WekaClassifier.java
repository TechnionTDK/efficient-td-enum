package tdk_enum.ml.classifiers;

import org.apache.commons.io.FilenameUtils;
import tdk_enum.common.configuration.config_types.MLModelType;
import tdk_enum.ml.classifiers.common.DecompositionDetails;
import tdk_enum.ml.classifiers.common.DecompositionPool;
import tdk_enum.ml.classifiers.common.PredictedDecompositionPool;
import tdk_enum.ml.classifiers.common.SelectionTemplate;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import weka.classifiers.Classifier;

import weka.classifiers.functions.*;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.trees.M5P;
import weka.core.*;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WekaClassifier implements IClassifier{


    /**
     * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
     */
    public  class InstanceConverter {

        public Instances getInstancesFromDecompositionPool(DecompositionPool results, boolean resetRuntime) {
            Instances ret =
                    new Instances("Instances", getAttributeVector(), 0);

            if (results != null) {
                for (DecompositionDetails result : results.accessDecompositions()) {
                    if (result != null) {
                        ret.add(getInstance(result, resetRuntime));
                    }
                }
            }

            ret.setClassIndex(0);

            return ret;
        }

        public  Instances getInstancesFromDecompositionDetails(List<DecompositionDetails> results, boolean resetRuntime) {
            Instances ret =
                    new Instances("Instances", getAttributeVector(),0);

            if (results != null) {
                for (DecompositionDetails result : results) {
                    if (result != null) {
                        ret.add(getInstance(result, resetRuntime));
                    }
                }
            }

            ret.setClassIndex(0);

            return ret;
        }

        public  Instances getInstancesFromFeatureExtractionResults(List<FeatureExtractionResult> results, boolean resetRuntime) {
            Instances ret =
                    new Instances("Instances", getAttributeVector(),0);

            if (results != null) {
                for (FeatureExtractionResult result : results) {
                    if (result != null) {
                        ret.add(getInstance(result, resetRuntime));
                    }
                }
            }

            ret.setClassIndex(0);

            return ret;
        }

        private Instance getInstance(DecompositionDetails result, boolean resetRuntime) {
            return new DenseInstance(1, result.getRelevantAttributeValues(resetRuntime));
        }

        private  Instance getInstance(FeatureExtractionResult result, boolean resetRuntime) {
            double[] values = DecompositionDetails.getRelevantAttributeValues(result);

            if (resetRuntime) {
                values[0] = Double.NaN;
            }

            return new DenseInstance(1, values);
        }

        private ArrayList<Attribute> getAttributeVector() {
            ArrayList<Attribute> ret = new ArrayList<>();
//            FastVector ret = new FastVector();

            List<String> attributes =
                    DecompositionDetails.getFeatureNames();

            for (String attribute : attributes) {
                if (attribute != null) {
                    ret.add(new Attribute(attribute));

                }
            }

            return ret;
        }

    }



    Map<MLModelType, Classifier> classifierMap = new HashMap<>();

    public WekaClassifier()
    {
        classifierMap.put(MLModelType.GAUSSIAN_PROCESSES, new GaussianProcesses());
        classifierMap.put(MLModelType.ISOTONIC_REGRESSION, new IsotonicRegression());
        classifierMap.put(MLModelType.LEAST_MED_SQ, new LeastMedSq());
        classifierMap.put(MLModelType.LINEAR_REGRESSION, new LinearRegression());
        classifierMap.put(MLModelType.MULTILAYER_PERCEPTRON, new MultilayerPerceptron());
        classifierMap.put(MLModelType.PACE_REGRESSION, new PaceRegression());
        classifierMap.put(MLModelType.PLS_CLASSIFIER, new PLSClassifier());
        classifierMap.put(MLModelType.SMOREG, new SMOreg());
        classifierMap.put(MLModelType.IBK, new IBk());
        classifierMap.put(MLModelType.KSTAR, new KStar());
        classifierMap.put(MLModelType.LWL, new LWL());
        classifierMap.put(MLModelType.ADDITIVE_REGRESSION, new AdditiveRegression());
        classifierMap.put(MLModelType.BAGGING, new Bagging());
        classifierMap.put(MLModelType.CV_PARAMETER_SELECTION, new CVParameterSelection());
        classifierMap.put(MLModelType.M5RULES, new M5Rules());
        classifierMap.put(MLModelType.M5P, new M5P());

    }


    @Override
    public void trainModel(String csv, String outputPath, MLModelType mlModelType) {
        CSVLoader loader = new CSVLoader();
        try {
            loader.setSource(new File(csv));
            Instances data = loader.getDataSet();

            data.setClassIndex(0);
            new File(outputPath).mkdirs();
            if(mlModelType == MLModelType.COMBINED)
            {
                for(MLModelType type : classifierMap.keySet())
                {


                    try
                    {
                        Classifier classifier = classifierMap.get(type);
                        classifier.buildClassifier(data);
                        SerializationHelper.write(outputPath+"/"+type+".model",classifier);
                        System.out.println("model has been stored as " +outputPath+"/"+type+".model");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("unable to store model "+ type);
                    }

                }
            }
            else
            {
                Classifier classifier = classifierMap.get(mlModelType);
                classifier.buildClassifier(data);

                SerializationHelper.write(outputPath+"/"+mlModelType+".model",classifier);
                System.out.println("model has been stored as " +outputPath+"/"+mlModelType+".model");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void loadModels(String modelsLoadPath) {

    }

    @Override
    public List<SelectionTemplate> predictDecompositions(DecompositionPool decompositions, String modelLoadPath) {
        File modelsFolder = new File(modelLoadPath);
        List<Classifier> classifiers = new ArrayList<>();
        List<MLModelType> modelTypes = new ArrayList<>();
        List<SelectionTemplate> selectionTemplates = new ArrayList<>();

        Instances instances =
                new InstanceConverter().getInstancesFromDecompositionPool(decompositions, true);

        for (File modelFile : modelsFolder.listFiles())
        {
            try {
                String extension = FilenameUtils.getExtension(modelFile.getAbsolutePath());
                if(!extension.equals( "model"))
                    continue;
                System.out.println("loading model " + modelFile.getAbsolutePath());
                Classifier classifier = (Classifier) SerializationHelper.read(modelFile.getAbsolutePath());
                classifiers.add(classifier);
                for(MLModelType mlModelType : classifierMap.keySet())
                {
                    if (classifier.getClass().equals(classifierMap.get(mlModelType).getClass()))
                    {
                        modelTypes.add(mlModelType);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i< classifiers.size(); i++)
        {
            System.out.println("running model " + modelTypes.get(i));
            selectionTemplates.add(generateSelectionTemplate(classifiers.get(i), modelTypes.get(i), decompositions, instances));
        }
//        selectionTemplates.add(generateSelectionTemplate(classifiers, decompositions, instances));
        return selectionTemplates;
    }

    private SelectionTemplate generateSelectionTemplate(List<Classifier> classifiers, DecompositionPool decompositions, Instances instances) {
        SelectionTemplate ret = null;

        if (decompositions != null && classifiers != null && decompositions.getSize() > 0) {
            long start =
                    System.currentTimeMillis();

            PredictedDecompositionPool predictedInstances =
                    predictDecompositions(decompositions, classifiers, instances);

            ret = generateSelectionTemplate(predictedInstances, start);
        }
        ret.setMlModelType(MLModelType.COMBINED);

        return ret;
    }

    private SelectionTemplate generateSelectionTemplate(Classifier classifier, MLModelType mlModelType, DecompositionPool decompositions, Instances instances) {
        SelectionTemplate ret = null;
        long start =
                System.currentTimeMillis();

        PredictedDecompositionPool predictedInstances = predictDecompositions(classifier, decompositions, instances);
        ret = generateSelectionTemplate(predictedInstances, start);
        ret.setMlModelType(mlModelType);
        return ret;
    }

    private SelectionTemplate generateSelectionTemplate(PredictedDecompositionPool predictedInstances, long start) {
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
                    (double)(System.currentTimeMillis() - start) / 1000;

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



    private PredictedDecompositionPool predictDecompositions(Classifier classifier, DecompositionPool decompositions, Instances instances) {
        PredictedDecompositionPool ret = null;
        List<Double> predictedRuntimes = new ArrayList<>();

        for (int i = 0; i < instances.numInstances(); i++) {
            if(i% 100 == 0)
            {
                System.out.println("predicting instance " + i + " out of " + instances.size());
            }

            predictedRuntimes.add(predictInstance(instances.instance(i), classifier));
        }
        ret = PredictedDecompositionPool.createPredictedDecompositionPool(decompositions, predictedRuntimes);
        return ret;
    }

    public  PredictedDecompositionPool predictDecompositions(DecompositionPool decompositions, List<Classifier> classifiers, Instances instances) {
        PredictedDecompositionPool ret = null;

        if (classifiers != null && decompositions != null) {
            List<Double> weights = new ArrayList<>();

            for (Classifier classifier : classifiers) {
                weights.add(1.0);
            }

            ret = predictDecompositions(decompositions, classifiers, weights, instances);

        }

        return ret;
    }

    private  PredictedDecompositionPool predictDecompositions(DecompositionPool decompositions, List<Classifier> classifiers, List<Double> weights, Instances instances) {
        PredictedDecompositionPool ret = null;

        List<Double> predictedRuntimes = new ArrayList<>();
        for (int i = 0; i < instances.numInstances(); i++) {
            predictedRuntimes.add(predictInstance(instances.instance(i), classifiers, weights));
        }

        ret = PredictedDecompositionPool.createPredictedDecompositionPool(decompositions, predictedRuntimes);



        return ret;
    }

    private Double predictInstance(Instance instance, Classifier classifier) {
        double ret = Double.NaN;

        if (classifier != null) {
            try {
                double[] distribution =
                        classifier.distributionForInstance(instance);

                if (distribution != null && distribution.length == 1) {
                    ret = distribution[0];
                }
            }
            catch (Exception e) {

            }

            if (Double.isNaN(ret)) {
                try {
                    ret = classifier.classifyInstance(instance);
                }
                catch (Exception e) {

                }
            }
        }

        return ret;
    }

    public  double predictInstance(Instance instance, List<Classifier> classifiers, List<Double> weights) {
        double ret =
                Double.NaN;

        if (instance != null && classifiers != null && !classifiers.isEmpty() && weights != null && weights.size() == classifiers.size()) {
            double totalWeight = 0.0;

            List<Double> actualWeights =
                    new ArrayList<>(weights);

            for (Double weight : weights) {
                if (weight != null && !weight.isNaN() && weight >= 0) {
                    totalWeight += weight;
                }
            }

            for (int i = 0; i < actualWeights.size(); i++) {
                Double weight =
                        actualWeights.get(i);

                if (weight != null && !weight.isNaN() && !weight.isInfinite() && weight >= 0) {
                    actualWeights.set(i, weight / totalWeight);
                }
                else {
                    actualWeights.set(i, Double.NaN);
                }
            }

            ret = 0.0;

            for (int i = 0; i < classifiers.size(); i++) {
                double weight =
                        actualWeights.get(i);

                Classifier classifier =
                        classifiers.get(i);

                if (classifier != null) {
                    ret += predictInstance(instance, classifier) * weight;
                }
                else {
                    ret = Double.NaN;
                }
            }
        }

        return ret;
    }


}
