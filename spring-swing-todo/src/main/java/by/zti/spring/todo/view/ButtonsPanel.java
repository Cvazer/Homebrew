package by.zti.spring.todo.view;

import javax.swing.*;

public class ButtonsPanel extends JPanel{
    private JButton addButton;
    private JButton removeButton;
    private AbstractAction addAction;
    private AbstractAction removeAction;

    private void init(){
        addButton.addActionListener(addAction);
        removeButton.addActionListener(removeAction);
        add(addButton);
        add(removeButton);
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public void setRemoveButton(JButton removeButton) {
        this.removeButton = removeButton;
    }

    public void setAddAction(AbstractAction addAction) {
        this.addAction = addAction;
    }

    public void setRemoveAction(AbstractAction removeAction) {
        this.removeAction = removeAction;
    }
}
