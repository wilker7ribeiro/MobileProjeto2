package br.com.wilker.projeto2.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Parte do Padrão Observables, classe helder
 * Created by Wilker on 06/06/2018.
 */
public class EventEmitter<T>{
    private List<Subscriber<T>> listeners = new ArrayList<>();

    // adiciona um escutador ao evento
    public Subscriber subscribe(Subscriber<T> subscriber){
        subscriber.eventEmitter = this;
        listeners.add(subscriber);
        subscriber.subscribed = true;
        return subscriber;
    }


    // emite o evento
    public void emit(T valor){
        for (int i = 0; i < listeners.size(); i++){
            listeners.get(i).onEventEmit(valor);
        }
    }

    // remove escutador
    public void unsubscribe(Subscriber<T> subscriber) {
        listeners.remove(subscriber);
        subscriber.subscribed = false;
    }
}
