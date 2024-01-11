public class JoueurIntelligent extends Joueur{
    public JoueurIntelligent(String id, String nom, String prenom, int argent,Serveur serveur) {
        super(id,nom,prenom,argent,serveur);
    }

    public void run(){
        while(this.getPlayable() && this.getArgent()>this.getServeur().getprixBilletUn()){
            System.out.println("\nLe joueur intelligent "+this.getId()+" commence une transaction");
            this.setArgent((int)(this.getArgent()-this.getServeur().getprixBilletUn()));
            this.getServeur().vendreBillet(1, this.getId(),null);
            System.out.println("\nLe joueur intelligent "+this.getId()+" a effectué une transaction");
            try{
                Thread.sleep(200);
            }
            catch(InterruptedException ie){}
        }
        System.out.println("\nLe joueur intelligent "+this.getId()+" ne peut plus jouer");
    }
}