package by.zti.spring.todo.view;

import by.zti.spring.todo.model.ItemProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveAction extends AbstractAction{
    private ItemProvider provider;
    private JTable table;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRow()==-1||table.isEditing()){return;}
        provider.removeItem(table.getSelectedRow());
        table.updateUI();
        table.getSelectionModel().clearSelection();
    }

    public void setProvider(ItemProvider provider) {
        this.provider = provider;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
