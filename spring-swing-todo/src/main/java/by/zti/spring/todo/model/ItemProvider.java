package by.zti.spring.todo.model;

import java.util.List;

public class ItemProvider {
    private List<ToDo> items;

    public void addItem(ToDo item){
        items.add(item);
    }

    public void removeItem(ToDo item){
        items.remove(item);
    }

    public void removeItem(int index){
        items.remove(index);
    }

    public List<ToDo> getItems() {
        return items;
    }

    public void setItems(List<ToDo> items) {
        this.items = items;
    }
}
