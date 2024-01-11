public class FrameAnalyst{
    private Serveur serveur;
    private int indexBillet,indexSave;
    private ListeSansDoublon <Billet> listeBillet;

    public FrameAnalyst(Serveur serveur){
        this.serveur=serveur;
        this.listeBillet=new ListeSansDoublon<>();
        this.indexBillet=0;
        this.indexSave=0;
    }

    public boolean getNextList(){
        if(this.indexSave<this.serveur.getGestionnaireBillet().getListeIndex().size()){
            this.indexSave++;
            this.listeBillet=this.serveur.getGestionnaireBillet().chargerListeBillet(this.serveur.getGestionnaireBillet().getListeIndex().getIndex(this.indexSave));
            this.indexBillet=0;
            return true;
        }
        else{
            return false;
        }
        
    }

    public boolean getPrevtList(){
        if(this.indexSave>0){
            this.indexSave--;
            this.listeBillet=this.serveur.getGestionnaireBillet().chargerListeBillet(this.serveur.getGestionnaireBillet().getListeIndex().getIndex(this.indexSave));
            this.indexBillet=0;
            return true;
        }
        else{
            return false;
        }
    }

    public String getNextBillet(){
        if(this.indexBillet<this.listeBillet.size()){
            this.indexBillet++;
            Billet b=this.listeBillet.getIndex(this.indexBillet);
            Joueur j=this.serveur.getJoueur(b.getIdJoueur());
            String ret="Joueur : "+j.getNom()+" "+j.getPrenom()+" a acheté"+"\nBillet :"+b.getId()+" - "+b.getKNombres();
            return ret;
        }
        return null;
    }

    public String getPrevBillet(){
        if(this.indexBillet>0){
            this.indexBillet--;
            Billet b=this.listeBillet.getIndex(this.indexBillet);
            Joueur j=this.serveur.getJoueur(b.getIdJoueur());
            String ret="Joueur : "+j.getNom()+" "+j.getPrenom()+" a acheté"+"\nBillet :"+b.getId()+" - "+b.getKNombres();
            return ret;
        }
        return null;
    }
}