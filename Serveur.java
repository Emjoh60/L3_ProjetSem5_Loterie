import java.util.ArrayList;

public class Serveur {
    private int n;
    private int k;
    private int t;
    private boolean actif;
    private int duree;
    private Notifieur notifieur;
    private ArrayList<Joueur> listeJoueur;
    private ArrayList<Billet> listeBillet;

    public Serveur(int n, int k, int t, int duree) {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.listeJoueur = new ArrayList<>();
        this.listeBillet = new ArrayList<>();
    }

    public Serveur(int n, int k, int t, int duree, ArrayList<Joueur> listeJoueur) throws NullPointerException {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.listeJoueur = new ArrayList<>();
        this.listeBillet = new ArrayList<>();
        if (listeJoueur == null) {
            throw new NullPointerException("liste de joueur est vide");
        } else {
            this.listeJoueur.addAll(listeJoueur);
            for (Joueur j : this.listeJoueur) {
                this.notifieur.addAutreEventListener(j);
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

    public boolean containJoueur(Joueur j) {
        boolean ret = false;
        for (Joueur a : this.listeJoueur) {
            if (a.compareTo(j)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public void addJoueur(Joueur j) throws Exception {
        if (this.containJoueur(j))
            throw new Exception("Valeur déjà présente");
        else {
            this.listeJoueur.add(j);
        }
    }

    public void removeJoueur(Joueur rj) throws Exception {
        if (!this.containJoueur(rj))
            throw new Exception("Valeur introuvable");
        else {
            this.listeJoueur.remove(rj);
        }
    }

    public boolean containBillet(Billet b) {
        boolean ret = false;
        for (Billet a : this.listeBillet) {
            if (a.compareTo(b)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public void addBillet(Billet b) throws Exception {
        if (this.containBillet(b))
            throw new Exception("Valeur déjà présente");
        else {
            this.listeBillet.add(b);
        }
    }

    public void removeBilet(Billet rb) throws Exception {
        if (!this.containBillet(rb))
            throw new Exception("Valeur introuvable");
        else {
            this.listeBillet.remove(rb);
        }
    }
}