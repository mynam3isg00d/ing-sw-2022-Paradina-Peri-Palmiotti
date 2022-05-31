package Observer;
import Util.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import java.util.ArrayList;
import java.util.List;


public class Observable {

    @Exclude
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
                observer.update(this, o);
            }
        }
    }
}