package editabletables.client.tables;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import editabletables.client.Person;
import java.util.Objects;

public class ReadOnlyCellTable extends Composite {
    public ReadOnlyCellTable() {
        final FlowPanel container = new FlowPanel(); initWidget(container);
        final IntegerBox rows = new IntegerBox(); container.add(rows);
        final CellTable<Person> table = new CellTable<>(); container.add(table);

        table.addColumn(new TextColumn<Person>() {
            @Override public String getValue(Person object) { return object.getName(); }
        });
        table.addColumn(new TextColumn<Person>() {
            @Override public String getValue(Person object) { return Objects.toString(object.getAge()); }
        });
        table.addColumn(new TextColumn<Person>() {
            @Override public String getValue(Person object) { return Objects.toString(object.isAlive()); }
        });

        // connect and initialize
        rows.addValueChangeHandler(e -> table.setRowData(Person.generate(e.getValue())));
        rows.setValue(10, true);
    }
}
