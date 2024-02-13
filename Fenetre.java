import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class Fenetre extends JFrame implements AutreEventListener{
  /* Attributs */
  private JPanel haut,bas; // Les deux zones de l'écran
  private JButton buttonLancer,buttonValider,buttonFin; // Les boutons à utiliser et réutiliser
  private JTextField paramN,paramK,paramT,paramDur; // Les champs de texte
  private JLabel textN,textK,textT,textD,textV,textX,textY,img; // Les zones d'affichage de texte et d'image
  private Notifieur notifieurServeur; // Le notifieur pour envoyer des messages au Serveur
  private Serveur serveur; // Le serveur dont les données sont liées à la fenêtre
  private FrameAnalyst analyst; // Outil d'analyse des données lié au serveur pour la fenêtre
  private ActionListener a1,a2,a3; // Action liées aux boutons

  /* Constructeurs */
  public Fenetre(){
    super("Loterie");
    this.setLocationRelativeTo(null);
    // Définition de la taille de l'écran
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(screenSize.width, screenSize.height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
    // Définition de la position des différentes zones
    this.setLayout(null);
    // Définition du pannel haut
    this.haut=new JPanel();
    this.haut.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()*0.8)); // le pannel haut occupe 80% du haut de l'écran
    ImageIcon fond=null;
    fond = new ImageIcon("Image/fond.jpg");
    this.img=new JLabel(fond);
    this.img.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()));
    this.haut.add(this.img);
    this.add(this.haut);
    // Définition du pannel bas
    this.bas=new JPanel();
    this.bas.setBounds(0,(int)(this.getHeight()*0.8),(int)(this.getWidth()),(int)(this.getHeight()*0.18));  // le pannel bas occupe 18% du bas de l'écran
    this.bas.setBackground(Color.LIGHT_GRAY);
    this.add(this.bas);

    // Définition des Label
    this.textN=new JLabel();
    this.textK=new JLabel();
    this.textT=new JLabel();
    this.textD=new JLabel();
    this.textV=new JLabel();
    this.textX=new JLabel();
    this.textY=new JLabel();

    // Définition des zones de textes
    this.paramN=new JTextField();
    this.paramK=new JTextField();
    this.paramT=new JTextField();
    this.paramDur=new JTextField();

    // Définition des boutons
    this.buttonValider=new JButton();
    this.buttonLancer=new JButton();
    this.buttonFin=new JButton();
  }

  // Affectation d'un serveur à la fenêtre
  public void setServeur(Serveur serveur){
    this.serveur=serveur;
    this.notifieurServeur=null;
    this.notifieurServeur=new Notifieur();
    this.notifieurServeur.addAutreEventListener(this.serveur); // On lie le serveur au notifieur
    this.analyst=null;
    this.analyst=new FrameAnalyst(serveur); // On lie le serveur à l'analyst
  }

  public Serveur getServeur(){
    return this.serveur;
  }

  /* FONCTION POUR GENERER L'INTERFACE DE DEMARRAGE */
  public void demarrer() {
    // Définition du Layout
    this.haut.setLayout(null);
    this.bas.setLayout(new GridLayout(2,5));
    // Paramètrage des boutons
    this.buttonLancer.setText("LANCER LA LOTERIE");
    this.buttonLancer.setBackground(Color.YELLOW);
    this.buttonFin.setText("LANCER LA LOTERIE (Uncheck)");
    this.buttonFin.setBackground(Color.YELLOW);
    this.buttonLancer.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.03)));
    this.buttonFin.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.buttonLancer.setBounds((int)(this.getWidth()*0.2),(int)(this.getHeight()*0.2),(int)(this.getWidth()*0.2),(int)(this.getHeight()*0.2));
    this.buttonFin.setBounds((int)(this.getWidth()*0.6),(int)(this.getHeight()*0.2),(int)(this.getWidth()*0.2),(int)(this.getHeight()*0.2));
    this.a1=new ActionListener() {
      @Override
        public void actionPerformed(ActionEvent e) {
          notifieurServeur.diffuserAutreEvent(new AutreEvent(this,"lancement"));
        }
      };
    this.a3=new ActionListener() {
      @Override
        public void actionPerformed(ActionEvent e) {
          notifieurServeur.diffuserAutreEvent(new AutreEvent(this,"lancementUnsafe"));
        }
    };
    this.buttonLancer.addActionListener(a1);
    this.buttonFin.addActionListener(a3);
    this.buttonValider.setText("VALIDER");
    this.buttonValider.setBackground(Color.GREEN);
    // Paramètrage des zones de textes
    this.textN.setText("Valeur maximale des nombres :");
    this.textK.setText("Nombre par billet :");
    this.textT.setText("Nombre gagnant par billet :");
    this.textD.setText("Durée :");
    this.textV.setText("Valider :");
    this.paramN.setText(""+this.serveur.getN());
    this.paramK.setText(""+this.serveur.getK());
    this.paramT.setText(""+this.serveur.getT());
    this.paramDur.setText(""+this.serveur.getDuree());
    // Paramètrage du boutons de validation des champs de texte
    this.a2=new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Integer i;
        // On vérifie à chaque fois que le text passé est un entier
        try{
          String text = paramN.getText();
          if(text!=null && text.matches("^\\d+$")){
            i=Integer.valueOf(text);
            serveur.setN(i);
          }
          text = paramK.getText();
          if(text!=null && text.matches("^\\d+$")){
            i=Integer.valueOf(text);
            serveur.setK(i);
          }
          text = paramT.getText();
          if(text!=null && text.matches("^\\d+$")){
            i=Integer.valueOf(text);
            serveur.setT(i);
          }
          text = paramDur.getText();
          if(text!=null && text.matches("^\\d+$")){
            i=Integer.valueOf(text);
            serveur.setDuree(i);
          }
        }
        catch(NullPointerException npe){}
      }
    };
    // Ajout des composants
    this.buttonValider.addActionListener(this.a2);
    this.haut.add(this.buttonLancer);
    this.haut.add(this.buttonFin); 
    this.bas.add(this.textN);
    this.bas.add(this.textK);
    this.bas.add(this.textT);
    this.bas.add(this.textD);
    this.bas.add(this.textV);
    this.bas.add(this.paramN);
    this.bas.add(this.paramK);
    this.bas.add(this.paramT);
    this.bas.add(this.paramDur);
    this.bas.add(this.buttonValider);
    this.revalidate();
    this.repaint();// Rafraichissement de la page
  }

  /* FONCTION POUR SUPPRIMER LES COMPOSANTS DE L'INTERFACE DE DEMARRAGE */
  public void load(){
    this.haut.remove(this.buttonLancer);
    this.haut.remove(this.buttonFin);
    this.bas.remove(this.textN);
    this.bas.remove(this.textK);
    this.bas.remove(this.textT);
    this.bas.remove(this.textD);
    this.bas.remove(this.textV);
    this.bas.remove(this.paramN);
    this.bas.remove(this.paramK);
    this.bas.remove(this.paramT);
    this.bas.remove(this.paramDur);
    this.bas.remove(this.buttonValider);
    this.remove(this.bas);
    this.revalidate();
    this.repaint();// Rafraichissement de la page
  }

  /* FONCTION POUR GENERER L'INTERFACE DE LOTERIE */
  public void loterie(){
    // Redéfinition des JPanels
    this.haut.remove(this.bas);
    this.haut.remove(this.img);
    this.haut.setBackground(Color.CYAN);
    this.add(this.bas);
    this.haut.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()*0.9));
    this.bas.setBounds(0,(int)(this.getHeight()*0.9),(int)(this.getWidth()),(int)(this.getHeight()*0.09));
    this.haut.setLayout(new GridLayout(5,1));
    this.bas.setLayout(new GridLayout(1,5));
    // Ajout des composant au JPanel du haut, il contiendra une liste de 5 billet
    this.haut.add(this.textD);
    this.haut.add(this.textN);
    this.haut.add(this.textK);
    this.haut.add(this.textT);
    this.haut.add(this.textV);
    // Redéfinition des zones de textes
    this.textV.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.textD.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.textN.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.textK.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.textT.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    // Ajout des composant au JPanel du bas, il contiendra des informations et des boutons
    this.bas.add(this.buttonLancer);
    this.bas.add(this.textX);
    this.bas.add(this.buttonFin);
    this.bas.add(this.textY);
    this.bas.add(this.buttonValider);
    // Paramètrage des composants du JPanel du bas
    this.textX.setText("Nombre de joueur : "+this.serveur.getListeJoueur().size());
    this.textY.setText("Nombre de billets vendus : "+this.serveur.getNombreVente());
    this.buttonLancer.removeActionListener(this.a1);
    this.buttonValider.removeActionListener(this.a2);
    this.buttonFin.removeActionListener(this.a3);
    this.buttonLancer.setText("PRECEDENT");
    this.buttonValider.setText("SUIVANT");
    this.buttonFin.setText("OK");
    this.buttonFin.setBackground(Color.GREEN);
    this.buttonLancer.setBackground(Color.GREEN);
    this.buttonValider.setBackground(Color.GREEN);
    this.buttonLancer.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.buttonValider.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.02)));
    this.analyst.setListe();
    this.loadBillet();
    final Fenetre f=this;
    this.a1=new ActionListener() {
      @Override
        public void actionPerformed(ActionEvent e) {
          if(analyst.getNextList()){
            f.loadBillet();
          }
          SwingUtilities.updateComponentTreeUI(f);
        }
      };
    this.a2=new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(analyst.getPrevtList()){
          f.loadBillet();
        }
        SwingUtilities.updateComponentTreeUI(f);
      }
    };  
    this.a3=new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        notifieurServeur.diffuserAutreEvent(new AutreEvent(this, "finLoterie"));
      }
    };  
    this.buttonLancer.addActionListener(a1);
    this.buttonValider.addActionListener(a2);
    this.buttonFin.addActionListener(a3);
    this.revalidate();
    this.repaint();// Rafraichissement de la page
  }

  /* FONCTION POUR GENERER L'INTERFACE DE FIN DE LOTERIE */
  public void fin(){
    // Reparamètrage des composants du JPanel du bas afin qu'ils correspondent aux informations de fin de loterie
    this.textX.setText("Numéro gagnant : "+this.analyst.getTicketGagnant());
    this.textY.setText("Nombre de gagnant : "+this.serveur.getNombreGagnant());
    this.buttonFin.removeActionListener(this.a3);
    this.buttonFin.setText("FIN");
    final Fenetre f=this;
    this.a3=new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        f.dispose();
      }
    };  
    this.buttonFin.addActionListener(a3);
    this.revalidate();
    this.repaint();// Rafraichissement de la page
  }

  // Méthode permettant de charger les billets de l'analyst dans les zones de textes
  public void loadBillet(){
    String tamp=this.analyst.getNextBillet();
    if(tamp!=null){
      this.textD.setText(tamp);
    }
    tamp=this.analyst.getNextBillet();
    if(tamp!=null){
      this.textN.setText(tamp);
    }
    tamp=this.analyst.getNextBillet();
    if(tamp!=null){
      this.textK.setText(tamp);
    }
    tamp=this.analyst.getNextBillet();
    if(tamp!=null){
      this.textT.setText(tamp);
    }
    tamp=this.analyst.getNextBillet();
    if(tamp!=null){
      this.textV.setText(tamp);
    }
  }

  // Récupération des évènements du Serveur
  public void actionADeclancher(AutreEvent event) {
    if (event.getSource() instanceof Serveur && event.getDonnee() instanceof String){
      if(event.getDonnee().equals("load")){
        this.load();
      }
      else if(event.getDonnee().equals("endStart")){
        this.loterie();
      }
      else if(event.getDonnee().equals("done")){
        this.fin();
      }
    }
  }
}