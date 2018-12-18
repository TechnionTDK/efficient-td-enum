package tdk_enum.ml.classifiers.common;

import tdk_enum.ml.feature_extractor.abseher.feature.BenchmarkRun;
import tdk_enum.ml.solvers.execution.MemoryFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class BenchmarkDetails {

    private List<BenchmarkRun> benchmarkRuns = null;

    public BenchmarkDetails() {
        this.benchmarkRuns = new ArrayList<>();
    }

    public void addBenchmarkRun(BenchmarkRun benchmarkRun) {
        if (benchmarkRun != null) {
            benchmarkRuns.add(benchmarkRun);
        }
    }

    public boolean containsValues() {
        return !benchmarkRuns.isEmpty();
    }

    public List<BenchmarkRun> getBenchmarkRuns() {
        return new ArrayList<>(benchmarkRuns);
    }

    public List<BenchmarkRun> getBenchmarkRuns(File instance) {
        List<BenchmarkRun> ret = new ArrayList<>();

        if (instance != null) {
            for (BenchmarkRun benchmarkRun : benchmarkRuns) {
                if (benchmarkRun != null && instance.equals(benchmarkRun.getInstance())) {
                    ret.add(benchmarkRun);
                }
            }
        }

        return ret;
    }

    public List<BenchmarkRun> getBenchmarkRuns(String instance) {
        List<BenchmarkRun> ret = new ArrayList<>();

        if (instance != null && !instance.isEmpty()) {
            ret = getBenchmarkRuns(new File(instance));
        }

        return ret;
    }

    public int getBenchmarkRunCount() {
        return benchmarkRuns.size();
    }

    public List<Double> getRunTimes() {
        List<Double> ret = new ArrayList<>();

        for (BenchmarkRun benchmarkRun : benchmarkRuns) {
            if (benchmarkRun != null) {
                ret.add(benchmarkRun.getUserTime());
            }
            else {
                ret.add(Double.NaN);
            }
        }

        return ret;
    }

    public List<Double> getRunTimes(File instance) {
        List<Double> ret = new ArrayList<>();

        if (instance != null) {
            for (BenchmarkRun benchmarkRun : benchmarkRuns) {
                if (benchmarkRun != null && instance.equals(benchmarkRun.getInstance())) {
                    ret.add(benchmarkRun.getUserTime());
                }
            }
        }

        return ret;
    }

    public List<Double> getRunTimes(String instance) {
        List<Double> ret = new ArrayList<>();

        if (instance != null && !instance.isEmpty()) {
            ret = getRunTimes(new File(instance));
        }

        return ret;
    }

    public List<File> getInstances() {
        List<File> ret = new ArrayList<>();

        for (BenchmarkRun benchmarkRun : benchmarkRuns) {
            if (benchmarkRun != null) {
                File instance =
                        benchmarkRun.getInstance();

                if (instance != null && !ret.contains(instance)) {
                    ret.add(instance);
                }
            }

            Collections.sort(ret);
        }

        return ret;
    }

    public MemoryFile toCSVFile(File target) {
        return toCSVFile(target, true);
    }

    public MemoryFile toCSVFile(File target, boolean includeHeader)
    {
        MemoryFile ret =
                MemoryFile.getInstance(target);

        if (ret != null) {
            if (includeHeader) {
                ret.appendLine(BenchmarkRun.getCSVHeaders());
            }

            if (benchmarkRuns != null) {
                for (BenchmarkRun run : benchmarkRuns) {
                    if (run != null) {
                        ret.appendLine(run.toCSV());
                    }
                }
            }
        }

        return ret;
    }

    public static BenchmarkDetails importFile(File file) {
        BenchmarkDetails ret = null;

        if (file != null && file.exists() && file.isFile() && file.canRead()) {
            ret = importFile(file.getAbsolutePath());
        }

        return ret;
    }

    public static BenchmarkDetails importFile(String path) {
        BenchmarkDetails ret =
                new BenchmarkDetails();

        BufferedReader br = null;

        if (path != null && !path.trim().isEmpty()) {
            try {
                br = new BufferedReader(new FileReader(path));

                boolean exit = false;
                boolean firstLine = true;

                while (!exit && br.ready()) {
                    String line = br.readLine();

                    if (firstLine) {
                        firstLine = false;
                    }
                    else {
                        if (line != null && !line.trim().isEmpty()) {
                            BenchmarkRun run = BenchmarkRun.importCSV(line);

                            if (run != null) {
                                ret.addBenchmarkRun(run);
                            }
                        }
                        else {
                            exit = true;
                        }
                    }
                }
            }
            catch (IOException ex) {

            }
            finally {
                if (br != null) {
                    try {
                        br.close();
                    }
                    catch (IOException ex) {

                    }
                }
            }

            if (!ret.containsValues()) {
                ret = null;
            }
        }

        return ret;
    }

}