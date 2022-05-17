package Observer;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(Object o){
        synchronized (observers) {
            for(Observer observer : observers){
                observer.update(o);
            }
        }
    }
}