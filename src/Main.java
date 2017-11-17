import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
}
