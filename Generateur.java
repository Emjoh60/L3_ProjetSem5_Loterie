import java.util.Random;

public class Generateur {

    /* Attributs */
    private String lettre; // Partie lettre de la chaine générée
    private Integer chiffre; // Partie chiffrée de la chaine générée

    /* Constructeur */
    public Generateur (){
        this.lettre = this.genererLettre(11);
        this.chiffre = 0;
    }

    /* Getter et setter */
    public void setlettre(String lettre) {
        this.lettre = lettre;
    }

    public String getlettre() {
        return this.lettre;
    }

    public void setchiffre(int chiffre) {
        this.chiffre = Integer.valueOf(chiffre);
    }

    public Integer getchiffre() {
        return this.chiffre;
    }

    // Méthode générant une chaine aléatoire de lenght taille selon un alphabet
    private String genererLettre(int lenght){
        String alphabet="abcdefghijklmnopqrstuvwxyz";
        String retour="";
        Random r=new Random();
        for ( int i=0; i<lenght; i++) {
            int pos = (int)(r.nextInt(alphabet.length()));
            retour=retour+alphabet.charAt(pos); 
        }
        return retour;
    }

    // Méthode générant une chaine aléatoire associant un code de lettre et une suite de 9 chiffre
    public String generateString(){
        String chaineFinal="";
        chaineFinal=chaineFinal+this.lettre;
        String chiffreString=this.chiffre.toString();
        // Si le chiffre contient moins de 9 caractère, on le complète avec des 0
        if(chiffreString.length()<9){
            String tamp="";
            for(int i =0;i<(9-chiffreString.length());i++){
                tamp=tamp+"0";
            }
            tamp=tamp+chiffreString;
            chiffreString=tamp;
            this.chiffre=Integer.valueOf(this.chiffre+1);
        }
        // Si le chiffre atteint 999999999, on génère une nouvelle chaine
        else if(this.chiffre.equals(Integer.valueOf(999999999))){
            this.lettre = this.genererLettre(11);
            this.chiffre=Integer.valueOf(0);
        }
        chaineFinal+=chiffreString;
        return chaineFinal;
    }
}