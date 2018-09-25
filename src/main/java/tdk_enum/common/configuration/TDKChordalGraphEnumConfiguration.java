package tdk_enum.common.configuration;

import tdk_enum.common.configuration.config_types.*;

import static tdk_enum.common.configuration.config_types.MinimalTriangulatorType.BASELINE;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TaskManagerType.BUSY_WAIT;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;

public class TDKChordalGraphEnumConfiguration extends TDKEnumConfiguration {

    protected SingleThreadMISEnumeratorType singleThreadMISEnumeratorType = SingleThreadMISEnumeratorType.IMPROVED_JV_CACHE;

    protected ParallelMISEnumeratorType parallelMISEnumeratorType = ParallelMISEnumeratorType.HORIZONTAL;


    protected MinimalTriangulatorType minimalTriangulatorType = BASELINE;


    protected TriangulationScoringCriterion triangulationScoringCriterion = NONE;



    protected SeparatorsGraphType separatorsGraphType = SeparatorsGraphType.CACHED;

    protected SeparatorsScoringCriterion separatorsScoringCriterion = UNIFORM;

    protected SetsExtenderType setsExtenderType = SetsExtenderType.VANILLA;

    protected TriangulationAlgorithm triangulationAlgorithm = MCS_M;

    protected TaskManagerType taskManagerType = BUSY_WAIT;

    protected CachePolicy cachePolicy = CachePolicy.NONE;

    protected int k=1;


    public CachePolicy getCachePolicy() {
        return cachePolicy;
    }

    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public TaskManagerType getTaskManagerType() {
        return taskManagerType;
    }

    public void setTaskManagerType(TaskManagerType taskManagerType) {
        this.taskManagerType = taskManagerType;
    }

    public SingleThreadMISEnumeratorType getSingleThreadMISEnumeratorType() {
        return singleThreadMISEnumeratorType;
    }

    public void setSingleThreadMISEnumeratorType(SingleThreadMISEnumeratorType singleThreadMISEnumeratorType) {
        this.singleThreadMISEnumeratorType = singleThreadMISEnumeratorType;
    }

    public ParallelMISEnumeratorType getParallelMISEnumeratorType() {
        return parallelMISEnumeratorType;
    }

    public void setParallelMISEnumeratorType(ParallelMISEnumeratorType parallelMISEnumeratorType) {
        this.parallelMISEnumeratorType = parallelMISEnumeratorType;
    }

    public MinimalTriangulatorType getMinimalTriangulatorType() {
        return minimalTriangulatorType;
    }

    public void setMinimalTriangulatorType(MinimalTriangulatorType minimalTriangulatorType) {
        this.minimalTriangulatorType = minimalTriangulatorType;
    }

    public TriangulationScoringCriterion getTriangulationScoringCriterion() {
        return triangulationScoringCriterion;
    }

    public void setTriangulationScoringCriterion(TriangulationScoringCriterion triangulationScoringCriterion) {
        this.triangulationScoringCriterion = triangulationScoringCriterion;
    }

    public SeparatorsGraphType getSeparatorsGraphType() {
        return separatorsGraphType;
    }

    public void setSeparatorsGraphType(SeparatorsGraphType separatorsGraphType) {
        this.separatorsGraphType = separatorsGraphType;
    }

    public SeparatorsScoringCriterion getSeparatorsScoringCriterion() {
        return separatorsScoringCriterion;
    }

    public void setSeparatorsScoringCriterion(SeparatorsScoringCriterion separatorsScoringCriterion) {
        this.separatorsScoringCriterion = separatorsScoringCriterion;
    }

    public SetsExtenderType getSetsExtenderType() {
        return setsExtenderType;
    }

    public void setSetsExtenderType(SetsExtenderType setsExtenderType) {
        this.setsExtenderType = setsExtenderType;
    }

    public TriangulationAlgorithm getTriangulationAlgorithm() {
        return triangulationAlgorithm;
    }

    public void setTriangulationAlgorithm(TriangulationAlgorithm triangulationAlgorithm) {
        this.triangulationAlgorithm = triangulationAlgorithm;
    }


}
