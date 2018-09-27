package tdk_enum.ml;

import org.junit.Test;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

        LinearRegression lr = new LinearRegression();

        try {
            lr.buildClassifier(trainSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i =1; i < 100; i++)
        {
            double[] values = new double[trainSet.numAttributes()];
            values[0]=i;
            values[1]=2*i;
            Instance inst = new DenseInstance(1.0, values);
            testSet.add(inst);
        }

        try {
            Evaluation eval = new Evaluation(trainSet);
            eval.evaluateModel(lr, testSet);
            System.out.println(eval.toSummaryString("\nResults\n\n", false));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Instance row: testSet)
        {
            try {
                System.out.println("x = " + row.toString(0) + " y = " + lr.classifyInstance(row));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}