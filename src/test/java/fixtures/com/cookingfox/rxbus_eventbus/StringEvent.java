package fixtures.com.cookingfox.rxbus_eventbus;

/**
 * Example event class with one String constructor argument.
 */
public class StringEvent {

    final String value;

    public StringEvent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringEvent{" +
                "value='" + value + '\'' +
                '}';
    }

}
