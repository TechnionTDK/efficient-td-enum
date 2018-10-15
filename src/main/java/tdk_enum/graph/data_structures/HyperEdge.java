package tdk_enum.graph.data_structures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class HyperEdge {

    private List<String> vertices = null;

    private HyperEdge(List<String> vertices) {
        this.vertices = vertices;
    }

    public boolean containsVertex(String id) {
        return vertices.contains(id);
    }

    public List<String> getVertices() {
        List<String> ret =
                new ArrayList<>(vertices);

        return ret;
    }

    public List<String> accessVertices() {
        return vertices;
    }

    public static HyperEdge getInstance(String vertex) {
        HyperEdge ret = null;

        if (vertex != null) {
            List<String> content =
                    new ArrayList<>();

            content.add(vertex);

            ret = getInstance(content);
        }

        return ret;
    }

    public static HyperEdge getInstance(String vertex1,
                                        String vertex2) {
        HyperEdge ret = null;

        if (vertex1 != null && vertex2 != null) {
            List<String> content =
                    new ArrayList<>();

            content.add(vertex1);
            content.add(vertex2);

            ret = getInstance(content);
        }

        return ret;
    }

    public static HyperEdge getInstance(List<String> vertices) {
        HyperEdge ret = null;

        if (vertices != null) {
            List<String> content = new ArrayList<>();

            for (String vertex : vertices) {
                if (vertex != null && !content.contains(vertex)) {
                    content.add(vertex);
                }
            }

            if (!content.isEmpty()) {
                ret = new HyperEdge(content);
            }
        }

        return ret;
    }

}