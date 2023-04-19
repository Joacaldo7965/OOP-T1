JFLAGS = -g
JC = javac
JVM= java
FILE=config.txt
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        AePlayWave.java \
        Siren.java \
        Central.java \
        State.java \
        SwitchState.java \
        Sensor.java \
        MagneticSensor.java \
        Frame.java \
        Door.java \
        Window.java \
        Stage2.java

MAIN = Stage2

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class 
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class