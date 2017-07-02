
SRC = $(addprefix src/, Main.java BackTest.java Util.java)
OBJ = ${SRC:.java=.class}

CC = javac
JAR = jar cvfm
MNF = manifest.mf
EXE = $(addprefix bin/, prog.jar)

all : ${EXE}

${EXE} : ${OBJ} ${MNF}
	${JAR} ${EXE} ${MNF} ${OBJ}
	-rm -rf ${OBJ} ${MNF}

${MNF} : ${SRC}
	echo "Manifest-version: 1.0" > ${MNF}
	echo "Main-Class: src.Main" >> ${MNF}
	echo "" >> ${MNF}

${OBJ} : ${SRC}
	${CC} ${SRC}

clean :
	-rm -rf ${OBJ} ${MNF} ${EXE}

test : ${EXE}
	java -jar ${EXE}

