/**
 * Class to store the entries in the input file.
 *  Instance variables are 'public' to simulate the functionality of a
 *  C struct.
 */
public class Entry {
    public String name;
    public String owner;
    public String group;

    /**
     * Constructor to assign values to variables.
     *
     * @param name To be stored in this.name.
     * @param owner To be stored in this.owner.
     * @param group To be stored in this.group.
     */
    public Entry(String name, String owner, String group) {
        this.name = name;
        this.owner = owner;
        this.group = group;
    }
}
