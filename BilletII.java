import java.util.ArrayList;
import java.util.Scanner;

public class BilletII extends Billet{
    
    public BilletII(String idJoueur,String id,double prix,int n, int k){
        super(idJoueur,id,prix,n,k);
    }

    public BilletII(String idJoueur,String id,double prix,ArrayList <Integer> liste){
        super(idJoueur,id,prix,liste);
    }

    public void affecterNombre(int n,int k){
        Scanner sc=new Scanner(System.in);
        int val;
        for(int i=0;i<k;i++){
            System.out.println("\nEntrez la valeur "+(i+1)+" sur laquelle vous souhaitez parier"); 
            if(sc.hasNextInt()){
                val =sc.nextInt();
                this.addElementToListe(Integer.valueOf(val));
            }
        }
        sc.close();
    }
}