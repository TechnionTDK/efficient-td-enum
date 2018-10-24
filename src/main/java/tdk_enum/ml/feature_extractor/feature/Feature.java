package tdk_enum.ml.feature_extractor.feature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class Feature {

    private String name = "<UNKNOWN>";

    public Feature(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

}