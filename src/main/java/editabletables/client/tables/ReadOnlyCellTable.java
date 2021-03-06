package editabletables.client.tables;

import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import editabletables.client.Person;
import java.util.List;
import java.util.Objects;

public class ReadOnlyCellTable extends Composite {
    private double timer;
    public ReadOnlyCellTable() {
        final FlowPanel container = new FlowPanel(); initWidget(container);
        final IntegerBox rows = new IntegerBox(); container.add(rows);
        final InlineLabel msg = new InlineLabel(); container.add(msg);
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
        rows.addValueChangeHandler(e -> {
            msg.setText("loading " + rows.getValue() + " rows");
            List<Person> people = Person.generate(e.getValue());
            timer = JsDate.now();
            table.setRowData(people);

        });
        table.addRedrawHandler(() -> {
            final double duration = (JsDate.now() - timer) / 1000.;
            msg.setText(rows.getValue() + " rows drawn in " + duration + "seconds");
        });
        rows.setValue(10, true);
    }
}
