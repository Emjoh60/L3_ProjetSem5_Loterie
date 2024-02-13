import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GestionnaireBillet{

    /* Attributs */
    private int limiteBilletMemoire; // Limite de billet en mémoire
    private String saveName; // Nom du fichier dans lequel sérialiser les objets
    private ListeSansDoublon<String> listeIndex; // Liste contenant tous les noms des fichiers
    private SauvegardeBillet listeGeree; // Liste sans doublon sérialisable de manière safe

    /* Constructeurs */
    public GestionnaireBillet(int lim,String saveName,ListeSansDoublon<Billet> liste){
        this.limiteBilletMemoire=lim;
        this.listeGeree=new SauvegardeBillet(liste);
        this.listeIndex=new ListeSansDoublon<String>();
        this.saveName=saveName;
    }

    /* Getter et setter */
    public int getLimiteBilletMemoire(){
        return this.limiteBilletMemoire;
    }

    public ListeSansDoublon<String> getListeIndex(){
        return this.listeIndex;
    }

    public String getSaveName(){
        return this.saveName;
    }

    public SauvegardeBillet getListeGeree(){
        return this.listeGeree;
    }

    public void setLimiteBilletMemoire(int x){
        this.limiteBilletMemoire=x;
    }

    public void setSaveName(String x){
        this.saveName=x;
    }

    public void setListeGeree(ListeSansDoublon<Billet> liste) throws NullPointerException{
        if(liste!=null){
            this.listeGeree.viderListeBillet(); // En cas d'affectation de nouvelle liste, on vide la précédente de tout contenu
            this.listeGeree.enregistrerListeBillet(liste);
        }
        else{
            throw new NullPointerException("Liste vide passée en paramètre");
        }
    }

    public void viderListeGeree(){
        this.listeGeree.viderListeBillet();
    }

    public void enregisterBillet(Billet b){
        this.listeGeree.enregistrerBillet(b);
    }

    public void desenregisterBillet(Billet b){
        this.listeGeree.desenregistrerBillet(b);
    }

    /* Méthodes de sérialisation d'un billet unique */
    public void serializerBillet(Billet b){
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

    /* Méthodes de désérialisation d'un billet unique */
    public Billet deserializerBillet(String id){
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

    /* Méthodes de sérialisation d'une liste de billet */
    public synchronized void sauvegarderListeBillet(){
        try{
            // Si la liste contient un élément à sauvegarder
            if(this.listeGeree.getListe().size()>0){
                // Affectation d'un nom
                int x=this.listeIndex.size();
                String name;
                // Vérification de la disponibilité
                if(this.listeIndex.contains(this.saveName)){
                    name=this.saveName+x;
                }
                else{
                    name=this.saveName;
                }
                this.listeIndex.add(name);
                FileOutputStream fos = new FileOutputStream("Sauvegarde/"+name);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this.listeGeree);
                oos.close();
                System.out.println("\n SAUVEGARDE"+name);
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /* Méthodes de désérialisation d'une liste de billet */
    public ListeSansDoublon<Billet> chargerListeBillet(String id){
        try{
            FileInputStream fis = new FileInputStream("Sauvegarde/"+id);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SauvegardeBillet b=(SauvegardeBillet)ois.readObject();
            ois.close();
            return b.getListe();
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


    /* Méthode de contrôle du nombre de billet */
    public boolean gestionLimiteBillet(){
        if(this.listeGeree.size()<this.limiteBilletMemoire){
            return false;
        }
        else{
            this.sauvegarderListeBillet();
            this.listeGeree.viderListeBillet();
            return true;
        }
    }
}