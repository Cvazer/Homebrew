package by.zti.spring.todo.view;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel{
    private JTable table;
    private ButtonsPanel buttonsPanel;

    private void init(){
        add(table, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setButtonsPanel(ButtonsPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
