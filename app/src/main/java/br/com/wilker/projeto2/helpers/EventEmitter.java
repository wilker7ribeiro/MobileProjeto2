package br.com.wilker.projeto2.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilker on 06/06/2018.
 */
public class EventEmitter<T>{
    private List<Subscriber<T>> listeners = new ArrayList<>();

    public Subscriber subscribe(Subscriber<T> subscriber){
        subscriber.eventEmitter = this;
        listeners.add(subscriber);
        subscriber.subscribed = true;
        return subscriber;
    }


    public void emit(T valor){
        for (int i = 0; i < listeners.size(); i++){
            listeners.get(i).onEventEmit(valor);
        }
    }

    public void unsubscribe(Subscriber<T> subscriber) {
        listeners.remove(subscriber);
        subscriber.subscribed = false;
    }
}
