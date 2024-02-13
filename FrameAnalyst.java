public class FrameAnalyst{
    
    /* Attributs */
    private Serveur serveur; // Serveur surveillé
    private int indexBillet,indexSave; // Index du dernier billet chargé, de la dernière sauvegarde chargée
    private ListeSansDoublon <Billet> listeBillet; // Liste de billet obtenue par la désérialisation

    /* Constructeurs */
    public FrameAnalyst(Serveur serveur){
        this.serveur=serveur;
        this.indexBillet=0;
        this.indexSave=0;
        this.listeBillet=new ListeSansDoublon<>();
    }

    // Initialisation de la liste
    public void setListe(){
        if(this.serveur.getGestionnaireBillet().getListeIndex().size()>0){
            this.listeBillet=this.serveur.getGestionnaireBillet().chargerListeBillet(this.serveur.getGestionnaireBillet().getListeIndex().getIndex(0));
        }
    }

    // Désérialisation d'une liste plus récente
    public boolean getNextList(){
        this.indexSave++;
        // Si il existe des listes plus récente
        if(this.indexSave<this.serveur.getGestionnaireBillet().getListeIndex().size()){ 
            this.listeBillet=this.serveur.getGestionnaireBillet().chargerListeBillet(this.serveur.getGestionnaireBillet().getListeIndex().getIndex(this.indexSave));
            this.indexBillet=0;
            return true;
        }
        else{
            this.indexSave--;
            return false;
        }
        
    }

    // Désérialisation d'une liste plus ancienne
    public boolean getPrevtList(){
        this.indexSave--;
        // Si il existe des listes plus ancienne
        if(this.indexSave>=0){
            this.listeBillet=this.serveur.getGestionnaireBillet().chargerListeBillet(this.serveur.getGestionnaireBillet().getListeIndex().getIndex(this.indexSave));
            this.indexBillet=0;
            return true;
        }
        else{
            this.indexSave++;
            return false;
        }
    }

    // Récupération des informations du billet de la liste courrante sous forme de chaine
    public String getNextBillet(){
        if(this.indexBillet<this.listeBillet.size()){
            Billet b=this.listeBillet.getIndex(this.indexBillet);
            Joueur j=this.serveur.getJoueur(b.getIdJoueur());
            String ret="Joueur : "+j.getNom()+" "+j.getPrenom()+" a achete le "+b.getKNombres();
            this.indexBillet++;
            return ret;
        }
        else{
            return "Champ vide";
        }
    }

    // Récupération du ticket gagnant sous forme de chaine
    public String getTicketGagnant(){
        ListeSansDoublon<Integer> liste=this.serveur.getTicketGagnant();
        String tamp="";
        int i;
        for(i=0;i<liste.size();i++){
            if(i<liste.size()-1){
                tamp=tamp+liste.getIndex(i)+" - ";
            }
            else{
                tamp=tamp+liste.getIndex(i);
            }
        }
        return tamp;
    }
}