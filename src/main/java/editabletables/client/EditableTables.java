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

public class EditableTables implements EntryPoint {
    @Override public void onModuleLoad() {
        TabLayoutPanel tabs = new TabLayoutPanel(32, PX);
        tabs.add(new ScrollPanel(new ReadOnlyCellTable()), "Cell view");
        tabs.add(new ScrollPanel(new EditableCellTable()), "Cell edit");
        tabs.add(new ScrollPanel(new EditableFlexTable()), "Flex edit");
        tabs.add(new ScrollPanel(new EditableGwtQueryTable()), "GQuery edit");
        RootLayoutPanel.get().add(tabs);
    }
}
