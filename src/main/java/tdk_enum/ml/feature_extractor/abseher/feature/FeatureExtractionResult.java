package tdk_enum.ml.feature_extractor.abseher.feature;

import tdk_enum.graph.graphs.IGraph;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class FeatureExtractionResult {

    private int tdID = 0;
    private IGraph instance = null;

    private double userTime = -1;
    private double systemTime = -1;
    private double wallClockTime = -1;

    private double memoryConsumption = -1;

    private boolean memoryError = false;
    private boolean timeoutError = false;
    private boolean exitCodeError = false;

    private List<FeatureMeasurement> measurements = null;

    public FeatureExtractionResult(int tdID,
                                   IGraph instance) {
        this.measurements = new ArrayList<>();

        this.tdID = this.tdID;
        this.instance = instance;

        this.userTime = Double.NaN;
        this.systemTime = Double.NaN;
        this.wallClockTime = Double.NaN;
        this.memoryConsumption = Double.NaN;

        this.memoryError = false;
        this.timeoutError = false;
        this.exitCodeError = false;
    }

    public FeatureExtractionResult(int tdID,
                                   IGraph instance,
                                   double userTime,
                                   double systemTime,
                                   double wallClockTime,
                                   double memoryConsumption,
                                   boolean memoryError,
                                   boolean timeoutError,
                                   boolean exitCodeError) {
        this.measurements = new ArrayList<>();

        this.tdID = tdID;
        this.instance = instance;
        this.userTime = userTime;
        this.systemTime = systemTime;
        this.wallClockTime = wallClockTime;
        this.memoryConsumption = memoryConsumption;

        this.memoryError = memoryError;
        this.timeoutError = timeoutError;
        this.exitCodeError = exitCodeError;
    }

    public int getTdID() {
        return tdID;
    }

    public String getInstancePath() {
        String ret = null;

        if (instance != null) {
            ret = instance.getOriginalPath();
        }

        return ret;
    }

    public double getUserTime() {
        return userTime;
    }

    public double getSystemTime() {
        return systemTime;
    }

    public double getWallClockTime() {
        return wallClockTime;
    }

    public double getMemoryConsumption() {
        return memoryConsumption;
    }

    public boolean isMemoryError() {
        return memoryError;
    }

    public boolean isTimeoutError() {
        return timeoutError;
    }

    public boolean isExitCodeError() {
        return exitCodeError;
    }

    public int getMeasurementCount() {
        return measurements.size();
    }

    public FeatureMeasurement getMeasurement(int index) {
        FeatureMeasurement ret = null;

        if (index >= 0 && index < measurements.size()) {
            ret = measurements.get(index).getDeepCopy();
        }

        return ret;
    }

    public FeatureMeasurement accessMeasurement(int index) {
        FeatureMeasurement ret = null;

        if (index >= 0 && index < measurements.size()) {
            ret = measurements.get(index);
        }

        return ret;
    }

    public void addMeasurement(FeatureMeasurement measurement) {
        if (measurement != null) {
            measurements.add(measurement);
        }
    }

    public String getCSVHeader() {
        StringBuilder sb =
                new StringBuilder();

        sb.append("Instance,");
        sb.append("Seed,");
        sb.append("User-Time,");
        sb.append("System-Time,");
        sb.append("Wall-Clock-Time,");
        sb.append("MemoryConsumption,");
        sb.append("\"Number of Memory-Errors\",");
        sb.append("\"Number of Timeout-Errors\",");
        sb.append("\"Number of Exitcode-Errors\"");

        if (!measurements.isEmpty()) {
            sb.append(",");

            for (int i = 0; i < measurements.size(); i++) {
                FeatureMeasurement measurement =
                        measurements.get(i);

                sb.append(measurement.getCSVHeaders());

                if (i < measurements.size() - 1) {
                    sb.append(",");
                }
            }
        }

        return sb.toString();
    }

    public String toCSV() {
        StringBuilder sb =
                new StringBuilder();

        DecimalFormat format =
                new DecimalFormat("0.000");

        sb.append("\"");
        if (instance != null) {
            sb.append(instance.getOriginalPath());
        }
        else {
            sb.append("<UNKNOWN>");
        }
        sb.append("\",");
        sb.append(tdID);
        sb.append(",");

        if (userTime >= 0) {
            sb.append(format.format(userTime));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (systemTime >= 0) {
            sb.append(format.format(systemTime));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (wallClockTime >= 0) {
            sb.append(format.format(wallClockTime));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (memoryConsumption >= 0) {
            sb.append(format.format(memoryConsumption));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (memoryError) {
            sb.append(1);
        }
        else {
            sb.append(0);
        }

        sb.append(",");

        if (timeoutError) {
            sb.append(1);
        }
        else {
            sb.append(0);
        }

        sb.append(",");

        if (exitCodeError) {
            sb.append(1);
        }
        else {
            sb.append(0);
        }

        sb.append(",");

        for (int i = 0; i < measurements.size(); i++) {
            FeatureMeasurement measurement =
                    measurements.get(i);

            sb.append(measurement.toCSV());

            if (i < measurements.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public Map<String, Double> toFlatMap()
    {
        Map<String, Double> features = new LinkedHashMap<>();

        features.put("ID", (double)tdID);
        features.put("User-Time", userTime);
        features.put("System-Time", systemTime);
        features.put("Wall-Clock-Time", wallClockTime);
        features.put("MemoryConsumption", memoryConsumption);
        features.put("Number of Memory-Errors", memoryError ? 1.0 : 0.0);
        features.put("Number of Timeout-Errors", timeoutError ? 1.0: 0.0);
        features.put("Number of Exitcode-Errors", exitCodeError? 1.0: 0.0);
        for (int i = 0; i < measurements.size(); i++) {
            FeatureMeasurement measurement =
                    measurements.get(i);

           features.putAll(measurement.toFlatMap());
        }
        return features;

    }


}