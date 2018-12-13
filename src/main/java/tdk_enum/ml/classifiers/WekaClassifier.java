package tdk_enum.ml.classifiers;

import tdk_enum.common.configuration.config_types.MLModelType;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WekaClassifier implements IClassifier{


    Map<MLModelType, Classifier> classifierMap = new HashMap<>();

    public WekaClassifier()
    {
        classifierMap.put(MLModelType.LR, new LinearRegression());
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
                    Classifier classifier = classifierMap.get(type);
                    classifier.buildClassifier(data);

                    SerializationHelper.write(outputPath+"/"+type+".model",classifier);
                    System.out.println("model has been stored as " +outputPath+"/"+type+".model");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
