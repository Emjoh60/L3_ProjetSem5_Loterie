public abstract class Joueur{
    private String id;
    private String nom;
    private String prenom;
    private int argent;

    public Joueur(String id,String nom,String prenom,int argent){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.argent=argent;
    }

    public void setId(String id){
        this.id=id;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public void setArgent(int argent){
        this.argent=argent;
    }

    public String getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public int getArgent() {
        return this.argent;
    }

}