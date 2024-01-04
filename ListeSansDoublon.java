import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

public class ListeSansDoublon <T> {
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

    public boolean contains(T element) {
        return this.liste.contains(element);
    }

    public T get(T element) {
       return(this.liste.get(this.liste.indexOf(element)));
    }

    public void add(T element) throws IllegalArgumentException {
        if (this.contains(element))
            throw new IllegalArgumentException("Element déjà présent");
        else {
            this.liste.add(element);
        }
    }

    public void remove(T element) throws NoSuchElementException {
        if (!this.contains(element))
            throw new NoSuchElementException("Element n'est pas présent");
        else {
            this.liste.remove(element);
        }
    }
}