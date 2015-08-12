package com.ghosty.desktop.util;

import java.util.List;

public interface Observable {

    List<Observer> getObservers();

    default void addObserver(Observer o) {
        if (o != null) {
            getObservers().add(o);
        }
    }

    default void removeObserver(Observer o) {
        if (o != null) {
            getObservers().remove(o);
        }
    }

    default void notifyObservers() {
        getObservers().forEach(obs -> obs.notify(this));
    }
}
