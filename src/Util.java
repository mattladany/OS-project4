import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;
import java.util.Set;

/**
 * Class to hold static functions that will be used depending on the menu
 *  option entered when running the program.
 */
public class Util {

    /******** Final variables to bitmask with ********/
    public static final byte READ = 0b100;
    public static final byte WRITE = 0b10;
    public static final byte EXECUTE = 0b1;

    /******** The current user, to be updated by the su function ********/
    public static String current_user = "root";

    /**
     * Function to be called when menu option 1 is entered.
     *  Will change the current user.
     *
     *  BONUS: Will require a password parameter.
     *
     * @param user The user the user wants to switch to.
     * @return 1 on success; 0 on failure.
     */
    public static int su(String user) {

        // Checking if the specified user exists.
        if (!user.equals("root")) {
            if (!Structures._users.contains(user)) {
                System.out.println("User " + user + " does not exist.");
                return 0;
            }
        }

        // Performing su.
        current_user = user;

        return 1;
    }

    /**
     * Function to be called when menu option 1 is entered.
     *  Will change the current user if the given password is correct.
     *
     * @param user The user the user wants to switch to.
     * @param password The entered password for the specified user.
     * @return 1 on success; 0 on failure.
     */
    public static int su_bonus(String user, String password) {

        // Checking if the specified user exists.
        if (!user.equals("root")) {
            if (!Structures._users.contains(user)) {
                System.out.println("User " + user + " does not exist.");
                return 0;
            }
        }

        // Verifying the password.
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        md.update(password.getBytes());
        byte[] digest_bytes = md.digest();
        String computed_hash = String.format("%064x",
                new BigInteger(1, digest_bytes));
        Main.log("Computed SHA-256 hash: " + computed_hash);

        // Getting the stored hash for 'user' in the hashes.txt file.
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(new File("passwords/hashes.txt")));
            String line;
            while((line = reader.readLine()) != null) {
                String[] split_line = line.split(":");
                if (split_line[0].equals(user)) {
                    Main.log("Stored SHA-256 hash:   " + split_line[1]);

                    // Comparing the hashes to verify correctness.
                    if (computed_hash.equals(split_line[1])) break;
                    else {
                        System.out.println("Password for user " + user
                                + " is not correct.");
                        return 0;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }



        // Performing su.
        current_user = user;

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

        // Asserting that the current user is 'root'.
        if (!current_user.equals("root")) {
            System.out.println("Chown cannot be done by any user that" +
                    " is not root.");
            return 0;
        }

        // Checking if the specified user exists.
        if (!user.equals("root")) {
            if (!Structures._users.contains(user)) {
                System.out.println("User " + user + " does not exist.");
                return 0;
            }
        }

        // Checking if the specified object exists.
        if (Structures.objects.get(obj) == null) {
            System.out.println("Object " + obj + " does not exist.");
            return 0;
        }

        // Performing chown.
        Object_t object_t = Structures.objects.get(obj);
        object_t.owner = user;

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

        // Asserting that the current user is 'root'.
        if (!current_user.equals("root")) {
            System.out.println("Chgrp cannot be done by any user that" +
                    " is not root.");
            return 0;
        }

        // Checking if the specified group exists.
        if (!group.equals("root")) {
            if (!Structures._groups.contains(group)) {
                System.out.println("Group " + group + " does not exist.");
                return 0;
            }
        }

        // Checking if the specified object exists.
        if (Structures.objects.get(obj) == null) {
            System.out.println("Object " + obj + " does not exist.");
            return 0;
        }

        // Performing chgrp.
        Object_t object_t = Structures.objects.get(obj);
        object_t.group = group;

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

        // Asserting that the specified object exists.
        if (!Structures.objects.keySet().contains(obj)) {
            System.out.println("Object " + obj + " does not exist.");
            return 0;
        }

        // Asserting that the rights parameter is a valid access right.
        for (char c : rights.toCharArray()) {
            if (!valid_num(c)) {
                System.out.println("String provided is not a valid access" +
                        " right setting.");
                return 0;
            }
        }

        // Asserting that the current user has the permission to change the
        //  access rights of the specified object.
        if (!current_user.equals("root")
                && !current_user.equals(Structures.objects.get(obj).owner)) {
            System.out.println("The current user (" +  current_user + ") does " +
                    "not have the permission to change the access rights on " +
                    "object " + obj + ".");
            return 0;
        }

        // Performing chmod.
        for (String u : Structures._users) {
            List<String> rights_list = Structures.matrix.get(u);
            rights_list.set(Structures.objects.get(obj).index, rights);
            Structures.matrix.put(u, rights_list);
        }

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

        // Making sure that the current user is root.
        if (!current_user.equals("root")) {
            System.out.println("Groupadd cannot be done by any user that is " +
                    "not root.");
            return 0;
        }

        // Asserting that the specified user, exists.
        if (!Structures._users.contains(user)) {
            System.out.println("User " + user + " does not exist.");
            return 0;
        }

        // Asserting that the specified group, exists.
        if (!Structures._groups.contains(group)) {
            System.out.println("Group " + group + " does not exist.");
            return 0;
        }

        // Performing groupadd.
        Set<String> group_members = Structures.groups.get(group);
        group_members.add(user);
        Structures.groups.put(group, group_members);

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

        // Making sure the current user is root.
        if (!current_user.equals("root")) {
            System.out.println("Groupadd cannot be done by any user that is " +
                    "not root.");
            return 0;
        }

        // Asserting that the specified user, exists.
        if (!Structures._users.contains(user)) {
            System.out.println("User " + user + " does not exist.");
            return 0;
        }

        // Asserting that the specified group, exists.
        if (!Structures._groups.contains(group)) {
            System.out.println("Group " + group + " does not exist.");
            return 0;
        }

        // Asserting that the group contains the specified user.
        if (!Structures.groups.get(group).contains(user)) {
            System.out.println("Group " + group + " does not contain the " +
                    "user " + user + ".");
            return 0;
        }

        // Performing groupdel.
        Set<String> group_members = Structures.groups.get(group);
        group_members.remove(user);
        Structures.groups.put(group, group_members);

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

        // Asserting that the object specified, exists.
        if (!Structures.objects.containsKey(obj)) {
            System.out.println("Object " + obj + " does not exist.");
            return 0;
        }

        // Asserting the method specified is R, W, or X.
        if (!method.equals("W") && !method.equals("R")
                && !method.equals("X")) {
            System.out.println("Method " + method + " needs to be 'R', " +
                    "'W', or 'X'.");
            return 0;
        }

        // Performing the access.
        if (!current_user.equals("root")) {
            String permissions = Structures.matrix.get(current_user)
                    .get(Structures.objects.get(obj).index);

            // Owner rights
            if (current_user.equals(Structures.objects
                            .get(obj).owner)) {
                byte b = Byte.parseByte(permissions.substring(0, 1));
                if (!valid_access(b, method)) {
                    System.out.println("The current user (" + current_user +
                            ") does not have permission to read object " +
                            obj + ".");
                    return 0;
                }

            } // Group rights
            else if (Structures.groups.get(Structures.objects
                    .get(obj).group).contains(current_user)) {
                byte b = Byte.parseByte(permissions.substring(1, 2));
                if (!valid_access(b, method)) {
                    System.out.println("The current user (" + current_user +
                            ") does not have permission to write object " +
                            obj + ".");
                    return 0;
                }

            } // World rights
            else {
                byte b = Byte.parseByte(permissions.substring(2, 3));
                if (!valid_access(b, method)) {
                    System.out.println("The current user (" + current_user +
                            ") does not have permission to execute object " +
                            obj + ".");
                    return 0;
                }
            }
        }

        return 1;
    }


    /**
     * Function to determine if the specified method is valid, given the
     *  specified byte.
     *
     *  The given byte will be bitwise-AND'd with the READ, WRITE, or EXECUTE
     *      final values defined above, depending on the method param's value.
     *
     * @param b The byte to be bitwise-AND'd with.
     * @param method The method that is being validated.
     * @return false if the bitwise-AND outputs a 0; true otherwise.
     */
    private static boolean valid_access(byte b, String method) {

        switch (method) {
            case "R": if ((b & READ) != 0)    return true; break;
            case "W": if ((b & WRITE) != 0)   return true; break;
            case "X": if ((b & EXECUTE) != 0) return true; break;
            default: System.exit(-1);
        }

        return false;
    }

    /**
     * Function to validate that the specified character is one that is
     *  between 0 and 7.
     *
     * @param c The character being validated.
     * @return true if c is 0-7; false otherwise.
     */
    private static boolean valid_num(char c) {
        return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
                || c == '5' || c == '6' || c == '7');
    }

}
