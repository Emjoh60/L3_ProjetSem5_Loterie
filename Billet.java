import java.util.ArrayList;

public abstract class Billet{
    private String id;
    private int prix;
    private ArrayList <Integer> liste;

    public Billet(String id,int prix,ArrayList <Integer> liste) throws NullPointerException{
        this.id=id;
        this.prix=prix;
        this.liste=new ArrayList<Integer>();
        if(liste==null||liste.isEmpty()){
            throw new NullPointerException("Liste vide");
        }
        else{
            this.liste.addAll(liste);
        }
    }

    public void setId(String id){
        this.id=id;
    }
    public void setId(int prix){
        this.prix=prix;
    }

    public String getId() {
        return this.id;
    }

    public int getPrix() {
        return this.prix;
    }

    public void addElementToListe(Integer element){
        this.liste.add(element);
    }

    public boolean checkElementInListe(Integer element){
        return this.liste.contains(element);
    }

    public void removeElementToListe(Integer element) throws NoSuchElementException{
        if(!this.liste.contains(element)){
            throw new NoSuchElementException();
        }
        else{
            this.liste.remove(element);
        }
    }
}