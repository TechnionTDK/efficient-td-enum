package tdk_enum.generators;

public interface IGenerator<GraphType, EnumType> {
    EnumType generateNew (GraphType graph, EnumType B);
    EnumType generateNew (EnumType B);
}
