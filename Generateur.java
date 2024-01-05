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
        for ( int i=0; i<lenght; i++) {
            int pos = (int)(alphabet.length()*Math.random());
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
            
        }
        else if(this.chiffre.equals(Integer.valueOf(999999999))){
            this.lettre = this.genererLettre(11);
            this.chiffre=Integer.valueOf(0);
        }
        chaineFinal+=chiffreString;
        return chaineFinal;
    }
}