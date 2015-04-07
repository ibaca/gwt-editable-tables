package editabletables.flextable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import editabletables.common.Person;
import java.util.List;

public class EditableFlexTable implements EntryPoint {
    public void onModuleLoad() {
        FlexTable table = new FlexTable();

        List<Person> data = Person.generate(10);
        int row = -1;
        for (final Person person : data) {
            row++;
            TextBox nameBox = new TextBox();
            nameBox.setValue(person.getName());
            nameBox.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override public void onValueChange(ValueChangeEvent<String> event) {
                    person.setName(event.getValue());
                }
            });
            table.setWidget(row, 0, nameBox);

            IntegerBox ageBox = new IntegerBox();
            ageBox.setValue(person.getAge());
            ageBox.addValueChangeHandler(new ValueChangeHandler<Integer>() {
                @Override public void onValueChange(ValueChangeEvent<Integer> event) {
                    person.setAge(event.getValue());
                }
            });

            table.setWidget(row, 1, ageBox);
        }

        RootPanel.get().add(table);
    }
}
