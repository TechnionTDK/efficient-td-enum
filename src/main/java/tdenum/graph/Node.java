package tdenum.graph;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */
public class Node {
    int value;

    public Node(int value)
    {
        this.value = value;
    }

    public int intValue()
    {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder().append(value);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return value == node.value;
    }

    @Override
    public int hashCode() {
        return value;
    }


}
