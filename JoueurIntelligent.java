import java.util.ArrayList;

public class JoueurIntelligent extends Joueur{
    public JoueurIntelligent(String id, String nom, String prenom, int argent,Serveur serveur) {
        super(id,nom,prenom,argent,serveur);
    }

    public void run(){
        System.out.println("\nLancement");
        int j,n,k,count;
        n=this.getServeur().getN();
        k=this.getServeur().getK();
        j=0;
        count=0;
        ArrayList<Integer> list=new ArrayList<>();
        System.out.println("\nLancement");
        while(this.getPlayable() && this.getArgent()>this.getServeur().getprixBilletDeux() && count<n){
            for(int i=j;i<k;i++){
                list.add(Integer.valueOf(i));
            }
            count++;
            if(j>n){
                j=(int)((n/k)*count);
            }
            else{
                j=k;
                k=k+k;
            }
            System.out.println("\nLe joueur intelligent "+this.getId()+" commence une transaction");
            this.setArgent((int)(this.getArgent()-this.getServeur().getprixBilletDeux()));
            this.getServeur().vendreBillet(2, this.getId(),list);
            System.out.println("\nLe joueur intelligent "+this.getId()+" a effectué une transaction");
            list.removeAll(list);
            try{
                Thread.sleep(200);
            }
            catch(InterruptedException ie){

            }
        }
        System.out.println("\nLe joueur intelligent "+this.getId()+" ne peut plus jouer");
    }
}