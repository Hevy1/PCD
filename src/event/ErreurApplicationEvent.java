package event;

import javafx.event.Event;
import javafx.event.EventType;

public class ErreurApplicationEvent extends Event {

    public static final EventType<ErreurApplicationEvent> ERREUR_APPLICATION = new EventType<>("ERREUR_APPLICATION");

    public static final EventType<ErreurApplicationEvent> ERREUR_APPLICATION_CRITIQUE = new EventType<>("ERREUR_APPLICATION_EVENT");

    private final String message;

    public ErreurApplicationEvent(EventType<? extends Event> eventType, String message) {
        super(eventType);

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
