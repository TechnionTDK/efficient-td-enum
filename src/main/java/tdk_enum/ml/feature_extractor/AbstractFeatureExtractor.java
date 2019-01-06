package tdk_enum.ml.feature_extractor;

import tdk_enum.common.IO.CSVOperations;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractFeatureExtractor implements IFeatureExtractor {

    @Override
    public void toCSV(File csv, FeatureExtractionResult features) {

        if (!csv.exists()) {
            csv.getParentFile().mkdirs();
            try {
                csv.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (PrintWriter output = new PrintWriter(csv)) {

                String headers = features.getCSVHeader();
                output.println(headers);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        try (PrintWriter output = new PrintWriter(new FileOutputStream(csv, true))) {

            if(features.getUserTime()>=0)
            {
                output.println(features.toCSV());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toCSV(File csv, Map<String, Double> flatFeatures) {
        if (!csv.exists()) {
            csv.getParentFile().mkdirs();
            try {
                csv.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (PrintWriter output = new PrintWriter(csv)) {

                String headers = CSVOperations.dataToCSV(new ArrayList<>(flatFeatures.keySet()));
                output.println(headers);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        try (PrintWriter output = new PrintWriter(new FileOutputStream(csv, true))) {

            List<Double> values = new ArrayList<>();
            for (String key : flatFeatures.keySet())
            {
                values.add(flatFeatures.get(key));
            }
            output.println(CSVOperations.dataToCSV(values));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
