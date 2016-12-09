package editabletables.client.tables;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.fromTrustedString;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import editabletables.client.Person;
import java.util.Objects;

public class EditableCellTable extends Composite {
    public EditableCellTable() {
        final FlowPanel container = new FlowPanel(); initWidget(container);
        final IntegerBox rows = new IntegerBox(); container.add(rows);
        final CellTable<Person> table = new CellTable<>(); container.add(table);

        final Column<Person, String> nameColumn = new Column<Person, String>(new TextInputCell()) {
            { setFieldUpdater((i, p, v) -> p.setName(v)); }

            @Override public String getValue(Person object) { return object.getName(); }
        };
        table.addColumn(nameColumn);

        final Column<Person, Integer> ageColumn = new Column<Person, Integer>(new IntegerInputCell()) {
            { setFieldUpdater((i, p, v) -> p.setAge(v)); }

            @Override public Integer getValue(Person object) { return object.getAge(); }
        };
        table.addColumn(ageColumn);

        final Column<Person, Boolean> aliveColumn = new Column<Person, Boolean>(new CheckboxCell()) {
            {
                setFieldUpdater((i, p, v) -> {
                    if (Window.confirm("Are You God?")) p.setAlive(v);
                    else {
                        ((CheckboxCell) getCell()).clearViewData(p);
                        table.redrawRow(i); /* not sure if 'i' is absolute or relative ¿?¿?*/
                    }
                });
            }

            @Override public Boolean getValue(Person object) { return object.isAlive(); }
        };
        table.addColumn(aliveColumn);

        // connect and initialize
        rows.addValueChangeHandler(e -> table.setRowData(Person.generate(e.getValue())));
        rows.setValue(10, true);
    }

    private static class IntegerInputCell extends AbstractInputCell<Integer, IntegerInputCell.ViewData> {
        public IntegerInputCell() {
            super("change");
        }

        @Override
        public void onBrowserEvent(Context c, Element p, Integer v, NativeEvent e, ValueUpdater<Integer> u) {
            super.onBrowserEvent(c, p, v, e, u);

            // Ignore events that don't target the input.
            Element target = e.getEventTarget().cast();
            if (!p.getFirstChildElement().isOrHasChild(target)) {
                return;
            }

            Object key = c.getKey();
            ViewData viewData = getViewData(key);
            String eventType = e.getType();
            if ("change".equals(eventType)) {
                InputElement input = p.getFirstChild().cast();

                // Save the new value in the view data.
                if (viewData == null) {
                    viewData = new ViewData();
                    viewData.value = v;
                    setViewData(key, viewData);
                }
                String newValue = input.getValue();
                viewData.setValue(newValue);
                finishEditing(p, viewData.value, key, u);

                // Update the value updater, which updates the field updater.
                if (u != null) u.update(viewData.value);
            }
        }

        @Override
        public void render(Context context, Integer value, SafeHtmlBuilder sb) {
            Object key = context.getKey();
            ViewData viewData = getViewData(key);
            if (viewData != null && Objects.equals(viewData.value, value)) {
                // Clear the view data if the value is the same as the current value.
                clearViewData(key);
                viewData = null;
            }

            Integer pendingValue = (viewData == null) ? null : viewData.value;
            String asString = Objects.toString(pendingValue != null ? pendingValue : value);
            sb.append(fromTrustedString("<input type=\"text\" value=\"" + asString + "\" tabindex=\"-1\"/>"));
        }

        @Override
        protected void onEnterKeyDown(Context context, Element parent, Integer value,
                NativeEvent event, ValueUpdater<Integer> valueUpdater) {
            Element target = event.getEventTarget().cast();
            if (getInputElement(parent).isOrHasChild(target)) {
                finishEditing(parent, value, context.getKey(), valueUpdater);
            } else {
                super.onEnterKeyDown(context, parent, value, event, valueUpdater);
            }
        }

        static class ViewData {
            private Integer value;

            public void setValue(String value) {
                try {
                    this.value = Integer.parseInt(value);
                } catch (Exception ignore) { }
            }
        }
    }
}
