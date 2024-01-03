import java.io.Serializable;
import java.util.ArrayList;

public class BilletI extends Billet implements Serializable{

    public BilletI(String idJoueur,String id,int prix,ArrayList <Integer> liste){
        super(idJoueur,id,prix,liste);
    }
}