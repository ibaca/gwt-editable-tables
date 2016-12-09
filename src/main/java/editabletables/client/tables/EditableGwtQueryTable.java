package editabletables.client.tables;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;
import editabletables.client.Person;
import java.util.List;

public class EditableGwtQueryTable extends SimplePanel {
    public EditableGwtQueryTable() {
        addAttachHandler(e -> {
            getElement().setInnerHTML(""); // cleanup on each attach or detach
            if (!e.isAttached()) return;

            final GQuery table = $("<table/>");
            final List<Person> data = Person.generate(10);

            for (final Person person : data) {
                GQuery row = $(DOM.createTR());
                row.append($("<td><input type=text /></td>").children()
                        .attr("value", person.getName())
                        .on("change", new Function() {
                            @Override public void f() {
                                InputElement input = getElement();
                                person.setName(input.getValue());
                            }
                        }));
                row.append($("<td><input type=number /></td>").children()
                        .attr("value", person.getAge())
                        .on("change", new Function() {
                            @Override public void f() {
                                InputElement input = getElement();
                                try {
                                    person.setAge(Integer.valueOf(input.getValue()));
                                } catch (Exception ignore) {
                                }
                            }
                        }));
                row.appendTo(table);
            }

            table.appendTo(getElement());
        });
    }
}
