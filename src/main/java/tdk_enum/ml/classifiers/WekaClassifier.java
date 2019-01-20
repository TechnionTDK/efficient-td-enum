package tdk_enum.ml.classifiers;

import org.apache.commons.io.FilenameUtils;
import tdk_enum.common.configuration.config_types.MLModelType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.ml.feature_extractor_factory.FeatureExtractorFactory;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.classifiers.common.DecompositionDetails;
import tdk_enum.ml.classifiers.common.DecompositionPool;
import tdk_enum.ml.classifiers.common.PredictedDecompositionPool;
import tdk_enum.ml.classifiers.common.SelectionTemplate;
import tdk_enum.ml.feature_extractor.IFeatureExtractor;
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
import java.util.*;

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
                        Classifier classifier = createClassifier(type);

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

    @Override
    public List<SelectionTemplate> predictDecompositions(List<ITreeDecomposition> decompositions, String modelLoadPath) {
        File modelsFolder = new File(modelLoadPath);
        List<Classifier> classifiers = new ArrayList<>();
        List<MLModelType> modelTypes = new ArrayList<>();
        List<SelectionTemplate> selectionTemplates = new ArrayList<>();


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
            selectionTemplates.add(generateSelectionTemplate(classifiers.get(i), modelTypes.get(i), decompositions));
        }
