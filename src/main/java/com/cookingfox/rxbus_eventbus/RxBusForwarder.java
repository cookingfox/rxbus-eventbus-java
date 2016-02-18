package com.cookingfox.rxbus_eventbus;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Operations for forwarding observables to events. What this means is that when a provided 'source'
 * observable emits a value, an 'event producer' is triggered that creates an event (with or
 * without the emitted value), which is then emitted on the RxBus.
 */
public interface RxBusForwarder {

    /**
     * Subscribes to the source stream and, on a new value, produces an event using the provided
     * producer and emits that event on the EventBus.
     *
     * @param source        The source stream to subscribe to.
     * @param eventProducer The function that creates an event, ignoring the emitted value of the
     *                      source stream.
     * @param <E>           Indicates the event type which the event producer creates.
     * @return The subscription for the forwarding stream.
     */
    <E> Subscription forward(Observable<?> source, Func0<E> eventProducer);

    /**
     * Subscribes to the source stream and, on a new value, produces an event using the provided
     * producer and emits that event on the EventBus.
     *
     * @param source        The source stream to subscribe to.
     * @param eventProducer The function that creates an event, ignoring the emitted value of the
     *                      source stream.
     * @param onError       The action you have designed to accept any error notification from the
     *                      Observable.
     * @param <E>           Indicates the event type which the event producer creates.
     * @return The subscription for the forwarding stream.
     */
    <E> Subscription forward(Observable<?> source, Func0<E> eventProducer, Action1<Throwable> onError);

    /**
     * Subscribes to the source stream and, on a new value, produces an event using the provided
     * producer and emits that event on the EventBus.
     *
     * @param source        The source stream to subscribe to.
     * @param eventProducer The function that creates an event using the emitted value of the source
     *                      stream.
     * @param <T>           Indicates the type of value which the source stream emits.
     * @param <E>           Indicates the event type which the event producer creates.
     * @return The subscription for the forwarding stream.
     */
    <T, E> Subscription forward(Observable<T> source, Func1<T, E> eventProducer);

    /**
     * Subscribes to the source stream and, on a new value, produces an event using the provided
     * producer and emits that event on the EventBus.
     *
     * @param source        The source stream to subscribe to.
     * @param eventProducer The function that creates an event using the emitted value of the source
     *                      stream.
     * @param onError       The action you have designed to accept any error notification from the
     *                      Observable.
     * @param <T>           Indicates the type of value which the source stream emits.
     * @param <E>           Indicates the event type which the event producer creates.
     * @return The subscription for the forwarding stream.
     */
    <T, E> Subscription forward(Observable<T> source, Func1<T, E> eventProducer, Action1<Throwable> onError);

}
