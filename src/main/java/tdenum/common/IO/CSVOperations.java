package tdenum.common.IO;

import java.io.File;
import java.io.IOException;

public class CSVOperations {






    public static String dataToCSV(Object... args)
    {
        StringBuilder sb = new StringBuilder();
        for (Object o :args)
        {
            sb.append(o).append(",");

        }
        return sb.toString();
    }
}
