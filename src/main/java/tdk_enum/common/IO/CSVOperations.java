package tdk_enum.common.IO;

public class CSVOperations {






    public static String dataToCSV(Object... args)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < args.length; i++)
        {
            sb.append(args[i]);
            if (i != args.length-1)
            {
               sb.append(",");
            }


        }
        return sb.toString();
    }
}
