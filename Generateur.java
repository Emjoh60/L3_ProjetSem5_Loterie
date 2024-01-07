import java.util.Random;

public class Generateur {
    private String lettre;
    private Integer chiffre;

    public Generateur (){
        this.lettre = this.genererLettre(11);
        this.chiffre = 0;
    }

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

    public String generateString(){
        String chaineFinal="";
        chaineFinal=chaineFinal+this.lettre;
        String chiffreString=this.chiffre.toString();
        if(chiffreString.length()<9){
            String tamp="";
            for(int i =0;i<(9-chiffreString.length());i++){
                tamp=tamp+"0";
            }
            tamp=tamp+chiffreString;
            chiffreString=tamp;
            this.chiffre=Integer.valueOf(this.chiffre+1);
        }
        else if(this.chiffre.equals(Integer.valueOf(999999999))){
            this.lettre = this.genererLettre(11);
            this.chiffre=Integer.valueOf(0);
        }
        chaineFinal+=chiffreString;
        return chaineFinal;
    }

    public static void main(String args[]){
        Generateur gen=new Generateur();
        for(int i=0;i<10000;i++){
            System.out.println("\n"+gen.generateString());
        }
    }
}