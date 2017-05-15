package by.zti.spring.todo.view;

import by.zti.spring.todo.model.ItemProvider;

import javax.swing.table.AbstractTableModel;

public class ToDoTableModel extends AbstractTableModel{
    private ItemProvider provider;

    @Override
    public int getRowCount() {
        return provider.getItems().size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return provider.getItems().get(rowIndex).getText();
    }

    public void setProvider(ItemProvider provider) {
        this.provider = provider;
    }
}
