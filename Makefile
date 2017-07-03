
SDIR = src/
BDIR = bin/

SRC = $(addprefix ${SDIR}, Main.java BackTest.java Monitor.java Indicator.java Data.java)
#OBJ = ${SRC:.java=.class}
OBJ = $(addprefix ${SDIR}, *.class)

CC = javac
JAR = jar cvfm
MNF = manifest.mf
EXE = $(addprefix ${BDIR}, prog.jar)

all : ${EXE}

${EXE} : ${OBJ} ${MNF}
	${JAR} ${EXE} ${MNF} ${OBJ}

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

