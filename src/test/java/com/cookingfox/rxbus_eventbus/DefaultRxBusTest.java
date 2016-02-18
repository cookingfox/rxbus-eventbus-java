package com.cookingfox.rxbus_eventbus;

import fixtures.com.cookingfox.rxbus_eventbus.SimpleEvent;
import fixtures.com.cookingfox.rxbus_eventbus.StringEvent;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link DefaultRxBus}.
 */
public class DefaultRxBusTest {

    private DefaultRxBus rxBus;

    //----------------------------------------------------------------------------------------------
    // SETUP
    //----------------------------------------------------------------------------------------------

    @Before
    public void setUp() throws Exception {
        rxBus = new DefaultRxBus();
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: constructor (without subject)
    //----------------------------------------------------------------------------------------------

    @Test
    public void constructor_should_create_serialized_subject() throws Exception {
        assertNotNull(rxBus.subject);
        assertTrue(rxBus.subject instanceof SerializedSubject);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: constructor (with subject)
    //----------------------------------------------------------------------------------------------

    @Test
    public void constructor_with_subject_should_set_subject() throws Exception {
        final Subject<Object, Object> subject = BehaviorSubject.create().toSerialized();
        rxBus = new DefaultRxBus(subject);

        assertSame(rxBus.subject, subject);
    }

    @Test
    public void constructor_with_subject_should_serialize_subject() throws Exception {
        final Subject<Object, Object> subject = BehaviorSubject.create();
        rxBus = new DefaultRxBus(subject);

        assertTrue(rxBus.subject instanceof SerializedSubject);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: emit
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void emit_should_throw_if_null() throws Exception {
        rxBus.emit(null);
    }

    @Test
    public void emit_should_emit_event_to_observers() throws Exception {
        final SimpleEvent event = new SimpleEvent();
        final TestSubscriber<SimpleEvent> subscriberA = TestSubscriber.create();
        final TestSubscriber<SimpleEvent> subscriberB = TestSubscriber.create();

        rxBus.observe(SimpleEvent.class).subscribe(subscriberA);
        rxBus.observe(SimpleEvent.class).subscribe(subscriberB);
        rxBus.emit(event);

        subscriberA.assertValue(event);
        subscriberB.assertValue(event);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: forward
    //----------------------------------------------------------------------------------------------

    @Test
    public void forward_should_forward_source_to_event_producer() throws Exception {
        final String value = "foo";
        final PublishSubject<String> stringObservable = PublishSubject.create();
        final TestSubscriber<StringEvent> subscriber = TestSubscriber.create();

        rxBus.forward(stringObservable, StringEvent::new);
        rxBus.observe(StringEvent.class).subscribe(subscriber);

        stringObservable.onNext(value);

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);

        assertEquals(value, subscriber.getOnNextEvents().get(0).getValue());
    }

    @Test
    public void forward_should_accept_event_class_without_arguments() throws Exception {
        final PublishSubject<Object> observable = PublishSubject.create();
        final TestSubscriber<SimpleEvent> subscriber = TestSubscriber.create();

        rxBus.forward(observable, SimpleEvent::new);
        rxBus.observe(SimpleEvent.class).subscribe(subscriber);

        observable.onNext(null);

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
    }

    @Test(expected = NullPointerException.class)
    public void forward_should_throw_for_null_event_producer() throws Exception {
        rxBus.forward(Observable.empty(), (Func0<Object>) null);
    }

    @Test(expected = NullPointerException.class)
    public void forward_value_should_throw_for_null_event_producer() throws Exception {
        rxBus.forward(Observable.empty(), (Func1<Object, Object>) null);
    }

    @Test
    public void forward_should_call_onError_if_event_producer_returns_null() throws Exception {
        final PublishSubject<Object> observable = PublishSubject.create();
        final AtomicReference<Throwable> errorRef = new AtomicReference<>();

        rxBus.forward(observable, () -> null, errorRef::set);
        rxBus.subscribe(SimpleEvent.class, TestSubscriber.create());

        observable.onNext("example");

        Throwable error = errorRef.get();

        assertNotNull(error);
        assertTrue(error instanceof NullPointerException);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: observe (without event class)
    //----------------------------------------------------------------------------------------------

    @Test
    public void observe_should_return_new_observable() throws Exception {
        Observable observable = rxBus.observe();

        assertNotNull(observable);
        assertNotSame(rxBus.subject, observable);
    }

    @Test
    public void observe_should_receive_all_events() throws Exception {
        final TestSubscriber<Object> subscriber = TestSubscriber.create();
        final SimpleEvent firstEvent = new SimpleEvent();
        final StringEvent secondEvent = new StringEvent("foo");

        rxBus.observe().subscribe(subscriber);
        rxBus.emit(firstEvent);
        rxBus.emit(secondEvent);

        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        assertEquals(firstEvent, subscriber.getOnNextEvents().get(0));
        assertEquals(secondEvent, subscriber.getOnNextEvents().get(1));
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: observe with event class
    //----------------------------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void observe_class_should_throw_if_null() throws Exception {
        rxBus.observe(null);
    }

    @Test
    public void observe_class_should_return_observable() throws Exception {
        Observable<SimpleEvent> observable = rxBus.observe(SimpleEvent.class);

        assertNotNull(observable);
    }

    //----------------------------------------------------------------------------------------------
    // TESTS: subscribe observer (without event class)
    //----------------------------------------------------------------------------------------------

    @Test
    public void subscribe_observer_should_receive_all_events() throws Exception {
        final TestSubscriber<Object> subscriber = TestSubscriber.create();
        final SimpleEvent firstEvent = new SimpleEvent();
        final StringEvent secondEvent = new StringEvent("foo");

        rxBus.subscribe(subscriber);
        rxBus.emit(firstEvent);
        rxBus.emit(secondEvent);

        subscriber.assertNoErrors();
        subscriber.assertValueCount(2);

        assertEquals(firstEvent, subscriber.getOnNextEvents().get(0));
        assertEquals(secondEvent, subscriber.getOnNextEvents().get(1));
    }

}
