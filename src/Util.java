/**
 * Class to hold static functions that will be used depending on the menu
 *  option entered when running the program.
 */
public class Util {

    /**
     * Function to be called when menu option 1 is entered.
     *  Will change the current user.
     *
     * @param user The user the user wants to switch to.
     * @return 1 on success; 0 on failure.
     */
    public static int su(String user) {

        return 1;
    }


    /**
     * Function to be called when menu option 2 is entered.
     *  Will change the owner of the specified object.
     *
     *  Note: Will only be valid if the current user is 'root'.
     *
     * @param user The user assign to the object.
     * @param obj The object the user is being assigned to.
     * @return 1 on success; 0 on failure.
     */
    public static int chown(String user, String obj) {

        return 1;
    }


    /**
     * Function to be called when menu option 3 is entered.
     *  Will change the group of the specified object.
     *
     *  Note: Will only be valid if the current user is 'root'.
     *
     * @param group The group assign to the object.
     * @param obj The object the group is being assigned to.
     * @return 1 on success; 0 on failure.
     */
    public static int chgrp(String group, String obj) {

        return 1;
    }


    /**
     * Function to be called when menu option 4 is entered.
     *  Will change the access rights of the specified object.
     *
     * @param obj The object whose rights are being changed.
     * @param rights The rights to assign to the object specified.
     * @return 1 on success; 0 on failure.
     */
    public static int chmod(String obj, String rights) {

        return 1;
    }


    /**
     * Function to be called when menu option 5 is entered.
     *  Will add a user to a group.
     *
     * @param user The user to add.
     * @param group The group the user should be added to.
     * @return 1 on success; 0 on failure.
     */
    public static int groupadd(String user, String group) {

        return 1;
    }

    /**
     * Function to be called when menu option 6 is entered.
     *  Will remove a user from a group.
     *
     * @param user The user to remove.
     * @param group The group the user is to be removed from.
     * @return 1 on success; 0 on failure.
     */
    public static int groupdel(String user, String group) {

        return 1;
    }


    /**
     * Function to be called when menu option 7 is entered.
     *  Will determine if the current user can access the given object.
     *
     * @param obj The object trying to be accessed.
     * @param method The method at which the object is trying to be accessed.
     * @return 1 on success; 0 on failure.
     */
    public static int access(String obj, String method) {

        return 1;
    }

}
