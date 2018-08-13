package tdk_enum.enumerators.generators;

public interface IGenerator<GraphType, EnumType> {

    EnumType generateNew(GraphType graph, EnumType B);
    EnumType generateNew (EnumType B);
}