//        selectionTemplates.add(generateSelectionTemplate(classifiers, decompositions, instances));
        return selectionTemplates;
    }

    private SelectionTemplate generateSelectionTemplate(Classifier classifier, MLModelType mlModelType, List<ITreeDecomposition> decompositions) {
        SelectionTemplate ret = null;
        long start =
                System.currentTimeMillis();

        PredictedDecompositionPool predictedInstances = predictDecompositions(classifier, decompositions);
        ret = generateSelectionTemplate(predictedInstances, start);
        ret.setMlModelType(mlModelType);
        return ret;

    }

    private PredictedDecompositionPool predictDecompositions(Classifier classifier, List<ITreeDecomposition> decompositions) {

        long start =
                System.currentTimeMillis();
        PredictedDecompositionPool ret = new PredictedDecompositionPool();


        IFeatureExtractor featureExtractor = new FeatureExtractorFactory().produce();
        InstanceConverter converter = new InstanceConverter();
        for (int i = 0; i < decompositions.size(); i++) {
            DecompositionDetails details = DecompositionDetails.getInstance(featureExtractor.getFeatures(i,decompositions.get(i), TDKEnumFactory.getGraph() ));
            Instance instance = converter.getInstance(details, true);
            if(i% 100 == 0)
            {
                System.out.println("predicting instance " + i + " out of " + decompositions.size());

                for (Double value : details.getRelevantAttributeValues())
                {
                    System.out.print(value + ",");
                }
                System.out.println();
                //        System.out.println(decompositions.accessDecomposition(i).toCSV());
                for (Double value : instance.toDoubleArray())
                {
                    System.out.print(value + ",");
                }
                System.out.println();
            }
            ret.addPrediction(details, predictInstance(instance, classifier));


        }
        return ret;


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

        Enumeration<Attribute> attributes = instances.enumerateAttributes();
//        while (attributes.hasMoreElements())
//        {
//            System.out.print(attributes.nextElement().name() + ", ");
//        }
        for (int i = 0; i < instances.numInstances(); i++) {
            if(i% 100 == 0)
            {
                System.out.println("predicting instance " + i + " out of " + instances.size());
                for (Double value : decompositions.accessDecomposition(i).getRelevantAttributeValues())
                {
                    System.out.print(value + ",");
                }
                System.out.println();
                //        System.out.println(decompositions.accessDecomposition(i).toCSV());
                for (Double value : instances.instance(i).toDoubleArray())
                {
                    System.out.print(value + ",");
                }
                System.out.println();
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
                    e.printStackTrace();

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


    private Classifier createClassifier(MLModelType mlModelType) {
        switch (mlModelType){
            case GAUSSIAN_PROCESSES:{
                return createGaussianProcess();
            }
            case ISOTONIC_REGRESSION:{
                return createIsotonicRegression();
            }
            case LEAST_MED_SQ:
            {
                return createLeastMedsq();
            }
            case LINEAR_REGRESSION:
            {
                return  createLinearRegression();
            }
            case MULTILAYER_PERCEPTRON:{
                return createMultilayerPerceptron();
            }
            case PACE_REGRESSION:{
                return createPaceRegression();
            }
            case PLS_CLASSIFIER:{
                return createPLSClassifier();
            }
            case SMOREG:{
                return createSMOREG();
            }
            case IBK:{
                return createIBK();
            }
            case KSTAR:
            {
                return createKStar();
            }
            case LWL:{
                return createLWL();
            }
            case ADDITIVE_REGRESSION:{
                return createAdditiveRegression();
            }
            case BAGGING:{
                return createBagging();
            }
            case CV_PARAMETER_SELECTION:{
                return createCVParameterSelection();
            }
            case M5RULES:{
                return createM5Rules();
            }

            case M5P:{
                return createM5P();
            }

            default:
                return null;


        }
    }

    private Classifier createM5P() {
        M5P m5P = new M5P();
        try {
            m5P.setOptions(Utils.splitOptions("-M 2.0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m5P;
    }

    private Classifier createM5Rules() {
        M5Rules m5Rules = new M5Rules();
        try {
            m5Rules.setOptions(Utils.splitOptions("-M 2.0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m5Rules;

    }

    private Classifier createCVParameterSelection() {
        CVParameterSelection cvParameterSelection = new CVParameterSelection();
        try {
            cvParameterSelection.setOptions(Utils.splitOptions("-X 10 -S 1 -W weka.classifiers.trees.M5P -- -M 4.0"));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return cvParameterSelection;
    }

    private Classifier createBagging() {
        Bagging bagging = new Bagging();
        try {
            bagging.setOptions(Utils.splitOptions("-P 100 -S 1 -num-slots 1 -I 10 -W weka.classifiers.functions.SMOreg -- -C 1.0 -N 2 -I \"weka.classifiers.functions.supportVector.RegSMOImproved -T 0.001 -V -P 1.0E-12 -L 0.001 -W 1\" -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bagging;

    }

    private Classifier createAdditiveRegression() {
        AdditiveRegression additiveRegression = new AdditiveRegression();
        try {
            additiveRegression.setOptions(Utils.splitOptions("-S 1.0 -I 10 -W weka.classifiers.functions.SMOreg -- -C 1.0 -N 2 -I \"weka.classifiers.functions.supportVector.RegSMOImproved -T 0.001 -V -P 1.0E-12 -L 0.001 -W 1\" -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return additiveRegression;
    }

    private Classifier createLWL() {
        LWL lwl = new LWL();
        try {
            lwl.setOptions(Utils.splitOptions("-U 0 -K -1 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\" -W weka.classifiers.functions.SMOreg -- -C 1.0 -N 2 -I \"weka.classifiers.functions.supportVector.RegSMOImproved -T 0.001 -V -P 1.0E-12 -L 0.001 -W 1\" -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lwl;
    }

    private Classifier createGaussianProcess() {
        GaussianProcesses gaussianProcesses = new GaussianProcesses();
        try {
            gaussianProcesses.setOptions(Utils.splitOptions("-L 1.0 -N 0 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gaussianProcesses;
    }

    private Classifier createKStar() {
        KStar kStar = new KStar();
        try {
            kStar.setOptions(Utils.splitOptions("-B 20 -M a"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kStar;

    }

    private Classifier createIBK() {
        IBk iBk = new IBk();

        try {
            iBk.setOptions(Utils.splitOptions("-K 9 -W 0 -I -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iBk;
    }

    private Classifier createSMOREG() {
        SMOreg schme = new SMOreg();
        try {
            schme.setOptions(Utils.splitOptions("-C 1.0 -N 2 -I \"weka.classifiers.functions.supportVector.RegSMOImproved -T 0.001 -V -P 1.0E-12 -L 0.001 -W 1\" -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schme;
    }

    private Classifier createPLSClassifier() {
        // create new instance of scheme
        weka.classifiers.functions.PLSClassifier scheme = new weka.classifiers.functions.PLSClassifier();

        // set options
        try {
            scheme.setOptions(Utils.splitOptions("-filter \"weka.filters.supervised.attribute.PLSFilter -C 20 -M -A PLS1 -P none\" -S 1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheme;
    }

    Classifier createIsotonicRegression()
    {
        return new IsotonicRegression();
    }
    Classifier createLeastMedsq()
    {

        weka.classifiers.functions.LeastMedSq scheme = new weka.classifiers.functions.LeastMedSq();

        // set options
        try {
            scheme.setOptions(Utils.splitOptions("-S 4 -G 0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheme;

    }

    Classifier createLinearRegression(){
        // create new instance of scheme
        weka.classifiers.functions.LinearRegression scheme = new weka.classifiers.functions.LinearRegression();

        // set options
        try {
            scheme.setOptions(Utils.splitOptions("-S 1 -R 1.0E-8 -num-decimal-places 4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheme;

    }

    Classifier createMultilayerPerceptron()
    {
        weka.classifiers.functions.MultilayerPerceptron scheme = new weka.classifiers.functions.MultilayerPerceptron();

        // set options
        try {
            scheme.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H a"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheme;
    }

    Classifier createPaceRegression()
    {
        // create new instance of scheme
        weka.classifiers.functions.PaceRegression scheme = new weka.classifiers.functions.PaceRegression();

        // set options
        try {
            scheme.setOptions(Utils.splitOptions("-E pace6"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheme;
    }



}
