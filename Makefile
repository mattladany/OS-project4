#########################################################################
###                        MAKEFILE FOR PROJECT 4                     ###
#########################################################################
 
### TARGETS

# The first target, to be called when 'make' is ran in the command line:
all: compile

# Target to compile the needed .java files and store the resulting .class files in the
#   working directory:
compile: src/Main.java src/Entry.java
	javac -d ./ src/Main.java src/Entry.java

# Target to be ran when wanting to create a tar-ball of the needed files to run the project:
tar:
	make clean ; mkdir project04 ; cp Makefile project04/ ; cp README.md project04/ ; cp run.sh project04/ ; cp -r src project04/ ; tar -czf project04.tar.gz project04/

# Target to be ran when wanting to remove all created files, other than necessities, and start fresh:
clean:
	\rm -rf project04.tar.gz *.class project04/
