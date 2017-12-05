import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to hold the public data structures, used for the protection of objects.
 */
public class Structures {

    public static Map<String, List<String>> matrix;
    public static Map<String, Set<String>> groups;
    public static Map<String, Object_t> objects;
    public static Set<String> _groups;
    public static Set<String> _users;


    /**
     * Debug function to print a String representation of the matrix structure.
     *
     * @return A grid displaying the values in the matrix.
     */
    public static String printMatrix() {

        StringBuilder output = new StringBuilder();
        output.append("-----------------------------------------------------" +
                "--------------\n");
        output.append("|     ");

        // Outputting the first line
        Set<String> objKeys = objects.keySet();
        for (String key : objKeys) {
            output.append(String.format("| %3s ", key));
        }
        output.append("|\n");
        output.append("-----------------------------------------------------" +
                "--------------\n");

        // Outputting the rest of the lines
        Set<String> matrixKeys = matrix.keySet();

        for(String key : matrixKeys) {
            output.append(String.format("| %3s ", key));
            for(Object perm : matrix.get(key)) {
                String permission = (String)perm;
                output.append(String.format("| %3s ", permission));
            }
            output.append("|\n");
            output.append("-----------------------------------------------------" +
                    "--------------\n");
        }

        return output.toString();
    }


    /**
     * Debug function to print a list of the groups, and the users in them.
     *
     * @return A table showing the groups, and their users.
     */
    public static String printGroups() {

        StringBuilder output = new StringBuilder();
        for (String group : _groups) {
            output.append(String.format("%s:", group));
                for (Object _user : groups.get(group)) {
                    String user = (String)_user;
                    output.append(String.format(" %s", user));
                }
            output.append("\n");
        }


        return output.toString();
    }


    /**
     * Debug function to print the information about each object.
     *
     * @return A grid displaying the objects, and their owner/group.
     */
    public static String printObjects() {

        StringBuilder output = new StringBuilder();
        output.append("-----------------------------------------------------" +
                "------------------------\n");
        output.append("|     ");

        // Outputting the first line
        Set<String> objKeys = objects.keySet();
        for (String key : objKeys) {
            output.append(String.format("| %4s ", key));
        }
        output.append("|\n");
        output.append("-----------------------------------------------------" +
                "------------------------\n");

        // Outputting the usr line
        output.append("| usr ");
        for (String key : objKeys) {
            output.append(String.format("| %4s ", objects.get(key).owner));
        }
        output.append("|\n");
        output.append("-----------------------------------------------------" +
                "------------------------\n");

        // Outputting the grp line
        output.append("| grp ");
        for (String key : objKeys) {
            output.append(String.format("| %4s ", objects.get(key).group));
        }
        output.append("|\n");
            output.append("-----------------------------------------------------" +
                    "------------------------\n");

        return output.toString();
    }
}
