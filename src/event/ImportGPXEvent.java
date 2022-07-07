package event;

import io.jenetics.jpx.GPX;
import javafx.event.Event;
import javafx.event.EventType;


import static java.util.Objects.requireNonNull;

public class ImportGPXEvent extends Event {

    public static final EventType<ImportGPXEvent> GPX_IMPORTED = new EventType<>("GPX_IMPORTED");

    private final GPX gpx;

    public ImportGPXEvent(EventType<? extends Event> eventType, GPX gpx) {
        super(eventType);
        this.gpx = requireNonNull(gpx);
    }

    public GPX getGpx() {
        return gpx;
    }
}
