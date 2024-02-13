public class Main{
    public static void main(String args[]){
        Fenetre f=new Fenetre();
        Serveur s=new Serveur(10, 3, 2, 100 , 5, 2, 4,f);
        try{
            f.setServeur(s);
            f.demarrer();
        }
        catch(NullPointerException npe){

        }

        
        s.addJoueur(new JoueurNormal("1", "Lorier", "Sebastien", 2, s));
        s.addJoueur(new JoueurNormal("2", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("3", "Pierre", "Maurice", 2, s));
        s.addJoueur(new JoueurNormal("4", "Fraise", "Louis", 2, s));
        s.addJoueur(new JoueurNormal("5", "Lesconte", "Benoit", 2, s));
        s.addJoueur(new JoueurNormal("6", "Bart", "Jeanne", 2, s));
        s.addJoueur(new JoueurNormal("7", "Wolfang", "Mozart", 2, s));
        s.addJoueur(new JoueurNormal("8", "Pard", "Leo", 2, s));
        s.addJoueur(new JoueurIntelligent("9", "Malin", "Pierre", 200, s));
        s.addJoueur(new JoueurNormal("10", "Bois", "Sylvain", 2, s));
        s.addJoueur(new JoueurNormal("11", "Ferry", "Lisa", 2, s));
        s.addJoueur(new JoueurNormal("12", "Valjean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("13", "Valmont", "Fantine", 2, s));
        s.addJoueur(new JoueurNormal("14", "Thuillier", "Thomas", 2, s));
    }
}