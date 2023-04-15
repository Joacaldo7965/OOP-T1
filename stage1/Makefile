JFLAGS = -g
JC = javac
JVM= java
FILE=config.txt
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        State.java \
        SwitchState.java \
        Sensor.java \
        MagneticSensor.java \
        Door.java \
        Window.java \
        Stage1.java

MAIN = Stage1

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class 
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class