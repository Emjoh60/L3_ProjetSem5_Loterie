import java.io.Serializable;
import java.util.ArrayList;

public class BilletII extends Billet implements Serializable{
    
    public BilletII(String idJoueur,String id,int prix,ArrayList <Integer> liste){
        super(idJoueur,id,prix,liste);
    }
}