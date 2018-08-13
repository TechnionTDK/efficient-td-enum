package tdk_enum.graph.data_structures;

import tdk_enum.graph.data_structures.Edge;
import tdk_enum.graph.data_structures.Node;

public class WeightedEdge extends Edge {

    int weight = 0;



    public WeightedEdge(Node u, Node v) {
        super(u, v);
    }

    public WeightedEdge(int u, int v) {
        super(u, v);
    }


    public WeightedEdge(Node u, Node v, int weight) {

        super(u, v);
        this.weight = weight;
    }

    public WeightedEdge(int u, int v, int weight) {
        super(u, v);
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
