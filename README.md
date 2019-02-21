# OS-project4
Object Accessability

To compile and run the program, type the following commands:
```
$ make
$ ./run.sh <option(s)>
```
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

**Bonus Details**

There are two files in the passwords/ directory. One (passwords.txt) holds the users and their passwords; the other (hashes.txt) holds the users and their hashed passwords. The passwords.txt file can be used to know what password to enter for what user, and the hashes.txt file is used by the program to get the actual hash of the specified user's password, and compare it to the hash calculated using the password entered by the user of the program.
