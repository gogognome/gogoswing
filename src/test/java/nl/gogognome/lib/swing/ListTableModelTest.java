package nl.gogognome.lib.swing;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class ListTableModelTest {

    private ListTableModel<String> tableModel = new ListTableModel<String>(
            singletonList(ColumnDefinition.<String>builder("editInvoiceView.tableHeader.descriptions", String.class, 300)
                    .add(row -> row)
                    .build()),
            asList("one", "two", "three")) {
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
        tableModel.setRows(asList("ONE", "TWO"));

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