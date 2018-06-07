package br.com.wilker.projeto2.helpers;

/**
 * Created by Wilker on 06/06/2018.
 */
public abstract class Subscriber<T> implements EventListener<T>{
    EventEmitter<T> eventEmitter;

    @Override
    public abstract void onEventEmit(T value);
    public boolean subscribed;
    public boolean unsubscribe(){
        if(subscribed){
            eventEmitter.unsubscribe(this);
        }
        return subscribed;
    }

    public boolean resubscribe(){
        if(!subscribed){
            eventEmitter.subscribe(this);
        }
        return subscribed;
    }

}
