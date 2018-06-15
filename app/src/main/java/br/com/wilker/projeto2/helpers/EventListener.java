package br.com.wilker.projeto2.helpers;

/**
 * Parte do Padrão Observables, Interface Helper
 * Created by Wilker on 06/06/2018.
 */
public interface EventListener<T> {
    // Método a ser implementado pelos Listeners
    void onEventEmit(T value);
}
