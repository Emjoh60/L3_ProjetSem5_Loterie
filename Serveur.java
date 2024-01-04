import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Serveur{
    private int n;
    private int k;
    private int t;
    private int duree;
    private boolean actif;
    private Notifieur notifieur;
    private ListeSansDoublon <Joueur> listeJoueur;
    private ListeSansDoublon <Billet> listeBillet;

    public Serveur(int n, int k, int t, int duree) {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.listeJoueur = new ListeSansDoublon<Joueur>();
        this.listeBillet = new ListeSansDoublon<Billet>();
    }

    public Serveur(int n, int k, int t, int duree, ArrayList<Joueur> listeJoueur) throws NullPointerException {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.listeBillet = new ListeSansDoublon<Billet>();
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

    public boolean isJoueurRegistered(Joueur j) {
        return this.listeJoueur.contains(j);
    }

    public Joueur getJoueur(String id) {
        return this.listeJoueur.get(new Joueur(id,"","",0));
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

    public boolean containBillet(Billet b) {
        return this.listeBillet.contains(b);
    }

    public Billet getBillet(String id) {
        return this.listeBillet.get(new BilletI(id,"",0,new ArrayList<Integer>()));
    }

    public void addBillet(Billet b){
        try{
            this.listeBillet.add(b);
        }
        catch(IllegalArgumentException iae){
            System.err.println(iae.getMessage());
        }
    }

    public void removeBilet(Billet rb){
        try{
            this.listeBillet.remove(rb);
        }
        catch(NoSuchElementException nsee){
            System.err.println(nsee.getMessage());
        }
    }
}