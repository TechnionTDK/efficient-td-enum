package tdk_enum.common.configuration.config_types;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public enum TransformationMode {
    NONE(0), NORMALIZE(1), STANDARDIZE(2);

    private int value = 0;

    TransformationMode(int value) {
        this.value = value;
    }

    public int getStatusValue() {
        return value;
    }

    public static TransformationMode getFromStatusValue(int value) {
        switch (value) {
            case 0: {
                return NONE;
            }
            case 1: {
                return NORMALIZE;
            }
            case 2: {
                return STANDARDIZE;
            }
            default:
                return NONE;
        }
    }

    @Override
    public String toString() {
        String ret = "NONE";

        switch (value) {
            case 0: {
                ret = "NONE";

                break;
            }
            case 1: {
                ret = "NORMALIZE";

                break;
            }
            case 2: {
                ret = "STANDARDIZE";

                break;
            }
            default: {
                break;
            }
        }

        return ret;
    }

}
