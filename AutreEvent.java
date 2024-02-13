public class AutreEvent extends java.util.EventObject {
    private Object donnee;
    public AutreEvent(Object source, Object donnee) {
        super(source); // Source de l'événement
        this.donnee = donnee; // Donnée transmise
    }
    public Object getDonnee() {
        return this.donnee;
    }
}