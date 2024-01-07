import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GestionnaireBillet{
    private int limiteBilletMemoire;
    private String saveName;
    private ListeSansDoublon<String> listeIndex;
    private SauvegardeBillet listeGeree;

    public GestionnaireBillet(int lim,String saveName,ListeSansDoublon<Billet> liste){
        this.limiteBilletMemoire=lim;
        this.listeGeree=new SauvegardeBillet(liste);
        this.listeIndex=new ListeSansDoublon<String>();
        this.saveName=saveName;
    }

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
            this.listeGeree.viderListeBillet();
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

    public synchronized void sauvegarderListeBillet(){
        try{
            if(this.listeGeree.getListe().size()>0){
                int x=this.listeIndex.size();
                String name;
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