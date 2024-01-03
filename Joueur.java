public class Joueur implements AutreEventListener {
    private String id;
    private String nom;
    private String prenom;
    private int argent;
    private boolean playable;

    public Joueur(String id, String nom, String prenom, int argent) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.argent = argent;
        this.playable = false;
    }

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

    @Override
    public boolean equals(Joueur j) {
        return (this.getId().equals(j.getId()));
    }

    public void actionADeclancher(AutreEvent event) {
        if (event.getSource() instanceof Serveur)
            this.playable = (boolean) event.getDonnee();
    }
    public static void main(String[] args) {
        Joueur j1=new Joueur("A", null, null, 0)        
    }
}