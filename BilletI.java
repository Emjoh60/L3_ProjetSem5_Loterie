import java.util.ArrayList;
import java.util.Random;

public class BilletI extends Billet{

    public BilletI(String idJoueur,String id,double prix,int n,int k){
        super(idJoueur,id,prix,n,k);
    }

    public BilletI(String idJoueur,String id,double prix,ArrayList <Integer> liste){
        super(idJoueur,id,prix,liste);
    }

    public void affecterNombre(int n,int k){
        for(int i=0;i<k;i++){
            Random r=new Random();
            int val=r.nextInt(n+1);
            this.addElementToListe(Integer.valueOf(val));
        }
    }
}