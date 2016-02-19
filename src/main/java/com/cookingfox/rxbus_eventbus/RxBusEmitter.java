package com.cookingfox.rxbus_eventbus;

/**
 * Operations for emitting events.
 */
public interface RxBusEmitter {

    /**
     * Alias for {@link #emit(Object)}.
     *
     * @see #emit(Object)
     */
    <T> void dispatch(T event);

    /**
     * Emit an event object to all observers.
     *
     * @param event The event.
     */
    <T> void emit(T event);

    /**
     * Alias for {@link #emit(Object)}.
     *
     * @see #emit(Object)
     */
    <T> void post(T event);

}
