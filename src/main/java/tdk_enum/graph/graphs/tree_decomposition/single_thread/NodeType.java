package tdk_enum.graph.graphs.tree_decomposition.single_thread;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public enum NodeType {
    DEFAULT(0), ROOT(1), LEAF(2), JOIN(3), FORGET(4), EXCHANGE(5), INTRODUCE(6);

    private int value = 0;

    NodeType(int value) {
        this.value = value;
    }

    public int getStatusValue() {
        return value;
    }

    public static NodeType getFromStatusValue(int value) {
        switch (value) {
            case 0: {
                return DEFAULT;
            }
            case 1: {
                return ROOT;
            }
            case 2: {
                return LEAF;
            }
            case 3: {
                return JOIN;
            }
            case 4: {
                return FORGET;
            }
            case 5: {
                return EXCHANGE;
            }
            case 6: {
                return INTRODUCE;
            }
            default:
                return DEFAULT;
        }
    }
}