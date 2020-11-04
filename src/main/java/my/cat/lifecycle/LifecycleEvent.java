package my.cat.lifecycle;

import java.util.EventObject;

public class LifecycleEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public LifecycleEvent(Object source) {
        super(source);
    }
}
