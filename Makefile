JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	AutreEvent.java \
	AutreEventListener.java \
	Notifieur.java \
	Billet.java \
	BilletI.java \
	BilletII.java \
	ListeSansDoublon.java \
    SauvegardeBillet.java \
	Joueur.java \
	JoueurNormal.java \
	JoueurIntelligent.java \
	Generateur.java \
	GestionnaireBillet.java \
	Serveur.java \
	FrameAnalyst.java \
    Fenetre.java \
    Main.java 

default: classes

classes: $(CLASSES:.java=.class)

run:
	java Main

clean:
	$(RM) *.class