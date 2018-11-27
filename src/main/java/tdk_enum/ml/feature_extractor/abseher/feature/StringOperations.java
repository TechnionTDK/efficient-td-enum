package tdk_enum.ml.feature_extractor.abseher.feature;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class StringOperations {

    public static String indentString(String value, int indentationWidth) {
        String ret = value;

        if (ret != null && indentationWidth > 0) {
            String spaces = String.format("%" + indentationWidth + "s", "");

            ret = spaces + ret;
        }

        return ret;
    }

    public static String formatNumber(DecimalFormat format, double number) {
        String ret = Double.toString(number);

        if (format != null) {
            if (Double.isNaN(number)) {
                ret = "UNKNOWN";
            }
            else if (Double.isInfinite(number)) {
                ret = "INFINITE";
            }
            else {
                ret = format.format(number);
            }
        }

        return ret;
    }

    public static String escapeForCSV(String value) {
        String ret = value;

        if (ret != null) {
            ret = "\"" + ret.replaceAll("\"", "\"\"") + "\"";
        }
        else {
            ret = "UNKNOWN";
        }

        return ret;
    }

    public static String[] readFromCSV(String value) {
        List<String> ret =
                new ArrayList<>();

        if (value != null) {
            int length =
                    value.length();

            StringBuilder sb =
                    new StringBuilder();

            boolean isError = false;
            boolean isString = false;

            for (int i = 0; i < length; i++) {
                char symbol =
                        value.charAt(i);

                switch (symbol) {
                    case '\"': {
                        if (isString) {
                            if (i < length - 1) {
                                switch (value.charAt(i + 1))
                                {
                                    case '\"': {
                                        sb.append('\"');

                                        i++;

                                        break;
                                    }
                                    case ',': {
                                        isString = false;

                                        break;
                                    }
                                    default: {
                                        isString = false;
                                        isError = true;

                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            isString = true;
                        }

                        break;
                    }
                    case ',': {
                        if (!isString) {
                            ret.add(sb.toString());

                            sb = new StringBuilder();
                        }

                        if (isError) {
                            isError = false;
                        }

                        break;
                    }
                    default: {
                        if (!isError) {
                            sb.append(symbol);
                        }

                        break;
                    }
                }
            }

            ret.add(sb.toString());
        }

        return ret.toArray(new String[0]);
    }

}