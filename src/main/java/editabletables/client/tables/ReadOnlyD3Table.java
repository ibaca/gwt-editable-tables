package editabletables.client.tables;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.UpdateSelection;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Widget;
import editabletables.client.Person;
import java.util.List;
import java.util.Objects;

public class ReadOnlyD3Table extends Composite {
    private double timer;
    public ReadOnlyD3Table() {
        final FlowPanel container = new FlowPanel(); initWidget(container);
        final IntegerBox rows = new IntegerBox(); container.add(rows);
        final InlineLabel msg = new InlineLabel(); container.add(msg);
        final Widget table = new HTMLPanel("table", ""); container.add(table);

        rows.addValueChangeHandler(e -> {
            msg.setText("loading " + rows.getValue() + " rows");
            List<Person> people = Person.generate(e.getValue());
            timer = JsDate.now();
            UpdateSelection tr = D3.select(table).selectAll("tr").data(people);
            tr.enter().append("tr");
            tr.exit().remove();
            tr.html((context, d, index) -> {
                final Person p = d.as();
                return "<td>" + p.getName() + "</td>"
                        + "<td>" + Objects.toString(p.getAge()) + "</td>"
                        + "<td>" + Objects.toString(p.isAlive()) + "</td>";
            });
            final double duration = (JsDate.now() - timer) / 1000.;
            msg.setText(rows.getValue() + " rows drawn in " + duration + "seconds");
        });
        rows.setValue(10, true);
    }
}
