
SRC = Prog.java
OBJ = ${SRC:.java=.class}
MNF = manifest.mf

CC = javac
JAR = jar cvfm
EXE = prog.jar

all : ${EXE}

${EXE} : ${OBJ} ${MNF}
	${JAR} ${EXE} ${MNF} ${OBJ}
	-rm -rf ${OBJ} ${MNF}

${MNF} : ${SRC}
	echo "Manifest-version: 1.0" > ${MNF}
	echo "Main-Class: Prog" >> ${MNF}
	echo "" >> ${MNF}

${OBJ} : ${SRC}
	${CC} ${SRC}

clean :
	-rm -rf ${OBJ} ${MNF} ${EXE}

test :
	java -jar ${EXE}

