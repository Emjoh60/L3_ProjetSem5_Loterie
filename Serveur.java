import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Serveur implements AutreEventListener{

    /* Attributs */

    private int n; // nombre de numéros max d'une loterie
    private int k; // nombre de numéros gagnants par billet
    private int t; // nombre de numero gagnant par billet
    private int duree; // durée de la loterie
    private int nombreVente; // nombre de billet vendu
    private int nombreGagnant; // nombre de gagnant
    private boolean actif; // désigne si le serveur est ouvert à la vente ou pas
    private Notifieur notifieur; // notifie les joueurs de l'ouverture
    private Notifieur notifieurGraphique; // notifie la fenètre d'un changement d'état
    private GestionnaireBillet gestionnaire; // gère la limite de billet en mémoire et par extension les sauvegardes
    private Generateur generateur; // génère des identifiants pour les billets
    private ListeSansDoublon <Integer> ticketGagnant; // contient les numéros du ticket gagnant
    private ListeSansDoublon <Joueur> listeJoueur; // contient tous les joueurs du serveur
    private ListeSansDoublon <Billet> listeBillet; // contient les billets présentdans la RAM du serveur
    private double prixBilletUn; // prix des billets de catégorie I
    private double prixBilletDeux; // prix des billets de catégorie II

    /* Constructeurs */

    public Serveur(int n, int k, int t, int duree, int limiteBillet, double prixBilletUn, double prixBilletDeux, Fenetre f) {
        // Contrôle des paramètres passés
        if(n<1){
            this.n = 25;
        }else{
            this.n = n;
        }
        if(k<1 || k >= this.n){
            this.k = 1;
        }else{
            this.k = k;
        }
        if(t<1 || t>this.k){
            this.t = 1;
        }else{
            this.t = t;
        }
        this.actif = false;
        this.duree = duree;
        this.nombreVente=0;
        this.notifieur = new Notifieur();
        this.notifieurGraphique = new Notifieur();
        this.notifieurGraphique.addAutreEventListener(f);
        this.generateur = new Generateur();
        this.ticketGagnant = new ListeSansDoublon <Integer>();
        this.listeJoueur = new ListeSansDoublon<Joueur>();
        this.listeBillet = new ListeSansDoublon<Billet>();
        this.gestionnaire = new GestionnaireBillet(limiteBillet,"saveBillet",this.listeBillet);
        this.prixBilletUn=prixBilletUn;
        this.prixBilletDeux=prixBilletDeux;
    }

    public Serveur(int n, int k, int t, int duree, int limiteBillet, double prixBilletUn, double prixBilletDeux, ArrayList<Joueur> listeJoueur, Fenetre f) throws NullPointerException {
        // Contrôle des paramètres passés
        if(n<1){
            this.n = 25;
        }else{
            this.n = n;
        }
        if(k<1 || k > this.n){
            this.k = 1;
        }else{
            this.k = k;
        }
        if(t<1 || t>this.k){
            this.t = 1;
        }else{
            this.t = t;
        }
        this.actif = false;
        this.duree = duree;
        this.nombreVente=0;
        this.notifieur = new Notifieur();
        this.notifieurGraphique = new Notifieur();
        this.notifieurGraphique.addAutreEventListener(f);
        this.generateur = new Generateur();
        this.ticketGagnant = new ListeSansDoublon <Integer>();
        this.listeBillet = new ListeSansDoublon<Billet>();
        this.prixBilletUn=prixBilletUn;
        this.prixBilletDeux=prixBilletDeux;
        this.gestionnaire = new GestionnaireBillet(limiteBillet,"saveBillet",this.listeBillet);
        // Si la liste de joueur est nulle, on lève une exception
        if (listeJoueur == null) {
            this.listeJoueur = new ListeSansDoublon<Joueur>();
            throw new NullPointerException("La liste de joueur est vide");
        } 
        else {
            // Si la liste de joueur contient des doublons, on attrape l'exception du new ListeSansDoublon
            try{
                this.listeJoueur = new ListeSansDoublon<Joueur>(listeJoueur);
                for (Joueur j : listeJoueur) {
                    this.notifieur.addAutreEventListener(j);
                }
            }
            catch(IllegalArgumentException iae){
                System.err.println(iae.getMessage());
            }  
        }
    }

    /* Methodes GET et SET */

    public void setN(int n) {
        if(n>=this.k && n>=this.t){
            this.n = n;
        }
    }

    public void setK(int k) {
        if(k>0 && k <= this.n && k >= this.t){
            this.k = k;
        }
    }

    public void setT(int t) {
        if(t>0 && t <= this.k){
            this.t = t;
        }
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setprixBilletUn(int prixBilletUn) {
        this.prixBilletUn = prixBilletUn;
    }

    public void setprixBilletDeux(int prixBilletDeux) {
        this.prixBilletDeux = prixBilletDeux;
    }

    public int getN() {
        return this.n;
    }

    public int getK() {
        return this.k;
    }

    public int getT() {
        return this.t;
    }

    public int getDuree() {
        return this.duree;
    }

    public int getNombreVente() {
        return this.nombreVente;
    }

    public boolean getState() {
        return this.actif;
    }

    public double getprixBilletUn() {
        return this.prixBilletUn;
    }

    public double getprixBilletDeux() {
        return this.prixBilletDeux;
    }

    public GestionnaireBillet getGestionnaireBillet() {
        return this.gestionnaire;
    }

    public ListeSansDoublon<Integer> getTicketGagnant() {
        return this.ticketGagnant;
    }

    public int getNombreGagnant(){
        return this.nombreGagnant;
    }

    /* Méthodes de gestion des listes */

    // Liste de joueurs
    public boolean isJoueurRegistered(Joueur j) {
        return this.listeJoueur.contains(j);
    }

    public Joueur getJoueur(String id) {
        return this.listeJoueur.get(new JoueurNormal(id,"","",0,this));
    }

    public void addJoueur(Joueur j){
        try{
            this.listeJoueur.add(j);
            this.notifieur.addAutreEventListener(j);
        }
        catch(IllegalArgumentException iae){
            System.err.println(iae.getMessage());
        }  
    }

    public void removeJoueur(Joueur rj){
        try{
            this.listeJoueur.remove(rj);
            this.notifieur.removeAutreEventListener(rj);
        }
        catch(NoSuchElementException nsee){
            System.err.println(nsee.getMessage());
        }
    }

    public ListeSansDoublon<Joueur> getListeJoueur(){
        return this.listeJoueur;
    }

    // Liste de billet

    public boolean containBillet(Billet b) {
        return this.listeBillet.contains(b);
    }

    public Billet getBillet(String id) {
        return this.listeBillet.get(new BilletI(id,"",0,new ArrayList<Integer>()));
    }

    public void addBillet(Billet b){
        try{
            this.listeBillet.add(b);
            this.gestionnaire.enregisterBillet(b);
        }
        catch(IllegalArgumentException iae){
            System.err.println(iae.getMessage());
        }
    }

    public void removeBilet(Billet rb){
        try{
            this.listeBillet.remove(rb);
            this.gestionnaire.desenregisterBillet(rb);
        }
        catch(NoSuchElementException nsee){
            System.err.println(nsee.getMessage());
        }
    }

    // Méthodes de gestion de la lotterie

    public void lancementLoterie(){
        // Au lancement de la loterie, le serveur indique au notifieur graphique d'indiquer l'état du serveur
        this.notifieurGraphique.diffuserAutreEvent(new AutreEvent(this, "load"));
        Thread[] tabThread= new Thread[this.listeJoueur.size()]; // Déclaration des threads
        int i;
        for(i=0;i<this.listeJoueur.size();i++){
            tabThread[i]=new Thread(this.listeJoueur.getIndex(i)); // Initialisation des threads
        }
        this.actif=true; // Activation du serveur
        this.notifieur.diffuserAutreEvent(new AutreEvent(this,true)); // Indication aux joueurs que le serveur est actif 
        long timer = System.currentTimeMillis(); // On récupère le temps d’exécution du programme au lancement du timer
        // Lancement des threads liés à chaque joueurs
        for(i=0;i<this.listeJoueur.size();i++){
                tabThread[i].start();
            }
        while (System.currentTimeMillis() - timer < this.duree) // Tant que le temps écoulé depuis l'initialisation du timmer est inférieur à la durée
        {}
        this.actif=false; // Une fois le temps écoulé, le serveur devient inactif
        this.notifieur.diffuserAutreEvent(new AutreEvent(this,false)); // Le notifieur du serveur l'indique aux joueurs
        // On attends que les threads encore en cours se terminent
        for(i=0;i<this.listeJoueur.size();i++){
            try{
                tabThread[i].join();
            }
            catch(InterruptedException ie){
                System.err.println(ie.getMessage());
            }
        }
        // Sauvegarde des derniers et affichage de toutes les sauvegardes
        this.gestionnaire.sauvegarderListeBillet();
        this.gestionnaire.getListeIndex().afficherListe();
        this.gestionnaire.viderListeGeree();
        this.listeBillet.removeAll();
        // Notifie à la fenêtre graphique que la loterie est terminée et qu'il faut charger l'interface des résultats
        this.notifieurGraphique.diffuserAutreEvent(new AutreEvent(this, "endStart"));
    }

    // Méthode permettant la vente de billet, cette méthode est appellée par les joueurs
        // On lui passe l'identifiant de l'acheteur, un entier x indiquant quel type de billet l'acheteur désire et une liste d'entier
    public boolean vendreBillet(int x,String idAcheteur,ArrayList<Integer> liste){
        // Tant que le serveur est actif
        if(this.actif){
            String name;
            BilletI b1=null;
            BilletII b2=null;
            // Chaque billet accède un par un au générateur d'id
            synchronized(this.generateur){
                name=this.generateur.generateString();
            }
            // Si x est à 1 on crée un billet de type 1
            if(x==1){
                b1=new BilletI(idAcheteur,name,this.prixBilletUn,this.n,this.k); 
            }
            // Si x est à 0 on crée un billet de type 2
            else if(x==0){
                if(liste==null||liste.size()!=this.k){
                    // On s'assure qu'un seul thread accède au terminal pour créer un billet de catégorie II
                    synchronized(this){
                        b2=new BilletII(idAcheteur,name,this.prixBilletDeux,this.n,this.k);
                    }
                }
                else{
                    b2=new BilletII(idAcheteur,name,this.prixBilletDeux,liste);    
                }
            }
            else if(x==2){
                b1=new BilletI(idAcheteur,name,this.prixBilletUn,this.n,this.k);
                synchronized(this){
                    int i=0;
                    while(!this.verifJoueur(b1)&&(i<Math.pow(this.n,this.k))){
                        b1=new BilletI(idAcheteur,name,this.prixBilletUn,this.n,this.k);
                        i++;
                    }
                }
            }
            // On s'assure qu'il n'y a qu'un seul thread accédant à la liste de billet et y ajoutant des éléments
            synchronized(this){
                try{
                    this.nombreVente++;
                    if(x==1||x==2){
                        this.addBillet(b1);
                    }
                    else if(x==0){
                        this.addBillet(b2);
                    }
                }
                catch(IllegalArgumentException iae){
                    System.err.println("Erreur de saisie");
                    this.nombreVente--;
                }
                // Vérification du nombre de billet dans la liste
                if(this.gestionnaire.gestionLimiteBillet()){
                    this.listeBillet.removeAll();
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    // Tirage des numéros gagnants
    public void tirageGagnant(){
        int i;;
        Random r=new Random();
        this.ticketGagnant.removeAll(); // Suppression des numéros déjà présent, s'il y en a
        // Tirage de k numéros
        for(i=0;i<this.k;i++){
            int x=r.nextInt((this.n)+1);
            // Vérification que le numéro n'est pas déjà présent
            try{
                this.ticketGagnant.add(Integer.valueOf(x));
            }
            catch(IllegalArgumentException iae){
                while(this.ticketGagnant.contains(Integer.valueOf(x))){
                     x=r.nextInt(this.n+1);       
                }
                this.ticketGagnant.add(Integer.valueOf(x));
            }
        }
    }

    // Comparaison par rapport au numéro gagnant

    public boolean comparaison(Billet billet){
        int i,cpt;
        cpt=0;
        // On compte le nombre d'élément pour le billet, se trouvant dans le ticket gagnant
        for(i=0;i<this.k;i++){
            if(billet.checkElementInListe((this.ticketGagnant.getIndex(i)))){
                cpt++;
            }
        }
        if(cpt>=this.t){
            return true;
        }
        else{
            return false;
        }
    }


    // Récupérer les billets gagnants
    public ListeSansDoublon<Billet> designerGagnant(){
        ListeSansDoublon<Billet> listeTampon=new ListeSansDoublon<>(); 
        ListeSansDoublon<Billet> listeGagnant=new ListeSansDoublon<>();
        int i,j;
        this.tirageGagnant(); // Tirage des numéros gagnant
        // Pour chaque sauvegarde
        for(i=0;i<this.gestionnaire.getListeIndex().size();i++){
            // On récupère la sauvegarde dans une liste tampon
            listeTampon=this.gestionnaire.chargerListeBillet(this.gestionnaire.getListeIndex().getIndex(i));
            // Pour chaque élément dans le billet
            for(j=0;j<listeTampon.size();j++){
                // Si la comparaison est vraie, on ajoute le billet à la liste des gagnants
                if(comparaison(listeTampon.getIndex(j))){
                    listeGagnant.add(listeTampon.getIndex(j));
                }
            }
            listeTampon.removeAll();
        }
        return listeGagnant;
    }

    // Vérifier qu'un tel billet n'existe pas pour un joueur
    public boolean verifJoueur(Billet b){
        ListeSansDoublon<Billet> listeTampon=new ListeSansDoublon<>(); 
        int i,j,h,kbis;
        // Pour chaque sauvegarde
        if(this.gestionnaire.getListeIndex().size()>0){
            for(i=0;i<this.gestionnaire.getListeIndex().size();i++){
                // On récupère la sauvegarde dans une liste tampon
                listeTampon=this.gestionnaire.chargerListeBillet(this.gestionnaire.getListeIndex().getIndex(i));
                // Pour chaque billet
                for(j=0;j<listeTampon.size();j++){
                    kbis=0;
                    if(b.equals(listeTampon.getIndex(j))){
                        // Si chaque élément est présent une fois
                        for(h=0;h<listeTampon.getIndex(j).getListe().size();h++){
                            // Si chaque élément est présent une fois
                            if(b.checkElementInListe(listeTampon.getIndex(j).getListe().get(h))){
                                kbis++;
                            }
                        }
                        if(kbis==this.k){
                            return false;
                        }
                    }
                }
                listeTampon.removeAll();
            }
        }
        for(j=0;j<this.listeBillet.size();j++){
            kbis=0;
            if(b.equals(this.listeBillet.getIndex(j))){
                // Si chaque élément est présent une fois
                for(h=0;h<this.listeBillet.getIndex(j).getListe().size();h++){
                    // Si chaque élément est présent une fois
                    if(b.checkElementInListe(this.listeBillet.getIndex(j).getListe().get(h))){
                        kbis++;
                    }
                }
                if(kbis==this.k){
                    return false;
                }
            }
        }
        return true;
    }


    // Fin de la loterie
    public void finLoterie(){
        ListeSansDoublon<Billet> billetGagnant=new ListeSansDoublon<>();
        ListeSansDoublon<Joueur> joueurGagnant=new ListeSansDoublon<>();
        // Récupération des billets gagnants
        billetGagnant=designerGagnant();
        int control=0;
        // Tant qu'il n'y a pas de gagnant, on réeffectue un tirage 10 fois au maximum
        while(billetGagnant.size()==0 && control<10){
            System.out.println("\nPas de gagnant ! Tirage relancé");
            billetGagnant=designerGagnant();
            control++;
        }
        int i;
        // Affichage du numéro gagnant
        this.ticketGagnant.afficherListe();
        // Pour chaque élément dans la liste de billet, on récupère le joueur qui est associé
        for(i=0;i<billetGagnant.size();i++){
            System.out.println("\nBillet gagnant : "+billetGagnant.getIndex(i).getKNombres());
            Joueur joueur=this.getJoueur(billetGagnant.getIndex(i).getIdJoueur());
            try{
                joueurGagnant.add(joueur);
            }
            catch(IllegalArgumentException iae){}
            
        }
        // On affiche sur le terminal chaque gagnant
        for(i=0;i<joueurGagnant.size();i++){
            System.out.println("\nGagnant "+i+" : "+joueurGagnant.getIndex(i).getNom()+" "+joueurGagnant.getIndex(i).getPrenom());
        }
        this.nombreGagnant=joueurGagnant.size();
        // On indique à la fenêtre que le serveur a fini ses actions
        this.notifieurGraphique.diffuserAutreEvent(new AutreEvent(this,"done"));
        
    }

    /* Action à déclencher selon l'évènement récupéré */
    public void actionADeclancher(AutreEvent obse){
        if(obse.getDonnee().equals("lancement")){
            this.testValidite(); // Normalement, on vérifie la conformité des paramètres selon une probabilité
            this.lancementLoterie();
        }
        else if(obse.getDonnee().equals("lancementUnsafe")){
            this.lancementLoterie();
        }
        else if(obse.getDonnee().equals("finLoterie")){
            this.finLoterie();
        }
    }

    /* FONCTION DE TEST DE LA PROBABILITE */

    public boolean testValidite(){
        int factN=factorielle(this.n);
        int factT=factorielle(this.t);
        int factNK=factorielle(this.n-this.k);
        double proba;
        // Si le nombre dépasse la limite, les factorielles sont à 0
        if(factT>0 && factNK>0){
            proba=(factN/(factT*factNK));
            System.out.println("\nProbabilité de victoire : 1 chance sur "+proba);
        }
        else{
            System.out.println("\nFaibles chances de victoire");
            this.n=10;
            this.k=5;
            this.t=3;
            return true;
        }
        // Tant que la probabilité est supérieur au nombre de joueurs multiplié par les 10 tentatives
        while(proba>(double)((this.listeJoueur.size())*10)){
            this.n--;
            this.k=(int)(this.n*0.4);
            this.t=(int)(this.k*0.8);
            factN=factorielle(this.n);
            factT=factorielle(this.t);
            factNK=factorielle(this.n-this.k);
            proba=(factN/(factT*factNK));
        }
        // Si la durée est trop importante, on la diminue
        if(this.duree>10000){
            this.duree=100;
        }
        System.out.println("\nProbabilité de victoire : 1 chance sur "+proba);
        return true;
        
    }

    // Fonction nécessaire pour le calcul des probabilités
    public int factorielle(int n){
        if(n!=0){
            return n*factorielle(n-1);
        }
        else{
            return 1;
        }
    }
}