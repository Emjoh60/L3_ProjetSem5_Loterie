public abstract class Joueur implements AutreEventListener, Runnable {

    /* Attributs */
    private String id; // Id du joueur
    private String nom; // Nom du joueur
    private String prenom; // Prénom du joueur
    private int argent; // Argent du joueur
    private boolean playable; // Etat du joueur
    private Serveur serveur; // Serveur associé au joueur

    /* Constructeurs */
    public Joueur(String id, String nom, String prenom, int argent,Serveur serveur){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.argent = argent;
        this.playable = false;
        this.serveur=serveur;
    }

    /* Getter et setter */
    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public void setPlayable(boolean b) {
        this.playable = b;
    }

    public void setServeur(Serveur s) {
        this.serveur = s;
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

    public boolean getPlayable() {
        return this.playable;
    }

    public Serveur getServeur() {
        return this.serveur;
    }

    /* Redéfinition des méthodes equals et toString pour les rendre comparables selon l'id */

    @Override
    public boolean equals(Object o) {
        if(o instanceof Joueur){
            Joueur j=(Joueur)o;
            return (this.getId().equals(j.getId()));
        }
        else{
            return false;
        }
    }

    @Override 
    public String toString(){
        return this.id;
    }

    // La classe joueur étant un listener, elle doit pouvoir déclencher une méthode en cas de réception d'un signal
    public void actionADeclancher(AutreEvent event) {
        if (event.getSource() instanceof Serveur)
            this.playable = (boolean) event.getDonnee();
    }


}