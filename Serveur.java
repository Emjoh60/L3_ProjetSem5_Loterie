import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Serveur{

    //Attributs

    private int n;
    private int k;
    private int t;
    private int duree;
    public boolean actif;
    private Notifieur notifieur;
    private GestionnaireBillet gestionnaire;
    private Generateur generateur;
    private ListeSansDoublon <Integer> ticketGagnant;
    private ListeSansDoublon <Joueur> listeJoueur;
    private ListeSansDoublon <Billet> listeBillet;
    private double prixBilletUn;
    private double prixBilletDeux;

    // Constructeurs

    public Serveur(int n, int k, int t, int duree, int limiteBillet, double prixBilletUn, double prixBilletDeux) {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.generateur = new Generateur();
        this.ticketGagnant = new ListeSansDoublon <Integer>();
        this.listeJoueur = new ListeSansDoublon<Joueur>();
        this.listeBillet = new ListeSansDoublon<Billet>();
        this.gestionnaire = new GestionnaireBillet(limiteBillet,"saveBillet",this.listeBillet);
        this.prixBilletUn=prixBilletUn;
        this.prixBilletDeux=prixBilletDeux;
    }

    public Serveur(int n, int k, int t, int duree, int limiteBillet, double prixBilletUn, double prixBilletDeux, ArrayList<Joueur> listeJoueur) throws NullPointerException {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
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
        this.n = n;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setT(int t) {
        this.t = t;
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

    public boolean getState() {
        return this.actif;
    }

    public double getprixBilletUn() {
        return this.prixBilletUn;
    }

    public double getprixBilletDeux() {
        return this.prixBilletDeux;
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
                    if(x==1){
                        this.addBillet(b1);
                    }
                    else if(x==0){
                        this.addBillet(b2);
                    }
                }
                catch(IllegalArgumentException iae){
                    System.err.println("Erreur de saisie");
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

    public static void main(String args[]){
        Serveur s=new Serveur(10, 3, 2, 10 , 5, 2, 4);
        JoueurNormal j1=new JoueurNormal("1", "Jean", "Jules", 2, s);
        JoueurNormal j2=new JoueurNormal("2", "Jean", "Jules", 2, s);
        JoueurNormal j3=new JoueurNormal("3", "Jean", "Jules", 2, s);
        JoueurNormal j4=new JoueurNormal("4", "Jean", "Jules", 2, s);
        JoueurNormal j5=new JoueurNormal("5", "Jean", "Jules", 2, s);
        JoueurNormal j6=new JoueurNormal("6", "Jean", "Jules", 2, s);
        JoueurNormal j7=new JoueurNormal("7", "Jean", "Jules", 2, s);
        JoueurNormal j8=new JoueurNormal("8", "Jean", "Jules", 2, s);

        s.addJoueur(j1);
        s.addJoueur(j2);
        s.addJoueur(j3);
        s.addJoueur(j4);
        s.addJoueur(j5);
        s.addJoueur(j6);
        s.addJoueur(j7);
        s.addJoueur(j8);
        s.addJoueur(new JoueurIntelligent("9", "Jean", "Malin", 200, s));
        s.addJoueur(new JoueurNormal("10", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("11", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("12", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("13", "Jean", "Jules", 2, s));

        s.lancementLoterie();
        System.out.println("\n FIN LANCEMENT");

        s.finLoterie();
        

    }
}