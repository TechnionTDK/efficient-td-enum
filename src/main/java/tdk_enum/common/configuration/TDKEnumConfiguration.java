package tdk_enum.common.configuration;

import tdk_enum.common.configuration.config_types.*;

import static tdk_enum.common.configuration.config_types.EnumerationPurpose.STANDALONE;
import static tdk_enum.common.configuration.config_types.EnumerationType.SEPARATORS;
import static tdk_enum.common.configuration.config_types.RunningMode.SINGLE_THREAD;
import static tdk_enum.common.configuration.config_types.WhenToPrint.NEVER;

public abstract class TDKEnumConfiguration {


    int id;

    protected EnumerationType enumerationType = SEPARATORS;

    protected RunningMode runningMode = SINGLE_THREAD;

    protected EnumerationPurpose enumerationPurpose= STANDALONE;

    protected int benchMarkBaseId;




    protected int time_limit;

    protected WhenToPrint whenToPrint = NEVER;

    protected OutputType outputType = OutputType.CSV;

    protected String fileName = "";

    protected Integer threadNumber = Runtime.getRuntime().availableProcessors();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnumerationType getEnumerationType() {
        return enumerationType;
    }

    public void setEnumerationType(EnumerationType enumerationType) {
        this.enumerationType = enumerationType;
    }

    public RunningMode getRunningMode() {
        return runningMode;
    }

    public void setRunningMode(RunningMode runningMode) {
        this.runningMode = runningMode;
    }

    public EnumerationPurpose getEnumerationPurpose() {
        return enumerationPurpose;
    }

    public void setEnumerationPurpose(EnumerationPurpose enumerationPurpose) {
        this.enumerationPurpose = enumerationPurpose;
    }

    public int getBenchMarkBaseId() {
        return benchMarkBaseId;
    }

    public void setBenchMarkBaseId(int benchMarkBaseId) {
        this.benchMarkBaseId = benchMarkBaseId;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public WhenToPrint getWhenToPrint() {
        return whenToPrint;
    }

    public void setWhenToPrint(WhenToPrint whenToPrint) {
        this.whenToPrint = whenToPrint;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getThreadNumder() {
        return threadNumber;
    }

    public void setThreadNumder(Integer threadNumder) {
        this.threadNumber = threadNumder;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }
}
