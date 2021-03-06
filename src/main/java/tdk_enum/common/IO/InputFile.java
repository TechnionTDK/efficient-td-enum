package tdk_enum.common.IO;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputFile {

    String name;
    String wholeName;
    String innerContainingFolder = "None";
    String outerContainingFolder = "None";

    Path path;

    File file;

    public InputFile(String fileName)
    {
        wholeName = fileName;
        Path path = Paths.get(fileName);
        this.path = path;
        this.file = path.toFile();
        Path parent = path.getParent();
        if (parent == null)
        {
            name = FilenameUtils.getBaseName(fileName);
        }
        else
        {
            name = FilenameUtils.getBaseName(fileName);

            innerContainingFolder = parent.getFileName().toString();
            Path parentParent = parent.getParent();
            if (!(parentParent == null))
            {
                outerContainingFolder = parentParent.getFileName().toString();
            }

        }
    }

    public String getName() {
        return name;
    }



    public String getPath() {
        return wholeName;
    }



    public String getType() {
        return innerContainingFolder;
    }



    public String getField() {
        return outerContainingFolder;
    }

    public File getFile()
    {
        return file;
    }


}
