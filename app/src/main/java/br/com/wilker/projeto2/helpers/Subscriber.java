package br.com.wilker.projeto2.helpers;

/**
 * Parte do Padrão Observables, classe Helper, representa um escutador de evento
 * Created by Wilker on 06/06/2018.
 */
public abstract class Subscriber<T> implements EventListener<T>{
    EventEmitter<T> eventEmitter;

    // metodo a ser implementado que executa ao evento ouvido ser emitido
    @Override
    public abstract void onEventEmit(T value);

    public boolean subscribed;
    
    // para de ouvir ao evento
    public boolean unsubscribe(){
        if(subscribed){
            eventEmitter.unsubscribe(this);
        }
        return subscribed;
    }

    // volta a ouvir o evento
    public boolean resubscribe(){
        if(!subscribed){
            eventEmitter.subscribe(this);
        }
        return subscribed;
    }

}
