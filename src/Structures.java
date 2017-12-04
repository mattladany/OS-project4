import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to hold the public data structures, used for the protection of objects.
 */
public class Structures {

    public static Map<String, List> matrix;
    public static Map<String, List> groups;
    public static Map<String, Integer> objects;


    /**
     * Function to print a String representation of the matrix structure.
     *
     * @return A grid displaying the values in the matrix.
     */
    public static String printMatrix() {

        StringBuilder output = new StringBuilder();
        output.append("|     ");

        // Outputting the first line
        Set<String> objKeys = objects.keySet();
        for (String key : objKeys) {
            output.append(String.format("| %3s ", key));
        }
        output.append("|\n");
        output.append("-------------------------------------------------------------------\n");

        // Outputting the rest of the lines
        Set<String> matrixKeys = matrix.keySet();

        for(String key : matrixKeys) {
            output.append(String.format("| %3s ", key));
            for(Object perm : matrix.get(key)) {
                String permission = (String)perm;
                output.append(String.format("| %3s ", permission));
            }
            output.append("|\n");
        }

        return output.toString();
    }


    public static String printGroups() {

        return null;
    }
}
