package nl.gogognome.lib.swing;

import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class AbstractListTableModelTest {

    private final static ColumnDefinition COLUMN = new ColumnDefinition("editInvoiceView.tableHeader.descriptions", String.class, 300);

    private AbstractListTableModel<String> tableModel = new AbstractListTableModel<String>(
            Collections.singletonList(COLUMN),
            asList("one", "two", "three")) {

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return getRow(rowIndex);
        }
    };

    @Test
    public void testGetRowCount() {
        assertEquals(3, tableModel.getRowCount());
    }

    @Test
    public void testAddRow() {
        tableModel.addRow("four");
        assertTableModelContains("one", "two", "three", "four");
    }

    @Test
    public void testRemoveRow() {
        tableModel.removeRow(2);
        assertTableModelContains("one", "two");

        tableModel.removeRow(0);
        assertTableModelContains("two");

        tableModel.removeRow(0);
        assertTableModelContains();
    }

    @Test
    public void testRemoveRows() {
        tableModel.removeRows(new int[] { 2 });
        assertTableModelContains("one", "two");

        tableModel.removeRows(new int[] { 0, 1 });
        assertTableModelContains();
    }

    @Test
    public void testClear() {
        tableModel.clear();

        assertTableModelContains();
    }

    @Test
    public void testUpdateRow() {
        tableModel.updateRow(2, "THREE");

        assertTableModelContains("one", "two", "THREE");
    }

    @Test
    public void testReplaceRows() {
        tableModel.replaceRows(asList("ONE", "TWO"));

        assertTableModelContains("ONE", "TWO");
    }

    @Test
    public void testGetRow() {
        assertEquals("one", tableModel.getRow(0));
        assertEquals("two", tableModel.getRow(1));
        assertEquals("three", tableModel.getRow(2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetRowWithInvalidIndex() {
        tableModel.getRow(-1);
    }
    
    @Test
    public void testGetRows() {
        assertEquals(asList("one", "two", "three"), tableModel.getRows());
    }

    private void assertTableModelContains(String... expectedRows) {
        assertEquals(asList(expectedRows).toString(), tableModel.getRows().toString());
    }
}