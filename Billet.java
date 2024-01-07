import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class Billet implements Serializable {
    private String idJoueur;
    private String id;
    private double prix;
    private ArrayList<Integer> liste;

    public Billet(String idJoueur, String id, double prix) {
        this.idJoueur = idJoueur;
        this.id = id;
        this.prix = prix;
        this.liste = new ArrayList<Integer>();
    }

    public Billet(String idJoueur, String id, double prix,int n, int k) {
        this.idJoueur = idJoueur;
        this.id = id;
        this.prix = prix;
        this.liste = new ArrayList<Integer>();
        this.affecterNombre(n, k);
    }

    public Billet(String idJoueur, String id, double prix, ArrayList<Integer> liste) throws NullPointerException {
        this.idJoueur = idJoueur;
        this.id = id;
        this.prix = prix;
        this.liste = new ArrayList<Integer>();
        if (liste == null || liste.isEmpty()) {
            throw new NullPointerException("Liste vide");
        } else {
            this.liste.addAll(liste);
        }
    }

    public void setIdJoueur(String id) {
        this.idJoueur = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getIdJoueur() {
        return this.idJoueur;
    }

    public String getId() {
        return this.id;
    }

    public double getPrix() {
        return this.prix;
    }

    public void addElementToListe(Integer element) {
        this.liste.add(element);
    }

    public boolean checkElementInListe(Integer element) {
        return this.liste.contains(element);
    }

    public void removeElementToListe(Integer element) throws NoSuchElementException {
        if (!this.liste.contains(element)) {
            throw new NoSuchElementException();
        } else {
            this.liste.remove(element);
        }
    }

    public String getKNombres(){
        String chaine="Billet "+this.getId();
        for (Integer val : liste) {
            chaine=chaine+" - "+val;
        }
        return chaine;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Billet){
            Billet b=(Billet)o;
            return (this.getId().equals(b.getId()));
        }
        else{
            return false;
        }
    }

    public abstract void affecterNombre(int n,int k);

    @Override 
    public String toString(){
        return this.id;
    }
}