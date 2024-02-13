import java.util.Random;

public class JoueurNormal extends Joueur{

    /* Constructeur */
    public JoueurNormal(String id, String nom, String prenom, int argent,Serveur serveur) {
        super(id,nom,prenom,argent,serveur);
    }

    // Le joueur normal n'essaiera qu'une fois d'acheter un billet de catégorie I ou II selon son argent et sa volonté
    public void run(){
        if(this.getPlayable()){
            Random r = new Random();
            int hasard=r.nextInt(2); // Tirage au sort pour un billet de catégorie I ou II
            System.out.println("\nLe joueur "+this.getId()+" commence une transaction");
            if(hasard==0 && this.getServeur().getprixBilletDeux()>this.getArgent()){
                hasard=1;
                this.getServeur().vendreBillet(hasard, this.getId(),null);
                System.out.println("\nLe joueur "+this.getId()+" a effectué une transaction");
                this.setArgent((int)(this.getArgent()-this.getServeur().getprixBilletUn()));
            }
            else if(hasard==1 && this.getServeur().getprixBilletUn()>this.getArgent()){
                System.out.println("\nLe joueur "+this.getId()+" n'a pas assez d'argent");
            }
            else{
                this.getServeur().vendreBillet(hasard, this.getId(),null);
                System.out.println("\nLe joueur "+this.getId()+" a effectué une transaction");
                this.setArgent((int)(this.getArgent()-this.getServeur().getprixBilletDeux()));
            }
        }
        else{
            System.out.println("\nLe joueur "+this.getId()+" ne peut jouer");
        }
    }
}