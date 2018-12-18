package tdk_enum.ml.classifiers.common;

/**
 *
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class EvaluatedDecompositionDetails extends PredictedDecompositionDetails {

    private int exitCode = -1;

    private boolean memoryError = false;
    private boolean timeoutError = false;
    private boolean exitCodeError = false;

    private double actualRuntime = Double.NaN;
    private double memoryConsumption = Double.NaN;

    private EvaluatedDecompositionDetails(PredictedDecompositionDetails decompositionDetails, double actualRuntime, double memoryConsumption, int exitCode, boolean memoryError, boolean timeoutError, boolean exitCodeError) {
        super(decompositionDetails.getInstancePath(), decompositionDetails.getTdId(), decompositionDetails.getRelevantAttributeValues(), decompositionDetails.getPredictedRuntime());

        this.exitCode = exitCode;

        this.memoryError = memoryError;
        this.timeoutError = timeoutError;
        this.exitCodeError = exitCodeError;

        this.actualRuntime = actualRuntime;
        this.memoryConsumption = memoryConsumption;
    }

    public int getExitCode() {
        return exitCode;
    }

    public double getActualRuntime() {
        return actualRuntime;
    }

    public double getMemoryConsumption() {
        return memoryConsumption;
    }

    public boolean isValidMeasurement() {
        return !memoryError && !timeoutError && !exitCodeError;
    }

    public boolean isMemoryErrorOccurred() {
        return memoryError;
    }

    public boolean isTimeoutErrorOccurred() {
        return timeoutError;
    }

    public boolean isExitCodeErrorOccurred() {
        return exitCodeError;
    }

    @Override
    public String toCSV() {
        return super.toCSV(actualRuntime);
    }

    public static EvaluatedDecompositionDetails getInstance(PredictedDecompositionDetails decompositionDetails, double actualRuntime, double memoryConsumption, int exitCode, boolean memoryError, boolean timeoutError, boolean exitCodeError) {
        EvaluatedDecompositionDetails ret = null;

        if (decompositionDetails != null) {
            ret = new EvaluatedDecompositionDetails(decompositionDetails, actualRuntime, memoryConsumption, exitCode, memoryError, timeoutError, exitCodeError);
        }

        return ret;
    }

}
