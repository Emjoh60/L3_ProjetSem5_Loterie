import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Serveur{
    private int n;
    private int k;
    private int t;
    private int duree;
    private boolean actif;
    private Notifieur notifieur;
    private ListeSansDoublon <Joueur> listeJoueur;
    private ListeSansDoublon <Billet> listeBillet;
    private double prixBilletUn;
    private double prixBilletDeux;

    public Serveur(int n, int k, int t, int duree, double prixBilletUn, double prixBilletDeux) {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.listeJoueur = new ListeSansDoublon<Joueur>();
        this.listeBillet = new ListeSansDoublon<Billet>();
        this.prixBilletUn=prixBilletUn;
        this.prixBilletDeux=prixBilletDeux;
    }

    public Serveur(int n, int k, int t, int duree, double prixBilletUn, double prixBilletDeux, ArrayList<Joueur> listeJoueur) throws NullPointerException {
        this.n = n;
        this.k = k;
        this.t = t;
        this.actif = false;
        this.duree = duree;
        this.notifieur = new Notifieur();
        this.listeBillet = new ListeSansDoublon<Billet>();
        this.prixBilletUn=prixBilletUn;
        this.prixBilletDeux=prixBilletDeux;
        if (listeJoueur == null) {
            this.listeJoueur = new ListeSansDoublon<Joueur>();
            throw new NullPointerException("La liste de joueur est vide");
        } 
        else {
            try{
                this.listeJoueur = new ListeSansDoublon<Joueur>(listeJoueur);
                for (Joueur j : listeJoueur) {
                    this.notifieur.addAutreEventListener(j);
                }
            }
            catch(IllegalArgumentException iae){
                System.err.println(iae.getMessage());
            }  
        }
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setprixBilletUn(int prixBilletUn) {
        this.prixBilletUn = prixBilletUn;
    }

    public void setprixBilletDeux(int prixBilletDeux) {
        this.prixBilletDeux = prixBilletDeux;
    }

    public int getN() {
        return this.n;
    }

    public int getK() {
        return this.k;
    }

    public int getT() {
        return this.t;
    }

    public int getDuree() {
        return this.duree;
    }

    public boolean getState() {
        return this.actif;
    }

    public double getprixBilletUn() {
        return this.prixBilletUn;
    }

    public double getprixBilletDeux() {
        return this.prixBilletDeux;
    }

    public boolean isJoueurRegistered(Joueur j) {
        return this.listeJoueur.contains(j);
    }

    public Joueur getJoueur(String id) {
        return this.listeJoueur.get(new JoueurNormal(id,"","",0));
    }

    public void addJoueur(Joueur j){
        try{
            this.listeJoueur.add(j);
            this.notifieur.addAutreEventListener(j);
        }
        catch(IllegalArgumentException iae){
            System.err.println(iae.getMessage());
        }  
    }

    public void removeJoueur(Joueur rj){
        try{
            this.listeJoueur.remove(rj);
            this.notifieur.removeAutreEventListener(rj);
        }
        catch(NoSuchElementException nsee){
            System.err.println(nsee.getMessage());
        }
    }

    public boolean containBillet(Billet b) {
        return this.listeBillet.contains(b);
    }

    public Billet getBillet(String id) {
        return this.listeBillet.get(new BilletI(id,"",0,new ArrayList<Integer>()));
    }

    public void addBillet(Billet b){
        try{
            this.listeBillet.add(b);
        }
        catch(IllegalArgumentException iae){
            System.err.println(iae.getMessage());
        }
    }

    public void removeBilet(Billet rb){
        try{
            this.listeBillet.remove(rb);
        }
        catch(NoSuchElementException nsee){
            System.err.println(nsee.getMessage());
        }
    }

    public void enregisterBillet(Billet b){
        try{
            FileOutputStream fos = new FileOutputStream("Billet/"+b.getId());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(b);
            oos.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public Billet chargerBillet(String id){
        try{
            FileInputStream fis = new FileInputStream("Billet/"+id);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Billet b=(Billet) ois.readObject();
            ois.close();
            return b;
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }
        catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            return null;
        }
        
    }

    public static void main(String args[]){
        ArrayList <Integer> a=new ArrayList<>();
        a.add(Integer.valueOf(2));
        BilletI b=new BilletI("22", "AZE", 0,a);
        BilletI o=new BilletI("33", "ASE", 0,a);
        Serveur s=new Serveur(1,1,1,1,2.3,2.3);
        s.enregisterBillet(b);
        o=(BilletI)s.chargerBillet("AZE");
        System.out.print(o.getId()+"\n");
    }


}