package com.mokelab.demo.kiilib.app;

/**
 * callback
 */
public interface ItemCallback<T> {
    void onSuccess(T item);
    void onError(Exception e);
}
