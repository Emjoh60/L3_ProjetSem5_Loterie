public class JoueurIntelligent extends Joueur{
    public JoueurIntelligent(String id, String nom, String prenom, int argent,Serveur serveur) {
        super(id,nom,prenom,argent,serveur);
    }

    public void run(){
        while(this.getPlayable()){
            
        }
    }
}