package tdenum.graph.data_structures;

import java.util.Collection;

/**
 * Created by dvir.dukhan on 7/5/2017.
 */
public class MinimalSeparator extends NodeSet
{

    int id = -1;

    public MinimalSeparator()
    {
        super();
    }

    public MinimalSeparator(Collection<Node> s)
    {
        super(s);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
//        final StringBuilder sb = new StringBuilder("MinimalSeparator{");
//        sb.append("id=").append(id);
//        sb.append('}');
        final StringBuilder sb = new StringBuilder(id);
        return sb.toString();
    }
}
