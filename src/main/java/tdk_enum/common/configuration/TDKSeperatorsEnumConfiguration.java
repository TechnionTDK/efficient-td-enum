package tdk_enum.common.configuration;

import tdk_enum.common.configuration.config_types.ParallelSeperatorsEnumeratorType;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.common.configuration.config_types.SingleThreadSeperatorsEnumeratorType;
import tdk_enum.common.configuration.config_types.TaskManagerType;

import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TaskManagerType.BUSY_WAIT;

public class TDKSeperatorsEnumConfiguration extends TDKEnumConfiguration {

    protected SingleThreadSeperatorsEnumeratorType singleThreadSeperatorsEnumeratorType = SingleThreadSeperatorsEnumeratorType.VANILLA;

    protected ParallelSeperatorsEnumeratorType parallelSeperatorsEnumeratorType = ParallelSeperatorsEnumeratorType.VANILLA;

    protected TaskManagerType taskManagerType = BUSY_WAIT;

    protected SeparatorsScoringCriterion separatorsScoringCriterion = UNIFORM;

    public SeparatorsScoringCriterion getSeparatorsScoringCriterion() {
        return separatorsScoringCriterion;
    }

    public void setSeparatorsScoringCriterion(SeparatorsScoringCriterion separatorsScoringCriterion) {
        this.separatorsScoringCriterion = separatorsScoringCriterion;
    }

    public SingleThreadSeperatorsEnumeratorType getSingleThreadSeperatorsEnumeratorType() {
        return singleThreadSeperatorsEnumeratorType;
    }

    public void setSingleThreadSeperatorsEnumeratorType(SingleThreadSeperatorsEnumeratorType singleThreadSeperatorsEnumeratorType) {
        this.singleThreadSeperatorsEnumeratorType = singleThreadSeperatorsEnumeratorType;
    }

    public ParallelSeperatorsEnumeratorType getParallelSeperatorsEnumeratorType() {
        return parallelSeperatorsEnumeratorType;
    }

    public void setParallelSeperatorsEnumeratorType(ParallelSeperatorsEnumeratorType parallelSeperatorsEnumeratorType) {
        this.parallelSeperatorsEnumeratorType = parallelSeperatorsEnumeratorType;
    }

    public TaskManagerType getTaskManagerType() {
        return taskManagerType;
    }

    public void setTaskManagerType(TaskManagerType taskManagerType) {
        this.taskManagerType = taskManagerType;
    }
}
