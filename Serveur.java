import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Serveur implements AutreEventListener{

    //Attributs

    private int n;
    private int k;
    private int t;
    private int duree;
    private int nombreVente;
    private boolean actif;
    private Notifieur notifieur;
    private Notifieur notifieurGraphique;
    private GestionnaireBillet gestionnaire;
    private Generateur generateur;
    private ListeSansDoublon <Integer> ticketGagnant;
    private ListeSansDoublon <Joueur> listeJoueur;
    private ListeSansDoublon <Billet> listeBillet;
    private double prixBilletUn;
    private double prixBilletDeux;

    // Constructeurs

    public Serveur(int n, int k, int t, int duree, int limiteBillet, double prixBilletUn, double prixBilletDeux, Fenetre f) {
        if(n==0){
            this.n = 25;
        }else{
            this.n = n;
        }
        if(k<0 || k >= n){
            this.k = 1;
        }else{
            this.k = n;
        }
        if(t<1 || t>k){
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
        if(n<1){
            this.n = 25;
        }else{
            this.n = n;
        }
        if(k<1 || k > this.n){
            this.k = 1;
        }else{
            this.k = n;
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
        if (listeJoueur == null) {
            this.listeJoueur = new ListeSansDoublon<Joueur>();
            throw new NullPointerException("La liste de joueur est vide");
        } 
        else {
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

    // Methodes GET et SET

    public void setN(int n) {
        if(n>=1){
            this.n = n;
        }
    }

    public void setK(int k) {
        if(k>0 && k <= this.n){
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

    // Méthodes de gestion des listes

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
        this.notifieurGraphique.diffuserAutreEvent(new AutreEvent(this, "start"));
        Thread[] tabThread= new Thread[this.listeJoueur.size()];
        int i;
        for(i=0;i<this.listeJoueur.size();i++){
            tabThread[i]=new Thread(this.listeJoueur.getIndex(i));
        }
        this.actif=true;
        this.notifieur.diffuserAutreEvent(new AutreEvent(this,true));
        long timer = System.currentTimeMillis(); //on récupère le temps d’exécution du programme au lancement du timer
        for(i=0;i<this.listeJoueur.size();i++){
                tabThread[i].start();
            }
        while (System.currentTimeMillis() - timer < this.duree) //tant que le temps écoulé depuis qu'on a initialisé le timer est inférieur au delay
        {}
        this.actif=false;
        this.notifieur.diffuserAutreEvent(new AutreEvent(this,false));
        for(i=0;i<this.listeJoueur.size();i++){
            try{
                tabThread[i].join();
            }
            catch(InterruptedException ie){
                System.err.println(ie.getMessage());
            }
        }
        this.gestionnaire.sauvegarderListeBillet();
        this.gestionnaire.getListeIndex().afficherListe();
        this.gestionnaire.viderListeGeree();
        this.listeBillet.removeAll();   
    }

    public boolean vendreBillet(int x,String idAcheteur,ArrayList<Integer> liste){
        if(this.actif){
            String name;
            BilletI b1=null;
            BilletII b2=null;
            synchronized(this.generateur){
                name=this.generateur.generateString();
            }
            if(x==1){
                b1=new BilletI(idAcheteur,name,this.prixBilletUn,this.n,this.k); 
            }
            else if(x==0){
                if(liste==null||liste.size()!=this.k){
                    b2=new BilletII(idAcheteur,name,this.prixBilletDeux,this.n,this.k);
                }
                else{
                    b2=new BilletII(idAcheteur,name,this.prixBilletDeux,liste);    
                }
            }
            synchronized(this){
                try{
                    this.nombreVente++;
                    if(x==1){
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
        this.ticketGagnant.removeAll();
        for(i=0;i<this.k;i++){
            int x=r.nextInt((this.n)+1);
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
        int i,j;;
        this.tirageGagnant();
        for(i=0;i<this.gestionnaire.getListeIndex().size();i++){
            listeTampon=this.gestionnaire.chargerListeBillet(this.gestionnaire.getListeIndex().getIndex(i));
            for(j=0;j<listeTampon.size();j++){
                if(comparaison(listeTampon.getIndex(j))){
                    listeGagnant.add(listeTampon.getIndex(j));
                }
            }
            listeTampon.removeAll();
        }
        return listeGagnant;
    }

    // Récupérer les billets gagnants
    public void finLoterie(){
        ListeSansDoublon<Billet> billetGagnant=new ListeSansDoublon<>();
        ListeSansDoublon<Joueur> joueurGagnant=new ListeSansDoublon<>();
        billetGagnant=designerGagnant();
        int aaa=0;
        while(billetGagnant.size()==0 && aaa<10){
            System.out.println("\nERREUR");
            billetGagnant=designerGagnant();
            aaa++;
        }
        int i;
        this.ticketGagnant.afficherListe();
        for(i=0;i<billetGagnant.size();i++){
            System.out.println("\nBillet gagnant : "+billetGagnant.getIndex(i).getKNombres());
            Joueur joueur=this.getJoueur(billetGagnant.getIndex(i).getIdJoueur());
            try{
                joueurGagnant.add(joueur);
            }
            catch(IllegalArgumentException iae){}
            
        }
        for(i=0;i<joueurGagnant.size();i++){
            System.out.println("\nGagnant "+i+" : "+joueurGagnant.getIndex(i).getNom()+" "+joueurGagnant.getIndex(i).getPrenom());
        }
        
    }

    public void actionADeclancher(AutreEvent obse){
        if(obse.getDonnee().equals("lancement")){
            this.lancementLoterie();
        }
        else if(obse.getDonnee().equals("finLoterie")){
            this.finLoterie();
        }
    }
}