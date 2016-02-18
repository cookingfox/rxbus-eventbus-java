package com.cookingfox.rxbus_eventbus;

/**
 * Represents an EventBus that emits its events using RX.
 */
public interface RxBus extends RxBusEmitter, RxBusForwarder, RxBusObserver, RxBusSubscriber {

}
