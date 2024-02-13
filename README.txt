Projet Programmation Orientée Objet : L3 - Loterie
Par Sara KHALED et Johann THOMAS

GUIDE D'UTILISATION

1/ Prérequis
    Le programme nécessite une version au moins égale à Java 17. Pour connaitre votre version veuillez exécuter la commande : java --version.
    Il est compatible sur Linux et Windows.

2/ Installation
    L'installation, l'exécution et le nettoyage sont gérés par un Makefile. Ainsi pour installer le programme, il est nécessaire de taper :
        -make

3/ Lancement
    Pour lancer le programme, il suffit de taper :
        - make run

4/ Nettoyage des class
    Pour supprimer les fichiers générés, il suffit de taper :
        - make clean
    ATTENTION ! En cas de suppression de ces fichiers le programme ne pourra plus s'exécuter, il faudra le réinstaller.

PRESENTATION DU PROJET

Le projet qui vous est présenté permet de gérer une loterie à travers une interface graphique et le terminal. 
Au lancement du programme l'utilisateur arrive sur une page l'invitant à entrer les paramètres du serveur.
S'il choisit de lancer le programme ne mode unchecked, le serveur se lancera quel que soit les paramètres sinon il testera les paramètres de manières à ce qu'il y ait un gagnant à la fin.
Au cours de l'exécution du programme, l'utilisateur pourra jouer à la loterie à travers le terminal, suivre l'évolution du jeu et consulter les informations sur le jeu aussi bien à travers une interface graphique que sur le terminal.