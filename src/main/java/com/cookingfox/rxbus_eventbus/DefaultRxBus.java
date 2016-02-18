package com.cookingfox.rxbus_eventbus;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import java.util.Objects;

/**
 * Default implementation of {@link RxBus}.
 */
public class DefaultRxBus implements RxBus {

    //----------------------------------------------------------------------------------------------
    // PROPERTIES
    //----------------------------------------------------------------------------------------------

    /**
     * Serialized publish subject.
     */
    protected final Subject<Object, Object> subject;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------------------------------

    public DefaultRxBus() {
        this(null);
    }

    public DefaultRxBus(Subject<Object, Object> subject) {
        if (subject == null) {
            subject = PublishSubject.create();
        }

        if (!(subject instanceof SerializedSubject)) {
            subject = subject.toSerialized();
        }

        this.subject = subject;
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS: RxBusEmitter
    //----------------------------------------------------------------------------------------------

    @Override
    public <T> void emit(T event) {
        subject.onNext(validateEvent(event));
    }

    @Override
    public <T> void post(T event) {
        subject.onNext(validateEvent(event));
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS: RxBusForwarder
    //----------------------------------------------------------------------------------------------

    @Override
    public <E> Subscription forward(Observable<?> source, Func0<E> eventProducer) {
        validateEventProducer(eventProducer);

        return source.map(o -> eventProducer.call()).subscribe(this::emit);
    }

    @Override
    public <E> Subscription forward(Observable<?> source, Func0<E> eventProducer, Action1<Throwable> onError) {
        validateEventProducer(eventProducer);

        return source.map(o -> eventProducer.call()).subscribe(this::emit, onError);
    }

    @Override
    public <T, E> Subscription forward(Observable<T> source, Func1<T, E> eventProducer) {
        validateEventProducer(eventProducer);

        return source.map(eventProducer).subscribe(this::emit);
    }

    @Override
    public <T, E> Subscription forward(Observable<T> source, Func1<T, E> eventProducer, Action1<Throwable> onError) {
        validateEventProducer(eventProducer);

        return source.map(eventProducer).subscribe(this::emit, onError);
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS: RxBusObserver
    //----------------------------------------------------------------------------------------------

    @Override
    public Observable<?> observe() {
        return subject.asObservable();
    }

    @Override
    public <T> Observable<T> observe(Class<T> eventClass) {
        return subject.ofType(validateEventClass(eventClass));
    }

    @Override
    public Observable<?> on() {
        return observe();
    }

    @Override
    public <T> Observable<T> on(Class<T> eventClass) {
        return observe(eventClass);
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS: RxBusSubscriber
    //----------------------------------------------------------------------------------------------

    @Override
    public Subscription subscribe(Observer<Object> observer) {
        return subject.subscribe(observer);
    }

    @Override
    public Subscription subscribe(Action1<Object> onNext) {
        return subject.subscribe(onNext);
    }

    @Override
    public Subscription subscribe(Action1<Object> onNext, Action1<Throwable> onError) {
        return subject.subscribe(onNext, onError);
    }

    @Override
    public <T> Subscription subscribe(Class<T> eventClass, Observer<T> observer) {
        return subject.ofType(validateEventClass(eventClass))
                .subscribe(observer);
    }

    @Override
    public <T> Subscription subscribe(Class<T> eventClass, Action1<T> onNext) {
        return subject.ofType(validateEventClass(eventClass))
                .subscribe(onNext);
    }

    @Override
    public <T> Subscription subscribe(Class<T> eventClass, Action1<T> onNext, Action1<Throwable> onError) {
        return subject.ofType(validateEventClass(eventClass))
                .subscribe(onNext, onError);
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    protected Object validateEvent(Object event) {
        Objects.requireNonNull(event, "Event can not be null");

        return event;
    }

    protected <T> Class<T> validateEventClass(Class<T> eventClass) {
        Objects.requireNonNull(eventClass, "Event class can not be null");

        return eventClass;
    }

    protected <E> Func0<E> validateEventProducer(Func0<E> eventProducer) {
        Objects.requireNonNull(eventProducer, "Event producer can not be null");

        return eventProducer;
    }

    protected <T, E> Func1<T, E> validateEventProducer(Func1<T, E> eventProducer) {
        Objects.requireNonNull(eventProducer, "Event producer can not be null");

        return eventProducer;
    }

}
