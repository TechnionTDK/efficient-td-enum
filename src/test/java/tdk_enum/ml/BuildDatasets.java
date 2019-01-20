package tdk_enum.ml;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BuildDatasets {

    @Test
    public void build()
    {
        String datasetName = "steiner_tree";
        String datasetCsvFilePath = "./" +datasetName + ".csv";
        for (int i =1; i <=10; i++)
        {
            String folderPath = "/home/dvirdu/IdeaProjects/efficient-td-enum/datasets/" + datasetName + "/" +i;
            try (BufferedReader br = new BufferedReader(new FileReader(datasetCsvFilePath))) {
                String line;
                int j = 0;
                int test = 0;
                int train =0;
                while ((line = br.readLine()) != null) {
                    j++;
                    File input = new File(line);
                    if(!input.exists())
                    {
                        System.out.println("file not exist");
                    }
                    double rand = Math.random();
                    String trainOrTest = "";
                    if (rand <= ((double)20/70))
                    {
                     if (train <20)
                     {
                         train++;
                         trainOrTest = "train";

                     }
                     else {
                         trainOrTest= "test";
                         test++;
                     }
                    }
                    else
                    {
                        if(test <50)
                        {
                            test++;
                            trainOrTest = "test";
                        }
                        else
                        {
                            trainOrTest = "train";
                            train++;
                        }
                    }

                    File newFile = new File(folderPath+"/" + trainOrTest +"/"+input.getParentFile().getName() +"_"+input.getName());
                    newFile.getParentFile().mkdirs();
//                    newFile.createNewFile();
                    Files.copy(input.toPath(), newFile.toPath());
                }
                System.out.println(j + " files where copied");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
