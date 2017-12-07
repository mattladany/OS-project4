/**
 * Class to store information about objects in the matrix.
 *
 *  NOTE: Instance variables are 'public' to simulate the functionality of a
 *  C struct.
 */
public class Object_t {

    public int index;
    public String owner;
    public String group;

    /**
     * Constructor to assign parameters to the instance variables.
     *
     * @param index To be assigned to this.index.
     * @param owner To be assigned to this.owner.
     * @param group To be assigned to this.group.
     */
    public Object_t(int index, String owner, String group) {
        this.index = index;
        this.owner = owner;
        this.group = group;
    }
}
