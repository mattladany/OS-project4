# OS-project4
Object Accessability

To compile and run the program, type the following commands:
1. $ make
2. $ ./run.sh <option(s)>

Flagged options:

    -h --help
        Display usage
     -d --debug
        Display debugging output while running (not needed for execution)
    -b --bonus
        Run the program in 'bonus' mode (must enter passwords for usernames)
    -f --file file_path
        Provide the file to be read in. This is mandatory
**About**
 
This program simulates the basic functionalities of accessing objects that have read, write, and execute permissions on them. There will be a menu that shows up, after running the program witht the correct flags, that will options as follows:

    1)  Su       - change to another user
    2)  Chown    - change the owner of an object
    3)  Chgrp    - change the group of an object
    4)  Chmod    - change the access rights on an object
    5)  Groupadd - add a user to a group
    6)  Groupdel - delete a user from a group
    7)  Access an object
    8)  Exit the program

You can do any of the things above, by typing in the number associated with them, and following the instructions afterwards.

