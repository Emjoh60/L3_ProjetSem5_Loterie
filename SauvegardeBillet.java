import java.io.Serializable;

public class SauvegardeBillet implements Serializable{
    private ListeSansDoublon <Billet> liste;

    public SauvegardeBillet(){
        this.liste=new ListeSansDoublon<Billet>();
    }

    public SauvegardeBillet(ListeSansDoublon <Billet> liste){
        this.liste=new ListeSansDoublon<Billet>();
        this.liste.addAll(liste);
    }

    public void enregistrerBillet(Billet b){
        this.liste.add(b);
    }

    public void desenregistrerBillet(Billet b){
        this.liste.remove(b);
    }

    public void enregistrerListeBillet(ListeSansDoublon<Billet> b){
        this.liste.addAll(b);
    }

    public void viderListeBillet(){
        this.liste.removeAll();
    }

    public int size(){
        return this.liste.size();
    }

    public ListeSansDoublon <Billet> getListe(){
        return this.liste;
    }
}