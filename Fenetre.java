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
import java.awt.Image;

public class Fenetre extends JFrame implements AutreEventListener{
  private JPanel haut,bas;
  private JButton buttonLancer,buttonValider;
  private JTextField paramN,paramK,paramT,paramDur;
  private JLabel textN,textK,textT,textD,textV,textX,textY;
  private Notifieur notifieurServeur;
  private Serveur serveur;
  private FrameAnalyst analyst;

  public Fenetre(){
    super("Loterie");
    this.setLocationRelativeTo(null);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(screenSize.width, screenSize.height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
    this.setLayout(null);
    this.haut=new JPanel();
    this.haut.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()*0.8));
    ImageIcon fond=null;
    fond = new ImageIcon("fond.jpg");
    JLabel img=new JLabel(fond);
    img.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()));
    this.haut.add(img);
    this.add(this.haut);
    this.bas=new JPanel();
    this.bas.setBounds(0,(int)(this.getHeight()*0.8),(int)(this.getWidth()),(int)(this.getHeight()*0.18));
    this.bas.setBackground(Color.GRAY);
    this.add(this.bas);

    this.textN=new JLabel();
    this.textK=new JLabel();
    this.textT=new JLabel();
    this.textD=new JLabel();
    this.textV=new JLabel();
    this.textX=new JLabel();
    this.textY=new JLabel();

    this.paramN=new JTextField();
    this.paramK=new JTextField();
    this.paramT=new JTextField();
    this.paramDur=new JTextField();

    this.buttonValider=new JButton();
    this.buttonLancer=new JButton();

  }

  public void setServeur(Serveur serveur){
    this.serveur=serveur;
    this.notifieurServeur=null;
    this.notifieurServeur=new Notifieur();
    this.notifieurServeur.addAutreEventListener(this.serveur);
    this.analyst=null;
    this.analyst=new FrameAnalyst(serveur);
  }

  public Serveur getServeur(){
    return this.serveur;
  }

  public void demarrer() {
    this.haut.setLayout(null);
    this.bas.setLayout(new GridLayout(2,5));
    this.buttonLancer.setText("LANCER LA LOTERIE");
    this.buttonLancer.setBackground(Color.YELLOW);
    this.buttonLancer.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.03)));
    this.buttonLancer.setBounds((int)(this.getWidth()*0.4),(int)(this.getHeight()*0.2),(int)(this.getWidth()*0.2),(int)(this.getHeight()*0.2));
    this.buttonLancer.addActionListener(new ActionListener() {
    @Override
      public void actionPerformed(ActionEvent e) {
        notifieurServeur.diffuserAutreEvent(new AutreEvent(this,"lancement"));
      }
    });
    this.buttonValider.setText("VALIDER");
    this.buttonValider.setBackground(Color.GREEN);
    this.textN.setText("Valeur maximale des nombres :");
    this.textK.setText("Nombre par billet :");
    this.textT.setText("Nombre gagnant par billet :");
    this.textD.setText("Durée :");
    this.textV.setText("Valider :");
    this.paramN.setText(""+this.serveur.getN());
    this.paramK.setText(""+this.serveur.getK());
    this.paramT.setText(""+this.serveur.getT());
    paramDur.setText(""+this.serveur.getDuree());
    this.buttonValider.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Integer i;
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
    });
    this.haut.add(this.buttonLancer);
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
    SwingUtilities.updateComponentTreeUI(this);
  }

  public void start(){
    this.haut.remove(this.buttonLancer);
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
    this.haut.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()));
    this.bas.setBackground(Color.YELLOW);
    this.bas.setBounds((int)(this.getWidth()*0.333),(int)(this.getHeight()*0.333),(int)(this.getWidth()*0.333),(int)(this.getHeight()*0.2));
    this.textV.setFont(new Font("Arial", Font.PLAIN,(int)(this.getHeight()*0.06)));
    this.bas.add(this.textV);
    this.textV.setText("Loterie en cours");
    this.haut.add(this.bas);
    SwingUtilities.updateComponentTreeUI(this);
  }



  public void loterie(){
    this.haut.remove(this.bas);
    this.bas.remove(this.textV);
    this.haut.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()*0.9));
    this.bas.setBounds(0,(int)(this.getHeight()*0.9),(int)(this.getWidth()),(int)(this.getHeight()*0.09));
    this.haut.setLayout(new GridLayout(5,1));
    this.bas.setLayout(new GridLayout(1,4));
    this.haut.add(this.textD);
    this.haut.add(this.textN);
    this.haut.add(this.textK);
    this.haut.add(this.textT);
    this.haut.add(this.textV);
    this.bas.add(this.buttonLancer);
    this.bas.add(this.textX);
    this.bas.add(this.textY);
    this.bas.add(this.buttonValider);
    this.textX.setText("Nombre de joueur :"+this.serveur.getListeJoueur().size());
    this.textY.setText("Nombre de billets vendus :"+this.serveur.getNombreVente());
    this.buttonLancer.setText("Précédent");
    this.buttonValider.setText("Suivant");
    SwingUtilities.updateComponentTreeUI(this);
  }

  public void actionADeclancher(AutreEvent event) {
    if (event.getSource() instanceof Serveur && event.getDonnee() instanceof String){
      if(event.getDonnee().equals("start")){
        this.start();
      }
      else if(event.getDonnee().equals("endStart")){
        this.haut.remove(this.bas);
        this.bas.remove(this.textV);
        this.haut.setBounds(0,0,(int)(this.getWidth()),(int)(this.getHeight()*0.9));
        this.bas.setBounds(0,(int)(this.getHeight()*0.9),(int)(this.getWidth()),(int)(this.getHeight()*0.09));
        this.haut.setLayout(null);
        this.bas.setLayout(new GridLayout(1,2));
      }
      else if(event.getDonnee().equals("done")){
        this.loterie();
      }
    }
  }
}