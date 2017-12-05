if [ -f "Main.class" ]
then
	java -classpath bin/ Main $@
else
	echo "Please compile by running 'make' first"
fi
