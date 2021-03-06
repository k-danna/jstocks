
SDIR = src/
BDIR = bin/

SRC = $(addprefix ${SDIR}, Main.java LoadData.java Analyze.java \
        Calculate.java Trade.java User.java Visualize.java Database.java)
#OBJ = ${SRC:.java=.class}
OBJ = $(addprefix ${SDIR}, *.class)

CC = javac
JAR = jar cvfm
RLIB = -classpath ".:lib/sqlite-jdbc-3.19.3.jar"
LIB = -classpath ".:lib/jfreechart-1.0.19.jar:lib/jcommon-1.0.23.jar"
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
	${CC} ${LIB} ${SRC}

clean :
	-rm -rf ${OBJ} ${MNF} ${EXE}

test : ${EXE}
	java ${RLIB} src.Main

