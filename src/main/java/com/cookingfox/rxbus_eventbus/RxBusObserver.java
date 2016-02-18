package com.cookingfox.rxbus_eventbus;

import rx.Observable;

/**
 * Operations for observing streams.
 */
public interface RxBusObserver {

    /**
     * Returns a stream for all events that are emitted on this EventBus.
     */
    Observable<?> observe();

    /**
     * Returns a stream for events of the provided class that are emitted on this EventBus.
     *
     * @param eventClass The type of event to observe.
     * @param <T>        Indicates the type of event to observe.
     */
    <T> Observable<T> observe(Class<T> eventClass);

    /**
     * Alias for {@link #observe()}.
     *
     * @see #observe()
     */
    Observable<?> on();

    /**
     * Alias for {@link #observe(Class)}.
     *
     * @see #observe(Class)
     */
    <T> Observable<T> on(Class<T> eventClass);

}
