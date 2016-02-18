package com.cookingfox.rxbus_eventbus;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Operations for subscribing to streams.
 */
public interface RxBusSubscriber {

    /**
     * Subscribes to all events that are emitted on this EventBus.
     *
     * @param observer The Observer that will handle emissions and notifications from the
     *                 Observable.
     * @return A subscription reference with which the observer can stop receiving items before
     * the Observable has completed.
     */
    Subscription subscribe(Observer<Object> observer);

    /**
     * Subscribes to all events that are emitted on this EventBus.
     *
     * @param onNext The action you have designed to accept emissions from the Observable.
     * @return A subscription reference with which the observer can stop receiving items before
     * the Observable has completed.
     */
    Subscription subscribe(Action1<Object> onNext);

    /**
     * Subscribes to all events that are emitted on this EventBus.
     *
     * @param onNext  The action you have designed to accept emissions from the Observable.
     * @param onError The action you have designed to accept any error notification from the
     *                Observable.
     * @return A subscription reference with which the observer can stop receiving items before
     * the Observable has completed.
     */
    Subscription subscribe(Action1<Object> onNext, Action1<Throwable> onError);

    /**
     * Subscribes to events of the provided type that are emitted on this EventBus.
     *
     * @param eventClass The type of event to observe.
     * @param observer   The Observer that will handle emissions and notifications from the
     *                   Observable.
     * @return A subscription reference with which the observer can stop receiving items before
     * the Observable has completed.
     */
    <T> Subscription subscribe(Class<T> eventClass, Observer<T> observer);

    /**
     * Subscribes to events of the provided type that are emitted on this EventBus.
     *
     * @param eventClass The type of event to observe.
     * @param onNext     The action you have designed to accept emissions from the Observable.
     * @return A subscription reference with which the observer can stop receiving items before
     * the Observable has completed.
     */
    <T> Subscription subscribe(Class<T> eventClass, Action1<T> onNext);

    /**
     * Subscribes to events of the provided type that are emitted on this EventBus.
     *
     * @param eventClass The type of event to observe.
     * @param onNext     The action you have designed to accept emissions from the Observable.
     * @param onError    The action you have designed to accept any error notification from the
     *                   Observable.
     * @return A subscription reference with which the observer can stop receiving items before
     * the Observable has completed.
     */
    <T> Subscription subscribe(Class<T> eventClass, Action1<T> onNext, Action1<Throwable> onError);

}
