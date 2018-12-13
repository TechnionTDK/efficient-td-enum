package tdk_enum.ml.solvers.execution;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class MemoryFile {
    private File target = null;

    private String title = null;

    private List<String> content = null;

    private MemoryFile() {
        this.target = null;

        this.content = new ArrayList<>();
    }

    private MemoryFile(File target) {
        this.target = target;

        this.content = new ArrayList<>();
    }

    private MemoryFile(File target, String title) {
        this.title = title;
        this.target = target;

        this.content = new ArrayList<>();
    }

    public File getTarget() {
        return target;
    }

    public String getTitle() {
        return title;
    }

    public String getTargetPath() {
        String ret = "<UNKNOWN>";

        if (target != null) {
            ret = target.getAbsolutePath();
        }

        return ret;
    }

    public int getLineCount() {
        return content.size();
    }

    public boolean isEmptyFile() {
        return content.isEmpty();
    }

    public String getLine(int index) {
        String ret = null;

        if (index >= 0 && index < content.size()) {
            ret = content.get(index);
        }

        return ret;
    }

    public String consumeLine(int index) {
        String ret = null;

        if (index >= 0 && index < content.size()) {
            ret = content.remove(index);
        }

        return ret;
    }

    public boolean appendLine() {
        content.add("");

        return true;
    }

    public boolean appendLine(String line) {
        boolean ret = false;

        if (line != null) {
            content.add(line);

            ret = true;
        }

        return ret;
    }

    public boolean insertLine(String line, int index) {
        boolean ret = false;

        if (line != null && index >= 0 && index <= content.size()) {
            if (index == content.size()) {
                content.add(line);

                ret = true;
            } else {
                try {
                    content.add(index, line);

                    ret = true;
                } catch (IndexOutOfBoundsException ex) {

                }
            }
        }

        return ret;
    }

    public boolean importLines(MemoryFile memoryFile) {
        boolean ret = false;

        if (memoryFile != null) {
            ret = memoryFile.exportLines(this);
        }

        return ret;
    }

    public boolean exportLines(MemoryFile memoryFile) {
        boolean ret = false;

        if (memoryFile != null) {
            for (String line : content) {
                memoryFile.appendLine(line);
            }

            ret = true;
        }

        return ret;
    }

    public boolean export(boolean override) {
        boolean ret = false;

        if (target != null) {
            ret = export(target, override);
        }

        return ret;
    }

    public boolean export(String targetPath, boolean override) {
        boolean ret = false;

        if (targetPath != null) {
            File targetFile =
                    new File(targetPath);

            ret = export(targetFile, override);
        }

        return ret;
    }

    public boolean export(File targetFile, boolean override) {
        boolean ret = false;

        if (targetFile != null) {
            if (!targetFile.exists() || override) {
                try {
                    FileWriter fw =
                            new FileWriter(targetFile);

                    try (BufferedWriter bw =
                                 new BufferedWriter(fw)) {

                        PrintWriter pw =
                                new PrintWriter(bw);

                        for (String value : content) {
                            pw.println(value);
                        }

                        bw.flush();
                    }

                    ret = true;
                } catch (IOException ex) {

                }
            }
        }

        return ret;
    }

    public void print() {
        print(0);
    }

    public void print(int indentationWidth) {
        for (String value : content) {
            System.out.println(value);
        }
    }

    public void clear() {
        this.content = new ArrayList<>();
    }

    public static boolean tryImportFile(String targetFile) {
        boolean ret = false;

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(targetFile));

            br.readLine();

            ret = true;
        } catch (IOException ex) {
            ret = false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ret = false;
                }
            }
        }

        return ret;
    }

    public static MemoryFile importFile(String targetFile) {
        return importFile(targetFile, targetFile);
    }

    public static MemoryFile importFile(String targetPath, String title) {
        MemoryFile ret = null;

        BufferedReader br = null;

        if (targetPath != null) {
            File targetFile =
                    new File(targetPath);

            try {
                br = new BufferedReader(new FileReader(targetFile));

                ret = new MemoryFile(targetFile, title);

                while (br.ready()) {
                    String line = br.readLine();

                    if (line != null) {
                        ret.appendLine(line);
                    }
                }
            } catch (IOException ex) {
                ret = null;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        ret = null;
                    }
                }
            }
        }

        return ret;
    }

    public static MemoryFile getInstance() {
        return new MemoryFile(null, "<UNKNOWN>");
    }

    public static MemoryFile getInstance(File target) {
        return getInstance(target, "<UNKNOWN>");
    }

    public static MemoryFile getInstance(File target, String title) {
        MemoryFile ret = null;

        if (target != null && title != null) {
            ret = new MemoryFile(target, title);
        }

        return ret;

    }


    @Override
    public String toString() {
        return "MemoryFile{" +
                "content=" + content +
                '}';
    }
}