if [ -f "Main.class" ]
then
	java Main $@
else
	echo "Please compile by running 'make' first"
fi
