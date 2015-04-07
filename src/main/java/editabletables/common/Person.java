package editabletables.common;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.LegacyHandlerWrapper;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Person implements HasValueChangeHandlers<Person> {
    private final EventBus eventHandler = new SimpleEventBus();
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        final Person oldValue = new Person(this.name, this.age);
        this.name = name;
        ValueChangeEvent.fireIfNotEqual(this, oldValue, this);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        final Person oldValue = new Person(this.name, this.age);
        this.age = age;
        ValueChangeEvent.fireIfNotEqual(this, oldValue, this);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getName(), person.getName()) &&
                Objects.equals(getAge(), person.getAge());
    }

    @Override public int hashCode() {
        return Objects.hash(getName(), getAge());
    }

    @Override public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Person> handler) {
        return new LegacyHandlerWrapper(eventHandler.addHandler(ValueChangeEvent.getType(), handler));
    }

    @Override public void fireEvent(GwtEvent<?> event) {
        eventHandler.fireEvent(event);
    }

    public static List<Person> generate(int num) {
        return generate(num, new ValueChangeHandler<Person>() {
            @Override public void onValueChange(ValueChangeEvent<Person> event) {
                Logger.getGlobal().info("change event: " + event.toDebugString());
            }
        });
    }

    public static List<Person> generate(int num, ValueChangeHandler<Person> handler) {
        final List<Person> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            Person person = new Person("Person#" + i, (int) (Math.random() * 100.));
            person.addValueChangeHandler(handler);
            result.add(person);
        }
        return result;
    }
}
