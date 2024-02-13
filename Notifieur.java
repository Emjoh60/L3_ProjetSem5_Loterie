import javax.swing.event.EventListenerList;

public class Notifieur {
    private EventListenerList listenerList = new EventListenerList();

    public void addAutreEventListener(AutreEventListener listener) {
        listenerList.add(AutreEventListener.class, listener);
    }

    public void removeAutreEventListener(AutreEventListener listener) {
        listenerList.remove(AutreEventListener.class, listener);
    }

    // Méthode appellée par le notifieur d'un objet permettant de prévenir tous les listener de cet objet que celui-ci diffuse un message
    public void diffuserAutreEvent(AutreEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            ((AutreEventListener) listeners[i + 1]).actionADeclancher(evt);
        }
    }
}