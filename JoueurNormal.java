import java.util.Random;

public class JoueurNormal extends Joueur{
    public JoueurNormal(String id, String nom, String prenom, int argent,Serveur serveur) {
        super(id,nom,prenom,argent,serveur);
    }

    public void run(){
        if(this.getPlayable()){
            Random r = new Random();
            int hasard=r.nextInt(2);
            System.out.println("\nLe joueur "+this.getId()+" commence une transaction");
            if(hasard==0 && this.getServeur().getprixBilletDeux()>this.getArgent()){
                hasard=1;
                this.getServeur().vendreBillet(hasard, this.getId());
                System.out.println("\nLe joueur "+this.getId()+" a effectué une transaction");
            }
            else if(hasard==1 && this.getServeur().getprixBilletUn()>this.getArgent()){
                System.out.println("\nLe joueur "+this.getId()+" n'a pas assez d'argent");
            }
            else{
                this.getServeur().vendreBillet(hasard, this.getId());
                System.out.println("\nLe joueur "+this.getId()+" a effectué une transaction");
            }
        }
        else{
            System.out.println("\nLe joueur "+this.getId()+" ne peut jouer");
        }
    }
}