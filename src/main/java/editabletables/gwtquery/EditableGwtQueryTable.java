package editabletables.gwtquery;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import editabletables.common.Person;
import java.util.List;

public class EditableGwtQueryTable implements EntryPoint {
    public void onModuleLoad() {
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

        table.appendTo(RootPanel.get().getElement());
    }
}
