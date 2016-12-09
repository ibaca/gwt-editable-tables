package editabletables.client;

import static com.google.gwt.dom.client.Style.Unit.PX;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import editabletables.client.tables.EditableCellTable;
import editabletables.client.tables.EditableFlexTable;
import editabletables.client.tables.EditableGwtQueryTable;
import editabletables.client.tables.ReadOnlyCellTable;
import editabletables.client.tables.ReadOnlyD3Table;

public class EditableTables implements EntryPoint {
    @Override public void onModuleLoad() {
        TabLayoutPanel tabs = new TabLayoutPanel(32, PX);
        tabs.add(new ScrollPanel(new ReadOnlyCellTable()), "Cell read only");
        tabs.add(new ScrollPanel(new ReadOnlyD3Table()), "D3 read only");
        tabs.add(new ScrollPanel(new EditableCellTable()), "Cell editable");
        tabs.add(new ScrollPanel(new EditableFlexTable()), "Flex editable");
        tabs.add(new ScrollPanel(new EditableGwtQueryTable()), "GQuery editable");
        RootLayoutPanel.get().add(tabs);
    }
}
