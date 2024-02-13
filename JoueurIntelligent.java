public class JoueurIntelligent extends Joueur{

    /* Constructeur */
    public JoueurIntelligent(String id, String nom, String prenom, int argent,Serveur serveur) {
        super(id,nom,prenom,argent,serveur);
    }

    // Le joueur intelligent essaie tant qu'il le peut et qu'il a assez d'argent d'effectuer autant d'achat que possible
    public void run(){
        while(this.getPlayable() && this.getArgent()>this.getServeur().getprixBilletUn()){
            System.out.println("\nLe joueur intelligent "+this.getId()+" commence une transaction");
            this.setArgent((int)(this.getArgent()-this.getServeur().getprixBilletUn()));
            this.getServeur().vendreBillet(2, this.getId(),null);
            System.out.println("\nLe joueur intelligent "+this.getId()+" a effectué une transaction");
            // On impose aux joueurs un temps d'attente avant de rejouer
            try{
                Thread.sleep(200); // Le thread du joueur passe en inactif pour ne pas monopoliser l'accès au serveur
            }
            catch(InterruptedException ie){}
        }
        System.out.println("\nLe joueur intelligent "+this.getId()+" ne peut plus jouer");
    }
}