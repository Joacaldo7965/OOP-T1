JFLAGS = -g
JC = javac
JVM= java
FILE=config.txt
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ._AePlayWave.java \
        AePlayWave.java \
        Siren.java \
        Central.java \
        State.java \
        SwitchState.java \
        Sensor.java \
        PIR_Detector.java \
        MagneticSensor.java \
        Frame.java \
        Door.java \
        Window.java \
        Person.java \
        Stage4.java

MAIN = Stage4

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class 
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class