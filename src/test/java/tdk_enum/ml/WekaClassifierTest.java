package tdk_enum.ml;

import org.junit.Test;
import tdk_enum.common.configuration.config_types.MLModelType;
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

import java.util.ArrayList;

public class WekaClassifierTest {

    @Test
    public void test1()
    {
        Attribute num1 = new Attribute("x");
        Attribute num2 = new Attribute("y");
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(num1);
        attributes.add(num2);
        Instances testSet = new Instances("Test-dataset", attributes, 0);
        testSet.setClassIndex(testSet.numAttributes()-1);
        Instances trainSet = new Instances("Train-dataset", attributes, 0);
        trainSet.setClassIndex(trainSet.numAttributes()-1);

        for (int i =1; i < 100; i++)
        {
            double[] values = new double[trainSet.numAttributes()];
            values[0]=i;
            values[1]=2*i;
            Instance inst = new DenseInstance(1.0, values);
            trainSet.add(inst);
        }


        Classifier classifier = createClassifier(MLModelType.M5P);


        try {
            classifier.buildClassifier(trainSet);
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i =1; i < 100; i++)
        {
            double[] values = new double[trainSet.numAttributes()];
            values[0]=i;
            values[1]=0;
            Instance inst = new DenseInstance(1.0, values);
            testSet.add(inst);
        }

//        try {
//            Evaluation eval = new Evaluation(trainSet);
//            eval.evaluateModel(lr, testSet);
//            System.out.println(eval.toSummaryString("\nResults\n\n", false));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        for (Instance row: testSet)
        {
            try {
                System.out.println("x = " + row.toString(0) + " y = " + predictInstance(row, classifier));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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