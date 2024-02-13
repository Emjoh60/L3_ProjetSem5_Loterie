import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public abstract class Billet implements Serializable {
    /* Attributs */
    private String idJoueur; // Identifiant du joueur ayant acheté le billet
    private String id; // Identifiant du billet
    private double prix; // Prix du billet
    private ArrayList<Integer> liste; // Numéros composant le billet

    /* Constructeurs */
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
        // Si la liste des numéros est vide, on lève une exception
        if (liste == null || liste.isEmpty()) {
            throw new NullPointerException("Liste vide");
        } else {
            this.liste.addAll(liste); // Sinon on ajoute tous les numéros au billet
        }
    }

    /* Getter et Setter */

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

    public ArrayList<Integer> getListe() {
        return this.liste;
    }

    /* Fonctions relative à la liste */

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

    // Cette fonction renvoie les éléments de la liste sous format d'une chaine
    public String getKNombres(){
        String chaine="Billet "+this.getId(); 
        for (Integer val : liste) {
            chaine=chaine+" - "+val;
        }
        return chaine; 
    }

    /* Redéfinition des méthodes equals et toString pour les rendre comparables selon l'id */

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