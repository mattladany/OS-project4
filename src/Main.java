import java.io.*;
import java.util.*;

/**
 *
 */
public class Main {

    private static boolean debug = false;


    public static void main(String[] args) {

        // Parsing command line arguments.
        String filename = parse_commandline_args(args);

        System.out.println("########################## OS Project 4 " +
                           "##########################");

        log("Running in debug mode...");

        BufferedReader reader;
        List<Entry> entries = new ArrayList<>();

        // Reading the file from the command line args.
        try {
            reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split_line = line.split(" ");
                entries.add(new Entry(split_line[0], split_line[1], split_line[2]));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Invalid file path.");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        /**** Initializing the needed data structures. ****/
        // Getting the objects, groups, and users from the entries
        Structures.objects = new LinkedHashMap<>();
        Set<String> _users = new LinkedHashSet<>();
        Set<String> _groups = new LinkedHashSet<>();

        int index = 0;
        for (Entry entry : entries) {
            Structures.objects.put(entry.name, index);
            _users.add(entry.owner);
            _groups.add(entry.group);
            index++;
        }

        // Initializing groups
        Structures.groups = new HashMap<>();

        for (String group : _groups) {
            Structures.groups.put(group, new ArrayList<String>());
        }
        Structures.groups.put("root", new ArrayList());

        log("Groups structure has been initialized.");

        // List holding 000 string elements, to initially be put in the matrix
        List<String> perms = new ArrayList<>(Structures.objects.size());
        for(int i = 0; i < Structures.objects.size(); i++) {
            perms.add(i, "000");
        }

        // Initializing matrix

        Structures.matrix = new LinkedHashMap<>();
        for (String user : _users) {
            Structures.matrix.put(user, perms);
        }

        log("Access Matrix has been initialized.");

        Scanner kb = new Scanner(System.in);

        String username;
        String group;
        String object;
        String permission;

        // Starting the menu loop
        loop:
        while(true) {

            print_menu();

            switch(kb.nextLine()) {

                // su
                case "1":
                    System.out.println("Enter the username of the user you would like to swap to:");
                    username = kb.nextLine();
                    if (Util.su(username) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                        System.out.println("Success. User is now: " + username);
                    }
                    break;

                // chown
                case "2":
                    System.out.println("Enter the username to assign to the object:");
                    username = kb.nextLine();
                    System.out.println("Enter the object to be modified:");
                    object = kb.nextLine();
                    if (Util.chown(username, object) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                        System.out.println("Success. Object " + object + " now has " + username
                                + " as its owner.");
                    }
                    break;

                // chgrp
                case "3":
                    System.out.println("Enter the group to assign to the object:");
                    group = kb.nextLine();
                    System.out.println("Enter the object to be modified:");
                    object = kb.nextLine();
                    if (Util.chgrp(group, object) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                        System.out.println("Success. Object " + object + " now has " + group
                                + " as its group");
                    }
                    break;

                // chmod
                case "4":
                    System.out.println("Enter the object to be modified:");
                    object = kb.nextLine();
                    System.out.println("Enter the access rights to assign to the object:");
                    permission = kb.nextLine();
                    if (Util.chmod(object, permission) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                        System.out.println("Success. Object " + object + " now has " + permission
                                + " as its access rights.");
                    }
                    break;

                // group add
                case "5":
                    System.out.println("Enter the username to be added to a group:");
                    username = kb.nextLine();
                    System.out.println("Enter the group to add the user to:");
                    group = kb.nextLine();
                    if (Util.groupadd(username, group) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                    System.out.println("Success. User " + username
                            + " has been added to group " + group + ".");
                    }

                    break;

                // group delete
                case "6":
                    System.out.println("Enter the username to be removed from a group:");
                    username = kb.nextLine();
                    System.out.println("Enter the group to remove the user from:");
                    group = kb.nextLine();
                    if (Util.groupdel(username, group) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                        System.out.println("Success. User " + username
                                + " has been removed from group " + group + ".");
                    }
                    break;

                // access an object
                case "7":
                    System.out.println("Enter the object wanting to be accessed:");
                    object = kb.nextLine();
                    System.out.println("Enter the manor in which the object is to " +
                            "be accessed (R, W, or X):");
                    String action = kb.nextLine();
                    if (!action.equals("R") || !action.equals("W") || !action.equals("X"))
                    if (Util.access(object, action) == 0) {
                        System.out.println("Operation failed.");
                    } else {
                        System.out.println("Success. User " + Util.current_user
                                + " has the permission to " + action
                                + " object " + object + ".");
                    }
                    break;

                // exit the program
                case "8":
                    System.out.println("Exiting...");
                    break loop;

                // if something other than 1-8 was entered...
                default:
                    System.out.println("Please enter a number between 1 and 8.");
            }

            log("Matrix looks like:");
            log(Structures.printMatrix());
        }

    }


    /**
     * Function to be used by the main method to verify the command line arguments.
     *  Will exit if there are no args, if no file is passed, or if there are invalid args.
     *
     * @param args The arguments passed through the command line.
     * @return The file path
     */
    private static String parse_commandline_args(String[] args) {

        // Checking to make sure something was passed as an argument.
        if (args.length == 0) {
            System.out.println("Please provide a file as a command line argument.");
            print_help_message();
            System.exit(-1);
        }

        String file_path = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {

                case "-h":
                case "--help":
                    print_help_message();
                    System.exit(0);

                case "-d":
                case "--debug":
                    Main.debug = true;
                    break;

                case "-f":
                case "--file":
                    file_path = args[++i];
                    break;

                default:
                    System.out.println(args[i] + " is an invalid argument.");
                    print_help_message();
                    System.exit(-1);
            }
        }

        return file_path;
    }

    /**
     * Function to determine whether to print the debugging output passed in, or not.
     *  Will print the message if the -d flag is passed, and nothing, otherwise.
     *
     * @param msg The message intended to be printed.
     */
    public static void log(String msg) {
        if (Main.debug)
            System.out.println(msg);
    }

    /**
     * Function will print out the help message to display info about possible command line args.
     */
    private static void print_help_message() {
        System.out.println("Usage: ./run.sh <option(s)>");
        System.out.println(" Options are:");
        System.out.println("  -h --help   Display usage\n");
        System.out.println("  -d --debug  Display debugging output while running\n");
        System.out.println("  -f --file " + (char)27 + "[4mfile-path" + (char)27 + "[0m");
        System.out.println("              Provide the file to be read in. This is mandatory\n");
    }

    /**
     * Function will print out the menu options each loop iteration in the main() function.
     */
    private static void print_menu() {
        System.out.println("What would you like to do:");
        System.out.println(" 1)  Su       - change to another user");
        System.out.println(" 2)  Chown    - change the owner of an object");
        System.out.println(" 3)  Chgrp    - change the group of an object");
        System.out.println(" 4)  Chmod    - change the access rights on an object");
        System.out.println(" 5)  Groupadd - add a user to a group");
        System.out.println(" 6)  Groupdel - delete a user from a group");
        System.out.println(" 7)  Access an object");
        System.out.println(" 8)  Exit the program");
    }
}
