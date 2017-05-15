package by.zti.spring.todo.view;

import by.zti.spring.todo.Main;
import by.zti.spring.todo.model.ItemProvider;
import by.zti.spring.todo.model.ToDo;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractAction{
    private ItemProvider provider;
    private JTable table;

    @Override
    public void actionPerformed(ActionEvent e) {
        provider.addItem(Main.context.getBean("todo", ToDo.class));
        table.updateUI();
    }

    public void setProvider(ItemProvider provider) {
        this.provider = provider;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
