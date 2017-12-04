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
        Map<String, Integer> _objects = new HashMap<>();
        Set<String> _users = new HashSet<>();
        Set<String> _groups = new HashSet<>();

        int index = 0;
        for (Entry entry : entries) {
            _objects.put(entry.name, index);
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

        // Initializing matrix
        Structures.matrix = new HashMap<>();
        for (String user : _users) {
            Structures.matrix.put(user, new ArrayList<String>(_objects.size()));
        }

        log("Access Matrix has been initialized.");

        Scanner kb = new Scanner(System.in);

        // Starting the menu loop
        loop:
        while(true) {

            print_menu();

            switch(kb.nextLine()) {

                // su
                case "1":

                    break;

                // chown
                case "2":

                    break;

                // chgrp
                case "3":

                    break;

                // chmod
                case "4":

                    break;

                // group add
                case "5":

                    break;

                // group delete
                case "6":

                    break;

                // access an object
                case "7":

                    break;

                // exit the program
                case "8":
                    System.out.println("Exiting...");
                    break loop;

                // if something other than 1-8 was entered...
                default:
                    System.out.println("Please enter a number between 1 and 8.");
            }

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
