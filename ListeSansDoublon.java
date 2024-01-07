import java.util.ArrayList;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

public class ListeSansDoublon <T> implements Serializable{
    private ArrayList <T> liste;

    public ListeSansDoublon(){
        this.liste=new ArrayList<T>();
    }

    public ListeSansDoublon(ArrayList <T> liste) throws NullPointerException,IllegalArgumentException{
        this.liste=new ArrayList<T>();
        if (liste == null) {
            throw new NullPointerException("La liste est vide");
        }
        else{
            if(this.containDuplicate(liste)){
                throw new IllegalArgumentException("La liste contient des doublons");
            }
            else{
                this.liste.addAll(liste);
            }
        }
    }

    public boolean containDuplicate(ArrayList <T> liste){
        boolean ret=false;
        int count;
        for(T element : liste){
            count=0;
            for(T elementBis : liste){
                if(element.equals(elementBis)){
                    count++;
                }
            }
            if(count>1){
                ret=true;
            }
        }
        return ret;
    }

    protected ArrayList<T> getListe() {
       return this.liste;
    }

    public boolean contains(T element) {
        return this.liste.contains(element);
    }

    public T get(T element) {
       return(this.liste.get(this.liste.indexOf(element)));
    }

    public T getIndex(int x){
        return (this.liste.get(x));
    }

    public int size(){
        return this.liste.size();
    }

    public void add(T element) throws IllegalArgumentException {
        if (this.contains(element))
            throw new IllegalArgumentException("Element déjà présent");
        else {
            this.liste.add(element);
        }
    }

    public void addAll(ListeSansDoublon<T> l) {
        this.liste.addAll(l.getListe());
    }

    public void remove(T element) throws NoSuchElementException {
        if (!this.contains(element))
            throw new NoSuchElementException("Element n'est pas présent");
        else {
            this.liste.remove(element);
        }
    }

    public void removeAll(){
        this.liste.removeAll(this.liste);
    }

    public void afficherListe() {
        System.out.println("\nAffichage des éléments de la liste :");
        for(int i=0;i<this.liste.size();i++){
            System.out.println("- "+this.liste.get(i).toString());
        }
        System.out.println("\nFin de l'affichage des éléments de la liste :");
    }
}