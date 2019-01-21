package tdk_enum.ml.feature_extractor;

import tdk_enum.common.IO.CSVOperations;
import tdk_enum.ml.classifiers.common.DecompositionDetails;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import tdk_enum.ml.solvers.execution.CommandExecutor;

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

    @Override
    public void toCSV(File csv, DecompositionDetails decompositionDetails)
    {
        if (!csv.exists()) {
            csv.getParentFile().mkdirs();
            try {
                csv.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (PrintWriter output = new PrintWriter(csv)) {

                String headers = CSVOperations.dataToCSV(decompositionDetails.toCSVHeaders());
                output.println(headers);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        try (PrintWriter output = new PrintWriter(new FileOutputStream(csv, true))) {

            if(decompositionDetails.getFeatureValue(1)>=0)
            {
                output.println(decompositionDetails.toCSV());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String prepareCSV(String rawCSV, String output) {

//        String command = "sed s/-//g -i.bak " + rawCSV +"\n" +
//                "sed s/://g -i.bak " + rawCSV +"\n" +
//                "sed s/%//g -i.bak " + rawCSV +"\n" +
//                "sed s/\\ //g -i.bak " + rawCSV +"\n" +
//                "sed s/\\(//g -i.bak " + rawCSV +"\n" +
//                "sed s/\\)//g -i.bak " + rawCSV +"\n" +
//                "sed s/UNKNOWN/\\?/g -i.bak " + rawCSV +"\n" +
//                "sed s/INFINITE/\\?/g -i.bak " + rawCSV +"\n" +
//                "\n" +
//                "cat "  + rawCSV +" | head -n 1 | cut -d, -f 1,3,57-62,65,67,70,72-77,80,82,85,87-92,95,97,100,103-107,110,112,115,118-122,125,127,130,133-137,140,142,145,147-152,155,157,160,162-167,170,172,175,177-182,185,187,190,192-197,200,202,205,207-212,215,217,220,221-225,228-232,235,237,240,243-247,250,252,255,258-262,265,267,270,273-277,280,282,285,288-292,295,297,300,303-307,310,312,315,318-322,325,327,330,333-337,340,342,345,348-352,355,357,360,363-367,370,372,375-382,385-389,392,394,397,400-404,407,409,412,415-419,422,424,427,430-434,437,439,442,445-449,452,454,457,460-464,467,469,472,475-479,482,484,487,490-494,497,499,502,505-509,512,514,517,520-524,527,529,532,535-539,542,544,547,550-554,557,559,562,563-568,571-574,577-582 > tmp.csv\n" +
//                "\n" +
//                "cat "  + rawCSV +" | tail -n +2 | cut -d, -f 1,3,57-62,65,67,70,72-77,80,82,85,87-92,95,97,100,103-107,110,112,115,118-122,125,127,130,133-137,140,142,145,147-152,155,157,160,162-167,170,172,175,177-182,185,187,190,192-197,200,202,205,207-212,215,217,220,221-225,228-232,235,237,240,243-247,250,252,255,258-262,265,267,270,273-277,280,282,285,288-292,295,297,300,303-307,310,312,315,318-322,325,327,330,333-337,340,342,345,348-352,355,357,360,363-367,370,372,375-382,385-389,392,394,397,400-404,407,409,412,415-419,422,424,427,430-434,437,439,442,445-449,452,454,457,460-464,467,469,472,475-479,482,484,487,490-494,497,499,502,505-509,512,514,517,520-524,527,529,532,535-539,542,544,547,550-554,557,559,562,563-568,571-574,577-582 >> tmp.csv\n" +
//                "\n" +
//                "cat tmp.csv | cut -d, -f 1-7,11-16,20-25,29-33,37-41,45-49,53-58,62-67,71-76,80-85,89-94,98-107,111-115,119-123,127-131,135-139,143-147,151-155,159-163,167-171,175-179,183-194,198-202,206-210,214-218,222-226,230-234,238-242,246-250,254-258,262-266,270-274,278-282,286,287-302 > tmp2.csv\n" +
//                "\n" +
//                "rm tmp.csv\n" +
//                "\n" +
//                "cat tmp2.csv | cut -d, -f 1-5,7-11,13-14,21-22,24-27,29-30,36-38,40-44,46-50,52-56,58-62,64-72,74-77,79-82,84-87,89-92,94-97,99-102,104-107,109-112,114-117,119-129,131-134,136-139,141-144,146-149,151-154,156-159,161-164,166-169,171-174,176-179,181-184,186-204 > tmp3.csv\n" +
//                "\n" +
//                "rm tmp2.csv\n" +
//                "\n" +
//                "cat tmp3.csv | cut -d, -f1-25,31-46,48-54,59-93,95-121,134-161 > tmp4.csv\n" +
//                "\n" +
//                "rm tmp3.csv\n" +
//                "\n" +
//                "cat tmp4.csv | cut -d, -f1-64,69-86,95-122,124-138 >" +output +"\n" +
//                "\n" +
//                "rm tmp4.csv";
//        new CommandExecutor().runScript(command);

        String command = "Rscript ./dflat/preparation.r " +output;
        new CommandExecutor().runScript(command);
        String outputFile = output.replace(".csv", "")+"_results_cleaned.csv";
        System.out.println("dataset prepared features were written on " + outputFile);
        return outputFile;

    }



}
