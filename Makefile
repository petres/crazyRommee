# Compile java code and build a jar file
all:
	mkdir -p bin
	javac -d bin src/crazyRommee/*.java

#   Note: cd and command must be on one line (make spawns a new shell for each line)
	cd bin; jar cf crazyRommee.jar crazyRommee/*.class

clean:
	rm -Rf bin
