public class Main{
    public static void main(String args[]){
        Fenetre f=new Fenetre();
        Serveur s=new Serveur(10, 3, 2, 10 , 5, 2, 4,f);
        f.setServeur(s);
        f.demarrer();
        
        s.addJoueur(new JoueurNormal("1", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("2", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("3", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("4", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("5", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("6", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("7", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("8", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurIntelligent("9", "Jean", "Malin", 200, s));
        s.addJoueur(new JoueurNormal("10", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("11", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("12", "Jean", "Jules", 2, s));
        s.addJoueur(new JoueurNormal("13", "Jean", "Jules", 2, s));
    }
}