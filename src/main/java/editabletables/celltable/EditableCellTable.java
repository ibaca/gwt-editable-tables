package editabletables.celltable;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.fromTrustedString;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.RootPanel;
import editabletables.common.Person;
import java.util.List;
import java.util.Objects;

public class EditableCellTable implements EntryPoint {
    public void onModuleLoad() {
        final CellTable<Person> table = new CellTable<>();

        //@formatter:off
        final Column<Person, String> nameColumn = new Column<Person, String>(new TextInputCell()) {
            { setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override public void update(int i, Person p, String v) { p.setName(v); } }); }
            @Override public String getValue(Person object) { return object.getName(); } };
        table.addColumn(nameColumn);
        final Column<Person, Integer> ageColumn = new Column<Person, Integer>(new IntegerInputCell()) {
            { setFieldUpdater(new FieldUpdater<Person, Integer>() {
            @Override public void update(int i, Person p, Integer v) { p.setAge(v); } }); }
            @Override public Integer getValue(Person object) { return object.getAge(); } };
        table.addColumn(ageColumn);
        //@formatter:on

        final List<Person> data = Person.generate(10);
        table.setRowData(data);

        RootPanel.get().add(table);
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
                } catch (Exception ignore) {
                }
            }
        }
    }
}
