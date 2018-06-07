package br.com.wilker.projeto2.helpers;

/**
 * Created by Wilker on 06/06/2018.
 */
public interface EventListener<T> {
    void onEventEmit(T value);
}
