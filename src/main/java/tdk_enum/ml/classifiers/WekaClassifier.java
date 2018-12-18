package tdk_enum.ml.classifiers;

import tdk_enum.common.configuration.config_types.MLModelType;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.classifiers.common.DecompositionDetails;
import tdk_enum.ml.classifiers.common.DecompositionPool;
import tdk_enum.ml.classifiers.common.PredictedDecompositionPool;
import tdk_enum.ml.classifiers.common.SelectionTemplate;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import weka.classifiers.AbstractClassifier;
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
            if(mlModelType == MLModelType.OMNI)
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
        for (File modelFile : modelsFolder.listFiles())
        {
            try {
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
            generateSelectionTemplate(classifiers.get(i), modelTypes.get(i), decompositions);
        }
    }

    private SelectionTemplate generateSelectionTemplate(Classifier classifier, MLModelType mlModelType, DecompositionPool decompositions) {
        SelectionTemplate ret = null;
        long start =
                System.currentTimeMillis();

        PredictedDecompositionPool predictedInstances = predictDecompositions(classifier, decompositions);
        ret = generateSelectionTemplate(predictedInstances, start);
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



    private PredictedDecompositionPool predictDecompositions(Classifier classifier, DecompositionPool decompositions) {
        PredictedDecompositionPool ret = null;
        List<Double> predictedRuntimes = new ArrayList<>();

        Instances instances =
               new InstanceConverter().getInstancesFromDecompositionPool(decompositions, true);
        for (int i = 0; i < instances.numInstances(); i++) {
            predictedRuntimes.add(predictInstance(instances.instance(i), classifier));
        }
        ret = PredictedDecompositionPool.createPredictedDecompositionPool(decompositions, predictedRuntimes);
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


}
