package event;


import javafx.event.Event;
import javafx.event.EventType;
import model.Randonnee;

import static java.util.Objects.requireNonNull;


public class SelectionRandonneeEvent extends Event {

    public static final EventType<SelectionRandonneeEvent> RANDO_SELEC = new EventType<>("RANDO_SELEC");

    public static final EventType<SelectionRandonneeEvent> RANDO_SELEC_MOD = new EventType<>("RANDO_SELEC_MOD");

    private final Randonnee randonnee;

    public SelectionRandonneeEvent(EventType<? extends Event> eventType, Randonnee randonnee) {
        super(eventType);
        this.randonnee = requireNonNull(randonnee);
    }

    public Randonnee getRandonnee(){
        return randonnee;
    }
}
