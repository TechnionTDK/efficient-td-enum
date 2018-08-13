package tdk_enum.enumerators.common;

import tdk_enum.enumerators.common.AbstractEnumerator;

public abstract class AbstractConverterEnumerator <NodeType, EnumType, GraphType> extends AbstractEnumerator<NodeType, EnumType, GraphType> {
    @Override
    protected void iteratingNodePhase() {

    }

    @Override
    public void stepByStepDoFirstStep() {

    }

    @Override
    protected void getNextResultToManipulate() {

    }

    @Override
    protected boolean runStepByStepFullEnumeration() {
        return false;
    }

    @Override
    protected EnumType manipulateNodeAndResult(NodeType node, EnumType result) {
        return null;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromNode(NodeType node) {
        return false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResultFromResult(EnumType result) {
        return false;
    }

    @Override
    protected boolean stepByStepTryGenerateNewResult(NodeType node, EnumType currentResult) {
        return false;
    }

    @Override
    protected void doFirstStep() {

    }

    @Override
    protected void tryGenerateNewResult(NodeType node, EnumType result) {

    }

    @Override
    protected void newResultFound(EnumType c) {

    }

    @Override
    protected void changeVIfNeeded() {

    }

    @Override
    protected boolean newStepByStepResultFound(EnumType c) {
        return false;
    }

    @Override
    protected boolean stepByStepIteratingNodesPhase() {
        return false;
    }


}
