public class Serveur{
    private int n;
    private int k;
    private int t;
    private int duree;

    public Serveur(int n,int k,int t,int duree){
        this.n=n;
        this.k=k;
        this.t=t;
        this.duree=duree;
    }

    public void setN(int n){
        this.n=n;
    }
    public void setK(int k){
        this.k=k;
    }
    public void setT(int t){
        this.t=t;
    }
    public void setDuree(int duree){
        this.duree=duree;
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
}